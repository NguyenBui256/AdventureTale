package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.io.IOException;
import static helper.Constants.APP_HEIGHT;
import static helper.Constants.APP_WIDTH;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Adventure Tale");
		config.setWindowedMode(APP_WIDTH, APP_HEIGHT);
		config.setWindowIcon("textures/characters/cucaibb.png");
		config.setResizable(false);

		new Lwjgl3Application(new Main(), config);
	}

}