package Graphics.Managers;

import Constants.GameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;

public class ImageManager {
    private final HashMap<String, BufferedImage> images;

    public ImageManager() {
        images = new HashMap<>();
        loadImageAs(GameConstants.IMAGE_LOGO, GameConstants.IMAGE_LOGO_FILEPATH);
    }

    // Method to load an image file and put it in the images' hashmap
    public void loadImageAs(String key, String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            images.put(key, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to display a specific image in a provided JLabel
    public JLabel displayImage(String key) {
        BufferedImage image = images.get(key);
        if (image != null) {
            return new JLabel(new ImageIcon(image));
        }
        return null;
    }

    // Method to resize an image by a percentage
    public BufferedImage resizeImage(String key, double percentage) {
        BufferedImage originalImage = images.get(key);
        if (originalImage != null) {
            int newWidth = (int) (originalImage.getWidth() * (percentage / 100));
            int newHeight = (int) (originalImage.getHeight() * (percentage / 100));

            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            return resizedImage;
        }
        return null;
    }

    /**
     * Return the image paired with the given key.
     * @param key   the key of the image
     * @return  the image
     */
    public BufferedImage GetImage(String key) {
        return images.get(key);
    }

    /**
     * Loads the game images.
     */
    public void LoadGameImages() {

        loadImageAs(GameConstants.IMAGE_BG, GameConstants.IMAGE_BG_FILEPATH);
        loadImageAs(GameConstants.IMAGE_WALL, GameConstants.IMAGE_WALL_FILEPATH);

        loadImageAs(GameConstants.IMAGE_DOOR, GameConstants.IMAGE_DOOR_FILEPATH);
        loadImageAs(GameConstants.IMAGE_DOOR_OUTLINE, GameConstants.IMAGE_DOOR_OUTLINE_FILEPATH);

        // Items
        loadImageAs(GameConstants.IMAGE_AIR_FRESHENER, GameConstants.IMAGE_AIR_FRESHENER_FILEPATH);
        loadImageAs(GameConstants.IMAGE_BEER, GameConstants.IMAGE_BEER_FILEPATH);
        loadImageAs(GameConstants.IMAGE_CHEESE, GameConstants.IMAGE_CHEESE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_WET_CLOTH, GameConstants.IMAGE_WET_CLOTH_FILEPATH);
        loadImageAs(GameConstants.IMAGE_FFP2_MASK, GameConstants.IMAGE_FFP2_MASK_FILEPATH);
        loadImageAs(GameConstants.IMAGE_SLIPSTICK, GameConstants.IMAGE_SLIPSTICK_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TRANSISTOR, GameConstants.IMAGE_TRANSISTOR_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TVSZ, GameConstants.IMAGE_TVSZ_FILEPATH);

        // Item outlines
        loadImageAs(GameConstants.IMAGE_AIR_FRESHENER_OUTLINE, GameConstants.IMAGE_AIR_FRESHENER_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_BEER_OUTLINE, GameConstants.IMAGE_BEER_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_CHEESE_OUTLINE, GameConstants.IMAGE_CHEESE_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_WET_CLOTH_OUTLINE, GameConstants.IMAGE_WET_CLOTH_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_FFP2_MASK_OUTLINE, GameConstants.IMAGE_FFP2_MASK_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_SLIPSTICK_OUTLINE, GameConstants.IMAGE_SLIPSTICK_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TRANSISTOR_OUTLINE, GameConstants.IMAGE_TRANSISTOR_OUTLINE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TVSZ_OUTLINE, GameConstants.IMAGE_TVSZ_OUTLINE_FILEPATH);

        // Item outlines unpickable
        loadImageAs(GameConstants.IMAGE_AIR_FRESHENER_OUTLINE_UNPICKABLE, GameConstants.IMAGE_AIR_FRESHENER_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_BEER_OUTLINE_UNPICKABLE, GameConstants.IMAGE_BEER_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_CHEESE_OUTLINE_UNPICKABLE, GameConstants.IMAGE_CHEESE_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_WET_CLOTH_OUTLINE_UNPICKABLE, GameConstants.IMAGE_WET_CLOTH_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_FFP2_MASK_OUTLINE_UNPICKABLE, GameConstants.IMAGE_FFP2_MASK_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_SLIPSTICK_OUTLINE_UNPICKABLE, GameConstants.IMAGE_SLIPSTICK_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TRANSISTOR_OUTLINE_UNPICKABLE, GameConstants.IMAGE_TRANSISTOR_OUTLINE_UNPICKABLE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TVSZ_OUTLINE_UNPICKABLE, GameConstants.IMAGE_TVSZ_OUTLINE_UNPICKABLE_FILEPATH);

        loadImageAs(GameConstants.IMAGE_STUDENT1, GameConstants.IMAGE_STUDENT1_FILEPATH);
        loadImageAs(GameConstants.IMAGE_STUDENT2, GameConstants.IMAGE_STUDENT2_FILEPATH);
        loadImageAs(GameConstants.IMAGE_STUDENT3, GameConstants.IMAGE_STUDENT3_FILEPATH);
        loadImageAs(GameConstants.IMAGE_STUDENT4, GameConstants.IMAGE_STUDENT4_FILEPATH);
        loadImageAs(GameConstants.IMAGE_PROFESSOR1, GameConstants.IMAGE_PROFESSOR1_FILEPATH);
        loadImageAs(GameConstants.IMAGE_PROFESSOR2, GameConstants.IMAGE_PROFESSOR2_FILEPATH);
        loadImageAs(GameConstants.IMAGE_PROFESSOR3, GameConstants.IMAGE_PROFESSOR3_FILEPATH);
        loadImageAs(GameConstants.IMAGE_PROFESSOR4, GameConstants.IMAGE_PROFESSOR4_FILEPATH);
        loadImageAs(GameConstants.IMAGE_JANITOR1, GameConstants.IMAGE_JANITOR1_FILEPATH);
        loadImageAs(GameConstants.IMAGE_JANITOR2, GameConstants.IMAGE_JANITOR2_FILEPATH);
        loadImageAs(GameConstants.IMAGE_JANITOR3, GameConstants.IMAGE_JANITOR3_FILEPATH);
        loadImageAs(GameConstants.IMAGE_JANITOR4, GameConstants.IMAGE_JANITOR4_FILEPATH);
    }
}
