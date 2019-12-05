package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CatalogoBase;
import model.Puesto;

import java.sql.*;

public class CatPuestoDAO implements BasicCatDAO {

    Connection connection;

    public CatPuestoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(CatalogoBase puesto) {
        Puesto puesto1 = (Puesto) puesto;

        String query = "Insert into Puesto(cvePuesto, descripcion, salario) values (?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, puesto1.getClave());
            ps.setString(2, puesto1.getDescription());
            ps.setDouble(3, puesto1.getSalario());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean update(CatalogoBase puesto) {
        Puesto puesto1 = (Puesto) puesto;

        String query = "update Puesto" +
                " set descripcion = ?, " +
                "     salario = ?" +
                " where cvePuesto = '" + puesto1.getClave() + "'";

        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, puesto1.getDescription());
            ps.setFloat(2, puesto1.getSalario());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public ObservableList<Puesto> selectAll() {
        String query = "SELECT * FROM Puesto";

        ObservableList<Puesto> listPuesto = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                float sal = rs.getFloat(3);

                listPuesto.add(new Puesto(cve, desc, sal));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listPuesto;
    }

    @Override
    public boolean delete(CatalogoBase puesto) {
        String query = "DELETE FROM Puesto WHERE cvePuesto = '" +puesto.getClave()+"'";
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
