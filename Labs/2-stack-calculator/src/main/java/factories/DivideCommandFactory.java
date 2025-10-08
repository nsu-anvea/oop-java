package factories;

import commands.Command;
import commands.DivideCommand;

public class DivideCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new DivideCommand();
    }
}
