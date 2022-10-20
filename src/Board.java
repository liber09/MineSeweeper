import java.util.Random;

public class Board {
    private int boardSize; //The size of the board.
    private String[][] board; //Contains the board.
    private int totalMinesFromStart;
    private BoardType type;
    private final String UNKNOWN = "#"; //Hidden square
    private final String MINE = "*"; //Mine square
    private final String FLAG = "F"; //User suspects mine square
    private final String EMPTY = " "; //Empty square

    /*
        Board constructor.
        Takes boardSize as parameter and fills
        every square with UNKNOWN sign.


     */
    public Board(int boardSize, BoardType type) {
        this.boardSize = boardSize;
        this.type = type;
        createBoard(type);
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    //Checks a square to see if it contains a flag, if so return true, otherwise return false.
    public Boolean isSquareFlag(int x, int y){
        if (checkSquare(x,y).equals(FLAG)){
            return true;
        }
        return false;
    }
    //Place a flag if square is unknown or remove flag if there is a flag on the position. Place on playerBoard and print the board.
    public void placeFlag(int x, int y){
        if(board[x][y].equals(UNKNOWN)){
            board[x][y] = FLAG;
        }
        else if(board[x][y].equals(FLAG)){
            board[x][y] = UNKNOWN;
        }
        printBoard();
    }
    public void createBoard(BoardType type) {
        board = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (type == BoardType.PlayerBoard) {
                    board[i][j] = UNKNOWN;
                }
                if (type == BoardType.BackendBoard) {
                    board[i][j] = EMPTY;
                }

            }
        }
    }

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

    /*
    Takes an int, difficulty, that the user provides.
    Sets up backendBoard with mines and numbers, using placeBombs and countBombs
     */
    public void setUpBackendBoard(int difficulty) {
        placeBombs(boardSize, difficulty);
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                int surroundingMines = countBombs(row, column);
                if (surroundingMines != 0 && board[row][column] == EMPTY) {
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

                    if (!(y == row && x == column)) {
                        if (board[y][x] == "*") { // << Fixed it!
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

    public void placeBombs(int boardSize, int totalMinesFromStart) {
        Random ran = new Random();

        double percent = totalMinesFromStart / 100.0;
        int boardslots = boardSize * boardSize;
        double bombs = boardslots * percent;

        for (int i = 0; i < bombs; i++) {
            int x = ran.nextInt(boardSize);
            int y = ran.nextInt(boardSize);

            board[x][y] = MINE;

        }

    }
    //Takes x and y coordinates and returns the value of that square
    public String checkSquare(int x, int y){
        return board[x][y];
    }

    /*
        Checks if the player has won by looping through the board
        and count all UNKNOWN squares.
        If they are more than the totalMinesFromStart
        the player has not won. If they are equal, the player has cleared all unknown squares without mines and won the game.
     */
    public boolean checkWin() {
        int unKnownCounter = 0;
        boolean hasPlayerWon = false;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].equals(UNKNOWN)) {
                    unKnownCounter++;
                }
                if (unKnownCounter > totalMinesFromStart) {
                    hasPlayerWon = false;
                } else {
                    hasPlayerWon = true;
                }
            }
        }
        return hasPlayerWon;
    }

    //Call createNewBoard to reset the gameBoard, use boardType to get correct initial layout.
    public void resetBoard() {
        createBoard(this.type);

    }

    public boolean checkIfMine(int x, int y) {
       return checkSquare(x,y).equals(MINE);
    }

    public void setEmptySpace(int x, int y) {
        board[x][y] = EMPTY;
    }
}