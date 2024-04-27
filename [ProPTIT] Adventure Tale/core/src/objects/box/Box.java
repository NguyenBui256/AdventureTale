package objects.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;

import static helper.Constants.*;

public class Box extends Sprite {
    public World world;
    public Body body;
    public TextureRegion[][] region;
    public Texture texture;
    public float speed, velX, velY;
    public Box(GameScreen screen, Body body) {
        this.world = screen.world;
        texture = new Texture("tile_0026.png");
        region = TextureRegion.split(texture, 18, 18);
        Animation animation = new Animation(0.3f, region[0]);
        setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));

        setBounds(body.getPosition().x,body.getPosition().y,TILE_SIZE/PPM, TILE_SIZE/PPM);
        this.body = body;
        MassData massData = new MassData();
        massData.mass = 10;
        this.body.setMassData(massData);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }


}
