import commands.CommandContext;
import factories.CommandFactory;
import factories.FactoryGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Calculator {
    private static final Logger logger = Logger.getLogger(Calculator.class.getName());

    public void executeCommands(Properties properties, BufferedReader reader) {
        CommandContext context = new CommandContext();
        FactoryGenerator factoryGenerator = new FactoryGenerator();
        try {
            String commandLine;
            while ((commandLine = reader.readLine()) != null) {
                if (!commandLine.startsWith("#")) {
                    String[] commandAndArguments = commandLine.split(" ");

                    if (commandAndArguments[0].equals("STOP")) {
                        logger.info("Сompletion of the program.");
                        break;
                    }

                    if (!properties.containsKey(commandAndArguments[0])) {
                        logger.warning("Unknown command: " + commandAndArguments[0]);
                        throw new RuntimeException("Unknown command: " + commandAndArguments[0]);
                    }
                    CommandFactory commandFactory = factoryGenerator.
                            generateCommandFactoryByCommand(commandAndArguments[0], properties);
                    List<String> arguments = new ArrayList<>(Arrays.asList(commandAndArguments).subList(1, commandAndArguments.length));

                    commandFactory.createCommand().execute(arguments, context);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
