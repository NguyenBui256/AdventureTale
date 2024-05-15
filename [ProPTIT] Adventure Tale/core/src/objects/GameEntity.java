package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameEntity extends Sprite {
    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;

    public GameEntity(float width, float height, Body body, TextureRegion region) {
        super(region);
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public Body getBody(){
        return body;
    }

    public void setBody(Body body){
        this.body = body;
    }
}
