package GameManagers.Commands;

import Constants.GameConstants;
import Entities.*;
import Labyrinth.Room;
import Runnable.Main;
import static Runnable.Main.os;

public class Commands {

    public static void Move(String[] args) {
        if (args.length != 3) {
            os.println("Usage: move <-s/-j> <room name>");
            return;
        }

        String option = args[1];
        String roomName = args[2];

        switch (option) {
            case "-s":
                // TODO TEMPORARY
                Student student = Main.game.GetRoundManager().GetActiveStudent(); // TODO null check
                os.println("Moving " + student.GetName() + " to Room2"); // todo faszom temp
                break;
            case "-j":
                break;
            default:
                os.println("Invalid option: " + option);
                break;
        }
    }

    public static void List(String[] args) {
        if (args.length < 2) {
            os.println("Usage: list <-it/-in/-n/-m> [<entity>]");
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
                    os.println("Usage: list -n <entity>");
                    return;
                }
                String entity = args[2];
                break;
            case "-m": {

                os.println("The rooms and their neighbours in the map:");
                for (Room room : Main.game.GetMap().GetRooms()) {
                    os.println("-> " + room.GetName() + " " + (room.IsGassed() ? "(gassed)" : ""));
                    for (Room neighbours : room.GetNeighbours()) {
                        os.println("\t-> " + neighbours.GetName() + " " + (neighbours.IsGassed() ? "(gassed)" : ""));
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
            os.println("Usage: pick_up_item <item name>");
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
            os.println("Usage: merge [<room name> <room name>]");
        }
    }

    public static void Separate(String[] args) {
        if (args.length == 1) {
            //random separation
        } else if (args.length == 2) {
            String room = args[1];
            //manual separation
        } else {
            os.println("Usage: separate [<room name>]");
        }
    }

    public static void Load(String[] args) {

    }

    public static void Save(String[] args) {

    }

    public static void Random(String[] args) {

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
                os.println("pipa");
                Main.game.GetRoundManager().EndTurn();
                break;
            case "-endr":
                os.println("pipa_ended");
                Main.game.GetRoundManager().EndOfRound();   // TODO: safe check to not skip 2 rounds
                Main.game.GetRoundManager().EndTurn();
                break;

            default:
                os.println("Invalid option: " + option);
                break;
        }
    }

    private static int validateSlotNumber(String[] args) {
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
}
