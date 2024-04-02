package GameManagers.Commands;

public class Commands {

    public static void UseItem(String[] args) {
        System.out.println("useitem exec");
    }

    public static void Move(String[] args) {
        if (args.length < 3) {
            System.out.println("Error: no option\nUsage: move <FASZ>");
            return;
        }

        switch (args[1]) {
            case "-p":
                System.out.println("player");
                break;

            default:
                System.out.println("Error: no option\nUsage: move <FASZ>");
                return;
        }
    }
}
