package Graphics.Utils.Clickable;

import Constants.GameConstants;
import Entities.Entity;
import Entities.Janitor;
import Entities.Professor;
import Entities.Student;
import Graphics.Utils.Vector2;
import Graphics.GameWindowPanel;
import Items.Item;
import Labyrinth.Room;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Runnable.Main.*;

public class RoomObject {

    /**
     * The room this room object represents.
     */
    private final Room room;
    private final Vector2 centerPos;
    private final GameWindowPanel gamePanel;
    private final boolean isSmallRoom;
    private HashMap<String, BufferedImage> skins = new HashMap<>();

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
        rectangle.x -= 2*GameConstants.WALL_SIZE;
        rectangle.y -= 2*GameConstants.WALL_SIZE;

        graphics2D.drawImage(GetRoomImage(polygon), rectangle.x , rectangle.y , null);

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

        // Entities
        final float angleBetween = 360f / room.GetCapacity();
        final int _dist = isSmallRoom ? (int) (50 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 50;
        Vector2 distanceFromCenter = new Vector2(_dist, 0);

        // todo different texture and consistent

        ArrayList<Entity> entities = room.GetEntities();
        int drawnEntities = 0;
        int students = 0;
        int professors = 0;
        int janitors = 0;

        // assigns skin to entity if it doesn't already have one, otherwise draws the skin
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


        // Items
        final float itemAng = 360f / room.GetInventory().size();
        final int itemDist = isSmallRoom ? (int) (90 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 90;
        Vector2 itemDistFromCenter = new Vector2(itemDist, 0);

        ArrayList<Item> items = (ArrayList<Item>) room.GetInventory();
        int drawnItems = 0;

        for (Item item : items) {
            Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(itemDistFromCenter,drawnItems++ * itemAng));
            ItemObject itemObject = new ItemObject(pos, item, !isSmallRoom);
            gamePanel.AddClickable(itemObject);
            itemObject.Draw(graphics2D, gameController.GetMousePosition());
        }

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
            door.Draw(graphics2D, gamePanel.GetMousePosition());
        }
        /*final float angleBetween = 360f / room.GetCapacity();
        final int _dist = isSmallRoom ? (int) (50 * GameConstants.SMALL_ROOM_SIZE_RATIO) : 50;
        Vector2 distanceFromCenter = new Vector2(_dist, 0);

        // Entities
        ArrayList<Entity> entities = room.GetEntities();
        int drawnEntities = 0;
        int students = 0;
        int professors = 0;
        int janitors = 0;

        for (Entity entity : entities) {
            BufferedImage image = null; // Reset image for each entity
            String imagePath = "";

            if (entity instanceof Student) {
                students++;
                switch (students) {
                    case 1 -> imagePath = GameConstants.IMAGE_STUDENT1;
                    case 2 -> imagePath = GameConstants.IMAGE_STUDENT2;
                    case 3 -> imagePath = GameConstants.IMAGE_STUDENT3;
                    case 4 -> imagePath = GameConstants.IMAGE_STUDENT4;
                }
            } else if (entity instanceof Professor) {
                professors++;
                switch (professors) {
                    case 1 -> imagePath = GameConstants.IMAGE_PROFESSOR1;
                    case 2 -> imagePath = GameConstants.IMAGE_PROFESSOR2;
                    case 3 -> imagePath = GameConstants.IMAGE_PROFESSOR3;
                    case 4 -> imagePath = GameConstants.IMAGE_PROFESSOR4;
                }
            } else if (entity instanceof Janitor) {
                janitors++;
                switch (janitors) {
                    case 1 -> imagePath = GameConstants.IMAGE_JANITOR1;
                    case 2 -> imagePath = GameConstants.IMAGE_JANITOR2;
                    case 3 -> imagePath = GameConstants.IMAGE_JANITOR3;
                    case 4 -> imagePath = GameConstants.IMAGE_JANITOR4;
                }
            }

            if (!imagePath.isEmpty()) {
                try {
                    image = ImageIO.read(new File(imagePath));
                    if (image != null) {
                        int newWidth = 70;
                        int newHeight = 30;
                        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                        // Convert the scaled Image back to BufferedImage
                        BufferedImage bufferedScaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedScaledImage.createGraphics();
                        g2d.drawImage(scaledImage, 0, 0, null);
                        g2d.dispose();

                        image = bufferedScaledImage; // Use the newly created buffered image
                    }
                } catch (IOException e) {
                    System.out.println("Image not found: " + imagePath);
                }

                if (image != null) { // Ensure that we have an image to draw
                    Vector2 pos = Vector2.Add(centerPos, Vector2.RotateBy(distanceFromCenter, drawnEntities++ * angleBetween));
                    System.out.println("Drawing entity at position: " + pos + " with image: " + imagePath);
                    graphics2D.drawImage(image, pos.x - image.getWidth() / 2, pos.y - image.getHeight() / 2, null);
                } else {
                    System.out.println("No image for entity: " + entity);
                    return;
                }
            }
        }*/
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
     * Draws a textured wall along the side of a polygon
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
        AffineTransform originalTransform = g.getTransform();
        double wallAngle = Math.PI/polygon.npoints;
        double z = GameConstants.WALL_SIZE / Math.cos(wallAngle);
        int x2n = (int) (x1+ Point2D.distance(x1,y1,x2,y2));
        int y2n = y1;
        int x3n = (int) (x2n + Math.sin(wallAngle)*z);
        int y3n = y2n - GameConstants.WALL_SIZE;
        int x4n = (int) (x1 - Math.sin(wallAngle)*z);
        int y4n = y3n;

        int[] xpoints = {x1, x2n, x3n, x4n};
        int[] ypoints = {y1, y2n, y3n, y4n};

        Polygon wall = new Polygon(xpoints, ypoints, 4);
        Rectangle wallBounds = wall.getBounds();

        // Calculate the angle of the wall segment
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Create a new TexturePaint with the original image
        BufferedImage wallImage = imageManager.GetImage(GameConstants.IMAGE_WALL);
        Rectangle2D anchorRect = new Rectangle2D.Float(wallBounds.x, wallBounds.y, GameConstants.WALL_SIZE, GameConstants.WALL_SIZE);
        TexturePaint wallPaint = new TexturePaint(wallImage, anchorRect);

        // Rotate the Graphics2D context to align the texture
        g.rotate(angle, x1, y1);
        g.setPaint(wallPaint);
        g.fill(wall);

        // Reset the transform
        g.setTransform(originalTransform);
    }
        public BufferedImage GetImageForEntity(Entity entity, int num) {
            String imagePath = null;
            num = num % 4;

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
            return (imagePath != null) ? imageManager.resizeImage(imagePath, 150) : null;
        }



}
