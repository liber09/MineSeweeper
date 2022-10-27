import java.util.Scanner;
public class Game {
    Board playerBoard; //The board that is visible to the player
    Board backendBoard; //The board in the background, handling mine positions etc
    Scanner sc = new Scanner(System.in);
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_L_BLUE = "\u001B[94m";
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_WHITE = "\u001B[37m";
    public static final String TEXT_BOLD ="\033[0;1m";
    public void startGame(){
        int boardSize;
        int difficulty;
        int round = 1;

// Insane Bomb Panic 2!
        System.out.println("              "+TEXT_RED+"                 *  S  *       ;  . \n"+ TEXT_RESET+
                "Hello! \uD83D\uDCA3 Welcome to"+ TEXT_BOLD+ TEXT_RED+"   I     * N,  *  * A,  NE\n"+
                "                       "+TEXT_RED+"  * '; "+TEXT_YELLOW+"*  *  '"+TEXT_RED+"' *  ;\n"+
                "                        *;"+TEXT_YELLOW+" * BO *"+TEXT_RED+" ;MB *'\n"+
                "                          *P *"+TEXT_YELLOW+"*;AN;"+TEXT_RED+";*,\n" +
                "                         "+TEXT_WHITE+"/  "+TEXT_RED+"I* ; C* \n" +
                "                   "+TEXT_WHITE+"    /   \n" +
                "            "+TEXT_BLUE+"         _"+TEXT_WHITE+"s"+TEXT_BLUE+"_       \n"+
                "                    OOOO  \\\n" +
                "               OOOOO OOOOOOO \\\n"+
                "             OOOOT O"+TEXT_L_BLUE+" INSANE"+TEXT_BLUE+" OO \\\n" +
                "            OOOL  OOO"+TEXT_L_BLUE+" BOMB"+TEXT_BLUE+" OOOO )\n" +
                "            OOOL  OOO"+TEXT_L_BLUE+" PANIC "+TEXT_BLUE+"0OO )\n" +
                "              OOOJ OOO"+TEXT_L_BLUE+" 2 "+TEXT_BLUE+"OOOOO )\n" +
                "                OOOOOOOOOOO  )\n"+TEXT_RESET);


        do {
            boardSize = chooseLayout();
            difficulty = chooseDifficulty();

            backendBoard = new Board(boardSize, BoardType.BackendBoard);
            playerBoard = new Board(boardSize, BoardType.PlayerBoard);
            backendBoard.setUpBackendBoard(difficulty);
            playerBoard.printBoard();

            System.out.println("Get ready for round " + round + "!");
            gameLoop();

            round++;
        }
        while (playAgain());
    }
    private void gameLoop() {
        Scanner input = new Scanner(System.in);
        String[] currentInput;
        int[] coordinates = new int[2];
        Countdown Count = new Countdown();
        Count.counter(counter);
        String wannaPlaceFlag;
        boolean gaveUp = false;
        int hint = 3; //Player is given three hints each round

        // Outer loop, runs for each move the player makes
        while (true) {
            printNumberOfBombsAndMarkedBombs();
            // Inner loop, runs until the player enters correct input
            //int timeLeft =Count.remainingTime();

            while (true) {
                if(Count.timesLeft()){
                    System.out.println(Count.remainingTime()+" seconds left");} else {
                    timeIsUp();
                    return;

                }
                System.out.println(getInstructions(hint));

                String rawInput = input.nextLine();
                if (rawInput.equalsIgnoreCase("h") && hint > 0){
                    backendBoard.hint(playerBoard);
                    playerBoard.printBoard();
                    hint--;
                    if(playerBoard.checkWin(backendBoard.getTotalNumberOfBombs(), backendBoard)) {
                        System.out.println(TEXT_YELLOW +"Congratulations! You made it!"+TEXT_RESET);
                        wins++;
                        System.out.println("Your total wins are: " + wins);
                        return;
                    }
                    continue;
                }
               if(rawInput.equals("q")){
                    System.out.println("Sad you gave up so easy!");
                    gaveUp = true;
                    break;       }



                    currentInput = rawInput.split(" ");
                // ok den sätter första på index 1 andra på index2 så har jag två strings
                if(currentInput.length == 1){
                    System.out.println("Input is needed, please try again");
                    continue;
                }
                if (rawInput.charAt(0) == ' ') {
                    System.out.println("Please put first tho coordinate for X then space then Y");
                    continue;}
                wannaPlaceFlag = currentInput[0].substring(0, 1);

                if (wannaPlaceFlag.equalsIgnoreCase("F")) {
                    try {
                        placeFlags(currentInput);
                    } catch (NumberFormatException n) {
                        System.out.println("Please enter co-ordinates (row and column) with just a space in between.");
                    } catch (IndexOutOfBoundsException i) {
                        System.out.println("Please enter TWO numbers; row and column.");
                    }
                } else {
                    try {
                        for (int i = 0; i < 2; i++) {
                            coordinates[i] = Integer.parseInt(currentInput[i]);
                        }
                        backendBoard.revealSquares(coordinates[0], coordinates[1], playerBoard);
                        playerBoard.printBoard();
                        if (playerBoard.isSquareFlag(coordinates[0], coordinates[1])){
                            System.out.println("There is a flag already on this position, please try another or remove flag first.");
                            break;
                        }else{
                            if (backendBoard.checkIfBomb(coordinates[0], coordinates[1])) {
                                gameOver(coordinates[0], coordinates[1]);
                                return;
                            }
                        }
                    } catch (NumberFormatException n) {
                        System.out.println("Please enter co-ordinates (row and column) with just a space in between.");
                    } catch (IndexOutOfBoundsException i) {
                        System.out.println("Please enter TWO numbers; row and column.");
                    }
                }
                if(playerBoard.checkWin(backendBoard.getTotalNumberOfBombs(), backendBoard)) {
                    System.out.println(TEXT_YELLOW +"Congratulations! You made it!"+TEXT_RESET);
                    wins++;
                    System.out.println("Your total wins are: " + wins);
                    return;
                }
            }

            if(gaveUp){
                break;
            }
        }
    }
    //Takes user input and converts it to int array then calls placeFlags on playerBoard.
    private void placeFlags(String[] input){
        int[] coordinates = new int[2];
        try{
            coordinates[0] = Integer.parseInt(input[0].substring(1));
            coordinates[1] = Integer.parseInt(input[1]);
            playerBoard.placeFlag(coordinates[0], coordinates[1]);
            backendBoard.printBoard();
            playerBoard.printBoard();
        } catch (NumberFormatException n) {
            System.out.println("Please enter co-ordinates (row and column) with just a space in between.");

        } catch (IndexOutOfBoundsException i) {
            System.out.println("Please enter TWO numbers; row and column.");
        }
    }
    //Returns user instructions depending on hints left, if zero we omit the row with number of hints left.
    private String getInstructions(int hintsLeft){
        String instructions;
        if (hintsLeft > 0){
            instructions = "Want to " + TEXT_YELLOW + "check a field: " + TEXT_RESET +
                    "Enter coordinates  x and y, separate with space. \n" +
                    "Want to" + TEXT_RED + " set/remove a flag: " + TEXT_RESET +
                    "Put an " + TEXT_RED + "\"F\""+TEXT_RESET+" before your coordinates \n\n" +
                    "Hints left:"+hintsLeft+" Do you want a hint? press \"h\"\n" +
                    "If you want to give up, please type \"q\""+TEXT_RESET;
        }else{
            instructions = "Want to " + TEXT_YELLOW + "check a field: " + TEXT_RESET +
                    "Enter coordinates  x and y, separate with space. \n" +
                    "Want to" + TEXT_RED + " set/remove a flag: " + TEXT_RESET +
                    "Put an " + TEXT_RED + "\"F\""+TEXT_RESET+" before your coordinates \n" +
                    "Want to give up, please " + TEXT_RED + " type \"q\""+TEXT_RESET;
        }
        return instructions;
    }

    //What layout does the player want. Can be 6x6 up to 40x40.
    public int chooseLayout() {
        System.out.println(
                " How large shall your board be?" +
                        "Choose between a "+TEXT_YELLOW+"scale of 6x6 to 40x40!"+TEXT_RESET+"\n " +
                        "Type "+TEXT_BOLD+TEXT_YELLOW+"\"6\" for 6x6, \"7\" for 7x7 "+ TEXT_RESET+"and so on.");
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
                    System.out.println("Not a valid choice! Please choose a number between 6 and 40");
                    validAnswer = false;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Not a valid choice! Please choose a number between 6 and 40");
                validAnswer = false;
            }
        } while (!validAnswer);

        return scale; // boardSize
    }

    //How hard should the game be. Easy=10% bombs, Medium=15% bombs, Hard=20% bombs
    protected int chooseDifficulty() {
        System.out.println("GREAT CHOICE! Choose your level of difficulty:" + TEXT_YELLOW +"\"e\" for easy, \"m\" for medium and \"h\" for hard!"+TEXT_RESET);

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
                            "please choose between"+TEXT_YELLOW+" \"e\" for easy, \"m\" for medium and \"h\" for hard!"+TEXT_RESET);
                    validChoiceDifficulty = false;
                }
            }
        } while (!validChoiceDifficulty);

        return difficulty;
    }

    //Checks if the player wants to play the game again
    public boolean playAgain() {


        while (true) {
            System.out.println("Wanna play again? Type " + TEXT_YELLOW + "\"c\" for continue," + TEXT_RED + " type \"q\" to quit" + TEXT_RESET);
            String playAgain = sc.nextLine();

            if (playAgain.equalsIgnoreCase("c")) {
                return true;

            } else if (playAgain.equalsIgnoreCase("q")) {
                System.out.println("See you around! Bye, bye!");

                return false;
            } else {
                System.out.println("Not a valid answer");
            }
        }
    }

    //The game is over, player hit a bomb and loose, inform player.
    public void gameOver(int x, int y){
        System.out.println(TEXT_RED+"BOOM!! \uD83D\uDCA3" +TEXT_RESET+" X= "+x+" and Y= "+y+" was a bomb\n GAME OVER!");
        backendBoard.printBoard();
    }
    public void timeIsUp(){
        System.out.println(TEXT_RED+"BOOM!! \uD83D\uDCA3" +TEXT_RESET+" your time was up - GAME OVER");
        backendBoard.printBoard();
    }
    //Prints number of mines and number of marked mines to the user
    public void printNumberOfBombsAndMarkedBombs(){
        System.out.println("Number of bombs to find "+ backendBoard.getTotalNumberOfBombs() + ". You have now marked "+playerBoard.countFlags()+ " suspected bombs.");
    }
    int counter =240;
    static int wins = 0;
}