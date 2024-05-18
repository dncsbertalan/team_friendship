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
import javax.swing.*;

import static Runnable.Main.imageManager;
import static Runnable.Main.os;

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
        graphics2D.drawImage(GetRoomImage(polygon), rectangle.x - 10, rectangle.y - 10, null);

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
        }
    }

    /**
     * Returns an image of a textured polygon.
     * @param polygon   the polygon to be textured
     * @return  the image of the textured polygon
     */
    private BufferedImage GetRoomImage(Shape polygon) {
        Rectangle rectangle = polygon.getBounds();
        BufferedImage tmp = new BufferedImage(rectangle.width + 20,rectangle.height + 20,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = tmp.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        // creates a transform that centers the shape in the image
        AffineTransform centerTransform = AffineTransform.getTranslateInstance(-rectangle.x + 10, -rectangle.y + 10);
        g.setTransform(centerTransform);

        // clips the shape from the image
        g.setClip(polygon);
        g.drawImage(imageManager.GetImage(GameConstants.IMAGE_WALL_TMP), rectangle.x, rectangle.y, null);
        g.setClip(null);

        // outline
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(isSmallRoom ? 10f * GameConstants.SMALL_ROOM_SIZE_RATIO : 10f));
        g.draw(polygon);

        g.dispose();

        return tmp;
    }
}
