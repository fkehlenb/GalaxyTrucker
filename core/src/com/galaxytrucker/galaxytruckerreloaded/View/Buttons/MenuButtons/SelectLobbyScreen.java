package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Select;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LobbyScreenHost;


public class SelectLobbyScreen implements Screen {

    private Main main;

    private Viewport viewport;

    private Stage stage;

    private Texture background;

    private TextField lobby;

    private SelectLobbyBackButton backButton;

    private SelectLobbyButton selectLobbyButton;

    public SelectLobbyScreen(Main main) {
        this.main = main;

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        lobby = new TextField("", skin);
        lobby.setPosition(main.WIDTH/2 - lobby.getWidth()/2, main.HEIGHT/2 - lobby.getHeight()/2 + 50);

        backButton = new SelectLobbyBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 100, 512, 48, this, main);
        selectLobbyButton = new SelectLobbyButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 50, 512, 48, this);

        stage.addActor(lobby);
        stage.addActor(backButton);
        stage.addActor(selectLobbyButton);

        background = new Texture("1080p.png");

        Gdx.input.setInputProcessor(stage);
    }

    public void joinLobby() {
        //controller call
        boolean host = true;
        if(host) {
            main.setScreen(new LobbyScreenHost(main));
        }
        dispose();
    }

    /**
     * Called when this screen becomes the current screen for a
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        main.batch.end();

        stage.draw();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}
