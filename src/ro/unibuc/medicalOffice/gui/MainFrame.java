package ro.unibuc.medicalOffice.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private DetailsPannel detailsPanel;

    public MainFrame(String title){
        super(title);

        //Set layout manager
        setLayout(new BorderLayout());

        //create swing components
        JTextArea textArea = new JTextArea();
        JButton button = new JButton("click me!");
        detailsPanel = new DetailsPannel();

        //add swing components to content pane
        Container c = getContentPane();


        c.add(textArea, BorderLayout.CENTER);
        c.add(button, BorderLayout.SOUTH);
        c.add(detailsPanel, BorderLayout.WEST);

        //add behaviour

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Hello!\n");
            }
        });
    }
}
