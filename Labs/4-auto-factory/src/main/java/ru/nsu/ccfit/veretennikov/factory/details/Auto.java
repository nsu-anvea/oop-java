package ru.nsu.ccfit.veretennikov.factory.details;

public class Auto extends Detail {
    private static int nextAutoId = 0;

    private final Body body;
    private final Motor motor;
    private final Accessory accessory;

    public Auto(int id, Body body, Motor motor, Accessory accessory) {
        this.body = body;
        this.motor = motor;
        this.accessory = accessory;
    }

    public Body getBody() {
        return body;
    }

    public Motor getMotor() {
        return motor;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    @Override
    protected int getNextId() {
        return nextAutoId++;
    }

    @Override
    public String toString() {
        return "Auto: " + getId() + " (" + body + ", " + motor + ", " + accessory + ")";
    }
}