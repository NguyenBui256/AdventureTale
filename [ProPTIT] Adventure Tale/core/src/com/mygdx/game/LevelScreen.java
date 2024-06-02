package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class LevelScreen implements Screen {
    ArrayList<Texture> list;
    float levelWidth = 100;
    float levelHeight = 110;
    float space = 30;
    Main main;
    public LevelScreen(Main main) {
        this.main = main;
        Texture level = new Texture("Dummy@0.5x.png");
        Texture levelLocked = new Texture("Locked@0.5x.png");
        list = new ArrayList<>();
        list.add(level);
        for (int i = 0; i < 11; ++i) {
            list.add(levelLocked);
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        float xBegin = (float) MenuScreen.Width / 2 - 2 * levelWidth - 3 * space / 2;
        float yBegin = MenuScreen.Height - (MenuScreen.Height - 3 * levelHeight - 2 * space) / 2 - levelHeight + space;
        int d = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 3; ++j) {
                main.batch.draw(list.get(d), xBegin + i * (levelWidth + space), yBegin - j * (levelHeight + space), levelWidth, levelHeight);
                main.font.setColor(Color.RED);
                ++d;
                main.font.draw(main.batch, d + "", xBegin + i * (levelWidth + space) + levelWidth / 2, yBegin - j * (levelHeight + space) + levelHeight / 2);
            }
        }
        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
        }
        if (Gdx.input.getX() >= xBegin + 0 * (levelWidth + space) && Gdx.input.getX() <= xBegin + 0 * (levelWidth + space) + levelWidth
                && Gdx.input.getY() <= MenuScreen.Height - (yBegin - 0 * (levelHeight + space)) && Gdx.input.getY() >= MenuScreen.Height - (yBegin - 0 * (levelHeight + space) + levelHeight)) {
            if (Gdx.input.isTouched()) {
                this.hide();
                main.gameScreen = new GameScreen(main, this);
                main.setScreen(main.gameScreen);
            }
        }
        if (Gdx.input.getX() >= 12 && Gdx.input.getX() <= 12 + MenuScreen.iconSize && Gdx.input.getY() >= 12 && Gdx.input.getY() <= 12 + MenuScreen.iconSize) {
            if (MenuScreen.checkSound) {
                main.batch.draw(MenuScreen.soundOnButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
                if (Gdx.input.isTouched()) {
                    MenuScreen.checkSound = false;
                    main.batch.draw(MenuScreen.soundOffButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
                }
            } else {
                main.batch.draw(MenuScreen.soundOffButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
                if (Gdx.input.isTouched()) {
                    MenuScreen.checkSound = true;
                    main.batch.draw(MenuScreen.soundOnButtonClick, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
                }
            }
        } else {
            if (MenuScreen.checkSound) {
                main.batch.draw(MenuScreen.soundOnButton, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
            } else {
                main.batch.draw(MenuScreen.soundOffButton, 12, 12, MenuScreen.iconSize, MenuScreen.iconSize);
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
