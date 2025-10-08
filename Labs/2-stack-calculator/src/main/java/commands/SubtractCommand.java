package commands;

import java.util.EmptyStackException;
import java.util.List;
import java.util.logging.Logger;

public class SubtractCommand implements Command {
    private static final Logger logger = Logger.getLogger(SubtractCommand.class.getName());

    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (!arguments.isEmpty()) {
            logger.warning("- command requires 0 argument.");
            throw new IllegalArgumentException("- command requires 0 argument.");
        }
        try {
            double value1 = context.stack.pop();
            double value2 = context.stack.pop();
            context.stack.push(value2 - value1);
            logger.info("Result: " + context.stack.peek());
        }
        catch (EmptyStackException e) {
            logger.warning("Too few arguments on the stack: " + e.getLocalizedMessage());
            throw new RuntimeException("Too few arguments on the stack: " + e.getLocalizedMessage());
        }
    }
}
