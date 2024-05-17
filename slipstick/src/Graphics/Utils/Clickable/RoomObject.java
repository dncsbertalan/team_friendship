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
    private final boolean roomDoorCanBeClicked;

    public RoomObject(GameWindowPanel gamePanel, Vector2 centerPos, Room room, boolean roomDoorCanBeClicked) {
        this.gamePanel = gamePanel;
        this.centerPos = centerPos;
        this.room = room;
        this.roomDoorCanBeClicked = roomDoorCanBeClicked;
    }

    public void Draw(Graphics2D graphics2D) {

        int neighbours = room.GetNeighbours().size();
        neighbours = Math.max(neighbours, GameConstants.GamePanel_ROOM_MIN_SIDES);
        final float angleBetween = 360f / neighbours;
        final int y = roomDoorCanBeClicked ? GameConstants.GamePanel_ROOM_SIZE : GameConstants.GamePanel_SMALL_ROOM_SIZE;
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

        DrawInside(graphics2D);
    }

    private void DrawInside(Graphics2D graphics2D) {
        final int cap = room.GetCapacity();
        final float angleBetween = 360f / cap;
        Vector2 distanceFromCenter = new Vector2(50, 0);

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

        final float doorAngleBetween = 360f / Math.max(room.GetNeighbours().size(), GameConstants.GamePanel_ROOM_MIN_SIDES);
        final int y = roomDoorCanBeClicked ? GameConstants.GamePanel_ROOM_SIZE : GameConstants.GamePanel_SMALL_ROOM_SIZE;
        int doorDist = (int) ((float) y * Math.cos(Math.toRadians(doorAngleBetween / 2.0)));
        Vector2 doorDistanceFromCenter = new Vector2(0, -doorDist);
        doorDistanceFromCenter.RotateBy(doorAngleBetween / 2f);
        int drawnDoor = 0;

        for (Room neighbour : room.GetNeighbours()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(doorDistanceFromCenter,drawnDoor++ * doorAngleBetween));
            DoorObject door = new DoorObject(pos, neighbour, roomDoorCanBeClicked);
            gamePanel.AddClickable(door);
            door.Draw(graphics2D, gamePanel.GetMousePosiotion());
        }
    }

    private BufferedImage GetRoomImage(Shape polygon) {
        // TODO image loading is temporary
        File file;
        BufferedImage image;
        try {
            file = new File("rsc/wall.png");    // berci
            //file = new File("/slipstick/rsc/wall.png");
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
        g.drawImage(image, (int) rectangle.x, (int) rectangle.y, null);
        g.setClip(null);

        // outline
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(5f));
        g.draw(polygon);

        g.dispose();

        return tmp;
    }
}
