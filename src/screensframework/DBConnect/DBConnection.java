package screensframework.DBConnect;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    
    private static Connection conn;
    private static Properties props = new Properties();

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Error: No se pudo encontrar el archivo config.properties en el classpath");
            } else {
                props.load(input);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar config.properties: " + e.getMessage());
        }
    }

    /**
     * Retorna la conexión existente o crea una nueva si no existe o está cerrada.
     * Implementa una lógica similar a Singleton para la instancia de Connection.
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (conn == null || conn.isClosed()) {
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.pass");
                
                if (url == null || user == null || pass == null) {
                    throw new SQLException("Faltan propiedades de configuración en config.properties");
                }
                
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Conexión a la base de datos establecida correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
        return conn;
    }

    /**
     * Método para cerrar la conexión explícitamente si es necesario.
     */
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
