import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        int boardSize;

        System.out.println("Hello! Welcome to minesweeper!\n");
        boardSize = game.chooseLayout();
        game.backendBoard = new Board(boardSize, BoardType.BackendBoard);
        Scanner sc = new Scanner(System.in);
        game.backendBoard.printBoard();

        game.chooseDifficulty(); // code for easy, medium or hard here


        if (playAgain(sc)) {
            continueSettings(sc,game); // the player can choose weather to continue with the same settings or choose new ones
        }

    }

    private static void continueSettings(Scanner sc, Game game) {


        boolean validSettings = true;
        do {
            System.out.println("Do you want to play with the same settings as before press \"b\" \n" +
                    "if you want to change your settings press \"c\" ");
            String howToContinue = sc.nextLine();
            if (howToContinue.equals("b")) {
                System.out.println("hugahahah");
                // method call for reset board
            } else if (howToContinue.equals("c")) {
                game.chooseLayout();
                game.chooseDifficulty();

            } else {
                System.out.println("not a valid choice");
                validSettings = false;
            }

        } while (!validSettings);
    }

    public static boolean playAgain(Scanner sc) {
        boolean validAnswer;

        do {
            System.out.println("Wanna play again? Type \"c\" for continue, type \"q\" to quit");
            String playAgain = sc.nextLine();

            if (playAgain.equals("c")) {

                return true;

            } else if (playAgain.equals("q")) {
                System.out.println("See you around! Bye, bye!");

                return false;
            } else {
                System.out.println("Not a valid answer");
                validAnswer = false;
            }

        } while (!validAnswer);
        return true;
    }

}