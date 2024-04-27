package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {
	public static final int Width = 720;
	public static final int Height = 480;
	float playButtonWidth = 150;
	float playButtonHeight = 70;
	public static float iconSize = 40;
	public static boolean checkSound = true; // true = on, false = off
	Texture playButton;
	Texture playButtonClick;
	public static Texture soundOffButton;
	public static Texture soundOffButtonClick;
	public static Texture soundOnButton;
	public static Texture soundOnButtonClick;
	Main main;
	public MenuScreen(Main main) {
		this.main = main;
		playButton = new Texture("PLAY_Default@0.5x.png");
		playButtonClick = new Texture("PLAY_Hover@0.5x.png");
		soundOffButton = new Texture("SOUND_OFF_Default@0.5x.png");
		soundOffButtonClick = new Texture("SOUND_OFF_Hover@0.5x.png");
		soundOnButton = new Texture("SOUND_ON_Default@0.5x.png");
		soundOnButtonClick = new Texture("SOUND_ON_Hover@0.5x.png");
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		main.batch.begin();
		float xPlay = (Width - playButtonWidth) / 2;
		float yPlay = (Height - playButtonHeight) / 2;
		if (Gdx.input.getX() >= xPlay && Gdx.input.getX() <= xPlay + playButtonWidth && Gdx.input.getY() >= yPlay && Gdx.input.getY() <= yPlay + playButtonHeight) {
			main.batch.draw(playButtonClick, xPlay, yPlay, playButtonWidth, playButtonHeight);
			if (Gdx.input.isTouched()) {
				this.dispose();
				main.setScreen(new LevelScreen(main));
			}
		} else {
			main.batch.draw(playButton, xPlay, yPlay, playButtonWidth, playButtonHeight);
		}
		if (Gdx.input.getX() >= 12 && Gdx.input.getX() <= 12 + iconSize && Gdx.input.getY() >= 12 && Gdx.input.getY() <= 12 + iconSize) {
			if (checkSound) {
				main.batch.draw(soundOnButtonClick, 12, 12, iconSize, iconSize);
				if (Gdx.input.isTouched()) {
					checkSound = false;
					main.batch.draw(soundOffButtonClick, 12, 12, iconSize, iconSize);
				}
			} else {
				main.batch.draw(soundOffButtonClick, 12, 12, iconSize, iconSize);
				if (Gdx.input.isTouched()) {
					checkSound = true;
					main.batch.draw(soundOnButtonClick, 12, 12, iconSize, iconSize);
				}
			}
		} else {
			if (checkSound) {
				main.batch.draw(soundOnButton, 12, 12, iconSize, iconSize);
			} else {
				main.batch.draw(soundOffButton, 12, 12, iconSize, iconSize);
			}
		}
		main.batch.end();
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
		main.batch.dispose();
		playButton.dispose();
		playButtonClick.dispose();
		soundOffButton.dispose();
		soundOnButton.dispose();
		soundOffButtonClick.dispose();
		soundOnButtonClick.dispose();
	}
}
