package GameManagers.Commands;

import Constants.GameConstants;
import Entities.*;
import Labyrinth.Room;
import Runnable.Main;

public class Commands {

    public static void Move(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: move <-s/-j> <room name>");
            return;
        }

        String option = args[1];
        String roomName = args[2];

        switch (option) {
            case "-s":
                break;
            case "-j":
                break;
            default:
                System.out.println("Invalid option: " + option);
                break;
        }
    }

    public static void List(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: list <-it/-in/-n/-m> [<entity>]");
            return;
        }

        String option = args[1];

        switch (option) {
            case "-it":
                break;
            case "-in":
                break;
            case "-n":
                if (args.length < 3) {
                    System.out.println("Usage: list -n <entity>");
                    return;
                }
                String entity = args[2];
                break;
            case "-m": {

                System.out.println("The rooms and their neighbours in the map:");
                for (Room room : Main.game.GetMap().GetRooms()) {
                    System.out.println("-> " + room.GetName() + " " + (room.IsGassed() ? "(gassed)" : ""));
                    for (Room neighbours : room.GetNeighbours()) {
                        System.out.println("\t-> " + neighbours.GetName() + " " + (neighbours.IsGassed() ? "(gassed)" : ""));
                    }
                }
                break;
            }
            case "-all": {

                System.out.println("The entities in the game:");
                for (Student student : Main.game.GetStudents()) {
                    System.out.println("->" + student.GetName() + ": Student");
                }
                for (Professor prof : Main.game.GetProfessors()) {
                    System.out.println("->" + prof.GetName() + ": Professor");
                }
                for (Janitor janitor : Main.game.GetJanitors()) {
                    System.out.println("->" + janitor.GetName() + ": Janitor");
                }
                break;
            }
            default:
                System.out.println("Invalid option: " + option);
                break;
        }
    }

    public static void UseItem(String[] args) {
        int slotNumber = validateSlotNumber(args);
        if (slotNumber == -1) {
            return;
        }

        //use
    }

    public static void ActivateItem(String[] args) {
        int slotNumber = validateSlotNumber(args);
        if (slotNumber == -1) {
            return;
        }

        //activate
    }

    public static void PickUpItem(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: pick_up_item <item name>");
            return;
        }
    }

    public static void DropItem(String[] args) {
        int slotNumber = validateSlotNumber(args);
        if (slotNumber == -1) {
            return;
        }

        //drop
    }

    public static void Merge(String[] args) {
        if (args.length == 1) {
            //random merge
        } else if (args.length == 3) {
            String room1 = args[1];
            String room2 = args[2];
            //manual merge
        } else {
            System.out.println("Usage: merge [<room name> <room name>]");
        }
    }

    public static void Separate(String[] args) {
        if (args.length == 1) {
            //random separation
        } else if (args.length == 2) {
            String room = args[1];
            //manual separation
        } else {
            System.out.println("Usage: separate [<room name>]");
        }
    }

    public static void Load(String[] args) {

    }

    public static void Save(String[] args) {

    }

    public static void Random(String[] args) {

    }

    public static void RM(String[] args) {
        /*if (args.length < 2) {
            System.out.println("Usage: rm < /-endr>");
            return;
        }*/

        if (args.length < 2) {
            System.out.println("Usage: rm < /-endr>");
            return;
        }

        String option = args[1];

        switch (option) {
            case "-it":
                break;
            case "-in":
                break;
            case "-n":
                if (args.length < 3) {
                    System.out.println("Usage: list -n <entity>");
                    return;
                }
                String entity = args[2];
                break;
            case "-m": {

                System.out.println("The rooms and their neighbours in the map:");
                for (Room room : Main.game.GetMap().GetRooms()) {
                    System.out.println("-> " + room.GetName() + " " + (room.IsGassed() ? "(gassed)" : ""));
                    for (Room neighbours : room.GetNeighbours()) {
                        System.out.println("\t-> " + neighbours.GetName() + " " + (neighbours.IsGassed() ? "(gassed)" : ""));
                    }
                }
                break;
            }
            case "-all": {

                System.out.println("The entities in the game:");
                for (Student student : Main.game.GetStudents()) {
                    System.out.println("->" + student.GetName() + ": Student");
                }
                for (Professor prof : Main.game.GetProfessors()) {
                    System.out.println("->" + prof.GetName() + ": Professor");
                }
                for (Janitor janitor : Main.game.GetJanitors()) {
                    System.out.println("->" + janitor.GetName() + ": Janitor");
                }
                break;
            }
            default:
                System.out.println("Invalid option: " + option);
                break;
        }
    }

    private static int validateSlotNumber(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <use/activate>_item <slot number>");
            return -1;
        }

        int slotNumber;
        try {
            slotNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid slot number. Please enter a number between 1 and " + GameConstants.InventoryMaxSize + ".");
            return -1;
        }

        if (slotNumber < 1 || slotNumber > GameConstants.InventoryMaxSize) {
            System.out.println("Invalid slot number. Please enter a number between 1 and " + GameConstants.InventoryMaxSize + ".");
            return -1;
        }

        return slotNumber;
    }
}
