package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player extends Sprite {
    private Vector2 coordinates = new Vector2();
    private Vector2 apexTopLeft = new Vector2();
    private Vector2 apexTopRight = new Vector2();
    private Vector2 apexBottomLeft = new Vector2();
    private Vector2 apexBottomRight = new Vector2();
    int roll;
    float stateTime;
    Animation[] rolls;
    Texture img;
    Box box;
    char direction;
    public Player(Box box) {
        this.box = box;
        img = new Texture("Running (32 x 32).png");
        roll = 0;
        rolls = new Animation[1];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(img, 32, 32);
        rolls[roll] = new Animation(0.2f, rollSpriteSheet[0]);
    }
    public void collisionBox() {
        apexTopLeft.x = coordinates.x;
        apexTopLeft.y = coordinates.y - 100;
        apexTopRight.x = coordinates.x + 100;
        apexTopRight.y = coordinates.y - 100;
        apexBottomLeft = coordinates;
        apexBottomRight.x = coordinates.x + 100;
        apexBottomRight.y = coordinates.y;
        ArrayList<Vector2> list = new ArrayList<>();
        list.add(apexTopLeft);
        list.add(apexTopRight);
        list.add(apexBottomLeft);
        list.add(apexBottomRight);
        for (Vector2 i : list) {
            if (i.x >= coordinates.x && i.x <= coordinates.x + 100 && i.y >= MenuScreen.Height - (coordinates.y + 100) && i.y <= MenuScreen.Height - coordinates.y) {
                if (direction == 'U') {
                    box.getCoordinates().y += GameScreen.speed * Gdx.graphics.getDeltaTime();
                } else if (direction == 'D') {
                    box.getCoordinates().y -= GameScreen.speed * Gdx.graphics.getDeltaTime();
                } else if (direction == 'L') {
                    box.getCoordinates().x -= GameScreen.speed * Gdx.graphics.getDeltaTime();
                } else if (direction == 'R') {
                    box.getCoordinates().x += GameScreen.speed * Gdx.graphics.getDeltaTime();
                }
            }
        }
    }
    @Override
    public void draw(Batch batch, float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            coordinates.y += GameScreen.speed * Gdx.graphics.getDeltaTime();
            direction = 'U';
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            coordinates.y -= GameScreen.speed * Gdx.graphics.getDeltaTime();
            direction = 'D';
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            coordinates.x -= GameScreen.speed * Gdx.graphics.getDeltaTime();
            direction = 'L';
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            coordinates.x += GameScreen.speed * Gdx.graphics.getDeltaTime();
            direction = 'R';
        }
        collisionBox();
        stateTime += delta;
        batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), coordinates.x, coordinates.y, 100, 100);
        //game.batch.draw(img, x, y);
    }
}
