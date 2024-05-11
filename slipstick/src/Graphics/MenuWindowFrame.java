package Graphics;

import Constants.GameConstants;

import javax.swing.*;

import static Runnable.Main.gameController;

public class MenuWindowFrame extends JFrame {
    private final MenuWindowPanel1 menuWindowPanel1;
    private final MenuWindowPanel2 menuWindowPanel2;

    public MenuWindowFrame() {

        // Window init
        this.setTitle(GameConstants.WindowTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Menu panels init
        menuWindowPanel1 = new MenuWindowPanel1(this);
        menuWindowPanel2 = new MenuWindowPanel2(this);
        this.add(menuWindowPanel1);
        this.pack();
        this.setLocationRelativeTo(null);

        // Window icon
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pacman/assets/icon.png")));
    }

    /**
     * Sets the first menu panel visible and the second invisible.
     */
    public void SetMenuPanel1Visible() {
        this.remove(menuWindowPanel2);
        this.add(menuWindowPanel1);
        this.pack();
    }

    /**
     * Sets the second menu panel visible and the fisrt invisible.
     */
    public void SetMenuPanel2Visible() {
        this.remove(menuWindowPanel1);
        this.add(menuWindowPanel2);
        this.pack();
    }
}
