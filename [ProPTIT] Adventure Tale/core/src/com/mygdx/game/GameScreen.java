package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import helper.TileMapHelper;
import helper.WorldContactListener;
import jdk.internal.net.http.common.Pair;
import objects.box.*;
import objects.player.Player;

import java.io.IOException;
import java.util.ArrayList;

import static helper.Constants.*;

public class GameScreen implements Screen {
    public float stateTime;
    public NhanVat nhanVat;
    public boolean endMap = false, DestroyFlag = false, checkButton = false, isPass = false,  winn = false, soundOn = true, musicOn = true, checkDoor = false;
    protected Hud hud;
    public Main game;
    public LevelScreen levelScreen;
    public TransitionScreen TRS;
    public World world;
    public Player player;
    public Door door;
    public Button button;
    public ArrayList<Glass> glassList;
    public Texture CuCaiButton, BachTuocButton, CucDaButton, restart, pause;
    public ArrayList<Fire> fireList;
    public ArrayList<Box> boxList;
    public ArrayList<Bubble> bubbleList, destroyList;
    public TileMapHelper tileMapHelper;
    public OrthographicCamera staticCamera;
    public OrthographicCamera playerCamera;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer box2DDebugRenderer;
    public Music ingameBGMusic = Gdx.audio.newMusic(Gdx.files.internal(MenuBGMusicPath));

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
        this.fireList = new ArrayList<>();
        this.glassList = new ArrayList<>();
//        this.brokenGlassList = new ArrayList<>();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawJoints(false);
        box2DDebugRenderer.setDrawBodies(false);
        box2DDebugRenderer.setDrawContacts(false);
        this.tileMapHelper = new TileMapHelper(this);
        this.renderer = tileMapHelper.setupMap();

        this.hud = new Hud(player);
        Gdx.input.setInputProcessor(hud.stage);
        CuCaiButton = new Texture(CuCaiButtonPath);
        BachTuocButton = new Texture(BachTuocButtonPath);
        CucDaButton = new Texture(CucDaButtonPath);
        restart = new Texture(RestartButtonPath);
        pause = new Texture(PauseButtonPath);

        this.nhanVat = NhanVat.CUCAI;


        ingameBGMusic.setVolume(0.3f);
        ingameBGMusic.play();
        ingameBGMusic.setLooping(true);
    }
    @Override
    public void show() {
        staticCamera = new OrthographicCamera(CAMERA_VIEWPORT_WIDTH, CAMERA_VIEWPORT_HEIGHT);
        playerCamera = new OrthographicCamera(CAMERA_VIEWPORT_WIDTH, CAMERA_VIEWPORT_HEIGHT);
    }

    public void update(float dt){
        if(DestroyFlag){
            for(Bubble bubble : destroyList){
//                System.out.println("Destroyed!");
                world.destroyBody(bubble.body);
                bubbleList.remove(bubble);
            }
            destroyList.clear();
            DestroyFlag = false;
        }
//        System.out.println(destroyList.size());

        for (int i = 0; i < glassList.size(); ++i) {
            if (glassList.get(i).isBroken && !glassList.get(i).isVisited) {
                glassList.get(i).body.setType(BodyDef.BodyType.KinematicBody);
                for (Fixture fixture : glassList.get(i).body.getFixtureList()) {
                    fixture.setSensor(true);
                }
//                world.destroyBody(glassList.get(i).body);
//                Pair<Rectangle, Pair<Integer, Integer>> brokenGlass = brokenGlassList.get(i);
//                glassList.set(i, new Glass(this, tileMapHelper.createBubble(brokenGlass.first, "glass" + i, true, true), brokenGlass.second.first, brokenGlass.second.second));
                glassList.get(i).isVisited = true;
            }
        }
        world.step(1/60f, 6, 2);
        if (bubbleList.isEmpty() && (!checkButton || Button.isClick)) {
            isPass = true;
        } else {
            isPass = false;
        }

        Vector3 position = playerCamera.position;
        position.x = Math.round(player.body.getPosition().x * PPM * 10 / 10f);
        position.y = Math.round(player.body.getPosition().y * PPM * 10 / 10f);
        if(position.x - CAMERA_VIEWPORT_WIDTH / 2 < 0) position.x = CAMERA_VIEWPORT_WIDTH / 2;
        if(position.x + CAMERA_VIEWPORT_WIDTH / 2 > TILE_SIZE * 60) position.x = TILE_SIZE * 60  - CAMERA_VIEWPORT_WIDTH / 2;
        if(position.y - CAMERA_VIEWPORT_HEIGHT / 2 < 0) position.y = CAMERA_VIEWPORT_HEIGHT / 2;
        if(position.y + CAMERA_VIEWPORT_HEIGHT / 2 > TILE_SIZE * 40) position.y = TILE_SIZE * 40 - CAMERA_VIEWPORT_HEIGHT / 2;
        playerCamera.position.set(position);
        staticCamera.position.set(position);
        hud.update();
        player.update(dt);
        nhanVat = player.nhanVat;
        door.update(dt);

        for (Fire fire : fireList) {
            fire.update(dt);
        }
        for (Glass glass : glassList) {
            glass.update(dt);
        }
        if (checkButton) {
            button.update(dt);
        }
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
            winn = true;
            TRS.time = System.currentTimeMillis();
            TRS.transitionOutFlag = false;
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
                if(TRS.transitionState == 2){
                    ingameBGMusic.stop();
//                    game.menuScreen.bgMusic.play();
                    TRS.fadeInStage.dispose();
                    game.batch = new SpriteBatch();
                    this.dispose();
                    game.gameScreen = new GameScreen(game, game.levelScreen);
                    game.setScreen(game.gameScreen);
                    this.tileMapHelper = new TileMapHelper(this);
                }
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
            if(Gdx.input.isKeyJustPressed(Input.Keys.P)|| hud.restart){
                player.reset();
                ingameBGMusic.stop();
                game.gameScreen = new GameScreen(game, levelScreen);
                game.setScreen(game.gameScreen);
                this.tileMapHelper = new TileMapHelper(this);
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
            for (Fire fire : fireList) {
                fire.draw(game.batch);
            }
            for (Glass glass : glassList) {
                glass.draw(game.batch);
            }
            if (checkButton) {
                button.draw(game.batch);
            }
            game.batch.end();

            hud.stage.act(Gdx.graphics.getDeltaTime());
            hud.stage.draw();
            if(!hud.sound && soundOn){
                player.soundOn = false;
                soundOn = false;
            }
            else if(hud.sound && !soundOn){
                player.soundOn = true;
                soundOn = true;
            }
            if(!hud.music && musicOn){
                ingameBGMusic.stop();
                musicOn = false;
            }
            else if(hud.music && !musicOn){
                ingameBGMusic.play();
                musicOn = true;
            }
            if(hud.level){
                ingameBGMusic.stop();
                game.menuScreen.bgMusic.play();
                TRS.fadeInStage.dispose();
                game.batch = new SpriteBatch();
                this.dispose();
                game.setScreen(game.levelScreen);
                this.tileMapHelper = new TileMapHelper(this);
            }
            if(winn) {
                if (Main.chooseLevel == Main.level) {
                    ++Main.level;
                    try {
                        System.out.println("Save file");
                        System.out.println(Main.level);
                        Main.fw.write(Main.level);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                //hud.nextlevel = true;
                hud.win();
                if (hud.goToNextLevel) {
                    ++Main.chooseLevel;
                    TRS.transitionState = 2;
                    TRS.transitionRunnning = true;
                    endMap = true;
                }
            }
        }
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
        ingameBGMusic.stop();
        endMap = false;
        player.reset();
        world.dispose();
        renderer.dispose();
        box2DDebugRenderer.dispose();
    }
}