package Items;

import Constants.Enums;
import Entities.Student;

public class WetCloth extends Item {

    public WetCloth() {
        this.protectionType = Enums.ProtectionType.wetCloth;
    }

    @Override
    public void UseItem(Student student) {

    }
}
