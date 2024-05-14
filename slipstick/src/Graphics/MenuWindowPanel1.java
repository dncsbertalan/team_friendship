package Graphics;

import Constants.GameConstants;
import Graphics.Utils.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Runnable.Main.*;

public class MenuWindowPanel1 extends JPanel implements ActionListener {
    private final JButton exitButton;
    private final JButton playButton;

    private final MenuWindowFrame menuWF;

    public MenuWindowPanel1(MenuWindowFrame frame) {

        menuWF = frame;

        // ez kurvára ideiglenes // TODO change this bitches
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(GameConstants.MenuPanel1_WIDTH, GameConstants.MenuPanel1_HEIGHT));

        // Panel init
        exitButton = new MenuButton(GameConstants.MenuPanel1_EXIT_BUTTON);
        playButton = new MenuButton(GameConstants.MenuPanel1_PLAY_BUTTON);

        exitButton.setFont(GameConstants.Menu_BUTTONFONT);
        playButton.setFont(GameConstants.Menu_BUTTONFONT);

        exitButton.setPreferredSize(GameConstants.Menu_BUTTONSIZE);
        playButton.setPreferredSize(GameConstants.Menu_BUTTONSIZE);

        playButton.addActionListener(this);
        exitButton.addActionListener(this);

        this.add(playButton);
        this.add(exitButton);
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
