package factories;

import commands.Command;
import commands.SubtractCommand;

public class SubtractCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new SubtractCommand();
    }
}
