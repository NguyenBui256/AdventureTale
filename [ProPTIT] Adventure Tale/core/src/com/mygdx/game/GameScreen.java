package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;

    public OrthographicCamera camera;

    float speed = 120;
    Main game;
    Texture img;
    float x;
    float y;
    int roll;
    float stateTime;
    Animation[] rolls;
    public GameScreen (Main game){
        this.game = game;
        img = new Texture("Running (32 x 32).png");
        roll = 0;
        rolls = new Animation[1];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(img, 32, 32);
        rolls[roll] = new Animation(0.2f, rollSpriteSheet[0]);
    }
    @Override
    public void show() {
        map = new TmxMapLoader().load("map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

    }

    @Override
    public void resize(int i, int i1) {
        camera.viewportWidth = i;
        camera.viewportHeight = i1;
        camera.position.set(i / 3, i1 / 3, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x += speed * Gdx.graphics.getDeltaTime();
        }
        stateTime += delta;
        game.batch.begin();
        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, 100, 100);
        //game.batch.draw(img, x, y);
        game.batch.end();
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
