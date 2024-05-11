package Constants;

import Utils.Vector2;

public class GameConstants {
    public static int MaxRounds = 20;
    public static final int RoundsMissed_GasRoom = 1;
    public static final int TVSZ_MaxUses = 3;
    public static final int FFP2Mask_MoveCountIncrease = 1;
    public static final int FFP2Mask_MaxUses = 3;
    public static final int WetCloth_MissRoundCount = 1;
    public static final int EntitiesToBecomeSticky = 5; // idk

    public static final int InventoryMaxSize = 5;
    public static final int DesiredFPS = 60;

    public static final String ProfName = "Professor_";
    public static final String JanitorName = "Janitor_";
    public static final String AirFreshener = "AirFreshener_";
    public static final String Beer = "Beer_";
    public static final String Cheese = "Cheese_";
    public static final String FakeItem = "FakeItem_";
    public static final String FFP2Mask = "FFP2_"; // "FFP2Mask_";
    public static final String SlipSlick = "Slipstick";
    public static final String Transistor = "Transistor_";
    public static final String TVSZ = "TVSZ_";
    public static final String WetCloth = "WetCloth_";
    public static final String RoomName = "Room_";
    public static final String RoomName_MainHall = "MainHall_";
    public static final String RoomName_JanitorsRoom = "Janitor'sRoom_";
    public static final String  RoomName_TeachersLounge = "Teachers'Lounge_";
    public static final String  RoomName_WinningRoom = "WinningRoom";
    public static final long randomSeed = 69;

// region GRAPHICS CONSTANTS ===========================================================================================

    public static final String WindowTitle = "Slipstick";
    // MENU 1
    public static final int MenuPanel1_WIDTH = 200;
    public static final int MenuPanel1_HEIGHT = 300;
    public static final String MenuPanel1_EXIT_BUTTON = "Exit";
    public static final String MenuPanel1_PLAY_BUTTON = "Play";

    // MENU 2
    public static final int MenuPanel2_WIDTH = 200;
    public static final int MenuPanel2_HEIGHT = 300;
    public static final int MenuPanel2_NAME_FIELD_WIDTH = 10;
    public static final String MenuPanel2_DONE_BUTTON = "Done";

    // GAME
    public static final String GamePanel_ROUND_TEXT = "Round: ";

    public static final int GamePanel_WIDTH = 1000;
    public static final int GamePanel_HEIGHT = 800;

    public static Vector2 GamePanel_INVENTORY_POS() { return new Vector2(20, 20); }
    public static Vector2 GamePanel_ROUND_POS() { return new Vector2(900, 20); }
    public static Vector2 GamePanel_ENTITY_INFO_POS() { return new Vector2(20, 100); }

// endregion
}
