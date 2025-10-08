package minesweeper1;

import java.util.*;

public class Model implements Observable {
    private final int rows, cols, mines;
    private final Cell[][] grid;
    private final List<Observer> observers = new ArrayList<>();

    public Model(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                grid[i][j] = new Cell();
        placeMines();
        calculateNeighbors();
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

    private void calculateNeighbors() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isMine) {
                    grid[r][c].neighborMines = countMinesAround(r, c);
                }
            }
        }
    }

    private int countMinesAround(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr;
                int nc = c + dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc].isMine) {
                    count++;
                }
            }
        }
        return count;
    }

    public void reveal(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c].isRevealed) {
            return;
        }
        grid[r][c].isRevealed = true;
        if (grid[r][c].neighborMines == 0 && !grid[r][c].isMine) {
            for (int dr = -1; dr <= 1; dr++)
                for (int dc = -1; dc <= 1; dc++)
                    if (!(dr == 0 && dc == 0))
                        reveal(r + dr, c + dc);
        }
        notifyObservers();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!grid[r][c].isMine && !grid[r][c].isRevealed)
                    return false;
        return true;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers)
            o.update();
    }
}
