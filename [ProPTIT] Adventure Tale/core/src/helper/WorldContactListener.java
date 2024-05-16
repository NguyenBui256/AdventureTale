package helper;

import com.badlogic.gdx.physics.box2d.*;

import static objects.player.Player.top;
import static objects.player.Player.bottom;
import static objects.player.Player.left;
import static objects.player.Player.right;
import static objects.player.Player.isTop;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fix = (contact.getFixtureA().getUserData() != "ground") ? contact.getFixtureA() : contact.getFixtureB();
        if(fix.getUserData() == "leftSensor"){
            top = true;
            bottom = true;
            System.out.println("Left here");
        }
        if(fix.getUserData() == "rightSensor"){
            top = true;
            bottom = true;
            System.out.println("Right here");
        }
        if(fix.getUserData() == "topSensor"){
            left = true;
            right = true;
            isTop = true;
            System.out.println("Top here");
        }
        if(fix.getUserData() == "bottomSensor"){
            left = true;
            right = true;
            System.out.println("Bottom here");
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("End contact");
        Fixture fix = (contact.getFixtureA().getUserData() != "ground") ? contact.getFixtureA() : contact.getFixtureB();
        if(fix.getUserData() == "leftSensor"){

            top = false;
            bottom = false;
        }
        if(fix.getUserData() == "rightSensor"){
            top = false;
            bottom = false;
        }
        if(fix.getUserData() == "topSensor"){
            left = false;
            right = false;
            isTop = false;
        }
        if(fix.getUserData() == "bottomSensor"){
            left = false;
            right = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}