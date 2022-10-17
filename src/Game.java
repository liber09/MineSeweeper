import java.util.Scanner;

public class Game {
    Board playerBoard; //The board that is visible to the player
    Board backendBoard; //The board in the background, handling mine positions etc

    Scanner sc = new Scanner(System.in);



    public int chooseLayout() {



        System.out.println(
                "How large do you want your board to be?\n" +
                        "You can choose between a scale of 6x6 to 99x99!\n" +
                        "Please type 6 for 6x6, 8 for 8x8 and so on."); // just a scratch until we decide how many fields min and max are
        // do while until valid answer och try catch if string
        boolean validAnswer = true;

        int scale = 0;
        do {

            try {
                int boardScale = Integer.parseInt(sc.nextLine());
                if (boardScale > 6 && boardScale < 99) {
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
            ;
        } while (!validAnswer);
        //System.out.println(scale);
        return scale;


    }

    protected   int chooseDifficulty() {
        System.out.println("GREAT CHOICE! What level of difficulty do you choose?\n" +
                "Please press \"e\" for easy, \"m\" for medium and \"h\" for hard!");

        boolean validChoiceDifficulty = true;
        int difficulty = 0;
        do {

            String choiceOfDifficulty = sc.nextLine();
            if (choiceOfDifficulty.equals("e")) {
                // 10% of game board will be mines
              difficulty = 10;
                validChoiceDifficulty = true;
            } else if
            (choiceOfDifficulty.equals("m")) {
                // 15% math.ceil of gameboard will be mines
difficulty = 15;
                //call method
                validChoiceDifficulty = true;
            } else if
            (choiceOfDifficulty.equals("h")) {
                // 20% math.ceil of gameboard will be mines
                difficulty = 20;
                //call method
                validChoiceDifficulty = true;
            } else {
                System.out.println("That's not a valid answer, " +
                        "please choose between \"e\" for easy, \"m\" for medium and \"h\" for hard!");
                validChoiceDifficulty = false;

            }
        } while (!validChoiceDifficulty);
System.out.println(difficulty);
        return difficulty;
    }
}
