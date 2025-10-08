package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SubtractCommandTest {
    public static SubtractCommand subtractCommand;

    SubtractCommandTest() {
        subtractCommand = new SubtractCommand();
    }

    @Test
    public void subtractValues() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        Random random = new Random();
        double value1 = random.nextDouble();
        double value2 = random.nextDouble();
        context.stack.push(value1);
        context.stack.push(value2);

        subtractCommand.execute(arguments, context);

        assertEquals(value1 - value2, context.stack.peek());
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereAreArguments() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> subtractCommand.execute(arguments, context));
    }

    @Test
    public void ReturnRuntimeExceptionIfTooFewValuesOnStack() {
        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        context.stack.push(new Random().nextDouble());

        assertThrows(RuntimeException.class, () -> subtractCommand.execute(arguments, context));
    }
}