package Control;

import Constants.GameConstants;
import Entities.IAI;
import Entities.Student;
import GameManagers.RoundManager;
import Graphics.GameWindowPanel;
import Graphics.Utils.Clickable.ItemObject;
import Graphics.Utils.ScreenMessage;
import Items.Item;
import Items.Transistor;
import Labyrinth.Room;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

import static Runnable.Main.*;

public class GameController {

    public boolean isFirstMove = true;
    private boolean isRunning;
    private GameWindowPanel gamePanel;
    private Thread gameThread;
    private Item selectedItem;
    private final RoundManager roundManager = game.GetRoundManager();

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

                if (game.IsPreGame()) {     // If the game is not initialized yet
                    // TODO: ez szükséges-e
                }
                HandleInput(roundManager.GetActiveStudent());
                GameLogic();
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
        imageManager.LoadImages();
        soundManager.LoadGameSounds();
        game.SetPreGame();
    }

    public void StopGame() {
        isRunning = false;
    }

    private void HandleInput(Student student) {
        Scanner scanner = new Scanner(System.in);
        // Start the loop
        while (true) {
            NewScreenMessage(270,"Its your turn " + student.GetName());
            String input = scanner.nextLine();
            switch (input) {
                case "D":
                    student.DropSelectedItem();
                    break;
                case "P":
                    Item transistor1 = selectedItem;
                    if(transistor1.getClass() == Items.Transistor.class) {
                        for(Item transistor2 : student.GetInventory())
                        {
                            if(transistor2!=transistor1 && transistor2.getClass() == Items.Transistor.class){
                                student.PairTransistors((Transistor) transistor1, (Transistor) transistor2);
                            }
                        }
                    }
                    break;
                case "U":
                    student.UseSelectedItem();
                    break;
                case "A":
                    student.ActivateItem(selectedItem);
                    break;
                case "E":
                    roundManager.EndTurn();
                    isFirstMove = true;
                    return;
                case"C":
                    student.PickUpItem(selectedItem);
            }
        }
    }

    /**
     * The given student tries to step into the given room.
     * @param student   the student that steps into the room
     * @param stepInto  the room the student wants to step into
     */
    public void StepStudent(Student student, Room stepInto) {
        boolean success = student.StepInto(stepInto);
        if (!success) {
            gamePanel.CreateScreenMessage(240, Color.red, "The room is full");
            return;
        }
        isFirstMove = false;
        Random rand = new Random();
        if (rand.nextBoolean()) soundManager.playSoundOnce(GameConstants.SOUND_DOOR1);
        else soundManager.playSoundOnce(GameConstants.SOUND_DOOR2);
    }

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param message   the message
     */
    public void NewScreenMessage(int timeLeft, String message) {
        gamePanel.CreateScreenMessage(timeLeft, message);
    }

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param color     the color of the message
     * @param message   the message
     */
    public void NewScreenMessage(int timeLeft, Color color, String message) {
        gamePanel.CreateScreenMessage(timeLeft, color, message);
    }

    /**
     * Sets the currently selected item.
     * @param newSelectedItem the item
     */
    public void SetSelectedItem(Item newSelectedItem) { selectedItem = newSelectedItem; }

    /**
     * Clears the currently selected item.
     */
    public void ClearSelectedItem() { selectedItem = null; }

//region Game logic ====================================================================================================

    public void GameLogic() {

        Student activeStudent = roundManager.GetActiveStudent();
        IAI activeAIEntity = roundManager.GetActiveAIEntity();

        // Handle student and professor
        this.HandleStudent(activeStudent);
        this.HandleAIEntities(activeAIEntity);
    }

    private void HandleStudent(Student student) {
        if (student == null) return;
        if(student.IsDead()) {
            NewScreenMessage(240, Color.RED,"Student " + student.GetName() + " is dead.");
            roundManager.EndTurn();
            isFirstMove = true;
        }

        HandleInput(student);
    }

    private void HandleAIEntities(IAI entities) {
        if (entities == null) return;

        entities.AI();
    }

//endregion
}
