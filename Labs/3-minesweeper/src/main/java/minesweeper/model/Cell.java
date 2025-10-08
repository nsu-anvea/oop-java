package minesweeper.model;

public class Cell {
    public boolean isMine = false;
    public boolean isRevealed = false;
    public boolean isFlagged = false;
    public int neighborMines = 0;
}
