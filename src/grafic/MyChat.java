package grafic;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyChat extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtInput;

    /**
     * Create the frame.
     */
    public MyChat() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel();
        contentPane.add(panelSuperior, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);

        JPanel panelInputs = new JPanel();
        contentPane.add(panelInputs, BorderLayout.SOUTH);
        panelInputs.setLayout(new BorderLayout(0, 0));

        txtInput = new JTextField();
        txtInput.setText("Entra el teu missatge...");

        panelInputs.add(txtInput);

        JButton botoEnvia = new JButton("Envia");
        panelInputs.add(botoEnvia, BorderLayout.EAST);
    }

    /**
     * Crea la secció de login
     */
    public void login(){
        // TODO Auto-generated method block
    }

}
