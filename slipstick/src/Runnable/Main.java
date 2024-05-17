package Runnable;

import Control.GameController;
import GameManagers.CommandController;
import GameManagers.Game;
import GameManagers.Menu;
import Graphics.MenuWindowFrame;
import Graphics.SoundManager;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {

    public static PrintStream os = System.out;
    public static final Game game = new Game();
    public static final Menu menu = new Menu();
    public static final GameController gameController = new GameController();
    public static final SoundManager soundManager = new SoundManager();

    public static void main(String[] args) {
        //System.setProperty("file.encoding", "UTF-8");

        try {
            //game.LoadGame("slipstick/testmap.txt");
            game.LoadGame("C:\\Users\\bened\\team_friendship\\team_friendship\\slipstick\\testmap.txt"); // bercinek így jó
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MenuWindowFrame menuWF = new MenuWindowFrame();
        soundManager.playSoundLooped("menu");

        CommandController.GetInput();
    }
}
