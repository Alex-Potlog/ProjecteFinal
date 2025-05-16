package magatzematge;

/**
 * Magatzema la informació d'un missatge.
 * @author Alex Potlog
 */

public class Missatge {
    private String message;
    private String nick;
    private String data;

    public Missatge(String message, String nick, String data) {
        this.message = message;
        this.nick = nick;
        this.data = data;
    }

    @Override
    public String toString() {
        return nick + '\n' + message + '\n' + data ;
    }

    public String getMessage() {
        return message;
    }

    public String getNick() {
        return nick;
    }

    public String getData() {
        return data;
    }
}
