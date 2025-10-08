//package ru.nsu.ccfit.veretennikov.minesweeper;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MineSweeperView extends View {
//    private Dimension windowSize = new Dimension(775, 775);
//    private GameField gameField;
//    private JPanel    timePanel;
//    private JLabel    timeLabel;
//
//    public MineSweeperView() {
//        this.setTitle("MineSweeper");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(new GridBagLayout());
//        centerWindow();
//
//        addGameField();
//        addActionListeners();
//
//        addTimePanel();
//
//        SwingUtilities.invokeLater(() -> {
//            // Вопрос где использовать
//        });
//    }
//
//    private void addTimePanel() {
//        this.timePanel = new JPanel();
//        this.timePanel.setMinimumSize(new Dimension(0, 30));
//        this.timePanel.setPreferredSize(new Dimension(0, 30));
//        this.timePanel.setLayout(new BorderLayout());
//
//        this.timeLabel = new JLabel("time");
//        this.timePanel.add(timeLabel, BorderLayout.WEST);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 1.0;
//        gbc.weighty = 0.0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        this.add(timePanel, gbc);
//    }
//
//    private void addActionListeners() {
//        for (int i = 0; i < gameField.getRows(); i++) {
//            for (int j = 0; j < gameField.getCols(); j++) {
//                JButton gameTile = gameField.getTile(i, j);
//                gameTile.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        System.out.println(gameTile.getText());
//                    }
//                });
//            }
//        }
//    }
//
//    private void addGameField() {
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        gbc.fill = GridBagConstraints.BOTH;
//
//        this.gameField = new GameField();
//        this.add(gameField, gbc);
//    }
//
//    private void centerWindow() {
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();
//
//        this.setBounds(
//                screenSize.width / 2 - windowSize.width / 2,
//                screenSize.height / 2 - windowSize.height / 2,
//                windowSize.width,
//                windowSize.height
//        );
//    }
//}
