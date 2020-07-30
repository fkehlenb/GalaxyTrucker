package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.SystemController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.List;

public class ShopSystem extends CurrentShopUI {

    /**
     * the items that can be sold
     */
    private List<ShopElement> elements;

    public ShopSystem(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x,y);
        elements = new ArrayList<>();
        float dist = 40;
        int i = 0;
        for(Room r: ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()){
            Texture t;
            System s;
            if(r.isSystem() && ((System) r).getSystemType()  != SystemType.SHIELDS){
                t = new Texture("shipsys/shields/shieldsoverlay.png");
                s = (System) r;
            }
            else if(r.isSystem() && ((System) r).getSystemType() != SystemType.CAMERAS){
                t = new Texture("shipsys/cameras/camerasoverlay.png");
                s = (System) r;
            }
            else if(r.isSystem() && ((System) r).getSystemType() != SystemType.MEDBAY){
                t = new Texture("shipsys/medbay/medbayoverlay.png");
                s = (System) r;
            }
            else{
                 return;

            }
            elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.SYSTEM));
            i++;
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
