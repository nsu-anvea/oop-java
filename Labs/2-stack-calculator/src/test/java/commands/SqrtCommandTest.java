package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SqrtCommandTest {
    public static SqrtCommand sqrtCommand;

    SqrtCommandTest() {
        sqrtCommand = new SqrtCommand();
    }

    @Test
    public void sqrtCommandTest() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        double value = new Random().nextDouble();
        context.stack.push(value);

        sqrtCommand.execute(arguments, context);

        assertEquals(Math.sqrt(value), context.stack.peek());
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereAreArguments() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> sqrtCommand.execute(arguments, context));
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfNegativeValueOnStack() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        context.stack.push(new Random().nextDouble() * -1);

        assertThrows(IllegalArgumentException.class, () -> sqrtCommand.execute(arguments, context));
    }
}