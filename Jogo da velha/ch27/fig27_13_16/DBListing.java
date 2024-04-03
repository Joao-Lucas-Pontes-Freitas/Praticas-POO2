import java.sql.*;
import java.util.*;

public class DBListing {
    public Jogo launch(String chave) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        DBConnection dbConnection = new DBConnection(driver);

        Connection conn = null;
        ResultSet jogos;
        PreparedStatement psInsert;

        Jogo jogo = null;


        try {
            conn = dbConnection.getConnection();
            if (conn == null) return null;

            psInsert = conn.prepareStatement("select chave, tabuleiro, jogador from Jogo where chave = ?");
            psInsert.setString(1, chave);
            jogos = psInsert.executeQuery();


            if (jogos.next()) {
                jogo = new Jogo(jogos.getInt(3), jogos.getString(2), jogos.getString(1));
                System.out.println(jogo.tabuleiro + jogo.chave);
            }


            jogos.close();
            conn.close();
            psInsert.close();

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
        return jogo;
    }
}