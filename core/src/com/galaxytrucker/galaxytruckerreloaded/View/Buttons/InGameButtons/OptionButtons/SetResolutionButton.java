package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;

public class SetResolutionButton extends ImButton {

    private Sound clickSound;

    private OptionUI optionUI;

    private Main main;

    private int gameWidth;

    private int gameHeight;


    public SetResolutionButton(float x, float y, float width, float height, Main main, int gameWidth, int gameHeight) {
        super(new Texture("options/set_button.png"), x, y, width, height);
        this.main = main;
        this.gameHeight = gameHeight;
        if (this.gameHeight == 0){this.gameHeight = 1080;}
        this.gameWidth = gameWidth;
        if(this.gameWidth == 0){this.gameWidth = 1920;}
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    @Override
    public void leftClick() {
        main.setResolution(gameWidth, gameHeight);
    }
}
