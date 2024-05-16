package Graphics.Utils.Clickable;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Janitor;
import Entities.Professor;
import Entities.Student;
import Graphics.Utils.Vector2;
import Graphics.GameWindowPanel;
import Labyrinth.Room;

import java.awt.*;
import java.util.ArrayList;

public class RoomObject {

    /**
     * The room this room object represents.
     */
    private final Room room;
    private final Vector2 centerPos;
    private final GameWindowPanel gamePanel;

    public RoomObject(GameWindowPanel gamePanel, Vector2 centerPos, Room room) {
        this.gamePanel = gamePanel;
        this.centerPos = centerPos;
        this.room = room;
    }

    public void Draw(Graphics2D graphics2D) {

        int neighbours = room.GetNeighbours().size();
        neighbours = Math.max(neighbours, GameConstants.GamePanel_ROOM_MIN_SIDES);
        float angleBetween = 360f / neighbours;
        Vector2 distanceFromCenter = new Vector2(0, -GameConstants.GamePanel_ROOM_SIZE);

        Vector2 point;
        Polygon polygon = new Polygon();

        for (int i = 0; i < neighbours; i++) {
            point = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter,i * angleBetween));
            polygon.addPoint(point.x, point.y);
        }

        graphics2D.setColor(Color.pink);
        graphics2D.fillPolygon(polygon);

        DrawInside(graphics2D);
    }

    private void DrawInside(Graphics2D graphics2D) {
        int cap = room.GetCapacity();
        float angleBetween = 360f / cap;
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

        float doorAngleBetween = 360f / Math.max(room.GetNeighbours().size(), GameConstants.GamePanel_ROOM_MIN_SIDES);
        int doorDist = (int) ((float) GameConstants.GamePanel_ROOM_SIZE * Math.cos(Math.toRadians(doorAngleBetween / 2.0)));
        Vector2 doorDistanceFromCenter = new Vector2(0, -doorDist);
        doorDistanceFromCenter.RotateBy(doorAngleBetween / 2f);
        int drawnDoor = 0;

        for (Room neighbour : room.GetNeighbours()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(doorDistanceFromCenter,drawnDoor++ * doorAngleBetween));
            DoorObject door = new DoorObject(pos, neighbour);
            gamePanel.AddClickable(door);
            door.Draw(graphics2D, gamePanel.GetMousePosiotion());
        }
    }
}
