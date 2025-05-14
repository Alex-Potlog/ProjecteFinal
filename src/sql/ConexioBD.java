package sql;
import usuari.LectorCredencials;

import javax.swing.*;
import java.sql.*;

public class ConexioBD {
    private Connection conn = null;

    /**
     * Crea una connexió a la base de dades.
     * @return Retorna la connexió a la base de dades
     * @throws SQLException Si alguna sentència és incorrecta
     * @throws ClassNotFoundException Si no es troba el driver
     */

    public Connection obtener() throws SQLException, ClassNotFoundException {
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
            } catch (Exception e) {
                conn = null; // Importante: resetear la conexión si falla
                throw new SQLException("Error de conexión: " + e.getMessage());
            }
        }
        return conn;
    }

    /**
     * Tanca la connexió.
     * @throws SQLException Llençat des de Connexion.close
     */

    public void cerrar() throws SQLException {
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
