package minesweeper.model;

import java.util.*;

public class Board {
    private final int rows, cols, mines;
    private final Cell[][] grid;
    private final List<BoardObserver> observers = new ArrayList<>();

    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.grid = new Cell[rows][cols];
        init();
    }

    private void init() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Cell();

        placeMines();
        calculateNumbers();
        notifyObservers();
    }

    private void placeMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < mines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!grid[r][c].isMine) {
                grid[r][c].isMine = true;
                placed++;
            }
        }
    }

    private void calculateNumbers() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!grid[r][c].isMine)
                    grid[r][c].neighborMines = countMines(r, c);
    }

    private int countMines(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++)
                if (inBounds(r + dr, c + dc) && grid[r + dr][c + dc].isMine)
                    count++;
        return count;
    }

    public void reveal(int r, int c) {
        if (!inBounds(r, c) || grid[r][c].isRevealed || grid[r][c].isFlagged) return;

        grid[r][c].isRevealed = true;
        if (grid[r][c].neighborMines == 0 && !grid[r][c].isMine) {
            for (int dr = -1; dr <= 1; dr++)
                for (int dc = -1; dc <= 1; dc++)
                    if (dr != 0 || dc != 0)
                        reveal(r + dr, c + dc);
        }

        notifyObservers();
    }

    public void toggleFlag(int r, int c) {
        if (!inBounds(r, c) || grid[r][c].isRevealed) return;
        grid[r][c].isFlagged = !grid[r][c].isFlagged;
        notifyObservers();
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public void addObserver(BoardObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers() {
        for (BoardObserver o : observers)
            o.boardUpdated();
    }
}
