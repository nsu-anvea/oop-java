package commands;

import java.util.List;

public interface Command {
    void execute(List<String> arguments, CommandContext context);
}
