package objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameScreen;
import static helper.Constants.*;

public class Button extends Sprite {
    public World world;
    public Body body;
    public TextureRegion[][] region;
    public Texture texture, textureClick;
    public static boolean isClick;
    public static int pressCount = 0;
    public GameScreen screen;
    public Button(GameScreen screen, Body body) {
        this.world = screen.world;
        this.screen = screen;
        this.body = body;
        textureClick = new Texture(GAME_BTN_TEXTURE);
        texture = new Texture(GAME_BTN_PRESSED_TEXTURE);
        isClick = false;
        region = TextureRegion.split(texture, 18, 18);
        Animation animation = new Animation(0.3f, region[0]);
        setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));

        setBounds(body.getPosition().x,body.getPosition().y,TILE_SIZE/PPM, TILE_SIZE/PPM);
        MassData massData = new MassData();
        massData.mass = 50;
        this.body.setMassData(massData);
    }
    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        Texture buttonTexture;
        if (!isClick) {
            buttonTexture = texture;
        } else {
            buttonTexture = textureClick;
        }
        region = TextureRegion.split(buttonTexture, 18, 18);
        Animation animation = new Animation(0.3f, region[0]);
        setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));
    }
}