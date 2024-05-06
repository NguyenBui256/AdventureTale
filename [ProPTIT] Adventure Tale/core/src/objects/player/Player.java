package objects.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameScreen;

import static helper.Constants.PPM;

public class Player extends Sprite {
    public World world;
    public Body body;
    public TextureRegion region;
    public GameScreen screen;
    public float speed, velX, velY;

    public Player(GameScreen screen, Body body) {
        super(screen.atlas.findRegion("images"));
        this.world = screen.world;
        region = new TextureRegion(getTexture(), 0,0,225,225);
        setBounds(body.getPosition().x,body.getPosition().y,32/PPM, 32/PPM);
        setRegion(region);
        this.body = body;
        this.speed = 10f;
    }
    public void update() {
//        x = body.getPosition().x * PPM;
//        y = body.getPosition().y * PPM;
        checkUserInput();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void checkUserInput(){
        velX = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) velX = 1;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) velX = -1;
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0){
            System.out.println(body.getMass());
            float force = body.getMass() * 10f;
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
        }

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
    }
}
