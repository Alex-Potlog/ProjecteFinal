package sql;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {
    public static void entra (String username, Connection con) throws SQLException {
        String conectaUsuari = "CALL connect('" + username + "');";
        Statement registrar = con.createStatement();
        registrar.execute(conectaUsuari);
    }

    public static void surt (String username, Connection con) throws SQLException {
        String desconectaUsuari = "CALL disconnect('" + username + "');";
        Statement registrar = con.createStatement();
        registrar.execute(desconectaUsuari);
    }
}
