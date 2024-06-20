package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import helper.DrawText;

import java.util.ArrayList;

import static helper.Constants.*;

public class LevelScreen implements Screen {
    public static ArrayList<Boolean> point;
    Texture level, levelClick, lock, soundOffButton, soundOffButtonClick, soundOnButton, soundOnButtonClick, back, backClick;
    float levelWidth = LEVEL_BUTTON_SIZE;
    float levelHeight = LEVEL_BUTTON_SIZE;
    float space = LEVEL_BUTTON_PADDING;
    public static Texture background;
    Main main;
    DrawText drawText;
    public Music bonusSound = Gdx.audio.newMusic(Gdx.files.internal(CLICK_SOUND));

    public LevelScreen(Main main) {
        this.main = main;
        drawText = new DrawText(main);
        level = new Texture(LEVEL_ICON_BG);
        levelClick = new Texture(LEVEL_ICON_HOVER_BG);
        lock = new Texture(LEVEL_LOCK_ICON);
        background = new Texture(LEVEL_BG_IMAGE);
        soundOffButton = new Texture(SOUND_OFF_BTN);
        soundOffButtonClick = new Texture(SOUND_OFF_BTN_HOVER);
        soundOnButton = new Texture(SOUND_ON_BTN);
        soundOnButtonClick = new Texture(SOUND_ON_BTN_HOVER);
        back = new Texture(LEVEL_BACK_BTN);
        backClick = new Texture(LEVEL_BACK_BTN_HOVER);
        point = new ArrayList<>();
        for (int i = 0; i < 12; ++i) point.add(false);
        bonusSound.setVolume(0.4f);
    }
    public void update() {
        for (int i = 0; i < Main.level; ++i) {
            point.set(i, true);
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        main.batch.begin();
        main.batch.draw(background, 0, 0, MenuScreen.WIDTH, MenuScreen.HEIGHT);
        float xBegin = (float) MenuScreen.WIDTH / 2 - 2 * levelWidth - 3 * space / 2;
        float yBegin = MenuScreen.HEIGHT - (MenuScreen.HEIGHT - 3 * levelHeight - 2 * space) / 2 - levelHeight + space;
        int d = 0;
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 4; ++i) {
                if (!point.get(d)) {
                    main.batch.draw(lock, xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                } else {
                    main.batch.draw(level, xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                    drawText.drText("font/Unnamed.fnt", Color.RED, (d + 1) + "",
                            xBegin + i * (levelWidth + space) + levelWidth / 2 - 8 - (float) (d + 1 + "").length() /2,
                            yBegin - j * (levelHeight + space) + levelHeight / 2 + 16, 0.4f);
                }
                ++d;
            }
        }
        d = 0;
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 4; ++i) {
                if (point.get(d) && Gdx.input.getX() >= xBegin + i * (levelWidth + space) && Gdx.input.getX() <= xBegin + i * (levelWidth + space) + levelWidth
                        && Gdx.input.getY() <= MenuScreen.HEIGHT - (yBegin - j * (levelHeight + space)) && Gdx.input.getY() >= MenuScreen.HEIGHT - (yBegin - j * (levelHeight + space) + levelHeight)){
                    main.batch.draw(levelClick, xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                    if (Gdx.input.isTouched()) {
                        Main.chooseLevel = d + 1;
                        this.hide();
                        bonusSound.play();
                        main.gameScreen = new GameScreen(main, this);
                        main.setScreen(main.gameScreen);
                        main.menuScreen.menu.bgMusic.stop();
                    }
                }
                ++d;
            }
        }
        int xBack = 20;
        if (Gdx.input.getX() >= xBack && Gdx.input.getX() <= xBack + ICON_SIZE && Gdx.input.getY() <= xBack + ICON_SIZE && Gdx.input.getY() >= xBack) {
            main.batch.draw(backClick, xBack, APP_HEIGHT - 3 * xBack, ICON_SIZE, ICON_SIZE);
            if (Gdx.input.isTouched()) {
                this.dispose();
                bonusSound.play();
                main.setScreen(main.menuScreen);
            }
        } else {
            main.batch.draw(back, xBack, APP_HEIGHT - 3 * xBack, ICON_SIZE, ICON_SIZE);
        }
        main.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}