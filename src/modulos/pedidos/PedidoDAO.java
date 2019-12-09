package modulos.pedidos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;
import modulos.facturas.TableBean;
import modulos.productos.ProductDAO;

import java.sql.*;
import java.util.List;

public class PedidoDAO implements BasicDAO {

    private Connection connection;

    public PedidoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Object bean) {
        Pedido ped = (Pedido) bean;

        String query = "Insert into Pedido(status, fecha, subtotal, descuento,iva, total, observaciones, rfcTrab, idCliente) values (?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, ped.getStatus());
            ps.setDate(2, ped.getFecha());
            ps.setDouble(3, ped.getSubtotal());
            ps.setDouble(4, ped.getDescuento());
            ps.setDouble(5, ped.getImpuesto());
            ps.setDouble(6, ped.getTotal());
            ps.setString(7, ped.getObs());
            ps.setString(8, ped.getRfcTrab());
            ps.setInt(9, ped.getIdCliente());
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
    public ObservableList selectAll() {
        String query = "Select * from Pedido";
        return select(query);
    }

    public ObservableList<Pedido> selectByCliente(int idCliente){
        String query = "Select * from Pedido " +
                " where idCliente = " + idCliente;
        return select(query);
    }

    /**
     * Regresa el Folio maximo + 1
     *
     * @return
     */
    public int getNextFolio() {
        int nextFolio = 1;

        String query = "SELECT MAX(noPedido) FROM Pedido";
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
     * Registra una lista de productos en un determinado pedido
     *
     * @param products
     * @param noPedido    El noPedido del pedido a registrar
     * @return
     */
    public boolean registerProducts(List<TableBean> products, int noPedido) {

        String query = "Insert into RegistroVenta(codProd,noPedido,cantidad,precioUnit, descuento) values (?,?,?,?,?)";
        try {
            for (TableBean prod : products) {
                if (prod.getProducto() != null) {
                    PreparedStatement ps = connection.prepareStatement(query);

                    ps.setString(1, prod.getProducto());
                    ps.setInt(2, noPedido);
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
            productDAO.decrementStock(prod.getProducto(), prod.getCantidad());
    }

    @Override
    public boolean delete(Object bean) {
        return false;
    }

    private ObservableList<Pedido> select(String query) {
        ObservableList<Pedido> listPeds = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int noPed = rs.getInt("noPedido");
                String status = rs.getString("status");
                Date fecha = rs.getDate("fecha");
                double sub = rs.getDouble("subtotal");
                double des = rs.getDouble("descuento");
                double imp = rs.getDouble("iva");
                double total = rs.getDouble("total");
                String obs = rs.getString("observaciones");
                String rfcTrab = rs.getString("rfcTrab");
                int idCliente = rs.getInt("idCliente");

                listPeds.add(new Pedido(noPed, status, fecha, sub, des, imp, total, obs, rfcTrab, idCliente));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listPeds;
    }

    /**
     * Regresa todos los productos de un determinado pedido
     *
     * @param noPed
     * @return
     */
    public ObservableList<TableBean> selectProductByNoPed(int noPed) {
        ObservableList<TableBean> beans = FXCollections.observableArrayList();

        String query = "select P.codProd, RV.cantidad, P.descripcion, RV.precioUnit, RV.descuento " +
                "from Producto P join RegistroVenta RV on P.codProd = RV.codProd " +
                "where RV.noPedido = " + noPed;

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
