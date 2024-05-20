package Control;

import Constants.GameConstants;
import Entities.IAI;
import Entities.Student;
import GameManagers.RoundManager;
import Graphics.GameWindowPanel;
import Graphics.Utils.ScreenMessage;
import Graphics.Utils.Vector2;
import Items.Item;
import Items.Transistor;
import Labyrinth.Room;

import java.awt.*;
import java.util.Random;

import static Runnable.Main.*;

public class GameController {
    //a student can only use 1 door in a turn
    private boolean isRunning;
    private GameWindowPanel gamePanel;
    private Thread gameThread;
    private Item selectedItemInRoom;
    private RoundManager roundManager;

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
        long drawtime = 0;

        while (isRunning) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {

                if (!game.IsPreGame()) {     // If the game is not initialized yet
                    GameLogic();
                    gamePanel.UpdateScreenMessages();
                }
                //long t1 = System.currentTimeMillis();
                long t1 = System.nanoTime();
                gamePanel.repaint();
                //long t2 = System.currentTimeMillis();
                long t2 = System.nanoTime();
                long dt = t2 - t1;
                drawtime += dt;
                //System.out.println(dt + " ns");
                //System.out.println(dt + " ms");

                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
                //System.out.println(++ellapsedTime);
                double res = drawtime / (double) GameConstants.DesiredFPS / 1_000_000.0;
                String formattedNumber = String.format("%.8f", res);
                NewScreenMessage(60, Color.black, "Avarage draw time: " + formattedNumber + " ms");
                drawtime = 0;
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
        // Start the game thread
        gameThread = new Thread(this::MainGameLoop);
        isRunning = true;
        gameThread.start();

        // Init the game
        roundManager = game.GetRoundManager();
        imageManager.LoadGameImages();
        soundManager.LoadGameSounds();
        game.GetMap().GenerateLabyrinth(game.GetStudents().size());
        game.InitRandom(5);
        game.InitEntities();

        // Finished init
        game.SetPreGame();
    }

    public void StopGame() {
        isRunning = false;
    }

    public void HandleInput(Student student,char input) {

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
            case 'p':
                Item transistor1 = selectedItemInRoom;
                if (selectedItemInRoom !=null && transistor1 instanceof Transistor) {
                    for (Item transistor2 : student.GetInventory()) {
                        if (transistor2 != transistor1 && transistor2.getClass() == Items.Transistor.class) {
                            student.PairTransistors((Transistor) transistor1, (Transistor) transistor2);
                        }
                    }
                }
                break;
            case 'u':
                if(student.GetSelectedItem() != null) {
                    student.UseSelectedItem();
                }
                break;
            case 'a':
                if(student.GetSelectedItem() !=null) {
                    student.ActivateItem(selectedItemInRoom);
                }
                break;
            case 'e':
                roundManager.EndTurn();
                return;
            case 'c':
                student.PickUpItem(selectedItemInRoom);
                selectedItemInRoom = null;
                break;
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

        Random rand = new Random();
        if (rand.nextBoolean()) soundManager.playSoundOnce(GameConstants.SOUND_DOOR1);
        else soundManager.playSoundOnce(GameConstants.SOUND_DOOR2);
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
        if(student.IsDead()) {
            NewScreenMessage(240, Color.RED,"Student " + student.GetName() + " is dead.");
            roundManager.EndTurn();
           // isFirstMove = true;
        }

    }

    private void HandleAIEntities(IAI entities) {
        if (entities == null) return;

        entities.AI();
    }

//endregion
}
