import java.sql.*;

public class DBConfiguration {
    public DBConfiguration() {
        launch();
    }

    public void launch() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn;
        Statement stmt;


        String dropJogoString = "DROP TABLE Jogo";

        String createJogoString = "CREATE TABLE Jogo  "
                + "(chave VARCHAR(100) NOT NULL, "
                + "tabuleiro CHAR(9) NOT NULL, "
                + "jogador int NOT NULL, "
                + "CONSTRAINT pk_chave PRIMARY KEY (chave))";


        conn = dbConnection.getConnection();
        if (conn == null) return;
        try {
            stmt = conn.createStatement();

            // Drop tables if they exist
            if (tableExists(conn, "Jogo"))
                stmt.execute(dropJogoString);
            System.out.println(" . . . . dropped table Jogo if exists");

            // Create tables
            stmt.execute(createJogoString);
            System.out.println(" . . . . created table Jogo");

            stmt.close(); // Close statement only
            // Do not close connection here
            conn.close();
            System.out.println("Closed statement"); // Print statement closed message


            boolean gotSQLExc = false;
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                if (se.getSQLState().equals("XJ015")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database shut down normally");
            }

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
        return result.next();
    }
}
