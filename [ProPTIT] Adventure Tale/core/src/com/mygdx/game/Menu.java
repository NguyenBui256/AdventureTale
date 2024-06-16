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
    public static final int WIDTH = APP_WIDTH;
    public static final int HEIGHT = APP_HEIGHT;
    float playButtonWidth = 236;
    float playButtonHeight = 124;
    ImageButton playButton, tutButton, tutButtonClick, playButtonClick, controlBoard, exitButton, exitClickButton;
    public ImageButton background;
    public Texture name;
    Main main;
    Stage stage;
    protected ImageButton MusicOnButton, MusicOnClickButton, MusicOffButton, MusicOffClickButton;

    public Music bonusSound = Gdx.audio.newMusic(Gdx.files.internal(BonusSound)),
            bgMusic = Gdx.audio.newMusic(Gdx.files.internal(MenuBGMusicPath));
    public Menu (Main main) {
        this.main = main;
        stage = new Stage();
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Default.png"))));
        playButtonClick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Hover.png"))));
        tutButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("TutButton.png"))));
        tutButtonClick = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("TutPressButton.png"))));
        background = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(BackGroundMenuPath))));
        controlBoard = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("ControlBoard.png"))));
        MusicOnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MusicOnPath))));
        MusicOnClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MusicOnClickPath))));
        MusicOffButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MusicOffPath))));
        MusicOffClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(MusicOffClickPath))));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Exit.png"))));
        exitClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("ExitPress.png"))));


        bgMusic.setVolume(0.3f);
        bgMusic.setLooping(true);
        bgMusic.play();
        bonusSound.setVolume(0.5f);

        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitButton.setVisible(false);
                exitClickButton.setVisible(true);
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
        int xSound = 12;
        float xPlay = (WIDTH - playButtonWidth) / 2;
        float yPlay = (HEIGHT - playButtonHeight) / 2 - 10;
        stage.getActors().get(0).setPosition(0, 0);
        stage.getActors().get(0).setSize(WIDTH, HEIGHT);
        stage.getActors().get(1).setSize(ICON_SIZE+ 30, ICON_SIZE+ 30);
        stage.getActors().get(1).setPosition(xSound, xSound);
        stage.getActors().get(2).setSize(ICON_SIZE + 30, ICON_SIZE+ 30);
        stage.getActors().get(2).setPosition(xSound, xSound);
        stage.getActors().get(3).setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        stage.getActors().get(3).setPosition(xSound, xSound);
        stage.getActors().get(4).setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        stage.getActors().get(4).setPosition(xSound, xSound);
        stage.getActors().get(5).setSize(playButtonWidth, playButtonHeight);
        stage.getActors().get(5).setPosition(xPlay, yPlay);
        stage.getActors().get(6).setSize(playButtonWidth, playButtonHeight);
        stage.getActors().get(6).setPosition(xPlay, yPlay);
        stage.getActors().get(7).setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        stage.getActors().get(7).setPosition(WIDTH - xSound - ICON_SIZE - 30, xSound);
        stage.getActors().get(8).setSize(ICON_SIZE + 30, ICON_SIZE + 30);
        stage.getActors().get(8).setPosition(WIDTH - xSound - ICON_SIZE - 30, xSound);
        stage.getActors().get(9).setSize(playButtonWidth - 35, playButtonHeight - 35);
        stage.getActors().get(9).setPosition(xPlay + 15, yPlay - playButtonHeight + 40);
        stage.getActors().get(10).setSize( playButtonWidth - 35, playButtonHeight - 35);
        stage.getActors().get(10).setPosition(xPlay + 15, yPlay - playButtonHeight + 40);
        stage.getActors().get(11).setSize(600, 586);
        stage.getActors().get(11).setPosition((WIDTH - 600) / 2, (HEIGHT - 586) / 2);
    }
}
