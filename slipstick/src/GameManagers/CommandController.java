package GameManagers;

import GameManagers.Commands.Commands;
import GameManagers.Commands.ICommand;

import java.util.HashMap;
import java.util.Scanner;

public class CommandController {

    private final static HashMap<String, ICommand> commands = new HashMap<>();
    static {
        commands.put("exit", (args) -> CommandController.getInput = false);
        commands.put("use_item", Commands::UseItem);
        commands.put("move", Commands::Move);
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
