package helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import objects.box.Box;
import objects.box.Bubble;

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
//        if(fixA.getUserData() != null) System.out.println("A: " + fixA.getUserData().getClass());
//        if(fixB.getUserData() != null) System.out.println("B: " + fixB.getUserData().getClass());

        if(fixA.getUserData() == "leftSensor" || fixB.getUserData() == "leftSensor"){
            System.out.println("Left here");
        }
        if(fixA.getUserData() == "rightSensor" || fixB.getUserData() == "rightSensor"){
            System.out.println("Right here");
        }
        if(fixA.getUserData() == "topSensor" || fixB.getUserData() == "topSensor"){
            System.out.println("Top here");
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
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
