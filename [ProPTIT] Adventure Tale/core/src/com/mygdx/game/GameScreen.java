package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

    public OrthogonalTiledMapRenderer renderer;

    public Player player;
    public OrthographicCamera camera;
    public World world;
    public Box2DDebugRenderer box2DDebugRenderer;
    public TileMapHelper tileMapHelper;
    public Box box;
    public TextureAtlas atlas;
    Main game;
    Texture img;
    int roll;
    float stateTime;
    Animation[] rolls;
    public GameScreen (Main game){
        atlas = new TextureAtlas("box.pack");
        this.world = new World(new Vector2(0,-25f), false);
//        this.box = new box(world, this);
        this.game = game;
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.renderer = tileMapHelper.setupMap();

        img = new Texture("Running (32 x 32).png");
        roll = 0;
        rolls = new Animation[1];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(img, 32, 32);
        rolls[roll] = new Animation(0.2f, rollSpriteSheet[0]);
    }
    @Override
    public void show() {
        camera = new OrthographicCamera(750, 500);
//        camera = new OrthographicCamera(960, 640);
    }

    public void update(){
        world.step(1/60f, 6, 2);

        Vector3 position = camera.position;
        position.x = Math.round(player.body.getPosition().x * PPM * 10 / 10f);
        position.y = Math.round(player.body.getPosition().y * PPM * 10 / 10f);
        camera.position.set(position);

        box.update();
        player.update();
//        TiledMapTileLayer layer0 = (TiledMapTileLayer) TileMapHelper.map.getLayers().get(0);
//        Vector3 center = new Vector3(layer0.getWidth() * layer0.getTileWidth() / 2, layer0.getHeight() * layer0.getTileHeight() / 2, 0);
//        camera.position.set(center);
        game.batch.setProjectionMatrix(camera.combined);

        camera.update();
        renderer.setView(camera);
        // #Player movement
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
//        box2DDebugRenderer.setDrawBodies(false);
//        box2DDebugRenderer.setDrawJoints(false);

        stateTime += delta;
        game.batch.begin();
        player.draw(game.batch);
//        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true),
//                895/2,
//                600/2,
//                64,
//                64
//        );
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
    }
}
