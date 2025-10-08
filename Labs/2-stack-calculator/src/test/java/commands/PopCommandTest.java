package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

class PopCommandTest {
    private static PopCommand popCommand;

    PopCommandTest() {
        popCommand = new PopCommand();
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereAreArguments() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> popCommand.execute(arguments, context));
    }

    @Test
    public void ReturnArrayIndexOutOfBoundsExceptionIfTooFewValuesOnStack() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        assertThrows(EmptyStackException.class, () -> popCommand.execute(arguments, context));
    }
}