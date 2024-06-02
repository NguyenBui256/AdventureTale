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
        Object dataA = fixA.getUserData();
        Object dataB = fixB.getUserData();
//        if(fixA.getUserData() != null) System.out.println("A: " + fixA.getUserData().getClass());
//        if(dataB != null) System.out.println("B: " + dataB.getClass());

        if(dataA == "leftSensor" || dataB == "leftSensor"){
            screen.player.senL = true;
            System.out.println("Left here");
        }
        if(dataA == "rightSensor" || dataB == "rightSensor"){
            screen.player.senR = true;
            System.out.println("Right here");
        }
        if(dataA == "topSensor" || dataB == "topSensor"){
            screen.player.senT = true;
            System.out.println("Top here");
        }
        if(dataA == "topLeftSensor" || dataB == "topLeftSensor"){
            screen.player.senTLCount++;
            screen.player.senTL = true;
            System.out.println("TL here");
        }
        if(dataA == "topRightSensor" || dataB == "topRightSensor"){
            screen.player.senTRCount++;
            screen.player.senTR = true;
            System.out.println("TR here");
        }
        if(dataA == "bottomSensor" || dataB == "bottomSensor"){
            screen.player.senB = true;
            System.out.println("Bot here");
        }
        if(dataA == "bottomLeftSensor" || dataB == "bottomLeftSensor"){
            screen.player.senBLCount++;
            screen.player.senBL = true;
            System.out.println("BL here");
        }
        if(dataA == "bottomRightSensor" || dataB == "bottomRightSensor"){
            screen.player.senBRCount++;
            screen.player.senBR = true;
            System.out.println("BR here");
        }
        if(dataA == "BachTuoc" || dataB == "BachTuoc"){
            removeBubble("BachTuoc");
            screen.player.BachTuocFlag = true;
        }
        if(dataA == "CucDa" || dataB == "CucDa"){
            removeBubble("CucDa");
            screen.player.CucDaFlag = true;
        }
        if(dataA == "door" || dataB == "door" || dataA == "bound" || dataB == "bound"){
            screen.endMap = true;
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
        System.out.println("end contact");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        Object dataA = fixA.getUserData();
        Object dataB = fixB.getUserData();
        if(dataA == "leftSensor" || dataB == "leftSensor"){
            screen.player.senL = false;
        }
        if(dataA == "rightSensor" || dataB == "rightSensor"){
            screen.player.senR = false;
        }
        if(dataA == "topSensor" || dataB == "topSensor"){
            screen.player.senT = false;
        }
        if(dataA == "topLeftSensor" || dataB == "topLeftSensor"){
            screen.player.senTLCount--;
            if(screen.player.senTLCount <= 1) screen.player.senTL = false;
        }
        if(dataA == "topRightSensor" || dataB == "topRightSensor"){
            screen.player.senTRCount--;
            if(screen.player.senTRCount <= 1) screen.player.senTR = false;
        }
        if(dataA == "bottomSensor" || dataB == "bottomSensor"){
            screen.player.senB = false;
        }
        if(dataA == "bottomLeftSensor" || dataB == "bottomLeftSensor"){
            screen.player.senBLCount--;
            if(screen.player.senBLCount <= 1) screen.player.senBL = false;
        }
        if(dataA == "bottomRightSensor" || dataB == "bottomRightSensor"){
            screen.player.senBRCount--;
            if(screen.player.senBRCount <= 1) screen.player.senBR = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
