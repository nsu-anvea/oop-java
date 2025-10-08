package ru.nsu.ccfit.veretennikov.threadpool;

public interface Task {
    void performWork() throws InterruptedException;
}