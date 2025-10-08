package minesweeper1;

import java.util.Scanner;

public class Controller {
    private final Model model;
    private final Scanner scanner = new Scanner(System.in);

    public Controller(Model model) {
        this.model = model;
    }

    public void startGame() {
        while (!model.isGameOver()) {
            System.out.print("Enter row and column to reveal (e.g. 1 2): ");
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            model.reveal(r, c);

            if (model.getGrid()[r][c].isMine) {
                System.out.println("BOOM! You hit a mine.");
                return;
            }
        }
        System.out.println("Congratulations! You cleared the model.");
    }
}
