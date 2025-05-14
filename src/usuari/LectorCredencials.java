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
    private boolean enNom = false;
    private boolean enContrasenya = false;

    public LectorCredencials() {
        super();
    }

    public void run() throws ParserConfigurationException, SAXException, IOException {
        File fitxer = new File("src/usuari/credencials.xml");
        if (!fitxer.exists()) {
            throw new IOException("El archivo de credenciales no existe");
        }
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(fitxer, this);
    }

    public String getNom() {
        return nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("user")) {
            enNom = true;
        } else if (qName.equals("password")) {
            enContrasenya = true;
        }
        text = "";
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
