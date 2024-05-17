package Graphics;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Janitor;
import Entities.Professor;
import Entities.Student;
import Graphics.Listeners.GameWindowMouseWheelListener;
import Graphics.Utils.Clickable.ClickableObject;
import Graphics.Listeners.GameWindowMouseListener;
import Graphics.Utils.Clickable.DoorObject;
import Graphics.Utils.Clickable.RoomObject;
import Graphics.Utils.HelperMethods;
import Graphics.Utils.MenuButton;
import Graphics.Utils.ScreenMessage;
import Labyrinth.Room;
import Graphics.Utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static Runnable.Main.game;
import static Runnable.Main.soundManager;

public class GameWindowPanel extends JPanel {
    private final GameWindowFrame gameWindowFrame;
    private Vector2 mousePosition;
    private final Vector2 windowSize;

    public GameWindowPanel(GameWindowFrame frame) {

        gameWindowFrame = frame;
        windowSize = new Vector2(frame.getWidth(), frame.getHeight());

        // ez kurvára ideiglenes // TODO change this bitches
        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(GameConstants.GamePanel_WIDTH, GameConstants.GamePanel_HEIGHT));
        /*int width = GameConstants.GamePanel_WIDTH;
        int height = GameConstants.GamePanel_HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        int centerX = width / 2;
        int centerY = height / 2;
        int circleRadius = 100;
        g.setColor(Color.yellow);
        g.fillOval(centerX - circleRadius, centerY - circleRadius, 2 * circleRadius, 2 * circleRadius);
        g.dispose();
        File outputFile = new File("TransparentCircleImage.png");
        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("A kép sikeresen létrejött: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba a kép létrehozása közben: " + e.getMessage());
        }*/

        // Panel init
        GameWindowMouseListener mouseListener = new GameWindowMouseListener(this);
        this.addMouseListener(mouseListener);
        GameWindowMouseWheelListener mouseWheelListener = new GameWindowMouseWheelListener();
        this.addMouseWheelListener(mouseWheelListener);
        this.setDoubleBuffered(true);

        // TODO temp or maybe good (?) xd
        this.setLayout(null);
        MenuButton menuButton = new MenuButton(
                GameConstants.GamePanel_EXIT_BUTTON,
                GameConstants.GamePanel_EXIT_BUTTON_BACKGROUND_COLOR,
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_COLOR,
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_THICKNESS);
        this.add(menuButton);
        menuButton.setLayout(null);
        menuButton.setBounds(windowSize.x - 125 - 20, windowSize.y - 75 - 20, 125, 75 );
        menuButton.setFont(GameConstants.MenuPanel1_BUTTON_FONT);
        menuButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                soundManager.playSoundLooped("menu");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        if (game.IsPreGame()) {     // If the game is not loaded fully
            return;
        }

        ResetClickables();  // TODO ezt maybe nem itt

        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, this);
        mousePosition = new Vector2(point.x, point.y);

        DrawCurrentRound(graphics2D);
        DrawEntitryInfo(graphics2D);
        DrawInventory(graphics2D);
        DrawRoom(graphics2D);
        DrawScreenMessages(graphics2D);
    }

// region Getters/Setters ==============================================================================================

    public Vector2 GetMousePosiotion() {
        return mousePosition;
    }

//endregion

// region DRAW =========================================================================================================

    /**
     * Draws the currently active student's room.
     * @param graphics2D graphics instance
     */
    private void DrawRoom(Graphics2D graphics2D) {

        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        Room curRoom = active.GetCurrentRoom();

        RoomObject roomObject = new RoomObject(this, Vector2.Mult(windowSize, 0.5f), curRoom, true);
        roomObject.Draw(graphics2D);

        float angle = 360f / Math.max(curRoom.GetNeighbours().size(), GameConstants.GamePanel_ROOM_MIN_SIDES);
        int dist = (int) ((float) GameConstants.GamePanel_ROOM_SIZE * Math.cos(Math.toRadians(angle / 2.0)));
        Vector2 neighbourDistanceFromCenter = new Vector2(0, -dist - 300);
        neighbourDistanceFromCenter.RotateBy(angle / 2f);
        int drawnRoom = 0;
        Vector2 centerPos = Vector2.Mult(windowSize, 0.5f);

        for (Room neighbour : curRoom.GetNeighbours()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(neighbourDistanceFromCenter,drawnRoom++ * angle));
            RoomObject ro = new RoomObject(this, pos, neighbour, false);
            ro.Draw(graphics2D);
        }
    }

    /**
     * Draws the inventory to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawInventory(Graphics2D graphics2D) {

        // TODO EZ SZAR XD
        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        int selectedSlot = active.GetSelectedInventorySlot();
        int invRectSize = 50;
        int selectedRectSize = 54;
        for (int i = 0; i < 5; i++) {
            Vector2 pos = GameConstants.GamePanel_INVENTORY_POS();
            pos.AddX(i * (50 + 10));
            if (i == selectedSlot) {
                graphics2D.setColor(Color.yellow);
                graphics2D.fillRect(pos.x - 2, pos.y - 2, selectedRectSize, selectedRectSize);
            }
            graphics2D.setColor(Color.black);
            graphics2D.fillRect(pos.x, pos.y, invRectSize, invRectSize);
        }
    }

    /**
     * Draws the current round to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawCurrentRound(Graphics2D graphics2D) {
        // TODO TEMPORARY
        //Font font = new Font("Times New Roman", Font.BOLD, 25);
        Font font = new Font("Courier New", Font.BOLD, 25);
        graphics2D.setFont(font);

        Vector2 pos = Vector2.Mult(windowSize, 0.9f, 0.05f);
        graphics2D.drawString(GameConstants.GamePanel_ROUND_TEXT + game.GetRoundManager().GetCurrentRound()
        , pos.x, pos.y);
    }

    /**
     * Draws the entities to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawEntitryInfo(Graphics2D graphics2D) {
        Vector2 pos = new Vector2(GameConstants.GamePanel_ENTITY_INFO_POS().x, (int) (windowSize.y * 0.15f));
        int spaceBetweenLines = 20;

        // TODO TEMPORARY
        Font font = new Font("Times New Roman", Font.BOLD, 20);
        graphics2D.setFont(font);

        ArrayList<Entity> entities = new ArrayList<>(game.GetStudents()); // TODO IDK HOGY KELL E MINDEN ENTITÁS
        for (Entity entity : entities) {

            pos.AddY(spaceBetweenLines);
            String text = entity.GetName(); // TODO MORE INFO
            graphics2D.drawString(text, pos.x, pos.y);
        }
    }

    /**
     * Draws the screen messages to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawScreenMessages(Graphics2D graphics2D) {
        int textsHeight = (screenMessages.size() - 1) * GameConstants.GamePanel_SCREEN_MESSAGE_DISTANCE;
        int textHeight = (int) -graphics2D.getFontMetrics().getStringBounds("GetHeight!<3", graphics2D).getY();
        graphics2D.setFont(GameConstants.GamePanel_SCREEN_MESSAGE_FONT);

        for (ScreenMessage sc : screenMessages) {
            textsHeight += textHeight;
        }

        Vector2 posOnScreen = new Vector2(GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT().x,
                windowSize.y - GameConstants.GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT().y - textsHeight);

        for (ScreenMessage sc : screenMessages) {
            int alpha = (int) HelperMethods.Remap(sc.GetTimeLeft(), 60, 0, 255, 0, true);
            graphics2D.setColor(new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), alpha));
            graphics2D.drawString(sc.GetMessage(), posOnScreen.x, posOnScreen.y);
            posOnScreen.AddY(textHeight + GameConstants.GamePanel_SCREEN_MESSAGE_DISTANCE);
        }
    }

// endregion

// region Clickable objects ============================================================================================

    private final ArrayList<ClickableObject> clickableObjects = new ArrayList<>();

    /**
     * Adds a new clickable object to the screen.
     * @param clickableObject the clickable object to be added
     */
    public void AddClickable(ClickableObject clickableObject) {
        clickableObjects.add(clickableObject);
    }

    /**
     * Resests the clickable objects aka clears the content of the clickableObjects field.
     */
    private void ResetClickables() {
        clickableObjects.clear();
    }

    /**
     * Checks wether a click happened on a clickable object.
     * If yes then it executes the objects command.
     * @param mousePosition the position of the mouse on the screen
     */
    public void ClickOnScreen(Vector2 mousePosition) {
        for (ClickableObject clickableObject : clickableObjects) {
            if (clickableObject.IsInside(mousePosition)) {
                clickableObject.Click();
                return;
            }
        }
    }

// endregion

// region Message panel ================================================================================================

    private final ArrayList<ScreenMessage> screenMessages = new ArrayList<>();

    /**
     * Creates a new {@link ScreenMessage} and adds it the screen messages list.
     * <p>If the number of messages exceeds the {@link GameConstants#GamePanel_MAX_SCREEN_MESSAGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param message   the message
     */
    public void CreateScreenMessage(int timeLeft, String message) {
        if (screenMessages.size() == GameConstants.GamePanel_MAX_SCREEN_MESSAGES) {
            screenMessages.remove(0);
        }
        screenMessages.add(new ScreenMessage(timeLeft, message));
    }

    /**
     * Updates the screen messages.
     */
    public void UpdateScreenMessages() {
        ArrayList<ScreenMessage> toDelete = new ArrayList<>();

        for (ScreenMessage message : screenMessages) {
            message.Update();
            if (message.GetTimeLeft() == 0) toDelete.add(message);
        }

        for (ScreenMessage del : toDelete) {
            screenMessages.remove(del);
        }
    }

// endregion
}
