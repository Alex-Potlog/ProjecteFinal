package grafic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public MyChat()  {
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

    public void initialize(){
        creacioMenu();
        creacioPanells();
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
        mnMenu.add(usuarisVisibles);
    }

    /**
     * Crea els elements visuals de les dues parts de la pantalla
     */

    public void creacioPanells(){
        JPanel panelSuperior = new JPanel();
        contentPane.add(panelSuperior, BorderLayout.CENTER);

        JPanel panelInputs = new JPanel();
        contentPane.add(panelInputs, BorderLayout.SOUTH);
        panelInputs.setLayout(new BorderLayout(0, 0));

        txtInput = new JTextField("Entra el teu missatge...");
        //afagir que quan li fan focus treu el text, quan li treuen els focus torna a ficar el text
        panelInputs.add(txtInput);

        JButton botoEnvia = new JButton("Envia");
        panelInputs.add(botoEnvia, BorderLayout.EAST);
    }

}
