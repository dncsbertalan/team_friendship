package Graphics;

import Constants.GameConstants;
import Graphics.Utils.MenuButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Runnable.Main.*;

public class MenuWindowPanel1 extends JPanel implements ActionListener {
    private final JButton exitButton;
    private final JButton playButton;
    private JLabel logoLabel = null;

    private final MenuWindowFrame menuWF;

    public MenuWindowPanel1(MenuWindowFrame frame) {
        menuWF = frame;

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.setBackground(GameConstants.MenuPanel1_BACKGROUND_COLOR);
        this.setPreferredSize(new Dimension(GameConstants.MenuPanel1_WIDTH, GameConstants.MenuPanel1_HEIGHT));

        try {
            Image logo = ImageIO.read(new File(GameConstants.MenuPanel1_LOGO_FILEPATH));
            int newWidth = GameConstants.MenuPanel1_LOGO_RESIZE_WIDTH;
            int newHeight = GameConstants.MenuPanel1_LOGO_RESIZE_HEIGHT;
            Image scaledLogo = logo.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                logoLabel = new JLabel(new ImageIcon(scaledLogo));
        } catch (IOException e) {
            os.println("Logo not found.");
        }

        // Initialize buttons
        exitButton = new MenuButton(
                GameConstants.MenuPanel1_EXIT_BUTTON,
                GameConstants.MenuPanel1_BUTTON_BACKGROUND_COLOR,
                GameConstants.MenuPanel1_BUTTON_BORDER_COLOR,
                GameConstants.MenuPanel1_BUTTON_BORDER_THICKNESS);
        playButton = new MenuButton(
                GameConstants.MenuPanel1_PLAY_BUTTON,
                GameConstants.MenuPanel1_BUTTON_BACKGROUND_COLOR,
                GameConstants.MenuPanel1_BUTTON_BORDER_COLOR,
                GameConstants.MenuPanel1_BUTTON_BORDER_THICKNESS);

        exitButton.setFont(GameConstants.MenuPanel1_BUTTON_FONT);
        playButton.setFont(GameConstants.MenuPanel1_BUTTON_FONT);

        exitButton.setPreferredSize(new Dimension(GameConstants.MenuPanel1_BUTTON_WIDTH, GameConstants.MenuPanel1_BUTTON_HEIGHT));
        playButton.setPreferredSize(new Dimension(GameConstants.MenuPanel1_BUTTON_WIDTH, GameConstants.MenuPanel1_BUTTON_HEIGHT));

        playButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Add components using GridBagConstraints

        // Add logo label
        if (logoLabel != null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span across two columns
            gbc.insets = new Insets(0, 10, 180, 10); // Bottom padding to increase the gap between the image and the buttons
            gbc.anchor = GridBagConstraints.NORTH;
            this.add(logoLabel, gbc);
        }

        // Add play button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Single column
        gbc.insets = new Insets(0, 10, 10, 70); // Right padding to increase the gap between the 2 buttons
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(playButton, gbc);

        // Add exit button
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 70, 10, 10); // Left padding to increase the gap between the 2 buttons
        this.add(exitButton, gbc);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param event the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource().equals(playButton)) {         // PLAY BUTTON
            menuWF.SetMenuPanel2Visible();
        }
        if (event.getSource().equals(exitButton)) {         // EXIT BUTTON
            gameController.StopGame();
            System.exit(0);
        }
    }

}
