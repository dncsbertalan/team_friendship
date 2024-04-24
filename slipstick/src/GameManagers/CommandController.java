package GameManagers;

import GameManagers.Commands.Commands;
import GameManagers.Commands.ICommand;

import java.util.HashMap;
import java.util.Scanner;

import static Runnable.Main.os;

public class CommandController {

    public final static HashMap<String, ICommand> commands = new HashMap<>();
    static {
        commands.put("exit", Commands::Exit);
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

        commands.put("input", Commands::Input);
        commands.put("output", Commands::Output);

        commands.put("state", Commands::State);
        commands.put("pair", Commands::Pair);
    }

    public static boolean GetInput = true;
    public static boolean DefaultIS = true;

    public static void GetInput() {

        Scanner scanner = new Scanner(System.in);

        while (GetInput) {
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
}
