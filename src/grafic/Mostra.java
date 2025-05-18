package grafic;

import excepcions.ChatException;
import magatzematge.Usuari;

import java.sql.SQLException;
import java.util.TreeSet;

/**
 * Interface que mostra els usuaris i comprova l'input del chat i del login
 * @author Alex Potlog
 */

public interface Mostra {

    /**
     * Mostra els usuaris
     * @param usuaris Colecció on es troben els usuaris
     */
    void mostraUsuaris(TreeSet<Usuari> usuaris);

    /**
     * Comprova l'input realitzat
     * @param missatge Missatge a comprovar
     * @return Si és correcte o no
     * @throws SQLException Llençat des de SQLManager
     * @throws ChatException Si els inputs es troben buits
     */

    boolean comprovaInput(String missatge) throws SQLException, ChatException;
}
