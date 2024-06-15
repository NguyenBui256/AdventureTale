package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import helper.DrawText;

import java.util.ArrayList;

import static helper.Constants.BonusSound;

public class LevelScreen implements Screen {
    public static ArrayList<Boolean> point;
    Texture level, levelClick, lock, soundOffButton, soundOffButtonClick, soundOnButton, soundOnButtonClick;
    float levelWidth = 100;
    float levelHeight = 100;
    float space = 30;
    public static Texture background;
    Main main;
    DrawText drawText;
    public Music bonusSound = Gdx.audio.newMusic(Gdx.files.internal(BonusSound));

    public LevelScreen(Main main) {
        this.main = main;
        drawText = new DrawText(main);
        level = new Texture("Default@2x-1.png");
        levelClick = new Texture("Hover@2x.png");
        lock = new Texture("lock (1).png");
        background = new Texture("bg3.png");
        soundOffButton = new Texture("soundOff.png");
        soundOffButtonClick = new Texture("soundOffPress.png");
        soundOnButton = new Texture("soundOn.png");
        soundOnButtonClick = new Texture("soundOnPress.png");
        point = new ArrayList<>();
        for (int i = 0; i < 12; ++i) {
            point.add(false);
        }

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
        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
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
                        main.menuScreen.bgMusic.stop();
                    }
                }
                ++d;
            }
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