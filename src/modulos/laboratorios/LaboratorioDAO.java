package modulos.laboratorios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BasicDAO;

import java.sql.*;

public class LaboratorioDAO implements BasicDAO {
    Connection connection;
    
    public LaboratorioDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Guarda una nuevo Laboratorio
     * @param bean
     *
     * @return
     */
    public boolean insert(Object bean) {
        Laboratorio lab = (Laboratorio) bean;
        String query = "Insert into Laboratorio(cveLab, nombre,domicilio,ciudad,estado) values (?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, lab.getCveLab());
            ps.setString(2, lab.getNombre());
            ps.setString(3, lab.getDomicilio());
            ps.setString(4, lab.getCiudad());
            ps.setString(5, lab.getEstado());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Actualiza un Trabajador
     * @param bean
     * @return
     */
    public boolean update(Object bean) {

        Laboratorio lab = (Laboratorio) bean;

        String query = "update Laboratorio" +
                " set nombre = ?," +
                "domicilio = ?," +
                "ciudad = ?," +
                "estado = ?" +
                " where cveLab = '" + lab.getCveLab() + "'";
        boolean success = false;
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, lab.getNombre());
            ps.setString(2, lab.getDomicilio());
            ps.setString(3, lab.getCiudad());
            ps.setString(4, lab.getEstado());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    /**
     * Devuelve todas los Laboratorios
     * @return
     */
    public ObservableList<Laboratorio> selectAll() {
        String query = "SELECT * FROM Laboratorio";
        ObservableList<Laboratorio> listLaboratorio = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String cveLab = rs.getString(1);
                String nombre = rs.getString(2);
                String domicilio = rs.getString(3);
                String ciudad = rs.getString(4);
                String estado = rs.getString(5);

                listLaboratorio.add(new Laboratorio(cveLab,nombre,domicilio,ciudad,estado));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listLaboratorio;
    }

    public boolean delete(Object bean){
        Laboratorio lab = (Laboratorio) bean;

        String query = "DELETE FROM Laboratorio WHERE cveLab = '" + lab.getCveLab()+"'";
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
