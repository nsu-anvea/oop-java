package ru.nsu.ccfit.veretennikov.minesweeper;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}