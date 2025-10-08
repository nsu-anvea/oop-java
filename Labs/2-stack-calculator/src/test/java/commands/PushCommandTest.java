package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PushCommandTest {
    public static PushCommand pushCommand;

    PushCommandTest() {
        pushCommand = new PushCommand();
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfIncorrectNumberOfArguments() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> pushCommand.execute(arguments, context));
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereIsNoSuchDefinedVariable() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> pushCommand.execute(arguments, context));
    }
}