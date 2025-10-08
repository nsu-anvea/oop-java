package ru.nsu.ccfit.veretennikov.minesweeper;

import ru.nsu.ccfit.veretennikov.minesweeper.records.RecordsManager;

import java.util.Random;

public class Model extends Observable {
    private final int rows, cols, mines;
    private boolean isGameEnded;
    private final Cell[][] grid;
    private RecordsManager recordsManager;

    public Model(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.isGameEnded = false;
        this.grid = new Cell[rows][cols];
        this.recordsManager = new RecordsManager();
        init();
    }

    private void init() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
        placeMines();
        calculateNeighborMinesNumbers();
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

    private void calculateNeighborMinesNumbers() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isMine) {
                    grid[r][c].neighborMines = countMines(r, c);
                }
            }
        }
    }

    private int countMines(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr;
                int nc = c + dc;
                if (isInBounds(nr, nc) && grid[nr][nc].isMine) {
                    count++;
                }
            }
        }
        return count;
    }

    public void reveal(int r, int c) {
        if (!isInBounds(r, c) || grid[r][c].isRevealed || grid[r][c].isFlagged) {
            return;
        }
        grid[r][c].isRevealed = true;
        if (grid[r][c].isMine) {
            isGameEnded = true;
        }
        if (grid[r][c].neighborMines == 0 && !grid[r][c].isMine) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        reveal(r + dr, c + dc);
                    }
                }
            }
        }
        notifyObservers();
    }

    public void revealAll() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isFlagged) {
                    toggleFlag(r, c);
                }
                reveal(r, c);
            }
        }
    }

    public boolean isGameOver() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isMine && !grid[r][c].isRevealed) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }

    public void setIsGameEnded(boolean flag) {
        isGameEnded = flag;
    }

    public void toggleFlag(int r, int c) {
        if (!isInBounds(r, c) || grid[r][c].isRevealed) {
            return;
        }
        grid[r][c].isFlagged = !grid[r][c].isFlagged;
        notifyObservers();
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }

    public RecordsManager getRecordsManager() {
        return recordsManager;
    }

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
}
