package learning.properties;

import commands.CommandContext;
import commands.DefineCommand;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Properties {
    public static void main(String[] args) throws FileNotFoundException {
        /* Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "qwerty");

        System.out.println(properties.getProperty("password"));



        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("src/main/java/factories/config.properties");
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getLocalizedMessage());
        }

        try {
            properties.store(fos, "anyComments");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */

        CommandContext context = new CommandContext();
        List<String> arguments = new ArrayList<>();
        arguments.add("a");
        arguments.add("5");
        DefineCommand defineCommand = new DefineCommand();
        defineCommand.execute(arguments, context);
        System.out.println(context.definedValues);

        arguments.clear();
        arguments.add("b");
        arguments.add("5");
        defineCommand.execute(arguments, context);
        System.out.println(context.definedValues);

    }
}
