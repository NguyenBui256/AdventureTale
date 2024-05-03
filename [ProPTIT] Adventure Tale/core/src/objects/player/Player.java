package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.Constants.PPM;

public class Player extends GameEntity{
    public Player(float width, float height, Body body, String imgPath, int roll) {
        super(width, height, body, imgPath, roll);
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
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            roll = 3;
            velX = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            velX = -1;
            roll = 2;
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//            velY = 1;
//            if(roll == 2) roll = 4;
//            else if(roll == 3) roll = 5;
//        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0){
            System.out.println(body.getMass());
            float force = body.getMass() * 10f;
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
        }
        if(body.getLinearVelocity().y!=0) {
            if (roll == 2 || roll == 1) roll = 4;
            else if (roll == 3 || roll == 0) roll = 5;
        }
        if(this.velX == 0 && body.getLinearVelocity().y == 0){
            if(roll == 3 || roll == 5) roll = 0;
            else if(roll == 2 || roll == 4) roll = 1;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
    }
    public  int getRoll(){
        return roll;
    }
    @Override
    public void render(SpriteBatch batch) {

    }
}
