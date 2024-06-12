package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public MenuScreen menuScreen;
    public LevelScreen levelScreen;
    public GameScreen gameScreen;
    public SpriteBatch batch;
    public BitmapFont font;
    public static int level;
    public static int chooseLevel;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        level = 1;
        menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen);
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int i, int i1) {
        super.resize(i, i1);
    }
}
