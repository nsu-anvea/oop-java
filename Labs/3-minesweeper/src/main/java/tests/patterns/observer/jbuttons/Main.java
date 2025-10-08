package tests.patterns.observer.jbuttons;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JButton jButton = new JButton();
        jButton.setPreferredSize(new Dimension(25, 100));
        jButton.setBackground(Color.RED);

        JPanel panel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        panel.setLayout(borderLayout);
        panel.add(jButton, BorderLayout.WEST);
        panel.add(jButton);
        panel.add(new JLabel(), BorderLayout.CENTER);


        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setBounds(0, 0, 1000, 500);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.add(panel);
    }
}
