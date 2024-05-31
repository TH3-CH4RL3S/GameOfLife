import java.util.Random;

public class GameOfLife {
    private int columns;
    private int rows;
    private int[][] board;

    public GameOfLife(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new int[columns][rows];

        initializeBoard(rows, columns);
    }

    private void initializeBoard(int rows, int columns) {
        board = new int[rows][columns];
        Random random = new Random();
        double threshold = 0.4; // Justera tröskelvärdet för att ändra andelen levande celler

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (random.nextDouble() > threshold) {
                    board[i][j] = 1; // Levande cell
                } else {
                    board[i][j] = 0; // Död cell
                }
            }
        }
    }

    public void updateBoard() {
        int[][] newBoard = new int[columns][rows];

        // Gå igenom varje cell i brädet
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                // Räkna antalet levande grannar för den aktuella cellen
                int neighbors = countNeighbors(i, j);

                // Tillämpa reglerna för "The Game of Life"
                if (board[i][j] == 1 && (neighbors < 2 || neighbors > 3)) {
                    // En levande cell med färre än två eller fler än tre levande grannar dör
                    newBoard[i][j] = 0;
                } else if (board[i][j] == 0 && neighbors == 3) {
                    // En död cell med exakt tre levande grannar blir levande
                    newBoard[i][j] = 1;
                } else {
                    // Annars behåller cellen sitt nuvarande tillstånd
                    newBoard[i][j] = board[i][j];
                }
            }
        }

        // Uppdatera spelplanen med det nya tillståndet
        board = newBoard;
    }

    public void resetBoard(){
        initializeBoard(rows, columns); //Sätter ett nytt seed
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int[][] getBoard() {
        return board;
    }

    private int countNeighbors(int x, int y) {
        int count = 0;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Gå igenom varje granncell runt den aktuella cellen
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // Kontrollera om granncellen är inom spelplanen och om den är levande
            if (nx >= 0 && nx < columns && ny >= 0 && ny < rows && board[nx][ny] == 1) {
                count++;
            }
        }

        return count;
    }
}