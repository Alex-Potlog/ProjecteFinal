package grafic;

import excepcions.ChatException;
import magatzematge.Usuari;

import java.sql.SQLException;
import java.util.TreeSet;

public interface Mostra {

    /**
     * Mostra els usuaris
     * @param usuaris colecció on es troba
     */
    void mostraUsuaris(TreeSet<Usuari> usuaris);

    /**
     * Comprova si el escrit per input és correcte
     * @param missatge
     * @return
     */

    boolean comprovaInput(String missatge) throws SQLException, ChatException;
}
