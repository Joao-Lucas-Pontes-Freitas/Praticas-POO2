import java.sql.*;

public class DBPopulation {
    public void launch(Jogo jogo) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        Statement stmt;
        PreparedStatement psInsert;

        try {
            conn = dbConnection.getConnection();
            if (conn == null) return;

            stmt = conn.createStatement();
            psInsert = conn.prepareStatement("INSERT INTO Jogo(chave, tabuleiro, jogador) VALUES(?,?,?)");
            psInsert.setString(1, jogo.chave);
            psInsert.setString(2, jogo.tabuleiro);
            psInsert.setInt(3, jogo.jogador);
            psInsert.executeUpdate();

            psInsert.close();
            stmt.close();
            conn.close();
            System.out.println("Closed connection");

            if (dbConnection.driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
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
            }

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }
}
