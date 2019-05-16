package ro.unibuc.medicalOffice.gui;

import javax.swing.*;

public class GUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable () {
            public void run() {
                JFrame frame = new MainFrame("Medical Office");
                frame.setSize(500, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }
}
