package factories;

import commands.Command;
import commands.PushCommand;

public class PushCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new PushCommand();
    }
}
