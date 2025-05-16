package sql;
import org.xml.sax.SAXException;
import usuari.LectorCredencials;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class ConexioBD {
    private Connection conn = null;

    /**
     * Constructor per si ja tens una connexió especifica
     * @param conn la conexió especifica
     */

    public ConexioBD(Connection conn){ this.conn = conn; }

    /**
     * Constructor default per certs casos
     */

    public ConexioBD() { }

    /**
     * Crea una connexió a la base de dades o l'agafa.
     * @return Retorna la connexió a la base de dades
     */

    public Connection obtener() {
        if (conn == null) {
            try {
                LectorCredencials lector = new LectorCredencials();
                lector.run();
                String user = lector.getNom();
                String password = lector.getContrasenya();

                if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
                    throw new SQLException("Credenciales inválidas");
                }

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thos_xat", user, password);
            } catch (SQLException | ClassNotFoundException | ParserConfigurationException | IOException | SAXException ex) {
                conn = null;
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return conn;
    }

    /**
     * Tanca la connexió.
     * @throws SQLException Llençat des de Connexion.close
     */

    public void tancar() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Retorna la conexió.
     * @return Retorna la conexió.
     */

    public Connection getConn() {
        return conn;
    }
}
