package learning.forNameAndNewInstance;

import commands.Command;
import factories.CommandFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ForNameAndNewInstance {
    public static void main(String[] args) {
        Properties properties = new Properties();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/main/java/factories/config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String key : properties.stringPropertyNames()) {
            CommandFactory commandFactory = null;
            try {
                commandFactory = (CommandFactory) Class.forName(properties.getProperty(key))
                        .getDeclaredConstructor().newInstance();
                Command command = commandFactory.createCommand();
                System.out.print(command.getClass().getName() + " -> ");
            }
            catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}
