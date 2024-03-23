package Skeleton;

import Entities.Student;
import GameManagers.Game;
import Items.FFP2Mask;
import Labyrinth.Map;
import Labyrinth.Room;

import java.util.Scanner;

/**
 * The static Skeleton class which is used to test the use-cases defined in the week 5 documentation.
 */
public class Skeleton {

    private static int ID = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // beolvasás
            String[] cmd = scanner.nextLine().split(" ");

            int in = Integer.parseInt(cmd[0]);

            switch (in) {
                case 0:
                    System.exit(0);
                case 1:
                    System.out.print("l");
                    break;
                case 2:
                    break;
                case 26:
                    Test_26();
                    break;
                case 27:
                    Test_27();
                    break;
            }
        }
    }

    //region Use-cases

    //region Berci use-cases

    /**
     * Student entering a gassed room (with protection)
     */
    private static void Test_26() {
        FancyPrint("Test #26");
        System.out.println("Student entering a gassed room (with protection)");

        // inits
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        FFP2Mask ffp2Mask = new FFP2Mask();
        student.PickUpItem(ffp2Mask);
        Room mainHall = new Room();
        Room room1 = new Room();
        Room room2 = new Room();
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);

        //
    }

    /**
     * Student entering a gassed room (without protection)
     */
    private static void Test_27() {
        FancyPrint("Test #27");
        System.out.println("Student entering a gassed room (without protection)");

        // inits
        Game game = new Game();
        Student student = new Student(game);
        game.AddStudent(student);
        Room mainHall = new Room();
        Room room1 = new Room();
        Room room2 = new Room();
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
    }

    //endregion
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
