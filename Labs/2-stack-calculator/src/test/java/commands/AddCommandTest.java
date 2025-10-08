package commands;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {
    @Test
    public void addTwoValues() {
        AddCommand addCommand = new AddCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        Random random = new Random();
        double value1 = random.nextDouble();
        double value2 = random.nextDouble();
        context.stack.push(value1);
        context.stack.push(value2);

        addCommand.execute(arguments, context);

        double expected = value1 + value2;
        assertEquals(expected, context.stack.peek());
    }

    @Test
    public void ReturnIllegalArgumentExceptionIfThereAreArguments() {
        AddCommand addCommand = new AddCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        arguments.add("anyArgument");
        assertThrows(IllegalArgumentException.class,() -> addCommand.execute(arguments, context));
    }

    @Test
    public void ReturnRuntimeExceptionIfTooFewValuesOnStack() {
        AddCommand addCommand = new AddCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        assertThrows(RuntimeException.class,() -> addCommand.execute(arguments, context));

        context.stack.push(new Random().nextDouble());

        assertThrows(RuntimeException.class,() -> addCommand.execute(arguments, context));
    }
}