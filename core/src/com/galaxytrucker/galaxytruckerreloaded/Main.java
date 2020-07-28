package com.galaxytrucker.galaxytruckerreloaded;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Controller.AudioController;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;
import lombok.Getter;
import lombok.Setter;

public class Main extends Game {

    /**
     * Settings
     */
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;

    public static final String TITLE = "Galaxy Trucker";

    /**
     * Sprite batch
     */
    public SpriteBatch batch;

    /**
     * font, white, size 15
     */
    @Getter
    private BitmapFont font15;

    /**
     * font, white, size 25
     */
    @Getter
    private BitmapFont font25;

    /**
     * font, red, size 25
     */
    @Getter
    private BitmapFont font25Red;

    /**
     * font, white, size Main.HEIGHT/48
     */
    @Getter
    private BitmapFont font48;

    /**
     * font, white, size Main.HEIGHT/72
     */
    @Getter
    private BitmapFont font72;

    /**
     * the client
     */
    @Getter
    private Client client;

    @Getter
    private boolean host;

    /**
     * whether this is a multiplayer game
     */
    @Getter
    @Setter
    private boolean multiplayer;

    /**
     * start a server, if there isnt one
     */
    public void startServer() {
        Server.getInstance();
        host = true;
    }

    /**
     * start a client, if there isnt already one
     */
    public void startClient(String address, int port) {
        if (client == null) {
            client = new Client(address, port);
        }
    }

    /**
     * logout
     * after official logout from server through clientControllerCommunicator
     */
    public void logout() {
        if(host) {
            Server.getInstance().killServer();
        }
        setScreen(new MainMenu(this));
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        AudioController.getInstance().setMusic(Gdx.files.internal("Sounds/Music/bp_MUS_WastelandEXPLORE.ogg"));

        //font generator to get bitmapfont from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/JustinFont11Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //setting parameters of font
        params.borderWidth = 1;
        params.borderColor = Color.BLACK;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 15;

        //font 15
        font15 = generator.generateFont(params);

        //font 25 white
        params.size = 25;
        font25 = generator.generateFont(params);

        //font 48 white
        params.size = Main.HEIGHT/48;
        font48 = generator.generateFont(params);

        //font 72 white
        params.size = Main.HEIGHT/72;
        font72 = generator.generateFont(params);

        //font 25 red
        params.color = Color.RED;
        params.size = 25;
        font25Red = generator.generateFont(params);

        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
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

    public void setResolution(int width, int height){WIDTH = width; HEIGHT = height; create();}
}

