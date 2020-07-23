package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopCrew extends CurrentShopUI{

    public ShopCrew(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);

        //crew stock
        for(Crew c : trader.getCrewStock()) {
            Texture t;
            if(c.getName().equals("ana")) {
                t = new Texture("crew/anaerobic.png");
            }
            else if(c.getName().equals("battle")) {
                t = new Texture("crew/battle.png");
            }
            else {
                t = new Texture("crew/energy.png"); //TODO wie sieht das mit namen aus?
            }
            elements.add(new ShopElement(main, stage, t, 0, 0, this, null, c, 0, null));
        }
    }


    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
