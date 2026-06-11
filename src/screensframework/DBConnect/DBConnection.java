package screensframework.DBConnect;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    
    private static Connection conn;
    private static Properties props = new Properties();

    /*
    private static String url = "jdbc:mysql://localhost/sysventas";
    private static String user = "root";
    private static String pass = "";*/

    static {
        try(InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")){
            if(input == null){
                System.out.println("No se pudo encontrar el archivo config.properties");
            }else{
                props.load(input);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException{

	try {
            Class.forName("org.postgresql.Driver").newInstance();
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch(ClassNotFoundException cnfe) {
            System.err.println("Error: "+cnfe.getMessage());
	} catch(InstantiationException ie) {
            System.err.println("Error: "+ie.getMessage());
	} catch(IllegalAccessException iae) {
            System.err.println("Error: "+iae.getMessage());
	}
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.pass");

        conn = DriverManager.getConnection(url,user,pass);
        return conn;
	}
	
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
            connect();
            return conn;
    }
}
