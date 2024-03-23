package Skeleton;

import Entities.Professor;
import Entities.Student;
import GameManagers.Game;
import Items.FFP2Mask;
import Items.TVSZ;
import Labyrinth.Map;
import Labyrinth.Room;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The static Skeleton class which is used to test the use-cases defined in the week 5 documentation.
 */
public class Skeleton {

    //region Use-case names
    /**
     * Here we store the use-case names in order to be easily modified and to stay always the same.
     */
    private static final HashMap<Integer, String> testNames = new HashMap<>();
    static {
        testNames.put(0, "Exit");
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

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            PrintMenu();
            int in = GetNumberFromInput(scanner);

            switch (in) {
                case 0:
                    System.exit(0);
                case 1:
                    System.out.print("l");
                    break;
                case 2:
                    break;

                //region Berci use-cases
                case 26:
                    Test_26();
                    GetKeyToContinue(scanner);
                    break;
                case 27:
                    Test_27();
                    GetKeyToContinue(scanner);
                    break;
                case 28:
                    Test_28();
                    GetKeyToContinue(scanner);
                    break;
                case 29:
                    Test_29();
                    GetKeyToContinue(scanner);
                    break;
                case 30:
                    Test_30();
                    GetKeyToContinue(scanner);
                    break;
                case 31:
                    Test_31();
                    GetKeyToContinue(scanner);
                    break;
                case 32:
                    Test_32();
                    GetKeyToContinue(scanner);
                    break;
                case 33:
                    Test_33();
                    GetKeyToContinue(scanner);
                    break;
                //endregion

                default:
                    System.out.println("Choose a number from the menu!");
                    break;
            }
        }
    }

    //region Use-cases

    //region Berci use-cases
    // region GAS ROOM
    /**
     * Student entering a gassed room (with protection)
     */
    private static void Test_26() {
        FancyPrint("Test #26");
        System.out.println(testNames.get(26));

        // Instantiate
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        FFP2Mask ffp2Mask = new FFP2Mask();
            student.PickUpItem(ffp2Mask);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
            room2.AddNeighbour(room1); room1.AddNeighbour(room1);
            room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
            room1.SetToxicity();
        Map map = new Map(game);
            map.AddRoom(room1);
            map.AddRoom(room2);
            map.AddMainHall(mainHall);
        game.setMap(map);

        // Test

    }

    /**
     * Student entering a gassed room (without protection)
     */
    private static void Test_27() {
        FancyPrint("Test #27");
        System.out.println(testNames.get(27));

        // inits
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Room mainHall = new Room(game);
        Room room1 = new Room(game);
        Room room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room1);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.setMap(map);

        // test
        student.StepInto(room1);
        if (student.GetCurrentRoom() == mainHall) {
            System.out.println("Test successful!\nStudent is in the main hall");
        }
    }

    /**
     * Professor entering a gassed room (with protection)
     */
    private static void Test_28() {
        FancyPrint("Test #28");
        System.out.println(testNames.get(28));

        // inits
        Game game = new Game();
        Professor professor = new Professor(game); game.AddProfessor(professor);
        FFP2Mask ffp2Mask = new FFP2Mask();
        professor.PickUpItem(ffp2Mask);
        Room teachersLounge = new Room(game);
        Room room1 = new Room(game);
        Room room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddTeachersLounge(teachersLounge);
        game.setMap(map);
    }

    /**
     * Professor entering a gassed room (without protection)
     */
    private static void Test_29() {
        FancyPrint("Test #29");
        System.out.println(testNames.get(29));

        // inits
        Game game = new Game();
        Professor professor = new Professor(game); game.AddProfessor(professor);
        Room teachersLounge = new Room(game);
        Room room1 = new Room(game);
        Room room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddTeachersLounge(teachersLounge);
        game.setMap(map);

        // test
        professor.StepInto(room1);
        if (professor.GetCurrentRoom() == teachersLounge) {
            System.out.println("Test successful!\nProfessor is in the theachers' lounge");
        }
    }
    //endregion

    //region STUDENT-PROF ENCOUNTER
    /**
     * Student entering a room with a professor (with protection)
     */
    private static void Test_30() {
        FancyPrint("Test #30");
        System.out.println(testNames.get(30));

        // inits
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Professor professor = new Professor(game); game.AddProfessor(professor);
        TVSZ tvsz = new TVSZ();
            student.PickUpItem(tvsz);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
            room2.AddNeighbour(room1);
            room1.AddNeighbour(room1);
        room1.AddStudentToRoom(student); student.SetCurrentRoom(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        Map map = new Map(game);
            map.AddRoom(room1);
            map.AddRoom(room2);
            map.AddMainHall(mainHall);
        game.setMap(map);
    }

    /**
     * Student entering a room with a professor (without protection)
     */
    private static void Test_31() {
        FancyPrint("Test #31");
        System.out.println(testNames.get(31));

        // inits
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Professor professor = new Professor(game); game.AddProfessor(professor);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room1.AddStudentToRoom(student); student.SetCurrentRoom(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.setMap(map);
    }

    /**
     * Professor entering a room with a student (with protection)
     */
    private static void Test_32() {
        FancyPrint("Test #32");
        System.out.println(testNames.get(32));

        // inits
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Professor professor = new Professor(game); game.AddProfessor(professor);
        TVSZ tvsz = new TVSZ();
        student.PickUpItem(tvsz);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room1.AddStudentToRoom(student); student.SetCurrentRoom(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.setMap(map);
    }

    /**
     * Professor entering a room with a student (without protection)
     */
    private static void Test_33() {
        FancyPrint("Test #33");
        System.out.println(testNames.get(33));

        // inits
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Professor professor = new Professor(game); game.AddProfessor(professor);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room1.AddStudentToRoom(student); student.SetCurrentRoom(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.setMap(map);
    }
    //endregion
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
        System.out.println("Choose a number from the menu to select the use-case to be tested!\nThe use-cases:\n");

        for (int i = 0; i <= 33; i++) {
            System.out.println("\t" + i + "\t->\t" + testNames.get(i));
        }
    }

    /**
     * Reads the standard input and searches for a number.
     * @param scanner a scanner
     * @return the number read from the standard input
     */
    private static int GetNumberFromInput(Scanner scanner) {
        String input;
        do {
            System.out.print("\nChoose from the menu: ");
            input = scanner.nextLine();
        } while (!IsNumeric(input));
        return Integer.parseInt(input);
        /*try {
            in = Integer.parseInt(inputString);
        }
        catch (NumberFormatException e) {
            System.out.println("\nERROR: A number must be entered");
        }*/
    }

    /**
     * Checks wether a string is a number.
     * @param str the string
     * @return true if it is a number, false else
     */
    private static boolean IsNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            System.out.print("\nIncorrect input! A number must be entered.");
            return false;
        }
        return true;
    }

    /**
     * Waits until a key is pressed on the keyboard.
     * @param scanner a scanner instance
     */
    private static void GetKeyToContinue(Scanner scanner) {
        System.out.print("Press any key to go back to the menu...");
        scanner.nextLine();
    }
    //endregion
}
