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
     * Comprova l'input realitzat
     * @param missatge Missatge a comprovar
     * @return si és correcte o no
     * @throws SQLException llençat desde SQLManager
     * @throws ChatException si els inputs es troben buits
     */

    boolean comprovaInput(String missatge) throws SQLException, ChatException;
}
