package magatzematge;

public class Usuari implements Comparable {
    private String nom;
    private String data;

    public Usuari(String data, String nom) {
        this.data = data;
        this.nom = nom;
    }

    @Override
    public String toString() {
        if (data != null) return  nom + '\n' + data;
        return nom;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Usuari) return data.compareTo(o.toString());
        return 0;
    }

    public String getNom() {
        return nom;
    }

    public String getData() {
        return data;
    }


}
