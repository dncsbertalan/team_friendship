package Constants;

import Graphics.Utils.Vector2;

import java.awt.*;

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

// region MENU 1 =======================================================================================================
    public static final int MenuPanel1_WIDTH = 1000;
    public static final int MenuPanel1_HEIGHT = 800;
    public static final Color MenuPanel1_BACKGROUND_COLOR = Color.BLACK;
    public static final String MenuPanel1_EXIT_BUTTON = "Exit";
    public static final String MenuPanel1_PLAY_BUTTON = "Play";
    // LOGO
    //public static final String MenuPanel1_LOGO_FILEPATH = "slipstick/rsc/logo.png";
    public static final String MenuPanel1_LOGO_FILEPATH = "rsc/logo.png"; // bercinek ez jó
    public static final int MenuPanel1_LOGO_RESIZE_WIDTH = 550;
    public static final int MenuPanel1_LOGO_RESIZE_HEIGHT = 250;
    // BUTTONS FOR MENU 1
    public static final int MenuPanel1_BUTTON_WIDTH = 300;
    public static final int MenuPanel1_BUTTON_HEIGHT = 80;
    public static final Font MenuPanel1_BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 28);
    public static final Color MenuPanel1_BUTTON_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color MenuPanel1_BUTTON_BACKGROUND_COLOR = Color.WHITE;
    public static final float MenuPanel1_BUTTON_BORDER_THICKNESS = 5.0f;

// endregion

// region MENU 2 =======================================================================================================
    public static final int MenuPanel2_WIDTH = 1000;
    public static final int MenuPanel2_HEIGHT = 800;
    public static final Color MenuPanel2_BACKGROUND_COLOR = Color.BLACK;
    public static final int MenuPanel2_NAME_FIELD_WIDTH = 50;
    public static final String MenuPanel2_DONE_BUTTON = "Done";
    public static final String MenuPanel2_CANCEL_BUTTON = "Cancel";
    public static final String MenuPanel2_NAMES_LABEL = "Names";
    public static final Font MenuPanel2_NAMES_LABEL_FONT = new Font("Times New Roman", Font.PLAIN, 28);
    public static final Color MenuPanel2_NAMES_LABEL_COLOR = new Color(115,85,90,255);
    // BUTTONS FOR MENU 2
    public static final int MenuPanel2_BUTTON_WIDTH = 150;
    public static final int MenuPanel2_BUTTON_HEIGHT = 60;
    public static final Font MenuPanel2_BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 28);
    public static final Color MenuPanel2_BUTTON_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color MenuPanel2_BUTTON_BACKGROUND_COLOR = Color.WHITE;
    public static final float MenuPanel2_BUTTON_BORDER_THICKNESS = 5.0f;
    // TEXT FIELDS FOR PLAYER NAMES IN MENU 2
    public static final int MenuPanel2_TEXTFIELD_WIDTH = 400;
    public static final int MenuPanel2_TEXTFIELD_HEIGHT = 60;
    public static final Color MenuPanel2_TEXTFIELD_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color MenuPanel2_TEXTFIELD_BACKGROUND_COLOR = Color.WHITE;
    public static final Font MenuPanel2_TEXTFIELD_FONT = new Font("Times New Roman", Font.BOLD, 28);
    public static final float MenuPanel2_TEXTFIELD_BORDER_THICKNESS = 5.0f;
    // LABELS FOR TEXT FIELDS IN MENU 2
    public static final int MenuPanel2_TEXTFIELD_LABEL_WIDTH = 200;
    public static final int MenuPanel2_TEXTFIELD_LABEL_HEIGHT = 60;
    public static final Color MenuPanel2_TEXTFIELD_LABEL_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color MenuPanel2_TEXTFIELD_LABEL_BACKGROUND_COLOR = Color.WHITE;
    public static final Font MenuPanel2_TEXTFIELD_LABEL_FONT = new Font("Times New Roman", Font.BOLD, 28);
    public static final float MenuPanel2_TEXTFIELD_LABEL_BORDER_THICKNESS = 5.0f;
    // ERROR MESSAGE WHEN NO NAMES WERE GIVEN
    public static final String MenuPanel2_ERROR_MESSAGE = "At least one name must be given.";
    public static final int MenuPanel2_ERROR_MESSAGE_WIDTH = 320;
    public static final int MenuPanel2_ERROR_MESSAGE_HEIGHT = 140;
    public static final Color MenuPanel2_ERROR_MESSAGE_BORDER_COLOR = Color.RED;
    public static final Color MenuPanel2_ERROR_MESSAGE_BACKGROUND_COLOR = Color.WHITE;
    public static final float MenuPanel2_ERROR_MESSAGE_BORDER_THICKNESS = 5.0f;
    public static final Font MenuPanel2_ERROR_MESSAGE_FONT = new Font("Times New Roman", Font.BOLD, 20);
    public static final String MenuPanel2_ERROR_MESSAGE_BUTTON = "OK";
    public static final Color MenuPanel2_ERROR_MESSAGE_BUTTON_BORDER_COLOR = Color.RED;
    public static final Color MenuPanel2_ERROR_MESSAGE_BUTTON_BACKGROUND_COLOR = Color.WHITE;
    public static final float MenuPanel2_ERROR_MESSAGE_BUTTON_BORDER_THICKNESS = 2.0f;
    public static final Font MenuPanel2_ERROR_MESSAGE_BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 28);

// endregion

    // GAME
    public static final String GamePanel_ROUND_TEXT = "Round: ";

    public static final int GamePanel_WIDTH = 1000;
    public static final int GamePanel_HEIGHT = 800;
    public static final String GamePanel_EXIT_BUTTON = "Exit";
    public static final Color GamePanel_EXIT_BUTTON_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color GamePanel_EXIT_BUTTON_BACKGROUND_COLOR = Color.WHITE;
    public static final float GamePanel_EXIT_BUTTON_BORDER_THICKNESS = 5.0f;

    // Room parameters
    public static final int GamePanel_ROOM_SIZE = 250;
    public static final int GamePanel_ROOM_MIN_SIDES = 3;

    // Screen messages
    public static final int GamePanel_MAX_SCREEN_MESSAGES = 5;
    public static final int GamePanel_SCREEN_MESSAGE_DISTANCE = 7;
    public static final Font GamePanel_SCREEN_MESSAGE_FONT = new Font("Courier New", Font.BOLD, 20);
    public static Vector2 GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT() { return new Vector2(20, 20); }


    public static Vector2 GamePanel_INVENTORY_POS() { return new Vector2(20, 20); }
    public static Vector2 GamePanel_ROUND_POS() { return new Vector2(900, 20); }
    public static Vector2 GamePanel_ENTITY_INFO_POS() { return new Vector2(20, 100); }

// endregion
}
