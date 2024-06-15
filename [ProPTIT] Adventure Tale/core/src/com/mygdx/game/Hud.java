package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import helper.TileMapHelper;
import objects.player.Player;

import static helper.Constants.*;

public class Hud {
    public Stage stage;
    public Player player;
    public boolean restart = false;
    public boolean level = false;
    public boolean nextlevel = false, goToNextLevel = false;
    protected ImageButton CuCaiButton, BachTuocButton, CucDaButton, PauseButton, PauseClickButton, RestartButton, RestartClickButton;
    protected ImageButton PauseTB, WinTB, ContinueButton, LevelButton, ContinueClickButton, LevelClickButton, RestartButton2, RestartClickButton2;
    protected ImageButton DarkBackground;
    protected boolean BachTuocAddFlag, CucDaAddFlag;
    public Music bonusSound = Gdx.audio.newMusic(Gdx.files.internal(BonusSound));
    public Hud(Player player){
        stage = new Stage();
        this.player = player;
        CuCaiButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(CuCaiButtonPath))));
        BachTuocButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(BachTuocButtonPath))));
        CucDaButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(CucDaButtonPath))));
        PauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(PauseButtonPath))));
        PauseClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(PauseClickButtonPath))));
        RestartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(RestartButtonPath))));
        RestartClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(RestartClickButtonPath))));
        PauseTB = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(PauseTbPath))));
        WinTB = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(WinTbPath))));
        ContinueButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(ContinuePath))));
        ContinueClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(ContinueClickPath))));
        LevelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(LevelPath))));
        LevelClickButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(LevelClickPath))));
        RestartButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(RestartButtonPath))));
        RestartClickButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(RestartClickButtonPath))));
        DarkBackground = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(BlackFadePath))));

        bonusSound.setVolume(0.4f);

        PauseButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bonusSound.play();
                DarkBackground.setVisible(true);
                DarkBackground.setColor(0, 0, 0, 0.2f);
                PauseButton.setVisible(false);
                PauseClickButton.setVisible(true);
                PauseTB.setVisible(true);
                ContinueButton.setVisible(true);
                LevelButton.setVisible(true);
                RestartButton2.setVisible(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PauseButton.setVisible(true);
                PauseClickButton.setVisible(false);
            }
        });
        RestartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bonusSound.play();
                RestartButton.setVisible(false);
                RestartClickButton.setVisible(true);
                restart = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RestartButton.setVisible(true);
                RestartClickButton.setVisible(false);
            }
        });
        RestartButton2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bonusSound.play();
                RestartButton2.setVisible(false);
                RestartClickButton2.setVisible(true);
                restart = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RestartClickButton2.setVisible(false);
                PauseTB.setVisible(false);
                WinTB.setVisible(false);
                ContinueButton.setVisible(false);
                LevelButton.setVisible(false);
                DarkBackground.setVisible(false);
            }
        });
        ContinueButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bonusSound.play();
                if(nextlevel) {
                    System.out.println("Duoc roi ne!!");
                    goToNextLevel = true;
                }
                ContinueButton.setVisible(false);
                ContinueClickButton.setVisible(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PauseTB.setVisible(false);
                DarkBackground.setVisible(false);
                ContinueClickButton.setVisible(false);
                LevelButton.setVisible(false);
                RestartButton2.setVisible(false);
            }
        });
        LevelButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bonusSound.play();
                LevelButton.setVisible(false);
                LevelClickButton.setVisible(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                level = true;
            }
        });
        //CuCai is default character
        CuCaiButton.setVisible(true); BachTuocButton.setVisible(false); CucDaButton.setVisible(false);
        RestartButton.setVisible(true); PauseButton.setVisible(true);
        RestartClickButton.setVisible(false); PauseClickButton.setVisible(false);
        ContinueButton.setVisible(false); ContinueClickButton.setVisible(false);
        LevelButton.setVisible(false); LevelClickButton.setVisible(false);
        RestartButton2.setVisible(false); RestartClickButton2.setVisible(false);
        DarkBackground.setVisible(false); WinTB.setVisible(false);
        PauseTB.setVisible(false);
        stage.addActor(CuCaiButton); //index 0
        stage.addActor(BachTuocButton); //index 1
        stage.addActor(CucDaButton); //index 2
        stage.addActor(RestartButton); //index 3
        stage.addActor(RestartClickButton); //index 4
        stage.addActor(PauseButton); //index 5
        stage.addActor(PauseClickButton); //index 6
        stage.addActor(DarkBackground); //index 7
        stage.addActor(PauseTB); //index 8
        stage.addActor(WinTB); //index 9
        stage.addActor(ContinueButton); //index 10
        stage.addActor(ContinueClickButton); //index 11
        stage.addActor(LevelButton); //index 12
        stage.addActor(LevelClickButton); //index 13
        stage.addActor(RestartButton2); //index 14
        stage.addActor(RestartClickButton2); //index 15
        focusTo(0);
    }

    public void update(){
        stage.getActors().get(3).setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
        stage.getActors().get(3).setPosition(APP_WIDTH - SMALL_BUTTON_SIZE * 2 - BUTTON_DISTANCE, BUTTON_POS_Y2);
        stage.getActors().get(4).setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
        stage.getActors().get(4).setPosition(APP_WIDTH - SMALL_BUTTON_SIZE * 2 - BUTTON_DISTANCE, BUTTON_POS_Y2);
        stage.getActors().get(5).setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
        stage.getActors().get(5).setPosition(APP_WIDTH - SMALL_BUTTON_SIZE - BUTTON_DISTANCE, BUTTON_POS_Y2);
        stage.getActors().get(6).setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
        stage.getActors().get(6).setPosition(APP_WIDTH - SMALL_BUTTON_SIZE - BUTTON_DISTANCE, BUTTON_POS_Y2);
        stage.getActors().get(7).setPosition(0, 0);
        stage.getActors().get(8).setSize(TB_WIDTH, TB_HEIGHT);
        stage.getActors().get(8).setPosition(TB_POS_X, TB_POS_Y);
        stage.getActors().get(9).setSize(TB_WIDTH, TB_HEIGHT);
        stage.getActors().get(9).setPosition(TB_POS_X, TB_POS_Y);
        stage.getActors().get(10).setSize(BIG_BUTTON_SIZE, BIG_BUTTON_SIZE);
        stage.getActors().get(10).setPosition((APP_WIDTH - BUTTON_SIZE)/2 - 5, TB_POS_Y + 95);
        stage.getActors().get(11).setSize(BIG_BUTTON_SIZE, BIG_BUTTON_SIZE);
        stage.getActors().get(11).setPosition((APP_WIDTH - BUTTON_SIZE)/2 - 5, TB_POS_Y + 95);
        stage.getActors().get(12).setSize(BUTTON_SIZE, BUTTON_SIZE);
        stage.getActors().get(12).setPosition((APP_WIDTH - BUTTON_SIZE)/2 - BIG_BUTTON_SIZE - 5, TB_POS_Y + 95);
        stage.getActors().get(13).setSize(BUTTON_SIZE, BUTTON_SIZE);
        stage.getActors().get(13).setPosition((APP_WIDTH - BUTTON_SIZE)/2 - BIG_BUTTON_SIZE - 5, TB_POS_Y + 95);
        stage.getActors().get(14).setSize(BUTTON_SIZE, BUTTON_SIZE);
        stage.getActors().get(14).setPosition((APP_WIDTH - BUTTON_SIZE)/2 + BIG_BUTTON_SIZE + 5, TB_POS_Y + 95);
        stage.getActors().get(15).setSize(BUTTON_SIZE, BUTTON_SIZE);
        stage.getActors().get(15).setPosition((APP_WIDTH - BUTTON_SIZE)/2 + BIG_BUTTON_SIZE + 5, TB_POS_Y + 95);
        if(player.BachTuocFlag && !this.BachTuocAddFlag) {
            stage.addActor(BachTuocButton);
            stage.getActors().get(1).setVisible(true);
            this.BachTuocAddFlag = true;
        }
        if(player.CucDaFlag && !this.CucDaAddFlag){
            stage.addActor(CucDaButton);
            stage.getActors().get(2).setVisible(true);
            this.CucDaAddFlag = true;
        }

        if(player.nhanVat == NhanVat.CUCAI) focusTo(0);
        else if(player.nhanVat == NhanVat.BACHTUOC) focusTo(1);
        else if(player.nhanVat == NhanVat.CUCDA) focusTo(2);
    }

    public void focusTo(int index){
        stage.getActors().get(index).setColor(0,0,0,1);
        stage.getActors().get(index).setSize(BIG_BUTTON_SIZE,BIG_BUTTON_SIZE);
        stage.getActors().get((index + 1) % 3).setColor(0,0,0,0.2f);
        stage.getActors().get((index + 1) % 3).setSize(BUTTON_SIZE,BUTTON_SIZE);
        stage.getActors().get((index + 2) % 3).setColor(0,0,0,0.2f);
        stage.getActors().get((index + 2) % 3).setSize(BUTTON_SIZE,BUTTON_SIZE);

        if(index == 0){
            stage.getActors().get(0).setPosition(BUTTON_PADDING,BUTTON_POS_Y - (BIG_BUTTON_SIZE - BUTTON_SIZE) / 2);
            stage.getActors().get(1).setPosition(2*BUTTON_PADDING + BIG_BUTTON_SIZE,BUTTON_POS_Y);
            stage.getActors().get(2).setPosition(3*BUTTON_PADDING + BIG_BUTTON_SIZE + BUTTON_SIZE,BUTTON_POS_Y);
        }
        if(index == 1){
            stage.getActors().get(0).setPosition(BUTTON_PADDING,BUTTON_POS_Y);
            stage.getActors().get(1).setPosition(2*BUTTON_PADDING + BUTTON_SIZE,BUTTON_POS_Y - (BIG_BUTTON_SIZE - BUTTON_SIZE) / 2);
            stage.getActors().get(2).setPosition(3*BUTTON_PADDING + BIG_BUTTON_SIZE + BUTTON_SIZE,BUTTON_POS_Y);
        }
        if(index == 2){
            stage.getActors().get(0).setPosition(BUTTON_PADDING,BUTTON_POS_Y);
            stage.getActors().get(1).setPosition(2*BUTTON_PADDING + BUTTON_SIZE,BUTTON_POS_Y);
            stage.getActors().get(2).setPosition(3*BUTTON_PADDING + 2*BUTTON_SIZE,BUTTON_POS_Y - (BIG_BUTTON_SIZE - BUTTON_SIZE) / 2);
        }
    }
    public void win(){
        this.nextlevel = true;
        DarkBackground.setVisible(true);
        DarkBackground.setColor(0, 0, 0, 0.2f);
        PauseButton.setVisible(false);
        PauseClickButton.setVisible(true);
        WinTB.setVisible(true);
        ContinueButton.setVisible(true);
        LevelButton.setVisible(true);
        RestartButton2.setVisible(true);
    }
}