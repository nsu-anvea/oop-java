package minesweeper1;

public interface Observable {
    void addObserver(Observer o);
    void notifyObservers();
}
