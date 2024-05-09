package objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Character {
    public Texture IdleRight, IdleLeft, RunningLeft, RunningRight, JumpingLeft, JumpingRight;
    public Animation[] rolls = new Animation[10];

    public Character(String IdleRightPath,
                     String IdleLeftPath,
                     String RunningRightPath,
                     String RunningLeftPath,
                     String JumpingLeftPath,
                     String JumpingRightPath)
    {
        IdleRight = new Texture(IdleRightPath);
        IdleLeft = new Texture(IdleLeftPath);
        RunningRight = new Texture(RunningRightPath);
        RunningLeft = new Texture(RunningLeftPath);
        JumpingLeft = new Texture(JumpingLeftPath);
        JumpingRight = new Texture(JumpingRightPath);
        TextureRegion[][] idleRight = TextureRegion.split(IdleRight,32,32);
        TextureRegion[][] idleLeft = TextureRegion.split(IdleLeft, 32,32);
        TextureRegion[][] runningRight = TextureRegion.split(RunningRight, 32,32);
        TextureRegion[][] runningLeft = TextureRegion.split(RunningLeft, 32,32);
        TextureRegion[][] jumpingLeft = TextureRegion.split(JumpingLeft, 32,32);
        TextureRegion[][] jumpingRight = TextureRegion.split(JumpingRight, 32,32);

        rolls[0] = new Animation(0.8f, idleRight[0]);
        rolls[1] = new Animation(0.8f, idleLeft[0]);
        rolls[2] = new Animation(0.2f, runningLeft[0]);
        rolls[3] = new Animation(0.2f, runningRight[0]);
        rolls[4] = new Animation(0.2f, jumpingLeft[0]);
        rolls[5] = new Animation(0.2f, jumpingRight[0]);
    }
}
