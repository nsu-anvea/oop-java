import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream("resourses/" + args[0]), StandardCharsets.UTF_8);
            HashMap<String, Integer> words = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            int numWords = 0;
            int character;
            while ((character = reader.read()) != -1) {
                if (Character.isLetterOrDigit((char)character)) {
                    sb.append((char)character);
                }
                else {
                    if (!sb.isEmpty()) {
                        addWord(words, sb.toString());
                        sb.delete(0, sb.length());
                        numWords++;
                    }
                }
            }
            if (!sb.isEmpty()) {
                addWord(words, sb.toString());
                sb.delete(0, sb.length());
                numWords++;
            }
            System.out.println(numWords);
            System.out.println(words.size());

            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(words.entrySet());
            sortedWords.sort((o1, o2) -> {
                if (o1.getValue().equals(o2.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return o2.getValue().compareTo(o1.getValue());
            });

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("resourses/table.csv");

                Writer writer = new Writer();
                writer.write(fos, sortedWords, numWords);
            }
            catch (IOException e) {
                System.err.println("Error while writing to file: " + e.getLocalizedMessage());
            }
            finally {
                if (fos != null) {
                    try {
                        fos.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    public static void addWord(HashMap<String, Integer> words, String word) {
        words.putIfAbsent(word.toLowerCase(), 0);
        words.put(word.toLowerCase(), words.get(word.toLowerCase()) + 1);
    }
}