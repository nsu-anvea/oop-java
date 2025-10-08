package minesweeper1;

import javax.swing.*;
import java.awt.*;

public class SwingView extends JFrame implements Observer {
    private final Model board;
    private final JButton[][] buttons;
    private boolean gameEnded = false;

    public SwingView(Model board) {
        this.board = board;
        int rows = board.getGrid().length;
        int cols = board.getGrid()[0].length;
        this.buttons = new JButton[rows][cols];

        setTitle("Minesweeper (Swing)");
        setLayout(new GridLayout(rows, cols));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JButton btn = getjButton(board, r, c);
                buttons[r][c] = btn;
                add(btn);
            }
        }

        setVisible(true);
    }

    private JButton getjButton(Model board, int r, int c) {
        JButton btn = new JButton();
        btn.addActionListener(e -> {
            if (!gameEnded) {
                board.reveal(r, c);
                if (board.getGrid()[r][c].isMine) {
                    showMessage("Вы проиграли! Вы нажали на мину.");
                    gameEnded = true;
                    revealAll();
                } else if (board.isGameOver()) {
                    showMessage("Поздравляем! Вы победили.");
                    gameEnded = true;
                    revealAll();
                }
            }
        });
        return btn;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(this::render);
    }

    public void render() {
        Cell[][] grid = board.getGrid();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                Cell cell = grid[r][c];
                JButton btn = buttons[r][c];

                if (cell.isRevealed) {
                    btn.setEnabled(false);
                    if (cell.isMine) {
                        btn.setText("*");
                        btn.setBackground(Color.RED);
                    } else {
                        btn.setText(cell.neighborMines == 0 ? "" : String.valueOf(cell.neighborMines));
                    }
                }
            }
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void revealAll() {
        Cell[][] grid = board.getGrid();
        for (Cell[] cells : grid) {
            for (int c = 0; c < grid[0].length; c++) {
                cells[c].isRevealed = true;
            }
        }
        render();
    }
}
