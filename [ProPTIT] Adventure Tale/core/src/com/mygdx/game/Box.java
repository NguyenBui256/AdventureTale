package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;

public class Box extends Sprite {
    private Vector2 coordinates = new Vector2();
    int roll;
    float stateTime;
    Animation[] rolls;
    Texture img;
    public Vector2 getCoordinates() {
        return coordinates;
    }

    public Box() {
        coordinates.x = 100;
        coordinates.y = 100;
        img = new Texture("Tank_Movement (32 x 32).png");
        roll = 0;
        rolls = new Animation[1];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(img, 32, 32);
        rolls[roll] = new Animation(0.2f, rollSpriteSheet[0]);
    }

    @Override
    public void draw(Batch batch, float delta) {
        stateTime += delta;
        batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), coordinates.x, coordinates.y, 100, 100);
        //game.batch.draw(img, x, y);
    }
}
