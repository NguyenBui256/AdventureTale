package objects.box;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameScreen;
import com.badlogic.gdx.utils.Timer;

import static helper.Constants.*;

public class Door extends Sprite {
    public Body body;
    public Texture texture;
    public Animation[] rolls = new Animation[4];
    public float stateTime;
    public int roll;
    public GameScreen screen;
    public int width, height;
    public Door(GameScreen screen, Body body, int width, int height) {
        this.screen = screen;
        this.body = body;
        this.width = width;
        this.height = height;
        if (screen.isPass) {
            screen.checkDoor = true;
            for (int state = 0; state < 4; ++state) {
                texture = new Texture(DOOR_TEXTURE_PATH + (state + 1) + ".png");
                TextureRegion[][] region = TextureRegion.split(texture, width, height);
                rolls[state] = new Animation(1.5f, region[0]);
            }
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
        } else {
            texture = new Texture(DOOR_TEXTURE_PATH + ".png");
            TextureRegion[][] region = TextureRegion.split(texture, width, height);
            Animation animation = new Animation(0.3f, region[0]);
            setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));
        }
        setBounds(body.getPosition().x,body.getPosition().y,2*TILE_SIZE/PPM, 2*TILE_SIZE/PPM);
    }
    public void update(float dt) {
        if (screen.isPass && !screen.checkDoor) {
            screen.checkDoor = true;
            for (int state = 0; state < 4; ++state) {
                texture = new Texture(DOOR_TEXTURE_PATH + (state + 1) + ".png");
                TextureRegion[][] region = TextureRegion.split(texture, width, height);
                rolls[state] = new Animation(1.5f, region[0]);
            }
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
        } else if (!screen.isPass) {
            texture = new Texture(DOOR_TEXTURE_PATH + ".png");
            TextureRegion[][] region = TextureRegion.split(texture, width, height);
            Animation animation = new Animation(0.3f, region[0]);
            setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));
        }
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
