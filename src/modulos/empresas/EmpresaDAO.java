package modulos.empresas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;

import java.sql.*;

public class EmpresaDAO implements BasicDAO {

    Connection connection;
    
    public EmpresaDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Guarda una nueva Empresa
     * @param bean
     * @return
     */
    public boolean insert(Object bean) {
        Empresa emp = (Empresa) bean;

        String query = "Insert into Empresa(cveEmpresa, nombre, status, descuento," +
                " email, observaciones,rfc,telefono) values (?,?,?,?,?,?,?,?)";
        boolean success = false;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, emp.getCveEmpresa());
            ps.setString(2, emp.getNombre());
            ps.setString(3, emp.getStatus());
            ps.setDouble(4, emp.getDescuento());
            ps.setString(5, emp.getEmail());
            ps.setString(6, emp.getObservaciones());
            ps.setString(7, emp.getRfc());
            ps.setString(8, emp.getTelefono());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Actualiza una Empresa
     * @param bean
     * @return
     */
    public boolean update(Object bean) {
        Empresa emp = (Empresa) bean;

        String query = "update Empresa " +
                "set nombre = ?," +
                "status = ?," +
                "descuento = ?," +
                "email = ?," +
                "observaciones = ?," +
                "rfc = ?," +
                "telefono = ?" +
                " where cveEmpresa = '" + emp.getCveEmpresa() + "'";
        boolean success = false;

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getStatus());
            ps.setDouble(3, emp.getDescuento());
            ps.setString(4, emp.getEmail());
            ps.setString(5, emp.getObservaciones());
            ps.setString(6, emp.getRfc());
            ps.setString(7, emp.getTelefono());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Devuelve todas las Empresas
     * @return
     */
    public ObservableList<Empresa> selectAll() {
        String query = "SELECT * FROM Empresa";
        return select(query);
    }

    public Empresa selectByID(String cveEmpresa){
        String query = "SELECT * FROM Empresa " +
                " WHERE cveEmpresa = '" + cveEmpresa + "'";
        return select(query).get(0);
    }


    public boolean delete(Object bean){

        Empresa empresa = (Empresa) bean;

        String query = "DELETE FROM Empresa WHERE cveEmpresa = '" + empresa.getCveEmpresa()+"'";
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

    public ObservableList<Empresa> select(String query) {
        ObservableList<Empresa> lisEmpresas = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cveEmpresa = rs.getString(1);
                String nombre = rs.getString(2);
                String status = rs.getString(3);
                double descuento = rs.getDouble(4);
                String email = rs.getString(5);
                String observaciones = rs.getString(6);
                String rfc = rs.getString(7);
                String telefono = rs.getString(8);

                lisEmpresas.add(new Empresa(cveEmpresa, nombre,status, descuento,
                        email, observaciones, rfc, telefono));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lisEmpresas;
    }


}
