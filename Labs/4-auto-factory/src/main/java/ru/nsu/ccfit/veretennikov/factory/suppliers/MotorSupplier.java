package ru.nsu.ccfit.veretennikov.factory.suppliers;

import ru.nsu.ccfit.veretennikov.factory.details.Motor;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;

public class MotorSupplier extends Supplier<Motor> {
    public MotorSupplier(Storage<Motor> storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Motor createDetail() {
        return new Motor();
    }
}