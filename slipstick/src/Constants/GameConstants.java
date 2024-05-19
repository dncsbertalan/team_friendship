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

    public static final int STEPS_IN_ONE_ROUND = 1;

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
    public static final String RoomName_MainHall = "MainHall";
    public static final String RoomName_JanitorsRoom = "Janitor'sRoom";
    public static final String  RoomName_TeachersLounge = "Teachers'Lounge";
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
    public static final String MenuPanel1_LOGO_FILEPATH = "slipstick/rsc/logo.png";
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
    public static final int MAXIMUM_PLAYER_NAME_LENGTH = 20;

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

// region GAME =========================================================================================================
    public static final String GamePanel_ROUND_TEXT = "Round: ";
    public static final String REMAINING_ROUND_TEXT = "   -> steps left: ";
    public static final String STUDENT_STEPS_TEXT = "Steps left: ";

    public static final int GamePanel_WIDTH = 1000;
    public static final int GamePanel_HEIGHT = 800;
    public static final String GamePanel_EXIT_BUTTON = "Exit";
    public static final Color GamePanel_EXIT_BUTTON_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color GamePanel_EXIT_BUTTON_BACKGROUND_COLOR = Color.WHITE;
    public static final float GamePanel_EXIT_BUTTON_BORDER_THICKNESS = 5.0f;

    // Item information panel
    public static final Color GamePanel_ITEM_INFORMATION_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color GamePanel_ITEM_INFORMATION_FILL_COLOR = new Color(255, 255, 255, 186);

    public static final String GamePanel_ROOM_ITEM_TEXT_1 = "Selected item";
    public static final String GamePanel_ROOM_ITEM_TEXT_2 = "from room:";
    public static final String GamePanel_INVENTORY_ITEM_TEXT_1 = "Selected item";
    public static final String GamePanel_INVENTORY_ITEM_TEXT_2 = "from inventory:";

    // Room parameters
    public static final int ROOM_SIZE = 250;
    public static final int ROOM_MIN_SIDES = 3;
    public static final float SMALL_ROOM_SIZE_RATIO = 0.5f;
    public static int CHARACTERIZE = 250;
    // Wall
    public static final int WALL_SIZE = 50;

    // Screen messages
    public static final int MAX_SCREEN_MESSAGES = 8;
    public static final int SCREEN_MESSAGE_DISTANCE = 7;
    public static final Font SCREEN_MESSAGE_FONT = new Font("Courier New", Font.BOLD, 20);
    public static final Color SCREEN_MESSAGE_DEFAUL_COLOR = Color.black;
    public static Vector2 GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT() { return new Vector2(20, 20); }

    public static Vector2 GamePanel_INVENTORY_POS() { return new Vector2(20, 20); }
    public static Vector2 GamePanel_ENTITY_INFO_POS() { return new Vector2(20, 100); }

// endregion

// region IMAGES =======================================================================================================

    public static final String IMAGE_LOGO_FILEPATH = "slipstick/rsc/logo.png";
    public static final String IMAGE_BG_FILEPATH = "slipstick/rsc/bg2.png";
    public static final String IMAGE_WALL_FILEPATH = "slipstick/rsc/wall3.png";

    public static final String IMAGE_LOGO = "logo";
    public static final String IMAGE_BG = "room_bg";
    public static final String IMAGE_WALL = "room_wall";

    // Item keys
    public static final String IMAGE_AIR_FRESHENER = "air_freshener";
    public static final String IMAGE_BEER = "beer";
    public static final String IMAGE_CHEESE = "cheese";
    public static final String IMAGE_WET_CLOTH = "cloth";
    public static final String IMAGE_FFP2_MASK = "mask";
    public static final String IMAGE_SLIPSTICK = "slipstick";
    public static final String IMAGE_TRANSISTOR = "transistor";
    public static final String IMAGE_TVSZ = "tvsz";

    // Item paths
    public static final String IMAGE_AIR_FRESHENER_FILEPATH = "slipstick/rsc/items/AirFreshener.png";
    public static final String IMAGE_BEER_FILEPATH = "slipstick/rsc/items/Beer.png";
    public static final String IMAGE_CHEESE_FILEPATH = "slipstick/rsc/items/Cheese.png";
    public static final String IMAGE_WET_CLOTH_FILEPATH = "slipstick/rsc/items/Cloth.png";
    public static final String IMAGE_FFP2_MASK_FILEPATH = "slipstick/rsc/items/Mask.png";
    public static final String IMAGE_SLIPSTICK_FILEPATH = "slipstick/rsc/items/Slipstick.png";
    public static final String IMAGE_TRANSISTOR_FILEPATH = "slipstick/rsc/items/Transistor.png";
    public static final String IMAGE_TVSZ_FILEPATH = "slipstick/rsc/items/TVSZ.png";

    // Entity keys
    public static final String IMAGE_STUDENT1 = "Player1";
    public static final String IMAGE_STUDENT2 = "Player2";
    public static final String IMAGE_STUDENT3 = "Player3";
    public static final String IMAGE_STUDENT4 = "Player4";
    public static final String IMAGE_PROFESSOR1 = "professor1";
    public static final String IMAGE_PROFESSOR2 = "professor2";
    public static final String IMAGE_PROFESSOR3 = "professor3";
    public static final String IMAGE_PROFESSOR4 = "professor4";
    public static final String IMAGE_JANITOR1 = "janitor1";
    public static final String IMAGE_JANITOR2 = "janitor2";
    public static final String IMAGE_JANITOR3 = "janitor3";
    public static final String IMAGE_JANITOR4 = "janitor4";

    // Entity paths
    public static final String IMAGE_STUDENT1_FILEPATH = "slipstick/rsc/entities/Player1.png";
    public static final String IMAGE_STUDENT2_FILEPATH = "slipstick/rsc/entities/Player2.png";
    public static final String IMAGE_STUDENT3_FILEPATH = "slipstick/rsc/entities/Player3.png";
    public static final String IMAGE_STUDENT4_FILEPATH = "slipstick/rsc/entities/Player4.png";
    public static final String IMAGE_PROFESSOR1_FILEPATH = "slipstick/rsc/entities/professor1.png";
    public static final String IMAGE_PROFESSOR2_FILEPATH = "slipstick/rsc/entities/professor2.png";
    public static final String IMAGE_PROFESSOR3_FILEPATH = "slipstick/rsc/entities/professor3.png";
    public static final String IMAGE_PROFESSOR4_FILEPATH = "slipstick/rsc/entities/professor4.png";
    public static final String IMAGE_JANITOR1_FILEPATH = "slipstick/rsc/entities/janitor1.png";
    public static final String IMAGE_JANITOR2_FILEPATH = "slipstick/rsc/entities/janitor2.png";
    public static final String IMAGE_JANITOR3_FILEPATH = "slipstick/rsc/entities/janitor3.png";
    public static final String IMAGE_JANITOR4_FILEPATH = "slipstick/rsc/entities/janitor4.png";

// endregion ===========================================================================================================

// region SOUNDS =======================================================================================================

    public static final String MENU_MUSIC_FILEPATH = "slipstick/rsc/sounds/menu.wav";
    public static final String GAME_MUSIC_FILEPATH = "slipstick/rsc/sounds/game.wav";
    public static final String ENDGAME_MUSIC_FILEPATH = "slipstick/rsc/sounds/endgame.wav";

    public static final String SOUND_DOOR1_FILEPATH = "slipstick/rsc/sounds/door1.wav";
    public static final String SOUND_DOOR2_FILEPATH = "slipstick/rsc/sounds/door2.wav";
    public static final String SOUND_DOOR1 = "door1";
    public static final String SOUND_DOOR2 = "door2";

// endregion
}
