package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {
    Player player;

    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;

    public OrthographicCamera camera;

    public static float speed = 120;
    Main game;
    Box box;
    public GameScreen (Main game){
        this.game = game;
    }
    public void parseMapObjects (MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                float[] vertices = ((PolygonMapObject) mapObject).getPolygon().getTransformedVertices();
                System.out.println(vertices.length);
            }
        }
    }
    @Override
    public void show() {
        map = new TmxMapLoader().load("map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
//        parseMapObjects(map.getLayers().get("objects").getObjects());
        camera = new OrthographicCamera();
        box = new Box();
        player = new Player(box);
    }

    @Override
    public void resize(int i, int i1) {
        camera.viewportWidth = i;
        camera.viewportHeight = i1;
        camera.position.set(i / 2, i1 / 2, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        game.batch.begin();
        player.draw(game.batch, delta);
        box.draw(game.batch, delta);
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
        player.getTexture().dispose();
    }
}
