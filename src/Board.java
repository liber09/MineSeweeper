import java.util.ArrayList;
import java.util.Random;
public class Board {
    protected int boardSize; //The size of the board.
    private String[][] board; //Contains the board.
    private int totalNumberOfBombs;
    private BoardType type;
    protected final String UNKNOWN = TEXT_GREEN+"\u25af"+TEXT_RESET; //Hidden square
    private final String BOMB = TEXT_RED+"\u2638"+TEXT_RESET; //Mine square
    private final String FLAG = TEXT_LIGHT_RED +"\u2691"+TEXT_RESET; //User suspects mine square
    protected final String EMPTY = " "; //Empty square
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_LIGHT_RED = "\u001B[91m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_RESET = "\u001B[0m";

    /*
      Board constructor.
      Takes boardSize and BoardType as parameter and fills
      every square with UNKNOWN or EMPTY sign, depending on BoardType.
    */
    public Board(int boardSize, BoardType type) {
        this.boardSize = boardSize;
        this.type = type;
        createBoard(type);
    }

    //return the number of bombs on the board.
    public int getTotalNumberOfBombs(){
        return this.totalNumberOfBombs;
    }

    //loop through the board if an unknown is found return true, otherwise false
    private boolean areThereUnknownSquaresLeft(){
        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                if (board[i][j].equals(UNKNOWN)){
                    return true;
                }
            }
        }
        return false;
    }

    //Count how many flags the player has placed on the board and return number
    public int countFlags(){
        int flagCounter = 0;
        for(int row = 0;row<boardSize;row++){
            for(int column = 0;column<boardSize;column++){
                if(board[row][column].equals(FLAG)){
                    flagCounter++;
                }
            }
        }
        return flagCounter;
    }


    //Takes row and y coordinates and returns the value of that square
    public String checkSquare(int row, int column){
        return board[row][column];
    }


    //Checks a square to see if it contains a flag, if so return true, otherwise return false.
    public Boolean isSquareFlag(int row, int column){
        return checkSquare(row,column).equals(FLAG);
    }


    public boolean checkIfBomb(int row, int column) {
        return checkSquare(row,column).equals(BOMB);
    }


    //Place a flag if square is unknown or remove flag if there is a flag on the position. Place on playerBoard.
    public void placeFlag(int row, int column){
        if(board[row][column].equals(UNKNOWN)){
            board[row][column] = FLAG;
        }
        else if(board[row][column].equals(FLAG)){
            board[row][column] = UNKNOWN;
        }

    }
    public void createBoard(BoardType type) {
        board = new String[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (type == BoardType.PlayerBoard) {
                    board[row][column] = UNKNOWN;
                }
                if (type == BoardType.BackendBoard) {
                    board[row][column] = EMPTY;
                }

            }
        }
    }


    /*
    Takes an int, difficulty, that the user provides.
    Sets up backendBoard with mines and numbers, using placeBombs and countBombs
     */
    public void setUpBackendBoard(int difficulty) {
        int totalSquares = boardSize * boardSize;
        totalNumberOfBombs = totalSquares * difficulty / 100;
        placeBombs();

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                int surroundingMines = countBombs(row, column);
                if (surroundingMines != 0 && board[row][column].equals(EMPTY)) {
                    board[row][column] = String.valueOf(surroundingMines);
                }
            }
        }
    }

    /*
    Takes two ints, representing index for row and column in the board
    Counts the bombs on the (up to) eight squares surrounding the chosen square.
    Returns the result as an int.
    */
    public int countBombs(int row, int column) {

        int numberOfBombs = 0;

        for (int y = row - 1; y <= row + 1; y++) {
            for (int x = column - 1; x <= column + 1; x++) {

                // If we check a square next to the edge, we will get an IndexOutOfBondsException,
                // so we just catch it and move on.
                try {
                    // We don't check the middle square!
                    if (!(y == row && x == column)) {
                        if (board[y][x].equals(BOMB)) {
                            numberOfBombs++;
                        }
                    }
                } catch (IndexOutOfBoundsException i) {
                    /*
                    Nothing to see here, just carry on with your day!
                    */
                }
            }
        }
        return numberOfBombs;
    }

    //Place bombs
    public void placeBombs() {
        Random ran = new Random();

        for (int i = 0; i < totalNumberOfBombs; i++) {
            int row = ran.nextInt(boardSize);
            int column = ran.nextInt(boardSize);
            if (!checkIfBomb(row,column)){ // Changed from validBombPlacement to !checkIfBomb - removed validBombPlacement.
                board[row][column] = BOMB;
            }else{
                i--;
            }
        }
    }

    /*
    Used in backendBoard to reveal empty squares and add them to playerBoard. It also adds the border numbers.
     */
    public void revealSquares(int row, int column, Board playerBoard) {
        // first, we check the bounderies
        if(row < 0 || row > boardSize - 1 || column < 0 || column > boardSize - 1) {
            return;
        }
        // if all squares are checked but the bombs
        //if(totalMinesFromStart+1==playerBoard.UNKNOWN.length()){
          //  System.out.println("HINT: There are only "+totalMinesFromStart+" bombs on the field");

       // }
        // Here we check if the square is empty and not already checked.
        if(board[row][column].equals(EMPTY) && playerBoard.board[row][column].equals(UNKNOWN)) {
            // If so, we update the square in playerBoard...
            playerBoard.board[row][column] = EMPTY;
            // ...and check the surrounding squares by four recursive calls!
            revealSquares(row - 1, column, playerBoard);
            revealSquares(row + 1, column, playerBoard);
            revealSquares(row, column - 1, playerBoard);
            revealSquares(row, column + 1, playerBoard);
        // If the square isn't EMPTY, but unchecked, we just uncover it
        } else if(playerBoard.board[row][column].equals(UNKNOWN)) {
            playerBoard.board[row][column] = board[row][column];
        }
    }

    /*
        Checks if the player has won by looping through the board
        and count all UNKNOWN squares.
        If they are more than the totalMinesFromStart
        the player has not won. If they are equal, the player has cleared all unknown squares without mines and won the game.
     */
    public boolean checkWin(int totalMineCount, Board backendBoard) {
        int unKnownCounter = 0;
        int flagCounter = 0;
        int falseFlag =0;
        boolean hasPlayerWon = false;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].equals(UNKNOWN)) {
                    unKnownCounter++;
                }
                if (board[i][j].equals(FLAG) && backendBoard.board[i][j].equals(BOMB)){
                    flagCounter++;
                }
                if (board[i][j].equals(FLAG) && !backendBoard.board[i][j].equals(BOMB)){
                    falseFlag++;
                }
            }
        }
        if (((unKnownCounter+flagCounter) == totalMineCount || flagCounter == totalMineCount) && falseFlag==0) {

            hasPlayerWon = true;

        }

        return hasPlayerWon;
    }

    /*
        Player wants help to open a square, check if there is unknown left and if so, random coordinates and check if bomb and that it is unknown.
        if so call revealSquares.
     */

    public void hint(Board PlayerBoard ){
        boolean unknownLeft = areThereUnknownSquaresLeft();
        //If we don't have any unknown squares leave method.
        if (unknownLeft){
            System.out.println("There are no unknown squares left but you have more flags than there is bombs. Unflag first and try again.");
            return;
        }
        Random ran = new Random();
        int row = ran.nextInt(boardSize);
        int column = ran.nextInt(boardSize);
        if (checkIfBomb(row, column)|| !PlayerBoard.checkSquare(row,column).equals(UNKNOWN))
        {
            hint(PlayerBoard);
        } else {
            revealSquares(row,column, PlayerBoard);
        }
    }

    public boolean newHint(Board playerBoard) {

        ArrayList<int[]> validSquares = new ArrayList<>();
        Random ran = new Random();

        for(int row = 0;row < boardSize; row++){
            for(int column = 0; column < boardSize; column++){
                if(!checkIfBomb(row, column) &&
                        (playerBoard.board[row][column].equals(UNKNOWN))) {
                    validSquares.add(new int[]{row, column});
                }
            }
        }
        if(validSquares.size() > 0) {
            int[] randomSquare = validSquares.get(ran.nextInt(validSquares.size()));
            revealSquares(randomSquare[0], randomSquare[1], playerBoard);
            return true;
        } else {
            return false;
        }
    }

    // does not work properly when all mines are flagged but one is a false flag
    //it casts an error


    /*
      Prints the board. A couple of loops
      printing different parts of the board.
    */
    public void printBoard() {
        //Print column headers
        for (int i = 0; i < boardSize; i++) {
            if (i == 0) {
                System.out.print(" Y   " + i);
            } else if (i == boardSize - 1) {
                if (i < 10) {
                    System.out.println("  " + i);
                } else {
                    System.out.println(" " + i);
                }

            } else if (i < 10) {
                System.out.print("  " + i);
            } else {
                System.out.print(" " + i);
            }
        }
        //Print top border
        for (int j = 0; j < boardSize; j++) {
            if (j == 0) {
                System.out.print("X____");
            } else if (j == boardSize - 1) {
                System.out.println("______");
            } else if (j < 10) {
                System.out.print("___");
            } else {
                System.out.print("___");
            }
        }
        /*
            Print row header, right and lefter borders
            and the game squares.
         */
        for (int l = 0; l < boardSize; l++) {
            if (l == 0) {
                System.out.print(l + " |");
            } else if (l == boardSize - 1) {
                if (l < 10) {
                    System.out.print(l + " |");
                } else {
                    System.out.print(l + "|");
                }
            } else {
                if (l < 10) {
                    System.out.print(l + " |");
                } else {
                    System.out.print(l + "|");
                }

            }
            for (int m = 0; m < boardSize; m++) {
                if (m == boardSize - 1) {
                    System.out.println("  " + board[l][m] + " |");
                } else {
                    System.out.print("  " + board[l][m]);
                }
            }

        }
        //Print the bottom border
        for (int n = 0; n < boardSize; n++) {
            if (n == 0) {
                System.out.print("_____");
            } else if (n == boardSize - 1) {
                System.out.println("______");
            } else if (n < 10) {
                System.out.print("___");
            } else {
                System.out.print("___");
            }
        }
    }
}