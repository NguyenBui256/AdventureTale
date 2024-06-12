package helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import objects.box.Box;
import objects.box.Bubble;
import helper.Constants.*;

public class WorldContactListener implements ContactListener {
    protected World world;
    protected GameScreen screen;
    public WorldContactListener(World world, GameScreen screen) {
        this.world = world;
        this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {

        Object sensorDirectionA = contact.getFixtureA().getUserData();
        Object sensorDirectionB = contact.getFixtureB().getUserData();

        if(sensorDirectionA == SensorDirection.LEFT || sensorDirectionB == SensorDirection.LEFT){
            screen.player.senL = true;
            screen.player.senLCount++;
//            System.out.println("Left here");
        }
        if(sensorDirectionA == SensorDirection.RIGHT || sensorDirectionB == SensorDirection.RIGHT){
            screen.player.senR = true;
            screen.player.senRCount++;
//            System.out.println("Right here");
        }
        if(sensorDirectionA == SensorDirection.TOP || sensorDirectionB == SensorDirection.TOP){
            screen.player.senT = true;
            screen.player.senTCount++;
//            System.out.println("Top here");
        }
        if(sensorDirectionA == SensorDirection.TOPLEFT || sensorDirectionB == SensorDirection.TOPLEFT){
            screen.player.senTLCount++;
            screen.player.senTL = true;
//            System.out.println("TL here");
        }
        if(sensorDirectionA == SensorDirection.TOPRIGHT|| sensorDirectionB == SensorDirection.TOPRIGHT){
            screen.player.senTRCount++;
            screen.player.senTR = true;
//            System.out.println("TR here");
        }
        if(sensorDirectionA == SensorDirection.BOT|| sensorDirectionB == SensorDirection.BOT){
            screen.player.senB = true;
            screen.player.senBCount++;
//            System.out.println("Bot here");
        }
        if(sensorDirectionA == SensorDirection.BOTLEFT|| sensorDirectionB == SensorDirection.BOTLEFT){
            screen.player.senBLCount++;
            screen.player.senBL = true;
//            System.out.println("BL here");
        }
        if(sensorDirectionA == SensorDirection.BOTRIGHT || sensorDirectionB == SensorDirection.BOTRIGHT){
            screen.player.senBRCount++;
            screen.player.senBR = true;
//            System.out.println("BR here");
        }
        if((sensorDirectionA == NhanVat.BACHTUOC && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionB == NhanVat.BACHTUOC && sensorDirectionA == NhanVat.MAIN)) {
            removeBubble(NhanVat.BACHTUOC);
            screen.player.BachTuocFlag = true;
            screen.player.bonusSound.play();
        }
        if((sensorDirectionA == NhanVat.CUCDA && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionB == NhanVat.CUCDA && sensorDirectionA == NhanVat.MAIN)){
            removeBubble(NhanVat.CUCDA);
            screen.player.CucDaFlag = true;
            screen.player.bonusSound.play();
        }
        if((sensorDirectionA == VatThe.DOOR && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionB == VatThe.DOOR && sensorDirectionA == NhanVat.MAIN)
        || (sensorDirectionA == VatThe.MAPBOUND && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionB == VatThe.MAPBOUND && sensorDirectionA == NhanVat.MAIN)){
            screen.TRS.transitionOutFlag = true;
            screen.player.endlevelMusic.play();
            screen.ingameBGMusic.stop();
        }
    }

    public void removeBubble(NhanVat data){
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
//        System.out.println("end contact");
        Object sensorDirectionA = contact.getFixtureA().getUserData();
        Object sensorDirectionB = contact.getFixtureB().getUserData();
        if(sensorDirectionA == SensorDirection.LEFT || sensorDirectionB == SensorDirection.LEFT){
            screen.player.senLCount--;
            if(screen.player.senLCount <= 1) screen.player.senL = false;
        }
        if(sensorDirectionA == SensorDirection.RIGHT || sensorDirectionB == SensorDirection.RIGHT){
            screen.player.senRCount--;
            if(screen.player.senRCount <= 1) screen.player.senR = false;
        }
        if(sensorDirectionA == SensorDirection.TOP || sensorDirectionB == SensorDirection.TOP){
            screen.player.senTCount--;
            if(screen.player.senTCount <= 1) screen.player.senT = false;
        }
        if(sensorDirectionA == SensorDirection.TOPLEFT || sensorDirectionB == SensorDirection.TOPLEFT){
            screen.player.senTLCount--;
            if(screen.player.senTLCount <= 1) screen.player.senTL = false;
        }
        if(sensorDirectionA == SensorDirection.TOPRIGHT || sensorDirectionB == SensorDirection.TOPRIGHT){
            screen.player.senTRCount--;
            if(screen.player.senTRCount <= 1) screen.player.senTR = false;
        }
        if(sensorDirectionA == SensorDirection.BOT || sensorDirectionB == SensorDirection.BOT){
            screen.player.senBCount--;
            if(screen.player.senBCount <= 1) screen.player.senB = false;
        }
        if(sensorDirectionA == SensorDirection.BOTLEFT || sensorDirectionB == SensorDirection.BOTLEFT){
            screen.player.senBLCount--;
            if(screen.player.senBLCount <= 1) screen.player.senBL = false;
        }
        if(sensorDirectionA == SensorDirection.BOTRIGHT || sensorDirectionB == SensorDirection.BOTRIGHT){
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
