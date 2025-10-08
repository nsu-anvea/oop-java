package minesweeper.controller;

import minesweeper.model.Board;

public class  BoardController {
    private final Board board;

    public BoardController(Board board) {
        this.board = board;
    }

    public void onLeftClick(int row, int col) {
        board.reveal(row, col);
    }

    public void onRightClick(int row, int col) {
        board.toggleFlag(row, col);
    }
}
