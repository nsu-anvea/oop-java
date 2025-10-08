package minesweeper;

import minesweeper.model.Board;
import minesweeper.controller.BoardController;
import minesweeper.view.BoardView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Board board = new Board(10, 10, 10);
            BoardController controller = new BoardController(board);
            new BoardView(board, controller);
        });
    }
}
