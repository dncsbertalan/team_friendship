package Skeleton;

import Entities.Professor;
import Entities.Student;
import GameManagers.Game;
import GameManagers.RoundManager;
import Items.FFP2Mask;
import Items.SlipStick;
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
        testNames.put(1, "empty");
        testNames.put(2, "empty");
        testNames.put(3, "empty");
        testNames.put(4, "empty");
        testNames.put(5, "empty");
        testNames.put(6, "empty");
        testNames.put(7, "empty");
        testNames.put(8, "empty");
        testNames.put(9, "empty");
        testNames.put(10, "Successful student movement between rooms");
        testNames.put(11, "Successful professor movement between rooms");
        testNames.put(12, "Unsuccessful student movement between rooms");
        testNames.put(13, "Unsuccessful professor movement between rooms");
        testNames.put(14, "Slipstick acquisition");
        testNames.put(15, "Slipstick disposal");
        testNames.put(16, "Winning");
        testNames.put(17, "empty");
        testNames.put(18, "empty");
        testNames.put(19, "empty");
        testNames.put(20, "empty");
        testNames.put(21, "empty");
        testNames.put(22, "empty");
        testNames.put(23, "empty");
        testNames.put(24, "empty");
        testNames.put(25, "empty");
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
                    break;
                case 1:
                    System.out.print("l\n");
                    break;
                case 2:
                    break;
                //region Bene
                case 10:
                    Test_10();
                    GetKeyToContinue(scanner);
                    break;
                case 11:
                    Test_11();
                    GetKeyToContinue(scanner);
                    break;
                case 12:
                    Test_12();
                    GetKeyToContinue(scanner);
                    break;
                case 13:
                    Test_13();
                    GetKeyToContinue(scanner);
                    break;
                case 14:
                    Test_14();
                    GetKeyToContinue(scanner);
                    break;
                case 15:
                    Test_15();
                    GetKeyToContinue(scanner);
                    break;
                case 16:
                    Test_16();
                    GetKeyToContinue(scanner);
                    break;
                case 17:
                    Test_17();
                    GetKeyToContinue(scanner);
                //endregion
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
        TestHead(26);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        FFP2Mask ffp2Mask = new FFP2Mask();
            student.PickUpItem(ffp2Mask);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
            room2.AddNeighbour(room1); room1.AddNeighbour(room2);
            room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
            room1.SetToxicity();
        Map map = new Map(game);
            map.AddRoom(room1);
            map.AddRoom(room2);
            map.AddMainHall(mainHall);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.StepInto(room1);

        boolean success = student.GetCurrentRoom() == room1;
        TestPrint(success
                , "Student is in the gas room and is protected against gas"
                , "Student is not in the gas room");
    }

    /**
     * Student entering a gassed room (without protection)
     */
    private static void Test_27() {
        TestHead(27);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Room mainHall = new Room(game);
        Room room1 = new Room(game);
        Room room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        room1.SetToxicity();
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.StepInto(room1);

        boolean success = student.GetCurrentRoom() == mainHall && student.CheckRoundMiss();
        TestPrint(success
                , "Student is in the main hall and misses round(s)"
                , "Student is not in the main hall");
    }

    /**
     * Professor entering a gassed room (with protection)
     */
    private static void Test_28() {
        TestHead(28);

        // Initializing
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
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        professor.StepInto(room1);

        boolean success = professor.GetCurrentRoom() == room1;
        TestPrint(success
                , "Professor is in the gas room and is protected against gas"
                , "Professor is not in the gas room");
    }

    /**
     * Professor entering a gassed room (without protection)
     */
    private static void Test_29() {
        TestHead(29);

        // Initializing
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
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        professor.StepInto(room1);

        boolean success = professor.GetCurrentRoom() == teachersLounge && professor.CheckRoundMiss();
        TestPrint(success
                , "Professor is in the teachers' lounge and misses round(s)"
                , "Professor is not in the teachers' lounge");
    }
    //endregion

    //region STUDENT-PROF ENCOUNTER
    /**
     * Student entering a room with a professor (with protection)
     */
    private static void Test_30() {
        TestHead(30);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Professor professor = new Professor(game); game.AddProfessor(professor);
        TVSZ tvsz = new TVSZ();
            student.PickUpItem(tvsz);
        Room mainHall = new Room(game), teachersLounge = new Room(game), room1 = new Room(game), room2 = new Room(game);
            room2.AddNeighbour(room1);
            room1.AddNeighbour(room1);
        room1.AddStudentToRoom(student); student.SetCurrentRoom(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        Map map = new Map(game);
            map.AddRoom(room1);
            map.AddRoom(room2);
            map.AddMainHall(mainHall);
            map.AddTeachersLounge(teachersLounge);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.StepInto(room2);

        boolean success = student.GetCurrentRoom() == room2 && !student.IsDead() && professor.GetCurrentRoom() == teachersLounge;
        TestPrint(success
                , "Student survived teacher encounter"
                , "Student did not survive teacher encounter but should have");
    }

    /**
     * Student entering a room with a professor (without protection)
     */
    private static void Test_31() {
        TestHead(31);

        // Initializing
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
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.StepInto(room2);

        boolean success = student.GetCurrentRoom() == room2 && student.IsDead() && professor.GetCurrentRoom() == room2;
        TestPrint(success
                , "Student did not survive teacher encounter"
                , "Student survived teacher encounter but shouldn't have");
    }

    /**
     * Professor entering a room with a student (with protection)
     */
    private static void Test_32() {
        TestHead(32);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Professor professor = new Professor(game); game.AddProfessor(professor);
        TVSZ tvsz = new TVSZ();
            student.PickUpItem(tvsz);
        Room mainHall = new Room(game), teachersLounge = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1);
        room1.AddNeighbour(room1);
        room1.AddStudentToRoom(student); student.SetCurrentRoom(room1);
        room2.AddProfessorToRoom(professor); professor.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        map.AddTeachersLounge(teachersLounge);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        professor.StepInto(room1);

        boolean success = student.GetCurrentRoom() == room1 && !student.IsDead() && professor.GetCurrentRoom() == teachersLounge;
        TestPrint(success
                , "Student survived teacher encounter"
                , "Student did not survive teacher encounter but should have");
    }

    /**
     * Professor entering a room with a student (without protection)
     */
    private static void Test_33() {
        TestHead(33);

        // Initializing
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
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        professor.StepInto(room1);

        boolean success = student.GetCurrentRoom() == room1 && student.IsDead() && professor.GetCurrentRoom() == room1;
        TestPrint(success
                , "Student did not survive teacher encounter"
                , "Student survived teacher encounter but shouldn't have");
    }
    //endregion
    //endregion

    //region Bene regiojet

    public static void Test_10() {
        FancyPrint("Test #10");
        System.out.println(testNames.get(10));

        //inits
        Game g = new Game();
        Room r1 = new Room(g);
        Room r2 = new Room(g);
        r2.AddNeighbour(r1);
        r1.AddNeighbour(r2);
        Student student = new Student(g);
        r1.AddStudentToRoom(student);
        Map map = new Map(g);
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        //test
        student.StepInto(r2);
        boolean success = student.GetCurrentRoom() == r2;
        TestPrint(success
                , "Student's current room changed"
                , "Student's current room not changed");
    }

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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);

        //test
        professor.StepInto(r2);
        boolean success = professor.GetCurrentRoom() == r2;
        TestPrint(success
                , "Professor's current room changed"
                , "Professor's current room not changed");
    }

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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        //test
        student.StepInto(r2);
        boolean success = student.GetCurrentRoom() == r1;
        TestPrint(success
                , "Student's current room not changed"
                , "Student's current room changed");
    }

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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);

        //test
        professor.StepInto(r2);
        boolean success = professor.GetCurrentRoom() == r1;
        TestPrint(success
                , "Professor's current room not changed"
                , "Professor's current room changed");
    }

    public static void Test_14(){
        FancyPrint(("Test #14"));
        System.out.println(testNames.get(14));

        //inits
        SlipStick slip = new SlipStick();
        Game game = new Game();
        Student student = new Student(game);
        game.AddStudent(student);
        student.PickUpItem(slip);

        //test
        boolean success = game.IsLastPhase();
        TestPrint(success
                , "Last phase activated"
                , "Last phase not activated");
    }

    public static void Test_15(){
        FancyPrint("Test #15\n");
        System.out.println(testNames.get(15));

        //inits
        SlipStick slip = new SlipStick();
        Game game = new Game();
        Room room = new Room(game);
        Student student = new Student(game);
        room.AddStudentToRoom(student);
        game.AddStudent(student);
        student.PickUpItem(slip);

        //test
        student.SelectItem(slip);
        student.DropSelectedItem();
        boolean success = !game.IsLastPhase();
        TestPrint(success
                , "Last phase deactivated "
                , "Last phase not deactivated");
    }

    public static void Test_16() {
        FancyPrint("Test #16\n");
        System.out.println(testNames.get(16));

        //init
        Game game = new Game();
        Room r1 = new Room(game);
        Room r2 = new Room(game);
        r2.AddNeighbour(r1);
        Student student = new Student(game);
        r1.AddStudentToRoom(student);
        Map map = new Map(game);
        game.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        map.AddWinningRoom(r2);
        SlipStick slip = new SlipStick();
        student.PickUpItem(slip);

        //test
        student.StepInto(r2);
        boolean success = game.IsEnded();
        TestPrint(success
                , "Game engded with victory"
                , "Game not over");
    }

    public static void Test_17() {
        FancyPrint("Test #17");
        System.out.println(testNames.get(17));

        //init
        Game game = new Game();
        RoundManager rm = new RoundManager(game);
        game.SetRoundManager(rm);

        //test
        for(int i = 0; i<Constants.GameConstants.MaxRounds; i++) {
            rm.NextRound();
        }
        boolean success = !game.IsEnded();
        TestPrint(success
                , "Game ended with lose"
                , "Game not over");
    }
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
        System.out.print("\nPress Enter to go back to the menu...");
        scanner.nextLine();
    }

    /**
     * Prints a pretty box with 'TEST SUCCESFUL/TEST UNSUCCESFUL',
     * the expexted and the parameter texts based on success.
     * @param success whether the text was successful
     * @param expected the expected outcome, also the text when the test is successful
     * @param unsuccessfulText the text when the test is not successful
     */
    private static void TestPrint(boolean success, String expected, String unsuccessfulText) {
        String successText = success ? "TEST SUCCESSFUL!" : "TEST UNSUCCESSFUL!";
        String output = success ? expected : unsuccessfulText;
        int len = Math.max(output.length(), successText.length());
        len = Math.max(len, expected.length());

        System.out.print("\n");
        for (int i = 0; i < len + 2 + 2 + 2; i++) {
            System.out.print("#");
        }
        System.out.print("\n#  ");
        System.out.print(successText);
        for (int i = 0; i < len - successText.length(); i++) {
            System.out.print(" ");
        }
        System.out.print("  #");

        System.out.print("\n#  ");
        for (int i = 0; i < len; i++) {
            System.out.print(" ");
        }
        System.out.print("  #");

        System.out.print("\n#  ");
        System.out.print("Expected output:"); // 16 characters
        for (int i = 0; i < len - 16; i++) {
            System.out.print(" ");
        }
        System.out.print("  #");

        System.out.print("\n#  ");
        System.out.print(expected);
        for (int i = 0; i < len - expected.length(); i++) {
            System.out.print(" ");
        }
        System.out.print("  #");

        System.out.print("\n#  ");
        System.out.print("Test output:"); // 12 characters
        for (int i = 0; i < len - 12; i++) {
            System.out.print(" ");
        }
        System.out.print("  #");

        System.out.print("\n#  ");
        System.out.print(output);
        for (int i = 0; i < len - output.length(); i++) {
            System.out.print(" ");
        }
        System.out.print("  #\n");

        for (int i = 0; i < len + 2 + 2 + 2; i++) {
            System.out.print("#");
        }
        System.out.print("\n");
    }

    /**
     * Prints the header of a test.
     * @param testNum the test's number
     */
    private static void TestHead(int testNum) {
        FancyPrint("Test #" + testNum);
        System.out.println(testNames.get(testNum));
        System.out.println("\nInitializing test\n");
    }
    //endregion
}
