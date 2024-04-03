import java.sql.*;
import java.util.Properties;

public class DBConnection {
	String driver;
	String dbName = "Jogos";
	Properties props = new Properties();
	String connectionURL = "jdbc:derby:" + dbName + ";create=true";

	public DBConnection(String driver) {
		this.driver = driver;
	}

	public Connection getConnection() {
		Connection conn = null;
		props.put("user", "App");
		props.put("password", "");

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(connectionURL, props);
			System.out.println("Connected to database " + dbName);
		} catch (Throwable e) {
			System.out.println(" . . . exception thrown:");
			e.printStackTrace(System.out);
		}
		return conn;
	}
}
