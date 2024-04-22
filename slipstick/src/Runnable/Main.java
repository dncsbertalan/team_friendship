package Runnable;

import Entities.Janitor;
import GameManagers.CommandController;
import GameManagers.Game;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main {

    public static PrintStream os = System.out;
    public static final Game game = new Game();

    public static void main(String[] args) throws FileNotFoundException {
       CommandController.GetInput();
    }
}
