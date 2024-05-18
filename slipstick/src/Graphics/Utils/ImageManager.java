package Graphics.Utils;

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
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

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

        loadImageAs(GameConstants.IMAGE_WALL_TMP, GameConstants.IMAGE_WALL_TMP_FILEPATH);

        loadImageAs(GameConstants.IMAGE_AIR_FRESHENER, GameConstants.IMAGE_AIR_FRESHENER_FILEPATH);
        loadImageAs(GameConstants.IMAGE_BEER, GameConstants.IMAGE_BEER_FILEPATH);
        loadImageAs(GameConstants.IMAGE_CHEESE, GameConstants.IMAGE_CHEESE_FILEPATH);
        loadImageAs(GameConstants.IMAGE_WET_CLOTH, GameConstants.IMAGE_WALL_TMP_FILEPATH);
        loadImageAs(GameConstants.IMAGE_FFP2_MASK, GameConstants.IMAGE_FFP2_MASK_FILEPATH);
        loadImageAs(GameConstants.IMAGE_SLIPSTICK, GameConstants.IMAGE_SLIPSTICK_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TRANSISTOR, GameConstants.IMAGE_TRANSISTOR_FILEPATH);
        loadImageAs(GameConstants.IMAGE_TVSZ, GameConstants.IMAGE_TVSZ_FILEPATH);
    }
}
