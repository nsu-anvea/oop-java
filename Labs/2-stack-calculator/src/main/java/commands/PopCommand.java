package commands;

import java.util.List;
import java.util.logging.Logger;

public class PopCommand implements Command {
    private static final Logger logger = Logger.getLogger(PopCommand.class.getName());

    @Override
    public void execute(List<String> arguments, CommandContext context) {
        if (!arguments.isEmpty()) {
            logger.warning("POP command requires 0 argument.");
            throw new IllegalArgumentException("POP command requires 0 argument.");
        }
        context.stack.pop();
        logger.info("The value on the top of the stack was deleted.");
    }
}
