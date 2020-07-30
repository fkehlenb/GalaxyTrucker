package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

/**
 * button for moving onto the next round in a fight
 */
public class NextRoundButton extends ImButton {

    /**
     * the screen this button is on
     */
    private GamePlay screen;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen ui this button is on
     */
    public NextRoundButton(float x, float y, float width, float height, GamePlay screen) {
        super(new Texture("buttons/next_round_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * left click action
     */
    @Override
    public void leftClick() {
        screen.nextFightRound();
    }
}
