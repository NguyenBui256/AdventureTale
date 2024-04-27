package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
//        font = new BitmapFont(Gdx.files.internal("iCiel Crocante.otf"));
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("iCiel Crocante.otf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 24; // Cỡ chữ
//        font = generator.generateFont(parameter);
//        generator.dispose(); // Lưu ý giải phóng tài nguyên generator sau khi sử dụng
        this.setScreen(new MenuScreen(this));
    }
    @Override
    public void render() {
        super.render();
    }
}
