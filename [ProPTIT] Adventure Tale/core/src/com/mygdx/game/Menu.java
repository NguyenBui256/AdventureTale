package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static helper.Constants.*;

public class Menu {
    public static final int WIDTH = APP_WIDTH, HEIGHT = APP_HEIGHT;
    public int xSound;
    public float playButtonWidth = 236, playButtonHeight = 124, xPlay, yPlay;
    public ImageButton playButton, tutButton, tutButtonClick, playButtonClick, controlBoard, exitButton, exitClickButton;
    public ImageButton background;
    public Texture name;
    public Main main;
    public Stage stage;
    protected ImageButton MusicOnButton, MusicOnClickButton, MusicOffButton, MusicOffClickButton;

    public Music bonusSound = Gdx.audio.newMusic(Gdx.files.internal(CLICK_SOUND)),
            bgMusic = Gdx.audio.newMusic(Gdx.files.internal(MENU_BG_MUSIC));
    public Menu (Main main) {
        this.main = main;
        stage = new Stage();
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_PLAY_BTN))));
        playButtonClick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_PLAY_BTN_HOVER))));
        tutButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_TUTORIAL_BTN))));
        tutButtonClick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_TUTORIAL_BTN_HOVER))));
        background = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_BG_IMAGE))));
        controlBoard = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_TUTORIAL_CONTENT))));
        MusicOnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MUSIC_ON_BTN))));
        MusicOnClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MUSIC_ON_BTN_HOVER))));
        MusicOffButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MUSIC_OFF_BTN))));
        MusicOffClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MUSIC_OFF_BTN_HOVER))));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_EXIT_BTN))));
        exitClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MENU_EXIT_BTN_HOVER))));

        bonusSound.setVolume(0.5f);
        bgMusic.setVolume(0.3f);
        bgMusic.setLooping(true);
        bgMusic.play();

        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitButton.setVisible(false);
                exitClickButton.setVisible(true);
                bonusSound.play();
                bgMusic.stop();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                exitButton.setVisible(true);
                exitClickButton.setVisible(false);
                Gdx.app.exit();
            }
        });
        MusicOnButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MusicOnButton.setVisible(false);
                MusicOnClickButton.setVisible(true);
                bgMusic.stop();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MusicOffButton.setVisible(true);
                MusicOnClickButton.setVisible(false);
            }
        });
        MusicOffButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MusicOffButton.setVisible(false);
                MusicOffClickButton.setVisible(true);
                bgMusic.play();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MusicOnButton.setVisible(true);
                MusicOffClickButton.setVisible(false);
            }
        });
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playButton.setVisible(false);
                playButtonClick.setVisible(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playButton.setVisible(true);
                playButtonClick.setVisible(false);
                bonusSound.play();
                main.levelScreen = new LevelScreen(main);
                main.setScreen(main.levelScreen);
            }
        });
        tutButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bonusSound.play();
                tutButton.setVisible(false);
                tutButtonClick.setVisible(true);
                controlBoard.setVisible(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tutButton.setVisible(true);
                tutButtonClick.setVisible(false);
                controlBoard.setVisible(false);
            }
        });


        background.setVisible(true);
        MusicOnButton.setVisible(true); MusicOnClickButton.setVisible(false);
        MusicOffButton.setVisible(false); MusicOffClickButton.setVisible(false);
        playButton.setVisible(true); playButtonClick.setVisible(false);
        tutButton.setVisible(true); tutButtonClick.setVisible(false);
        exitButton.setVisible(true); exitClickButton.setVisible(false);
        controlBoard.setVisible(false);

        xSound = 12;
        xPlay = (WIDTH - playButtonWidth) / 2;
        yPlay = (HEIGHT - playButtonHeight) / 2 - 10;

        background.setSize(WIDTH, HEIGHT);
        background.setPosition(0, 0);

        MusicOnButton.setSize(ICON_SIZE+ 30, ICON_SIZE+ 30);
        MusicOnButton.setPosition(xSound, xSound);

        MusicOnClickButton.setSize(ICON_SIZE + 30, ICON_SIZE+ 30);
        MusicOnClickButton.setPosition(xSound, xSound);

        MusicOffButton.setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        MusicOffButton.setPosition(xSound, xSound);

        MusicOffClickButton.setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        MusicOffClickButton.setPosition(xSound, xSound);

        playButton.setSize(playButtonWidth, playButtonHeight);
        playButton.setPosition(xPlay, yPlay);

        playButtonClick.setSize(playButtonWidth, playButtonHeight);
        playButtonClick.setPosition(xPlay, yPlay);

        tutButton.setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        tutButton.setPosition(WIDTH - xSound - ICON_SIZE - 30, xSound);

        tutButtonClick.setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        tutButtonClick.setPosition(WIDTH - xSound - ICON_SIZE - 30, xSound);

        exitButton.setSize(playButtonWidth - 35, playButtonHeight - 35);
        exitButton.setPosition(xPlay + 15, yPlay - playButtonHeight + 40);

        exitClickButton.setSize( playButtonWidth - 35, playButtonHeight - 35);
        exitClickButton.setPosition(xPlay + 15, yPlay - playButtonHeight + 40);

        controlBoard.setSize(600, 586);
        controlBoard.setPosition((WIDTH - 600) / 2, (HEIGHT - 586) / 2);

        stage.addActor(background); // index 0
        stage.addActor(MusicOnButton); //index 1
        stage.addActor(MusicOnClickButton); //index 2
        stage.addActor(MusicOffButton); //index 3
        stage.addActor(MusicOffClickButton); //index 4
        stage.addActor(playButton); // index 5
        stage.addActor(playButtonClick); // index 6
        stage.addActor(tutButton); // index 7
        stage.addActor(tutButtonClick); // index 8
        stage.addActor(exitButton); //index 9
        stage.addActor(exitClickButton); //index 10
        stage.addActor(controlBoard); // index 11

    }
    public void update() {
    }
}
