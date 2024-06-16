package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static helper.Constants.*;

public class MenuScreen implements Screen{
	public static final int WIDTH = APP_WIDTH;
	public static final int HEIGHT = APP_HEIGHT;
	Main main;

	public Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal(MenuBGMusicPath));
	Menu menu;
	public MenuScreen(Main main) {
		this.main = main;
		this.menu = new Menu(main);
//		Gdx.input.setInputProcessor(menu.stage);
	}

	@Override
	public void render (float delta) {
		Gdx.input.setInputProcessor(menu.stage);
		menu.update();
		menu.stage.act(Gdx.graphics.getDeltaTime());
		menu.stage.draw();
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
	}
}
