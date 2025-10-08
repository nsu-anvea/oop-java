package ru.nsu.ccfit.veretennikov.minesweeper.gui;

import ru.nsu.ccfit.veretennikov.minesweeper.Cell;
import ru.nsu.ccfit.veretennikov.minesweeper.Controller;
import ru.nsu.ccfit.veretennikov.minesweeper.Model;
import ru.nsu.ccfit.veretennikov.minesweeper.Observer;
import ru.nsu.ccfit.veretennikov.minesweeper.records.RecordsManager;
import ru.nsu.ccfit.veretennikov.minesweeper.records.Record;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

public class View extends JFrame implements Observer {
    private final Controller controller;
    private JButton[][] buttons;

    private JPanel gridPanel;

    private JLabel timerLabel;
    private Timer timer;
    private int secondsElapsed = 0;

    public View(Controller controller) {
        this.controller = controller;
        this.buttons = new JButton[controller.getRows()][controller.getCols()];
        controller.addObserver(this);

        init();
        startTimer();
    }

    private void init() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/main/java/ru/nsu/ccfit/veretennikov/minesweeper/resources/icon.png");
        if (icon.getImage() == null) {
            System.err.println("Ошибка: иконка не загружена!");
            return;
        }

        setIconImage(icon.getImage());

        if (Taskbar.isTaskbarSupported()) {
            Taskbar taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                try {
                    taskbar.setIconImage(icon.getImage());
                } catch (UnsupportedOperationException e) {
                    System.err.println("Не поддерживается на этой платформе");
                }
            }
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 40));

        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        timerLabel = new JLabel("Время: 00:00");
        leftWrapper.add(timerLabel);

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JButton recordsButton = new JButton();
        recordsButton.setPreferredSize(new Dimension(32, 32));
        recordsButton.setContentAreaFilled(false); // прозрачный фон
        recordsButton.setBorderPainted(false);     // без рамки
        recordsButton.setFocusPainted(false);      // без рамки фокуса
        recordsButton.setToolTipText("Показать рекорды");

        ImageIcon recordsIcon = new ImageIcon("src/main/java/ru/nsu/ccfit/veretennikov/minesweeper/resources/records.png");
        Image scaledImage = recordsIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        recordsButton.setIcon(new ImageIcon(scaledImage));
        recordsButton.setPreferredSize(new Dimension(32, 32));
        recordsButton.setFocusable(false);
        recordsButton.addActionListener(e -> showRecordsDialog());

        rightWrapper.add(recordsButton);

        topPanel.add(leftWrapper, BorderLayout.WEST);
        topPanel.add(rightWrapper, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(controller.getRows(), controller.getCols()));
        buttons = new JButton[controller.getRows()][controller.getCols()];

        for (int r = 0; r < controller.getRows(); r++) {
            for (int c = 0; c < controller.getCols(); c++) {
                JButton button = getJButton(r, c);
                buttons[r][c] = button;
                gridPanel.add(button);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton getJButton(int r, int c) {
        JButton button = new JButton();
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(80, 60));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!controller.checkIsGameEnded()) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        controller.revealCell(r, c);
                        if (controller.getCell(r, c).isMine && !controller.getCell(r, c).isFlagged) {
                            controller.endGame();
                            controller.revealAll();
                            render();
                            showEndDialog("Вы проиграли!");
                        }
                        else if (controller.checkIsGameOver()) {
                            controller.endGame();
                            controller.revealAll();
                            render();
                            showEndDialog("Вы победили!");
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        controller.toggleFlag(r, c);
                    }
                }
            }
        });
        return button;
    }

    private void render() {
        for (int r = 0; r < controller.getRows(); r++) {
            for (int c = 0; c < controller.getCols(); c++) {
                Cell cell = controller.getCell(r, c);
                JButton btn = buttons[r][c];

                if (cell.isRevealed) {
                    btn.setEnabled(false);
                    if (cell.isMine) {
                        btn.setText("💣");
                        btn.setBackground(Color.RED);
                    } else if (cell.neighborMines > 0) {
                        btn.setText(Integer.toString(cell.neighborMines));
                        btn.setBackground(Color.LIGHT_GRAY);
                    } else {
                        btn.setText("");
                        btn.setBackground(Color.LIGHT_GRAY);
                    }
                } else if (cell.isFlagged) {
                    btn.setText("🚩");
                } else {
                    btn.setText("");
                    btn.setEnabled(true);
                    btn.setBackground(null);
                }
            }
        }
    }

    private void showEndDialog(String message) {
        stopTimer();
        int choice = JOptionPane.showOptionDialog(
                this,
                message + "\nХотите сыграть снова?",
                "Игра окончена",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Сыграть снова", "Выход"},
                "Сыграть снова"
        );

        if (message.contains("победили")) {
            controller.getRecordsManager().addRecord(new Record(
                    controller.getRows(),
                    controller.getCols(),
                    controller.getMines(),
                    secondsElapsed
            ));
        }

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            dispose();
        }

    }

    private void showRecordsDialog() {
        RecordsManager manager = controller.getRecordsManager();
        List<Record> top = manager.getTop(10, controller.getRows(), controller.getCols(), controller.getMines());

        String[] columnNames = {"#", "Время"};
        String[][] data = new String[top.size()][2];

        for (int i = 0; i < top.size(); i++) {
            Record r = top.get(i);
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = String.format("%02d:%02d", r.timeInSeconds / 60, r.timeInSeconds % 60);
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 250));

        JDialog dialog = new JDialog(this, "Рекорды", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void restartGame() {
        stopTimer();

        getContentPane().removeAll();
        controller.recreateModel(this);
//        this.model = new Model(controller.getRows(), controller.getCols(), controller.getMines());
//        this.model.addObserver(this);
//        this.controller.setModel(model);

        this.secondsElapsed = 0;

        init();
        startTimer();

        revalidate();
        repaint();
    }


    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                secondsElapsed++;
                SwingUtilities.invokeLater(() -> {
                    int mins = secondsElapsed / 60;
                    int secs = secondsElapsed % 60;
                    timerLabel.setText(String.format("Время: %02d:%02d", mins, secs));
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(this::render);
    }
}
