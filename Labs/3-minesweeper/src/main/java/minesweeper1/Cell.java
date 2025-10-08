package minesweeper1;

public class Cell {
    public boolean isMine;
    public boolean isRevealed;
    public boolean isFlagged;
    public int neighborMines;

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.neighborMines = 0;
    }
}
