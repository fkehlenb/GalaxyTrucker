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

    public ShopCrew(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x, y);
        elements = new ArrayList<>();
        float dist = 40;
        //crew stock
        int i = 0;
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
            elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null, c, null, 0, ShopElementType.CREW));
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
