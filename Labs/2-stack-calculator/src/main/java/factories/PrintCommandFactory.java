package factories;

import commands.Command;
import commands.PrintCommand;

public class PrintCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new PrintCommand();
    }
}
