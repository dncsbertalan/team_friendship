package Graphics;

import Constants.GameConstants;
import GameManagers.RoundManager;
import Graphics.Utils.MenuButton;
import Graphics.Utils.PlayerNameLabel;
import Graphics.Utils.PlayerNameTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Runnable.Main.*;

public class MenuWindowPanel2 extends JPanel implements ActionListener {
    private final JButton doneButton;
    private final JButton cancelButton;
    private JTextField nameField1;
    private JTextField nameField2;
    private JTextField nameField3;
    private JTextField nameField4;

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
                GameConstants.MenuPanel2_BUTTON_BORDER_COLOR);
        doneButton.setFont(GameConstants.MenuPanel2_BUTTON_FONT);
        doneButton.setPreferredSize(new Dimension(GameConstants.MenuPanel2_BUTTON_WIDTH, GameConstants.MenuPanel2_BUTTON_HEIGHT));
        doneButton.setMinimumSize(new Dimension(GameConstants.MenuPanel2_BUTTON_WIDTH, GameConstants.MenuPanel2_BUTTON_HEIGHT));

        // Initializing cancel button
        cancelButton = new MenuButton(
                GameConstants.MenuPanel2_CANCEL_BUTTON,
                GameConstants.MenuPanel2_BUTTON_BACKGROUND_COLOR,
                GameConstants.MenuPanel2_BUTTON_BORDER_COLOR);
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

        // Add the fields panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(fieldsPanel, gbc);
    }

    private void addPlayerTextFields(JPanel panel, GridBagConstraints gbc, String labelText, int row) {
        JLabel label = new PlayerNameLabel(labelText);
        label.setFont(GameConstants.MenuPanel2_TEXTFIELD_LABEL_FONT);
        label.setMinimumSize(new Dimension(GameConstants.MenuPanel2_TEXTFIELD_LABEL_WIDTH, GameConstants.MenuPanel2_TEXTFIELD_LABEL_HEIGHT));
        label.setHorizontalAlignment(JLabel.CENTER);
        PlayerNameTextField nameField = new PlayerNameTextField(GameConstants.MenuPanel2_NAME_FIELD_WIDTH);
        nameField.setFont(GameConstants.MenuPanel2_TEXTFIELD_FONT);
        nameField.setMinimumSize(new Dimension(GameConstants.MenuPanel2_TEXTFIELD_WIDTH, GameConstants.MenuPanel2_TEXTFIELD_HEIGHT));
        nameField.setHorizontalAlignment(JTextField.CENTER);

        // Set the field to the corresponding instance variable
        switch (row) {
            case 0 -> nameField1 = nameField;
            case 1 -> nameField2 = nameField;
            case 2 -> nameField3 = nameField;
            case 3 -> nameField4 = nameField;
        }

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
            menu.AddPlayer(nameField1.getText());
            menu.AddPlayer(nameField2.getText());
            menu.AddPlayer(nameField3.getText());
            menu.AddPlayer(nameField4.getText());

            menuWF.SetMenuPanel1Visible();
            menuWF.setVisible(false);
            GameWindowFrame gameWindowFrame = new GameWindowFrame(menuWF);
            menu.StartGame(game);
        }
        if (event.getSource().equals(cancelButton)) {
            menuWF.SetMenuPanel1Visible();
        }
    }
}
