package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Main;
import objects.box.Box;
import com.mygdx.game.GameScreen;
import objects.box.Bubble;
import objects.box.Door;
import objects.player.Player;

import static helper.Constants.*;

public class TileMapHelper {
    public TiledMap map;
    public GameScreen gameScreen;


    public TileMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap(){
        map = new TmxMapLoader().load("map" + Main.chooseLevel + ".tmx");
        parseMapObjects(map.getLayers().get("objects").getObjects());
        return new OrthogonalTiledMapRenderer(map);
    }

    public void parseMapObjects(MapObjects mapObjects){
        for(MapObject mapObject : mapObjects){
            if(mapObject instanceof PolygonMapObject) createStaticBody((PolygonMapObject) mapObject);
            if(mapObject instanceof RectangleMapObject){
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if(rectangleName.equals("player")) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.world,
                            0,
                            NhanVat.MAIN
                    );
                    gameScreen.player = new Player(gameScreen, body);
                }
                else if(rectangleName.equals("box")){
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.world,
                            2,
                            VatThe.BOX
                    );
                    gameScreen.boxList.add(new Box(gameScreen, body));
                }
                else if(rectangleName.equals("BachTuoc")){
                    gameScreen.bubbleList.add(new Bubble(
                        gameScreen, createBubble(rectangle, NhanVat.BACHTUOC),
                        BachTuocBubblePath, 171, 171));
                }
                else if(rectangleName.equals("CucDa")){
                    gameScreen.bubbleList.add(new Bubble(
                        gameScreen, createBubble(rectangle, NhanVat.CUCDA),
                        CucDaBubblePath, 169, 169));
                }
                else if(rectangleName.equals("door")){
                    gameScreen.door = new Door(gameScreen, createBubble(rectangle, VatThe.DOOR), 80, 100);
                }
                else if(rectangleName.equals("bound")){
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type =  BodyDef.BodyType.KinematicBody;
                    bodyDef.position.set(
                            (rectangle.getX() + rectangle.getWidth() / 2) / PPM,
                            (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
                    bodyDef.fixedRotation = true;
                    Body body = gameScreen.world.createBody(bodyDef);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.isSensor = true;
                    body.createFixture(fixtureDef).setUserData(VatThe.MAPBOUND);
                    shape.dispose();
                }
            }
        }
    }

    public Body createBubble(Rectangle rectangle, Object data) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type =  BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (rectangle.getX() + rectangle.getWidth() / 2) / PPM,
                (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
        bodyDef.fixedRotation = true;
        Body body = gameScreen.world.createBody(bodyDef);

        MassData massData = new MassData();
        massData.mass = 0;
        body.setMassData(massData);
        body.setGravityScale(0.1f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
//        fixtureDef.isSensor = true;
        fixtureDef.restitution = 1f;
        body.createFixture(fixtureDef).setUserData(data);
        shape.dispose();
        return body;
    }

    public void createStaticBody(PolygonMapObject mapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.world.createBody(bodyDef);
        FixtureDef fdef = new FixtureDef();
        fdef.friction = 5;
        fdef.density = 1000;
        fdef.shape = createPolygonShape(mapObject);
        body.createFixture(fdef);
    }

    private Shape createPolygonShape(PolygonMapObject mapObject) {
        float[] vertices = mapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < vertices.length / 2; i++){
            Vector2 current= new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

}
