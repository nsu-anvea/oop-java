package ru.nsu.ccfit.veretennikov.factory.suppliers;

import ru.nsu.ccfit.veretennikov.factory.details.Accessory;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;

public class AccessorySupplier extends Supplier<Accessory> {
    public AccessorySupplier(Storage<Accessory> storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Accessory createDetail() {
        return new Accessory();
    }
}