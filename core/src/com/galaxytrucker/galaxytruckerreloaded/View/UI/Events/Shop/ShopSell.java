package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.ClientHandler;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShopSell extends CurrentShopUI {
    /**
     * the items that can be sold
     */
    private List<ShopSellElement> sellElements;
    private List<Room> r;


    public ShopSell(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x, y);

        //add all the items that can be sold TODO geldanzeige immer ändern
        sellElements = new ArrayList<>();
        float dist = 40;
        int i = 0;

        //weapons
        for(Weapon w : ClientControllerCommunicator.getInstance(null).getClientShip().getInventory()) {
            sellElements.add(new ShopSellElement(main, stage, new Texture("shipsys/weapon_system/"+w.getWeaponType().toString().toLowerCase()+".png"), baseX, baseY+dist*i, shopUI, w, null));
            i++;
        }
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
        for(ShopSellElement e : sellElements) {
            e.render();
        }
    }

    @Override
    public void dispose() {
        for(ShopSellElement e : sellElements) {
            e.disposeShopSellElement();
        }
    }
}
