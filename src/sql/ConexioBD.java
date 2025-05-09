package sql;
import javax.swing.*;
import java.sql.*;

public class ConexioBD {
    private static Connection conn = null;

    /**
     * Crea una connexió a la base de dades.
     * @return la connexió a la base de dades
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static Connection obtener() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thos_xat", "appuser", "2025@Thos");
            } catch (SQLException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return conn;
    }

    public static void cerrar() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static Connection getConn() {
        return conn;
    }
}
