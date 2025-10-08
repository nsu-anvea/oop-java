package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MultiplyCommandTest {
    @Test
    public void multiplyValues() {
        MultiplyCommand multiplyCommand = new MultiplyCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        Random random = new Random();
        double value1 = random.nextDouble();
        double value2 = random.nextDouble();
        context.stack.push(value1);
        context.stack.push(value2);

        multiplyCommand.execute(arguments, context);

        assertEquals(value1 * value2, context.stack.peek());
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereAreArguments() {
        MultiplyCommand multiplyCommand = new MultiplyCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("A");

        assertThrows(IllegalArgumentException.class, () -> multiplyCommand.execute(arguments, context));
    }

    @Test
    public void ReturnRuntimeExceptionIfTooFewValuesOnStack() {
        MultiplyCommand multiplyCommand = new MultiplyCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        assertThrows(RuntimeException.class, () -> multiplyCommand.execute(arguments, context));

        context.stack.push(new Random().nextDouble());

        assertThrows(RuntimeException.class, () -> multiplyCommand.execute(arguments, context));
    }
}