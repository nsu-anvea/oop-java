package ru.nsu.ccfit.veretennikov.factory.details;

public class Body extends Detail {
    private static int nextBodyId = 0;

    @Override
    protected int getNextId() {
        return nextBodyId++;
    }

    @Override
    public String toString() {
        return "Body: " + getId();
    }
}