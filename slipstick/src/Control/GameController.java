package Control;

import Constants.GameConstants;
import GameManagers.Game;
import GameManagers.RoundManager;
import Graphics.GameWindowPanel;

import java.awt.*;

import static Runnable.Main.*;

public class GameController {
    private boolean isRunning = true;
    private GameWindowPanel gamePanel;
    private final Thread gameThread = new Thread(this::MainGameLoop);

    /**
     * The core of the game. It "pulls" the required information from the game.
     * Handles user inputs. And update the window.
     */
    private void MainGameLoop() {

        double drawInterval = 1_000_000_000.0 / GameConstants.DesiredFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        long ellapsedTime = 0;

        while (isRunning) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {

                HandleInput();
                game.GameLogic();
                gamePanel.repaint();
                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
                System.out.println(++ellapsedTime);
            }

        }
    }

    /**
     * Sets the given {@link GameWindowPanel} which this {@link GameController} controlls
     * @param panel the given game panel
     */
    public void SetGamePanel(GameWindowPanel panel) {
        this.gamePanel = panel;
    }

    public void StartGame(Game game){
        gameThread.start();
    }

    public void StopGame() {
        isRunning = false;
    }

    private void HandleInput(){

    }
}
