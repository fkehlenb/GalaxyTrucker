package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * creates a new OptionButton
 */
public class OptionButton extends ImButton{

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /** Menu object */
    private MainMenu mainMenu;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param mainMenu screen the button is on
     */
    public OptionButton(float x, float y, float width, float height, MainMenu mainMenu) {
        super(new Texture("buttons/options_button.png"), x, y, width, height);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
        this.mainMenu = mainMenu;
    }

    /**
     * create options
     */
    public void leftClick()
    {
        if(mainMenu != null){
            mainMenu.createOptions();
        }
        else {
            System.out.println("Fehler");
        }
    }

}


