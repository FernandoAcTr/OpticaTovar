package modulos.productos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;

import java.sql.*;

public class ProductDAO implements BasicDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Object bean) {
        Producto prod = (Producto) bean;
        String query = "Insert into Producto(codProd, precio, linea, stock,color, descripcion, genero, cveMarca, cveTipo) " +
                " values (?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, prod.getCodProd());
            ps.setDouble(2, prod.getPrecio());
            ps.setString(3, prod.getLinea());
            ps.setInt(4, prod.getStock());
            ps.setString(5, prod.getColor());
            ps.setString(6, prod.getDescripcion());
            ps.setString(7, prod.getGenero());
            ps.setString(8, prod.getCveMarca());
            ps.setString(9, prod.getCveTipo());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean update(Object bean) {
        Producto prod = (Producto) bean;

        String query = "update Producto" +
                " set precio = ?, " +
                " linea = ?," +
                " color = ?," +
                " descripcion = ?," +
                " genero = ?," +
                " cveMarca = ?," +
                " cveTipo = ?" +
                " where codProd = '" + prod.getCodProd() + "'";
        boolean success = false;

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setDouble(1, prod.getPrecio());
            ps.setString(2, prod.getLinea());
            ps.setString(3, prod.getColor());
            ps.setString(4, prod.getDescripcion());
            ps.setString(5, prod.getGenero());
            ps.setString(6, prod.getCveMarca());
            ps.setString(7, prod.getCveTipo());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public ObservableList<Producto> selectAll() {
        String query = "SELECT * FROM Producto";
        return select(query);
    }

    public Producto selectProductByID(String codProd) {
        String query = "SELECT * FROM Producto" +
                " WHERE codProd = '" + codProd + "'";

        ObservableList<Producto> products = select(query);

        if (products.size() == 0)
            return null;
        else
            return products.get(0);
    }

    public ObservableList<Producto> selectProductByType(String cveType) {
        String query = "SELECT * FROM Producto" +
                " WHERE cveTipo = '" + cveType + "'";
        return select(query);
    }

    public ObservableList<Producto> selectProductByMarca(String cveMarca) {
        String query = "SELECT * FROM Producto" +
                " WHERE cveMarca = '" + cveMarca + "'";
        return select(query);
    }

    public ObservableList<Producto> selectProductByDescription(String description) {
        String query = "SELECT * FROM Producto" +
                " WHERE descripcion like '%" + description + "%'";
        return select(query);
    }

    @Override
    public boolean delete(Object bean) {
        Producto prod = (Producto) bean;

        String query = "DELETE FROM Producto WHERE codProd = '" + prod.getCodProd() + "'";
        boolean success = false;
        try {
            Statement st = connection.createStatement();
            success = !st.execute(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public ObservableList<Producto> select(String query) {

        ObservableList<Producto> listProducts = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String codProd = rs.getString(1);
                double precio = rs.getDouble(2);
                String linea = rs.getString(3);
                int stock = rs.getInt(4);
                String color = rs.getString(5);
                String descripcion = rs.getString(6);
                String genero = rs.getString(7);
                String cveMarca = rs.getString(8);
                String cveTipo = rs.getString(9);

                listProducts.add(new Producto(codProd, (float) precio, linea, stock, color, descripcion, genero, cveMarca, cveTipo));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listProducts;
    }

    public boolean updateStock(String codProd, int stockIncrement) {

        String query = "update Producto " +
                " set stock = stock + ?" +
                " where codProd = ?";
        boolean success = false;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, stockIncrement);
            ps.setString(2, codProd);
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
