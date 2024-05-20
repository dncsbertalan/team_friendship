package Graphics.Utils;

import Constants.GameConstants;

import static Runnable.Main.gameController;
import static Runnable.Main.imageManager;

import java.awt.*;

public class GasCloud {

    private int timeLeft;
    private Vector2 position;
    private float scale;
    private int dir;
    private float opacity;
    private final int speed = 1;

    public GasCloud(int timeLeft, Vector2 position, float scale, int direction) {
        this.timeLeft = timeLeft;
        this.position = position;
        this.scale = scale * 50;
        this.dir = direction;
    }

    public void Update() {

        this.position.x += dir * speed;
        this.opacity = HelperMethods.Remap(timeLeft, 60, 0, .8f, .2f, true);

        timeLeft--;

        if (timeLeft == 0) gameController.KillGasCloud(this);
    }

    public void Draw(Graphics2D graphics2D) {
        Graphics2D g = (Graphics2D) graphics2D.create();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g.setComposite(ac);
        DrawUtils.DrawImageCentered(g, imageManager.resizeImage(GameConstants.IMAGE_GAS_CLOUD, scale), position);

        g.dispose();
    }
}
