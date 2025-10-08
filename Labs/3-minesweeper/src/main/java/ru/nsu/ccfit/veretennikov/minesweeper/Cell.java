package ru.nsu.ccfit.veretennikov.minesweeper;

public class Cell {
    public boolean isMine;
    public boolean isRevealed;
    public boolean isFlagged;
    public int neighborMines;

    public Cell() {
        isMine = false;
        isRevealed = false;
        isFlagged = false;
        neighborMines = 0;
    }
}
