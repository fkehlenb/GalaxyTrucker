package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

public class EquipmentTabButton extends Button
{
    /**
     * Constructor
     *
     * @param main - main class
     */
    public EquipmentTabButton(Main main) {
    }

    @Override
    public void leftClick()
    {
        //if(FTLView.instance().getScreen() instanceof SpaceScreen)
        //    ((SpaceScreen)FTLView.instance().getScreen()).openGUI(new EquipmentUI(FTLGame.instance().getPlayer()));
    }
}