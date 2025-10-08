package ru.nsu.ccfit.veretennikov.factory.suppliers;

import ru.nsu.ccfit.veretennikov.factory.details.Detail;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;

public abstract class Supplier<T extends Detail> extends Thread {
    protected final Storage<T> storage;
    private volatile int delay;
    private volatile boolean isRunning = true;

    public Supplier(Storage<T> storage, int delay) {
        this.storage = storage;
        this.delay = delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void stopRunning() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(delay);
                storage.put(createDetail());
            }
            catch (InterruptedException e) {
                if (!isRunning) {
                    return;
                }
            }
        }
    }

    protected abstract T createDetail();
}