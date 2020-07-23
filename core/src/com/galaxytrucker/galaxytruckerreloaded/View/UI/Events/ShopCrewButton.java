package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopCrewButton extends ImButton {

    private ShopCrew shopCrew;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    public ShopCrewButton(float x, float y, float width, float height, ShopUI shopUI, Trader trader, ShopCrew shopCrew) {

    }
    /**
     * buy a crew member from the trader
     * @param crew the crew member
     */
    boolean buyCrew(Crew crew) {
        return game.buyCrew(crew);
    }

    public void leftClick(){
        shop.getCurrent().dispose();
        new ShopCrew(main, stage, game, trader, shop);
    }
}
