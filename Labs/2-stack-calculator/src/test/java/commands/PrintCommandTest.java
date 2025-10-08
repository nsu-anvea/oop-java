package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PrintCommandTest {
    private static PrintCommand printCommand;

    PrintCommandTest() {
        printCommand = new PrintCommand();
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereAreArguments() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> printCommand.execute(arguments, context));
    }
}