package helper;

public class Constants {

    //----------Enums
    public enum State {IDLELEFT, IDLERIGHT, RUNNINGLEFT, RUNNINGRIGHT, JUMPINGLEFT, JUMPINGRIGHT, ROUND1, ROUND2, ROUND3,
    ROUND4, ROUND5, ROUND6, ROUND7, ROUND8}
    public enum NhanVat {MAIN, CUCAI, BACHTUOC, CUCDA};
    public enum VatThe {DOOR, MAPBOUND, BOX, FIRE, BUTTON};
    public enum SensorDirection {TOP, LEFT, RIGHT, BOT, TOPLEFT, TOPRIGHT, BOTLEFT, BOTRIGHT};


    //----------Arithmetics
    public static final int
            APP_WIDTH = 1200, APP_HEIGHT = 800,
            TB_WIDTH = 417, TB_HEIGHT = 429,
            TB_POS_X = (APP_WIDTH - TB_WIDTH)/2,
            TB_POS_Y = (APP_HEIGHT- TB_HEIGHT)/2 + 50,
            MAX_LEVEL = 13;
    public static final float
            PPM = 18.0f, MAX_HEIGHT_JUMP = 15f,
            CUCAI_BACHTUOC_SPEED = 9f, CUCDA_SPEED = 6f,
            CUCAI_BACHTUOC_MASS = 1, CUCDA_MASS = 15f,
            CAMERA_VIEWPORT_WIDTH = 360, CAMERA_VIEWPORT_HEIGHT = 240,
            BUTTON_PADDING = 20, BUTTON_SIZE = 64, BIG_BUTTON_SIZE = 96, SMALL_BUTTON_SIZE = 54,
            BUTTON_POS_Y = 700, BUTTON_POS_Y2 = 720, BUTTON_DISTANCE = 10,
            LEVEL_BUTTON_SIZE = 100, LEVEL_BUTTON_PADDING = 30,
            TILE_SIZE = 18f, ICON_SIZE = 50f,
            MAP_WIDTH = TILE_SIZE * 60, MAP_HEIGHT = TILE_SIZE * 40,
            CORNER_SENSOR_SIZE = 0.5f, EDGE_SENSOR_SIZE = 6f; //square



    //----------Menu Screen Textures
    public static final String
        MENU_BG_IMAGE = "textures/bgfn.png",
        MENU_PLAY_BTN = "textures/buttons/Default.png",
        MENU_PLAY_BTN_HOVER = "textures/buttons/Hover.png",
        MENU_TUTORIAL_BTN = "textures/buttons/TutButton.png",
        MENU_TUTORIAL_BTN_HOVER = "textures/buttons/TutPressButton.png",
        MENU_TUTORIAL_CONTENT = "textures/ControlBoard.png",
        MENU_EXIT_BTN = "textures/buttons/Exit.png",
        MENU_EXIT_BTN_HOVER = "textures/buttons/ExitPress.png";

    //----------Level Screen Textures
    public static final String
            LEVEL_ICON_BG = "textures/buttons/Default@2x-1.png",
            LEVEL_ICON_HOVER_BG = "textures/buttons/Hover@2x.png",
            LEVEL_LOCK_ICON = "textures/buttons/lock (1).png",
            LEVEL_BG_IMAGE = "textures/bg3.png",
            LEVEL_BACK_BTN = "textures/buttons/back.png",
            LEVEL_BACK_BTN_HOVER = "textures/buttons/backClick.png";

    //----------Game Screen Textures
    public static final String
            SMOKE_ANIMATION = "textures/characters/smokeAnimation.png",
            CUCAI_IDLE_LEFT = "textures/characters/IdleLeft.png",
            CUCAI_IDLE_RIGHT = "textures/characters/IdleRight.png",
            CUCAI_RUNNING_LEFT = "textures/characters/RunningLeft.png",
            CUCAI_RUNNING_RIGHT = "textures/characters/RunningRight.png",
            CUCAI_JUMPING_LEFT = "textures/characters/JumpingLeft.png",
            CUCAI_JUMPING_RIGHT = "textures/characters/JumpingRight.png",
            BACHTUOC_TEXTURE_PATH = "textures/characters/Sprite-000",
            CUCDA_IDLE_LEFT = "textures/characters/RockIdleLeft.png",
            CUCDA_IDLE_RIGHT = "textures/characters/RockIdleRight.png",
            CUCDA_RUNNING_LEFT ="textures/characters/RockRunLeft.png",
            CUCDA_RUNNING_RIGHT = "textures/characters/RockRunRight.png",
            BACH_TUOC_BUBBLE = "textures/objects/bachtuocbb.png",
            CUC_DA_BUBBLE = "textures/objects/cucdabb.png",
            DOOR_TEXTURE_PATH = "textures/objects/door",
            FIRE_TEXTURE_PATH = "textures/objects/fire",
            GLASS_UNBROKEN = "textures/objects/glass-unbroken.png",
            GLASS_BROKEN = "textures/objects/glass-broken.png",
            GLASS_UNBROKEN_STAND = "textures/objects/glass-unbroken-stand.png",
            GLASS_BROKEN_STAND= "textures/objects/glass-broken-stand.png",
            GAME_BTN_TEXTURE = "textures/objects/button-pressed.png",
            GAME_BTN_PRESSED_TEXTURE = "textures/objects/button-unpressed.png",
            BOX_TEXTURE = "textures/objects/box.png",
            BLACK_SCENE = "textures/blackFade.png";

    //----------HUD Textures
    public static final String
            CU_CAI_BTN = "textures/objects/cucaibtn.png",
            BACH_TUOC_BTN = "textures/objects/bachtuocbtn.png",
            CUC_DA_BTN = "textures/objects/cucdabtn.png",
            RESTART_BTN = "textures/buttons/restart.png",
            RESTART_BTN_HOVER = "textures/buttons/restartpress.png",
            PAUSE_BUTTON = "textures/buttons/Pause.png",
            PAUSE_BTN_HOVER = "textures/buttons/Pausepress.png",
            PAUSE_NOTI = "textures/PAUSETB.png",
            WIN_NOTI = "textures/WINTB.png",
            CONTINUE_BTN = "textures/buttons/Start.png",
            CONTINUE_BTN_HOVER = "textures/buttons/Startpress.png",
            LEVEL_BTN_PATH = "textures/buttons/Level.png",
            LEVEL_BTN_HOVER = "textures/buttons/Levelpress.png",
            MUSIC_ON_BTN = "textures/buttons/musicOn.png",
            MUSIC_ON_BTN_HOVER = "textures/buttons/musicOnPress.png",
            MUSIC_OFF_BTN = "textures/buttons/musicOff.png",
            MUSIC_OFF_BTN_HOVER = "textures/buttons/musicOffPress.png",
            SOUND_ON_BTN = "textures/buttons/soundOn.png",
            SOUND_ON_BTN_HOVER = "textures/buttons/soundOnPress.png",
            SOUND_OFF_BTN = "textures/buttons/soundOff.png",
            SOUND_OFF_BTN_HOVER = "textures/buttons/soundOffPress.png";


    //----------Images & Textures path
    public static final String
            CURSOR_ICON = "textures/cursor.png";

    //----------Save file path
    public static final String
            SAVE_FILE_PATH = "savedata.txt";


    //----------Audios path
    public static final String
            WALKING_SOUND = "sound/footstep.ogg",
            OCTOPUS_SOUND = "sound/slime.ogg",
            CLICK_SOUND = "sound/bonussound.ogg",
            END_LEVEL_MUSIC = "sound/endlevel2.ogg",
            TRANSFORM_SOUND = "sound/transform.ogg",
            ROCK_SOUND = "sound/rockmoving.ogg",
            MENU_BG_MUSIC = "sound/menuBGM.ogg",
            INGAME_BG_MUSIC = "sound/ingameBGM.ogg",
            GLASS_SOUND = "sound/glassbreak.ogg";

    //Audios

}