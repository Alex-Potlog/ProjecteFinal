package sql;

import excepcions.ChatException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.TreeSet;

public abstract class SQLManager {

    /**
     * Fa el login dels usuaris a la base de dades.
     * @param username nom del usuari que entra
     * @param con la conexió a la base de dades
     * @throws SQLException si alguna sentència és incorrecta
     * @throws ChatException si el nom d'usuari es troba buit
     */

    public static void entra (String username, Connection con) throws SQLException, ChatException {
        if (username.isEmpty()) throw new ChatException("El nom es troba buit");
        String conectaUsuari = "CALL connect('" + username + "');";
        Statement registrar = con.createStatement();
        registrar.execute(conectaUsuari);
    }

    /**
     * Remou l'usuari de la base de dades.
     * @param con la conexió a la base de dades
     * @throws SQLException si alguna sentència és incorrecta
     */

    public static void surt (Connection con) throws SQLException {
        String desconectaUsuari = "CALL disconnect();";
        Statement registrar = con.createStatement();
        registrar.execute(desconectaUsuari);
    }

    /**
     * Envia un missatge pel xat.
     * @param missatge missatge a enviar
     * @param con conexió a la base de dades
     * @throws SQLException si alguna sentència és incorrecta
     */

    public static void envia (String missatge, Connection con) throws SQLException {
        String enviaMissatgePrompt = "CALL send('" + missatge + "');";
        Statement registrar = con.createStatement();
        registrar.execute(enviaMissatgePrompt);
    }

    /**
     * Retorna la llista d'usuaris.
     *
     * @param con conexió a la base de dades
     * @return retorna un hashset d'usuaris
     * @throws SQLException si alguna sentència és incorrecta
     */

    public static TreeSet<String> getUsuaris(Connection con) throws SQLException {
        TreeSet<String> llistaUsuaris = new TreeSet<>();
        String missatgePrompt = "CALL getConnectedUsers();";
        Statement registrar = con.createStatement();
        ResultSet resultat = registrar.getResultSet();

        if (resultat !=null){
            while (resultat.next()){
                llistaUsuaris.add(resultat.getString("nick"));
            }
            resultat.close();
        }

        registrar.close();
        return llistaUsuaris;
    }
}
