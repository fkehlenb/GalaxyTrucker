package com.galaxytrucker.galaxytruckerreloaded;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import com.galaxytrucker.galaxytruckerreloaded.Server.ServerServiceCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LoginScreen;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;
import lombok.Getter;
import lombok.Setter;

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

    /**
     * the client
     */
    @Getter
    @Setter
    private Client client;

    /**
     * the server
     */
    @Getter
    @Setter
    private Server server;

    /**
     * start a server, if there isnt one
     */
    public void startServer() {
        if(server == null) {
            server = new Server();
            server.setPort(5050);
            server.setServerServiceCommunicator(new ServerServiceCommunicator());
            new Thread(server).start();
            try {
                Thread.sleep(1000);
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    /**
     * start a client, if there isnt already one
     */
    public void startClient() {
        if(client == null) {
            client = new Client("localhost", 5050);
        }
    }

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
