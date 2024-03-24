package Skeleton;

import Entities.Professor;
import Entities.Student;
import GameManagers.Game;
import GameManagers.RoundManager;
import Items.*;
import Labyrinth.Map;
import Labyrinth.Room;

import java.lang.reflect.Method;
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
        testNames.put(1, "Transistor Activation");
        testNames.put(2, "Transistor Pairing");
        testNames.put(3, "Dropping Unpaired transistor");
        testNames.put(4, "Dropping the first Activated, paired transistor");
        testNames.put(5, "Dropping the second Activated, paired transistor");
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
        testNames.put(17, "Losing");
        testNames.put(18, "Item acquisition ");
        testNames.put(19, "Student item disposal ");
        testNames.put(20, "Professor item disposal ");
        testNames.put(21, "Cheese Activation");
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

            if(in > 0 && in < 35) {
                try {
                    Skeleton obj = new Skeleton();
                    Method declaredMethod = obj.getClass().getDeclaredMethod("Test_" + in);
                    declaredMethod.invoke(obj);
                    GetKeyToContinue(scanner);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else if (in == 0) {
                System.exit(0);
                break;
            }else {
                System.out.println("Choose a number from the menu!");
            }

            /*switch (in) {
                //region Norbee bing-bong


                case 0:
                    System.exit(0);
                    break;
                case 1:
                    Test_1();
                    break;
                case 2:
                    Test_2();
                    break;
                case 3:
                    Test_3();
                    break;
                case 4:
                    Test_4();
                    break;
                case 5:
                    Test_5();
                    break;
                //endregion
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
                    break;
                //endregion
                //region Norbe 2 return of the Monke

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
            }*/
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

    //region Norbe region

    //region Transistor
    /**
     * Tests whether the Transistor was activated successfully
     */
    private static void Test_1() {
        TestHead(1);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        student.PickUpItem(t1);

        // Test
        System.out.println("\nStarting test\n");

        student.ActivateItem(t1);

        boolean success = t1.GetActivation();
        TestPrint(success
                , "The transistor is activated"
                , "The transistor is not activated");
    }

    /**
     * Tests whether the Transistors were paired successfully
     */
    private static void Test_2() {
        TestHead(2);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        student.PickUpItem(t1);
        Transistor t2 = new Transistor();
        student.PickUpItem(t2);

        // Test
        System.out.println("\nStarting test\n");

        student.PairTransistors(t1, t2);

        boolean success = (t1.GetPair() == t2) && (t2.GetPair() == t1);
        TestPrint(success
                , "The transistors are successfully paired"
                , "The transistors aren't paired");
    }

    /**
     * Tests whether an Unpaired transistor can be dropped without problems
     */
    private static void Test_3() {
        TestHead(3);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        student.PickUpItem(t1);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.DropItem(t1);

        boolean success = !(student.GetInventory().contains(t1)) && room2.GetInventory().contains(t1) && t1.GetCurrentRoom() == null;
        TestPrint(success
                , "The unpaired transistor was successfully dropped"
                , "The transistor wasn't dropped successfully");
    }

    /**
     * Tests whether the first Activated, Paired transistor can be dropped successfully
     */
    private static void Test_4() {
        TestHead(4);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        student.PickUpItem(t1);
        Transistor t2 = new Transistor();
        student.PickUpItem(t2);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);
        student.PairTransistors(t1, t2);
        student.ActivateItem(t1);
        student.ActivateItem(t2);

        // Test
        System.out.println("\nStarting test\n");

        student.DropItem(t1);

        boolean success = room2.GetInventory().contains(t1) && t1.GetCurrentRoom() == room2 && t1.GetPair() == t2;
        TestPrint(success
                , "The first paired transistor was successfully dropped"
                , "The first paired transistor wasn't dropped successfully");
    }

    /**
     * Tests whether the second Activated, Paired transistor can be dropped successfully
     */
    private static void Test_5() {
        TestHead(5);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        student.PickUpItem(t1);
        Transistor t2 = new Transistor();
        student.PickUpItem(t2);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);
        student.PairTransistors(t1, t2);
        student.ActivateItem(t1);
        student.ActivateItem(t2);
        student.DropItem(t1);

        // Test
        System.out.println("\nStarting test\n");

        student.StepInto(room1);
        student.DropItem(t2);

        boolean success = t1.GetCurrentRoom().GetStudents().contains(student) && !t1.GetCurrentRoom().GetInventory().contains(t1) && !t2.GetCurrentRoom().GetInventory().contains(t2);
        TestPrint(success
                , "The second paired transistor was successfully dropped (student successfully teleported and transistors deleted from room)"
                , "The first paired transistor wasn't dropped successfully (student didn't move or transistors still exist)");
        if (!success) {
            System.out.println("problem");
            if (room1.GetStudents().contains(student)) {
                System.out.println("tp error");
            }
            if (t1.GetCurrentRoom().GetInventory().contains(t1) || t2.GetCurrentRoom().GetInventory().contains(t2)) {
                System.out.println("deletion error");
            }
        }
    }
    //endregion
    /**
     * Tests item acquisition
     */
    private static void Test_18() {
        TestHead(18);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);
        room2.AddItemToRoom(t1);

        // Test
        System.out.println("\nStarting test\n");

        student.PickUpItem(t1);

        boolean success = (student.GetInventory().contains(t1)) && !room2.GetInventory().contains(t1);
        TestPrint(success
                , "The item was picked up successfully"
                , "The item wasn't picked up successfully");
    }

    /**
     * Tests item dropping of Students
     */
    private static void Test_19() {
        TestHead(19);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Transistor t1 = new Transistor();
        student.PickUpItem(t1);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.DropItem(t1);

        boolean success = !(student.GetInventory().contains(t1)) && room2.GetInventory().contains(t1);
        TestPrint(success
                , "The student dropped the item successfully"
                , "The student didn't drop the item successfully");
    }

    /**
     * Tests item dropping of Professors
     */
    private static void Test_20() {
        TestHead(20);

        // Initializing
        Game game = new Game();
        Professor prof = new Professor(game); game.AddProfessor(prof);
        Transistor t1 = new Transistor();
        prof.PickUpItem(t1);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddProfessorToRoom(prof); prof.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        prof.DropItem(t1);

        boolean success = !(prof.GetInventory().contains(t1)) && room2.GetInventory().contains(t1);
        TestPrint(success
                , "The professor dropped the item successfully"
                , "The professor didn't drop the item successfully");
    }

    /**
     * Tests of cheese
     */
    private static void Test_21() {
        TestHead(21);

        // Initializing
        Game game = new Game();
        Student student = new Student(game); game.AddStudent(student);
        Cheese cheese = new Cheese();
        student.PickUpItem(cheese);
        Room mainHall = new Room(game), room1 = new Room(game), room2 = new Room(game);
        room2.AddNeighbour(room1); room1.AddNeighbour(room2);
        room2.AddStudentToRoom(student); student.SetCurrentRoom(room2);
        Map map = new Map(game);
        map.AddRoom(room1);
        map.AddRoom(room2);
        map.AddMainHall(mainHall);
        game.SetMap(map);

        // Test
        System.out.println("\nStarting test\n");

        student.ActivateItem(cheese);
        student.UseItem(cheese);

        boolean success = room2.IsGassed() && room2.GetRemainingRoundsGassed() == 2;
        TestPrint(success
                , "The cheese was used successfully"
                , "The cheese has failed");
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
     * Checks whether a string is a number.
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
     * the expected and the parameter texts based on success.
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
