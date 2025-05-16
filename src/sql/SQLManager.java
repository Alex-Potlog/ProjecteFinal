package sql;

import excepcions.ChatException;
import magatzematge.Missatge;
import magatzematge.Usuari;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Classe que maneja les sentencies SQL i la connexió a la base de dades.
 * @author Alex Potlog
 */

public abstract class SQLManager {

    /**
     * Fa el login dels usuaris a la base de dades.
     * @param username Nom del usuari que entra
     * @param con La conexió a la base de dades
     * @throws SQLException Si alguna sentència és incorrecta
     * @throws ChatException Si el nom d'usuari es troba buit
     */

    public static void entra (String username, Connection con) throws SQLException, ChatException {
        if (username.isEmpty()) throw new ChatException("El nom es troba buit", "1001");
        String conectaUsuari = "CALL connect('" + username + "');";
        Statement registrar = con.createStatement();
        registrar.execute(conectaUsuari);
    }

    /**
     * Remou l'usuari de la base de dades.
     * @param con La conexió a la base de dades
     * @throws SQLException Si alguna sentència és incorrecta
     */

    public static void surt (Connection con) throws SQLException {
        String desconectaUsuari = "CALL disconnect();";
        Statement registrar = con.createStatement();
        registrar.execute(desconectaUsuari);
    }

    /**
     * Envia un missatge pel xat.
     * @param missatge Missatge a enviar
     * @param con Conexió a la base de dades
     * @throws SQLException Si alguna sentència és incorrecta
     */

    public static void envia (String missatge, Connection con) throws SQLException {
        String enviaMissatgePrompt = "CALL send('" + missatge + "');";
        Statement registrar = con.createStatement();
        registrar.execute(enviaMissatgePrompt);
    }

    /**
     * Retorna la llista d'usuaris.
     * @param con Conexió a la base de dades
     * @return Retorna un hashset d'usuaris
     * @throws SQLException Si alguna sentència és incorrecta
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
     * @param con Rep la connexió a la base de dades
     * @return Llista de missatges
     * @throws SQLException Si es fa una query malament
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
