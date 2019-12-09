package modulos.terapias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;
import utils.FormatDate;

import java.sql.*;

public class TerapiaDAO implements BasicDAO {

    Connection connection;

    public TerapiaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Object bean) {
        Terapia terapia = (Terapia) bean;
        String query = "Insert into Terapia(idCliente, fecha, hora, rfcTerapeuta) values (?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, terapia.getIdCliente());
            ps.setDate(2, terapia.getFecha());
            ps.setTime(3, terapia.getHora());
            ps.setString(4, terapia.getRfcTerapeuta());

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
    public ObservableList<Terapia> selectAll() {
        String query = "SELECT * FROM Terapia";
        return select(query);
    }

    public ObservableList<Terapia> selectByDate(int idCliente, Date desde) {
        ObservableList<Terapia> listTeras = FXCollections.observableArrayList();

        FormatDate formatDate = new FormatDate(desde);

        String query = "SELECT * FROM Terapia " +
                " WHERE fecha >= '" + formatDate.getSQLFormatDay() + "'" +
                " AND IdCliente = " + idCliente;

        return select(query);
    }

    @Override
    public boolean delete(Object bean) {
        Terapia tera = (Terapia) bean;

        String query = "DELETE FROM Terapia " +
                " WHERE idCliente = '" + tera.getIdCliente() + "'" +
                " AND fecha = '" + tera.getFecha().toString() + "'";
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

    private ObservableList<Terapia> select(String query) {
        ObservableList<Terapia> listTeras = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int idCliente = rs.getInt(1);
                Date fecha = rs.getDate(2);
                Time hora = rs.getTime(3);
                String rfc = rs.getString(4);

                listTeras.add(new Terapia(idCliente, fecha, hora, rfc));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTeras;
    }
}
