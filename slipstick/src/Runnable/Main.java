package Runnable;

import Control.GameController;
import GameManagers.CommandController;
import GameManagers.Game;
import GameManagers.Menu;
import Graphics.MenuWindowFrame;
import Graphics.Managers.SoundManager;
import Graphics.Managers.ImageManager;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {

    public static PrintStream os = System.out;
    public static Game game;
    public static final Menu menu = new Menu();
    public static final GameController gameController = new GameController();
    public static final ImageManager imageManager = new ImageManager();
    public static final SoundManager soundManager = new SoundManager();

    public static void main(String[] args) {

        MenuWindowFrame menuWF = new MenuWindowFrame();
        soundManager.playSoundLooped("menu");

        CommandController.GetInput();
    }
}
