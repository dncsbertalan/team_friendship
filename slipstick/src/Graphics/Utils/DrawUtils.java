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

        BufferedImage image;

        if (item instanceof AirFreshener) {
            image = imageManager.resizeImage(GameConstants.IMAGE_AIR_FRESHENER, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_AIR_FRESHENER);
        }
        else if (item instanceof Beer) {
            image = imageManager.resizeImage(GameConstants.IMAGE_BEER, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_BEER);
        }
        else if (item instanceof Cheese) {
            image = imageManager.resizeImage(GameConstants.IMAGE_CHEESE, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_CHEESE);
        }
        else if (item instanceof FFP2Mask) {
            image = imageManager.resizeImage(GameConstants.IMAGE_FFP2_MASK, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_FFP2_MASK);
        }
        else if (item instanceof SlipStick) {
            //image = imageManager.resizeImage(GameConstants.IMAGE_SLIPSTICK, scale);
            image = imageManager.GetImage(GameConstants.IMAGE_SLIPSTICK);
        }
        else if (item instanceof Transistor) {
            image = imageManager.resizeImage(GameConstants.IMAGE_TRANSISTOR, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_TRANSISTOR);
        }
        else if (item instanceof TVSZ) {
            image = imageManager.resizeImage(GameConstants.IMAGE_TVSZ, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_TVSZ);
        }
        else if (item instanceof WetCloth) {
            image = imageManager.resizeImage(GameConstants.IMAGE_WET_CLOTH, scale);
            //image = imageManager.GetImage(GameConstants.IMAGE_WET_CLOTH);
        }
        else {  // FAKE ITEM
            image = imageManager.resizeImage("temp", scale);
            //image = imageManager.GetImage("temp");
        }

        if (image == null) return;

        graphics2D.drawImage(image, center.x - image.getWidth() / 2, center.y - image.getHeight() / 2, null);
    }


}
