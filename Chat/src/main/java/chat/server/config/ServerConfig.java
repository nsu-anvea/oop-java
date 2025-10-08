package chat.server.config;

import java.io.*;
import java.util.Properties;

public class ServerConfig {
    private final Properties properties = new Properties();

    public ServerConfig(String config) throws IOException {
        properties.load(new FileInputStream(config));
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

    public boolean isLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("isLoggingEnabled"));
    }

    public String getLogFile() {
        return properties.getProperty("logFile");
    }
}