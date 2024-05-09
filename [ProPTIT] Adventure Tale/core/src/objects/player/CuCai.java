package objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CuCai extends Character {

    public CuCai(String IdleRightPath, String IdleLeftPath, String RunningRightPath, String RunningLeftPath, String JumpingLeftPath, String JumpingRightPath) {
        super(IdleRightPath, IdleLeftPath, RunningRightPath, RunningLeftPath, JumpingLeftPath, JumpingRightPath);
    }
}
