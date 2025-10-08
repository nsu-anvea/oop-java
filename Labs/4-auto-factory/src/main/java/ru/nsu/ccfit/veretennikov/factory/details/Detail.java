package ru.nsu.ccfit.veretennikov.factory.details;

public abstract class Detail {
    private final int id;

    protected Detail() {
        this.id = getNextId();
    }

    protected abstract int getNextId();

    public int getId() {
        return id;
    }
}