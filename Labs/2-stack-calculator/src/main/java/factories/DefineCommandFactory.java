package factories;

import commands.Command;
import commands.DefineCommand;

public class DefineCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new DefineCommand();
    }
}
