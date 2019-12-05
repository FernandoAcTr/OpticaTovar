package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CatalogoBase;
import model.TipoProd;

import java.sql.*;

public class CatProdDAO implements BasicCatDAO {

    Connection connection;

    public CatProdDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(CatalogoBase tipo) {
        String query = "Insert into TipoProd(cveTipo, descripcion) values (?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tipo.getClave());
            ps.setString(2, tipo.getDescription());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean update(CatalogoBase tipo) {
        String query = "update TipoProd" +
                " set descripcion = ?" +
                " where cveTipo = '" + tipo.getClave() + "'";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tipo.getDescription());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public ObservableList<TipoProd> selectAll() {
        String query = "SELECT * FROM TipoProd";
        ObservableList<TipoProd> listTypes = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                listTypes.add(new TipoProd(cve, desc));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTypes;
    }

    @Override
    public boolean delete(CatalogoBase tipo) {
        String query = "DELETE FROM TipoProd" +
                " WHERE cveTipo = '" + tipo.getClave() + "'";
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
