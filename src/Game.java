import java.util.Scanner;

public class Game {
    Board playerBoard; //The board that is visible to the player
    Board backendBoard; //The board in the background, handling mine positions etc
    Scanner sc = new Scanner(System.in);

    public void startGame(){
        int boardSize;
        int round = 1;
        System.out.println("Hello! Welcome to minesweeper!\n"+
                    "                          *  *  '   ;\n"+
                    "                       *; * ' * ; \n"+
                    "                         * **;*;;*,\n" +
                    "                          _/  * ; * \n" +
                    "                     ____/    \n" +
                    "                   __I__       \n"+
                    "                   OOOO  \n" +
                    "               OOOOOOOOOOO \n"+
                    "             OOOOOOOOOOOOOOO \n" +
                    "            OOOOOOOOOOOOOOOOO \n" +
                    "            OOOOOOOOOOOOOOOOO\n" +
                    "              OOOOOOOOOOOOO \n" +
                    "                OOOOOOOOO\n");

        do {
            boardSize = chooseLayout();
            backendBoard = new Board(boardSize, BoardType.BackendBoard);
            int difficulty =chooseDifficulty();
            backendBoard.setUpBackendBoard(difficulty);
            playerBoard = new Board(boardSize, BoardType.PlayerBoard);
            backendBoard.printBoard();
            playerBoard.printBoard();
            // ta in userinput war man vill undersöker etc.
            round++;

            System.out.println("Get ready for round " + round + "!");

        }

        while (playAgain());
          

    }

    public int chooseLayout() {
        System.out.println(
                """
                        How large do you want your board to be?
                        You can choose between a scale of 6x6 to 40x40!
                        Please type 6 for 6x6, 8 for 8x8 and so on.""");
        boolean validAnswer = true;

        int scale = 0;
        do {
            try {
                int boardScale = Integer.parseInt(sc.nextLine());
                if (boardScale >= 6 && boardScale <= 40) {
                    scale = boardScale;
                    validAnswer = true;
                }
                if (boardScale < 6 || boardScale > 40) {
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

        boolean validChoiceDifficulty;
        int difficulty = 0;
        do {

            String choiceOfDifficulty = sc.nextLine();
            switch (choiceOfDifficulty.toLowerCase()) {
                case "e" -> {
                    difficulty = 10;
                    validChoiceDifficulty = true;
                }
                case "m" -> {
                    difficulty = 15;
                    validChoiceDifficulty = true;
                }
                case "h" -> {
                    difficulty = 20;
                    validChoiceDifficulty = true;
                }
                default -> {
                    System.out.println("That's not a valid answer, " +
                            "please choose between \"e\" for easy, \"m\" for medium and \"h\" for hard!");
                    validChoiceDifficulty = false;
                }
            }
        } while (!validChoiceDifficulty);

        return difficulty;
    }

    public boolean playAgain() {
        boolean validAnswer;

        do {
            System.out.println("Wanna play again? Type \"c\" for continue, type \"q\" to quit");
            String playAgain = sc.nextLine();

            if (playAgain.equalsIgnoreCase("c")) {
                playerBoard.resetBoard();
                backendBoard.resetBoard();
                return true;

            } else if (playAgain.equalsIgnoreCase("q")) {
                System.out.println("See you around! Bye, bye!");

                return false;
            } else {
                System.out.println("Not a valid answer");
                validAnswer = false;
            }

        } while (!validAnswer);
        return true;
    }

    public void gameOver(int x, int y){
        System.out.println("BOOM!! X= "+x+" and Y= "+y+" was a mine\n GAME OVER!");
        backendBoard.printBoard();
    }

    static int wins = 0;
    public static void winCounter(String name) {

        // if checkwin is true
        wins++;
        System.out.println("Congratulations you won!!! \n Your total wins are" + wins);
    }



}
