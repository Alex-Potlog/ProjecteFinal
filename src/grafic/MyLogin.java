package grafic;

import excepcions.ChatException;
import magatzematge.Usuari;
import sql.ConexioBD;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeSet;


import static sql.ConexioBD.*;
import static sql.SQLManager.*;

public class MyLogin extends JDialog implements Mostra{

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel;
    private JTextField textField;
    private String username = "";
    private Connection conn;
    private JPanel panelUsuaris;

    /**
     * Crea la finestra
     */

    public MyLogin() {
        setTitle("Login");
        setBounds(100, 100, 250, 150);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    surt(conn);
                    new ConexioBD(conn).cerrar();
                } catch (Exception _) { }
                System.exit(0);
            }
        });
        inicialitza();
    }

    /**
     * Inicialitza la finestra del login.
     */

    public void inicialitza(){
        conn = new ConexioBD().obtener();
        creacioInput();
        creacioBotons();
        creacioMenu();
    }

    /**
     * Crea l'apartat del label i text del login.
     */

    public void creacioInput(){
        JLabel lblInstruccions = new JLabel("Insereix el teu username");
        contentPanel.add(lblInstruccions, BorderLayout.NORTH);

        textField = new JTextField();
        contentPanel.add(textField, BorderLayout.CENTER);
        textField.setColumns(10);
    }

    /**
     * Crea i maneja l'apartat dels botons del login.
     */

    public void creacioBotons(){
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comprovaInput();
            }
        });

        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (username.isEmpty()) System.exit(0);
                setVisible(false);
                MyChat chat = null;
                chat = new MyChat(conn);
                chat.setVisible(true);
            }
        });
        buttonPane.add(cancelButton);
    }

    public void creacioMenu(){
        JMenuBar barraMenu = new JMenuBar();
        this.setJMenuBar(barraMenu);
        JMenu menu = new JMenu("Menu");
        barraMenu.add(menu);
        JMenuItem menuItem = new JMenuItem("Mostra els usuaris connectats");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogUsuaris = new JDialog();
                dialogUsuaris.setTitle("Usuaris connectats");
                dialogUsuaris.setBounds(100, 100, 250, 125);
                dialogUsuaris.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialogUsuaris.setVisible(true);
                JScrollPane scrollPane = new JScrollPane();
                dialogUsuaris.getContentPane().add(scrollPane, BorderLayout.CENTER);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                panelUsuaris = new JPanel();
                panelUsuaris.setLayout(new BoxLayout(panelUsuaris, BoxLayout.Y_AXIS));
                try {
                    mostraUsuaris(getUsuaris(conn));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                dialogUsuaris.getContentPane().add(panelUsuaris);
            }
        });
        menu.add(menuItem);
        JMenuItem menuItem2 = new JMenuItem("Sortir");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn.close();
                    System.exit(0);
                } catch (SQLException _) { }
            }
        });
        menu.add(menuItem2);
    }

    /**
     * Comprava si l'input del username és possible.
     */

    public void comprovaInput(){
        username = textField.getText();
        boolean valid = true;
        try {
            surt(conn);
            entra(username, conn);
        } catch (SQLException | ChatException ex) {
            JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (valid) {
            setVisible(false);
            MyChat chat = null;

            chat = new MyChat(conn);
            chat.setVisible(true);
        }
    }

    @Override
    public void mostraUsuaris(TreeSet<Usuari> usuaris){
        panelUsuaris.removeAll();

        for (Usuari usuari : usuaris) {
            JTextArea area = new JTextArea(2, 15);
            area.setText(usuari.getNom() + "\n" + usuari.getData());
            area.setEditable(false);
            area.setFocusable(false);
            area.setOpaque(false);
            area.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            area.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            area.setPreferredSize(new Dimension(150, 36));
            panelUsuaris.add(area);

            JLabel separador = new JLabel();
            separador.setBorder(new EmptyBorder(3,3,3,3));
            separador.setMinimumSize(new Dimension(6, 8));
            panelUsuaris.add(separador);
        }

        panelUsuaris.revalidate();
        panelUsuaris.repaint();
        if (usuaris.isEmpty()) {
            JLabel label = new JLabel("No hi ha usuaris connectats");
            label.setBorder(new EmptyBorder(3,3,3,3));
            panelUsuaris.add(label);
        }
    }
}