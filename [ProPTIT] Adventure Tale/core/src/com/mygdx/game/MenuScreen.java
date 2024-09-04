package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import static helper.Constants.*;

public class MenuScreen implements Screen{
	public static final int WIDTH = APP_WIDTH;
	public static final int HEIGHT = APP_HEIGHT;
	Main main;
	public Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal(MENU_BG_MUSIC));
	public Menu menu;
	public MenuScreen(Main main) {
		this.main = main;
		this.menu = new Menu(main);
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
