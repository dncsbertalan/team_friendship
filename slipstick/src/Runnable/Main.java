package Runnable;

import Entities.Janitor;
import GameManagers.CommandController;
import GameManagers.Game;

public class Main {

    public static boolean isConsole = true;
    public static final Game game = new Game();

    public static void main(String[] args) {
        //Game game = new Game();
        //game.MainGameLoop();

        CommandController.GetInput();
    }
}
