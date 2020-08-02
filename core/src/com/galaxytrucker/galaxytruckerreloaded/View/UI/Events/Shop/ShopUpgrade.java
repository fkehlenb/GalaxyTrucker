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
        float dist = 40;
        int i = 0;
        List<Crew> crews = new ArrayList<>();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()){
            Texture t;
            System s;
            if(r.isSystem() && ((System) r).getSystemType()  == SystemType.SHIELDS && ((System) r).isUnlocked()){
                t = new Texture("shipsys/shields/shieldsoverlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            else if(r.isSystem() && ((System) r).getSystemType() == SystemType.CAMERAS && ((System) r).isUnlocked()){
                t = new Texture("shipsys/cameras/camerasoverlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            else if(r.isSystem() && ((System) r).getSystemType() == SystemType.MEDBAY && ((System) r).isUnlocked()){
                t = new Texture("shipsys/medbay/medbayoverlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            else if(r.isSystem() && ((System) r).getSystemType() == SystemType.WEAPON_SYSTEM && ((System) r).isUnlocked()){
                t = new Texture("shipsys/weapon_system/weapon_systemoverlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            else if(r.isSystem() && ((System) r).getSystemType() == SystemType.COCKPIT && ((System) r).isUnlocked()){
                t = new Texture("shipsys/cockpit/cockpitoverlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            else if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE && ((System) r).isUnlocked()){
                t = new Texture("shipsys/engine/engineoverlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            else if(r.isSystem() && ((System) r).getSystemType() == SystemType.O2 && ((System) r).isUnlocked()){
                t = new Texture("shipsys/o2/o2overlay.png");
                s = (System) r;
                elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null,null, s, 0 , ShopElementType.UPGRADES));
                i++;
            }
            if(r.getCrew() != null) {
                crews.addAll(r.getCrew());
            }
        }
        for(Crew c : crews) {
            Texture t = new Texture("crew/"+ ClientControllerCommunicator.getInstance(null).getClientShip().getShipType().toString().toLowerCase()+".png");
            elements.add(new ShopElement(main, stage, t, baseX, baseY+dist*i, shopUI, null, c, null, 0, ShopElementType.CREWUPGRADES));
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
