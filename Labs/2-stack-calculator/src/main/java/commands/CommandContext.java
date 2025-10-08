package commands;

import java.util.HashMap;
import java.util.Stack;

public class CommandContext {
    public Stack<Double> stack;
    public HashMap<String, Double> definedValues;

    public CommandContext() {
        stack = new Stack<>();
        definedValues = new HashMap<>();
    }
}
