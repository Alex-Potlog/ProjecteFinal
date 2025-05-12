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
        return  nom + '\n' + data;
    }

    @Override
    public int compareTo(Object o) {return data.compareTo((String) o);}

    public String getNom() {
        return nom;
    }

    public String getData() {
        return data;
    }


}
