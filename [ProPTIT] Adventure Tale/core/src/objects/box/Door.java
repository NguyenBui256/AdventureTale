package objects.box;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;
import com.badlogic.gdx.utils.Timer;

import static helper.Constants.PPM;
import static helper.Constants.tiledSize;

public class Door extends Sprite {
    public Body body;
    public Texture texture;
    public Animation[] rolls = new Animation[4];
    public float stateTime;
    public int roll;
    public Door(GameScreen screen, Body body, int width, int height) {
        this.body = body;
        for (int i = 0; i < 4; ++i) {
            texture = new Texture("door" + (i + 1) + ".png");
            TextureRegion[][] region = TextureRegion.split(texture, width, height);
            rolls[i] = new Animation(1.5f, region[0]);
        }
        setBounds(body.getPosition().x,body.getPosition().y,2*tiledSize/PPM, 2*tiledSize/PPM);
        stateTime = 0;
        roll = -1;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ++roll;
                if (roll == 4) {
                    roll = 0;
                }
                TextureRegion region = (TextureRegion) rolls[roll].getKeyFrame(stateTime, true);
                setRegion(region);
            }
        }, 0, 1/5.0f);
    }
    public void update(float dt) {
        stateTime += dt;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
