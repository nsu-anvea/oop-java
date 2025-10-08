package factories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class FactoryGeneratorTest {

    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    @Test
    public void generatingCommandFactoryByCommand() {
        Properties properties = getProperties();
        FactoryGenerator factoryGenerator = new FactoryGenerator();

        for (String command : properties.stringPropertyNames()) {
            assertEquals(properties.getProperty(command),
                    factoryGenerator.generateCommandFactoryByCommand(command, properties).getClass().getName());
        }
    }

    private Properties getProperties() {
        InputStream fis = null;
        Properties properties;
        try {
            fis = new FileInputStream(PATH_TO_PROPERTIES);
            properties = new Properties();
            try {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException | FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return properties;
    }
}