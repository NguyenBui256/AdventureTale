package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.GameEntity;

import static helper.Constants.PPM;

public class Player extends GameEntity {
    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        checkUserInput();
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

    @Override
    public void render(SpriteBatch batch) {

    }
}
