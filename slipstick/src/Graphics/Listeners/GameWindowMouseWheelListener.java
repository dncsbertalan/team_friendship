package Graphics.Listeners;

import Entities.Student;

import static Runnable.Main.*;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class GameWindowMouseWheelListener implements MouseWheelListener {
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getWheelRotation() < 0)
        {
            Student active = game.GetRoundManager().GetActiveStudent();
            if (active == null) return;

            int curr = active.GetSelectedInventorySlot();
            curr--;
            if (curr < 0) {
                active.SelectInventorySlot(5);
                return;
            }
            active.SelectInventorySlot(curr + 1);
        }
        else
        {
            Student active = game.GetRoundManager().GetActiveStudent();
            if (active == null) return;

            int curr = active.GetSelectedInventorySlot();
            curr++;
            if (curr > 4) {
                active.SelectInventorySlot(1);
                return;
            }
            active.SelectInventorySlot(curr + 1);
        }
    }
}
