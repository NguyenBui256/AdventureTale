package helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import objects.box.Bubble;
import helper.Constants.*;
import objects.box.Button;

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
        int index = -1;
        if (isGlass(sensorDirectionA)) {
            index = sensorDirectionA.toString().charAt(5) - '0';
            if (sensorDirectionA.toString().length() == 7) {
                index = index * 10 + sensorDirectionA.toString().charAt(6) - '0';
            }
        } else if (isGlass(sensorDirectionB)) {
            index = sensorDirectionB.toString().charAt(5) - '0';
            if (sensorDirectionB.toString().length() == 7) {
                index = index * 10 + sensorDirectionB.toString().charAt(6) - '0';
            }
        }
        if (index == -1 || (!screen.glassList.get(index).isBroken && screen.nhanVat != NhanVat.CUCDA)) {
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
                screen.player.senTL = true;
                screen.player.senTLCount++;
//            System.out.println("TL here");
            }
            if(sensorDirectionA == SensorDirection.TOPRIGHT|| sensorDirectionB == SensorDirection.TOPRIGHT){
                screen.player.senTR = true;
                screen.player.senTRCount++;
//            System.out.println("TR here");
            }
            if(sensorDirectionA == SensorDirection.BOT|| sensorDirectionB == SensorDirection.BOT){
                screen.player.senB = true;
                screen.player.senBCount++;
//            System.out.println("Bot here");
            }
            if(sensorDirectionA == SensorDirection.BOTLEFT|| sensorDirectionB == SensorDirection.BOTLEFT){
                screen.player.senBL = true;
                screen.player.senBLCount++;
//            System.out.println("BL here");
            }
            if(sensorDirectionA == SensorDirection.BOTRIGHT || sensorDirectionB == SensorDirection.BOTRIGHT){
                screen.player.senBR = true;
                screen.player.senBRCount++;
//            System.out.println("BR here");
            }
        }

        if((sensorDirectionA == NhanVat.BACHTUOC && sensorDirectionB == NhanVat.MAIN)
                || (sensorDirectionB == NhanVat.BACHTUOC && sensorDirectionA == NhanVat.MAIN)) {
            removeBubble(NhanVat.BACHTUOC);
            screen.player.BachTuocFlag = true;
            if(screen.player.soundOn) screen.player.bonusSound.play();
        }
        if((sensorDirectionA == NhanVat.CUCDA && sensorDirectionB == NhanVat.MAIN)
                || (sensorDirectionB == NhanVat.CUCDA && sensorDirectionA == NhanVat.MAIN)){
            removeBubble(NhanVat.CUCDA);
            screen.player.CucDaFlag = true;
            if(screen.player.soundOn) screen.player.bonusSound.play();
        }

        if((sensorDirectionA == VatThe.DOOR && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionB == VatThe.DOOR && sensorDirectionA == NhanVat.MAIN)){
            if (screen.isPass) {
                screen.TRS.transitionOutFlag = true;
                if(screen.player.soundOn) screen.player.endlevelMusic.play();
                screen.ingameBGMusic.stop();
            }
        }


        if((sensorDirectionA == VatThe.MAPBOUND && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionB == VatThe.MAPBOUND && sensorDirectionA == NhanVat.MAIN)
        || (sensorDirectionA == VatThe.FIRE && sensorDirectionB == NhanVat.MAIN)
        || (sensorDirectionA == NhanVat.MAIN && sensorDirectionB == VatThe.FIRE))
        {
            screen.gameScreenReset();
        }

        if(((sensorDirectionA == VatThe.BUTTON && sensorDirectionB == NhanVat.MAIN) ||
        (sensorDirectionA == NhanVat.MAIN && sensorDirectionB == VatThe.BUTTON)) ||
        ((sensorDirectionA == VatThe.BOX && sensorDirectionB == VatThe.BUTTON) ||
        (sensorDirectionA == VatThe.BUTTON && sensorDirectionB == VatThe.BOX))){
            Button.pressCount++;
            if(Button.pressCount > 0) Button.isClick = true;
        }

        if (screen.nhanVat == NhanVat.CUCDA && ((isGlass(sensorDirectionA) && sensorDirectionB == NhanVat.MAIN)
                || (sensorDirectionA == NhanVat.MAIN && isGlass(sensorDirectionB)))) {
            if(!screen.glassList.get(index).isBroken && screen.player.soundOn) screen.player.glassSound.play();
            screen.glassList.get(index).isBroken = true;
        }
    }
    public boolean isGlass (Object object) {
        return (object != null && object.toString().length() > 5 && object.toString().charAt(5) >= '0' && object.toString().charAt(5) <='9');
    }
    public void removeBubble(NhanVat data){
//        System.out.println("Bubble collided");
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
        Object sensorDirectionA = contact.getFixtureA().getUserData();
        Object sensorDirectionB = contact.getFixtureB().getUserData();

        // Bỏ qua nếu va chạm với FIRE hoặc MAPBOUND
//        if ((sensorDirectionA == VatThe.MAPBOUND || sensorDirectionB == VatThe.MAPBOUND) ||
//                (sensorDirectionA == VatThe.FIRE || sensorDirectionB == VatThe.FIRE)) {
//            return;
//        }

        int index = -1;
        if (isGlass(sensorDirectionA)) {
            index = sensorDirectionA.toString().charAt(5) - '0';
            if (sensorDirectionA.toString().length() == 7) {
                index = index * 10 + sensorDirectionA.toString().charAt(6) - '0';
            }
        } else if (isGlass(sensorDirectionB)) {
            index = sensorDirectionB.toString().charAt(5) - '0';
            if (sensorDirectionB.toString().length() == 7) {
                index = index * 10 + sensorDirectionB.toString().charAt(6) - '0';
            }
        }

        if (index == -1 || (!screen.glassList.get(index).isBroken && screen.nhanVat != NhanVat.CUCDA)) {
            // Giảm số lượng sensor tương ứng
            if (sensorDirectionA == SensorDirection.LEFT || sensorDirectionB == SensorDirection.LEFT) {
                if (screen.player.senLCount > 0) {
                    screen.player.senLCount--;
                    if (screen.player.senLCount <= 0) screen.player.senL = false;
                }
            }
            if (sensorDirectionA == SensorDirection.RIGHT || sensorDirectionB == SensorDirection.RIGHT) {
                if (screen.player.senRCount > 0) {
                    screen.player.senRCount--;
                    if (screen.player.senRCount <= 0) screen.player.senR = false;
                }
            }
            if (sensorDirectionA == SensorDirection.TOP || sensorDirectionB == SensorDirection.TOP) {
                if (screen.player.senTCount > 0) {
                    screen.player.senTCount--;
                    if (screen.player.senTCount <= 0) screen.player.senT = false;
                }
            }
            if (sensorDirectionA == SensorDirection.TOPLEFT || sensorDirectionB == SensorDirection.TOPLEFT) {
                if (screen.player.senTLCount > 0) {
                    screen.player.senTLCount--;
                    if (screen.player.senTLCount <= 0) screen.player.senTL = false;
                }
            }
            if (sensorDirectionA == SensorDirection.TOPRIGHT || sensorDirectionB == SensorDirection.TOPRIGHT) {
                if (screen.player.senTRCount > 0) {
                    screen.player.senTRCount--;
                    if (screen.player.senTRCount <= 0) screen.player.senTR = false;
                }
            }
            if (sensorDirectionA == SensorDirection.BOT || sensorDirectionB == SensorDirection.BOT) {
                if (screen.player.senBCount > 0) {
                    screen.player.senBCount--;
                    if (screen.player.senBCount <= 0) screen.player.senB = false;
                }
            }
            if (sensorDirectionA == SensorDirection.BOTLEFT || sensorDirectionB == SensorDirection.BOTLEFT) {
                if (screen.player.senBLCount > 0) {
                    screen.player.senBLCount--;
                    if (screen.player.senBLCount <= 0) screen.player.senBL = false;
                }
            }
            if (sensorDirectionA == SensorDirection.BOTRIGHT || sensorDirectionB == SensorDirection.BOTRIGHT) {
                if (screen.player.senBRCount > 0) {
                    screen.player.senBRCount--;
                    if (screen.player.senBRCount <= 0) screen.player.senBR = false;
                }
            }
        }

        if (((sensorDirectionA == VatThe.BUTTON && sensorDirectionB == NhanVat.MAIN) ||
                (sensorDirectionA == NhanVat.MAIN && sensorDirectionB == VatThe.BUTTON) ||
                (sensorDirectionA == VatThe.BOX && sensorDirectionB == VatThe.BUTTON) ||
                (sensorDirectionA == VatThe.BUTTON && sensorDirectionB == VatThe.BOX))) {
            Button.pressCount--;
            if (Button.pressCount == 0) Button.isClick = false;
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
