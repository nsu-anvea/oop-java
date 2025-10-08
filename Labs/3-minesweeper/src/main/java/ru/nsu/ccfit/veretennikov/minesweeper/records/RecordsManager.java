package ru.nsu.ccfit.veretennikov.minesweeper.records;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecordsManager {
    private static final String FILE_PATH = "src/main/java/ru/nsu/ccfit/veretennikov/minesweeper/records/records.txt";
    private final List<Record> records = new ArrayList<>();

    public RecordsManager() {
        load();
    }

    public void addRecord(Record r) {
        records.add(r);
        save();
    }

    public List<Record> getTop(int maxCount, int rows, int cols, int mines) {
        return records.stream()
                .filter(r -> r.rows == rows && r.cols == cols && r.mines == mines)
                .sorted(Comparator.naturalOrder())
                .limit(maxCount)
                .collect(Collectors.toList());
    }

    private void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Record r : records) {
                writer.printf("%d %d %d %d%n", r.rows, r.cols, r.mines, r.timeInSeconds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 4) continue;

                int rows = Integer.parseInt(parts[0]);
                int cols = Integer.parseInt(parts[1]);
                int mines = Integer.parseInt(parts[2]);
                int time = Integer.parseInt(parts[3]);

                records.add(new Record(rows, cols, mines, time));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
