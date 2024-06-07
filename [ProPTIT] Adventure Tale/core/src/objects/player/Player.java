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

import static helper.Constants.*;

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
    public float speed, velX, velY, stateTimer;
    public Texture boxTexture, smokeTexture;
    public TextureRegion[][] boxRegion, smokeRegion;
    public Animation boxAnimation, smokeAnimation;
    public boolean isTransition = false;
    public static boolean senTL = false, senTR = false, senBL = false, senBR = false, senT = false, senR = false, senB = false, senL = false;
    public static int senTLCount = 0, senTRCount = 0, senBLCount = 0, senBRCount = 0;
    public boolean BachTuocFlag = false, CucDaFlag = false, isJumping = false;
    public Body leftSensor = null, rightSensor = null, topSensor = null, bottomSensor = null, topLeftSensor = null, topRightSensor = null, bottomLeftSensor = null, bottomRightSensor = null;

    public Player(GameScreen screen, Body body) {
        this.game = screen.game;
        this.world = screen.world;
        this.body = body;

        setBounds(body.getPosition().x, body.getPosition().y,tiledSize/PPM,tiledSize/PPM);

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
        NhanVatBachTuoc = new Character(
                32, 32,
                null,
                null,
                null,
                null,
                null,
                null
        );

        leftSensor = createSensor(0,sizeSensorSize, "leftSensor");
        rightSensor = createSensor(0,sizeSensorSize, "rightSensor");
        topSensor = createSensor(sizeSensorSize,0, "topSensor");
        bottomSensor = createSensor(sizeSensorSize,0, "bottomSensor");
        topLeftSensor = createEdgeSensor(cornerSensorSize, cornerSensorSize, "topLeftSensor", 0, 0);
        topRightSensor = createEdgeSensor(cornerSensorSize, cornerSensorSize, "topRightSensor", 0, 0);
        bottomLeftSensor = createEdgeSensor(cornerSensorSize, cornerSensorSize, "bottomLeftSensor", 0, 0);
        bottomRightSensor = createEdgeSensor(cornerSensorSize, cornerSensorSize, "bottomRightSensor", 0, 0);

        boxTexture = new Texture("tile_0026.png");
        boxRegion = TextureRegion.split(boxTexture, 18, 18);
        boxAnimation = new Animation(0.3f, boxRegion[0]);

        smokeTexture = new Texture("smokeAnimation.png");
        smokeRegion = TextureRegion.split(smokeTexture, 64,64);
        smokeAnimation = new Animation(0.05f, smokeRegion[0]);

        stateTimer = 0;
        this.body = body;
        this.speed = 10f;
    }
    public void update(float dt) {

        //Sensors update
        leftSensor.setTransform(body.getPosition().x - getWidth() / 4, body.getPosition().y, 0);
        rightSensor.setTransform(body.getPosition().x + getWidth() / 4, body.getPosition().y, 0);
        topSensor.setTransform(body.getPosition().x, body.getPosition().y + getHeight() / 4, 0);
        bottomSensor.setTransform(body.getPosition().x, body.getPosition().y - getHeight() / 4, 0);
        topLeftSensor.setTransform(body.getPosition().x - getWidth() / 4, body.getPosition().y + getHeight() / 4, 0);
        topRightSensor.setTransform(body.getPosition().x + getWidth() / 4, body.getPosition().y + getHeight() / 4, 0);
        bottomLeftSensor.setTransform(body.getPosition().x - getWidth() / 4, body.getPosition().y - getHeight() / 4, 0);
        bottomRightSensor.setTransform(body.getPosition().x + getWidth() / 4, body.getPosition().y - getHeight() / 4, 0);

        //Input
        checkCharacterInput();
        checkMovingInput();

        //Transition animation between characters changes
        if(isTransition) {
            stateTimer = 0;
            isTransition = false;
        }
        if(nhanVat != previousNhanVat && !smokeAnimation.isAnimationFinished(stateTimer)){
            setRegion((TextureRegion) smokeAnimation.getKeyFrame(stateTimer, false));
            setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 3,2*tiledSize/PPM,2*tiledSize/PPM);
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
            MassData massData = new MassData();
            massData.mass = 0;
            body.setMassData(massData);
            setRegion(getFrame(NhanVatCuCai,dt));
            setBounds(body.getPosition().x, body.getPosition().y,2*tiledSize/PPM,2*tiledSize/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
        }
        else if(nhanVat == NhanVat.BACHTUOC) {
            MassData massData = new MassData();
            massData.mass = 0;
            body.setMassData(massData);
            setRegion(getFrame(NhanVatBachTuoc,dt));
            setBounds(body.getPosition().x,body.getPosition().y,2*tiledSize/PPM, 2*tiledSize/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 3);
        }
        else if(nhanVat == NhanVat.CUCDA){
            MassData massData = new MassData();
            massData.mass = 2;
            body.setMassData(massData);
            setRegion(getFrame(NhanVatCucDa,dt));
            setBounds(body.getPosition().x, body.getPosition().y,(2*tiledSize - 6)/PPM,(2*tiledSize - 6)/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 3);
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
        }
        else if (nhanVat == NhanVat.BACHTUOC) {
            region = (TextureRegion) character.rolls[currentState.name().charAt(5) - 49].getKeyFrame(stateTimer, true);
        }
        else if (nhanVat == NhanVat.CUCDA){
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

    public void checkCharacterInput(){
        if(nhanVat != NhanVat.CUCAI && Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            body.setGravityScale(1);
            currentState = State.IDLERIGHT;
            previousState = State.IDLERIGHT;
            changeCharacterStateTo(NhanVat.CUCAI);
        }
        if(BachTuocFlag && nhanVat != NhanVat.BACHTUOC && Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            currentState = State.ROUND1;
            previousState = State.ROUND1;
            roll = 0;
            changeCharacterStateTo(NhanVat.BACHTUOC);
        }
        if(CucDaFlag && nhanVat != NhanVat.CUCDA && Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            body.setGravityScale(1);
            currentState = State.IDLERIGHT;
            previousState = State.IDLERIGHT;
            changeCharacterStateTo(NhanVat.CUCDA);
        }
    }

    public void checkMovingInput(){

        if(Gdx.input.isTouched()){
            System.out.println("TL T TR: " + senTL + senT + senTR +  "| BL B BR: " + senBL + senB + senBR + "| L R: " + senL + senR);
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
            if(!isJumping && Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0){
                float force = (body.getMass() + 0.05f) * 10f;
                body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
                isJumping = true;
            }
            if(body.getLinearVelocity().y!=0) {
                if(currentState == State.RUNNINGLEFT || currentState == State.IDLELEFT) currentState = State.JUMPINGLEFT;
                else if(currentState == State.RUNNINGRIGHT || currentState == State.IDLERIGHT) currentState = State.JUMPINGRIGHT;
            }
            else isJumping = false;

            if(this.velX == 0 && body.getLinearVelocity().y == 0){
                if(currentState == State.RUNNINGRIGHT || currentState == State.JUMPINGRIGHT) currentState = State.IDLERIGHT;
                else if(currentState == State.RUNNINGLEFT || currentState == State.JUMPINGLEFT) currentState = State.IDLELEFT;
            }
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
        }
        else if (nhanVat == NhanVat.BACHTUOC) {
            boolean hasKeyPressed = false;
            velX = 0;
            velY = 0;
            boolean allowLeft = false, allowRight = false, allowUp = false, allowDown = false;
            //4 corners
            if(senBRCount == 2 && !senB && !senBL && !senR && !senTR) //top left
            {
                System.out.println("top left");
                body.setLinearVelocity(1,-2);
                allowLeft = false; allowUp = false;
                allowDown = true; allowRight = true;
            }
            if(senBLCount == 2 && !senB && !senBR && !senL && !senTL) //top right
            {
                body.setLinearVelocity(-1,-2);
                System.out.println("top right");
                allowRight = false; allowUp = false;
                allowDown = true; allowLeft = true;
            }
            if(senTRCount == 2 && !senT && !senTL && !senR && !senBR) //bot left
            {
                System.out.println("bot left");
                body.setLinearVelocity(1,2);
                allowLeft = false; allowDown = false;
                allowRight = true; allowUp = true;
            }
            if(senTLCount == 2 && !senT && !senTR && !senL && !senBL) //bot right
            {
                System.out.println("bot right");
                body.setLinearVelocity(-1,2);
                allowRight = false; allowDown = false;
                allowLeft = true; allowUp = true;
            }

            if((senBL && senB && senBR) || (senBL && senB) || (senB && senBR) || (senTL && senT && senTR) || (senTL && senT) || (senT && senTR)){
                if((senBL && senB && senBR) || (senBL && senB) || (senB && senBR)) body.setGravityScale(1); //ground
                if((senTL && senT && senTR) || (senTL && senT) || (senT && senTR)) body.setGravityScale(-1); //ceilling
                allowLeft = true; allowRight = true;
                if((senTL && senL && senBL) || (senTR && senR && senBR)) {
                    allowUp = true;
                    allowDown = true;
                    if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                        allowLeft = allowRight = false;
                    }
                    if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                        allowUp = allowDown = false;
                    }
                }
                else{
                    allowUp = false;
                    allowDown = false;
                }
            }
            else if((senTL && senL && senBL) || (senTL && senL) || (senBL && senL) || (senTR && senR && senBR) || (senTR && senR) || (senR && senBR)){
//                System.out.println("side");
                body.setGravityScale(0);
                allowUp = true; allowDown = true;
                if((senBL && senB && senBR) || (senTL && senT && senTR)){
                    allowLeft = true; allowRight = true;
                    if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                        allowLeft = allowRight = false;
                    }
                    if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                        allowUp = allowDown = false;
                    }
                }else{
                    allowLeft = false; allowRight = false;
                }
            }
            if(allowRight && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                hasKeyPressed = true;
                --roll;
                if (roll == -1) {
                    roll = 7;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velX = 1;
                body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            }
            else if(allowLeft && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                hasKeyPressed = true;
                ++roll;
                if (roll == 8) {
                    roll = 0;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velX = -1;
                body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            }
            else if(allowUp && Gdx.input.isKeyPressed(Input.Keys.UP)){
                hasKeyPressed = true;
                --roll;
                if (roll == -1) {
                    roll = 7;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velY = 1;
                body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, speed);
            }
            else if(allowDown && Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                hasKeyPressed = true;
                ++roll;
                if (roll == 8) {
                    roll = 0;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velY = -1;
                body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, -speed);
            }
            if(!hasKeyPressed){
                if(body.getLinearVelocity().y == 0)  body.setLinearVelocity(0,0);
                if(body.getLinearVelocity().x == 0 && (senL || senR)) body.setLinearVelocity(0,0);
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

    public void changeCharacterStateTo(NhanVat state){
        isTransition = true;
        nhanVat = state;
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

    public void reset(){
        senTL = false;senTR = false; senBL = false; senBR = false; senT = false; senR = false; senB = false; senL = false;
        BachTuocFlag = false; CucDaFlag = false; isJumping = false;
        senTLCount = 0; senTRCount = 0; senBLCount = 0; senBRCount = 0;
    }
}