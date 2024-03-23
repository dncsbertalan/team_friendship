package Skeleton;

import java.util.Scanner;

/**
 * The static Skeleton class which is used to test the use-cases defined in the week 5 documentation.
 */
public class Skeleton {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // beolvasás
            String[] cmd = scanner.nextLine().split(" ");

            int in=26;

            switch (in) {
                case 1:
                    System.out.print("l");
                    break;
                case 2:
                    break;
                case 26:
                    Test_26();
                    break;
            }
        }
    }

    //region Use-cases

    /**
     * Student entering a gassed room (without protection)
     */
    private static void Test_26() {

    }

    //endregion

    //region Initializations

    //endregion

    //region Helper methods

    /**
     * Just a fancy printing method.
     * @param text the text to be printed
     */
    private static void FancyPrint(String text) {
        for (int i = 0; i < text.length() + 4 + 5 + 5; i++) {
            System.out.print("=");
        }
        System.out.print("\n||     ");
        System.out.print(text);
        System.out.print("     ||\n");
        for (int i = 0; i < text.length() + 4 + 5 + 5; i++) {
            System.out.print("=");
        }
    }

    //endregion
}
