package com.galaxytrucker.galaxytruckerreloaded;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {

    }

    /**
     * Get the sprite batch
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch() {
        return this.batch;
    }

    /**
     * Get the orthographic camera
     *
     * @return the orthographic camera
     */
    public OrthographicCamera getCamera() {
        return this.camera;
    }
}
