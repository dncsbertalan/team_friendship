package Graphics.Utils.Clickable;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Janitor;
import Entities.Professor;
import Entities.Student;
import Graphics.Utils.Vector2;
import Graphics.GameWindowPanel;
import Labyrinth.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RoomObject {

    /**
     * The room this room object represents.
     */
    private final Room room;
    private final Vector2 centerPos;
    private final GameWindowPanel gamePanel;
    private final boolean isSmallRoom;

    public RoomObject(GameWindowPanel gamePanel, Vector2 centerPos, Room room, boolean isSmallRoom) {
        this.gamePanel = gamePanel;
        this.centerPos = centerPos;
        this.room = room;
        this.isSmallRoom = isSmallRoom;
    }

    /**
     * Draws the room and its content to the screen.
     * @param graphics2D graphics2D instance
     */
    public void Draw(Graphics2D graphics2D) {
        // Make a polygon that is at least 3 sided
        int neighbours = room.GetNeighbours().size();
        neighbours = Math.max(neighbours, GameConstants.ROOM_MIN_SIDES);
        final float angleBetween = 360f / neighbours;
        final int y = isSmallRoom ? (int) (GameConstants.ROOM_SIZE * GameConstants.SMALL_ROOM_SIZE_RATIO) : GameConstants.ROOM_SIZE;
        final Vector2 distanceFromCenter = new Vector2(0, -y);

        Vector2 point;
        Polygon polygon = new Polygon();

        for (int i = 0; i < neighbours; i++) {
            point = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter,i * angleBetween));
            polygon.addPoint(point.x, point.y);
        }

        graphics2D.setColor(Color.pink);
        Rectangle rectangle = polygon.getBounds();
        graphics2D.drawImage(GetRoomImage(polygon), rectangle.x, rectangle.y, null);

        // Room name
        Font font = new Font("Courier New", Font.BOLD, isSmallRoom ? 25 : 35);
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), 150));
        Rectangle2D bounding = graphics2D.getFontMetrics().getStringBounds(room.GetName(), graphics2D);
        Vector2 pos = new Vector2(centerPos.x - (int) bounding.getCenterX(), centerPos.y - (int) bounding.getCenterY());
        graphics2D.drawString(room.GetName(), pos.x, pos.y);

        // Draw the inside
        DrawInside(graphics2D);
    }

    /**
     * Draws the inside of the room
     * @param graphics2D graphics2D instance
     */
    private void DrawInside(Graphics2D graphics2D) {
        final float angleBetween = 360f / room.GetCapacity();
        final int _dist = isSmallRoom ? (int) (50 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 50;
        Vector2 distanceFromCenter = new Vector2(_dist, 0);

        // Entities
        ArrayList<Entity> entities = room.GetEntities();
        int drawnEntities = 0;

        for (Entity entity : entities) {
            if (entity instanceof Student) {
                graphics2D.setColor(Color.green);
            }
            else if (entity instanceof Professor) {
                graphics2D.setColor(Color.red);
            }
            else if (entity instanceof Janitor) {
                graphics2D.setColor(Color.orange);
            }
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter,drawnEntities++ * angleBetween));
            graphics2D.fillRect(pos.x, pos.y, 10, 10);
        }

        // Items
        // TODO

        // Doors
        final float doorAng = 360f / Math.max(room.GetNeighbours().size(), GameConstants.ROOM_MIN_SIDES);
        final int y = isSmallRoom ? (int) (GameConstants.ROOM_SIZE * GameConstants.SMALL_ROOM_SIZE_RATIO) : GameConstants.ROOM_SIZE;
        final int dist = (int) ((float) y * Math.cos(Math.toRadians(doorAng / 2.0)));
        Vector2 doorPosFromCenter = new Vector2(0, -dist);
        doorPosFromCenter.RotateBy(doorAng / 2f);
        int drawnDoor = 0;

        for (Room neighbour : room.GetNeighbours()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(doorPosFromCenter,drawnDoor++ * doorAng));
            DoorObject door = new DoorObject(pos, neighbour, !isSmallRoom);
            gamePanel.AddClickable(door);
            door.Draw(graphics2D, gamePanel.GetMousePosiotion());
        }
    }

    /**
     * Returns an image of a textured polygon.
     * @param polygon   the polygon to be textured
     * @return  the image of the textured polygon
     */
    private BufferedImage GetRoomImage(Shape polygon) {
        // TODO image loading is temporary
        File file;
        BufferedImage image;
        try {
            file = new File(GameConstants.WALL_BG_TEMP);
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Rectangle rectangle = polygon.getBounds();
        BufferedImage tmp = new BufferedImage(rectangle.width + 2,rectangle.height + 2,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = tmp.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        // creates a transform that centers the shape in the image
        AffineTransform centerTransform = AffineTransform.getTranslateInstance(-rectangle.x + 1, -rectangle.y + 1);
        g.setTransform(centerTransform);

        // clips the shape from the image
        g.setClip(polygon);
        g.drawImage(image, rectangle.x, rectangle.y, null);
        g.setClip(null);

        // outline
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(isSmallRoom ? 10f * GameConstants.SMALL_ROOM_SIZE_RATIO : 10f));
        g.draw(polygon);

        g.dispose();

        return tmp;
    }
}
