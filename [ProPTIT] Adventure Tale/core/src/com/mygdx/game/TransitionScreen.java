package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static helper.Constants.*;

public class TransitionScreen {
    public Main main;
    public Stage fadeInStage, fadeOutStage;
    public Image fadeIn, fadeOut;
    public boolean transitionInFlag = false, transitionOutFlag = false, transitionRunnning = false;
    public int transitionState = 0;
    public long time = 0;

    public TransitionScreen(Main main){

        this.main = main;

        fadeInStage = new Stage(new StretchViewport(APP_WIDTH, APP_HEIGHT));
        fadeOutStage = new Stage(new StretchViewport(APP_WIDTH, APP_HEIGHT));

        fadeIn = new Image(new Texture(Gdx.files.internal(BlackFadePath)));
        fadeIn.setOrigin(fadeIn.getWidth() / 2, fadeIn.getHeight());
        fadeIn.setPosition(0, 0);
        fadeIn.addAction(sequence(alpha(0f),fadeIn(0.5f), fadeOut(0.5f)));
        fadeInStage.addActor(fadeIn);

        fadeOut = new Image(new Texture(Gdx.files.internal(BlackFadePath)));
        fadeOut.setOrigin(fadeOut.getWidth() / 2, fadeOut.getHeight());
        fadeOut.setPosition(0, 0);
        fadeOut.addAction(sequence(alpha(0), fadeIn(0.5f), delay(0.5f)));
        fadeOutStage.addActor(fadeOut);
    }


}
