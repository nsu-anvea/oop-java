package ru.nsu.ccfit.veretennikov.factory.details;

public class Accessory extends Detail {
    private static int nextAccessoryId = 0;

    @Override
    protected int getNextId() {
        return nextAccessoryId++;
    }

    @Override
    public String toString() {
        return "Accessory: " + getId();
    }
}