package ru.nsu.ccfit.veretennikov.factory.suppliers;

import ru.nsu.ccfit.veretennikov.factory.details.Body;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;

public class BodySupplier extends Supplier<Body> {
    public BodySupplier(Storage<Body> storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Body createDetail() {
        return new Body();
    }
}