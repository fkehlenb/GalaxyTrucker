package com.galaxytrucker.galaxytruckerreloaded.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Main.WIDTH;
		config.height = Main.HEIGHT;
		config.title = Main.TITLE;

		new LwjglApplication(new Main(), config);
	}
}
