package objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CuCai extends Character {
    public Texture IdleRight, IdleLeft, RunningLeft, RunningRight, JumpingLeft, JumpingRight;
    public Animation[] rolls = new Animation[10];

    public CuCai(String IdleRightPath, int w1, int h1, String IdleLeftPath, int w2, int h2, String RunningRightPath, int w3, int h3, String RunningLeftPath, int w4, int h4, String JumpingLeftPath, int w5, int h5, String JumpingRightPath, int w6, int h6) {
        super(IdleRightPath, w1, h1, IdleLeftPath, w2, h2, RunningRightPath, w3, h3, RunningLeftPath, w4, h4, JumpingLeftPath, w5, h5, JumpingRightPath, w6, h6);
    }
}
