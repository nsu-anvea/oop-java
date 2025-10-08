package commands;

import java.util.List;
import java.util.logging.Logger;

public class PushCommand implements Command {
    private static final Logger logger = Logger.getLogger(PushCommand.class.getName());

    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (arguments.size() != 1) {
            logger.warning("PUSH command requires 1 argument.");
            throw new IllegalArgumentException("PUSH command requires 1 argument.");
        }
        Double value = context.definedValues.get(arguments.get(0));
        if (value == null) {
            logger.warning("Such a variable is not defined.");
            throw new IllegalArgumentException("There's no such variable.");
        }
        context.stack.push(context.definedValues.get(arguments.get(0)));
        logger.info("The variable " + arguments.get(0) +
                " = " + context.definedValues.get(arguments.get(0)) + " was pushed on the stack.");
    }
}
