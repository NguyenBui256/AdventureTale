package objects.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;

import static helper.Constants.PPM;

public class Box extends Sprite {
    public World world;
    public Body body;
    public TextureRegion region;
    public float speed, velX, velY;
    public Box(GameScreen screen, Body body) {
        super(screen.atlas.findRegion("images"));
        this.world = screen.world;
        region = new TextureRegion(getTexture(), 0,0,225,225);
        setBounds(body.getPosition().x,body.getPosition().y,32/PPM, 32/PPM);
        setRegion(region);
        this.body = body;
        MassData massData = new MassData();
        massData.mass = 15;
        this.body.setMassData(massData);
        this.speed = 10f;
    }

    public void update(){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }


}
