package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public SpriteBatch batch;
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));
    }
    @Override
    public void render() {
        super.render();
    }
}
