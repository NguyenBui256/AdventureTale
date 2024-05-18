package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import helper.WorldContactListener;
import objects.box.Box;
import objects.box.Bubble;
import objects.player.Player;

import java.util.ArrayList;

import static helper.Constants.PPM;

public class GameScreen implements Screen {
    public float stateTime;
    public Main game;
    public World world;
    public Player player;
    public Texture CuCaiButton, BachTuocButton, CucDaButton;
    protected Hud hud;
    public boolean DestroyFlag = false;
    public ArrayList<Box> boxList;
    public ArrayList<Bubble> bubbleList, destroyList;
    public TileMapHelper tileMapHelper;
    public OrthographicCamera staticCamera;
    public OrthographicCamera playerCamera;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer box2DDebugRenderer;
    public GameScreen (Main game){
        this.world = new World(new Vector2(0,-25f), false);
        this.world.setContactListener(new WorldContactListener(this.world, this));
        this.game = game;
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.boxList = new ArrayList<>();
        this.bubbleList = new ArrayList<>();
        this.destroyList = new ArrayList<>();
        box2DDebugRenderer.setDrawJoints(false);
        box2DDebugRenderer.setDrawBodies(false);
        box2DDebugRenderer.setDrawContacts(false);
        this.tileMapHelper = new TileMapHelper(this);
        this.renderer = tileMapHelper.setupMap();
        CuCaiButton = new Texture("CuCaiButton.png");
        BachTuocButton = new Texture("BachTuocButton.png");
        CucDaButton = new Texture("CucDaButton.png");
        this.hud = new Hud(player);
        Gdx.input.setInputProcessor(hud.stage);
    }
    @Override
    public void show() {
        staticCamera = new OrthographicCamera(750, 500);
        playerCamera = new OrthographicCamera(750, 500);
    }

    public void update(float dt){
        if(DestroyFlag){
            for(Bubble bubble : destroyList){
                System.out.println("Destroyed!");
                world.destroyBody(bubble.body);
                bubbleList.remove(bubble);
            }
            destroyList.clear();
            DestroyFlag = false;
        }
        world.step(1/60f, 6, 2);

        Vector3 position = playerCamera.position;
        position.x = Math.round(player.body.getPosition().x * PPM * 10 / 10f);
        position.y = Math.round(player.body.getPosition().y * PPM * 10 / 10f);
        playerCamera.position.set(position);
        staticCamera.position.set(position);
        hud.update();
        player.update(dt);
        for(Bubble bubble : bubbleList) bubble.update(dt);
        for(Box box : boxList) box.update(dt);
        playerCamera.update();
        staticCamera.update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.stage.act(Gdx.graphics.getDeltaTime());
        hud.stage.draw();

        this.update(delta);
        renderer.setView(playerCamera);
        renderer.render();
        box2DDebugRenderer.render(world, playerCamera.combined.scl(PPM));
        box2DDebugRenderer.render(world, staticCamera.combined.scl(PPM));

        stateTime += delta;

        game.batch.setProjectionMatrix(staticCamera.combined);
        game.batch.begin();
        for(Bubble bubble : bubbleList) bubble.draw(game.batch);
        for(Box box : boxList) box.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(playerCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
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