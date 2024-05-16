package helper;

import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() == "leftSensor" || fixB.getUserData() == "leftSensor"){
            System.out.println("Left here");
        }
        if(fixA.getUserData() == "rightSensor" || fixB.getUserData() == "rightSensor"){
            System.out.println("Right here");
        }
        if(fixA.getUserData() == "topSensor" || fixB.getUserData() == "topSensor"){
            System.out.println("Top here");
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
