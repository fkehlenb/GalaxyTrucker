package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.List;

public class ShopCrew extends CurrentShopUI {

    List<ShopElement> elements;

    public ShopCrew(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);
        elements = new ArrayList<>();
        //crew stock
        for(Crew c : trader.getCrewStock()) {
            Texture t;
            if(c.getName().equals("ana")) {
                t = new Texture("crew/killer.png");
            }
            else if(c.getName().equals("battle")) {
                t = new Texture("crew/barrage.png");
            }
            else {
                t = new Texture("crew/stealth.png"); //TODO wie sieht das mit namen aus?
            }
            elements.add(new ShopElement(main, stage, t, 0, 0, shopUI, null, c, null, 0, ShopElementType.CREW));
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
