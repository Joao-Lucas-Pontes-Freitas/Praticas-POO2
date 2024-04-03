import java.sql.*;
public class DBRemove {
	public int launch(String chave) {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		DBConnection dbConnection = new DBConnection(driver);

		Connection conn;

		try {
			conn = dbConnection.getConnection();
			if (conn == null) return 0;

			PreparedStatement statement = conn.prepareStatement("delete from Jogo where chave = " + chave);
			return statement.executeUpdate();

		} catch (Throwable e) {
			System.out.println(" . . . exception thrown:");
			e.printStackTrace(System.out);
		}
		return 0;
	}
}