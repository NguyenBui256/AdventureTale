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
    public enum State {IDLELEFT, IDLERIGHT, RUNNINGLEFT, RUNNINGRIGHT, JUMPINGLEFT, JUMPINGRIGHT, ROUND1, ROUND2, ROUND3, ROUND4, ROUND5, ROUND6, ROUND7, ROUND8};
    public enum NhanVat {CUCAI, BACHTUOC, CUCDA};
    public NhanVat nhanVat, previousNhanVat;
    public Character NhanVatCuCai, NhanVatCucDa, NhanVatBachTuoc;
    public State currentState;
    public State previousState;
    public Main game;
    public World world;
    public Body body;
    public int roll;
    public float speed, velX, stateTimer;

    public Texture boxTexture, smokeTexture;
    public TextureRegion[][] boxRegion, smokeRegion;
    public Animation boxAnimation, smokeAnimation;
    public boolean isTransition = false;
    public static boolean isTop, isWallLeft, isWallRight, isBottom;
    public Body leftSensor = null, rightSensor = null, topSensor = null, bottomSensor = null, topLeftSensor = null, topRightSensor = null, bottomLeftSensor = null, bottomRightSensor = null;

    public SpriteBatch spriteBatch;

    public Player(GameScreen screen, Body body) {
        this.game = screen.game;
        this.world = screen.world;
        this.spriteBatch = new SpriteBatch();
        this.body = body;
        this.body.setUserData("cu cai");

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
                null,
                null
        );


        boxTexture = new Texture("box.png");
        boxRegion = TextureRegion.split(boxTexture, 225, 225);
        boxAnimation = new Animation(0.3f, boxRegion[0]);

        smokeTexture = new Texture("smokeAnimation.png");
        smokeRegion = TextureRegion.split(smokeTexture, 64,64);
        smokeAnimation = new Animation(0.05f, smokeRegion[0]);
        NhanVatBachTuoc = new Character(
                32, 32,
                null,
                null,
                null,
                null,
                null,
                null
        );

        boxTexture = new Texture("box.png");
        boxRegion = TextureRegion.split(boxTexture, 225, 225);
        boxAnimation = new Animation(0.3f, boxRegion[0]);

        smokeTexture = new Texture("smokeAnimation.png");
        smokeRegion = TextureRegion.split(smokeTexture, 64,64);
        smokeAnimation = new Animation(0.05f, smokeRegion[0]);

        stateTimer = 0;
        this.body = body;
        this.speed = 10f;
    }
    public void update(float dt) {

        checkUserInput();

        //Transition animation between characters changes
        if(isTransition) {
            stateTimer = 0;
            isTransition = false;
        }
        if(nhanVat != previousNhanVat && !smokeAnimation.isAnimationFinished(stateTimer)){
            setRegion((TextureRegion) smokeAnimation.getKeyFrame(stateTimer, false));
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 3,64/PPM,64/PPM);
            stateTimer += Gdx.graphics.getDeltaTime();
            return;
        }
        if(nhanVat != previousNhanVat && smokeAnimation.isAnimationFinished(stateTimer)){
            System.out.println("Done");
            System.out.println(nhanVat);
            previousNhanVat = nhanVat;
            stateTimer = 0;
        }
        if(nhanVat == NhanVat.CUCAI) {
            setRegion(getFrame(NhanVatCuCai,dt));
            setBounds(body.getPosition().x, body.getPosition().y,64/PPM,64/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
        }
        else if(nhanVat == NhanVat.BACHTUOC) {
            setRegion(getFrame(NhanVatBachTuoc,dt));
            setBounds(body.getPosition().x,body.getPosition().y,32/PPM, 32/PPM);
            //Sensors update
            leftSensor.setTransform(body.getPosition().x - getWidth() / 2, body.getPosition().y, 0);
            rightSensor.setTransform(body.getPosition().x + getWidth() / 2, body.getPosition().y, 0);
            topSensor.setTransform(body.getPosition().x, body.getPosition().y + getHeight() / 2, 0);
            bottomSensor.setTransform(body.getPosition().x, body.getPosition().y - getHeight() / 2, 0);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
        else if(nhanVat == NhanVat.CUCDA){
            setRegion(getFrame(NhanVatCucDa,dt));
            setBounds(body.getPosition().x, body.getPosition().y,46/PPM,46/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    public TextureRegion getFrame(Character character, float dt){
        TextureRegion region = null;
        if (nhanVat == NhanVat.CUCAI) {
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
        } else if (nhanVat == NhanVat.BACHTUOC) {
            region = (TextureRegion) character.rolls[currentState.name().charAt(5) - 49].getKeyFrame(stateTimer, true);
        } else {
            switch (currentState) {
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
            }
        }
        stateTimer = currentState == previousState? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public void checkUserInput(){
        if(nhanVat != NhanVat.CUCAI && Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            body.setGravityScale(1);
            isTop = false;
            isWallLeft = false;
            isWallRight = false;
            isBottom = true;
            currentState = State.IDLERIGHT;
            previousState = State.IDLERIGHT;
            isTransition = true;
            nhanVat = NhanVat.CUCAI;
        }
        if(nhanVat != NhanVat.BACHTUOC && Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            leftSensor = createSensor(0.5f,0.5f, "leftSensor");
            rightSensor = createSensor(0.5f,0.5f, "rightSensor");
            topSensor = createSensor(0.5f,0.5f, "topSensor");
            bottomSensor = createSensor(0.5f,0.5f, "bottomSensor");
            topLeftSensor = createEdgeSensor(0.5f, 0.5f, "topLeftSensor", body.getPosition().x - 16f / PPM, body.getPosition().y + 16f / PPM);
            topRightSensor = createEdgeSensor(0.5f, 0.5f, "topRightSensor", body.getPosition().x + 16f / PPM, body.getPosition().y + 16f / PPM);
            bottomLeftSensor = createEdgeSensor(0.5f, 0.5f, "bottomLeftSensor", body.getPosition().x - 16f / PPM, body.getPosition().y - 16f / PPM);
            bottomRightSensor = createEdgeSensor(0.5f, 0.5f, "bottomRightSensor", body.getPosition().x + 16f / PPM, body.getPosition().y - 16f / PPM);
            currentState = State.ROUND1;
            previousState = State.ROUND1;
            isWallLeft = false;
            isTop = false;
            isBottom = true;
            isWallRight = false;
            roll = 0;
            isTransition = true;
            nhanVat = NhanVat.BACHTUOC;
        }
        if(nhanVat != NhanVat.CUCDA && Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            isWallLeft = false;
            isTop = false;
            isBottom = true;
            isWallRight = false;
            body.setGravityScale(1);
            currentState = State.IDLERIGHT;
            previousState = State.IDLERIGHT;
            isTransition = true;
            nhanVat = NhanVat.CUCDA;
        }
        if (nhanVat == NhanVat.CUCAI) {
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
        } else if (nhanVat == NhanVat.BACHTUOC) {
            if (!isTop && (!isWallLeft || !isWallRight)) {
                body.setGravityScale(1);
            }
            if (isTop) {
                body.setGravityScale(-1);
                velX = 0;
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                    --roll;
                    if (roll == -1) {
                        roll = 7;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    velX = 1;
                } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                    ++roll;
                    if (roll == 8) {
                        roll = 0;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    velX = -1;
                }
                body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            } else if (isBottom) {
                velX = 0;
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                    ++roll;
                    if (roll == 8) {
                        roll = 0;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    velX = 1;
                } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                    --roll;
                    if (roll == -1) {
                        roll = 7;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    velX = -1;
                }
                body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            }
            if (isWallLeft) {
                body.setGravityScale(0);
                if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                    --roll;
                    if (roll == -1) {
                        roll = 7;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, speed);
                } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                    ++roll;
                    if (roll == 8) {
                        roll = 0;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, -speed);
                } else {
                    body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, 0);
                }
            }
            else if(isWallRight){
                body.setGravityScale(0);
                if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                    ++roll;
                    if (roll == 8) {
                        roll = 0;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, speed);
                } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                    --roll;
                    if (roll == -1) {
                        roll = 7;
                    }
                    currentState = State.valueOf("ROUND" + (roll + 1));
                    body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, -speed);
                } else {
                    body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, 0);
                }
            }
        } else {
            velX = 0;
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                currentState = State.RUNNINGRIGHT;
                velX = 1;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                currentState = State.RUNNINGLEFT;
                velX = -1;
            }
            if(this.velX == 0 && body.getLinearVelocity().y == 0){
                if(currentState == State.RUNNINGRIGHT) currentState = State.IDLERIGHT;
                else if(currentState == State.RUNNINGLEFT) currentState = State.IDLELEFT;
            }
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
        }
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
    public Body createEdgeSensor(float width, float height, String data, float x, float y){
        Body sensorBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        sensorBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(width / PPM, height / PPM, new Vector2(x, y), 0);
        fixtureDef.shape=boxShape;
        fixtureDef.isSensor = true;
        sensorBody.createFixture(fixtureDef).setUserData(data);
        boxShape.dispose();
        return sensorBody;
    }
}
