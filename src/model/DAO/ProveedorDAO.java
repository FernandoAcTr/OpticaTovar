package model.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Proveedor;

import java.sql.*;

public class ProveedorDAO implements BasicDAO {
    Connection connection;

    public ProveedorDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Guarda una nueva Marca
     *
     * @param bean
     * @return
     */
    public boolean insert(Object bean) {
        Proveedor prov = (Proveedor) bean;

        String query = "Insert into Proveedor(codProveedor, nombre,telefono, email, observaciones, " +
                "pagWed, fax, estado, cp, pais, colonia, rfc, ciudad, domicilio, descuento," +
                "credito, diasCred) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, prov.getCodProveedor());
            ps.setString(2, prov.getNombre());
            ps.setString(3, prov.getTelefono());
            ps.setString(4, prov.getEmail());
            ps.setString(5, prov.getObservaciones());
            ps.setString(6, prov.getPagWeb());
            ps.setString(7, prov.getFax());
            ps.setString(8, prov.getEstado());
            ps.setString(9, prov.getCp());
            ps.setString(10, prov.getPais());
            ps.setString(11, prov.getColonia());
            ps.setString(12, prov.getRfc());
            ps.setString(13, prov.getCiudad());
            ps.setString(14, prov.getDomicilio());
            ps.setDouble(15, prov.getDescuento());
            ps.setDouble(16, prov.getCredito());
            ps.setInt(17, prov.getDiasCred());
            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Actualiza una Proveedor
     * @param bean
     * @return
     */
    public boolean update(Object bean) {
        Proveedor prov = (Proveedor) bean;

        String query = "update Proveedor " +
                "set nombre = ?," +
                "telefono = ?," +
                "email = ? ," +
                "observaciones = ?," +
                "pagWed = ? ," +
                "fax = ?," +
                "estado = ? ," +
                "cp = ?," +
                "pais = ?, " +
                "colonia = ?," +
                "rfc = ? ," +
                "ciudad = ?," +
                "domicilio = ? ," +
                "descuento = ?," +
                "credito = ? ," +
                "diasCred = ?" +
                " where codProveedor = '" + prov.getCodProveedor() + "'";
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, prov.getNombre());
            ps.setString(2, prov.getTelefono());
            ps.setString(3, prov.getEmail());
            ps.setString(4, prov.getObservaciones());
            ps.setString(5, prov.getPagWeb());
            ps.setString(6, prov.getFax());
            ps.setString(7, prov.getEstado());
            ps.setString(8, prov.getCp());
            ps.setString(9, prov.getPais());
            ps.setString(10, prov.getColonia());
            ps.setString(11, prov.getRfc());
            ps.setString(12, prov.getCiudad());
            ps.setString(13, prov.getDomicilio());
            ps.setDouble(14, prov.getDescuento());
            ps.setDouble(15, prov.getCredito());
            ps.setInt(16, prov.getDiasCred());

            success = !ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Devuelve todas los Proveedores
     * @return
     */

    public ObservableList<Proveedor> selectAll() {
        String query = "SELECT * FROM Proveedor";
        ObservableList<Proveedor> listProveedores = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String codPorveedor = rs.getString(1);
                String nombre = rs.getString(2);
                String telefono = rs.getString(3);
                String email = rs.getString(4);
                String observaciones = rs.getString(5);
                String pagwed = rs.getString(6);
                String fax = rs.getString(7);
                String estado = rs.getString(8);
                String cp = rs.getString(9);
                String pais = rs.getString(10);
                String colonia = rs.getString(11);
                String rfc = rs.getString(12);
                String ciudad = rs.getString(13);
                String domicilio = rs.getString(14);
                double descuento = rs.getDouble(15);
                double credito = rs.getDouble(16);
                int diasCred = rs.getInt(17);
                listProveedores.add(new Proveedor(codPorveedor, nombre, telefono, email, observaciones,
                        pagwed, fax, estado, cp, pais, colonia, rfc, ciudad, domicilio, descuento, credito, diasCred));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listProveedores;
    }


    public boolean delete(Object bean) {
        Proveedor prov = (Proveedor) bean;

        String query = "DELETE FROM Proveedor WHERE codProveedor = '" + prov.getCodProveedor() + "'";
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
