package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import helper.DrawText;

import java.util.ArrayList;

public class LevelScreen implements Screen {
    public static ArrayList<Boolean> point;
    Texture level, levelClick, lock, soundOffButton, soundOffButtonClick, soundOnButton, soundOnButtonClick;
    float levelWidth = 100;
    float levelHeight = 100;
    float space = 30;
    public static Texture background;
    Main main;
    DrawText drawText;
    //    Sound sound = Gdx.audio.newSound(Gdx.files.internal("onoff.wav"));
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
    }
    public void update() {
        System.out.println(Main.level);
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
        main.batch.draw(background, 0, 0, MenuScreen.Width, MenuScreen.Height);
        float xBegin = (float) MenuScreen.Width / 2 - 2 * levelWidth - 3 * space / 2;
        float yBegin = MenuScreen.Height - (MenuScreen.Height - 3 * levelHeight - 2 * space) / 2 - levelHeight + space;
        int d = 0;
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 4; ++i) {
                if (!point.get(d)) {
                    main.batch.draw(lock, xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                } else {
                    main.batch.draw(level, xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                    drawText.drText("font/Unnamed.fnt", Color.RED, (d + 1) + "", xBegin + i * (levelWidth + space) + levelWidth / 2 - 8, yBegin - j * (levelHeight + space) + levelHeight / 2 + 16, 0.4f);
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
                        && Gdx.input.getY() <= MenuScreen.Height - (yBegin - j * (levelHeight + space)) && Gdx.input.getY() >= MenuScreen.Height - (yBegin - j * (levelHeight + space) + levelHeight)) {
                    main.batch.draw(levelClick, xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                    if (Gdx.input.isTouched()) {
                        Main.chooseLevel = d + 1;
                        this.hide();
                        main.gameScreen = new GameScreen(main, this);
                        main.setScreen(main.gameScreen);
                    }
                }
                ++d;
            }
        }
//        if (Gdx.input.getX() >= 12 && Gdx.input.getX() <= 12 + MenuScreen.iconSize && Gdx.input.getY() >= 12 && Gdx.input.getY() <= 12 + MenuScreen.iconSize) {
//            if (MenuScreen.checkSound) {
//                main.batch.draw(soundOnButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
//                if (Gdx.input.isTouched()) {
////                    sound.play();
//                    MenuScreen.checkSound = false;
//                    main.batch.draw(soundOffButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
//                }
//            } else {
//                main.batch.draw(soundOffButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
//                if (Gdx.input.isTouched()) {
////                    sound.play();
//                    MenuScreen.checkSound = true;
//                    main.batch.draw(soundOnButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
//                }
//            }
//        } else {
//            if (MenuScreen.checkSound) {
//                main.batch.draw(soundOnButton, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
//            } else {
//                main.batch.draw(soundOffButton, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
//            }
//        }
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
