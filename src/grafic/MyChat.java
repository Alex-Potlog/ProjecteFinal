package grafic;

import magatzematge.Usuari;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.TreeSet;

import static sql.ConexioBD.*;
import static sql.SQLManager.*;

public class MyChat extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtInput;
    private String username;
    private final String INPUTTEXT = "Entra el teu missatge...";
    private boolean llistaUsuarisVisibles = true;
    private JPanel panelUsuaris;

    /**
     * Crea el frame.
     */
    public MyChat(String username){
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chat");
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));
        initialize();
    }

    /**
     * Inicialitza els mètodes de creació de tot l'apartat gràfic
     */

    public void initialize()  {
        try {
            creacioPanells();
            creacioMenu();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea els panells.
     * @throws SQLException Llençat des de SQLManager
     * @throws ClassNotFoundException Llençat des de SQLManager
     */

    public void creacioPanells() throws SQLException, ClassNotFoundException {
        JPanel panelSuperior = new JPanel();
        contentPane.add(panelSuperior, BorderLayout.CENTER);
        panelSuperior.setLayout(new BorderLayout(0, 0));

        panelUsuaris = new JPanel();
        JLabel prova = new JLabel("Exemple");
        panelUsuaris.add(prova);
        mostraUsuaris(getUsuaris(obtener()));
        panelSuperior.add(panelUsuaris, BorderLayout.EAST);
        panelUsuaris.setLayout(new BoxLayout(panelUsuaris, BoxLayout.Y_AXIS));

        JPanel panelInputs = new JPanel();
        contentPane.add(panelInputs, BorderLayout.SOUTH);
        panelInputs.setLayout(new BorderLayout(0, 0));

        txtInput = new JTextField("Entra el teu missatge...");
        txtInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtInput.setText("");
            }
        });

        panelInputs.add(txtInput);

        JButton botoEnvia = new JButton("Envia");
        botoEnvia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviaMissatge(obtener());
                    txtInput.setText(INPUTTEXT);
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelInputs.add(botoEnvia, BorderLayout.EAST);
    }

    /**
     * Crea tot l'apartat de menus amb els seus submenus.
     */

    public void creacioMenu(){
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);

        JMenuItem usuarisVisibles = new JMenuItem("Activa/desactiva vista d'usuaris");
        usuarisVisibles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llistaUsuarisVisibles = !llistaUsuarisVisibles;
                panelUsuaris.setVisible(llistaUsuarisVisibles);
                try {
                    mostraUsuaris(getUsuaris(obtener()));
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        usuarisVisibles.setMnemonic('A');
        mnMenu.add(usuarisVisibles);

        JMenuItem sortir = new JMenuItem("Sortir");
        sortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    surt(obtener());
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
            }
        });
        sortir.setMnemonic('Q');

        mnMenu.add(sortir);
    }

    /**
     * Mostra els usuaris al panell d'usuaris loguejats
     */

    public void mostraUsuaris(TreeSet<Usuari> usuaris){
        for (Usuari text : usuaris) {
            JLabel label = new JLabel(text.toString());
            label.setForeground(Color.BLUE);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelUsuaris.add(label); // Agregar label al panel
        }
    }

    public void enviaMissatge(Connection con) throws SQLException, ClassNotFoundException{
        String missatge = txtInput.getText();
        envia(missatge, con);
    }
}
