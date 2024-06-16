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

import static helper.Constants.APP_HEIGHT;
import static helper.Constants.APP_WIDTH;

public class MenuScreen implements Screen {
	public static final int WIDTH = APP_WIDTH;
	public static final int HEIGHT = APP_HEIGHT;
	float playButtonWidth = 196;
	float playButtonHeight = 84;
	public static float iconSize = 40;
	public static boolean checkSound = true; // true = on, false = off
	Texture playButton;
	Texture playButtonClick;
	public static Texture soundOffButton;
	public static Texture soundOffButtonClick;
	public static Texture soundOnButton;
	public static Texture soundOnButtonClick;
	public static Texture background;
	public static Texture name;
	Main main;

	public Music bonusSound = Gdx.audio.newMusic(Gdx.files.internal("sound/bonussound.ogg")),
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/background-DLTTAD.ogg"));
	public MenuScreen(Main main) {
		this.main = main;
		playButton = new Texture("Default.png");
		playButtonClick = new Texture("Hover.png");
		soundOffButton = new Texture("soundOff.png");
		soundOffButtonClick = new Texture("soundOffPress.png");
		soundOnButton = new Texture("soundOn.png");
		soundOnButtonClick = new Texture("soundOnPress.png");
		background = new Texture("bg2.png");
		name = new Texture("name.png");

		bgMusic.setVolume(0.3f);
		bgMusic.setLooping(true);
		bgMusic.play();
		bonusSound.setVolume(0.5f);
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		main.batch.begin();
		main.batch.draw(background, 0, 0, WIDTH, HEIGHT);
		main.batch.draw(name, 135, 120, 700, 700);
		float xPlay = (WIDTH - playButtonWidth) / 2;
		float yPlay = (HEIGHT - playButtonHeight) / 2;
		if (Gdx.input.getX() >= xPlay && Gdx.input.getX() <= xPlay + playButtonWidth && Gdx.input.getY() >= yPlay && Gdx.input.getY() <= yPlay + playButtonHeight) {
			main.batch.draw(playButtonClick, xPlay, yPlay, playButtonWidth, playButtonHeight);
			if (Gdx.input.isTouched()) {
				this.dispose();
				bonusSound.play();
				main.levelScreen = new LevelScreen(main);
				main.setScreen(main.levelScreen);
			}
		} else {
			main.batch.draw(playButton, xPlay, yPlay, playButtonWidth, playButtonHeight);
		}
		int xSound = 12;
		if (Gdx.input.getX() >= xSound && Gdx.input.getX() <= xSound + iconSize && Gdx.input.getY() >= HEIGHT - (xSound + iconSize) && Gdx.input.getY() <= HEIGHT - xSound) {

			if (checkSound) {
				main.batch.draw(soundOnButtonClick, xSound, xSound, iconSize, iconSize);
				if (Gdx.input.isTouched()) {
					checkSound = false;
					main.menuScreen.bgMusic.stop();
					main.batch.draw(soundOffButtonClick, xSound, xSound, iconSize, iconSize);
				}
			} else {
				main.batch.draw(soundOffButtonClick, xSound, xSound, iconSize, iconSize);
				if (Gdx.input.isTouched()) {
					checkSound = true;
					main.menuScreen.bgMusic.play();
					main.batch.draw(soundOnButtonClick, xSound, xSound, iconSize, iconSize);
				}
			}
		} else {
			if (checkSound) {
				main.batch.draw(soundOnButton, xSound, xSound, iconSize, iconSize);
			} else {
				main.batch.draw(soundOffButton, xSound, xSound, iconSize, iconSize);
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
	}
}
