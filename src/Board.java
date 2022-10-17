public class Board {

    public Board(int boardSize){

    }
    public int countBombs(int row, int column) {
        /*
        Takes two ints, representing index for row and column in the board.
        Counts the bombs on the (up to) eight squares surrounding the chosen square.
        Returns the result as an int.
        */
        int numberOfBombs = 0;

        for(int y = row - 1; y <= row + 1; y++) {
            for(int x = column - 1; x <= column + 1; x++){

                // If we check a square next to the edge, we will get an IndexOutOfBondsException,
                // so we just catch it and move on.
                try {
                    if(!(y == row && x == column)) {
                        if (hiddenBoard[y].charAt(x) == '*') { // << change this to whatever the String[] representing the board is called.
                            numberOfBombs++;
                        }
                    }
                } catch(IndexOutOfBoundsException i) {
                    /*
                    Nothing to see here, just carry on with your day!
                    */
                }
            }
        }
        return numberOfBombs;
    }
}
