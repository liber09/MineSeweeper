import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello! Welcome to minesweeper!\n");

        chooseLayout(); // code for scale here

        Scanner sc = new Scanner(System.in);

        chooseDifficulty(sc); // code for easy, medium or hard here


        if (playAgain(sc)) {
            continueSettings(sc); // the player can choose weather to continue with the same settings or choose new ones
        }

    }

    private static void continueSettings(Scanner sc) {


        boolean validSettings = true;
        do {
            System.out.println("Do you want to play with the same settings as before press \"b\" \n" +
                    "if you want to change your settings press \"c\" ");
            String howToContinue = sc.nextLine();
            if (howToContinue.equals("b")) {
                System.out.println("hugahahah");
                // method call for reset board
            } else if (howToContinue.equals("c")) {
                chooseLayout();
                chooseDifficulty(sc);

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

    public static int chooseLayout() {

        Scanner sc = new Scanner(System.in);

        System.out.println(
                "How large do you want your board to be?\n" +
                        "You can choose between a scale of 6x6 to 15x15!\n" +
                        "Please type 6 for 6x6, 8 for 8x8 and so on."); // just a scratch until we decide how many fields min and max are
        // do while until valid answer och try catch if string
        boolean validAnswer = true;

        int scale = 0;
        do {

            try {
                int boardScale = Integer.parseInt(sc.nextLine());
                if (boardScale > 6 && boardScale < 15) {
                    scale = boardScale;

                    validAnswer = true;
                }
                if (boardScale < 6 || boardScale > 15) {
                    System.out.println("Not a valid choice! Please choose a number between 6 and 15");
                    validAnswer = false;
                }

            } catch (NumberFormatException ex) {
                System.out.println("Not a valid choice! Please choose a number between 6 and 15");
                validAnswer = false;

            }
            ;
        } while (!validAnswer);
        //System.out.println(scale);
        return scale;


    }

    private static void chooseDifficulty(Scanner sc) {
        System.out.println("GREAT CHOICE! What level of difficulty do you choose?\n" +
                "Please press \"e\" for easy, \"m\" for medium and \"h\" for hard!");

        boolean validChoiceDifficulty = true;
        do {

            String choiceOfDifficulty = sc.nextLine();
            if (choiceOfDifficulty.equals("e")) {
                // 10% of game board will be mines
                int easy = 10;
                validChoiceDifficulty = true;
            } else if
            (choiceOfDifficulty.equals("m")) {
                // 15% math.ceil of gameboard will be mines
                int medium = 15;
                //call method
                validChoiceDifficulty = true;
            } else if
            (choiceOfDifficulty.equals("h")) {
                // 20% math.ceil of gameboard will be mines
                int hard = 20;
                //call method
                validChoiceDifficulty = true;
            } else {
                System.out.println("That's not a valid answer, " +
                        "please choose between \"e\" for easy, \"m\" for medium and \"h\" for hard!");
                validChoiceDifficulty = false;

            }
        } while (!validChoiceDifficulty);

    }

}