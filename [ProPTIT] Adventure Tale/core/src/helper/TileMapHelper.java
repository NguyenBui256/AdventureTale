package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import objects.box.Box;
import com.mygdx.game.GameScreen;
import objects.player.Player;

import static helper.Constants.PPM;

public class TileMapHelper {
    public static TiledMap map;
    public GameScreen gameScreen;


    public TileMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap(){
        map = new TmxMapLoader().load("map2.tmx");
        parseMapObjects(map.getLayers().get("ground").getObjects());
        return new OrthogonalTiledMapRenderer(map);
    }

    public void parseMapObjects(MapObjects mapObjects){
        for(MapObject mapObject : mapObjects){
            if(mapObject instanceof PolygonMapObject){
                createStaticBody((PolygonMapObject) mapObject);
            }
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
                            0
                    );
                    gameScreen.player = new Player(rectangle.getWidth(), rectangle.getWidth(), body);
                }
            }
            if(mapObject instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) mapObject;

                Body body = BodyHelperService.createBody(
                        tileMapObject.getX() + 16,
                        tileMapObject.getY() + 16,
                        32,
                        32,
                        false,
                        gameScreen.world,
                        5
                );
                gameScreen.box = new Box(32,32,body);
            }
        }
    }

    public void createStaticBody(PolygonMapObject mapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.world.createBody(bodyDef);
        Shape shape = createPolygonShape(mapObject);
        body.createFixture(shape,1000);
        shape.dispose();
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
