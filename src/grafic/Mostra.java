package grafic;

import magatzematge.Usuari;

import java.util.TreeSet;

public interface Mostra {
    void mostraUsuaris(TreeSet<Usuari> usuaris);

    boolean comprovaInput(String missatge);
}
