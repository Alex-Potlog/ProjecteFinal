package Main;
import grafic.MyChat;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            MyChat chat = new MyChat();
            chat.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}