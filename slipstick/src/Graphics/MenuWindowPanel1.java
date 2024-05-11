package Graphics;

import Constants.GameConstants;

import static Runnable.Main.game;
import static Runnable.Main.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        exitButton = new JButton(GameConstants.MenuPanel1_EXIT_BUTTON);
        playButton = new JButton(GameConstants.MenuPanel1_PLAY_BUTTON);
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
    }

}
