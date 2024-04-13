package GameManagers;

import GameManagers.Commands.Commands;
import GameManagers.Commands.ICommand;

import java.util.HashMap;
import java.util.Scanner;

public class CommandController {

    private final static HashMap<String, ICommand> commands = new HashMap<>();
    static {
        commands.put("exit", (args) -> CommandController.getInput = false);
        commands.put("move", Commands::Move);
        commands.put("list", Commands::List);
        commands.put("use_item", Commands::UseItem);
        commands.put("activate_item", Commands::ActivateItem);
        commands.put("pick_up_item", Commands::PickUpItem);
        commands.put("drop_item", Commands::DropItem);
        commands.put("merge", Commands::Merge);
        commands.put("separate", Commands::Separate);
        commands.put("load", Commands::Load);
        commands.put("save", Commands::Save);
        commands.put("random", Commands::Random);
        commands.put("roundm", Commands::Roundm);
    }

    static boolean getInput = true;

    public static void GetInput() {

        Scanner scanner = new Scanner(System.in);

        while (getInput) {
            String[] cmd = scanner.nextLine().split(" ");

            ICommand command = commands.get(cmd[0]);
            if (command == null) {
                System.out.println("Error: Unknown command");
            }
            else {
                command.execute(cmd);
            }
        }
    }
}
