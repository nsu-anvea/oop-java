package minesweeper.view;

import minesweeper.model.*;
import minesweeper.controller.BoardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardView extends JFrame implements BoardObserver {
    private final Board board;
    private final BoardController controller;
    private final JButton[][] buttons;

    public BoardView(Board board, BoardController controller) {
        this.board = board;
        this.controller = controller;
        this.buttons = new JButton[board.getRows()][board.getCols()];
        board.addObserver(this);
        initUI();
    }

    private void initUI() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(board.getRows(), board.getCols()));

        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                JButton btn = new JButton();
                int row = r, col = c;
                btn.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e))
                            controller.onLeftClick(row, col);
                        else if (SwingUtilities.isRightMouseButton(e))
                            controller.onRightClick(row, col);
                    }
                });
                buttons[r][c] = btn;
                add(btn);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void boardUpdated() {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                Cell cell = board.getCell(r, c);
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
}
