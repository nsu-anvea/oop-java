package ru.nsu.ccfit.veretennikov.factory.controller;

import ru.nsu.ccfit.veretennikov.factory.details.*;
import ru.nsu.ccfit.veretennikov.factory.worker.Worker;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;
import ru.nsu.ccfit.veretennikov.threadpool.ThreadPool;

public class StorageController extends Thread {
    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    private final ThreadPool threadPool;

    private final int minAutoCount;
    private volatile boolean isRunning = true;

    public StorageController(Storage<Body> bodyStorage,
                             Storage<Motor> motorStorage,
                             Storage<Accessory> accessoryStorage,
                             Storage<Auto> autoStorage,
                             ThreadPool threadPool,
                             int minAutoCount) {
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
        this.autoStorage = autoStorage;
        this.threadPool = threadPool;
        this.minAutoCount = minAutoCount;
    }

    public void stopRunning() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (autoStorage) {
                try {
                    if (threadPool.getTaskCount() < minAutoCount) {
                        threadPool.addTask(new Worker(bodyStorage, motorStorage, accessoryStorage, autoStorage));
                    }
                    autoStorage.wait();
                }
                catch (InterruptedException e) {
                    if (!isRunning) {
                        return;
                    }
                }
            }
        }
    }
}