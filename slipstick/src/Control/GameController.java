package Control;

import Constants.GameConstants;
import Entities.IAI;
import Entities.Student;
import GameManagers.RoundManager;
import Graphics.GameWindowPanel;
import Graphics.Utils.GasCloud;
import Graphics.Utils.ScreenMessage;
import Graphics.Utils.Vector2;
import Items.Fake;
import Items.Item;
import Items.Transistor;
import Labyrinth.Room;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Runnable.Main.*;

public class GameController {

    private boolean isRunning;
    private GameWindowPanel gamePanel;
    private Thread gameThread;
    private Item selectedItemInRoom;
    private RoundManager roundManager;
    private boolean lastPhaseStarted;
    public int timeForVisual;

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

        //long drawtime = 0;

        while (isRunning) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {

                if (!game.IsPreGame()) {     // If the game is not initialized yet
                    GameLogic();
                    gamePanel.UpdateScreenMessages();
                    gamePanel.UpdateGasClouds();

                    timeForVisual++;
                    if (timeForVisual > GameConstants.MAX_VISUAL_TIME_HOUR) timeForVisual = 0;
                }

                if (game.IsLastPhase()) {
                    if (!lastPhaseStarted) {
                        soundManager.playSoundLooped(GameConstants.ENDGAME_MUSIC);
                        lastPhaseStarted = true;
                    }
                } else {
                    if (lastPhaseStarted) {
                        soundManager.playSoundLooped(GameConstants.GAME_MUSIC);
                        lastPhaseStarted = false;
                    }
                }

                //long t1 = System.nanoTime();
                gamePanel.repaint();
                //long t2 = System.nanoTime();
                //long dt = t2 - t1;
                //drawtime += dt;

                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
                //double res = drawtime / (double) GameConstants.DesiredFPS / 1_000_000.0;
                //String formattedNumber = String.format("%.8f", res);
                //NewScreenMessage(60, Color.black, "Avarage draw time: " + formattedNumber + " ms");
                //drawtime = 0;
                NewScreenMessage(60, Color.black, "Draw count: " + gamePanel.drawCount);
                gamePanel.drawCount = 0;
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

// region Start and stop ===============================================================================================
    public void StartGame(){
        // Start the game thread
        gameThread = new Thread(this::MainGameLoop);
        isRunning = true;
        gameThread.start();

        // Init the game
        long t1 = System.nanoTime();
        roundManager = game.GetRoundManager();
        imageManager.LoadGameImages();
        soundManager.LoadGameSounds();
        game.GetMap().GenerateLabyrinth(game.GetStudents().size());
        game.InitRandom(1);
        game.InitEntities();
        long t2 = System.nanoTime();
        double res = (t2 - t1) / 1_000_000.0;
        String formattedNumber = String.format("%.6f", res);
        NewScreenMessage(300, Color.black, "Resources loaded in " + formattedNumber + " ms");

        // Finished init
        game.SetPreGame();
    }

    public void StopGame() {
        isRunning = false;
    }

// endregion ===========================================================================================================

    public void HandleInput(Student student, char input) {

        if (student == null) return;

        input = Character.toLowerCase(input);

        switch (input) {
            case '1':
                student.SelectInventorySlot(1);
                break;
            case '2':
                student.SelectInventorySlot(2);
                break;
            case '3':
                student.SelectInventorySlot(3);
                break;
            case '4':
                student.SelectInventorySlot(4);
                break;
            case '5':
                student.SelectInventorySlot(5);
                break;
            case 'd':
                if(student.GetSelectedItem() != null ) {
                    NewScreenMessage(270, "Dropped " + student.GetSelectedItem().GetName());
                    student.DropSelectedItem();
                }
                break;
            case 'p': {
                Item transistor1 = student.GetSelectedItem();
                if (transistor1 !=null ) {

                    if (transistor1 instanceof Transistor) {
                        for (Item transistor2 : student.GetInventory()) {
                            if (transistor2 != transistor1 && transistor2 instanceof Transistor) {
                                student.PairTransistors((Transistor) transistor1, (Transistor) transistor2);

                                NewScreenMessage(GameConstants.TRANSISTOR_PAIR_MESSAGE_TIMELEFT, GameConstants.TRANSISTOR_PAIR_MESSAGE);
                            }
                        }
                    }

                    if (transistor1 instanceof Fake) {
                        soundManager.playSoundOnce(GameConstants.SOUND_FAKE_ITEM_USE);
                        student.UseSelectedItem();
                        NewScreenMessage(GameConstants.FAKE_ITEM_USE_MESSAGE_TIMELEFT, GameConstants.FAKE_ITEM_USE_MESSAGE);
                    }
                }
                break;
            }
            case 'u': {
                Item useItem = student.GetSelectedItem();
                if(useItem != null) {

                    if (useItem instanceof Fake) {
                        soundManager.playSoundOnce(GameConstants.SOUND_FAKE_ITEM_USE);
                        NewScreenMessage(GameConstants.FAKE_ITEM_USE_MESSAGE_TIMELEFT, GameConstants.FAKE_ITEM_USE_MESSAGE);
                    }

                    student.UseSelectedItem();
                }
                break;
            }
            case 'a': {
                Item activateItem = student.GetSelectedItem();
                if(activateItem !=null) {

                    if (activateItem instanceof Fake) {
                        soundManager.playSoundOnce(GameConstants.SOUND_FAKE_ITEM_USE);
                        student.UseSelectedItem();
                        NewScreenMessage(GameConstants.FAKE_ITEM_USE_MESSAGE_TIMELEFT, GameConstants.FAKE_ITEM_USE_MESSAGE);
                        break;
                    }

                    student.ActivateItem(activateItem);
                }
                break;
            }
            case 'e':
                roundManager.EndTurn();
                return;
            case 'c':
                student.PickUpItem(selectedItemInRoom);
                selectedItemInRoom = null;
                break;
        }


    }

// region Methods that connct the control and view =====================================================================

    /**
     * The given student tries to step into the given room.
     * @param student   the student that steps into the room
     * @param stepInto  the room the student wants to step into
     */
    public void StepStudent(Student student, Room stepInto) {
        boolean success = student.StepInto(stepInto);

        if (!success) {
            if(student.GetRemainingTurns() > 0) {
                gamePanel.CreateScreenMessage(240, Color.red, "The room is full");
            }else{
                gamePanel.CreateScreenMessage(240, Color.red, "You have no steps left");
            }
            return;
        }

        // Messages
        if (stepInto.IsGassed())
            NewScreenMessage(240, Color.RED,student.GetName() + GameConstants.MESSAGE_GAS_ROOM_STEP);

        // Plays door sound
        Random rand = new Random();
        if (rand.nextBoolean()) soundManager.playSoundOnce(GameConstants.SOUND_DOOR1);
        else soundManager.playSoundOnce(GameConstants.SOUND_DOOR2);

        // Clears selected item
        ClearSelectedItem();
    }

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left in seconds
     * @param message   the message
     */
    public void NewScreenMessage(int timeLeft, String message) {
        gamePanel.CreateScreenMessage(timeLeft, message);
    }

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left in seconds
     * @param color     the color of the message
     * @param message   the message
     */
    public void NewScreenMessage(int timeLeft, Color color, String message) {
        gamePanel.CreateScreenMessage(timeLeft, color, message);
    }

    /**
     * Creates new gas cloud.
     * @param timeLeft  the time left of this cloud
     * @param position  the position
     * @param scale     the scale
     * @param direction the direction
     */
    public void NewGasCloud(int timeLeft, Vector2 position, float scale, int direction) {
        gamePanel.NewGasCloud(timeLeft, position, scale, direction);
    }

    /**
     * Removes gas cloud.
     * @param gasCloud the gas cloud to be removed.
     */
    public void KillGasCloud(GasCloud gasCloud) {
        gamePanel.KillGasCloud(gasCloud);
    }

    /**
     * Sets the currently selected item.
     * @param newSelectedItem the item
     */
    public void SetSelectedItem(Item newSelectedItem) { selectedItemInRoom = newSelectedItem; }

    /**
     * Clears the currently selected item.
     */
    public void ClearSelectedItem() { selectedItemInRoom = null; }

    /**
     * Returns selected item.
     */
    public Item GetSelectedItem(){
        return selectedItemInRoom;
    }

    /**
     * Returns the cursor position on the screen.
     * @return  the cursor position
     */
    public Vector2 GetMousePosition() {
        return gamePanel.GetMousePosition();
    }

// endregion ===========================================================================================================

//region Game logic ====================================================================================================

    private void GameLogic() {

        Student activeStudent = roundManager.GetActiveStudent();
        IAI activeAIEntity = roundManager.GetActiveAIEntity();

        // Handle student and professor
        this.HandleStudent(activeStudent);
        this.HandleAIEntities(activeAIEntity);
    }

    private void HandleStudent(Student student) {
        if (student == null) return;

        if (student.IsDead()) {
            NewScreenMessage(240, Color.RED,"Student " + student.GetName() + " is dead.");
            roundManager.EndTurn();
        }

        else if (student.IsParalysed()) {
            NewScreenMessage(240, Color.RED, student.GetName() + GameConstants.MESSAGE_PARALYZED_SKIP);
            roundManager.EndTurn();
        }
    }

    private void HandleAIEntities(IAI entities) {
        if (entities == null) return;

        entities.AI();
    }

//endregion

}
