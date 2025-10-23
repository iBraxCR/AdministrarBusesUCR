
package mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    


public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/buses";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa a la base de datos 'buses'");
        } catch (SQLException e) {
            System.out.println(" Error al conectar: " + e.getMessage());
        }
        return conn;
    }
}

}
