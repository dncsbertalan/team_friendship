package Graphics;

import Constants.GameConstants;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Runnable.Main.gameController;

public class GameWindowFrame extends JFrame {
    private final GameWindowPanel gameWindowPanel;
    private final MenuWindowFrame menuWF;

    GameWindowFrame(MenuWindowFrame menuFrame) {

        menuWF = menuFrame;

        // Window init
        this.setTitle(GameConstants.WindowTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Game panel init
        gameWindowPanel = new GameWindowPanel(this);
        this.add(gameWindowPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        gameController.SetGamePanel(gameWindowPanel);

        // temp
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                gameController.StopGame();
                menuWF.setVisible(true);
                menuWF.SetMenuPanel1Visible();
            }
        });
    }
}
