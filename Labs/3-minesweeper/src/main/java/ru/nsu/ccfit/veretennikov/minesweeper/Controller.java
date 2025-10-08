package ru.nsu.ccfit.veretennikov.minesweeper;

import ru.nsu.ccfit.veretennikov.minesweeper.gui.View;
import ru.nsu.ccfit.veretennikov.minesweeper.records.RecordsManager;

public class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void revealCell(int row, int col) {
        model.reveal(row, col);
    }

    public void revealAll() {
        model.revealAll();
    }

    public void endGame() {
        model.setIsGameEnded(true);
    }

    public void toggleFlag(int row, int col) {
        model.toggleFlag(row, col);
    }

    public void recreateModel(View view) {
        model = new Model(model.getRows(), model.getCols(), model.getMines());
        model.addObserver(view);
    }

    public void addObserver(View view) {
        model.addObserver(view);
    }

    public int getRows() {
        return model.getRows();
    }

    public int getCols() {
        return model.getCols();
    }

    public int getMines() {
        return model.getMines();
    }

    public Cell getCell(int r, int c) {
        return model.getCell(r, c);
    }

    public RecordsManager getRecordsManager() {
        return model.getRecordsManager();
    }

    public boolean checkIsGameEnded() {
        return model.isGameEnded();
    }

    public boolean checkIsGameOver() {
        return model.isGameOver();
    }
}
