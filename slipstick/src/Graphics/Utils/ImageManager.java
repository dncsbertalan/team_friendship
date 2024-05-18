package Graphics.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;

public class ImageManager {
    private HashMap<String, BufferedImage> images;

    public ImageManager() {
        images = new HashMap<>();
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
}
