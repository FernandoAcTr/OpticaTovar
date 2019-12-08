package modulos.catalogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CatLdcDAO implements BasicCatDAO {
    Connection connection;

    public CatLdcDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(CatalogoBase tipo) {
        String query = "Insert into TipoLDC(cveTipo, descripcion) values (?,?)";
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
        String query = "update TipoLDC" +
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
    public ObservableList<TipoLDC> selectAll() {
        String query = "SELECT * FROM TipoLDC";
        ObservableList<TipoLDC> listTypes = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                listTypes.add(new TipoLDC(cve, desc));
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
        String query = "DELETE FROM TipoLDC" +
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
