import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        // To run testBoard uncomment the following line and comment out game.startGame
        //testBoard(15, 10, game);
        game.startGame();

    }
    
    public static void testBoard(int size, int difficulty, Game game) {
        game.backendBoard = new Board(size, BoardType.BackendBoard);
        game.playerBoard = new Board(size, BoardType.PlayerBoard);
        game.backendBoard.setUpBackendBoard(difficulty);
        //game.backendBoard.setTotalMinesFromStart(size,difficulty);

        game.backendBoard.printBoard();

        while(true) {
            System.out.println("Enter coordinates on x and y and separate with space: ");
            Scanner input = new Scanner(System.in);
            String[] currentInput;
            int[] coordinates = new int[2];
            while(true) {
                try {
                    currentInput = input.nextLine().split(" ");
                    for (int i = 0; i < 2; i++) {
                        coordinates[i] = Integer.parseInt(currentInput[i]);
                    }
                    break;
                } catch (NumberFormatException n) {
                    System.out.println("Please enter co-ordinates (row and column) with just a space in between.");
                } catch(IndexOutOfBoundsException i) {
                    System.out.println("Please enter TWO numbers; row and column.");
                }
            }
            game.backendBoard.revealEmptySquares(coordinates[0], coordinates[1], game.playerBoard);
            game.playerBoard.printBoard();
        }
    }
}