package Runnable;

import GameManagers.CommandController;
import GameManagers.Game;

import java.io.PrintStream;


public class Main {

    public static PrintStream os = System.out;
    public static final Game game = new Game();

    public static void main(String[] args) {
        //System.setProperty("file.encoding", "UTF-8");
        CommandController.GetInput();
    }
}
