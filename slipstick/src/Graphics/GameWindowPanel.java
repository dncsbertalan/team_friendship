package Graphics;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Janitor;
import Entities.Professor;
import Entities.Student;
import Labyrinth.Room;
import Utils.Vector2;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.util.ArrayList;

import static Runnable.Main.game;

public class GameWindowPanel extends JPanel {
    private final GameWindowFrame gameWindowFrame;
    private Vector2 mousePosition;

    public GameWindowPanel(GameWindowFrame frame) {

        gameWindowFrame = frame;

        // ez kurvára ideiglenes // TODO change this bitches
        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(GameConstants.GamePanel_WIDTH, GameConstants.GamePanel_HEIGHT));

        // Panel init
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, this);
        mousePosition = new Vector2(point.x, point.y);

        //Random random = new Random();
        //graphics2D.drawRect(random.nextInt(200, 600), random.nextInt(100, 300), 10, 20);

        DrawCurrentRound(graphics2D);
        DrawEntitryInfo(graphics2D);
        DrawInventory(graphics2D);
        DrawRoom(graphics2D);
    }
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
        neighbours = Math.max(neighbours, 3);
        float angleBetween = 360f / neighbours;
        Vector2 distanceFromCenter = new Vector2(250, 0);

        Vector2 centerPos = new Vector2(GameConstants.GamePanel_WIDTH / 2, GameConstants.GamePanel_HEIGHT / 2);
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
        Vector2 centerPos = new Vector2(GameConstants.GamePanel_WIDTH / 2, GameConstants.GamePanel_HEIGHT / 2);

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

        ClickablePane clickablePane = new ClickablePane(centerPos);
        clickablePane.Draw(graphics2D, mousePosition);
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
        Font font = new Font("Times New Roman", Font.BOLD, 20);
        graphics2D.setFont(font);

        Vector2 pos = GameConstants.GamePanel_ROUND_POS();
        graphics2D.drawString(GameConstants.GamePanel_ROUND_TEXT + game.GetRoundManager().GetCurrentRound()
        , pos.x, pos.y);
    }

    /**
     * Draws the entities to the screen.
     * @param graphics2D graphics instance
     */
    private void DrawEntitryInfo(Graphics2D graphics2D) {
        Vector2 pos = GameConstants.GamePanel_ENTITY_INFO_POS();
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
}
