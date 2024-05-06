package objects.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameScreen;

import static helper.Constants.PPM;

public class Player extends Sprite {
    public enum State {IDLELEFT, IDLERIGHT, RUNNINGLEFT, RUNNINGRIGHT, JUMPINGLEFT, JUMPINGRIGHT};
    public State currentState;
    public State previousState;

    public Texture IdleRight, IdleLeft, RunningLeft, RunningRight, JumpingLeft, JumpingRight;
    public World world;
    public Body body;
    public TextureRegion region;
    public Animation[] rolls = new Animation[10];
    public GameScreen screen;
    public float speed, velX, velY, stateTimer;
    public int roll;

    public Player(GameScreen screen, Body body) {
//        super(screen.atlas.findRegion("images"));
        this.world = screen.world;
//        region = new TextureRegion(getTexture(), 0,0,225,225);
        setBounds(body.getPosition().x, body.getPosition().y,64/PPM,64/PPM);
//        setRegion(region);

        currentState = State.IDLERIGHT;
        previousState = State.IDLERIGHT;
        stateTimer = 0;
        this.body = body;
        this.speed = 10f;

        IdleRight = new Texture("IdleRight.png");
        IdleLeft = new Texture("IdleLeft.png");
        RunningRight = new Texture("RunningRight.png");
        RunningLeft = new Texture("RunningLeft.png");
        JumpingLeft = new Texture("JumpingLeft.png");
        JumpingRight = new Texture("JumpingRight.png");
        TextureRegion[][] idleRight = TextureRegion.split(IdleRight,32, 32);
        TextureRegion[][] idleLeft = TextureRegion.split(IdleLeft, 32, 32);
        TextureRegion[][] runningRight = TextureRegion.split(RunningRight, 32, 32);
        TextureRegion[][] runningLeft = TextureRegion.split(RunningLeft, 32, 32);
        TextureRegion[][] jumpingLeft = TextureRegion.split(JumpingLeft, 32, 32);
        TextureRegion[][] jumpingRight = TextureRegion.split(JumpingRight, 32, 32);

        rolls[0] = new Animation(0.8f, idleRight[0]);
        rolls[1] = new Animation(0.8f, idleLeft[0]);
        rolls[2] = new Animation(0.2f, runningLeft[0]);
        rolls[3] = new Animation(0.2f, runningRight[0]);
        rolls[4] = new Animation(0.2f, jumpingLeft[0]);
        rolls[5] = new Animation(0.2f, jumpingRight[0]);
    }
    public void update(float dt) {
        checkUserInput();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
//        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,32/PPM,32/PPM);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region = null;
        switch (currentState){
            case IDLELEFT:
                region = (TextureRegion) rolls[1].getKeyFrame(stateTimer, true);
                break;
            case IDLERIGHT:
                region = (TextureRegion) rolls[0].getKeyFrame(stateTimer, true);
                break;
            case RUNNINGLEFT:
                region = (TextureRegion) rolls[2].getKeyFrame(stateTimer, true);
                break;
            case RUNNINGRIGHT:
                region = (TextureRegion) rolls[3].getKeyFrame(stateTimer, true);
                break;
            case JUMPINGLEFT:
                region = (TextureRegion) rolls[4].getKeyFrame(stateTimer, true);
                break;
            case JUMPINGRIGHT:
                region = (TextureRegion) rolls[5].getKeyFrame(stateTimer, true);
                break;
        }
        stateTimer = currentState == previousState? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public void checkUserInput(){
        velX = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//            roll = 3;
            currentState = State.RUNNINGRIGHT;
            velX = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//            roll = 2;
            currentState = State.RUNNINGLEFT;
            velX = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0){
            float force = body.getMass() * 10f;
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
        }
        if(body.getLinearVelocity().y!=0) {
//            if (roll == 2 || roll == 1) roll = 4;
//            else if (roll == 3 || roll == 0) roll = 5;
            if(currentState == State.RUNNINGLEFT || currentState == State.IDLELEFT) currentState = State.JUMPINGLEFT;
            else if(currentState == State.RUNNINGRIGHT || currentState == State.IDLERIGHT) currentState = State.JUMPINGRIGHT;
        }
        if(this.velX == 0 && body.getLinearVelocity().y == 0){
//            if(roll == 3 || roll == 5) roll = 0;
//            else if(roll == 2 || roll == 4) roll = 1;
            if(currentState == State.RUNNINGRIGHT || currentState == State.JUMPINGRIGHT) currentState = State.IDLERIGHT;
            else if(currentState == State.RUNNINGLEFT || currentState == State.JUMPINGLEFT) currentState = State.IDLELEFT;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
    }
}
