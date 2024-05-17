package Graphics.Utils.Clickable;

import Entities.Student;
import Graphics.Utils.Vector2;
import Labyrinth.Room;
import static Runnable.Main.*;

public class DoorObject extends ClickableObject {

    private Room roomToGo;

    public DoorObject(Vector2 centerPosition, Room roomToGo) {
        super(centerPosition);
        this.roomToGo = roomToGo;
    }



    @Override
    public void Click() {
        Student active = game.GetRoundManager().GetActiveStudent();
        if (active == null) return;

        gameController.StepStudent(active, roomToGo);

        // TODO: temporary
        gameController.NewScreenMessage(240, "Clicked");
    }
}
