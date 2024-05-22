package Graphics.Listeners;

import Graphics.GameWindowFrame;
import Graphics.GameWindowPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Runnable.Main.soundManager;

public class GamePanelExitButtonListener implements MouseListener {

    private final GameWindowFrame frame;

    public GamePanelExitButtonListener(GameWindowFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        frame.dispose();
        soundManager.playSoundLooped("menu");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
