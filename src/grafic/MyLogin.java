package grafic;

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
    private String username;

    /**
     * Create the dialog.
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

    public void creacioInput(){
        JLabel lblInstruccions = new JLabel("Insereix el teu username");
        contentPanel.add(lblInstruccions, BorderLayout.NORTH);

        textField = new JTextField();
        contentPanel.add(textField, BorderLayout.CENTER);
        textField.setColumns(10);
    }

    public void creacioBotons(){
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = textField.getText();
                boolean valid = true;
                try {
                    surt(obtener());
                    entraUsername();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }
                setVisible(false);
                MyChat chat = new MyChat();
                chat.setVisible(true);
            }
        });
        okButton.setActionCommand("OK");

        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    public void entraUsername() {
        boolean verificat = true;
        try {
            entra(username, obtener());
        } catch (SQLException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}