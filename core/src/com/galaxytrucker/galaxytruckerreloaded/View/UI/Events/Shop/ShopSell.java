package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.LinkedList;
import java.util.List;

public class ShopSell extends CurrentShopUI {
    /**
     * the items that can be sold
     */
    private List<ShopSellElement> sellElements;


    public ShopSell(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);

        //add all the items that can be sold TODO geldanzeige immer ändern
        sellElements = new LinkedList<>();
        //Crew
        /*
        for(Crew c : shipCrew) {
            sellElements.add(new ShopSellElement(main, stage, new Texture("crew.png"), 0, 0, this, null, shipCrew));
        }
        //weapons
        for(Weapon w : shipWeapons) {
            sellElements.add(new ShopSellElement(main, stage, new Texture("laser.png"), 0, 0, this, w, 0));
        }*/
    }
    /**
     * remove a sellable element
     * @param e the element
     */
    public void removeSellElement(ShopSellElement e) {
        sellElements.remove(e); //TODO add to the list of buyable items
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
