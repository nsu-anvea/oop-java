package ru.nsu.ccfit.veretennikov.factory.worker;

import ru.nsu.ccfit.veretennikov.factory.details.*;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;
import ru.nsu.ccfit.veretennikov.threadpool.Task;

public class Worker implements Task {
    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    public Worker(Storage<Body> bodyStorage, Storage<Motor> motorStorage,
                  Storage<Accessory> accessoryStorage, Storage<Auto> autoStorage) {
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
        this.autoStorage = autoStorage;
    }

    @Override
    public void performWork() throws InterruptedException {
        Body body = bodyStorage.get();
        Motor motor = motorStorage.get();
        Accessory accessory = accessoryStorage.get();

        Auto auto = new Auto(autoStorage.getSize(), body, motor, accessory);
        autoStorage.put(auto);
    }
}