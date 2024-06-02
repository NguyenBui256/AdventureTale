package objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameScreen;

import static helper.Constants.PPM;
import static helper.Constants.tiledSize;

public class Bubble extends Sprite {
    public Body body;
    public TextureRegion[][] region;
    public Texture texture;

    public boolean isDoor = false;

    public Bubble(GameScreen screen, Body body, String imagePath, int width, int height){
        this.body = body;
        texture = new Texture(imagePath);
        region = TextureRegion.split(texture, width, height);
        Animation animation = new Animation(0.3f, region[0]);
        setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));
        for(Fixture fixture : body.getFixtureList()){
            if(fixture.getUserData() == "door"){
                isDoor = true;
                break;
            }
        }
        if(!isDoor) setBounds(body.getPosition().x,body.getPosition().y,1.4f*tiledSize/PPM, 1.4f*tiledSize/PPM);
        else setBounds(body.getPosition().x,body.getPosition().y,2*tiledSize/PPM, 2*tiledSize/PPM);
    }
    public void update(float dt){
        if(!isDoor) setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 5);
        else setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
