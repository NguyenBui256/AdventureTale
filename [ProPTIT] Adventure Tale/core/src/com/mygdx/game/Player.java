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
    final float SIZE_ENTITY = 50;
    public Player(Box box) {
        this.box = box;
//        img = new Texture("red.png");
        img = new Texture("Running (32 x 32).png");
        roll = 0;
        rolls = new Animation[1];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(img, 32, 32);
        rolls[roll] = new Animation(0.2f, rollSpriteSheet[0]);
    }
    public void collisionBox() {
        apexTopLeft.x = box.getCoordinates().x;
        apexTopLeft.y = box.getCoordinates().y - SIZE_ENTITY;
        apexTopRight.x = box.getCoordinates().x + SIZE_ENTITY;
        apexTopRight.y = box.getCoordinates().y - SIZE_ENTITY;
        apexBottomLeft = box.getCoordinates();
        apexBottomRight.x = box.getCoordinates().x + SIZE_ENTITY;
        apexBottomRight.y = box.getCoordinates().y;
//        apexTopLeft.x = coordinates.x;
//        apexTopLeft.y = coordinates.y - SIZE_ENTITY;
//        apexTopRight.x = coordinates.x + SIZE_ENTITY;
//        apexTopRight.y = coordinates.y - SIZE_ENTITY;
//        apexBottomLeft = coordinates;
//        apexBottomRight.x = coordinates.x + SIZE_ENTITY;
//        apexBottomRight.y = coordinates.y;
        ArrayList<Vector2> list = new ArrayList<>();
        list.add(apexTopLeft);
        list.add(apexTopRight);
        list.add(apexBottomLeft);
        list.add(apexBottomRight);
        for (Vector2 i : list) {
            if (i.x >= coordinates.x && i.x <= coordinates.x + SIZE_ENTITY && i.y >= coordinates.y - SIZE_ENTITY && i.y <= coordinates.y) {
                System.out.println("xe tang: " + box.getCoordinates().x + " " + box.getCoordinates().y + "cu cai: " + coordinates.x + " " + coordinates.y);
                if (direction == 'U') {
                    System.out.println(1);
                    box.getCoordinates().y += GameScreen.speed * Gdx.graphics.getDeltaTime();
                } else if (direction == 'D') {
                    System.out.println(2);
                    box.getCoordinates().y -= GameScreen.speed * Gdx.graphics.getDeltaTime();
                } else if (direction == 'L') {
                    System.out.println(3);
                    box.getCoordinates().x -= GameScreen.speed * Gdx.graphics.getDeltaTime();
                } else if (direction == 'R') {
                    System.out.println(4);
                    box.getCoordinates().x += GameScreen.speed * Gdx.graphics.getDeltaTime();
                }
            }
        }

//        // 4 dinh cua cu cai
//        apexTopLeft.x = box.getCoordinates().x;
//        apexTopLeft.y = box.getCoordinates().y - SIZE_ENTITY;
//        apexTopRight.x = box.getCoordinates().x + SIZE_ENTITY;
//        apexTopRight.y = box.getCoordinates().y - SIZE_ENTITY;
//        apexBottomLeft = box.getCoordinates();
//        apexBottomRight.x = box.getCoordinates().x + SIZE_ENTITY;
//        apexBottomRight.y = box.getCoordinates().y;
//        // left, cu cai right
//        if (apexBottomRight.x <= coordinates.x - 10 && apexBottomLeft.x >= coordinates.x - 110 && apexBottomRight.y >= MenuScreen.Height - (coordinates.y + SIZE_ENTITY) && apexBottomRight.y <= MenuScreen.Height - coordinates.y) {
//            box.getCoordinates().x -= GameScreen.speed * Gdx.graphics.getDeltaTime();
//        }
//        // cu cai left
//        if (apexBottomLeft.x == coordinates.x + SIZE_ENTITY && apexBottomRight.y >= MenuScreen.Height - (coordinates.y + SIZE_ENTITY) && apexBottomRight.y <= MenuScreen.Height - coordinates.y) {
//            box.getCoordinates().x += GameScreen.speed * Gdx.graphics.getDeltaTime();
//        }

//            if (i.x >= coordinates.x && i.x <= coordinates.x + SIZE_ENTITY && i.y >= MenuScreen.Height - (coordinates.y + SIZE_ENTITY) && i.y <= MenuScreen.Height - coordinates.y) {
//                if (direction == 'U') {
//                    box.getCoordinates().y = coordinates.y - SIZE_ENTITY;
//                } else if (direction == 'D') {
//                    box.getCoordinates().y = coordinates.y + SIZE_ENTITY;
//                } else if (direction == 'L') {
//                    box.getCoordinates().x = coordinates.x - SIZE_ENTITY;
//                } else if (direction == 'R') {
//                    box.getCoordinates().x = coordinates.x + SIZE_ENTITY;
//                }
//            }
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
//        System.out.println("cu cai: " + coordinates.x + " " + coordinates.y);
        batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), coordinates.x, coordinates.y, 100, 100);
//        batch.draw(img, coordinates.x, coordinates.y);
    }
}
