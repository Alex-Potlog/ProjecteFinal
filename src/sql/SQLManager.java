package sql;

import excepcions.ChatException;
import magatzematge.Missatge;
import magatzematge.Usuari;

import java.sql.*;
import java.util.ArrayList;
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
     * @param con conexió a la base de dades
     * @return retorna un hashset d'usuaris
     * @throws SQLException si alguna sentència és incorrecta
     */

    public static TreeSet<Usuari> getUsuaris(Connection con) throws SQLException {
        TreeSet<Usuari> llistaUsuaris = new TreeSet<>();
        String missatgePrompt = "CALL getConnectedUsers();";
        Statement registrar = con.createStatement();
        registrar.execute(missatgePrompt);
        ResultSet resultat = registrar.getResultSet();

        if (resultat != null) {
            while (resultat.next()) {
                llistaUsuaris.add(new Usuari(resultat.getString("date_con"), resultat.getString("nick")));
            }
            resultat.close();
        }
        registrar.close();
        return llistaUsuaris;
    }


    /**
     * Retorna els missatges enviats per xat.
     * @param con rep la connexió a la base de dades
     * @return llista de missatges
     * @throws SQLException si es fa una query malament
     */

    public static ArrayList<Missatge> getMessage(Connection con) throws SQLException {
        ArrayList<Missatge> missatges = new ArrayList<>(); //he escollit aquesta col·lecció perquè poden repetir-se els missatges
        // i no compleixen cap mena d'estructura adicional
        String missatgePrompt = "CALL getMessages();";
        Statement registrar = con.createStatement();
        registrar.execute(missatgePrompt);
        ResultSet resultat = registrar.getResultSet();

        if (resultat != null){
            while (resultat.next()){
                missatges.add(new Missatge(resultat.getString("message"), resultat.getString("nick"), resultat.getString("ts")));
            }
        }

        return missatges;
    }
}
