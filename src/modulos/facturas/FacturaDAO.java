package modulos.facturas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;
import modulos.productos.ProductDAO;
import modulos.productos.Producto;

import java.sql.*;
import java.util.List;

public class FacturaDAO implements BasicDAO {

    private Connection connection;

    public FacturaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Object bean) {
        Factura fact = (Factura) bean;

        String query = "Insert into Factura(factura,fechaExp,subtotal,descuento,impuesto, total,codProveedor) values (?,?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, fact.getFactura());
            ps.setDate(2, fact.getFechaExp());
            ps.setDouble(3, fact.getSubtotal());
            ps.setDouble(4, fact.getDescuento());
            ps.setDouble(5, fact.getImpuesto());
            ps.setDouble(6, fact.getTotal());
            ps.setString(7, fact.getCodProv());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean update(Object bean) {
        return false;
    }

    @Override
    public ObservableList<Factura> selectAll() {
        String query = "SELECT f.*,  P.nombre" +
                " FROM Factura f join Proveedor P on f.codProveedor = P.codProveedor";
        return select(query);
    }

    public ObservableList<Factura> selectByProv(String codProv) {
        String query = "SELECT f.*,  P.nombre" +
                " FROM Factura f join Proveedor P on f.codProveedor = P.codProveedor " +
                " WHERE P.codProveedor = '" + codProv + "'";
        return select(query);
    }

    @Override
    public boolean delete(Object bean) {
        return false;
    }

    /**
     * Regresa el Folio maximo + 1
     *
     * @return
     */
    public int getNextFolio() {
        int nextFolio = 1;

        String query = "SELECT MAX(folio) FROM Factura";
        Statement st;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next())
                nextFolio = rs.getInt(1) + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextFolio;
    }

    /**
     * Registra una lista de productos en una determinada factura
     *
     * @param products
     * @param folio    El folio de la factura donde se van a registrar
     * @return
     */
    public boolean registerProducts(List<TableBean> products, int folio) {

        String query = "Insert into ProductoFactura(folio,codProd,cantidad,precioUnit, descuento) values (?,?,?,?, ?)";
        try {
            for (TableBean prod : products) {
                if (prod.getProducto() != null) {
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, folio);
                    ps.setString(2, prod.getProducto());
                    ps.setInt(3, prod.getCantidad());
                    ps.setDouble(4, prod.getCosto());
                    ps.setInt(5, prod.getDescuento());
                    ps.execute();
                    ps.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        updateStockProductos(products);
        return true;
    }

    private void updateStockProductos(List<TableBean> products) {
        ProductDAO productDAO = new ProductDAO(connection);
        for (TableBean prod : products)
            productDAO.updateStock(prod.getProducto(), prod.getCantidad());

    }

    private ObservableList<Factura> select(String query) {
        ObservableList<Factura> listLaboratorio = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int folio = rs.getInt(1);
                String factura = rs.getString(2);
                Date fecha = rs.getDate(3);
                double sub = rs.getDouble(4);
                double des = rs.getDouble(5);
                double imp = rs.getDouble(6);
                double total = rs.getDouble(7);
                String codProv = rs.getString(8);
                String nombreProv = rs.getString(9);

                listLaboratorio.add(new Factura(folio, factura, fecha, sub, des, imp, total, codProv, nombreProv));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listLaboratorio;
    }

    /**
     * Regresa todos los productos de una determinada factura
     *
     * @param folio
     * @return
     */
    public ObservableList<TableBean> selectProductByFact(int folio) {
        ObservableList<TableBean> beans = FXCollections.observableArrayList();

        String query = "select P.codProd, PF.cantidad, P.descripcion, PF.precioUnit, PF.descuento\n" +
                "from Producto P join ProductoFactura PF on P.codProd = PF.codProd\n" +
                "where PF.folio = " + folio;

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String codProd = rs.getString(1);
                int cantidad = rs.getInt(2);
                String desc = rs.getString(3);
                double precio = rs.getDouble(4);
                int descuento = rs.getInt(5);
                TableBean bean = new TableBean(codProd, cantidad, desc, precio, descuento);
                bean.setTotalDescuento();
                beans.add(bean);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beans;
    }
}
