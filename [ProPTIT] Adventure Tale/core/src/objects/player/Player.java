package objects.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    public NhanVat nhanVat, previousNhanVat;
    public Character NhanVatCuCai, NhanVatCucDa, NhanVatBachTuoc;
    public State currentState, previousState;
    public Main game;
    public World world;
    public Body body;
    public int roll;
    public float speed, velX, velY, stateTimer;
    public Texture smokeTexture;
    public TextureRegion[][] smokeRegion;
    public Animation smokeAnimation;
    public boolean isTransition = false, soundOn = true;
    public static boolean senTL = false, senTR = false, senBL = false, senBR = false,
            senT = false, senR = false, senB = false, senL = false;
    public static int senTLCount = 0, senTRCount = 0, senBLCount = 0, senBRCount = 0,
            senTCount = 0, senBCount = 0, senLCount = 0, senRCount = 0;
    public boolean BachTuocFlag = false, CucDaFlag = false, isJumping = false;
    public Body leftSensor = null, rightSensor = null, topSensor = null, bottomSensor = null, topLeftSensor = null,
            topRightSensor = null, bottomLeftSensor = null, bottomRightSensor = null;

    final boolean[] soundCuCaiPlaying = { false }, soundBachTuocPlaying = { false }, soundCucDaPlaying = { false };
    public Music walkingSound = Gdx.audio.newMusic(Gdx.files.internal(WALKING_SOUND)),
                octopusSound = Gdx.audio.newMusic(Gdx.files.internal(OCTOPUS_SOUND)),
                bonusSound = Gdx.audio.newMusic(Gdx.files.internal(CLICK_SOUND)),
                endlevelMusic = Gdx.audio.newMusic(Gdx.files.internal(END_LEVEL_MUSIC)),
                transformSound = Gdx.audio.newMusic(Gdx.files.internal(TRANSFORM_SOUND)),
                rockSound = Gdx.audio.newMusic(Gdx.files.internal(ROCK_SOUND)),
                glassSound = Gdx.audio.newMusic(Gdx.files.internal(GLASS_SOUND));

    public MassData cuCaiAndBachTuocMassData, cucDaMassData;
    public Player(GameScreen screen, Body body) {
        this.game = screen.game;
        this.world = screen.world;
        this.body = body;
        stateTimer = 0;

        cuCaiAndBachTuocMassData = new MassData();
        cucDaMassData = new MassData();
        cuCaiAndBachTuocMassData.mass = CUCAI_BACHTUOC_MASS;
        cucDaMassData.mass = CUCDA_MASS;

        setBounds(body.getPosition().x, body.getPosition().y,TILE_SIZE/PPM,TILE_SIZE/PPM);

        currentState = State.IDLERIGHT;
        previousState = State.IDLERIGHT;

        nhanVat = NhanVat.CUCAI;
        previousNhanVat = NhanVat.CUCAI;
        NhanVatCuCai = new Character(
                32,32,
                CUCAI_IDLE_LEFT,
                CUCAI_IDLE_RIGHT,
                CUCAI_RUNNING_LEFT,
                CUCAI_RUNNING_RIGHT,
                CUCAI_JUMPING_LEFT,
                CUCAI_JUMPING_RIGHT
        );

        NhanVatCucDa = new Character(
                38,34,
                CUCDA_IDLE_LEFT,
                CUCDA_IDLE_RIGHT,
                CUCDA_RUNNING_LEFT,
                CUCDA_RUNNING_RIGHT,
                null,
                null
        );
        NhanVatBachTuoc = new Character(
                36, 36,
                null,
                null,
                null,
                null,
                null,
                null
        );

        smokeTexture = new Texture(SMOKE_ANIMATION);
        smokeRegion = TextureRegion.split(smokeTexture, 64,64);
        smokeAnimation = new Animation(0.05f, smokeRegion[0]);

        leftSensor = createSensor(0,EDGE_SENSOR_SIZE, SensorDirection.LEFT);
        rightSensor = createSensor(0,EDGE_SENSOR_SIZE, SensorDirection.RIGHT);
        topSensor = createSensor(EDGE_SENSOR_SIZE,0, SensorDirection.TOP);
        bottomSensor = createSensor(EDGE_SENSOR_SIZE,0, SensorDirection.BOT);
        topLeftSensor = createEdgeSensor(CORNER_SENSOR_SIZE, CORNER_SENSOR_SIZE, SensorDirection.TOPLEFT, 0, 0);
        topRightSensor = createEdgeSensor(CORNER_SENSOR_SIZE, CORNER_SENSOR_SIZE, SensorDirection.TOPRIGHT, 0, 0);
        bottomLeftSensor = createEdgeSensor(CORNER_SENSOR_SIZE, CORNER_SENSOR_SIZE, SensorDirection.BOTLEFT, 0, 0);
        bottomRightSensor = createEdgeSensor(CORNER_SENSOR_SIZE, CORNER_SENSOR_SIZE, SensorDirection.BOTRIGHT, 0, 0);

        walkingSound.setOnCompletionListener(music -> {
            soundCuCaiPlaying[0] = false;
            walkingSound.stop();
        });

        octopusSound.setOnCompletionListener(music -> {
            soundBachTuocPlaying[0] = false;
            octopusSound.stop();
        });

        rockSound.setOnCompletionListener(music -> {
            soundCucDaPlaying[0] = false;
            rockSound.stop();
        });

        octopusSound.setVolume(1);
        rockSound.setVolume(0.7f);
        transformSound.setVolume(0.4f);
        walkingSound.setVolume(1);
        bonusSound.setVolume(0.4f);
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
        checkCharacterChangeInput();
        checkMovingInput();

        //Transition animation between characters changes
        if(isTransition) {
            stateTimer = 0;
            isTransition = false;
        }
        if(nhanVat != previousNhanVat && !smokeAnimation.isAnimationFinished(stateTimer)){
            setRegion((TextureRegion) smokeAnimation.getKeyFrame(stateTimer, false));
            setBounds(body.getPosition().x - getWidth() / 2,
                    body.getPosition().y - getHeight() / 3,
                    2*TILE_SIZE/PPM,
                    2*TILE_SIZE/PPM);
            stateTimer += Gdx.graphics.getDeltaTime();
            return;
        }
        if(nhanVat != previousNhanVat && smokeAnimation.isAnimationFinished(stateTimer)){
//            System.out.println("Done");
//            System.out.println(nhanVat);
            previousNhanVat = nhanVat;
            stateTimer = 0;
        }
        if(nhanVat == NhanVat.CUCAI) {
            body.setMassData(cuCaiAndBachTuocMassData);
            this.speed = CUCAI_BACHTUOC_SPEED;
            setRegion(getFrame(NhanVatCuCai,dt));
            setBounds(body.getPosition().x, body.getPosition().y,2*TILE_SIZE/PPM,2*TILE_SIZE/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4 - 0.05f);
        }
        else if(nhanVat == NhanVat.BACHTUOC) {
            body.setMassData(cuCaiAndBachTuocMassData);
            this.speed = CUCAI_BACHTUOC_SPEED;
            setRegion(getFrame(NhanVatBachTuoc,dt));
            setBounds(body.getPosition().x,body.getPosition().y,2*TILE_SIZE/PPM, 2*TILE_SIZE/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
        else if(nhanVat == NhanVat.CUCDA){
            body.setMassData(cucDaMassData);
            this.speed = CUCDA_SPEED;
            setRegion(getFrame(NhanVatCucDa,dt));
            setBounds(body.getPosition().x, body.getPosition().y,(2*TILE_SIZE - 6)/PPM,(2*TILE_SIZE - 6)/PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 3);
        }
    }

    public TextureRegion getFrame(Character character, float dt){
        TextureRegion region = null;
        if (nhanVat == NhanVat.CUCAI) {
            switch (currentState){
                case IDLELEFT:
                    region = (TextureRegion) character.rolls[0].getKeyFrame(stateTimer, true);
                    break;
                case IDLERIGHT:
                    region = (TextureRegion) character.rolls[1].getKeyFrame(stateTimer, true);
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
                    region = (TextureRegion) character.rolls[0].getKeyFrame(stateTimer, true);
                    break;
                case IDLERIGHT:
                    region = (TextureRegion) character.rolls[1].getKeyFrame(stateTimer, true);
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

    public void checkCharacterChangeInput(){
        if(nhanVat != NhanVat.CUCAI && Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            if(soundOn) transformSound.play();
            body.setGravityScale(1);
            currentState = State.IDLERIGHT;
            previousState = State.IDLERIGHT;
            changeCharacterStateTo(NhanVat.CUCAI);
        }
        if(BachTuocFlag && nhanVat != NhanVat.BACHTUOC && Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            if(soundOn) transformSound.play();
            currentState = State.ROUND1;
            previousState = State.ROUND1;
            roll = 0;
            changeCharacterStateTo(NhanVat.BACHTUOC);
        }
        if(CucDaFlag && nhanVat != NhanVat.CUCDA && Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            if(soundOn) transformSound.play();
            body.setGravityScale(1);
            currentState = State.IDLERIGHT;
            previousState = State.IDLERIGHT;
            changeCharacterStateTo(NhanVat.CUCDA);
        }
    }

    public void checkMovingInput(){

        if(Gdx.input.isTouched()){
            System.out.println("TL T TR: " + senTL + senT + senTR +  "| BL B BR: " +
                    senBL + senB + senBR + "| L R: " + senL + senR);
            System.out.println("TL T TR: " + senTLCount + senTCount + senTRCount +  "| BL B BR: " +
                    senBLCount + senBCount + senBRCount + "| L R: " + senLCount + senRCount);
        }
        if (nhanVat == NhanVat.CUCAI) {
            boolean hasKeyPressed = false;
            velX = 0;
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                if(!isJumping && soundOn) walkingSound.play();
                hasKeyPressed = true;
                currentState = State.RUNNINGRIGHT;
                velX = 1;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                if(!isJumping && soundOn) walkingSound.play();
                hasKeyPressed = true;
                currentState = State.RUNNINGLEFT;
                velX = -1;
            }
            if(!isJumping && Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0){
                isJumping = true;
                hasKeyPressed = true;
                float force = (body.getMass() + 0.05f) * 10f;
                body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
            }
            if(body.getLinearVelocity().y!=0) {
                isJumping = true;
                if(currentState == State.RUNNINGLEFT || currentState == State.IDLELEFT) currentState = State.JUMPINGLEFT;
                else if(currentState == State.RUNNINGRIGHT || currentState == State.IDLERIGHT) currentState = State.JUMPINGRIGHT;
            }
            else {
                if(isJumping){
                    soundCuCaiPlaying[0] = true;
                    isJumping = false;
                }
                if(soundCuCaiPlaying[0] && soundOn){
                    System.out.println("hereCC");
                    walkingSound.play();
                }
            }
            if(this.velX == 0 && body.getLinearVelocity().y == 0){
                System.out.println("Standby");
                soundCuCaiPlaying[0] = false;
                isJumping = false;
                if(currentState == State.RUNNINGRIGHT || currentState == State.JUMPINGRIGHT) currentState = State.IDLERIGHT;
                else if(currentState == State.RUNNINGLEFT || currentState == State.JUMPINGLEFT) currentState = State.IDLELEFT;
            }
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            if(!soundCuCaiPlaying[0] && !hasKeyPressed) {
                walkingSound.stop();
                if(currentState == State.RUNNINGRIGHT || currentState == State.JUMPINGRIGHT) currentState = State.IDLERIGHT;
                else if(currentState == State.RUNNINGLEFT || currentState == State.JUMPINGLEFT) currentState = State.IDLELEFT;
            }
        }
        else if (nhanVat == NhanVat.BACHTUOC) {
            boolean hasKeyPressed = false;
            velX = 0;
            velY = 0;
            boolean allowLeft = false, allowRight = false, allowUp = false, allowDown = false;
            //4 corners
            if(senBRCount >= 2 && !senB && !senBL && !senR && !senTR) //top left
            {
//                System.out.println("top left");
                body.setLinearVelocity(1,-2);
                allowLeft = false; allowUp = false;
                allowDown = true; allowRight = true;
            }
            else if(senBLCount >= 2 && !senB && !senBR && !senL && !senTL) //top right
            {
//                System.out.println("top right");
                body.setLinearVelocity(-1,-2);
                allowRight = false; allowUp = false;
                allowDown = true; allowLeft = true;
            }
            else if(senTRCount >= 2 && !senT && !senTL && !senR && !senBR) //bot left
            {
//                System.out.println("bot left");
                body.setLinearVelocity(1,2);
                allowLeft = false; allowDown = false;
                allowRight = true; allowUp = true;
            }
            else if(senTLCount >= 2 && !senT && !senTR && !senL && !senBL) //bot right
            {
//                System.out.println("bot right");
                body.setLinearVelocity(-1,2);
                allowRight = false; allowDown = false;
                allowLeft = true; allowUp = true;
            }

            if((senBL && senB && senBR) || (senBL && senB) || (senB && senBR)
            || (senTL && senT && senTR) || (senTL && senT) || (senT && senTR)){
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
            else if((senTL && senL && senBL) || (senTL && senL) || (senBL && senL)
            || (senTR && senR && senBR) || (senTR && senR) || (senR && senBR)){
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
                if(soundOn) octopusSound.play();
                hasKeyPressed = true;
                ++roll;
                if (roll == 8) {
                    roll = 0;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velX = 1;
                body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            }
            else if(allowLeft && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                if(soundOn) octopusSound.play();
                hasKeyPressed = true;
                --roll;
                if (roll == -1) {
                    roll = 7;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velX = -1;
                body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            }
            else if(allowUp && Gdx.input.isKeyPressed(Input.Keys.UP)){
                if(soundOn) octopusSound.play();
                hasKeyPressed = true;
                ++roll;
                if (roll == 8) {
                    roll = 0;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velY = 1;
                body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, speed);
            }
            else if(allowDown && Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                if(soundOn) octopusSound.play();
                hasKeyPressed = true;
                --roll;
                if (roll == -1) {
                    roll = 7;
                }
                currentState = State.valueOf("ROUND" + (roll + 1));
                velY = -1;
                body.setLinearVelocity(body.getLinearVelocity().x < 15 ? body.getLinearVelocity().x : 15, -speed);
            }
            if(!soundBachTuocPlaying[0] && !hasKeyPressed) {
                octopusSound.stop();
            }
            if(!hasKeyPressed){
                if(body.getLinearVelocity().y == 0)  body.setLinearVelocity(0,0);
                if(body.getLinearVelocity().x == 0 &&
                        ((senTL && senL && senBL) || (senTL && senL) || (senBL && senL)
                        || (senTR && senR && senBR) || (senTR && senR) || (senR && senBR))) {
                    body.setLinearVelocity(0, 0);
                }
            }
        }
        else {
            boolean hasKeyPressed = false;
            velX = 0;
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                if(soundOn) rockSound.play();
                currentState = State.RUNNINGRIGHT;
                velX = 1;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                if(soundOn) rockSound.play();
                currentState = State.RUNNINGLEFT;
                velX = -1;
            }
            if (body.getLinearVelocity().y != 0) {
                isJumping = true;
            }
            else {
                if(isJumping){
                    soundCucDaPlaying[0] = true;
                    isJumping = false;
                }
                if(soundCucDaPlaying[0] && soundOn){
                    rockSound.play();
                }
            }
            if(this.velX == 0 && body.getLinearVelocity().y == 0){
                soundCucDaPlaying[0] = false;
                if(currentState == State.RUNNINGRIGHT) currentState = State.IDLERIGHT;
                else if(currentState == State.RUNNINGLEFT) currentState = State.IDLELEFT;
            }
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
            if(!soundCucDaPlaying[0] && !hasKeyPressed) {
                walkingSound.stop();
            }
        }
    }

    public void changeCharacterStateTo(NhanVat state){
        isTransition = true;
        nhanVat = state;
    }

    public Body createSensor(float width, float height, SensorDirection data){
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
    public Body createEdgeSensor(float width, float height, SensorDirection data, float x, float y){
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
        senTL = false; senTR = false; senBL = false; senBR = false; senT = false; senR = false; senB = false; senL = false;
        BachTuocFlag = false; CucDaFlag = false; isJumping = false;
        senTLCount = 0; senTRCount = 0; senBLCount = 0; senBRCount = 0;
        senTCount = 0; senBCount = 0; senLCount = 0; senRCount = 0;
    }
}