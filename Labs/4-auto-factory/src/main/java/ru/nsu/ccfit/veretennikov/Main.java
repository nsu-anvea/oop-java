package ru.nsu.ccfit.veretennikov;

import ru.nsu.ccfit.veretennikov.factory.Factory;

import java.io.IOException;

public class Main {
    private static final String CONFIG_FILE = "src/main/java/ru/nsu/ccfit/veretennikov/" +
            "factory/config/config.properties";

    public static void main(String[] args) {
        try {
            Factory factory = new Factory(CONFIG_FILE);
            factory.start();
            Runtime.getRuntime().addShutdownHook(new Thread(factory::stop));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
