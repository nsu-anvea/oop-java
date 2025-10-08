package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DefineCommandTest {
    @Test
    public void defining() {
        DefineCommand defineCommand = new DefineCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");
        arguments.add("1");

        defineCommand.execute(arguments, context);

        assertEquals(1, context.definedValues.get("A"));
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfIncorrectNumberOfArguments() {
        DefineCommand defineCommand = new DefineCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> defineCommand.execute(arguments, context));

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> defineCommand.execute(arguments, context));
    }
}