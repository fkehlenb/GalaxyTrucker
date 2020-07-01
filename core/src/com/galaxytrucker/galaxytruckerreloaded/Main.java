package com.galaxytrucker.galaxytruckerreloaded;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GameStateManager;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

public class Main extends Game {

    /**
     * Settings
     */
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public static final String TITLE = "Galaxy Trucker";
    /**
     * Sprite batch
     */
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        /*Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        //batch.begin();
        //batch.draw("1080p.png",0,0,WIDTH, HEIGHT);
        gsm.render(batch);
        //batch.end();*/
        super.render();
    }

    /**
     * Get the sprite batch
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch() {
        return this.batch;
    }

    public void setFullscreen() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    public void setWindowed() {
        Gdx.graphics.setWindowedMode(WIDTH,HEIGHT);
    }
}
