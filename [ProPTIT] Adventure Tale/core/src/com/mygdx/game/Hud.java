package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Predicate;
import com.badlogic.gdx.utils.viewport.Viewport;
import objects.player.Player;

public class Hud {
    public Stage stage;
    public Player player;
    protected ImageButton CuCaiButton, BachTuocButton, CucDaButton;
    protected boolean BachTuocAddFlag, CucDaAddFlag;
    public Hud(Player player){
        stage = new Stage();
        this.player = player;
        CuCaiButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("CuCaiButton.png"))));
        BachTuocButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("BachTuocButton.png"))));
        CucDaButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("CucDaButton.png"))));

        //Buttons settings
        CuCaiButton.setSize(64,64);
        BachTuocButton.setSize(64,64);
        CucDaButton.setSize(64,64);

        CuCaiButton.setPosition(10, 570);
        BachTuocButton.setPosition(74, 570);
        CucDaButton.setPosition(148, 570);

        CuCaiButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(player.nhanVat != Player.NhanVat.CUCAI) {
                    player.changeToCuCai();
                    focusTo(0);
                }
            }
        });

        BachTuocButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(player.nhanVat != Player.NhanVat.BACHTUOC) {
                    player.changeToBachTuoc();
                    focusTo(1);
                }
            }
        });

        CucDaButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(player.nhanVat != Player.NhanVat.CUCDA) {
                    player.changeToCucDa();
                    focusTo(2);
                }
            }
        });
        //CuCai is default character
        CuCaiButton.setVisible(true); BachTuocButton.setVisible(false); CucDaButton.setVisible(false);
        stage.addActor(CuCaiButton); //index 0
        stage.addActor(BachTuocButton); //index 1
        stage.addActor(CucDaButton); //index 2
        focusTo(0);
    }

    public void update(){
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

        if(player.nhanVat == Player.NhanVat.CUCAI) focusTo(0);
        else if(player.nhanVat == Player.NhanVat.BACHTUOC) focusTo(1);
        else if(player.nhanVat == Player.NhanVat.CUCDA) focusTo(2);
    }

    public void focusTo(int index){
        stage.getActors().get(index).setColor(0,0,0,1);
        stage.getActors().get((index + 1) % 3).setColor(0,0,0,0.3f);
        stage.getActors().get((index + 2) % 3).setColor(0,0,0,0.3f);
    }
}