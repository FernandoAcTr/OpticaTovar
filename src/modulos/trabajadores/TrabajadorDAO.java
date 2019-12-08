package modulos.trabajadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;

import java.sql.*;

public class TrabajadorDAO implements BasicDAO {
    Connection connection;

    public TrabajadorDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Guarda una nuevo Trabajador
     *
     * @param bean
     * @return
     */
    public boolean insert(Object bean) {
        Trabajador trab = (Trabajador) bean;

        String query = "Insert into Trabajador(rfc, genero, nombre, apellidos," +
                "domicilio, fechaNac, ciudad, estado, telefono, fechaContra, cvePuesto) values (?,?,?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, trab.getRfc());
            ps.setString(2, trab.getGenero());
            ps.setString(3, trab.getNombre());
            ps.setString(4, trab.getApellidos());
            ps.setString(5, trab.getDomicilio());
            ps.setDate(6, trab.getFechaNac());
            ps.setString(7, trab.getCiudad());
            ps.setString(8, trab.getEstado());
            ps.setString(9, trab.getTelefono());
            ps.setDate(10, trab.getFechaContra());
            ps.setString(11, trab.getCvePuesto());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Actualiza un Trabajador
     *
     * @param bean
     * @return
     */
    public boolean update(Object bean) {
        Trabajador trab = (Trabajador) bean;

        String query = "update Trabajador " +
                "set genero = ?," +
                "nombre = ?," +
                "apellidos = ?," +
                "domicilio = ?," +
                "fechaNac = ?," +
                "ciudad = ?," +
                "estado = ?," +
                "telefono = ?," +
                "fechaContra = ?," +
                "cvePuesto = ?" +
                " where rfc = '" + trab.getRfc() + "'";
        boolean success = false;

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, trab.getGenero());
            ps.setString(2, trab.getNombre());
            ps.setString(3, trab.getApellidos());
            ps.setString(4, trab.getDomicilio());
            ps.setDate(5, trab.getFechaNac());
            ps.setString(6, trab.getCiudad());
            ps.setString(7, trab.getEstado());
            ps.setString(8, trab.getTelefono());
            ps.setDate(9, trab.getFechaContra());
            ps.setString(10, trab.getCvePuesto());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Devuelve todas los trabajadores
     *
     * @return
     */

    public ObservableList<Trabajador> selectAll() {
        String query = "SELECT * FROM Trabajador";
        return select(query);
    }

    public boolean delete(Object bean) {
        Trabajador trab = (Trabajador) bean;

        String query = "DELETE FROM Trabajador WHERE rfc = '" + trab.getRfc() + "'";
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

    public ObservableList<Trabajador> selectTerapeutas() {
        String query = "select *" +
                " from Trabajador" +
                " where cvePuesto = (select p.cvePuesto" +
                "                   from Puesto p" +
                "                   where p.descripcion like 'terapeuta%')";
        return select(query);
    }

    public ObservableList<Trabajador> selectMostrador() {
        String query = "select *" +
                " from Trabajador" +
                " where cvePuesto = (select p.cvePuesto" +
                "                   from Puesto p" +
                "                   where p.descripcion like 'mostrador%')";
        return select(query);
    }

    public Trabajador selectByRFC(String rfc) {
        String query = "Select * from Trabajador" +
                " where rfc = '" + rfc + "'";

        return select(query).get(0);
    }

    private ObservableList<Trabajador> select(String query) {
        ObservableList<Trabajador> listTrab = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String rfc = rs.getString(1);
                String genero = rs.getString(2);
                String nombre = rs.getString(3);
                String apellidos = rs.getString(4);
                String domicilio = rs.getString(5);
                Date fechaNac = rs.getDate(6);
                String ciudad = rs.getString(7);
                String estado = rs.getString(8);
                String telefono = rs.getString(9);
                Date fechaContra = rs.getDate(10);
                String cvePuesto = rs.getString(11);

                listTrab.add(new Trabajador(rfc, genero, nombre, apellidos, domicilio, fechaNac, ciudad,
                        estado, telefono, fechaContra, cvePuesto));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTrab;
    }

}
