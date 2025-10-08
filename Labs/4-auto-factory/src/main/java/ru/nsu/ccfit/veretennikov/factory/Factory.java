package ru.nsu.ccfit.veretennikov.factory;

import ru.nsu.ccfit.veretennikov.factory.config.*;
import ru.nsu.ccfit.veretennikov.factory.controller.*;
import ru.nsu.ccfit.veretennikov.factory.dealer.*;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;
import ru.nsu.ccfit.veretennikov.factory.suppliers.*;
import ru.nsu.ccfit.veretennikov.factory.ui.gui.FactoryGUI;
import ru.nsu.ccfit.veretennikov.factory.details.*;
import ru.nsu.ccfit.veretennikov.threadpool.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    private final List<BodySupplier> bodySuppliers;
    private final List<MotorSupplier> motorSuppliers;
    private final List<AccessorySupplier> accessorySuppliers;
    private final List<Dealer> dealers;

    private final ThreadPool threadPool;

    private final StorageController storageController;

    private static final String LOG_FILE = "src/main/java/ru/nsu/ccfit/veretennikov/" +
            "factory/logs/factory.log";

    public Factory(String configFile) throws IOException {
        FactoryConfig config = new FactoryConfig(configFile);

        bodyStorage = new Storage<>(config.getBodyStorageCapacity());
        motorStorage = new Storage<>(config.getMotorStorageCapacity());
        accessoryStorage = new Storage<>(config.getAccessoryStorageCapacity());
        autoStorage = new Storage<>(config.getAutoStorageCapacity());

        bodySuppliers = new ArrayList<>();
        motorSuppliers = new ArrayList<>();
        accessorySuppliers = new ArrayList<>();

        bodySuppliers.add(new BodySupplier(bodyStorage, 1000));
        motorSuppliers.add(new MotorSupplier(motorStorage, 1000));

        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            accessorySuppliers.add(new AccessorySupplier(accessoryStorage, 1000));
        }

        dealers = new ArrayList<>();
        for (int i = 0; i < config.getDealersCount(); i++) {
            dealers.add(new Dealer(autoStorage, 1000, i, config.isLogSale(), LOG_FILE));
        }

        threadPool = new ThreadPool(config.getWorkersCount());

        storageController = new StorageController(
                bodyStorage,
                motorStorage,
                accessoryStorage,
                autoStorage,
                threadPool,
                config.getAutoStorageCapacity() / 2
        );
    }

    public void start() {
        bodySuppliers.forEach(Thread::start);
        motorSuppliers.forEach(Thread::start);
        accessorySuppliers.forEach(Thread::start);
        dealers.forEach(Thread::start);
        storageController.start();

        SwingUtilities.invokeLater(() -> {
            FactoryGUI factoryGUI = new FactoryGUI(this);
            factoryGUI.setVisible(true);
        });
    }

    public void stop() {
        bodySuppliers.forEach(BodySupplier::stopRunning);
        motorSuppliers.forEach(MotorSupplier::stopRunning);
        accessorySuppliers.forEach(AccessorySupplier::stopRunning);
        dealers.forEach(Dealer::stopRunning);
        storageController.stopRunning();

        threadPool.shutdown();
    }

    public Storage<Body> getBodyStorage() { return bodyStorage; }
    public Storage<Motor> getMotorStorage() { return motorStorage; }
    public Storage<Accessory> getAccessoryStorage() { return accessoryStorage; }
    public Storage<Auto> getAutoStorage() { return autoStorage; }

    public List<BodySupplier> getBodySuppliers() { return bodySuppliers; }
    public List<MotorSupplier> getMotorSuppliers() { return motorSuppliers; }
    public List<AccessorySupplier> getAccessorySuppliers() { return accessorySuppliers; }
    public List<Dealer> getDealers() { return dealers; }

    public ThreadPool getThreadPool() { return threadPool; }
}