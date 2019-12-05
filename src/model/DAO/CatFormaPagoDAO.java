package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CatalogoBase;
import model.FormaPago;

import java.sql.*;

public class CatFormaPagoDAO implements BasicCatDAO {
    Connection connection;

    public CatFormaPagoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(CatalogoBase payForm) {
        String query = "Insert into FormaPago(cveForma, descripcion) values (?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, payForm.getClave());
            ps.setString(2, payForm.getDescription());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean update(CatalogoBase payForm) {
        String query = "update FormaPago" +
                " set descripcion = ?" +
                " where cveForma = '" + payForm.getClave() + "'";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, payForm.getDescription());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public ObservableList<FormaPago> selectAll() {
        String query = "SELECT * FROM FormaPago";
        ObservableList<FormaPago> listPayForms = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cve = rs.getString(1);
                String desc = rs.getString(2);
                listPayForms.add(new FormaPago(cve, desc));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listPayForms;
    }

    @Override
    public boolean delete(CatalogoBase payForm) {
        String query = "DELETE FROM FormaPago WHERE cveForma = '" + payForm.getClave() + "'";
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

