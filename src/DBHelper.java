import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
	public static String username="postgres";
	public static String password="123456";
	public static String dbUrl="jdbc:postgresql://localhost:5432/online bilet satım otomasyonu";
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
