package commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class DivideCommandTest {
    @Test
    public void divideFirstValueBySecondValue() {
        DivideCommand divideCommand = new DivideCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        Random random = new Random();
        double value1 = random.nextDouble();
        double value2 = random.nextDouble();
        context.stack.push(value1);
        context.stack.push(value2);

        divideCommand.execute(arguments, context);

        assertEquals(value1 / value2, context.stack.pop());
    }

    @Test
    public void divideByZero() {
        DivideCommand divideCommand = new DivideCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        Random random = new Random();
        double value1 = random.nextDouble();
        double value2 = 0.0;
        context.stack.push(value1);
        context.stack.push(value2);

        assertThrows(RuntimeException.class, () -> divideCommand.execute(arguments, context));
    }

    @Test
    public void ReturnRuntimeExceptionIfTooFewValuesOnStack() {
        DivideCommand divideCommand = new DivideCommand();

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();

        assertThrows(RuntimeException.class, () -> divideCommand.execute(arguments, context));

        Random random = new Random();
        context.stack.push(random.nextDouble() + 1);

        assertThrows(RuntimeException.class, () -> divideCommand.execute(arguments, context));
    }
}