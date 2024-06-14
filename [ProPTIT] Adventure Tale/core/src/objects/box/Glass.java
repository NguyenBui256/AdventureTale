package objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameScreen;

import static helper.Constants.PPM;
import static helper.Constants.TILE_SIZE;

public class Glass extends Sprite {
    public World world;
    public Body body;
    public TextureRegion[][] region;
    public Texture glassUnbroken, glassBroken, glassUnbrokenSt, glassBrokenSt;
    public boolean isBroken, isVisited;
    public GameScreen screen;
    public int Width, Height;
    public Glass(GameScreen screen, Body body, int Width, int Height) {
        this.world = screen.world;
        this.screen = screen;
        this.body = body;
        this.Width = Width;
        this.Height = Height;
        isBroken = false;
        isVisited = false;

        if (Width > Height) {
            glassUnbroken = new Texture("glass-unbroken.png");
            glassBroken = new Texture("glass-broken.png");
            region = TextureRegion.split(glassUnbroken, Width, Height);
            setBounds(body.getPosition().x,body.getPosition().y,6 * TILE_SIZE/PPM, 2 * TILE_SIZE/PPM);
        } else {
            glassUnbrokenSt = new Texture("glass-unbroken-stand.png");
            glassBrokenSt = new Texture("glass-broken-stand.png");
            region = TextureRegion.split(glassUnbrokenSt, Width, Height);
            setBounds(body.getPosition().x,body.getPosition().y,2 * TILE_SIZE/PPM, 6 * TILE_SIZE/PPM);
        }
        Animation animation = new Animation(0.3f, region[0]);
        setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));
        MassData massData = new MassData();
        massData.mass = 50;
        this.body.setMassData(massData);
    }
    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        Texture glassTexture;
        if (!isBroken) {
            if (Width > Height) {
                glassTexture = glassUnbroken;
            } else {
                glassTexture = glassUnbrokenSt;
            }
        } else {
            if (Width > Height) {
                glassTexture = glassBroken;
            } else {
                glassTexture = glassBrokenSt;
            }
        }
        region = TextureRegion.split(glassTexture, Width, Height);
        Animation animation = new Animation(0.3f, region[0]);
        setRegion((TextureRegion) animation.getKeyFrame(screen.stateTime));
    }
}
