package ru.nsu.ccfit.veretennikov.factory.storage;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T> {
    private final Queue<T> storage = new LinkedList<>();
    private final int capacity;
    private int producedCount = 0;
    private int soldCount = 0;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (storage.size() >= capacity) {
            wait(); // wait освобождает монитор this,
                    // после чего другой поток может зайти в метод put
                    // и захватить монитор this.
        }
        storage.add(item);
        producedCount++;
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (storage.isEmpty()) {
            wait();
        }
        T item = storage.poll();
        soldCount++;
        notifyAll();
        return item;
    }

    public synchronized int getSize() {
        return storage.size();
    }

    public synchronized int getCapacity() {
        return capacity;
    }

    public synchronized int getProducedCount() {
        return producedCount;
    }

    public synchronized int getSoldCount() {
        return soldCount;
    }
}