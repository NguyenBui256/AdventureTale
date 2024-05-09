package objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Character {
    public Texture IdleRight, IdleLeft, RunningLeft, RunningRight, JumpingLeft, JumpingRight;
    public Animation[] rolls = new Animation[10];

    public Character(String IdleRightPath, int w1, int h1,
                     String IdleLeftPath, int w2, int h2,
                     String RunningRightPath, int w3, int h3,
                     String RunningLeftPath, int w4, int h4,
                     String JumpingLeftPath, int w5, int h5,
                     String JumpingRightPath, int w6, int h6)
    {
        IdleRight = new Texture(IdleRightPath);
        IdleLeft = new Texture(IdleLeftPath);
        RunningRight = new Texture(RunningRightPath);
        RunningLeft = new Texture(RunningLeftPath);
        JumpingLeft = new Texture(JumpingLeftPath);
        JumpingRight = new Texture(JumpingRightPath);
        TextureRegion[][] idleRight = TextureRegion.split(IdleRight,w1, h1);
        TextureRegion[][] idleLeft = TextureRegion.split(IdleLeft, w2, h2);
        TextureRegion[][] runningRight = TextureRegion.split(RunningRight, w3, h3);
        TextureRegion[][] runningLeft = TextureRegion.split(RunningLeft, w4, h4);
        TextureRegion[][] jumpingLeft = TextureRegion.split(JumpingLeft, w5, h5);
        TextureRegion[][] jumpingRight = TextureRegion.split(JumpingRight, w6, h6);

        rolls[0] = new Animation(0.8f, idleRight[0]);
        rolls[1] = new Animation(0.8f, idleLeft[0]);
        rolls[2] = new Animation(0.2f, runningLeft[0]);
        rolls[3] = new Animation(0.2f, runningRight[0]);
        rolls[4] = new Animation(0.2f, jumpingLeft[0]);
        rolls[5] = new Animation(0.2f, jumpingRight[0]);
    }
}
