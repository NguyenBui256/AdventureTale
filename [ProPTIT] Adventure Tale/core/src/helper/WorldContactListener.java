package helper;

import com.badlogic.gdx.physics.box2d.*;

import static objects.player.Player.isTop;
import static objects.player.Player.isWallLeft;
import static objects.player.Player.isWallRight;
import static objects.player.Player.isBottom;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
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