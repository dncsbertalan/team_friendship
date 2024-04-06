package GameManagers.Commands;

public class Commands {

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

    public static void List(String[] args) {

    }

    public static void UseItem(String[] args) {
        System.out.println("useitem exec");
    }

    public static void ActivateItem(String[] args) {

    }

    public static void PickUpItem(String[] args) {

    }

    public static void DropItem(String[] args) {

    }

    public static void Merge(String[] args) {

    }

    public static void Separate(String[] args) {

    }

    public static void Load(String[] args) {

    }

    public static void Save(String[] args) {

    }

    public static void Random(String[] args) {

    }
}
