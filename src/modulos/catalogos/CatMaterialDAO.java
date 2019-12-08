package modulos.catalogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CatMaterialDAO implements BasicCatDAO {

    Connection connection;

    public CatMaterialDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(CatalogoBase tipo) {
        String query = "Insert into TipoMaterial(cveMaterial, descripcion) values (?,?)";
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
        String query = "update TipoMaterial" +
                " set descripcion = ?" +
                " where cveMaterial = '" + tipo.getClave() + "'";
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
    public ObservableList<TipoMaterial> selectAll() {
        String query = "SELECT * FROM TipoMaterial";
        ObservableList<TipoMaterial> listTipos = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                listTipos.add(new TipoMaterial(cve, desc));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTipos;
    }

    @Override
    public boolean delete(CatalogoBase tipo) {
        String query = "DELETE FROM TipoMaterial" +
                " WHERE cveMaterial = '" +tipo.getClave()+"'";
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
