package modulos.facturas;

import javafx.collections.ObservableList;
import model.BasicDAO;
import modulos.productos.ProductDAO;

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
    public ObservableList selectAll() {
        return null;
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
     * @param products
     * @param folio El folio de la factura donde se van a registrar
     * @return
     */
    public boolean registerProducts(List<TableBean> products, int folio) {

        String query = "Insert into ProductoFactura(folio,codProd,cantidad,precioUnit) values (?,?,?,?)";
        try {
            for (TableBean prod : products) {
                if(prod.getProducto() != null) {
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, folio);
                    ps.setString(2, prod.getProducto());
                    ps.setInt(3, prod.getCantidad());
                    ps.setDouble(4, prod.getCosto());
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

    private void updateStockProductos(List<TableBean> products){
        ProductDAO productDAO = new ProductDAO(connection);
        for (TableBean prod : products)
            productDAO.updateStock(prod.getProducto(), prod.getCantidad());

    }
}
