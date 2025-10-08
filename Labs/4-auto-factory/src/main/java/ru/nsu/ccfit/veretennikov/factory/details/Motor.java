package ru.nsu.ccfit.veretennikov.factory.details;

public class Motor extends Detail {
    private static int nextMotorId = 0;

    @Override
    protected int getNextId() {
        return nextMotorId++;
    }

    @Override
    public String toString() {
        return "Motor: " + getId();
    }
}