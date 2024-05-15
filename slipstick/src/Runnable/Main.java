package Runnable;

import Control.GameController;
import GameManagers.CommandController;
import GameManagers.Game;
import GameManagers.Menu;
import Graphics.MenuWindowFrame;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {

    public static PrintStream os = System.out;
    public static final Game game = new Game();
    public static final Menu menu = new Menu();
    public static final GameController gameController = new GameController();

    public static void main(String[] args) {
        //System.setProperty("file.encoding", "UTF-8");

        try {
            game.LoadGame("slipstick/testmap.txt");
            //game.LoadGame("testmap.txt"); // bercinek így jó
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MenuWindowFrame menuWF = new MenuWindowFrame();

        CommandController.GetInput();
    }
}
