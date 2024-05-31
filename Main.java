public class Main
{
    public static void main(String[] args) {
        try {
            GameOfLife gameOfLife = new GameOfLife(200, 200);
            GameOfLifeGUI gui = new GameOfLifeGUI(gameOfLife); // Skapa instans av GUI
            gui.setVisible(true); // Gör fönstret synligt
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

