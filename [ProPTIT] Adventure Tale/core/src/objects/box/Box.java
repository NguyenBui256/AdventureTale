package objects.box;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Null;
import objects.GameEntity;

import static helper.Constants.PPM;

public class Box extends GameEntity {
    private Vector2 coordinates = new Vector2();
    int roll;
    float stateTime;
    Animation[] rolls;
    Texture img;

    Body body;
    public Vector2 getCoordinates() {
        return coordinates;
    }

    public Box(float width, float height, Body body) {
        super(width, height, body);
        this.body = body;
        MassData massData = new MassData();
        massData.mass = 15;
        body.setMassData(massData);
//        coordinates.x = 100;
//        coordinates.y = 100;
//        img = new Texture("green.png");
//        img = new Texture("Locked@0.5x.png");
//        roll = 0;
//        rolls = new Animation[1];
//        TextureRegion[][] rollSpriteSheet = TextureRegion.split(img, 32, 32);
//        rolls[roll] = new Animation(0.2f, rollSpriteSheet[0]);
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}