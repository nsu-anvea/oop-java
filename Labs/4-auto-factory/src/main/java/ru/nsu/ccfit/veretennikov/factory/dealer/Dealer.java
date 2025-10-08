package ru.nsu.ccfit.veretennikov.factory.dealer;

import ru.nsu.ccfit.veretennikov.factory.details.Auto;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dealer extends Thread {
    private final Storage<Auto> autoStorage;
    private volatile int delay;
    private final int id;
    private final boolean isLogSale;
    private FileWriter logFileWriter;
    private volatile boolean isRunning = true;

    public Dealer(Storage<Auto> autoStorage, int delay,
                  int id, boolean isLogSale, String logFile) throws IOException {
        this.autoStorage = autoStorage;
        this.delay = delay;
        this.id = id;
        this.isLogSale = isLogSale;

        if (isLogSale) {
            this.logFileWriter = new FileWriter(logFile, true);
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void stopRunning() {
        isRunning = false;
        interrupt();

        if (logFileWriter != null) {
            try {
                logFileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(delay);
                Auto auto = autoStorage.get();

                if (isLogSale && logFileWriter != null) {
                    String logEntry = String.format(
                            "<%s>: Dealer <%d>: Auto <%d> (Body: <%d>, Motor: <%d>, Accessory: <%d>)%n",
                            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                            id,
                            auto.getId(),
                            auto.getBody().getId(),
                            auto.getMotor().getId(),
                            auto.getAccessory().getId()
                    );
                    logFileWriter.write(logEntry);
                    logFileWriter.flush();
                }
            }
            catch (InterruptedException | IOException e) {
                if (!isRunning) {
                    return;
                }
            }
        }
    }
}