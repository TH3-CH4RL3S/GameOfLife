import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameOfLifeGUI extends JFrame {
    private static final int WIDTH = 820;
    private static final int HEIGHT = 900;

    private JPanel boardPanel;
    private JButton startButton;
    private JButton stopButton;
    private JButton stepButton;
    private JButton resetButton;
    private JTextField inputField;
    private Timer timer; // Timer för att upprepa uppdateringar

    private GameOfLife gameOfLife;
    private int intValue;
    private String userInput;

    public GameOfLifeGUI(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;

        setTitle("Game of Life");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrera fönstret på skärmen

        // Skapa spelplanen
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                //System.out.println("inside paintComponent");

                super.paintComponent(g);
                drawBoard(g, gameOfLife.getBoard(), gameOfLife.getColumns(), gameOfLife.getRows());
            }
        };
        boardPanel.setPreferredSize(new Dimension(600, 600)); // Storlek på spelplanen
        add(boardPanel, BorderLayout.CENTER);

        // Skapa knappar för att styra spelet
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        stepButton = new JButton("Step");
        resetButton = new JButton("Reset");
        inputField = new JTextField(10);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(inputField);
        add(buttonPanel, BorderLayout.SOUTH);

            // Lägg till lyssnare för knapparna
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Starta spelet genom att uppdatera spelplanen kontinuerligt
                startGame();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stoppa spelet genom att sluta uppdatera spelplanen
                stopGame();
            }
        });


        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Utför en generation av spelet
                stepGame();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Startar om spelet
                resetGame();
            }
        });

        // Lägg till lyssnare för textrutan
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
                // Hämta texten från textrutan och använd den på önskat sätt
                userInput = inputField.getText();
                try {
                    intValue = Integer.parseInt(userInput);
                    // Använd intValue på önskat sätt
                    System.out.println("User input as integer: " + intValue);
                } catch (NumberFormatException ex) {
                    // Felaktigt format, hantera detta
                    System.out.println("Invalid input format: " + userInput);
                }
                startGame();
            }
        });

    }

    // Metoder för att hantera knappfunktionalitet
    private void startGame() {
        // Starta spelet genom att uppdatera spelplanen kontinuerligt
        //System.out.println("Starta");
        if (timer == null) {
            timer = new Timer(intValue, new ActionListener() { // Uppdatera varje sekund
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameOfLife.updateBoard(); // Uppdatera spelplanen
                    boardPanel.repaint(); // Rita om spelplanen
                }
            });
            timer.start(); // Starta timer
        }
    }
    
    
    private void stopGame() {
        // Stoppa spelet genom att sluta uppdatera spelplanen
        //System.out.println("Stoppa");
        if (timer != null) {
            timer.stop(); // Stoppa timer
            timer = null; // Återställ timer
        }
    }
    
    private void stepGame() {
        // Utför en generation av spelet
        gameOfLife.updateBoard(); // Uppdatera spelplanen
        boardPanel.repaint(); // Rita om spelplanen
    }

    private void resetGame() {
        // Startar om spelet
        System.out.println("Reset");
        stopGame();
        gameOfLife.resetBoard(); //Återställer brädet
        boardPanel.repaint(); // Rita om spelplanen
    }


    private void drawBoard(Graphics g, int[][] board, int columns, int rows) {
        int cellSize = Math.min(boardPanel.getWidth() / columns, boardPanel.getHeight() / rows);

        // Rita varje cell på spelplanen
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j] == 1) {
                    //System.out.println("Svart");
                    g.setColor(Color.BLACK); // Svart för levande celler
                } else {
                    //System.out.println("Vit");
                    g.setColor(Color.WHITE); // Vit för döda celler
                }
                int x = i * cellSize;
                int y = j * cellSize;
                g.fillRect(x, y, cellSize, cellSize); // Rita en fyrkant för varje cell
            }
        }
    }
    
}

