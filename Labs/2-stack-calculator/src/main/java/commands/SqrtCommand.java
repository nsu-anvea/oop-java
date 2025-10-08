package commands;

import java.util.List;
import java.util.logging.Logger;

public class SqrtCommand implements Command{
    private static final Logger logger = Logger.getLogger(SqrtCommand.class.getName());

    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (!arguments.isEmpty()) {
            logger.warning("SQRT command requires 0 argument.");
            throw new IllegalArgumentException("SQRT command requires 0 argument.");
        }
        double value = context.stack.pop();
        if (value < 0) {
            logger.warning("SQRT command got negative value.");
            throw new IllegalArgumentException("SQRT command requires a positive number.");
        }
        context.stack.push(Math.sqrt(value));
        logger.info("Result: " + context.stack.peek());
    }
}
