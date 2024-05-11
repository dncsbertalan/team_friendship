package Graphics;

import Constants.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Runnable.Main.*;

public class MenuWindowPanel2 extends JPanel implements ActionListener {
    private final JButton doneButton;
    private final JTextField nameField1;
    private final JTextField nameField2;
    private final JTextField nameField3;
    private final JTextField nameField4;

    private final MenuWindowFrame menuWF;

    public MenuWindowPanel2(MenuWindowFrame frame) {

        menuWF = frame;

        // ez kurvára ideiglenes // TODO change this bitches
        this.setBackground(Color.cyan);
        this.setPreferredSize(new Dimension(GameConstants.MenuPanel2_WIDTH, GameConstants.MenuPanel2_HEIGHT));

        // Panel init
        doneButton = new JButton(GameConstants.MenuPanel2_DONE_BUTTON);
        nameField1 = new JTextField(GameConstants.MenuPanel2_NAME_FIELD_WIDTH);
        nameField2 = new JTextField(GameConstants.MenuPanel2_NAME_FIELD_WIDTH);
        nameField3 = new JTextField(GameConstants.MenuPanel2_NAME_FIELD_WIDTH);
        nameField4 = new JTextField(GameConstants.MenuPanel2_NAME_FIELD_WIDTH);

        doneButton.addActionListener(this);

        this.add(nameField1);
        this.add(nameField2);
        this.add(nameField3);
        this.add(nameField4);
        this.add(doneButton);

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

            menuWF.SetMenuPanel1Visible();
            menuWF.setVisible(false);
            GameWindowFrame gameWindowFrame = new GameWindowFrame(menuWF);
            menu.StartGame(game);
        }
    }

}
