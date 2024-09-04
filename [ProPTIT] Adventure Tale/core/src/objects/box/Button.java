package objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameScreen;
import static helper.Constants.*;

public class Button extends Sprite {
    public World world;
    public Body body;
    public TextureRegion[][] regionIsClick, regionNotClick;
    public Texture textureIsClick, textureNotClick;
    public static boolean isClick;
    public static int pressCount = 0;
    public Animation animationIsClick, animationNotClick;
    public GameScreen screen;
    public Button(GameScreen screen, Body body) {
        this.world = screen.world;
        this.screen = screen;
        this.body = body;
        textureNotClick = new Texture(GAME_BTN_TEXTURE);
        textureIsClick = new Texture(GAME_BTN_PRESSED_TEXTURE);
        isClick = false;
        regionIsClick = TextureRegion.split(textureIsClick, 18, 18);
        regionNotClick = TextureRegion.split(textureNotClick, 18, 18);
        animationNotClick = new Animation(0.3f, regionNotClick[0]);
        animationIsClick = new Animation(0.3f, regionIsClick[0]);
        setRegion((TextureRegion) animationNotClick.getKeyFrame(screen.stateTime));
        setBounds(body.getPosition().x,body.getPosition().y+10,TILE_SIZE/PPM, TILE_SIZE/PPM);
        MassData massData = new MassData();
        massData.mass = 50;
        this.body.setMassData(massData);
    }
    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if (!isClick) setRegion((TextureRegion) animationIsClick.getKeyFrame(screen.stateTime));
        else setRegion((TextureRegion) animationNotClick.getKeyFrame(screen.stateTime));
    }
}