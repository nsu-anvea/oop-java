package commands;

import java.util.List;
import java.util.logging.Logger;

public class PrintCommand implements Command {
    private static final Logger logger = Logger.getLogger(PrintCommand.class.getName());

    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (!arguments.isEmpty()) {
            logger.warning("PRINT command requires 0 argument.");
            throw new IllegalArgumentException("PRINT command requires 0 argument.");
        }
        System.out.println(context.stack.peek());
        logger.info("The value on the top of the stack was printed.");
    }
}