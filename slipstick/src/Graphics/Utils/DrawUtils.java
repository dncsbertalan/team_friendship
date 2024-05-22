package Graphics.Utils;

import Constants.GameConstants;
import Items.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Runnable.Main.imageManager;

public class DrawUtils {

    /**
     * Draws the item to the screen.
     * @param graphics2D    graphics2D instance
     * @param item          the item to be drawn
     * @param center        the center position of the item
     * @param scale         the scale of the item's image
     */
    public static void DrawItem(final Graphics2D graphics2D, final Item item, final Vector2 center, final float scale) {
        BufferedImage image = null;

        if (item instanceof AirFreshener) {
            image = imageManager.resizeImage(GameConstants.IMAGE_AIR_FRESHENER, scale);
        } else if (item instanceof Beer) {
            image = imageManager.resizeImage(GameConstants.IMAGE_BEER, scale);
        } else if (item instanceof Cheese) {
            image = imageManager.resizeImage(GameConstants.IMAGE_CHEESE, scale);
        } else if (item instanceof FFP2Mask) {
            image = imageManager.resizeImage(GameConstants.IMAGE_FFP2_MASK, scale);
        } else if (item instanceof SlipStick) {
            image = imageManager.resizeImage(GameConstants.IMAGE_SLIPSTICK, scale);
        } else if (item instanceof Transistor) {
            image = imageManager.resizeImage(GameConstants.IMAGE_TRANSISTOR, scale);
        } else if (item instanceof TVSZ) {
            image = imageManager.resizeImage(GameConstants.IMAGE_TVSZ, scale);
        } else if (item instanceof WetCloth) {
            image = imageManager.resizeImage(GameConstants.IMAGE_WET_CLOTH, scale);
        } else if (item instanceof Fake) {  // FAKE ITEM
            Item fakedItem = ((Fake) item).GetFakedItem();
            if (fakedItem != null) {
                DrawItem(graphics2D, fakedItem, center, scale);
            }
            return;
        }

        if (image != null) {
            graphics2D.drawImage(image, center.x - image.getWidth() / 2, center.y - image.getHeight() / 2, null);
        }
    }

    /**
     * Draws the item outline to the screen.
     * @param graphics2D    graphics2D instance
     * @param item          the item which's outline to be drawn
     * @param center        the center position of the item
     * @param scale         the scale of the item's image
     * @param unpickable    whether the pickable or not pickable outline is selected
     */
    public static void DrawItemOutline(final Graphics2D graphics2D, final Item item, final Vector2 center, final float scale, boolean unpickable) {

        BufferedImage image;

        if (item instanceof AirFreshener) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_AIR_FRESHENER_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_AIR_FRESHENER_OUTLINE, scale);
        }
        else if (item instanceof Beer) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_BEER_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_BEER_OUTLINE, scale);
        }
        else if (item instanceof Cheese) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_CHEESE_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_CHEESE_OUTLINE, scale);
        }
        else if (item instanceof FFP2Mask) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_FFP2_MASK_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_FFP2_MASK_OUTLINE, scale);
        }
        else if (item instanceof SlipStick) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_SLIPSTICK_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_SLIPSTICK_OUTLINE, scale);
        }
        else if (item instanceof Transistor) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_TRANSISTOR_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_TRANSISTOR_OUTLINE, scale);
        }
        else if (item instanceof TVSZ) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_TVSZ_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_TVSZ_OUTLINE, scale);
        }
        else if (item instanceof WetCloth) {
            image = unpickable ? imageManager.resizeImage(GameConstants.IMAGE_WET_CLOTH_OUTLINE_UNPICKABLE, scale)
                    : imageManager.resizeImage(GameConstants.IMAGE_WET_CLOTH_OUTLINE, scale);
        }
        else {  // FAKE ITEM
            image = null;
            if (unpickable) DrawItemOutline(graphics2D, ((Fake) item).GetFakedItem(), center, scale, unpickable);
            else DrawItemOutline(graphics2D, ((Fake) item).GetFakedItem(), center, scale, unpickable);
        }

        if (image == null) return;

        graphics2D.drawImage(image, center.x - image.getWidth() / 2, center.y - image.getHeight() / 2, null);
    }

    /**
     * Rotates the given image.
     * @param bimg  the image
     * @param angle the angle of rotation
     * @return      the rotated image
     */
    public static BufferedImage RotateImage(BufferedImage bimg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww-w)/2, (newh-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }

    /**
     * Draws the given image to the give position centered.
     * @param graphics2D    graphic2D instance
     * @param image         the image
     * @param centerPosition    the position
     */
    public static  void DrawImageCentered(Graphics2D graphics2D, BufferedImage image, Vector2 centerPosition) {
        graphics2D.drawImage(image, centerPosition.x - image.getWidth() / 2, centerPosition.y - image.getHeight() / 2, null);
    }
}
