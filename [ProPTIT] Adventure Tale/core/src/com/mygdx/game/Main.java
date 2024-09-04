package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.io.IOException;
import static helper.Constants.*;

public class Main extends Game {
    public static MenuScreen menuScreen;
    public static LevelScreen levelScreen;
    public static GameScreen gameScreen;
    public static SpriteBatch batch;
    public static BitmapFont font;
    public Image blackFade;
    public Stage stage;
    public TransitionScreen transitionScreen;
    public static int level;
    public static int chooseLevel;

    public Main() throws IOException {
    }
    @Override
    public void create() {
        level = 1;
        batch = new SpriteBatch();
        font = new BitmapFont();

        Pixmap pixmap = new Pixmap((Gdx.files.internal(CURSOR_ICON)));

        int xHotspot = 15, yHotspot = 15;
        Cursor cursor = Gdx.graphics.newCursor(pixmap,xHotspot,yHotspot);
        pixmap.dispose();
        Gdx.graphics.setCursor(cursor);

        batch = new SpriteBatch();
        font = new BitmapFont();
        menuScreen = new MenuScreen(this);
        transitionScreen = new TransitionScreen();
        this.setScreen(menuScreen);

        stage = new Stage(new StretchViewport(APP_WIDTH, APP_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        blackFade = new Image(new Texture(Gdx.files.internal(BLACK_SCENE)));
        blackFade.setOrigin(blackFade.getWidth() / 2, blackFade.getHeight() / 2);

        stage.addActor(blackFade);
    }
    @Override
    public void render() {super.render();}

    @Override
    public void resize(int i, int i1) {
        super.resize(i, i1);
    }
}
