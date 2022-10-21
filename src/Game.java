import java.util.Scanner;

public class Game {
    Board playerBoard; //The board that is visible to the player
    Board backendBoard; //The board in the background, handling mine positions etc
    Scanner sc = new Scanner(System.in);


    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_WHITE = "\u001B[37m";
    public static final String TEXT_BOLD ="\033[0;1m";
    public void startGame(){
        int boardSize;
        int round = 1;
        System.out.println("Hello! Welcome to"+ TEXT_BOLD+ TEXT_RED+"       * MI,  SW  **,  ER\n"+
                    "                      "+TEXT_RED+"  * '; "+TEXT_YELLOW+"NE *  EE"+TEXT_RED+"P' *  ;\n"+
                    "                       *;"+TEXT_YELLOW+" * ' *"+TEXT_RED+" ; *!'\n"+
                    "                         * *"+TEXT_YELLOW+"*;*;"+TEXT_RED+";*,\n" +
                    "                        "+TEXT_WHITE+"/  "+TEXT_RED+"* ; * \n" +
                    "                  "+TEXT_WHITE+"    /   \n" +
                    "           "+TEXT_BLUE+"         _"+TEXT_WHITE+"s"+TEXT_BLUE+"_       \n"+
                    "                   OOOO  \\\n" +
                    "               OOOOOOOOOOOO \\\n"+
                    "             OOOOOOOOOOOOOOOO \\\n" +
                    "            OOOOOOOOOOOOOOOOOO )\n" +
                    "            OOOOOOOOOOOOOOOOOO )\n" +
                    "              OOOOOOOOOOOOOO  )\n" +
                    "                OOOOOOOOOO  )\n"+TEXT_RESET);



        do {
            boardSize = chooseLayout();
            backendBoard = new Board(boardSize, BoardType.BackendBoard);
            playerBoard = new Board(boardSize, BoardType.PlayerBoard);
            int difficulty =chooseDifficulty();
            backendBoard.setUpBackendBoard(difficulty);


            printNumberOfMinesAndMarkedMines();
            //Check if the selected x,y coordinates is a flag, if so, ask if user wants to remove flag
            int x = 0; //Remove this when coding gameLoop
            int y = 0; //Remove this when coding gameLoop
            if(playerBoard.isSquareFlag(x,y)){
                System.out.println("Do you want to remove the flag? press "+TEXT_BOLD+"\"y\""+TEXT_RESET+"" +
                        " for yes or "+TEXT_BOLD+"\"n\""+TEXT_RESET+" for no");
                String removeFlag = sc.nextLine().toLowerCase();
                switch (removeFlag){
                    case "y":
                        playerBoard.placeFlag(x,y);
                        break;
                }
            } 
            backendBoard.printBoard();
            playerBoard.printBoard();

            // ta in userinput war man vill undersöker etc.
            round++;

            System.out.println("Get ready for round " + round + "!");
            gameLoop();

        }

        while (playAgain());
          

    }

    private void gameLoop() {
        while (true){
            playerBoard.printBoard();
            System.out.println("Choose the X coordinate");
            int catchX =  sc.nextInt();
            sc.nextLine();
            System.out.println("Choose the Y coordinate");
            int catchY = sc.nextInt();
            sc.nextLine();

            if(backendBoard.checkIfMine(catchX, catchY)){
                gameOver(catchX,catchY);
                break;
            }
            else {
                playerBoard.setEmptySpace(catchX,catchY);
            }
            //need to also check if we have won
        }
    }

    public int chooseLayout() {
        System.out.println(
                " How large do you want your board to be?\n " +
                        "You can choose between a scale of 6x6 to 40x40!\n " +
                        "Please type "+TEXT_BOLD+" 6 for 6x6, 8 for 8x8 "+ TEXT_RESET+"and so on.");
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
    //Prints number of mines and number of marked mines to the user
    public void printNumberOfMinesAndMarkedMines(){
        System.out.println("Number of mines to find "+ backendBoard.getTotalMinesFromStart() + ". You have now marked "+playerBoard.countNumberOfMarkedBombs()+ " of them.");
    }


    static int wins = 0;
    public static void winCounter(String name) {

        // if checkwin is true
        wins++;
        System.out.println("Congratulations you won!!! \n Your total wins are" + wins);
    }



}
