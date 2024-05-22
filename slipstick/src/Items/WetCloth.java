package Items;

import Constants.Enums;
import Constants.GameConstants;
import Entities.Professor;
import Entities.Student;

public class WetCloth extends Item {
    private static int ID = 0;
    private int roundsLeft = GameConstants.WetCloth_RoundsUsable;

    @Override
    public void UseItem(Student student) {

    }

    @Override
    public void AutoName() {
        this.name = GameConstants.WetCloth + ++ID;
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

    public void DeacreaseRounds(Student student) {
        roundsLeft = Math.max(0, --roundsLeft);
        if (roundsLeft == 0) student.DeleteItemFromInventory(this);
    }

    public int GetRoundsLeft() {
        return roundsLeft;
    }
}
