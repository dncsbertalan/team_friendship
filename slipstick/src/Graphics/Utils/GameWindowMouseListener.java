package Graphics.Utils;

import Graphics.GameWindowPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameWindowMouseListener implements MouseListener {

    private final GameWindowPanel gameWindowPanel;

    public GameWindowMouseListener(GameWindowPanel panel) {
        this.gameWindowPanel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gameWindowPanel.ClickOnScreen(gameWindowPanel.GetMousePosiotion());
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
