package Graphics;

import Constants.GameConstants;
import GameManagers.RoundManager;
import Graphics.Utils.ErrorMessage;
import Graphics.Utils.MenuButton;
import Graphics.Utils.PlayerNameLabel;
import Graphics.Utils.PlayerNameTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import static Runnable.Main.game;
import static Runnable.Main.menu;
import static Runnable.Main.soundManager;

public class MenuWindowPanel2 extends JPanel implements ActionListener {
    private final JButton doneButton;
    private final JButton cancelButton;
    private final List<JTextField> nameFields = new ArrayList<>();

    private final MenuWindowFrame menuWF;

    public MenuWindowPanel2(MenuWindowFrame frame) {
        menuWF = frame;

        // Panel configuration
        this.setBackground(GameConstants.MenuPanel2_BACKGROUND_COLOR);
        this.setPreferredSize(new Dimension(GameConstants.MenuPanel2_WIDTH, GameConstants.MenuPanel2_HEIGHT));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 40); // Margin between components

        // Create a panel for the text fields and labels
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(GameConstants.MenuPanel2_BACKGROUND_COLOR);

        // Adding text fields
        for (int i = 0; i < 4; i++) {
            int playerNum = i + 1;
            addPlayerTextFields(fieldsPanel, gbc, "Player " + playerNum + ":", i);
        }

        // Create a nested panel for the buttons with FlowLayout
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Align buttons to the right
        buttonsPanel.setBackground(GameConstants.MenuPanel2_BACKGROUND_COLOR);

        // Initializing done button
        doneButton = new MenuButton(
                GameConstants.MenuPanel2_DONE_BUTTON,
                GameConstants.MenuPanel2_BUTTON_BACKGROUND_COLOR,
                GameConstants.MenuPanel2_BUTTON_BORDER_COLOR,
                GameConstants.MenuPanel2_BUTTON_BORDER_THICKNESS);
        doneButton.setFont(GameConstants.MenuPanel2_BUTTON_FONT);
        doneButton.setPreferredSize(new Dimension(GameConstants.MenuPanel2_BUTTON_WIDTH, GameConstants.MenuPanel2_BUTTON_HEIGHT));
        doneButton.setMinimumSize(new Dimension(GameConstants.MenuPanel2_BUTTON_WIDTH, GameConstants.MenuPanel2_BUTTON_HEIGHT));

        // Initializing cancel button
        cancelButton = new MenuButton(
                GameConstants.MenuPanel2_CANCEL_BUTTON,
                GameConstants.MenuPanel2_BUTTON_BACKGROUND_COLOR,
                GameConstants.MenuPanel2_BUTTON_BORDER_COLOR,
                GameConstants.MenuPanel2_BUTTON_BORDER_THICKNESS);
        cancelButton.setFont(GameConstants.MenuPanel2_BUTTON_FONT);
        cancelButton.setPreferredSize(new Dimension(GameConstants.MenuPanel2_BUTTON_WIDTH, GameConstants.MenuPanel2_BUTTON_HEIGHT));
        cancelButton.setMinimumSize(new Dimension(GameConstants.MenuPanel2_BUTTON_WIDTH, GameConstants.MenuPanel2_BUTTON_HEIGHT));

        // Adding buttons to the buttons panel
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);

        // Adding buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span buttons across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10); // Margin between components
        this.add(buttonsPanel, gbc);

        // Adding action listeners
        doneButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Create and add "Names" label
        JLabel namesLabel = new JLabel(GameConstants.MenuPanel2_NAMES_LABEL);
        namesLabel.setFont(GameConstants.MenuPanel2_NAMES_LABEL_FONT);
        namesLabel.setHorizontalAlignment(JLabel.CENTER);
        namesLabel.setForeground(GameConstants.MenuPanel2_NAMES_LABEL_COLOR);

        // Adding the "Names" label to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span the label across two columns
        gbc.insets = new Insets(10, 200, 10, 10); // Align with the text fields
        this.add(namesLabel, gbc);

        // Add the fields panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 40); // Margin between components
        this.add(fieldsPanel, gbc);
    }

    private void addPlayerTextFields(JPanel panel, GridBagConstraints gbc, String labelText, int row) {
        JLabel label = new PlayerNameLabel(labelText, GameConstants.MenuPanel2_TEXTFIELD_LABEL_BORDER_THICKNESS);
        label.setFont(GameConstants.MenuPanel2_TEXTFIELD_LABEL_FONT);
        label.setMinimumSize(new Dimension(GameConstants.MenuPanel2_TEXTFIELD_LABEL_WIDTH, GameConstants.MenuPanel2_TEXTFIELD_LABEL_HEIGHT));
        label.setHorizontalAlignment(JLabel.CENTER);
        PlayerNameTextField nameField = new PlayerNameTextField(GameConstants.MenuPanel2_NAME_FIELD_WIDTH, GameConstants.MenuPanel2_TEXTFIELD_BORDER_THICKNESS);
        nameField.setFont(GameConstants.MenuPanel2_TEXTFIELD_FONT);
        nameField.setMinimumSize(new Dimension(GameConstants.MenuPanel2_TEXTFIELD_WIDTH, GameConstants.MenuPanel2_TEXTFIELD_HEIGHT));
        nameField.setHorizontalAlignment(JTextField.CENTER);

        // Add the text field to the list
        nameFields.add(nameField);

        // Adding label and text field to the panel
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param event the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(doneButton)) {         // DONE BUTTON
            boolean atLeastOneNameGiven = false;

            for (JTextField textField : nameFields) {
                if (!textField.getText().isEmpty()) {
                    atLeastOneNameGiven = true;
                    menu.AddPlayer(textField.getText());
                }
            }

            // Show error message when no names were given
            if (!atLeastOneNameGiven) {
                ErrorMessage popup = new ErrorMessage(menuWF, GameConstants.MenuPanel2_ERROR_MESSAGE);
                popup.setVisible(true);
                return;
            }

            menuWF.SetMenuPanel1Visible();
            menuWF.setVisible(false);
            GameWindowFrame gameWindowFrame = new GameWindowFrame(menuWF);
            menu.StartGame();
            soundManager.playSoundLooped("game");
        }
        if (event.getSource().equals(cancelButton)) {       // CANCEL BUTTON
            menuWF.SetMenuPanel1Visible();
        }
    }
}
