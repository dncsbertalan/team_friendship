package Items;

import Constants.Enums;
import Entities.Student;

public class WetCloth extends Item {

    @Override
    public void UseItem(Student student) {

    }

    @Override
    public void ActivateItem() {
        super.ActivateItem();
        this.protectionType = Enums.ProtectionType.wetCloth;
    }
}
