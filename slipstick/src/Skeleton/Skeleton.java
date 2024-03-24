package Skeleton;

import Entities.Professor;
import Entities.Student;
import GameManagers.Game;
import Items.FFP2Mask;
import Items.SlipStick;
import Items.TVSZ;
import Labyrinth.Map;
import Labyrinth.Room;
import org.junit.jupiter.api.DisplayName;

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
        game.SetMap(map);

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
        game.SetMap(map);

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
        game.SetMap(map);
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
        game.SetMap(map);

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
        game.SetMap(map);
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
        game.SetMap(map);
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
        game.SetMap(map);
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
        game.SetMap(map);
    }
    //endregion
    //endregion

    //Bene regiojet
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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        //test
        student.StepInto(r2);
        if(student.GetCurrentRoom()==r2) {
            System.out.println("Test #10 passed student's current room changed");
        }else{
            System.out.println("Test #10 failed student's current room not changed");

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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);

        //test
        professor.StepInto(r2);
        if(professor.GetCurrentRoom()==r2) {
            System.out.println("Test #11 passed professor's current room changed");
        }else{
            System.out.println("Test #11 failed professor's current room not changed");
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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);
        //test
        student.StepInto(r2);
        if(student.GetCurrentRoom()==r1) {
            System.out.println("Test #12 passed student's current room not changed");
        }else{
            System.out.println("Test #12 failed student's current room changed");
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
        g.SetMap(map);
        map.AddRoom(r1);
        map.AddRoom(r2);

        //test
        professor.StepInto(r2);
        if(professor.GetCurrentRoom()==r1) {
            System.out.println("Test #13 passed professor's current room not changed");
        }else{
            System.out.println("Test #13 failed professor's current room changed");
        }
    }

    @DisplayName("Slipstick acquisition")
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
        if(game.IsLastPhase()) {
            System.out.println("Test #14 passed last phase activated");
        }else{
            System.out.println("Test #14 failed last phase not activated");
        }
    }

    @DisplayName("Slipstick drop")
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
        if(!game.IsLastPhase()) {
            System.out.println("Test #15 passed last phase deactivated ");
        }else{
            System.out.println("Test #15 failed last phase not deactivated");
        }
    }
    @DisplayName("Slipstick drop")
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
        if(game.IsEnded()){
            System.out.println("Test #16 Passed victory");
        }else{
            System.out.println("Test #16 Failed game not over");
        }
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
        System.out.print("Press any key to go back to the menu...");
        scanner.nextLine();
    }
    //endregion
}
