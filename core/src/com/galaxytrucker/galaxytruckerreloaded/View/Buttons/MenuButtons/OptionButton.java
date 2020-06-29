package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;

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
     * Constructor
     *
     */
    public OptionButton(float x, float y, float width, float height, MainMenu mainMenu) {
        super(new Texture("options_select2.png"), x, y, width, height);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
        this.mainMenu = mainMenu;
    }

    /**
     * Creats new Game
     */
    public void leftClick()
    {
        //ui.render();
        if(mainMenu != null){
            mainMenu.createOptions();
        }
        else {
            System.out.println("Fehler");
        }
    }

}


