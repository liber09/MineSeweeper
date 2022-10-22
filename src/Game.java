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
            int difficulty =chooseDifficulty();
            gameLoop(boardSize,difficulty);

               round++;

            System.out.println("Get ready for round " + round + "!");

        }
        while (playAgain());
    }
    private void gameLoop(int boardSize, int difficulty) {
        backendBoard = new Board(boardSize, BoardType.BackendBoard);
        playerBoard = new Board(boardSize, BoardType.PlayerBoard);
        backendBoard.setUpBackendBoard(difficulty);
        printNumberOfMinesAndMarkedMines();
        backendBoard.printBoard();
        playerBoard.printBoard();
        /*backendBoard.setTotalMinesFromStart(boardSize,difficulty); */
        while (true){


            System.out.println("If your want to "+TEXT_YELLOW+"check a field: "+TEXT_RESET+" \n" +
                    "Enter coordinates on x and y and separate with space: \n"+
                    "If you want to"+TEXT_RED+" set or remove a flag"+TEXT_RESET+ "\n"+
                    "Enter first \"F\" and without any " +
                    "further space the coordinates on x and y and (separate those  with space): ");

            Scanner input = new Scanner(System.in);

            String[] currentInput;
            int[] coordinates = new int[2];

            String wannaPlaceFlag;




                    currentInput = input.nextLine().split(" ");
                    // ok den sätter första på index 1 andra på index2 så har jag två strings

                    wannaPlaceFlag = currentInput[0].substring(0,1);

                    if(wannaPlaceFlag.equalsIgnoreCase("F")){
                        try {

                        int x= Integer.parseInt(currentInput[0].substring(1));
                        int y= Integer.parseInt(currentInput[1]);
                        playerBoard.placeFlag(x,y); break;
                    } catch (NumberFormatException n) {
                    System.out.println("Please enter co-ordinates (row and column) with just a space in between.");
                } catch(IndexOutOfBoundsException i) {
                    System.out.println("Please enter TWO numbers; row and column.");
                }

                    } else {  try {

                    for (int i = 0; i < 2; i++) {
                        coordinates[i] = Integer.parseInt(currentInput[i]);
                        int x=coordinates[0];
                        int y=coordinates[1];
                        backendBoard.revealEmptySquares(x, y, playerBoard);
                        playerBoard.printBoard();
                        if (backendBoard.checkIfMine(x,y))
                        {gameOver(x,y);break;}
                    }
                    break;
                } catch (NumberFormatException n) {
                    System.out.println("Please enter co-ordinates (row and column) with just a space in between.");
                } catch(IndexOutOfBoundsException i) {
                    System.out.println("Please enter TWO numbers; row and column.");
                }
            }}
            }
            //else if (playerBoard.checkWin()== true){
              //  System.out.println("Who is awesome????? YOU ARE!!! Congrats you won!!");
               // break;
            //}








            /*playerBoard.printBoard();
            System.out.println("Choose the X coordinate");
            int catchX = sc.nextInt();
            sc.nextLine();
            System.out.println("Choose the Y coordinate");
            int catchY = sc.nextInt();
            sc.nextLine();

            if(backendBoard.checkIfMine(catchX, catchY)){
                gameOver(catchX, catchY);

            }
            else {
                playerBoard.setEmptySpace(catchX, catchY);
            }
            //need to also check if we have won
        }                 */



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

// gör F+koordinaterna för flaggan och bara för att undersöka koordinates



}
