package objects.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import org.w3c.dom.Text;

import java.awt.geom.RectangularShape;

import static helper.Constants.PPM;

public class Player extends Sprite {
    public enum State {IDLELEFT, IDLERIGHT, RUNNINGLEFT, RUNNINGRIGHT, JUMPINGLEFT, JUMPINGRIGHT};
    public enum NhanVat {CUCAI, BACHTUOC, CUCDA};
    public boolean BachTuocFlag = false, CucDaFlag = false;
    public NhanVat nhanVat, previousNhanVat;
    public Character NhanVatCuCai, NhanVatCucDa;
    public BachTuoc NhanVatBachTuoc;
    public State currentState;
    public State previousState;
    public Main game;
    public World world;
    public Body body;
    public float speed, velX, stateTimer;

    public Texture boxTexture, smokeTexture;
    public TextureRegion[][] boxRegion, smokeRegion;
    public Animation boxAnimation, smokeAnimation;
    public boolean isTransition = false;

    public Body leftSensor, rightSensor, topSensor, bottomSensor;

    public SpriteBatch spriteBatch;

    public Player(GameScreen screen, Body body) {
        this.game = screen.game;
        this.world = screen.world;
        this.spriteBatch = new SpriteBatch();
        this.body = body;

        leftSensor = createSensor(0.5f,10f, "leftSensor");
        rightSensor = createSensor(0.5f,10f, "rightSensor");
        topSensor = createSensor(10f,0.5f, "topSensor");
        bottomSensor = createSensor(10f,0.5f, "bottomSensor");


        setBounds(body.getPosition().x, body.getPosition().y,32/PPM,32/PPM);

        currentState = State.IDLERIGHT;
        previousState = State.IDLERIGHT;

        nhanVat = NhanVat.CUCAI;
        previousNhanVat = NhanVat.CUCAI;
        NhanVatCuCai = new Character(
                32,32,
                "IdleRight.png",
                "IdleLeft.png",
                "RunningRight.png",
                "RunningLeft.png",
                "JumpingLeft.png",
                "JumpingRight.png"
        );

        NhanVatCucDa = new Character(
                38,34,
            "RockIdleRight.png",
            "RockIdleLeft.png",
            "RockRunRight.png",
            "RockRunLeft.png",
            "RockRunLeft.png",
            "RockRunRight.png"
        );


        boxTexture = new Texture("box.png");
        boxRegion = TextureRegion.split(boxTexture, 225, 225);
        boxAnimation = new Animation(0.3f, boxRegion[0]);

        smokeTexture = new Texture("smokeAnimation.png");
        smokeRegion = TextureRegion.split(smokeTexture, 64,64);
        smokeAnimation = new Animation(0.05f, smokeRegion[0]);
//        NhanVatBachTuoc = new BachTuoc();

        stateTimer = 0;
        this.body = body;
        this.speed = 10f;
    }
    public void update(float dt) {

        //Sensors update
        leftSensor.setTransform(body.getPosition().x - getWidth() / 2, body.getPosition().y, 0);
        rightSensor.setTransform(body.getPosition().x + getWidth() / 2, body.getPosition().y, 0);
        topSensor.setTransform(body.getPosition().x, body.getPosition().y + getHeight() / 2, 0);
        bottomSensor.setTransform(body.getPosition().x, body.getPosition().y - getHeight() / 2, 0);

        //Change character input
        characterInput();

        //Movement input
        checkUserInput();

        //Transition animation between characters changes
        if(isTransition) {
            stateTimer = 0;
            isTransition = false;
        }
        if(nhanVat != previousNhanVat && !smokeAnimation.isAnimationFinished(stateTimer)){
            setRegion((TextureRegion) smokeAnimation.getKeyFrame(stateTimer, false));
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,32/PPM,32/PPM);
            stateTimer += Gdx.graphics.getDeltaTime();
            return;
        }
        if(nhanVat != previousNhanVat && smokeAnimation.isAnimationFinished(stateTimer)){
            System.out.println("Done");
            System.out.println(nhanVat);
            previousNhanVat = nhanVat;
            stateTimer = 0;
        }

        //Character frame
        if(nhanVat == NhanVat.CUCAI) {
            setBounds(body.getPosition().x, body.getPosition().y,32/PPM,32/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
            setRegion(getFrame(NhanVatCuCai,dt));
        }
        else if(nhanVat == NhanVat.BACHTUOC) {
            setRegion((TextureRegion) boxAnimation.getKeyFrame(stateTimer, true));
            setBounds(body.getPosition().x,body.getPosition().y,32/PPM, 32/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
        else if(nhanVat == NhanVat.CUCDA){
            setBounds(body.getPosition().x, body.getPosition().y,32/PPM,32/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(NhanVatCucDa,dt));
        }
    }

    public void characterInput() {
        if(nhanVat != NhanVat.CUCAI && Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            isTransition = true;
            nhanVat = NhanVat.CUCAI;
        }
        if(BachTuocFlag && nhanVat != NhanVat.BACHTUOC && Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            isTransition = true;
            nhanVat = NhanVat.BACHTUOC;
        }
        if(CucDaFlag && nhanVat != NhanVat.CUCDA && Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            isTransition = true;
            nhanVat = NhanVat.CUCDA;
        }
    }

    public TextureRegion getFrame(Character character, float dt){
        TextureRegion region = null;
        switch (currentState){
            case IDLELEFT:
                region = (TextureRegion) character.rolls[1].getKeyFrame(stateTimer, true);
                break;
            case IDLERIGHT:
                region = (TextureRegion) character.rolls[0].getKeyFrame(stateTimer, true);
                break;
            case RUNNINGLEFT:
                region = (TextureRegion) character.rolls[2].getKeyFrame(stateTimer, true);
                break;
            case RUNNINGRIGHT:
                region = (TextureRegion) character.rolls[3].getKeyFrame(stateTimer, true);
                break;
            case JUMPINGLEFT:
                region = (TextureRegion) character.rolls[4].getKeyFrame(stateTimer, true);
                break;
            case JUMPINGRIGHT:
                region = (TextureRegion) character.rolls[5].getKeyFrame(stateTimer, true);
                break;
        }
        stateTimer = currentState == previousState? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public void checkUserInput(){
        velX = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            currentState = State.RUNNINGRIGHT;
            velX = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            currentState = State.RUNNINGLEFT;
            velX = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0){
            float force = body.getMass() * 10f;
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
        }
        if(body.getLinearVelocity().y!=0) {
            if(currentState == State.RUNNINGLEFT || currentState == State.IDLELEFT) currentState = State.JUMPINGLEFT;
            else if(currentState == State.RUNNINGRIGHT || currentState == State.IDLERIGHT) currentState = State.JUMPINGRIGHT;
        }
        if(this.velX == 0 && body.getLinearVelocity().y == 0){
            if(currentState == State.RUNNINGRIGHT || currentState == State.JUMPINGRIGHT) currentState = State.IDLERIGHT;
            else if(currentState == State.RUNNINGLEFT || currentState == State.JUMPINGLEFT) currentState = State.IDLELEFT;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
    }

    public Body createSensor(float width, float height, String data){
        Body sensorBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        sensorBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(width / PPM, height / PPM);
        fixtureDef.shape=boxShape;
        fixtureDef.isSensor = true;
        sensorBody.createFixture(fixtureDef).setUserData(data);
        boxShape.dispose();
        return sensorBody;
    }
}
