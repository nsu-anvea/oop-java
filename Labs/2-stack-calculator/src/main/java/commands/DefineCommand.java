package commands;

import java.util.List;
import java.util.logging.Logger;

public class DefineCommand implements Command {
    private static final Logger logger = Logger.getLogger(DefineCommand.class.getName());
    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (arguments.size() != 2) {
            logger.warning("DEFINE command requires 2 argument.");
            throw new IllegalArgumentException("DEFINE command requires 2 argument.");
        }
        context.definedValues.put(arguments.get(0), Double.parseDouble(arguments.get(1)));
        logger.info("DEFINED " + arguments.get(0) + " = " + arguments.get(1));
    }
}