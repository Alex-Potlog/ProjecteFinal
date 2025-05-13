package grafic;

import magatzematge.Missatge;
import magatzematge.Usuari;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private JScrollPane panelUsuaris;
    private JPanel subPanelUsuaris;
    private JScrollPane panelXat;
    private JPanel subPanelXat;

    /**
     * Crea el frame.
     */
    public MyChat(String username){
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chat");
        setBounds(100, 100, 1000, 650);
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

        panelUsuaris = new JScrollPane();
        panelUsuaris.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        subPanelUsuaris = new JPanel();
        subPanelUsuaris.setLayout(new BoxLayout(subPanelUsuaris, BoxLayout.Y_AXIS));
        mostraUsuaris(getUsuaris(obtener()));
        panelSuperior.add(panelUsuaris, BorderLayout.EAST);
        panelXat = new JScrollPane();
        panelXat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelSuperior.add(panelXat, BorderLayout.CENTER);
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

        txtInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    botoEnvia.doClick();
                }
            }
        });

        panelInputs.add(txtInput);
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

    public void mostraUsuaris(TreeSet<Usuari> usuaris) {
        subPanelUsuaris.removeAll();

        for (Usuari usuari : usuaris) {
            JTextArea area = new JTextArea(2, 15);
            area.setText(usuari.getNom() + "\n" + usuari.getData());
            area.setEditable(false);
            area.setFocusable(false);
            area.setOpaque(false);
            area.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            area.setMaximumSize(new Dimension(Integer.MAX_VALUE, 37));
            area.setPreferredSize(new Dimension(150, 37)); // ancho y alto fijo
            subPanelUsuaris.add(area);
        }

        subPanelUsuaris.revalidate();
        subPanelUsuaris.repaint();
        panelUsuaris.setViewportView(subPanelUsuaris);
    }

    public void mostraMissatges(ArrayList<Missatge> missatges){
        panelXat.removeAll();
    }

    public void enviaMissatge(Connection con) throws SQLException, ClassNotFoundException{
        String missatge = txtInput.getText();
        envia(missatge, con);
    }


}
