package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CatalogoBase;
import model.TipoConsulta;

import java.sql.*;

public class CatConsultaDAO implements BasicCatDAO {

    Connection connection;

    public CatConsultaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(CatalogoBase tipoConsulta) {
        TipoConsulta consulta1 = (TipoConsulta) tipoConsulta;

        String query = "Insert into TipoConsulta(cveTipo, descripcion, costos) values (?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, consulta1.getClave());
            ps.setString(2, consulta1.getDescription());
            ps.setDouble(3, consulta1.getCosto());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean update(CatalogoBase tipoConsulta) {
        TipoConsulta consulta = (TipoConsulta) tipoConsulta;

        String query = "update TipoConsulta" +
                " set descripcion = ?, " +
                "     costos = ?" +
                " where cveTipo = '" + consulta.getClave() + "'";

        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, consulta.getDescription());
            ps.setFloat(2, consulta.getCosto());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public ObservableList<TipoConsulta> selectAll() {
        String query = "SELECT * FROM TipoConsulta";

        ObservableList<TipoConsulta> listTipo = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                float cos = rs.getFloat(3);

                listTipo.add(new TipoConsulta(cve, desc, cos));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTipo;
    }

    @Override
    public boolean delete(CatalogoBase tipoConsulta) {
        String query = "DELETE FROM TipoConsulta WHERE cveTipo = '" +tipoConsulta.getClave()+"'";
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
