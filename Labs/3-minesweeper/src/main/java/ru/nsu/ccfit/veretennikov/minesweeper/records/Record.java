package ru.nsu.ccfit.veretennikov.minesweeper.records;

import java.time.LocalDateTime;

public class Record implements Comparable<Record> {
    public final int rows, cols, mines, timeInSeconds;
    public final LocalDateTime dateTime;

    public Record(int rows, int cols, int mines, int timeInSeconds) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.timeInSeconds = timeInSeconds;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public int compareTo(Record other) {
        return Integer.compare(this.timeInSeconds, other.timeInSeconds);
    }

    @Override
    public String toString() {
        return String.format("Поле: %dx%d, Мины: %d, Время: %02d:%02d",
                rows, cols, mines,
                timeInSeconds / 60, timeInSeconds % 60);
    }
}
