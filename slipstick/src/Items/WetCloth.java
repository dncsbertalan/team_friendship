package Items;

import Constants.Enums;
import Constants.GameConstants;
import Entities.Professor;
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

    public void ProtectStudentFromProfessor(Professor p){
        p.MissRounds(GameConstants.WetCloth_MissRoundCount);
        p.DropAllItems();
    }
}
