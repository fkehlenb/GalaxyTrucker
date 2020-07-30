package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.List;

/**
 * displaying the crew members that can be bought
 */
public class ShopCrew extends CurrentShopUI {

    /**
     * the elements that need to be displayed
     */
    private List<ShopElement> elements;

    /**
     * construcot
     * @param main main class extending game
     * @param stage stage for buttons
     * @param game screen this is on
     * @param trader trader offering the items
     * @param shopUI ui this is on
     * @param x base x position for rendering
     * @param y base y position for rendering
     */
    public ShopCrew(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x, y);
        elements = new ArrayList<>();
        float dist = main.HEIGHT/27;
        //crew stock
        int i = 0;
        Texture t = new Texture("crew/"+ ClientControllerCommunicator.getInstance(null).getClientShip().getShipType().toString().toLowerCase()+".png");
        for(Crew c : trader.getCrewStock()) {
            elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null, c, null, 0, ShopElementType.CREW));
            i++;
        }
    }

    /**
     * render everything to the screen
     */
    @Override
    public void render() {
        for (ShopElement e: elements) {
            e.render();
        }
    }

    /**
     * dispose everything
     */
    @Override
    public void dispose() {
        for (ShopElement e: elements) {
            e.dispose();
        }
    }
}
