package usuari;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class LectorCredencials extends DefaultHandler {
    private String nom = "";
    private String contrasenya = "";
    private String text = "";

    /**
     * Constructor de la classe LectorCredencials
     */

    public LectorCredencials() {
        super();
    }

    /**
     * Llegeix el fitxer XML de credencials i emmagatzema el nom i contrasenya.
     * @throws ParserConfigurationException Si hi ha un error en la configuració del parser
     * @throws SAXException                Si hi ha un error en la lectura del fitxer XML
     * @throws IOException                 Si hi ha un error d'entrada/sortida
     */

    public void run() throws ParserConfigurationException, SAXException, IOException {
        File fitxer = new File("src/usuari/credencials.xml");
        if (!fitxer.exists()) {
            throw new IOException("El archivo de credenciales no existe");
        }
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(fitxer, this);
    }

    /**
     * Retorna el nom d'usuari.
     * @return Retorna el nom d'usuari
     */

    public String getNom() {
        return nom;
    }

    /**
     * Retorna la contrasenya d'usuari.
     * @return Retorna la contrasenya d'usuari
     */

    public String getContrasenya() {
        return contrasenya;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        text = new String(ch, start, length).trim();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("user")) {
            nom = text;
        } else if (qName.equals("password")) {
            contrasenya = text;
        }
    }
}
