package grafic;

import excepcions.ChatException;
import magatzematge.Missatge;
import magatzematge.Usuari;
import sql.ConexioBD;

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

/**
 * Classe que crea la finestra del xat i la maneja
 * @author Alex Potlog
 */

public class MyChat extends JFrame implements Mostra{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtInput;
    private final String INPUTTEXT = "Entra el teu missatge...";
    private boolean llistaUsuarisVisibles = true;
    private JScrollPane panelUsuaris;
    private JPanel subPanelUsuaris;
    private JScrollPane panelXat;
    private JPanel subPanelXat;
    private final Connection CONEXIO;

    /**
     * Crea el frame.
     */
    public MyChat(Connection CONEXIO){
        this.CONEXIO = CONEXIO;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    surt(CONEXIO);
                    new ConexioBD(CONEXIO).tancar();
                } catch (Exception _) { }
                System.exit(0);
            }
        });
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
        panelUsuaris.setViewportView(subPanelUsuaris);
        mostraUsuaris(getUsuaris(CONEXIO));
        panelSuperior.add(panelUsuaris, BorderLayout.EAST);

        panelXat = new JScrollPane();
        panelXat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        subPanelXat = new JPanel();
        subPanelXat.setLayout(new BoxLayout(subPanelXat, BoxLayout.Y_AXIS));
        panelXat.setViewportView(subPanelXat);
        mostraMissatges(getMessage(CONEXIO));
        panelSuperior.add(panelXat, BorderLayout.CENTER);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostraMissatges(getMessage(CONEXIO));
                    mostraUsuaris(getUsuaris(CONEXIO));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();

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
                    enviaMissatge(CONEXIO);
                    txtInput.setText(INPUTTEXT);
                } catch (SQLException | ClassNotFoundException | ChatException ex) {
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
                    mostraUsuaris(getUsuaris(CONEXIO));
                } catch (SQLException ex) {
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
                    surt(CONEXIO);
                } catch (SQLException ex) {
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

    @Override
    public void mostraUsuaris(TreeSet<Usuari> usuaris) {
        subPanelUsuaris.removeAll();

        for (Usuari usuari : usuaris) {
            JTextArea area = new JTextArea(2, 15);
            area.setText(usuari.getNom() + "\n" + usuari.getData());
            area.setEditable(false);
            area.setFocusable(false);
            area.setOpaque(false);
            area.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            area.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            area.setPreferredSize(new Dimension(150, 36));
            subPanelUsuaris.add(area);

            JLabel separador = new JLabel();
            separador.setBorder(new EmptyBorder(3,3,3,3));
            separador.setMinimumSize(new Dimension(6, 8));
            subPanelUsuaris.add(separador);
        }

        subPanelUsuaris.revalidate();
        subPanelUsuaris.repaint();
        panelUsuaris.setViewportView(subPanelUsuaris);
    }

    /**
     * Mostra els missatges al panell de missatges
     * @param missatges llista de missatges a mostrar
     */

    public void mostraMissatges(ArrayList<Missatge> missatges) {

        for (Missatge missatge : missatges) {
            JTextArea area = new JTextArea(3, 30);
            area.setText(missatge.getNick() + ":\n" + missatge.getMessage() + "\n" + missatge.getData());
            area.setEditable(false);
            area.setFocusable(false);
            area.setOpaque(false);
            area.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
            area.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            area.setPreferredSize(new Dimension(300, 45));
            subPanelXat.add(area);
            JLabel separador = new JLabel();
            separador.setBorder(new EmptyBorder(3, 3, 3, 3));
            separador.setMinimumSize(new Dimension(6, 8));
            subPanelXat.add(separador);
        }

        panelXat.setViewportView(subPanelXat);
    }
    /**
     * Envia el missatge passat per parametre al xat
     * @param con Coneció a la base de dades
     * @throws SQLException Llençat desde SQLManager
     * @throws ClassNotFoundException Llençat desde SQLManager
     * @throws ChatException Si el missatge es troba buit
     */

    public void enviaMissatge(Connection con) throws SQLException, ClassNotFoundException, ChatException{
        String missatge = txtInput.getText();
        if (comprovaInput(missatge)) envia(missatge, con);
        else throw new ChatException("El missatge no pot estar buit", "1002");
    }

    /**
     * Comprova si el missatge es troba buit o no
     * @param missatge Missatge a comprovar
     * @return Si el missatge es troba buit o no
     */

    @Override
    public boolean comprovaInput(String missatge) {
        if (missatge == null || missatge.isEmpty()) return false;
        return true;
    }

}
