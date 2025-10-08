import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Writer {
    public void write(FileOutputStream fos, List<Map.Entry<String, Integer>> words, int numWords) {
        StringBuilder sb = new StringBuilder();

        sb.append("Word")
                .append(";")
                .append("Frequency")
                .append(";")
                .append("Percentage")
                .append("\n");

        for (Map.Entry<String, Integer> entry : words) {
            float frequency = ((float) entry.getValue() / numWords) * 100;
            sb.append(entry.getKey())
                    .append(";")
                    .append(entry.getValue())
                    .append(";")
                    .append(String.format("%.2f", frequency))
                    .append("\n");
        }
        try {
            fos.write(sb.toString().getBytes());
        }
        catch (IOException e) {
            System.out.println("Cannot write to file: " + e.getLocalizedMessage());
        }
    }
}
