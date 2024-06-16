package Graphics.Clickable;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Janitor;
import Entities.Professor;
import Entities.Student;
import Graphics.Utils.Vector2;
import Graphics.GameWindowPanel;
import Items.Item;
import Labyrinth.Room;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static Runnable.Main.*;

public class RoomObject {

    /**
     * The room this room object represents.
     */
    private final Room room;
    private final Vector2 centerPos;
    private final GameWindowPanel gamePanel;
    private final boolean isSmallRoom;
    private final boolean specialHexaR;
    private final static HashMap<String, BufferedImage> skins = new HashMap<>();
    private float rotation;
    private final int indexOfMainRoom;
    private Polygon shape;

    public RoomObject(GameWindowPanel gamePanel, Vector2 centerPos, Room room, boolean isSmallRoom) {
        this(gamePanel, centerPos, room, isSmallRoom, 0, 0, false);
    }

    public RoomObject(GameWindowPanel gamePanel, Vector2 centerPos, Room room, boolean isSmallRoom,
                      float rotation, int indexOfMainRoom, boolean specialHexaR) {
        this.gamePanel = gamePanel;
        this.centerPos = centerPos;
        this.room = room;
        this.isSmallRoom = isSmallRoom;
        this.specialHexaR = specialHexaR;
        this.rotation = rotation;
        this.indexOfMainRoom = indexOfMainRoom;
        this.shape = createShape();
    }

    private Polygon createShape() {
        //Make a polygon at least 3 sides
        int neighbours = room.GetNeighbours().size();
        neighbours = Math.max(neighbours, GameConstants.ROOM_MIN_SIDES);
        final float angleBetween = 360f / neighbours;
        final int y = isSmallRoom ? (int) (GameConstants.ROOM_SIZE * GameConstants.SMALL_ROOM_SIZE_RATIO) : GameConstants.ROOM_SIZE;
        final Vector2 distanceFromCenter = new Vector2(0, -y);

        if (rotation != 0) {
            if (specialHexaR)
                rotation = 0;
            final float doorAng = 360f / Math.max(room.GetNeighbours().size(), GameConstants.ROOM_MIN_SIDES);
            final float angleOfConnectingDoor = doorAng / 2f + doorAng * indexOfMainRoom - 90;
            final float rotationReversed = rotation - 180;
            this.rotation = rotationReversed - angleOfConnectingDoor;
            distanceFromCenter.RotateBy(rotation);
        }

        Vector2 point;
        Polygon polygon = new Polygon();

        for (int i = 0; i < neighbours; i++) {
            point = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter, i * angleBetween));
            polygon.addPoint(point.x, point.y);
        }

        return polygon;
    }

    /**
     * Draws the room and its content to the screen.
     * @param graphics2D graphics2D instance
     */
    public void Draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.pink);
        Rectangle rectangle = shape.getBounds();
        rectangle.x -= 2*GameConstants.WALL_SIZE;
        rectangle.y -= 2*GameConstants.WALL_SIZE;

        graphics2D.drawImage(GetRoomImage(shape), rectangle.x , rectangle.y , null);

        // Draw the inside
        DrawInside(graphics2D);

        // Room name
        Font font = new Font("Courier New", Font.BOLD, isSmallRoom ? 25 : 35);
        graphics2D.setFont(font);
        Color roomNameColor = isSmallRoom ?
                new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 255) :
                new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), 255);
        graphics2D.setColor(roomNameColor);
        Rectangle2D bounding = graphics2D.getFontMetrics().getStringBounds(room.GetName(), graphics2D);
        Vector2 pos = isSmallRoom ?
                new Vector2(centerPos.x - (int) bounding.getCenterX(), centerPos.y - (int) bounding.getCenterY()) :
                new Vector2(20, 150);
        graphics2D.drawString(room.GetName(), pos.x, pos.y - 15);
        if (room.IsGassed()) {
            graphics2D.drawString(
                    "GASSED", isSmallRoom ? pos.x : pos.x + (int) bounding.getWidth() + 10, isSmallRoom ? pos.y + (int) bounding.getHeight() - 15 : pos.y);
            if (room.GetRemainingRoundsGassed() > 0)
                graphics2D.drawString(
                        "       FOR " + room.GetRemainingRoundsGassed(), isSmallRoom ? pos.x : pos.x + (int) bounding.getWidth() + 10, isSmallRoom ? pos.y + (int) bounding.getHeight() - 15 : pos.y);
        }
        if (room.IsSticky()) graphics2D.drawString(
                "STICKY", isSmallRoom ? pos.x : pos.x + (int) bounding.getWidth() + 10, isSmallRoom ? pos.y + (int) bounding.getHeight() - 15 : pos.y);
        if (room.IsCleaned()) graphics2D.drawString(
                "CLEANED", isSmallRoom ? pos.x : pos.x + (int) bounding.getWidth() + 10, isSmallRoom ? pos.y + (int) bounding.getHeight() - 15 : pos.y);

        if (game.IsLastPhase())
            highlightShortestPath(graphics2D);
    }

    private void highlightShortestPath(Graphics2D graphics2D) {
        List<Room> shortestPath = game.GetMap().findShortestPath(
                game.GetHuntedStudent().GetCurrentRoom(),
                game.GetMap().GetWinningRoom());

        if (shortestPath == null || shortestPath.isEmpty()) {
            return;
        }

        graphics2D.setColor(Color.YELLOW);
        graphics2D.setStroke(new BasicStroke(8));

        if (shortestPath.contains(room))
            graphics2D.drawPolygon(shape);
    }

    /**
     * Draws the inside of the room
     * @param graphics2D graphics2D instance
     */
    private void DrawInside(Graphics2D graphics2D) {

        // Entities ====================================================================================================
        DrawEntities(graphics2D);

        // Items =======================================================================================================
        DrawItems(graphics2D);

        // Doors =======================================================================================================
        DrawDoors(graphics2D);
    }

    private  void DrawEntities(Graphics2D graphics2D) {
        final float angleBetween = 360f / room.GetCapacity();
        final int _dist = isSmallRoom ? (int) (50 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 50;
        Vector2 distanceFromCenter = new Vector2(_dist, 0);

        ArrayList<Entity> entities = room.GetEntities();
        int drawnEntities = 0;
        int students = 0;
        int professors = 0;
        int janitors = 0;

        // Assigns skin to entity if it doesn't already have one, otherwise draws the skin
        for (Entity entity : entities) {
            BufferedImage entityImage = null;
            if(!skins.containsKey(entity.GetName())) {
                //System.out.println("ÚJ ENTITY" + entity.GetName());
                //skins.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
                if (entity instanceof Student) {
                    entityImage = GetImageForEntity(entity, ++students);
                }
                if (entity instanceof Professor) {
                    entityImage = GetImageForEntity(entity, ++professors);
                }
                if (entity instanceof Janitor) {
                    entityImage = GetImageForEntity(entity, ++janitors);
                }
                if (entityImage != null) {
                    skins.put(entity.GetName(), entityImage);
                    Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter, drawnEntities++ * angleBetween));
                    graphics2D.drawImage(entityImage, pos.x - entityImage.getWidth() / 2, pos.y - entityImage.getHeight() / 2, null);
                } else {
                    System.out.println("No image for entity: " + entity.GetName());
                }
            }else{
                entityImage = skins.get(entity.GetName());
                Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter, drawnEntities++ * angleBetween));
                graphics2D.drawImage(entityImage, pos.x - entityImage.getWidth() / 2, pos.y - entityImage.getHeight() / 2, null);
            }
        }
    }

    private void DrawItems(Graphics2D graphics2D) {
        final float itemAng = 360f / (room.GetInventory().size() + room.GetUnpickupableItems().size());
        final int itemDist = isSmallRoom ? (int) (90 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 90;
        Vector2 itemDistFromCenter = new Vector2(itemDist, 0);

        int drawnItems = 0;

        // Pickupable
        for (Item item : room.GetInventory()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(itemDistFromCenter,drawnItems++ * itemAng));
            ItemObject itemObject = new ItemObject(pos, item, 200, !isSmallRoom, false);
            gamePanel.AddClickable(itemObject);
            itemObject.Draw(graphics2D, gameController.GetMousePosition());
        }

        // Unpickupable
        for (Item item : room.GetUnpickupableItems()) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(itemDistFromCenter,drawnItems++ * itemAng));
            ItemObject itemObject = new ItemObject(pos, item, 200, !isSmallRoom, true);
            gamePanel.AddClickable(itemObject);
            itemObject.Draw(graphics2D, gameController.GetMousePosition());
        }
    }

    private void DrawDoors(Graphics2D graphics2D) {
        final float doorAng = 360f / Math.max(room.GetNeighbours().size(), GameConstants.ROOM_MIN_SIDES);
        final int y = isSmallRoom ? (int) (GameConstants.ROOM_SIZE * GameConstants.SMALL_ROOM_SIZE_RATIO) : GameConstants.ROOM_SIZE;
        final int dist = (int) ((float) y * Math.cos(Math.toRadians(doorAng / 2.0)));
        final int doorOffset = isSmallRoom ? (int) (24 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 24;
        Vector2 doorPosFromCenter = new Vector2(0, -dist - doorOffset);
        doorPosFromCenter.RotateBy(doorAng / 2f);
        doorPosFromCenter.RotateBy(rotation);
        int drawnDoor = 0;

        for (Room neighbour : room.GetNeighbours()) {
            Vector2 doorPos = Vector2.RotateBy(doorPosFromCenter,drawnDoor++ * doorAng);
            Vector2 pos = Vector2.Add(centerPos, doorPos);
            float doorScale = isSmallRoom ? 90 * GameConstants.SMALL_ROOM_SIZE_RATIO : 90;
            DoorObject door = new DoorObject(pos, neighbour, doorScale,(float) Math.toDegrees(doorPos.ToRotation()) + 90, !isSmallRoom);
            gamePanel.AddClickable(door);
            door.Draw(graphics2D, gamePanel.GetMousePosition());
        }
    }

    /**
     * Returns an image of a textured polygon.
     * @param polygon   the polygon to be textured
     * @return  the image of the textured polygon
     */
    private BufferedImage GetRoomImage(Polygon polygon) {
        Rectangle rectangle = polygon.getBounds();

        BufferedImage tmp = new BufferedImage(rectangle.width + 4*GameConstants.WALL_SIZE,rectangle.height + 4*GameConstants.WALL_SIZE,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = tmp.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        // creates a transform that centers the shape in the image
        AffineTransform centerTransform = AffineTransform.getTranslateInstance(-rectangle.x + 2*GameConstants.WALL_SIZE, -rectangle.y + 2*GameConstants.WALL_SIZE);
        g.setTransform(centerTransform);

        // Create TexturePaint
        BufferedImage bg_image = imageManager.GetImage(GameConstants.IMAGE_BG);
        TexturePaint texturePaint = new TexturePaint(bg_image, new Rectangle2D.Float(rectangle.x, rectangle.y, bg_image.getWidth(), bg_image.getHeight()));

        // Fill shape with TexturePaint
        g.setPaint(texturePaint);
        g.fill(polygon);

        // Draw trapezoids facing outwards along each edge of the polygon
        for (int i = 0; i < polygon.npoints; i++) {
            drawTexturedWall(g, polygon, i);
        }
        g.dispose();
        return tmp;
    }

    /**
     * Draws a textured wall along the side of a polygon.
     * @param g Graphics2D
     * @param polygon the polygon
     * @param i the index of the side to be drawn on
     */
    private void drawTexturedWall(Graphics2D g, Polygon polygon, int i) {
        //get base corners
        int x1 = polygon.xpoints[i];
        int y1 = polygon.ypoints[i];
        int x2 = polygon.xpoints[(i + 1) % polygon.npoints];
        int y2 = polygon.ypoints[(i + 1) % polygon.npoints];

        // Calculate the remaining coordinates for the trapezoid
        final int wallSize = isSmallRoom ? (int) (GameConstants.WALL_SIZE * GameConstants.SMALL_ROOM_SIZE_RATIO) : GameConstants.WALL_SIZE;

        AffineTransform originalTransform = g.getTransform();
        double wallAngle = Math.PI/polygon.npoints;
        double z = wallSize / Math.cos(wallAngle);
        int x2n = (int) (x1+ Point2D.distance(x1,y1,x2,y2));
        int y2n = y1;
        int x3n = (int) (x2n + Math.sin(wallAngle)*z);
        int y3n = y2n - wallSize;
        int x4n = (int) (x1 - Math.sin(wallAngle)*z);
        int y4n = y3n;

        int[] xpoints = {x1-1, x2n+1, x3n+1, x4n-1};
        int[] ypoints = {y1, y2n, y3n, y4n};

        Polygon wall = new Polygon(xpoints, ypoints, 4);
        Rectangle wallBounds = wall.getBounds();

        // Calculate the angle of the wall segment
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Create a new TexturePaint with the original image
        BufferedImage wallImage = imageManager.GetImage(GameConstants.IMAGE_WALL);
        Rectangle2D anchorRect = new Rectangle2D.Float(wallBounds.x, wallBounds.y, wallSize, wallSize);
        TexturePaint wallPaint = new TexturePaint(wallImage, anchorRect);

        // Rotate the Graphics2D context to align the texture
        g.rotate(angle, x1, y1);
        g.setPaint(wallPaint);
        g.fill(wall);

        // Reset the transform
        g.setTransform(originalTransform);
    }

    private BufferedImage GetImageForEntity(Entity entity, int num) {
        String imagePath = null;
        num = num % 4;

        // Ez ide f
        Map<Integer, String> StudentImageMap = new HashMap<>();
        Map<Integer, String> ProfessorImageMap = new HashMap<>();
        Map<Integer, String> JanitorImageMap = new HashMap<>();

        // Add mappings for Students
        StudentImageMap.put(0, GameConstants.IMAGE_STUDENT1);
        StudentImageMap.put(1, GameConstants.IMAGE_STUDENT2);
        StudentImageMap.put(2, GameConstants.IMAGE_STUDENT3);
        StudentImageMap.put(3, GameConstants.IMAGE_STUDENT4);
        // Add mappings for Professors
        ProfessorImageMap.put(0, GameConstants.IMAGE_PROFESSOR1);
        ProfessorImageMap.put(1, GameConstants.IMAGE_PROFESSOR2);
        ProfessorImageMap.put(2, GameConstants.IMAGE_PROFESSOR3);
        ProfessorImageMap.put(3, GameConstants.IMAGE_PROFESSOR4);
        // Add mappings for Janitors
        JanitorImageMap.put(0, GameConstants.IMAGE_JANITOR1);
        JanitorImageMap.put(1, GameConstants.IMAGE_JANITOR2);
        JanitorImageMap.put(2, GameConstants.IMAGE_JANITOR3);
        JanitorImageMap.put(3, GameConstants.IMAGE_JANITOR4);

            if(entity instanceof Student) {
                imagePath = StudentImageMap.get(num);
            }if(entity instanceof Professor) {
                imagePath = ProfessorImageMap.get(num);
            }if(entity instanceof Janitor) {
                imagePath = JanitorImageMap.get(num);
            }
            return (imagePath != null) ? imageManager.resizeImage(imagePath, GameConstants.CHARACTERIZE) : null;
        }
}
