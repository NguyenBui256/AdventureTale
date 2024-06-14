package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static helper.Constants.APP_HEIGHT;
import static helper.Constants.APP_WIDTH;

public class Main extends Game {
    public MenuScreen menuScreen;
    public LevelScreen levelScreen;
    public GameScreen gameScreen;
    public SpriteBatch batch;
    public BitmapFont font;
    public Image blackFade;
    public Stage stage;
    public TransitionScreen transitionScreen;
    public static int level;
    public static int chooseLevel;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        level = 1;
        menuScreen = new MenuScreen(this);
        transitionScreen = new TransitionScreen(this);
        this.setScreen(menuScreen);

        stage = new Stage(new StretchViewport(APP_WIDTH, APP_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        blackFade = new Image(new Texture(Gdx.files.internal("blackFade.png")));
        blackFade.setOrigin(blackFade.getWidth() / 2, blackFade.getHeight() / 2);

        stage.addActor(blackFade);
    }
    @Override
    public void render() {super.render();}

    @Override
    public void resize(int i, int i1) {
        super.resize(i, i1);
    }

    public void changeScreen(Screen targetScreen, float delta){
        this.setScreen(targetScreen);
    }
}
