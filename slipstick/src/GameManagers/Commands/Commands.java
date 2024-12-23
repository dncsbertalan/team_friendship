package GameManagers.Commands;

import Constants.GameConstants;
import Entities.*;
import GameManagers.CommandController;
import Items.Item;
import Items.Transistor;
import Labyrinth.Map;
import Labyrinth.Room;
import Runnable.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static GameManagers.CommandController.commands;
import static Runnable.Main.*;

public class Commands {
    private static Random random = new Random();

//region ==================== COMMAND METHODS ====================
    public static void Move(String[] args) {
        if (args.length != 3) {
            os.println("Usage: move <-s/-j/-p/entity name> <room name>");
            return;
        }

        String option_name = args[1];
        String roomName = args[2];

        Entity entityToMove;

        switch (option_name) {
            case "-s":
                entityToMove = Main.game.GetRoundManager().GetActiveStudent();
                if (entityToMove == null) {
                    os.println("Error: No active player.");
                    return;
                }
                break;
            case "-j":
                if (Main.game.GetRoundManager().GetActiveAIEntity() == null
                        || Main.game.GetRoundManager().GetActiveAIEntity().getClass() != Janitor.class) {
                    os.println("Error: No active janitor.");
                    return;
                }
                entityToMove = (Entity) Main.game.GetRoundManager().GetActiveAIEntity();
                break;
            case "-p":
                if (Main.game.GetRoundManager().GetActiveAIEntity() == null
                        || Main.game.GetRoundManager().GetActiveAIEntity().getClass() != Professor.class) {
                    os.println("Error: No active professor.");
                    return;
                }
                entityToMove = (Entity) Main.game.GetRoundManager().GetActiveAIEntity();
                break;
            default:    // searches by name
                entityToMove = GetEntityByName(option_name);
                break;
        }

        if (entityToMove == null) {
            os.println("Error: No existing entity with the name: " + option_name);
            return;
        }

        Room roomToMoveInto = Main.game.GetMap().GetRoomByName(roomName);
        if (roomToMoveInto == null) {
            os.println("Error: No existing room with the name: " + roomName);
            return;
        }

        boolean isNeighbour = false;
        for (Room room : entityToMove.GetCurrentRoom().GetNeighbours()) {
            if (room.equals(roomToMoveInto)) {
                isNeighbour = true;
                break;
            }
        }
        //os.println("Error: " + entityToMove.GetName() + " has no neighbour with the name: " + roomName);

        if (isNeighbour) {
            os.println("Moving " + entityToMove.GetName() + " from " + entityToMove.GetCurrentRoom().GetName() + " to " + roomName);
            entityToMove.StepInto(roomToMoveInto);
        } else {
            os.println("Moving " + entityToMove.GetName() + " to " + roomName + " was unsuccessful");
        }
    }

    public static void List(String[] args) {
        if (args.length < 2) {
            os.println("Usage: list <-it/-in/-n/-m/-all> [<entity>]");
            return;
        }

        String option = args[1];

        switch (option) {
            case "-it": {
                if (args.length < 3) {
                    os.println("Usage: list -it <entity>");
                    return;
                }
                String name = args[2];
                Entity entity = GetEntityByName(name);

                if (entity == null) {
                    os.println("Error: No existing entity with the name: " + name);
                    return;
                }

                os.println("Pickupable items in " + entity.GetName() + "'s room: " + (entity.GetCurrentRoom().GetInventory().isEmpty() ? "(empty)" : ""));
                for (Item item : entity.GetCurrentRoom().GetInventory()) {
                    os.println("\t-> " + item.GetName());
                }

                break;
            }
            case "-in": {
                if (args.length < 3) {
                    os.println("Usage: list -in <entity>");
                    return;
                }

                String name = args[2];
                Entity entity = GetEntityByName(name);

                if (entity == null) {
                    os.println("Error: No existing entity with the name: " + name);
                    return;
                }

                os.println(entity.GetName() + "'s inventory: " + (entity.GetInventory().isEmpty() ? "(empty)" : ""));
                for (Item item : entity.GetInventory()) {
                    os.println("\t-> " + item.GetName());
                }

                break;
            }
            case "-n": {
                if (args.length < 3) {
                    os.println("Usage: list -n <entity>");
                    return;
                }
                Entity entity = GetEntityByName(args[2]);
                if (entity == null) {
                    os.println("No entity with the name " + args[2]);
                    return;
                }
                os.println("The neighbouring rooms of " + args[2]);
                for (Room room : entity.GetCurrentRoom().GetNeighbours()) {
                    os.println("\t-> " + room.GetName() + (room.IsGassed() ? " (gassed)" : "") + (room.IsSticky() ? " (sticky)" : ""));
                }
                break;
            }
            case "-m": {

                os.println("The rooms and their neighbours in the map:");
                for (Room room : Main.game.GetMap().GetRooms()) {
                    os.println("-> " + room.GetName() + (room.IsGassed() ? " (gassed)" : "") + (room.IsSticky() ? " (sticky)" : ""));
                    for (Room neighbours : room.GetNeighbours()) {
                        os.println("\t-> " + neighbours.GetName() + (neighbours.IsGassed() ? " (gassed)" : "") + (room.IsSticky() ? " (sticky)" : ""));
                    }
                    for (Item item : room.GetInventory()) {
                        os.println("\t-> " + item.GetName());
                    }
                    for (Item item : room.GetUnpickupableItems()) {
                        os.println("\t-> " + item.GetName());
                    }
                }
                break;
            }
            case "-all": {

                os.println("The entities in the game:");
                for (Student student : Main.game.GetStudents()) {
                    os.println("->" + student.GetName() + ": Student");
                }
                for (Professor prof : Main.game.GetProfessors()) {
                    os.println("->" + prof.GetName() + ": Professor");
                }
                for (Janitor janitor : Main.game.GetJanitors()) {
                    os.println("->" + janitor.GetName() + ": Janitor");
                }
                break;
            }
            default:
                os.println("Invalid option: " + option);
                break;
        }
    }

    public static void UseItem(String[] args) {

        if (args.length < 2) {
            os.println("Usage: use_item <item name>");
            return;
        }

        String itemName = args[1];

        Student entity = Main.game.GetRoundManager().GetActiveStudent();

        if(entity == null){
            os.println("Error: No active player.");
            return;
        }

        Item item = GetItemFromEntityByName(entity, itemName);

        if(item == null){
            os.println("Error: Entity " + entity.GetName() + " does not own item " + itemName);
            return;
        }

        os.println(entity.GetName() + " used " + itemName);
        entity.UseItem(item);
    }

    public static void ActivateItem(String[] args) {
        if (args.length < 2) {
            os.println("Usage: use_item <item name>");
            return;
        }

        String itemName = args[1];

        Student entity = Main.game.GetRoundManager().GetActiveStudent();

        if(entity == null){
            os.println("Error: No active player.");
            return;
        }

        Item item = GetItemFromEntityByName(entity, itemName);

        if(item == null){
            os.println("Error: Entity " + entity.GetName() + " does not own item " + itemName);
            return;
        }

        os.println(entity.GetName() + " activated " + itemName);
        entity.ActivateItem(item);
    }

    public static void PickUpItem(String[] args) {
        if (args.length != 2) {
            os.println("Usage: pick_up_item <item name>");
            return;
        }

        Student student = game.GetRoundManager().GetActiveStudent();
        if (student == null) {
            os.println("Error: No active player.");
            return;
        }

        String itemName = args[1];

        Item item = GetItemFromRoomByName(student.GetCurrentRoom(), itemName);
        if (item != null ) {
            student.PickUpItem(item);
            os.println(student.GetName() + " picked up " + item.GetName());
        } else {
            os.println(student.GetName() + " could not pick up " + itemName);
        }
    }

    public static void DropItem(String[] args) {
        Room OriginalRoom;
        if (args.length != 2) {
            os.println("Usage: drop_item <item name>");
            return;
        }

        Student student = game.GetRoundManager().GetActiveStudent();
        if (student == null) {
            os.println("Error: No active player.");
            return;
        }

        OriginalRoom = student.GetCurrentRoom();
        Item item = GetItemFromEntityByName(student, args[1]);
        student.SetSelectedItem(item);
        if (item != null) {
            if(item.getClass() == Transistor.class){
                student.DropItem(item);
            }else {
                student.DropSelectedItem();
            }
            os.println(student.GetName() + " dropped " + item.GetName());
            if(student.GetCurrentRoom() != OriginalRoom)
                os.println("Teleported " + student.GetName() + " to " + student.GetCurrentRoom().GetName() +" from "+ OriginalRoom.GetName());
        } else {
            os.println(student.GetName() + " does not have " + args[1]);
        }
    }

    public static void Merge(String[] args) {
        Map map = game.GetMap();
        Room merged = null;

        if (args.length == 1) {
            // Random merge
            Room firstRandomlySelectedRoom = null;
            Room secondRandomlySelectedRoom = null;
            do {
                firstRandomlySelectedRoom = RandomlySelectRoomFromMap(map);
                secondRandomlySelectedRoom = RandomlySelectRoomFromMap(map);
            } while (firstRandomlySelectedRoom.equals(secondRandomlySelectedRoom));

            merged = map.MergeRooms(firstRandomlySelectedRoom, secondRandomlySelectedRoom);
            if (merged != null) {
                os.println(firstRandomlySelectedRoom.GetName() + " and " +
                        secondRandomlySelectedRoom.GetName() + " merged into " + merged.GetName());
            } else {
                os.println("Merge of " + firstRandomlySelectedRoom.GetName() + " and " +
                        secondRandomlySelectedRoom.GetName() + " not successful");
            }
        } else if (args.length == 3) {
            // Manual merge
            String room1name = args[1];
            String room2name = args[2];

            Room r1 = map.GetRoomByName(room1name);
            Room r2 = map.GetRoomByName(room2name);

            if (RoomIsValidForMergeOrDivision(map, r1) && RoomIsValidForMergeOrDivision(map, r2)) {
                merged = map.MergeRooms(r1, r2);
                if (merged != null) {
                    os.println(room1name + " and " + room2name + " merged into " + merged.GetName());
                } else {
                    os.println("Merge of " + room1name + " and " + room2name + " not successful");
                }
            }
        } else {
            os.println("Usage: merge [<room name> <room name>]");
        }
    }

    public static void Separate(String[] args) {
        Map map = game.GetMap();
        Room separated = null;

        if (args.length == 1) {
            // Random separation
            Room randomlySelectedRoom = RandomlySelectRoomFromMap(map);

            separated = map.SeparateRooms(randomlySelectedRoom);
            if (separated != null) {
                os.println(randomlySelectedRoom.GetName() + " separated into " +
                        randomlySelectedRoom.GetName() + " and " + separated.GetName());
            } else {
                os.println("Separation of " + randomlySelectedRoom.GetName() + " not successful");
            }
        } else if (args.length == 2) {
            // Manual separation
            String roomName = args[1];
            Room r = map.GetRoomByName(roomName);

            if (RoomIsValidForMergeOrDivision(map, r)) {
                separated = map.SeparateRooms(r);
                if (separated != null) {
                    os.println(roomName + " separated into " + roomName + " and " + separated.GetName());
                } else {
                    os.println("Separation of " + roomName + " not successful");
                }
            }
        } else {
            os.println("Usage: separate [<room name>]");
        }
    }

    public static void Load(String[] args) {
        if (args.length == 2) {
            try {
                game.LoadGame(args[1]);
            } catch (FileNotFoundException e) {
                os.println("Error: " + args[1] + " not found.");
                return;
            }
            os.println("Successfully loaded " + args[1]);
        } else {
            os.println("Usage: load <file name>");
        }
    }

    public static void Save(String[] args) {
        if (args.length == 2) {
            game.SaveGame(args[1]);
            os.println("Successfully saved the game to " + args[1]);
        } else {
            os.println("Usage: save <file name>");
        }
    }

    public static void Random(String[] args) {
        if(!game.IsPreGame()){
            os.println("The game has already started.");
        }
            if(args.length == 2){
                int i = -1;
                if(Objects.equals(args[1], "true")) i = 1;
                if(Objects.equals(args[1], "false")) i = 0;
                if(i == -1){
                    os.println("Incorrect boolean");
                    return;
                }
                game.InitRandom(i);
                os.println("Game's random state is now " + args[1]);
            }else{
                os.println("Usage: random <boolean>");
            }
    }

    public static void Input(String[] args) {
        if (args.length < 2) {
            os.println("Usage: input <file name>");
            return;
        }

        //String basePath = "tests/input/";

        Scanner scanner;
        try {
            scanner = new Scanner(new File(/*basePath + */args[1]));
        } catch (FileNotFoundException e) {
            os.println("Error: " + args[1] + " not found.");
            return;
        }
        CommandController.DefaultIS = false;
        while (!CommandController.DefaultIS) {
            String[] cmd = scanner.nextLine().split(" ");

            ICommand command = commands.get(cmd[0]);
            if (command == null) {
                os.println("Error: Unknown command");
            }
            else {
                command.execute(cmd);
            }
        }
    }

    public static void Output(String[] args) {
        if (args.length < 2) {
            os.println("Usage: input <file name>");
            return;
        }
        try {
            os = new PrintStream(args[1]);
        } catch (FileNotFoundException e) {
            os = System.out;
            os.println("Error: " + args[1] + " not found.");
        }
    }

    public static void Roundm(String[] args) {
        if (args.length < 2) {
            os.println("Informations of the current round:");
            os.println("-> Round number: " + Main.game.GetRoundManager().GetCurrentRound());
            os.println("-> Remaining rounds left: " + (GameConstants.MaxRounds - Main.game.GetRoundManager().GetCurrentRound()));
            String _name = "hupi", _class = "none";
            if (Main.game.GetRoundManager().GetActiveStudent() != null) {
                _name = Main.game.GetRoundManager().GetActiveStudent().GetName();
                _class = "Student";
            }
            if (Main.game.GetRoundManager().GetActiveAIEntity() != null) {
                Entity entity = (Entity)Main.game.GetRoundManager().GetActiveAIEntity();
                _name = entity.GetName();
                if (entity.getClass() == Professor.class) _class = "Professor";
                else if (entity.getClass() == Janitor.class) _class = "Janitor";
            }
            os.println("-> Active entity: " + _name + " (" + _class + ")");
            return;
        }

        String option = args[1];

        switch (option) {
            case "-endt":
                os.println("Current turn ended");
                Main.game.GetRoundManager().EndTurn();
                break;
            case "-endr":
                os.println("Current round ended");
                Main.game.GetRoundManager().EndOfRound();   // TODO: safe check to not skip 2 rounds
                Main.game.GetRoundManager().EndTurn();
                break;

            default:
                os.println("Invalid option: " + option);
                break;
        }
    }

    public static void State(String[] args) {
        if (args.length < 2) {
            os.println("Usage: state <-g/-r/-e> [<entity>]");
            return;
        }

        String option = args[1];

        switch (option) {
            case "-r": {
                if (args.length < 3) {
                    os.println("Usage: state -r <entity>");
                    return;
                }

                Entity entity = GetEntityByName(args[2]);
                if (entity == null) {
                    os.println("No entity with the name " + args[2]);
                    return;
                }
                Room entityRoom = entity.GetCurrentRoom();
                os.print(entityRoom.GetName() + " state:");
                if (entityRoom.IsGassed()) {
                    os.print(" gassed");
                }
                if (entityRoom.IsSticky()) {
                    os.print(" sticky");
                }
                if (entityRoom.IsCleaned()) {
                    os.print(" cleaned");
                }
                if (!entityRoom.IsGassed() && !entityRoom.IsSticky() && !entityRoom.IsCleaned()) {
                    os.print(" normal");
                }
                os.print("\n");

                break;
            }
            case "-g": {
                if (args.length > 2) {
                    os.println("Usage: state -g");
                    return;
                }

                if (!game.IsEnded() && game.IsPreGame()) {
                    os.println(("The current game state: pre-game"));
                    break;
                }

                if (!game.IsEnded() && game.IsLastPhase()) {
                    os.println("The current game state: last phase");
                    break;
                }
                if (game.IsEnded() && game.GetWin()) {
                    os.println("The current game state: won");
                    break;
                }
                if (game.IsEnded() && !game.GetWin()) {
                    os.println("The current game state: lost");
                    break;
                }
                os.println("The current game state: normal state");
                break;
            }
            case "-e": {
                if (args.length < 3) {
                    os.println("Usage: state -e <entity>");
                    return;
                }

                Entity entity = GetEntityByName(args[2]);
                if (entity == null) {
                    os.println("No entity with name " + args[2]);
                    return;
                }

                if (entity.IsParalysed()) {
                    os.println(entity.GetName() + " is paralyzed");
                    return;
                }
                if (entity.getClass() == Student.class && ((Student) entity).IsDead()) {
                    os.println(entity.GetName() + " is dead");
                    return;
                }
                os.println(entity.GetName() + " is alive");
                break;
            }
            default:
                os.println("Invalid option: " + option);
                break;
        }
    }

    public static void Pair(String[] args) {
        if(args.length==3 || args.length==4){
            Student student;
            String transistorString1;
            String transistorString2;
            if(args.length==3) {
                transistorString1= args[1];
                 transistorString2 = args[2];
                student = Main.game.GetRoundManager().GetActiveStudent();
                } else{
                     transistorString1 = args[3];
                     transistorString2 = args[4];
                    student = (Student) GetEntityByName(args[0]);
                }
                if(student == null){
                    os.println("Error: No active player.");
                    return;
                }

                Transistor transistor1 = (Transistor)GetItemFromEntityByName(student, transistorString1);
                Transistor transistor2 = (Transistor)GetItemFromEntityByName(student, transistorString2);

                if(transistor1 == null){
                    os.println("Error: Entity " + student.GetName() + " does not own item " + transistorString1);
                    return;
                }
                if(transistor2 == null){
                    os.println("Error: Entity " + student.GetName() + " does not own item " + transistorString2);
                    return;
                }

                os.println(transistorString1 + " and "+ transistorString2 +" were paired");
                student.PairTransistors(transistor1,transistor2);


        }else{
            os.println("Usage: pair <transistor 1> <transistor 2> / pair <student> <transistor 1> <transistor 2>");
        }


    }

    public static void Exit(String[] args) {
        if (CommandController.DefaultIS) {
            CommandController.GetInput = false;
            return;
        }
        os = System.out;
        CommandController.DefaultIS = true;
    }
//endregion

//region ==================== HELPER METHODS ====================
    private static int ValidateSlotNumber(String[] args) {
        if (args.length != 2) {
            os.println("Usage: <use/activate>_item <slot number>");
            return -1;
        }

        int slotNumber;
        try {
            slotNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            os.println("Invalid slot number. Please enter a number between 1 and " + GameConstants.InventoryMaxSize + ".");
            return -1;
        }

        if (slotNumber < 1 || slotNumber > GameConstants.InventoryMaxSize) {
            os.println("Invalid slot number. Please enter a number between 1 and " + GameConstants.InventoryMaxSize + ".");
            return -1;
        }

        return slotNumber;
    }

    private static Entity GetEntityByName(String name) {
        for (Entity entity : Main.game.GetStudents()) {
            if (entity.GetName().equals(name)) return entity;
        }
        for (Entity entity : Main.game.GetProfessors()) {
            if (entity.GetName().equals(name)) return entity;
        }
        for (Entity entity : Main.game.GetJanitors()) {
            if (entity.GetName().equals(name)) return entity;
        }
        return null;
    }

    private static Room RandomlySelectRoomFromMap(Map map) {
        int randomRoomNumber;
        Room randomlySelectedRoom = null;

        do {
            randomRoomNumber = random.nextInt(0, map.GetRooms().size());
            randomlySelectedRoom = map.GetRooms().get(randomRoomNumber);
        } while (!RoomIsValidForMergeOrDivision(map, randomlySelectedRoom));

        return randomlySelectedRoom;
    }

    private static boolean RoomIsValidForMergeOrDivision(Map map, Room room) {
        return !room.equals(map.GetMainHall()) || !room.equals(map.GetTeachersLounge()) ||
                !room.equals(map.GetJanitorsRoom()) || !map.IsWinningRoom(room);
    }

    private static Item GetItemFromEntityByName(Entity entity, String itemName){
        for(Item itemIter : entity.GetInventory()){
            if(itemIter.GetName().equals(itemName)){
                return itemIter;
            }
        }
        return null;
    }


    private static Item GetItemFromRoomByName(Room room, String itemName) {
        for (Item item : room.GetInventory()) {
            if (item.GetName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
//endregion
}