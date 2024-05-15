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
                GameConstants.GamePanel_EXIT_BUTTON_BORDER_COLOR);
        this.add(menuButton);
        menuButton.setLayout(null);
        menuButton.setBounds((int) (windowSize.x * 0.9f), (int) (windowSize.y * 0.85f), 150, 100 );
        menuButton.setFont(GameConstants.MenuPanel1_BUTTON_FONT);
        menuButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
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

        ResetClickables();

        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, this);
        mousePosition = new Vector2(point.x, point.y);

        DrawCurrentRound(graphics2D);
        DrawEntitryInfo(graphics2D);
        DrawInventory(graphics2D);
        DrawRoom(graphics2D);
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
        int neighbours = curRoom.GetNeighbours().size();
        neighbours = Math.max(neighbours, GameConstants.GamePanel_ROOM_MIN_SIDES);
        float angleBetween = 360f / neighbours;
        Vector2 distanceFromCenter = new Vector2(0, -GameConstants.GamePanel_ROOM_SIZE);

        Vector2 centerPos = Vector2.Mult(windowSize, 0.5f);
        Vector2 point;
        Polygon polygon = new Polygon();

        for (int i = 0; i < neighbours; i++) {
            point = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter,i * angleBetween));
            polygon.addPoint(point.x, point.y);
        }

        graphics2D.setColor(Color.pink);
        graphics2D.fillPolygon(polygon);

        DrawRoomInside(graphics2D, curRoom);
    }

    /**
     * Draws the inside of a room.
     * @param graphics2D graphics instance
     * @param room the room
     */
    public void DrawRoomInside(Graphics2D graphics2D, Room room) {

        int cap = room.GetCapacity();
        float angleBetween = 360f / cap;
        Vector2 distanceFromCenter = new Vector2(50, 0);
        Vector2 centerPos = Vector2.Mult(windowSize, 0.5f);

        ArrayList<Entity> entities = room.GetEntities();
        int drawnEntities = 0;

        for (Entity entity : entities) {
            if (entity.getClass() == Student.class) {
                graphics2D.setColor(Color.green);
            }
            else if (entity.getClass() == Professor.class) {
                graphics2D.setColor(Color.red);
            }
            else if (entity.getClass() == Janitor.class) {
                graphics2D.setColor(Color.orange);
            }
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter,drawnEntities++ * angleBetween));
            graphics2D.fillRect(pos.x, pos.y, 10, 10);
        }

        float doorAngleBetween = 360f / Math.max(room.GetNeighbours().size(), GameConstants.GamePanel_ROOM_MIN_SIDES);
        int doorDist = (int) ((float) GameConstants.GamePanel_ROOM_SIZE * Math.cos(Math.toRadians(doorAngleBetween / 2.0)));
        Vector2 doorDistanceFromCenter = new Vector2(0, -doorDist);
        doorDistanceFromCenter.RotateBy(doorAngleBetween / 2f);
        int drawnDoor = 0;

        for (Room neighbour : room.GetNeighbours()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(doorDistanceFromCenter,drawnDoor++ * doorAngleBetween));
            DoorObject door = new DoorObject(pos, neighbour);
            AddClickable(door);
            door.Draw(graphics2D, mousePosition);
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

// endregion

// region Clickable objects ============================================================================================

    private final ArrayList<ClickableObject> clickableObjects = new ArrayList<>();

    /**
     * Adds a new clickable object to the screen.
     * @param clickableObject the clickable object to be added
     */
    private void AddClickable(ClickableObject clickableObject) {
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
     * <p>If the number of messages exceeds the {@link GameConstants#GamePanel_MAX_SCREEN_MESSGES} the
     * oldest message gets deleted.</p>
     * @param timeLeft  the time this message has left
     * @param message   the message
     */
    public void CreateScreenMessage(int timeLeft, String message) {
        if (screenMessages.size() == GameConstants.GamePanel_MAX_SCREEN_MESSGES) {
            screenMessages.remove(0);
        }
        screenMessages.add(new ScreenMessage(timeLeft, message));
    }

    public void UpdateScreenMessages() {
        for (ScreenMessage message : screenMessages) {
            message.Update();
        }
    }

// endregion
}
