package main;

import grafic.MyLogin;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            MyLogin login = new MyLogin();
            login.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}