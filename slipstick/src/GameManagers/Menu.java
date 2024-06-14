package GameManagers;

import Graphics.MenuWindowPanel2;
import Runnable.Main;

import javax.swing.*;
import java.util.ArrayList;

public class Menu {

    private final ArrayList<String> playerNames = new ArrayList<>();

    /**
     * Starts the game from the menu.
     */
    public void StartGame() {
        Main.game = new Game();
        Main.game.InitPlayers(playerNames);
        Main.gameController.StartGame();
    }

    /**
     * Adds a new name to the list of player names.
     *
     * @param newName the name
     */
    public void AddPlayer(String newName) {
        for (String name : playerNames) {
            if (name.equals(newName)) {
                System.out.println("Error: Cannot add a name more than once");
                return;
            }
        }
        playerNames.add(newName);
    }

    public void ResetGame() {
        playerNames.clear();
        for (JTextField f : MenuWindowPanel2.GetNameFields()) {
            f.setText("");
        }
    }
}
