package factories;

import commands.Command;
import commands.PopCommand;

public class PopCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new PopCommand();
    }
}
