package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Constants {

    //----------Enums
    public enum State {IDLELEFT, IDLERIGHT, RUNNINGLEFT, RUNNINGRIGHT, JUMPINGLEFT, JUMPINGRIGHT, ROUND1,
        ROUND2, ROUND3, ROUND4, ROUND5, ROUND6, ROUND7, ROUND8};
    public enum NhanVat {MAIN, CUCAI, BACHTUOC, CUCDA};
    public enum VatThe {DOOR, MAPBOUND, BOX, FIRE, BUTTON};
    public enum SensorDirection {TOP, LEFT, RIGHT, BOT, TOPLEFT, TOPRIGHT, BOTLEFT, BOTRIGHT};


    //----------Arithmetics

    public static final float BUTTON_PADDING = 20, BUTTON_SIZE = 64, BIG_BUTTON_SIZE = 80, SMALL_BUTTON_SIZE = 40, BUTTON_POS_Y = 560, BUTTON_POS_Y2 = 580, BUTTON_DISTANCE = 10;
    public static final int APP_WIDTH = 960, APP_HEIGHT = 640;
    public static final int TB_WIDTH = 417, TB_HEIGHT = 429;
    public static final int TB_POS_X = (APP_WIDTH - TB_WIDTH)/2, TB_POS_Y = (APP_HEIGHT-TB_HEIGHT)/2 + 50;
    public static final float PPM = 18.0f;
    public static final float TILE_SIZE = 18f, ICON_SIZE = 40f; //square
    public static final float CORNER_SENSOR_SIZE = 1f, EDGE_SENSOR_SIZE = 6f;
    public static final float CAMERA_VIEWPORT_WIDTH = 360, CAMERA_VIEWPORT_HEIGHT = 240;




    //----------Images & Textures path strings
    public static final String
            BachTuocBubblePath = "bachtuocbb.png",
            CucDaBubblePath = "cucdabb.png",
            SmokeAnimationPath = "smokeAnimation.png",
            CuCaiButtonPath = "cucaibtn.png",
            BachTuocButtonPath = "bachtuocbtn.png",
            CucDaButtonPath = "cucdabtn.png",
            RestartButtonPath = "restart.png",
            RestartClickButtonPath = "restartpress.png",
            PauseButtonPath = "Pause.png",
            PauseClickButtonPath = "Pausepress.png",
            PauseTbPath = "PAUSETB.png",
            WinTbPath = "WINTB.png",
            ContinuePath = "Start.png",
            ContinueClickPath = "Startpress.png",
            LevelPath = "Level.png",
            LevelClickPath = "Levelpress.png",
            MusicOnPath = "musicOn.png",
            MusicOnClickPath = "musicOnPress.png",
            MusicOffPath = "musicOff.png",
            MusicOffClickPath = "musicOffPress.png",
            SoundOnPath = "soundOn.png",
            SoundOnClickPath = "soundOnPress.png",
            SoundOffPath = "soundOff.png",
            SoundOffClickPath = "soundOffPress.png",
            BlackFadePath = "blackfade.png",
            BackGroundMenuPath = "bgfn.png";


    //----------Audios path strings
    public static final String
            WalkingSoundPath = "sound/footstep.ogg",
            OctopusSound = "sound/slime.ogg",
            BonusSound = "sound/bonussound.ogg",
            EndLevelMusic = "sound/endlevel2.ogg",
            TransformSound = "sound/transform.ogg",
            RockSound = "sound/rockmoving.ogg",
            MenuBGMusicPath = "sound/background-DLTTAD.ogg",
            GlassSound = "sound/glassbreak.ogg";



    //Audios
    public Sound walkingSound = Gdx.audio.newSound(Gdx.files.internal("sound/walkingsound.wav"));
}