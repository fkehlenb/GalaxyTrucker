package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons.ShopBuyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShopUpgrade extends CurrentShopUI {

    /**
     * the button with which to buy something
     */
    private ShopBuyButton button;

    /**
     * the texture of this element
     */
    private Texture texture;

    /**
     * the position on the screen
     */
    private float x, y;

    /**
     * the weapon, if this is a weapon
     */
    private Weapon weapon;

    /**
     * the crew member, if this is a crew member
     */
    private Crew crew;

    /**
     * the amount, if this is fuel, hp or missiles
     */
    private int amount;

    /**
     * the name (fuel, hp, or missiles) if this is one of those
     */
    private String type;

    /**
     * the items that can be sold
     */
    private List<ShopElement> elements;

    public ShopUpgrade(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game, trader, shopUI, x, y);
        elements = new LinkedList<>();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()){
            if(r.isSystem())
                //TODO: Texturen und positionen anpassen
            elements.add(new ShopElement(main, stage, new Texture("shop/openShop.png"), x, y, shopUI, null, null, (System) r, 0, ShopElementType.UPGRADES ));
        }

    }

    @Override
    public void render() {
        for (ShopElement e: elements) {
            e.render();
        }
    }

    @Override
    public void dispose() {
        for (ShopElement e: elements) {
            e.dispose();
        }
    }

}
