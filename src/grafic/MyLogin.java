package grafic;

import excepcions.ChatException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


import static sql.ConexioBD.obtener;
import static sql.SQLManager.*;

public class MyLogin extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private String username = "";

    /**
     * Crea la finestra
     */
    public MyLogin() {
        setTitle("Login");
        setBounds(100, 100, 250, 125);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        creacioInput();
        creacioBotons();
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
                MyChat chat = new MyChat(username);
                chat.setVisible(true);
            }
        });
        buttonPane.add(cancelButton);
    }

    /**
     * Comprava si l'input del username és possible.
     */

    public void comprovaInput(){
        username = textField.getText();
        boolean valid = true;
        try {
            surt(obtener());
            entra(username, obtener());
        } catch (SQLException | ClassNotFoundException | ChatException ex) {
            JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (valid) {
            setVisible(false);
            MyChat chat = null;

            chat = new MyChat(username);
            chat.setVisible(true);
        }
    }
}