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
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
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
     * the server port field, if multiplayer
     */
    private TextField port;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public LoginScreen(Main main) {
        this.main = main;

        background = new Texture("1080p.png");
        loginButton = new LoginButton(main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*4, main.WIDTH/7.74f, main.HEIGHT/21.6f, this);
        backButton = new LoginBackButton(main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*6, main.WIDTH/7.74f, main.HEIGHT/21.6f, this);


        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        username.setSize(main.WIDTH/7.74f, main.HEIGHT/21.6f);
        username.setPosition(main.WIDTH/2 - username.getWidth()/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*2);



        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        font = main.getFont48();
        glyph.setText(font, "Please enter your username");

        stage.addActor(loginButton);
        stage.addActor(username);
        stage.addActor(backButton);

        if(main.isMultiplayer()) {
            address = new TextField("", skin);
            address.setSize(main.WIDTH/7.74f, main.HEIGHT/21.6f);
            address.setPosition(main.WIDTH/2 - username.getWidth()/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 +main.HEIGHT/21.6f*3);
            stage.addActor(address);

            port = new TextField("", skin);
            port.setSize(main.WIDTH/7.74f, main.HEIGHT/21.6f);
            port.setPosition(Main.WIDTH/2f - username.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 +Main.HEIGHT/21.6f*1);
            stage.addActor(port);

            glyph2.setText(font, "Please enter the server address and port"); //localhost if you are host

        }

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * go back to previous screen
     */
    public void goBack() {
        main.setScreen(new SPNewOrResume(main));
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
        font.draw(main.batch, glyph, main.WIDTH/2 - glyph.width/2, main.HEIGHT/2  -main.HEIGHT/21.6f*0);
        if(main.isMultiplayer()) {
            font.draw(main.batch, glyph2, main.WIDTH/2 - glyph2.width/2, main.HEIGHT/2  +main.HEIGHT/21.6f*5);
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
        font.dispose();
    }

    /**
     * login method, called by the button
     */
    public void login() {
        String name = username.getText();

        if(!main.isMultiplayer()) {
            main.startServer();
            main.startClient("localhost", 5050);
            boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(name, ShipType.DEFAULT, 0); //ShipType sowieso irrelevant, da kein neues schiff erstellt wird
            if(success) {
                main.setScreen(new SPResumeLobby(main));
                dispose();
            }
        }
        else {
            main.startClient(address.getText(), Integer.parseInt(port.getText()));
            boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(name, ShipType.DEFAULT, 0);
            if(success) {
                main.setScreen(new GamePlay(main));
                dispose();
            }
        }
    }
}
