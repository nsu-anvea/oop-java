import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        logger.info("Program started.");

        if (args.length > 1) {
            logger.warning("Too many arguments.");
            throw new IllegalArgumentException("Too many arguments.");
        }

        // Opening config.properties
        InputStream fis = null;
        Properties properties;
        try {
            fis = Main.class.getResourceAsStream("config.properties");
            properties = new Properties();
            try {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        // Creating Reader
        BufferedReader reader;
        try {
            if (args.length == 0) {
                reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            }
            else {
                InputStream is = Main.class.getResourceAsStream(args[0]);
                if (is == null) {
                    throw new FileNotFoundException(args[0]);
                }
                reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                //reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream(args[0])), StandardCharsets.UTF_8));
                //reader = new BufferedReader(new FileReader(PATH_TO_INPUT + args[0]));
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        // Calculating
        Calculator calculator = new Calculator();
        calculator.executeCommands(properties, reader);

        try {
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}