package helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import objects.box.Bubble;

import static objects.player.Player.isTop;
import static objects.player.Player.isWallLeft;
import static objects.player.Player.isWallRight;
import static objects.player.Player.isBottom;

public class WorldContactListener implements ContactListener {

    protected World world;
    protected GameScreen screen;
    public WorldContactListener(World world, GameScreen screen) {
        this.world = world;
        this.screen = screen;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        Fixture fix = (contact.getFixtureA().getUserData() != "ground") ? contact.getFixtureA() : contact.getFixtureB();
        if (!isTop && !isBottom && !isWallLeft && !isWallRight) {

        }
        if(fix.getUserData() == "leftSensor"){
            isWallLeft = true;
            System.out.println("Left here");
        }
        if(fix.getUserData() == "rightSensor"){
            isWallRight = true;
            System.out.println("Right here");
        }
        if(fix.getUserData() == "topSensor"){
            isTop = true;
            System.out.println("Top here");
        }
        if(fix.getUserData() == "bottomSensor"){
            isBottom = true;
            System.out.println("Bottom here");
        }

        if(fixA.getUserData() == "BachTuoc" || fixB.getUserData() == "BachTuoc"){
            removeBubble("BachTuoc");
            screen.player.BachTuocFlag = true;

        }
        if(fixA.getUserData() == "CucDa" || fixB.getUserData() == "CucDa"){
            removeBubble("CucDa");
            screen.player.CucDaFlag = true;
        }
    }

    public void removeBubble(String data){
        System.out.println("Bubble collided");
        for(Bubble bubble : screen.bubbleList){
            for(Fixture fixture : bubble.body.getFixtureList()){
                if(fixture.getUserData() != null && fixture.getUserData().equals(data)) {
                    screen.destroyList.add(bubble);
                    screen.DestroyFlag = true;
                    break;
                }
            }
            if(screen.DestroyFlag) break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("End contact");
        Fixture fix = (contact.getFixtureA().getUserData() != "ground") ? contact.getFixtureA() : contact.getFixtureB();
        if(fix.getUserData() == "leftSensor"){
            isWallLeft = false;
        } else if(fix.getUserData() == "rightSensor"){
            isWallRight = false;
        } else if(fix.getUserData() == "topSensor"){
            isTop = false;
        } else if(fix.getUserData() == "bottomSensor"){
            isBottom = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}