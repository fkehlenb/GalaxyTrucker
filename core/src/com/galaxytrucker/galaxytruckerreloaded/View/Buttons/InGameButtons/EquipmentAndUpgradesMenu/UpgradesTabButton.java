package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EquipmentAndUpgradesMenu;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Opens the Upgrade-Tab in the Menue
 */
public class UpgradesTabButton extends Button
{
    /**
     * Sprite batch
     */
    private SpriteBatch batch;
    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * Constructor
     * @param main - main class
     */
    public UpgradesTabButton(Main main) {
    }

    /**
     * opens the Upgrade-Tab-Screen
     */
    @Override
    public void leftClick()
   {
//        if(FTLView.instance().getScreen() instanceof SpaceScreen)
//            ((SpaceScreen)FTLView.instance().getScreen()).openGUI(new UpgradeUI(FTLGame.instance().getPlayer()));
    }
}