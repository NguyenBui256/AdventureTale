package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import objects.box.Bubble;
import helper.Constants.*;
import objects.box.Button;
import objects.player.Player;
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
        if((sensorDirectionA == VatThe.MAPBOUND && sensorDirectionB == NhanVat.MAIN)
                || (sensorDirectionB == VatThe.MAPBOUND && sensorDirectionA == NhanVat.MAIN)
                || (sensorDirectionA == VatThe.FIRE && sensorDirectionB == NhanVat.MAIN)
                || (sensorDirectionA == NhanVat.MAIN && sensorDirectionB == VatThe.FIRE))
        {
            screen.gameScreenReset();
        }

        if (index == -1 || (!screen.glassList.get(index).isBroken && screen.nhanVat != NhanVat.CUCDA)) {
            if(sensorDirectionA != VatThe.FIRE && sensorDirectionB != VatThe.FIRE
                    && sensorDirectionA != VatThe.MAPBOUND && sensorDirectionB != VatThe.MAPBOUND) {
                if (sensorDirectionA == SensorDirection.LEFT || sensorDirectionB == SensorDirection.LEFT) {
                    Player.senL = true;
                    Player.senLCount++;
//            System.out.println("Left here");
                }
                if (sensorDirectionA == SensorDirection.RIGHT || sensorDirectionB == SensorDirection.RIGHT) {
                    Player.senR = true;
                    Player.senRCount++;
//            System.out.println("Right here");
                }
                if (sensorDirectionA == SensorDirection.TOP || sensorDirectionB == SensorDirection.TOP) {
                    Player.senT = true;
                    Player.senTCount++;
//            System.out.println("Top here");
                }
                if (sensorDirectionA == SensorDirection.TOPLEFT || sensorDirectionB == SensorDirection.TOPLEFT) {
                    Player.senTL = true;
                    Player.senTLCount++;
//            System.out.println("TL here");
                }
                if (sensorDirectionA == SensorDirection.TOPRIGHT || sensorDirectionB == SensorDirection.TOPRIGHT) {
                    Player.senTR = true;
                    Player.senTRCount++;
//            System.out.println("TR here");
                }
                if (sensorDirectionA == SensorDirection.BOT || sensorDirectionB == SensorDirection.BOT) {
                    Player.senB = true;
                    Player.senBCount++;
//            System.out.println("Bot here");
                }
                if (sensorDirectionA == SensorDirection.BOTLEFT || sensorDirectionB == SensorDirection.BOTLEFT) {
                    Player.senBL = true;
                    Player.senBLCount++;
//            System.out.println("BL here");
                }
                if (sensorDirectionA == SensorDirection.BOTRIGHT || sensorDirectionB == SensorDirection.BOTRIGHT) {
                    Player.senBR = true;
                    Player.senBRCount++;
//            System.out.println("BR here");
                }
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
                if (Player.senLCount > 0) {
                    Player.senLCount--;
                    if (Player.senLCount <= 0) Player.senL = false;
                }
            }
            if (sensorDirectionA == SensorDirection.RIGHT || sensorDirectionB == SensorDirection.RIGHT) {
                if (Player.senRCount > 0) {
                    Player.senRCount--;
                    if (Player.senRCount <= 0) Player.senR = false;
                }
            }
            if (sensorDirectionA == SensorDirection.TOP || sensorDirectionB == SensorDirection.TOP) {
                if (Player.senTCount > 0) {
                    Player.senTCount--;
                    if (Player.senTCount <= 0) Player.senT = false;
                }
            }
            if (sensorDirectionA == SensorDirection.TOPLEFT || sensorDirectionB == SensorDirection.TOPLEFT) {
                if (Player.senTLCount > 0) {
                    Player.senTLCount--;
                    if (Player.senTLCount <= 0) Player.senTL = false;
                }
            }
            if (sensorDirectionA == SensorDirection.TOPRIGHT || sensorDirectionB == SensorDirection.TOPRIGHT) {
                if (Player.senTRCount > 0) {
                    Player.senTRCount--;
                    if (Player.senTRCount <= 0) Player.senTR = false;
                }
            }
            if (sensorDirectionA == SensorDirection.BOT || sensorDirectionB == SensorDirection.BOT) {
                if (Player.senBCount > 0) {
                    Player.senBCount--;
                    if (Player.senBCount <= 0) Player.senB = false;
                }
            }
            if (sensorDirectionA == SensorDirection.BOTLEFT || sensorDirectionB == SensorDirection.BOTLEFT) {
                if (Player.senBLCount > 0) {
                    Player.senBLCount--;
                    if (Player.senBLCount <= 0) Player.senBL = false;
                }
            }
            if (sensorDirectionA == SensorDirection.BOTRIGHT || sensorDirectionB == SensorDirection.BOTRIGHT) {
                if (Player.senBRCount > 0) {
                    Player.senBRCount--;
                    if (Player.senBRCount <= 0) Player.senBR = false;
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
