package Graphics.Clickable;

import Constants.GameConstants;
import Entities.Student;
import Graphics.Utils.DrawUtils;
import Graphics.Utils.Vector2;
import Labyrinth.Room;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.file.attribute.PosixFileAttributes;

import static Runnable.Main.*;

public class DoorObject extends ClickableObject {

    private final Room roomToGo;
    private final float rotation;

    public DoorObject(Vector2 centerPosition, Room roomToGo, float scale, float rotation, boolean canBeClicked) {
        super(centerPosition, canBeClicked, scale);
        this.roomToGo = roomToGo;
        this.rotation = rotation;
        this.size = imageManager.GetImage(GameConstants.IMAGE_DOOR).getWidth();
        this.position = new Vector2(centerPosition.x - size / 2, centerPosition.y - size / 2);
    }

    @Override
    public void Draw(Graphics2D graphics2D, Vector2 mousePos) {
        boolean inside = IsInside(mousePos);

        if (canBeClicked && inside) {
            BufferedImage image = DrawUtils.RotateImage(imageManager.resizeImage(GameConstants.IMAGE_DOOR_OUTLINE, scale), (double) rotation);
            DrawUtils.DrawImageCentered(graphics2D, image, centerPosition);
        }

        BufferedImage image = DrawUtils.RotateImage(imageManager.resizeImage(GameConstants.IMAGE_DOOR, scale), (double) rotation);
        DrawUtils.DrawImageCentered(graphics2D, image, centerPosition);
    }

    @Override
    protected void click() {
        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        gameController.StepStudent(active, roomToGo);
    }
}
