package Constants;

import Graphics.Utils.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class GameConstants {
    public static int MaxRounds = 20;
    public static final int RoundsMissed_GasRoom = 1;
    public static final int TVSZ_MaxUses = 3;
    public static final int FFP2Mask_MoveCountIncrease = 1;
    public static final int FFP2Mask_MaxUses = 3;
    public static final int WetCloth_MissRoundCount = 1;
    public static final int WetCloth_RoundsUsable = 5;
    public static final int EntitiesToBecomeSticky = 5; // idk

    public static final int STEPS_IN_ONE_ROUND = 2;
    public static final int JANITOR_NUMBER = 2;
    public static final int PROFESSOR_NUMBER = 2;

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
    public static final String RoomName_TeachersLounge = "Teachers'Lounge";
    public static final String RoomName_WinningRoom = "WinningRoom";
    public static final long randomSeed = 69;

    public static final ArrayList<String> JANITOR_NAMES = new ArrayList<>();
    public static final String JANITOR_NAME_END = " the janitor";

    public static final ArrayList<String> PROFESSOR_NAMES = new ArrayList<>();
    public static final String PROFESSOR_NAME_END = " the professor";

    static {
        JANITOR_NAMES.add("Consuela");
        JANITOR_NAMES.add("Janice");
        JANITOR_NAMES.add("Linda");
        JANITOR_NAMES.add("Helga");
        JANITOR_NAMES.add("oh- that's not...");
        JANITOR_NAMES.add("Rosie");
        JANITOR_NAMES.add("Berta");
        JANITOR_NAMES.add("God");
        JANITOR_NAMES.add("Elisa Esposito");
        JANITOR_NAMES.add("Carl Reed");
        JANITOR_NAMES.add("Mrs Doubtfire");
        JANITOR_NAMES.add("Mr Svenson");
        JANITOR_NAMES.add("WALL-E");
        PROFESSOR_NAMES.add("Dr. Cassandra Blake");
        PROFESSOR_NAMES.add("give me a fucking name");
        PROFESSOR_NAMES.add("I know where you live");
        PROFESSOR_NAMES.add("AHHHHHHHHH");
        PROFESSOR_NAMES.add("Papoka");
        PROFESSOR_NAMES.add("Csengoce");
    }

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

    // Student info panel
    public static final String REMAINING_ROUND_TEXT = " ¤ steps left: ";
    public static final String INFO_ALIVE = "";
    public static final String INFO_PARALYZED = " ¤ paralyzed";
    public static final String INFO_DEAD = " is dead";
    public static final String INFO_MISSED_ROUND1 = " ¤ remains out of ";
    public static final String INFO_MISSED_ROUND2 = " rounds";
    public static final String INFO_MISSED_ROUND3 = " ¤ remains out of this round";

    public static final int GamePanel_WIDTH = 1000;
    public static final int GamePanel_HEIGHT = 800;
    public static final String GamePanel_EXIT_BUTTON = "Exit";
    public static final Color GamePanel_EXIT_BUTTON_BORDER_COLOR = new Color(115,85,90,255);
    public static final Color GamePanel_EXIT_BUTTON_BACKGROUND_COLOR = Color.WHITE;
    public static final float GamePanel_EXIT_BUTTON_BORDER_THICKNESS = 5.0f;

    // Item information panel
    public static final Color GamePanel_ITEM_INFORMATION_BORDER_COLOR = new Color(115,85,90,150);
    public static final Color GamePanel_ITEM_INFORMATION_FILL_COLOR = new Color(255, 255, 255, 186);

    public static final String GamePanel_ROOM_ITEM_TEXT_1 = "Selected item";
    public static final String GamePanel_ROOM_ITEM_TEXT_2 = "from room:";
    public static final String GamePanel_INVENTORY_ITEM_TEXT_1 = "Selected item";
    public static final String GamePanel_INVENTORY_ITEM_TEXT_2 = "from inventory:";

    // Room parameters
    public static final int ROOM_SIZE = 250;
    public static final int ROOM_MIN_SIDES = 3;
    public static final float SMALL_ROOM_SIZE_RATIO = 0.5f;
    public static final int CHARACTERIZE = 250;
    public static final int SMALL_ROOM_DISTANCE = 190;

    // Wall
    public static final int WALL_SIZE = 50;

    // Screen messages
    public static final int MAX_SCREEN_MESSAGES = 10;
    public static final Font SCREEN_MESSAGE_FONT = new Font("Courier New", Font.BOLD, 20);
    public static final Color SCREEN_MESSAGE_DEFAUL_COLOR = Color.black;
    public static final int GamePanel_SCREEN_MESSAGE_BOTTOM_LEFT = 20;

    public static Vector2 GamePanel_INVENTORY_POS() { return new Vector2(20, 20); }
    public static final int GamePanel_ENTITY_INFO_POS_X = 20;

    // End screen
    public static final String WIN_MESSAGE = "CONGRATULATIONS! YOU WON!";
    public static final String LOST_MESSAGE = "LOSERS...";

    public static final String FAKE_ITEM_USE_MESSAGE = "Looks like it was a fake item";
    public static final int FAKE_ITEM_USE_MESSAGE_TIMELEFT = DesiredFPS * 3;
    public static final String TRANSISTOR_PAIR_MESSAGE = "Successfully paired transistors";
    public static final int TRANSISTOR_PAIR_MESSAGE_TIMELEFT = DesiredFPS * 3;

    public static final String MESSAGE_PARALYZED_SKIP = " misses this round because they are paralyzed";
    public static final String MESSAGE_GAS_ROOM_STEP = " stepped into a gassed room";

    public static final int MAX_VISUAL_TIME_HOUR = DesiredFPS * 60 * 60;
    public static final float GAS_PROBABILITY = 0.2f;
    public static final int GAS_RANGE = 300;

// endregion

// region IMAGES =======================================================================================================

    // Logo
    public static final String IMAGE_LOGO_FILEPATH = "slipstick/rsc/logo.png";
    public static final String IMAGE_LOGO = "logo";

    // Gas cloud
    public static final String IMAGE_GAS_CLOUD = "gas";
    public static final String IMAGE_GAS_CLOUD_FILEPATH = "slipstick/rsc/gas.png";

    // Room paths
    public static final String IMAGE_BG_FILEPATH = "slipstick/rsc/bg2.png";
    public static final String IMAGE_WALL_FILEPATH = "slipstick/rsc/wall3.png";
    public static final String IMAGE_DOOR_FILEPATH = "slipstick/rsc/door.png";
    public static final String IMAGE_DOOR_OUTLINE_FILEPATH = "slipstick/rsc/door_outline.png";

    // Room keys
    public static final String IMAGE_BG = "room_bg";
    public static final String IMAGE_WALL = "room_wall";
    public static final String IMAGE_DOOR = "door";
    public static final String IMAGE_DOOR_OUTLINE = "door_outline";

    // Item keys
    public static final String IMAGE_AIR_FRESHENER = "air_freshener";
    public static final String IMAGE_BEER = "beer";
    public static final String IMAGE_CHEESE = "cheese";
    public static final String IMAGE_WET_CLOTH = "cloth";
    public static final String IMAGE_FFP2_MASK = "mask";
    public static final String IMAGE_SLIPSTICK = "slipstick";
    public static final String IMAGE_TRANSISTOR = "transistor";
    public static final String IMAGE_TVSZ = "tvsz";

    public static final String IMAGE_AIR_FRESHENER_OUTLINE = "air_freshener_outline";
    public static final String IMAGE_BEER_OUTLINE = "beer_outline";
    public static final String IMAGE_CHEESE_OUTLINE = "cheese_outline";
    public static final String IMAGE_WET_CLOTH_OUTLINE = "cloth_outline";
    public static final String IMAGE_FFP2_MASK_OUTLINE = "mask_outline";
    public static final String IMAGE_SLIPSTICK_OUTLINE = "slipstick_outline";
    public static final String IMAGE_TRANSISTOR_OUTLINE = "transistor_outline";
    public static final String IMAGE_TVSZ_OUTLINE = "tvsz_outline";

    public static final String IMAGE_AIR_FRESHENER_OUTLINE_UNPICKABLE = "air_freshener_outline_unpickable";
    public static final String IMAGE_BEER_OUTLINE_UNPICKABLE = "beer_outline_unpickable";
    public static final String IMAGE_CHEESE_OUTLINE_UNPICKABLE = "cheese_outline_unpickable";
    public static final String IMAGE_WET_CLOTH_OUTLINE_UNPICKABLE = "cloth_outline_unpickable";
    public static final String IMAGE_FFP2_MASK_OUTLINE_UNPICKABLE = "mask_outline_unpickable";
    public static final String IMAGE_SLIPSTICK_OUTLINE_UNPICKABLE = "slipstick_outline_unpickable";
    public static final String IMAGE_TRANSISTOR_OUTLINE_UNPICKABLE = "transistor_outline_unpickable";
    public static final String IMAGE_TVSZ_OUTLINE_UNPICKABLE = "tvsz_outline_unpickable";

    // Item paths
    public static final String IMAGE_AIR_FRESHENER_FILEPATH = "slipstick/rsc/items/AirFreshener.png";
    public static final String IMAGE_BEER_FILEPATH = "slipstick/rsc/items/Beer.png";
    public static final String IMAGE_CHEESE_FILEPATH = "slipstick/rsc/items/Cheese.png";
    public static final String IMAGE_WET_CLOTH_FILEPATH = "slipstick/rsc/items/Cloth.png";
    public static final String IMAGE_FFP2_MASK_FILEPATH = "slipstick/rsc/items/Mask.png";
    public static final String IMAGE_SLIPSTICK_FILEPATH = "slipstick/rsc/items/Slipstick.png";
    public static final String IMAGE_TRANSISTOR_FILEPATH = "slipstick/rsc/items/Transistor.png";
    public static final String IMAGE_TVSZ_FILEPATH = "slipstick/rsc/items/TVSZ.png";

    public static final String IMAGE_AIR_FRESHENER_OUTLINE_FILEPATH = "slipstick/rsc/items/AirFreshener_outline.png";
    public static final String IMAGE_BEER_OUTLINE_FILEPATH = "slipstick/rsc/items/Beer_outline.png";
    public static final String IMAGE_CHEESE_OUTLINE_FILEPATH = "slipstick/rsc/items/Cheese_outline.png";
    public static final String IMAGE_WET_CLOTH_OUTLINE_FILEPATH = "slipstick/rsc/items/Cloth_outline.png";
    public static final String IMAGE_FFP2_MASK_OUTLINE_FILEPATH = "slipstick/rsc/items/Mask_outline.png";
    public static final String IMAGE_SLIPSTICK_OUTLINE_FILEPATH = "slipstick/rsc/items/Slipstick_outline.png";
    public static final String IMAGE_TRANSISTOR_OUTLINE_FILEPATH = "slipstick/rsc/items/Transistor_outline.png";
    public static final String IMAGE_TVSZ_OUTLINE_FILEPATH = "slipstick/rsc/items/TVSZ_outline.png";

    public static final String IMAGE_AIR_FRESHENER_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/AirFreshener_outline_unpickable.png";
    public static final String IMAGE_BEER_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/Beer_outline_unpickable.png";
    public static final String IMAGE_CHEESE_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/Cheese_outline_unpickable.png";
    public static final String IMAGE_WET_CLOTH_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/Cloth_outline_unpickable.png";
    public static final String IMAGE_FFP2_MASK_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/Mask_outline_unpickable.png";
    public static final String IMAGE_SLIPSTICK_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/Slipstick_outline_unpickable.png";
    public static final String IMAGE_TRANSISTOR_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/Transistor_outline_unpickable.png";
    public static final String IMAGE_TVSZ_OUTLINE_UNPICKABLE_FILEPATH = "slipstick/rsc/items/TVSZ_outline_unpickable.png";

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
    public static final String MENU_MUSIC = "menu";
    public static final String GAME_MUSIC_FILEPATH = "slipstick/rsc/sounds/game.wav";
    public static final String GAME_MUSIC = "game";
    public static final String ENDGAME_MUSIC_FILEPATH = "slipstick/rsc/sounds/endgame.wav";
    public static final String ENDGAME_MUSIC = "endgame";

    public static final String SOUND_DOOR1_FILEPATH = "slipstick/rsc/sounds/door1.wav";
    public static final String SOUND_DOOR2_FILEPATH = "slipstick/rsc/sounds/door2.wav";
    public static final String SOUND_DOOR1 = "door1";
    public static final String SOUND_DOOR2 = "door2";

    public static final String INVENTORY_FILEPATH = "slipstick/rsc/sounds/inventory.wav";
    public static final String INVENTORY = "inventory";

    public static final String SOUND_FAKE_ITEM_USE_FILEPATH = "slipstick/rsc/sounds/fake_item_use.wav";
    public static final String SOUND_FAKE_ITEM_USE = "fake";

// endregion
}
