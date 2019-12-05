package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {

    private static Connection connection = null;
    private static final String database = "optica";
    private static final String user = "root";
    private static final String password = "root";
    private static final String hostname = "localhost";
    private static final String url = "jdbc:mysql://"+ hostname +":3306/" + database;

    public static Connection getConnection() {
        if(connection == null) Connect();
        return connection;
    }

    public static void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conection initialized...");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    public static void Disconnect() {
        try {
            System.out.println("Conexion finalizada...");
            if(connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isClosed(){
        try {
            return connection.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }
}

