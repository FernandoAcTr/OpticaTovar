package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CatalogoBase;
import model.Marca;

import java.sql.*;

public class CatMarcaDAO implements BasicCatDAO {

    Connection connection;

    public CatMarcaDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Guarda una nueva Marca
     * @param marca
     * @return
     */
    public boolean insert(CatalogoBase marca) {
        String query = "Insert into Marca(cveMarca, descripcion) values (?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, marca.getClave());
            ps.setString(2, marca.getDescription());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Actualiza una marca
     * @param marca
     * @return
     */
    public boolean update(CatalogoBase marca) {
        String query = "update Marca set descripcion = ?" +
                " where cveMarca = '" + marca.getClave() + "'";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, marca.getDescription());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Devuelve todas las marcas
     * @return
     */
    public ObservableList<Marca> selectAll() {
        String query = "SELECT * FROM Marca";
        ObservableList<Marca> listMarcas = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                listMarcas.add(new Marca(cve, desc));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listMarcas;
    }

    public boolean delete(CatalogoBase cveMarca){
        String query = "DELETE FROM Marca WHERE cveMarca = '" +cveMarca.getClave()+"'";
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


}
