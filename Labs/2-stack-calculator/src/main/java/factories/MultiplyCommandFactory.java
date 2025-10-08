package factories;

import commands.Command;
import commands.MultiplyCommand;

public class MultiplyCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new MultiplyCommand();
    }
}
