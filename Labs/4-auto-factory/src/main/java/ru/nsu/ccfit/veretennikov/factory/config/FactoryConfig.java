package ru.nsu.ccfit.veretennikov.factory.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FactoryConfig {
    private final Properties properties = new Properties();

    public FactoryConfig(String configFile) throws IOException {
        properties.load(new FileInputStream(configFile));
    }

    public int getBodyStorageCapacity() {
        return Integer.parseInt(properties.getProperty("BodyStorageCapacity"));
    }

    public int getMotorStorageCapacity() {
        return Integer.parseInt(properties.getProperty("MotorStorageCapacity"));
    }

    public int getAccessoryStorageCapacity() {
        return Integer.parseInt(properties.getProperty("AccessoryStorageCapacity"));
    }

    public int getAutoStorageCapacity() {
        return Integer.parseInt(properties.getProperty("AutoStorageCapacity"));
    }

    public int getAccessorySuppliersCount() {
        return Integer.parseInt(properties.getProperty("AccessorySuppliersCount"));
    }

    public int getWorkersCount() {
        return Integer.parseInt(properties.getProperty("WorkersCount"));
    }

    public int getDealersCount() {
        return Integer.parseInt(properties.getProperty("DealersCount"));
    }

    public boolean isLogSale() {
        return Boolean.parseBoolean(properties.getProperty("IsLogSale"));
    }
}