package ru.nsu.ccfit.veretennikov.minesweeper;

import ru.nsu.ccfit.veretennikov.minesweeper.gui.View;

public class Main {
    public static void main(String[] args) {
        Model model = new Model(9, 9, 10);
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.addObserver(view);
    }
}