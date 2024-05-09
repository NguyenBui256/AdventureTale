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
    public enum NhanVat {CUCAI, BACHTUOC, CUCDA};
    public NhanVat nhanVat;
    public CuCai NhanVatCuCai;
    public BachTuoc NhanVatBachTuoc;
    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    public float speed, velX, stateTimer;

    public Player(GameScreen screen, Body body) {
        this.world = screen.world;
        setBounds(body.getPosition().x, body.getPosition().y,64/PPM,64/PPM);

        currentState = State.IDLERIGHT;
        previousState = State.IDLERIGHT;

        nhanVat = NhanVat.CUCAI;
        NhanVatCuCai = new CuCai(
                "IdleRight.png",
                "IdleLeft.png",
                "RunningRight.png",
                "RunningLeft.png",
                "JumpingLeft.png",
                "JumpingRight.png"
        );
//        NhanVatBachTuoc = new BachTuoc();

        stateTimer = 0;
        this.body = body;
        this.speed = 10f;
    }
    public void update(float dt) {
        checkUserInput();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
        if(nhanVat == NhanVat.CUCAI) setRegion(getFrame(NhanVatCuCai,dt));
//        else if(nhanVat == NhanVat.BACHTUOC) setRegion(getFrame(NhanVatBachTuoc,dt));
//        else if(nhanVat == NhanVat.CUCDA) setRegion(getFrame(NhanVatCucDa, dt));
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
}
