package Graphics;

import Constants.GameConstants;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Runnable.Main.os;

public class MenuWindowFrame extends JFrame {
    private final MenuWindowPanel1 menuWindowPanel1;
    private final MenuWindowPanel2 menuWindowPanel2;

    public MenuWindowFrame() {

        // Window init
        this.setTitle(GameConstants.WindowTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Menu panels init
        menuWindowPanel1 = new MenuWindowPanel1(this);
        menuWindowPanel2 = new MenuWindowPanel2(this);
        this.add(menuWindowPanel1);
        this.pack();
        this.setLocationRelativeTo(null);

        // Window icon
        ImageIcon img = new ImageIcon(GameConstants.MenuPanel1_LOGO_FILEPATH);
        this.setIconImage(img.getImage());

        // temp
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                os.println("Probably temporary window closing because of the GetInput() infinite loop for the input");
                System.exit(0);
            }
        });
    }

    /**
     * Sets the first menu panel visible and the second invisible.
     */
    public void SetMenuPanel1Visible() {
        this.setMinimumSize(new Dimension(1000, 700));
        this.remove(menuWindowPanel2);
        this.add(menuWindowPanel1);
        this.revalidate();
        this.repaint();
        this.pack();
    }

    /**
     * Sets the second menu panel visible and the fisrt invisible.
     */
    public void SetMenuPanel2Visible() {
        this.remove(menuWindowPanel1);
        this.add(menuWindowPanel2);
        this.revalidate();
        this.repaint();
        this.pack();
    }
}
