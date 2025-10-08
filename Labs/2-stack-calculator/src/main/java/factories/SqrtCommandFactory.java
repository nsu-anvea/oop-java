package factories;

import commands.Command;
import commands.SqrtCommand;

public class SqrtCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new SqrtCommand();
    }
}
