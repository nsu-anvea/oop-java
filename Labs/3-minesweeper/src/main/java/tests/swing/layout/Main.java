package tests.swing.layout;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = getFrame();
//        jFrame.add(new JButton("one"), BorderLayout.NORTH);
//        jFrame.add(new JButton("two"), BorderLayout.SOUTH);
//        jFrame.add(new JButton("three"), BorderLayout.WEST);
//        jFrame.add(new JButton("four"), BorderLayout.EAST);
//        jFrame.add(new JButton("five"), BorderLayout.CENTER);

        GridLayout gridLayout = new GridLayout();
        jFrame.setLayout(gridLayout);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                jFrame.add(new JButton(Integer.toString(i) + Integer.toString(j)));
            }
        }
        jFrame.add(new JButton("OOP"));
    }

    static JFrame getFrame() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setBounds(750, 250, 500, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return jFrame;
    }
}
