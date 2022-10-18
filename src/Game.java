import java.util.Scanner;

public class Game {
    Board playerBoard; //The board that is visible to the player
    Board backendBoard; //The board in the background, handling mine positions etc
    Scanner sc = new Scanner(System.in);

    public void startGame(){
        int boardSize;

        System.out.println("Hello! Welcome to minesweeper!\n");
        boardSize = chooseLayout();
        backendBoard = new Board(boardSize, BoardType.BackendBoard);
        Scanner sc = new Scanner(System.in);
        backendBoard.printBoard();
        backendBoard.placeBombs(boardSize, chooseDifficulty());
        backendBoard.printBoard();

        if (playAgain(sc)) {
            continueSettings(sc); // the player can choose weather to continue with the same settings or choose new ones
        }
    }

    public int chooseLayout() {
        System.out.println(
                "How large do you want your board to be?\n" +
                        "You can choose between a scale of 6x6 to 99x99!\n" +
                        "Please type 6 for 6x6, 8 for 8x8 and so on.");
        boolean validAnswer = true;

        int scale = 0;
        do {
            try {
                int boardScale = Integer.parseInt(sc.nextLine());
                if (boardScale >= 6 && boardScale <= 99) {
                    scale = boardScale;
                    validAnswer = true;
                }
                if (boardScale < 6 || boardScale > 99) {
                    System.out.println("Not a valid choice! Please choose a number between 6 and 99");
                    validAnswer = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Not a valid choice! Please choose a number between 6 and 99");
                validAnswer = false;
            }
        } while (!validAnswer);

        return scale; // boardSize
    }

    protected int chooseDifficulty() {
        System.out.println("GREAT CHOICE! What level of difficulty do you choose?\n" +
                "Please press \"e\" for easy, \"m\" for medium and \"h\" for hard!");

        boolean validChoiceDifficulty = true;
        int difficulty = 0;
        do {

            String choiceOfDifficulty = sc.nextLine();
            if (choiceOfDifficulty.equals("e")) {
                difficulty = 10;
                validChoiceDifficulty = true;
            } else if
            (choiceOfDifficulty.equals("m")) {
                difficulty = 15;
                validChoiceDifficulty = true;
            } else if
            (choiceOfDifficulty.equals("h")) {
                difficulty = 20;
                validChoiceDifficulty = true;
            } else {
                System.out.println("That's not a valid answer, " +
                        "please choose between \"e\" for easy, \"m\" for medium and \"h\" for hard!");
                validChoiceDifficulty = false;

            }
        } while (!validChoiceDifficulty);

        return difficulty;
    }

    public boolean playAgain(Scanner sc) {
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

    protected void continueSettings(Scanner sc) {
        boolean validSettings = true;
        int boardSize;
        do {
            System.out.println("Do you want to play with the same settings as before press \"b\" \n" +
                    "if you want to change your settings press \"c\" ");
            String howToContinue = sc.nextLine();
            if (howToContinue.equals("b")) {

               // backendBoard.resetBoard();
               // System.out.println(backendBoard); does not work yet!
                // cannot take in same difficulty yet
            } else if (howToContinue.equals("c")) {
                boardSize = chooseLayout();
                backendBoard = new Board(boardSize, BoardType.BackendBoard);
                backendBoard.placeBombs(boardSize, chooseDifficulty());
                backendBoard.printBoard();
            } else {
                System.out.println("not a valid choice");
                validSettings = false;
            }

        } while (!validSettings);
    }

}
