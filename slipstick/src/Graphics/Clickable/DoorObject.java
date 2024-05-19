package Graphics.Clickable;

import Entities.Student;
import Graphics.Utils.Vector2;
import Labyrinth.Room;

import static Runnable.Main.*;

public class DoorObject extends ClickableObject {

    private final Room roomToGo;

    public DoorObject(Vector2 centerPosition, Room roomToGo, boolean canBeClicked) {
        super(centerPosition, canBeClicked);
        this.roomToGo = roomToGo;
    }

    @Override
    protected void click() {
        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        gameController.StepStudent(active, roomToGo);

        // TODO: temporary
        gameController.NewScreenMessage(240, "Clicked door in " + active.GetCurrentRoom().GetName());
    }
}
