package minesweeper1;

public class View implements Observer {
    private final Model board;

    public View(Model board) {
        this.board = board;
    }

    public void update() {
        render();
    }

    public void render() {
        Cell[][] grid = board.getGrid();
        System.out.println("Current board:");
        for (Cell[] cells : grid) {
            for (int c = 0; c < grid[0].length; c++) {
                Cell cell = cells[c];
                if (cell.isRevealed) {
                    if (cell.isMine) {
                        System.out.print("* ");
                    } else {
                        System.out.print(cell.neighborMines + " ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
