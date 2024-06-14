package objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameScreen;

import static helper.Constants.*;

public class Fire extends Sprite {
    public Body body;
    public Texture texture;
    public Animation[] rolls = new Animation[7];
    public float stateTime;
    public int roll, tmp;
    public GameScreen screen;
    public Fire(GameScreen screen, Body body, int width) {
        this.screen = screen;
        int height;
        this.body = body;
        for (int i = 0; i < 7; ++i) {
            texture = new Texture("fire" + (i + 1) + ".png");
            switch (i) {
                case 0:
                    height = 39;
                    break;
                case 1:
                    height = 47;
                    break;
                case 2:
                    height = 62;
                    break;
                case 3:
                    height = 78;
                    break;
                case 4:
                    height = 100;
                    break;
                case 5:
                    height = 115;
                    break;
                case 6:
                    height = 137;
                    break;
                default:
                    height = 0;
            }
            TextureRegion[][] region = TextureRegion.split(texture, width, height);
            rolls[i] = new Animation(0.3f, region[0]);
        }
        setBounds(body.getPosition().x,body.getPosition().y,8*TILE_SIZE/PPM, 2*TILE_SIZE/PPM);
        stateTime = 0;
        roll = -1;
        tmp = 1;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                roll += tmp;
                if (roll == 6) {
                    tmp = -1;
                } else if (roll == 0) {
                    tmp = 1;
                }
                TextureRegion region = (TextureRegion) rolls[roll].getKeyFrame(stateTime, true);
                setRegion(region);
            }
        }, 0, 1/8.0f);
    }
    public void update(float dt) {
        stateTime += dt;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}