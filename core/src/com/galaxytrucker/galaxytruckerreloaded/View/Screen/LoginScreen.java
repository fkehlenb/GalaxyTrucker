package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.LoginButton;

/**
 * Login Screen
 */
public class LoginScreen extends MenuScreen {

    /**
     * Username input text field
     */
    private TextField username;

    /**
     * the font to draw text with
     */
    private BitmapFont font;

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph = new GlyphLayout();

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
        super(main);

        LoginButton loginButton = new LoginButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*4, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        BackButton backButton = new BackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*6, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);


        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        username.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        username.setPosition(Main.WIDTH/2f - username.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2);

        font = main.getFont48();
        glyph.setText(font, "Please enter your username");

        stage.addActor(loginButton);
        stage.addActor(username);
        stage.addActor(backButton);

        if(main.isMultiplayer()) {
            address = new TextField("", skin);
            address.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
            address.setPosition(Main.WIDTH/2f - username.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 +Main.HEIGHT/21.6f*3);
            stage.addActor(address);

            port = new TextField("", skin);
            port.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
            port.setPosition(Main.WIDTH/2f - username.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 +Main.HEIGHT/21.6f*1);
            stage.addActor(port);

            glyph2.setText(font, "Please enter the server address and port"); //localhost if you are host

        }

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * go back to previous screen
     */
    @Override
    public void goBack() {
        main.setScreen(new SPNewOrResume(main));
        dispose();
    }

    /**
     * render everything to the screen
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        font.draw(main.batch, glyph, Main.WIDTH/2f - glyph.width/2, Main.HEIGHT/2f  -Main.HEIGHT/21.6f*0);
        if(main.isMultiplayer()) {
            font.draw(main.batch, glyph2, Main.WIDTH/2f - glyph2.width/2, Main.HEIGHT/2f  +Main.HEIGHT/21.6f*5);
        }
        main.batch.end();
        stage.draw();
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
