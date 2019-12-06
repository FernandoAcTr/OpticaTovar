package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Paciente;

import java.sql.*;

public class PacienteDAO implements BasicDAO {
    Connection connection;

    public PacienteDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Object bean) {
        Paciente pac = (Paciente) bean;
        String query = "Insert into Paciente(nombre, apellidos, domicilio, colonia, codPostal, estado, ciudad, telefono, " +
                "genero, email, ocupacion, fechaNac, edad, cveEmpresa) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pac.getNombre());
            ps.setString(2, pac.getApellidos());
            ps.setString(3, pac.getDomicilio());
            ps.setString(4, pac.getColonia());
            ps.setString(5, pac.getCodPost());
            ps.setString(6, pac.getEstado());
            ps.setString(7, pac.getCiudad());
            ps.setString(8, pac.getTelefono());
            ps.setString(9, pac.getGenero());
            ps.setString(10, pac.getEmail());
            ps.setString(11, pac.getOcupacion());
            ps.setDate(12, pac.getFechaNac());
            ps.setInt(13, pac.getEdad());
            ps.setString(14, pac.getCveEmpresa());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean update(Object bean) {
        Paciente pac = (Paciente) bean;

        String query = "update Paciente" +
                " set nombre = ?, " +
                "apellidos = ?, " +
                "domicilio = ?, " +
                "colonia = ?, " +
                "codPostal = ?," +
                "estado = ?, " +
                "ciudad = ?, " +
                "telefono = ?, " +
                "genero = ?, " +
                "email = ?, " +
                "ocupacion = ?, " +
                "fechaNac = ?, " +
                "edad = ?, " +
                "cveEmpresa = ?" +
                " where idCliente = " + pac.getId();
        boolean success = false;

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, pac.getNombre());
            ps.setString(2, pac.getApellidos());
            ps.setString(3, pac.getDomicilio());
            ps.setString(4, pac.getColonia());
            ps.setString(5, pac.getCodPost());
            ps.setString(6, pac.getEstado());
            ps.setString(7, pac.getCiudad());
            ps.setString(8, pac.getTelefono());
            ps.setString(9, pac.getGenero());
            ps.setString(10, pac.getEmail());
            ps.setString(11, pac.getOcupacion());
            ps.setDate(12, pac.getFechaNac());
            ps.setInt(13, pac.getEdad());
            ps.setString(14, pac.getCveEmpresa());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public ObservableList<Paciente> selectAll() {
        String query = "SELECT * FROM Paciente";
        return select(query);
    }

    @Override
    public boolean delete(Object bean) {
        Paciente pac = (Paciente) bean;

        String query = "DELETE FROM Paciente WHERE idCliente = '" + pac.getId() + "'";
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

    /**
     * Regresa el ID maximo + 1
     *
     * @return
     */
    public int getNextID() {
        int nextID = 1;

        String query = "SELECT MAX(idCliente) FROM Paciente";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next())
                nextID = rs.getInt(1) + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextID;
    }

    public ObservableList<Paciente> selectByID(String idPaciente) {
        String query = "Select * FROM Paciente " +
                " where idCliente = " + idPaciente;

        return select(query);
    }

    public ObservableList<Paciente> selectByName(String name) {
        String query = "Select * FROM Paciente " +
                " where nombre like '" + name + "%'";

        return select(query);
    }

    public ObservableList<Paciente> selectByLastName(String lastName) {
        String query = "Select * FROM Paciente " +
                " where apellidos like '" + lastName + "%'";

        return select(query);
    }

    public ObservableList<Paciente> selectByNameANDLastName(String name, String lastName) {
        String query = "Select * FROM Paciente " +
                " where nombre like '" + name + "%' and apellidos like '" + lastName + "%'";

        return select(query);
    }

    private ObservableList<Paciente> select(String query) {
        ObservableList<Paciente> listPaciente = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellidos = rs.getString(3);
                String domicilio = rs.getString(4);
                String colonia = rs.getString(5);
                String cp = rs.getString(6);
                String estado = rs.getString(7);
                String ciudad = rs.getString(8);
                String tel = rs.getString(9);
                String genero = rs.getString(10);
                String email = rs.getString(11);
                String ocupacion = rs.getString(12);
                Date fechaNac = rs.getDate(13);
                int edad = rs.getInt(14);
                String cveEmp = rs.getString(15);

                listPaciente.add(new Paciente(id, nombre, apellidos, domicilio, colonia, cp, estado, ciudad, tel, genero, email,
                        ocupacion, fechaNac, (byte) edad, cveEmp));
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listPaciente;
    }
}
