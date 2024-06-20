package objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import static helper.Constants.BACHTUOC_TEXTURE_PATH;


public class Character {
    public Texture IdleRight, IdleLeft, RunningLeft, RunningRight, JumpingLeft, JumpingRight;
    public ArrayList<Texture> list = new ArrayList<>();
    public Animation[] rolls = new Animation[10];

    public Character(
            int tileWidth, int tileHeight,
            String IdleLeftPath,
            String IdleRightPath,
            String RunningLeftPath,
            String RunningRightPath,
            String JumpingLeftPath,
            String JumpingRightPath)
    {
        if (JumpingLeftPath != null) {
            IdleRight = new Texture(IdleRightPath);
            IdleLeft = new Texture(IdleLeftPath);
            RunningRight = new Texture(RunningRightPath);
            RunningLeft = new Texture(RunningLeftPath);
            JumpingLeft = new Texture(JumpingLeftPath);
            JumpingRight = new Texture(JumpingRightPath);
            TextureRegion[][] idleRight = TextureRegion.split(IdleRight,tileWidth,tileHeight);
            TextureRegion[][] idleLeft = TextureRegion.split(IdleLeft, tileWidth,tileHeight);
            TextureRegion[][] runningRight = TextureRegion.split(RunningRight, tileWidth,tileHeight);
            TextureRegion[][] runningLeft = TextureRegion.split(RunningLeft, tileWidth,tileHeight);
            TextureRegion[][] jumpingLeft = TextureRegion.split(JumpingLeft, tileWidth,tileHeight);
            TextureRegion[][] jumpingRight = TextureRegion.split(JumpingRight, tileWidth,tileHeight);

            rolls[0] = new Animation(0.5f, idleRight[0]);
            rolls[1] = new Animation(0.5f, idleLeft[0]);
            rolls[2] = new Animation(0.2f, runningLeft[0]);
            rolls[3] = new Animation(0.2f, runningRight[0]);
            rolls[4] = new Animation(0.2f, jumpingLeft[0]);
            rolls[5] = new Animation(0.2f, jumpingRight[0]);
        } else if (IdleLeftPath != null) {
            IdleRight = new Texture(IdleRightPath);
            IdleLeft = new Texture(IdleLeftPath);
            RunningRight = new Texture(RunningRightPath);
            RunningLeft = new Texture(RunningLeftPath);
            TextureRegion[][] idleRight = TextureRegion.split(IdleRight,tileWidth,tileHeight);
            TextureRegion[][] idleLeft = TextureRegion.split(IdleLeft, tileWidth,tileHeight);
            TextureRegion[][] runningRight = TextureRegion.split(RunningRight, tileWidth,tileHeight);
            TextureRegion[][] runningLeft = TextureRegion.split(RunningLeft, tileWidth,tileHeight);

            rolls[0] = new Animation(0.08f, idleRight[0]);
            rolls[1] = new Animation(0.08f, idleLeft[0]);
            rolls[2] = new Animation(0.05f, runningLeft[0]);
            rolls[3] = new Animation(0.05f, runningRight[0]);
        }
        else {
            for (int i = 0; i < 8; ++i) {
                String path = BACHTUOC_TEXTURE_PATH + (i + 1) + ".png";
                list.add(new Texture(path));
            }
            for (int i = 0; i < 8; ++i) {
                TextureRegion[][] textureRegions = TextureRegion.split(list.get(i), 40, 40);
                rolls[i] = new Animation(1.5f, textureRegions[0]);
            }
        }
    }
}