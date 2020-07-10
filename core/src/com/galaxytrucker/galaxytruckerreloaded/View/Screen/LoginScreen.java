package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.LoginBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.LoginButton;

/**
 * Login Screen
 */
public class LoginScreen implements Screen {

    /**
     * Background image
     */
    private Texture background;

    /**
     * Username input text field
     */
    private TextField username;

    /**
     * the login button
     */
    private LoginButton loginButton;

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the main game class
     */
    private Main main;

    /**
     * the stage
     */
    private Stage stage;

    /**
     * the viewpart
     */
    private Viewport viewport;

    /**
     * the font to draw text with
     */
    private BitmapFont font;

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * whether or not the game will be single player, chosen on an earlier screen
     */
    private boolean singleplayer;

    /**
     * the button to log in with
     */
    private LoginBackButton backButton;

    /**
     * the glyphlayout for the server address, if multiplayer
     * */
    private GlyphLayout glyph2 = new GlyphLayout();

    /**
     * the server address field, if multiplayer
     */
    private TextField address;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public LoginScreen(Main main, boolean singleplayer) {
        this.main = main;
        this.singleplayer = singleplayer;

        background = new Texture("1080p.png");
        loginButton = new LoginButton(main.WIDTH/2 - 124, main.HEIGHT/2 - 200, 248, 50, this);
        backButton = new LoginBackButton(main.WIDTH/2 - 124, main.HEIGHT/2 - 300, 248, 50, this);


        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        username.setSize(248, 50);
        username.setPosition(main.WIDTH/2 - username.getWidth()/2, main.HEIGHT/2 - 100);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

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
        params.size = 40;

        font = generator.generateFont(params);
        glyph.setText(font, "Please enter your username");

        stage.addActor(loginButton);
        stage.addActor(username);
        stage.addActor(backButton);

        if(!singleplayer) {
            address = new TextField("", skin);
            address.setSize(248, 50);
            address.setPosition(main.WIDTH/2 - username.getWidth()/2, main.HEIGHT/2 + 100);
            stage.addActor(address);

            glyph2.setText(font, "Please enter the server address"); //localhost if you are host

        }

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * go back to previous screen
     */
    public void goBack() {
        main.setScreen(new SPNewOrResume(main, singleplayer));
        dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        font.draw(main.batch, glyph, main.WIDTH/2 - glyph.width/2, main.HEIGHT/2 + 50);
        if(!singleplayer) {
            font.draw(main.batch, glyph2, main.WIDTH/2 - glyph2.width/2, main.HEIGHT/2 + 250);
        }
        main.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
    public void dispose() {
        background.dispose();
        stage.dispose();
    }

    /**
     * login method, called by the button
     */
    public void login() {
        String name = username.getText();

        if(singleplayer) {
            main.startClient();
            boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(name, ShipType.DEFAULT); //ShipType sowieso irrelevant, da kein neues schiff erstellt wird
            if(success) {
                main.setScreen(new SPResumeLobby(main));
                dispose();
            }
        }
        else {
            if(address.getText().equals("localhost") || address.getText().equals("127.0.0.1"))
            {
                main.startClient();
                boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(name, ShipType.DEFAULT);
                if(success) {
                    main.setScreen(new LobbyScreenHost(main, main.getClient().getMyShip().getShipType(), true, 0, name)); //TODO diff von server laden
                    dispose();
                }
            }
            else {
                main.startClient();
                boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(name, ShipType.DEFAULT);
                if(success) {
                    main.setScreen(new LobbyScreenHost(main, main.getClient().getMyShip().getShipType(), true, 0, name));
                    dispose();
                }
            }
        }
    }
}
