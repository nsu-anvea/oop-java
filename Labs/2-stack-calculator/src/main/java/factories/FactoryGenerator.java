package factories;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class FactoryGenerator {
    public CommandFactory generateCommandFactoryByCommand(String command, Properties properties) {
        CommandFactory commandFactory;

        try {
//            var commandFactory1 = Class.forName(properties.getProperty(command)).newInstance();
//            if (commandFactory1 instanceof CommandFactory) {
//
//            }
            commandFactory = (CommandFactory) Class.forName(properties.getProperty(command))
                    .getDeclaredConstructor().newInstance();
        } catch (RuntimeException | InvocationTargetException | ClassNotFoundException |
                 InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

        return commandFactory;
    }
}
