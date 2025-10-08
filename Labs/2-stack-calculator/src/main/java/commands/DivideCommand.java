package commands;

import java.util.EmptyStackException;
import java.util.List;
import java.util.logging.Logger;

public class DivideCommand implements Command {
    private static final Logger logger = Logger.getLogger(DivideCommand.class.getName());
    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (!arguments.isEmpty()) {
            logger.warning("/ command requires 0 argument.");
            throw new IllegalArgumentException("/ command requires 0 argument.");
        }
        try {
            double value1 = context.stack.pop();
            double value2 = context.stack.pop();
            if (value1 == 0) {
                logger.warning("divide by zero.");
                throw new IllegalArgumentException("divide by zero.");
            }
            context.stack.push(value2 / value1);
            logger.info("Result: " + context.stack.peek());
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        catch (EmptyStackException e) {
            logger.warning(e.getMessage());
            throw new EmptyStackException();
        }
    }
}
