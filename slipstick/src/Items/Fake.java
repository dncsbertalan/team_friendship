package Items;

import Constants.GameConstants;
import Entities.Student;

import java.util.Random;

public class Fake extends Item {
    private static int ID = 0;
    private final Item fakedItem;

    public Fake() {
        Random random = new Random();
        int choice = random.nextInt(7);
        switch (choice) {
            case 0 -> fakedItem = new AirFreshener();
            case 1 -> fakedItem = new Beer();
            case 2 -> fakedItem = new Cheese();
            case 3 -> fakedItem = new FFP2Mask();
            case 4 -> fakedItem = new Transistor();
            case 5 -> fakedItem = new TVSZ();
            default -> fakedItem = new WetCloth();
        }
    }

    public Item GetFakedItem() {
        return fakedItem;
    }

    @Override
    public void AutoName() {
        this.name = GameConstants.FakeItem + ++ID;
    }

    /**
     * Called when an item is used by the student.
     *
     * @param student the item user
     */
    @Override
    public void UseItem(Student student) {
        System.out.println("Nothing happened, it was a fake item! ^^");
        student.GetInventory().remove(this);
    }
}
