package Skeleton;

import Entities.Professor;
import Entities.Student;
import GameManagers.Game;
import Items.FFP2Mask;
import Items.SlipStick;
import Labyrinth.Map;
import Labyrinth.Room;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The static Skeleton class which is used to test the use-cases defined in the week 5 documentation.
 */
public class Skeleton {

    //region Use-case names
    static final HashMap<Integer, String> testNames = new HashMap<>();

    static {
        testNames.put(10, "Successful student movement between rooms");
        testNames.put(11, "Successful professor movement between rooms");
        testNames.put(12, "Unsuccessful student movement between rooms");
        testNames.put(13, "Unsuccessful professor movement between rooms");
        testNames.put(14, "Slipstick acquisition");
        testNames.put(15, "Slipstick disposal");
        testNames.put(16, "Winning");
        testNames.put(17, "Losing");
        testNames.put(26, "Student entering a gassed room (with protection)");
        testNames.put(27, "Student entering a gassed room (without protection)");
        testNames.put(28, "Professor entering a gassed room (with protection)");
        testNames.put(29, "Professor entering a gassed room (without protection)");
        testNames.put(30, "Student entering a room with a professor (with protection)");
        testNames.put(31, "Student entering a room with a professor (without protection)");
        testNames.put(32, "Professor entering a room with a student (with protection)");
        testNames.put(33, "Professor entering a room with a student (without protection)");
    }
    //endregion

    private static int ID = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // beolvasás
            //String[] cmd = scanner.nextLine().split(" ");
            String inputString = scanner.nextLine();
            int in = 0;

            try {
                in = Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                System.out.println("\nERROR: A number must be entered");
            }

            switch (in) {
                case 0:
                    System.exit(0);
                case 1:
                    Test_14();

                    break;
                case 2:
                    Test_10();
                    break;
                case 26:
                    Test_26();
                    break;
                case 27:
                    Test_27();
                    break;

                default:
                    System.out.println("\nChoose a number from the menu!");
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
        System.out.println(testNames.get(26));

        // inits
        Game game = new Game();
        Student student = new Student(game);
        game.AddStudent(student);
        FFP2Mask ffp2Mask = new FFP2Mask();
        student.PickUpItem(ffp2Mask);
        Room mainHall = new Room(game);
        Room room1 = new Room(game);
        Room room2 = new Room(game);
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room2.AddStudentToRoom(student);
        student.SetCurrentRoom(room2);
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
        System.out.println(testNames.get(27));

        // inits
        Game game = new Game();
        Student student = new Student(game);
        game.AddStudent(student);
        Room mainHall = new Room(game);
        Room room1 = new Room(game);
        Room room2 = new Room(game);
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room2.AddStudentToRoom(student);
        student.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
    }

    //endregion

    //region bene usekéz
    @DisplayName("Student movement successful")
    public static void Test_10() {
        FancyPrint("Test #10");
        System.out.println(testNames.get(10));

        //inits
        Game g = new Game();
        Room r1 = new Room(g);
        Room r2 = new Room(g);
        r2.AddNeighbour(r1);
        Student student = new Student(g);
        r1.AddStudentToRoom(student);
        Map map = new Map(g);
        g.setMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        boolean success = true;
        //test
        student.StepInto(r2);
        if(student.GetCurrentRoom()==r2) {
            System.out.println("Test #14 failed");
        }else{
            System.out.println("Test #14 passed");
        }
    }

    @DisplayName("Professor movement successful")
    public static void Test_11() {
        FancyPrint("Test #11");
        System.out.println(testNames.get(11));

        //inits
        Game g = new Game();
        Room r1 = new Room(g);
        Room r2 = new Room(g);
        r2.AddNeighbour(r1);
        Professor professor = new Professor(g);
        r1.AddProfessorToRoom(professor);
        Map map = new Map(g);
        g.setMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        boolean success = true;

        //test
        professor.StepInto(r2);
        if(professor.GetCurrentRoom()==r2) {
            System.out.println("Test #14 failed");
        }else{
            System.out.println("Test #14 passed");
        }
    }

    @DisplayName("Student movement successful")
    public static void Test_12() {
        FancyPrint("Test #12");
        System.out.println(testNames.get(12));

        //inits
        Game g = new Game();
        Room r1 = new Room(g);
        Room r2 = new Room(0, g);
        r2.AddNeighbour(r1);
        Student student = new Student(g);
        r1.AddStudentToRoom(student);
        Map map = new Map(g);
        g.setMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        boolean success = true;
        //test
        student.StepInto(r2);
        if(student.GetCurrentRoom()==r1) {
            System.out.println("Test #14 failed");
        }else{
            System.out.println("Test #14 passed");
        }

    }

    @DisplayName("Professor movement unsuccessful")
    public static void Test_13() {
        FancyPrint("Test #13");
        System.out.println(testNames.get(13));

        //inits
        Game g = new Game();
        Room r1 = new Room(g);
        Room r2 = new Room(0, g);
        r2.AddNeighbour(r1);
        Professor professor = new Professor(g);
        r1.AddProfessorToRoom(professor);
        Map map = new Map(g);
        g.setMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        boolean success = true;

        //test
        professor.StepInto(r2);
        if(professor.GetCurrentRoom()==r1) {
            System.out.println("Test #14 failed");
        }else{
            System.out.println("Test #14 passed");
        }
    }

    @DisplayName("Slipstick acquisition")
    public static void Test_14(){
        FancyPrint(("Test #14\n"+testNames.get(14)));

        //inits
        SlipStick slip = new SlipStick();
        Game game = new Game();
        Student student = new Student(game);
        game.AddStudent(student);
        student.PickUpItem(slip);

        //test
        if(game.IsLastPhase()) {
            System.out.println("Test #14 failed");
        }else{
            System.out.println("Test #14 passed");
        }
    }


        //endregion
    //endregion

    //region Helper methods

    /**
     * Just a fancy printing method. Ends the last line with '\n'.
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
        System.out.print("\n");
    }

    /**
     * Prints the menu to the standard output.
     */
    private static void PrintMenu() {
        FancyPrint("USE-CASE MENU");
        System.out.println("Choose a number from the menu to select the use-case to be tested!" +
                "\nThe use-cases: \n");
    }

    //endregion
}
