package minesweeper1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим игры:");
        System.out.println("1 - Консоль");
        System.out.println("2 - Графический (Swing)");
        System.out.print("Ваш выбор: ");
        int mode = scanner.nextInt();

        int rows = 10, cols = 10, mines = 5;
        Model board = new Model(rows, cols, mines);

        if (mode == 1) {
            View consoleView = new View(board);
            board.addObserver(consoleView);
            consoleView.render();
            Controller controller = new Controller(board);
            controller.startGame();
        } else if (mode == 2) {
            SwingView swingView = new SwingView(board);
            board.addObserver(swingView);
        } else {
            System.out.println("Неверный режим.");
        }
    }
}
