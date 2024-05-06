package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import objects.box.Box;
import objects.player.Player;

import static helper.Constants.PPM;

public class GameScreen implements Screen {
    public float stateTime;
    public Main game;
    public World world;
    public Player player;
    public Box box;
    public TileMapHelper tileMapHelper;
    public OrthographicCamera camera;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer box2DDebugRenderer;
    public GameScreen (Main game){
        this.world = new World(new Vector2(0,-25f), false);
        this.game = game;
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.renderer = tileMapHelper.setupMap();

    }
    @Override
    public void show() {
        camera = new OrthographicCamera(750, 500);
//        camera = new OrthographicCamera(960, 640);
    }

    public void update(float dt){
        world.step(1/60f, 6, 2);

        Vector3 position = camera.position;
        position.x = Math.round(player.body.getPosition().x * PPM * 10 / 10f);
        position.y = Math.round(player.body.getPosition().y * PPM * 10 / 10f);
        camera.position.set(position);

        player.update(dt);
        box.update(dt);
        game.batch.setProjectionMatrix(camera.combined);
        camera.update();
        renderer.setView(camera);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render(float delta) {
        this.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
        stateTime += delta;

        game.batch.begin();

        player.draw(game.batch);
        box.draw(game.batch);

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
        renderer.dispose();
        box2DDebugRenderer.dispose();
    }
}
