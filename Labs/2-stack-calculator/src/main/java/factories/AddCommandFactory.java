package factories;

import commands.Command;
import commands.AddCommand;

public class AddCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new AddCommand();
    }
}
