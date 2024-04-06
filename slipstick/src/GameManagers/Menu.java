package GameManagers;

import Runnable.Main;

import java.util.ArrayList;

public class Menu {

    private ArrayList<String> playerNames;
    private boolean gameRandom = true;

    /**
     * Starts the game from the menu.
     */
    public void StartGame() {
        Main.game.InitPlayers(playerNames);
        Main.game.GetRoundManager(); // TODO: start method here
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
    public void Random(boolean b) {
        gameRandom = b;
    }
}
