package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import helper.TileMapHelper;
import helper.WorldContactListener;
import objects.box.Box;
import objects.box.Bubble;

import objects.box.Door;
import objects.player.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static helper.Constants.*;


import static helper.Constants.PPM;


public class GameScreen implements Screen {
    public float stateTime;
    public boolean endMap = false, DestroyFlag = false;;
    protected Hud hud;
    public Main game;
    public LevelScreen levelScreen;
    public TransitionScreen TRS;
    public World world;
    public Player player;

    public Door door;
    public Texture CuCaiButton, BachTuocButton, CucDaButton;
    public Texture menu, restart;

    public ArrayList<Box> boxList;
    public ArrayList<Bubble> bubbleList, destroyList;
    public TileMapHelper tileMapHelper;
    public OrthographicCamera staticCamera;
    public OrthographicCamera playerCamera;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen (Main game, LevelScreen levelScreen){
        this.TRS = new TransitionScreen(game);
        TRS.transitionInFlag = true;
        this.levelScreen = levelScreen;
        this.world = new World(new Vector2(0,-25f), false);
        this.world.setContactListener(new WorldContactListener(this.world, this));
        this.game = game;
        this.boxList = new ArrayList<>();
        this.bubbleList = new ArrayList<>();
        this.destroyList = new ArrayList<>();
        this.box2DDebugRenderer = new Box2DDebugRenderer();

//        box2DDebugRenderer.setDrawJoints(false);
//        box2DDebugRenderer.setDrawBodies(false);
//        box2DDebugRenderer.setDrawContacts(false);
        this.tileMapHelper = new TileMapHelper(this);
        this.renderer = tileMapHelper.setupMap();
        CuCaiButton = new Texture("cucaibtn.png");
        BachTuocButton = new Texture("bachtuocbtn.png");
        CucDaButton = new Texture("cucdabtn.png");

        menu = new Texture("menu.png");
        restart = new Texture("restart.png");

        this.hud = new Hud(player);
        Gdx.input.setInputProcessor(hud.stage);
    }
    @Override
    public void show() {
        staticCamera = new OrthographicCamera(360, 240);
        playerCamera = new OrthographicCamera(360, 240);
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
        if(position.x < 0) position.x = 0;
        if(position.x + CameraViewportWidth / 2 > tiledSize * 60) position.x = tiledSize * 60  - CameraViewportWidth / 2;
        if(position.y - CameraViewportHeight / 2 < 0) position.y = CameraViewportHeight / 2;
        if(position.y + CameraViewportHeight / 2 > tiledSize * 40) position.y = tiledSize * 40 - CameraViewportHeight / 2;
        playerCamera.position.set(position);
        staticCamera.position.set(position);
        hud.update();
        player.update(dt);
        door.update(dt);

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
        if(TRS.transitionInFlag){
            TRS.time = System.currentTimeMillis();
            TRS.transitionInFlag = false;
            TRS.transitionRunnning = true;
            TRS.transitionState = 1;
        }
        else if(TRS.transitionOutFlag){
            System.out.println("OUTT");
            TRS.time = System.currentTimeMillis();
            TRS.transitionOutFlag = false;
            TRS.transitionRunnning = true;
            TRS.transitionState = 2;
        }
        else if(TRS.transitionRunnning) {
            if (System.currentTimeMillis() < TRS.time + 1000) {
                if(TRS.transitionState == 1){
                    TRS.fadeInStage.act(delta);
                    TRS.fadeInStage.draw();
                }
                else if(TRS.transitionState == 2){
                    TRS.fadeOutStage.act(delta);
                    TRS.fadeOutStage.draw();
                }
            } else {
                if(TRS.transitionState == 2) endMap = true;
                System.out.println("End Screen");
                TRS.transitionRunnning = false;
            }
        }
        else if(!endMap){
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if(Gdx.input.isTouched()){
                System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
                System.out.println(Math.round(player.body.getPosition().x * PPM * 10 / 10f) + " " + Math.round(player.body.getPosition().y * PPM * 10 / 10f));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
                player.reset();
                game.gameScreen = new GameScreen(game, levelScreen);
                game.setScreen(game.gameScreen);
            }

            this.update(delta);

            renderer.setView(playerCamera);
            renderer.render();
            box2DDebugRenderer.render(world, playerCamera.combined.scl(PPM));
            box2DDebugRenderer.render(world, staticCamera.combined.scl(PPM));

            hud.stage.act(Gdx.graphics.getDeltaTime());
            hud.stage.draw();

            stateTime += delta;

            game.batch.setProjectionMatrix(staticCamera.combined);
            game.batch.begin();
            for(Bubble bubble : bubbleList) bubble.draw(game.batch);
            for(Box box : boxList) box.draw(game.batch);
            game.batch.end();

            game.batch.setProjectionMatrix(playerCamera.combined);
            game.batch.begin();
            player.draw(game.batch);
            door.draw(game.batch);
            game.batch.end();
        }
        else if(endMap){
            TRS.fadeInStage.dispose();
            TRS.fadeOutStage.dispose();
            if (Main.chooseLevel == Main.level) {
                ++Main.level;
            }
            game.batch = new SpriteBatch();
            this.dispose();
            game.setScreen(game.levelScreen);
            this.tileMapHelper = new TileMapHelper(this);
        }

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
    }

    @Override
    public void dispose() {
        TRS.transitionInFlag = false; TRS.transitionOutFlag = false; TRS.transitionRunnning = false;
        endMap = false;
        player.reset();
        world.dispose();
        renderer.dispose();
        box2DDebugRenderer.dispose();
    }
}