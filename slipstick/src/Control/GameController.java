package Control;

import Constants.GameConstants;
import Entities.Student;
import Graphics.GameWindowPanel;
import Graphics.Utils.Clickable.ItemObject;
import Graphics.Utils.ScreenMessage;
import Labyrinth.Room;

import static Runnable.Main.*;

public class GameController {

    private boolean isRunning;
    private GameWindowPanel gamePanel;
    private Thread gameThread;
    private ItemObject selectedItem;

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

        //long ellapsedTime = 0;

        while (isRunning) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {

                HandleInput();
                game.GameLogic();
                gamePanel.UpdateScreenMessages();
                //long t1 = System.currentTimeMillis();
                //long t1 = System.nanoTime();
                gamePanel.repaint();
                //long t2 = System.currentTimeMillis();
                //long t2 = System.nanoTime();
                //long dt = t2 - t1;
                //System.out.println(dt + " ns");
                //System.out.println(dt + " ms");

                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
                //System.out.println(++ellapsedTime);
            }

        }

        os.println("Game thread exited");
    }

    /**
     * Sets the given {@link GameWindowPanel} which this {@link GameController} controlls
     * @param panel the given game panel
     */
    public void SetGamePanel(GameWindowPanel panel) {
        this.gamePanel = panel;
    }

    public void StartGame(){
        gameThread = new Thread(this::MainGameLoop);
        isRunning = true;
        gameThread.start();
    }

    public void StopGame() {
        isRunning = false;
    }

    private void HandleInput(){

    }

    /**
     * The given student tries to step into the given room.
     * @param student   the student that steps into the room
     * @param stepInto  the room the student wants to step into
     */
    public void StepStudent(Student student, Room stepInto) {
        boolean success = student.StepInto(stepInto);

        if (!success) gamePanel.CreateScreenMessage(240, "The room is full");
    }

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#GamePanel_MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param message   the message
     */
    public void NewScreenMessage(int timeLeft, String message) {
        gamePanel.CreateScreenMessage(timeLeft, message);
    }

    /**
     * Sets the currently selected item.
     * @param newSelectedItem the item
     */
    public void SetSelectedItem(ItemObject newSelectedItem) { selectedItem = newSelectedItem; }

    /**
     * Clears the currently selected item.
     */
    public void ClearSelectedItem() { selectedItem = null; }

    /**
     * Gives control to the student
     * @param student
     */
    public void activeStudent(Student student) {

    }
}
