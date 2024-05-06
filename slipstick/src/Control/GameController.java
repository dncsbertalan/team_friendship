package Control;

import Constants.GameConstants;
import GameManagers.Game;
import GameManagers.RoundManager;

public class GameController {
    private boolean isRunning;
    private Game _game;
    private RoundManager roundManager;
    private void MainGameLoop() {

        double drawInterval = 1_000_000_000.0 / GameConstants.DesiredFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (isRunning) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                _game.GameLogic();

                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
            }

        }
    }
    public void StartGame(Game game){
        game = _game;
    }
    private void HandleInput(String input){

    }


}
