package ru.nsu.ccfit.veretennikov.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final List<WorkerThread> workers = new ArrayList<>();
    private volatile boolean isRunning = true;

    public ThreadPool(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
            worker.start();
        }
    }

    public void addTask(Task task) {
        if (isRunning) {
            taskQueue.add(task);
        }
    }

    public int getTaskCount() {
        return taskQueue.size();
    }

    public void shutdown() {
        isRunning = false;
        workers.forEach(Thread::interrupt);
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    Task task = taskQueue.take();
                    task.performWork();
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