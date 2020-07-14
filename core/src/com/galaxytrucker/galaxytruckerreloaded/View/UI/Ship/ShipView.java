package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.AutofireButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MoveButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShipButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Map.MapUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipView extends AbstractShip {

    /**
     * the uis of the crew members, matched with their ids
     */
    private HashMap<Integer, CrewUI> crew;

    /**
     * rooms of the ship, key is system id
     */
    private HashMap<Integer, RoomUI> rooms;

    /**
     * the energy ui of this ship
     */
    private EnergyUI energy;

    /**
     * the hull ui of this ship
     */
    private HullUI hull;

    /**
     * the ui displaying the amount of money the player has
     */
    private ScrapUI money;

    /**
     * the ui displaying the amount of missiles the player has
     */
    private MissileUI missiles;

    /**
     * button to open the inventory (InventoryUI)
     */
    private ShipButton inventory;

    /**
     * button on the upper center
     * after the button is clicked, the map opens and a target needs to be selected
     */
    private MoveButton moveButton;

    /**
     * the background texture of the ship
     */
    private Texture shipBackground;

    /**
     * the room overlay
     */
    private Texture shipRoomBackground;

    /**
     * the general background for the weapon display in the bottom left corner next to the energy status display
     */
    private Texture weaponGeneralBackground;

    /**
     * the map (needed for the mapui)
     */
    private Overworld map;


    /**
     * the inventory, if existing
     */
    private InventoryUI inventoryUI;

    /**
     * the map, if existing
     */
    private MapUI mapUI;

    private float width, height;
    private float roomWidth, roomHeight;

    private BitmapFont font15;

    private Stage tileStage;

    private ShipType shipType;

    /**
     * Constructor
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main, Ship ship, Stage stage, Stage tileStage, Overworld map, GamePlay game) {
        super(main, ship, stage, game);
        this.tileStage = tileStage;

        shipType = ship.getShipType();

        //font generator to get bitmapfont from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/JustinFont11Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //setting parameters of font
        params.borderWidth = 1;
        params.borderColor = Color.BLACK;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 15;

        font15 = generator.generateFont(params);

        shipBackground = new Texture("ship/" + main.getClient().getMyShip().getShipType().toString().toLowerCase() + "base.png");
        shipRoomBackground = new Texture("ship/" + main.getClient().getMyShip().getShipType().toString().toLowerCase() + "floor.png");
        weaponGeneralBackground = new Texture("shipsys/weapon_system/generalbox.png");

        width = shipBackground.getWidth()*1.5f;
        height = shipBackground.getHeight()*1.5f;
        roomWidth = shipRoomBackground.getWidth()*1.5f;
        roomHeight = shipRoomBackground.getHeight()*1.5f;

        float roomsBaseX = (70 + width/2);
        float roomsBaseY = main.HEIGHT/2;

        List<Crew> crews = new ArrayList<>();
        for(Room r : ship.getSystems()) {
            crews.addAll(r.getCrew());
        }
        crew = new HashMap<>();
        float cy = Main.HEIGHT - 150;
        for(Crew c : crews) {
            crew.put(c.getId(), new CrewUI(main, c, stage, this, 30, cy, font15, getRoomX(ship.getShipType(), c.getCurrentRoom().getInteriorID(), roomsBaseX), getRoomY(ship.getShipType(), c.getCurrentRoom().getInteriorID(), roomsBaseY), ship.getShipType()));
            cy -= 60;
        }

        this.map = map;

        //uis for all the systems/rooms
        rooms = new HashMap<>();
        List<Room> existingSystems = ship.getSystems();
        float sx = 60;
        for(Room r : existingSystems) {
            if(r instanceof System) {
                if(r instanceof Shield) {
                    rooms.put(r.getId(), new ShieldUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), roomsBaseX), getRoomY(ship.getShipType(), r.getInteriorID(), roomsBaseY), (Shield) r, sx));
                }
                else if(! (r instanceof WeaponSystem)) {
                    rooms.put(r.getId(), new SubsystemUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), roomsBaseX), getRoomY(ship.getShipType(), r.getInteriorID(), roomsBaseY), (System) r, sx));
                }
                sx += 55;
            }
            else {
                rooms.put(r.getId(), new RoomUI(main, r, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), roomsBaseX), getRoomY(ship.getShipType(), r.getInteriorID(), roomsBaseY)));
            }
        }
        //need to be done extra bc they need the actual weapon, not just the system
        for(Weapon w : ship.getInventory()) {
            rooms.put(w.getId(), new WeaponUI(main, tileStage, this, getRoomX(ship.getShipType(), w.getWeaponSystem().getInteriorID(), roomsBaseX), getRoomY(ship.getShipType(), w.getWeaponSystem().getInteriorID(), roomsBaseY), w, sx + 55));
        }

        moveButton = new MoveButton(850, main.HEIGHT - 90, 150, 92, this);
        inventory = new ShipButton(660,main.HEIGHT - 60, 248, 50, this);

        money = new ScrapUI(main, ship.getCoins());
        missiles = new MissileUI(main, ship.getMissiles());
        hull = new HullUI(main, ship.getHp());
        energy = new EnergyUI(main, ship.getEnergy());

        stage.addActor(inventory);
        stage.addActor(moveButton);
    }

    /**
     * get the x position of a room depending on the interior id and the ship type
     * @param id the interior id of the room (from left to right, up to down)
     * @param bx the x position of the start (the most to the left)
     * @return the total x position (lower right corner of the room)
     */
    private float getRoomX(ShipType type, int id, float bx) {
        switch(type) {
            case DEFAULT:
                switch(id) {
                    case 0: return bx - 360;
                    case 1:
                    case 2:
                    case 3:
                        return bx-312;
                    case 4:
                    case 6:
                        return bx-216;
                    case 5:
                        return bx-168;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        return bx-72;
                    case 11:
                    case 12:
                        return bx+24;
                    case 13:
                    case 14:
                        return bx+120;
                    case 15:
                        return bx+216;
                    case 16: return bx+312;
                }
            case BARRAGE: return 0;
            case BOARDER: return 0;
            case TANK: return 0;
            case KILLER: return 0;
            case STEALTH: return 0;
            default: return 0;
        }
    }

    /**
     * return the y position of a room, depending on the ship type
     * @param type the ship type
     * @param id the interior id
     * @return total y position (lower right corner of room)
     */
    private float getRoomY(ShipType type, int id, float by) {
        switch(type) {
            case DEFAULT:
                switch(id) {
                    case 0:
                    case 2:
                    case 5:
                    case 14:
                    case 15:
                    case 16:
                        return by - 48;
                    case 1:
                    case 4:
                        return by +48;
                    case 3:
                    case 6:
                    case 9:
                    case 12:
                        return by -96;
                    case 7: return by +96;
                    case 8:
                    case 11:
                    case 13:
                        return by;
                    case 10 : return by -144;
                }
            case BARRAGE: return 0;
            case BOARDER: return 0;
            case TANK: return 0;
            case KILLER: return 0;
            case STEALTH: return 0;
            default: return 0;
        }
    }

    /**
     * renders the ship
     * no stage stuff!! only textures
     */
    @Override
    public void render() {

        main.batch.begin();
        main.batch.draw(shipBackground, 70, main.HEIGHT/2 - height/2, width, height);
        main.batch.draw(shipRoomBackground, (70 + width/2) - roomWidth/2, main.HEIGHT/2 - roomHeight/2, roomWidth, roomHeight);
        main.batch.draw(weaponGeneralBackground, 700, 100, 328, 90);
        main.batch.end();

        money.render();
        missiles.render();
        hull.render();
        energy.render();

        tileStage.draw();


        for(RoomUI r : rooms.values()) {
            r.render();
        }

        for(CrewUI c : crew.values()) {
            c.render();
        }

        if(inventoryUI != null) {
            inventoryUI.render();
        }
        else if(mapUI != null) {
            mapUI.render();
        }
    }

    /**
     * Dispose of ship
     */
    public void disposeShipView() {
        shipBackground.dispose();
        shipRoomBackground.dispose();
        weaponGeneralBackground.dispose();
        money.disposeScrapUI();
        missiles.disposeMissileUI();
        hull.disposeHullUI();
        energy.disposeEnergyUI();
        if(mapUI!=null) {mapUI.disposeMapUI();}
        if(inventoryUI!=null) {inventoryUI.disposeInventoryUI();}
        game.deleteShip();
        inventory.remove();
        moveButton.remove();
        for(RoomUI r : rooms.values()) {
            r.disposeRoomUI();
        }
        for(CrewUI c : crew.values()) {
            c.disposeCrewUI();
        }
        font15.dispose();
    }

    /**
     * update of the hull status (hp)
     *
     * @param hpvalue new status
     */
    @Override
    public void hullStatusUpdate(int hpvalue) {
        hull.updateStatus(hpvalue);
    }

    /**
     * shield status update
     *
     * @param shieldvalue new status
     */
    @Override
    public void shieldStatusUpdate(int shieldvalue) {
        shields = shieldvalue;
    }

    /**
     * open the inventory of this ship
     * called by ship button
     */
    public void openInventory() {
        if(inventoryUI == null){
            inventoryUI = new InventoryUI(main, game.loadCrew(), game.loadWeapons(), game.loadFuel(), game.loadMissiles(), stage, this, font15, shipType);
        }
    }

    /**
     * open the map
     * called by move button
     */
    public void openMap() {
        if(mapUI == null){
            mapUI = new MapUI(main, stage, map, this);
        }

    }

    /**
     * travel to a planet
     *
     * send to gameplay, through that to travelcontroller, which declares whether this jump is possible
     *
     * if possible, travel to new planet (with execution of event etc)
     * if not, error message??
     *
     * @param planet the target planet
     * @return whether travel valid
     */
    public boolean travel(Planet planet) {
        return game.travel(planet);
    }

    public void deleteInventory() {
        inventoryUI = null;
    }

    public void deleteMap() {
        mapUI = null;
    }



    /**
     * a crew member is moved to a new room
     * called by crew ui after player attempts to move a crew
     * @param crew the crew member
     * @param room the new room
     */
    public void crewMoved(Crew crew, Room room) {
        this.crew.get(crew.getId()).crewMoved(room);
    }

    /**
     * a crew member is moving to a new room
     * called by button
     * @param crew the crew member that was chosen
     */
    public void crewMoving(Crew crew) {
        game.crewMoving(crew);
    }

    /**
     * a room was chosen to move a crew member to
     * @param room the room
     */
    public void roomChosen(Room room) {
        game.roomChosen(room);
    }

    /**
     * crew member health update. called by game
     * @param crew the crew member
     * @param health the new health
     */
    public void crewHealth(Crew crew, int health) {
        if(health == 0) {
            this.crew.get(crew.getId()).crewDied();
        }
        else {
            this.crew.get(crew.getId()).statusUpdate(health);
        }
    }

    public void energyStatusUpdate(int status) {
        energy.energyUpdate(status);
    }

    /**
     * update the energy of a system in a room
     * @param amount the new total amount
     */
    public void roomSystemEnergyUpdate(Room room, int amount) {
        rooms.get(room.getId()).systemEnergyUpdate(amount);
    }

    /**
     * update the status of a system in a room
     * @param room the system
     * @param amount the new status
     */
    public void roomSystemStatusUpdate(Room room, int amount) {
        rooms.get(room.getId()).systemStatusUpdate(amount);
    }

    /**
     * the player has chosen a new amount of energy
     * @param amount how much should be subtracted/added
     */
    public void roomSystemEnergyChosen(Room room, int amount) {
        game.roomSystemEnergyChosen(room, amount);
    }

    /**
     * change the amount of scrap/money displayed
     * @param amount by how much the amount is changed
     */
    public void changeAmountScrap(int amount) { money.changeAmount(amount);}

    /**
     * change the amount of missiles displayed
     * @param amount by how much the amount is changed
     */
    public void changeAmountMissile(int amount) { missiles.changeAmount(amount);}

    public void weaponShot(int weaponid, Room room) { game.weaponShot(weaponid, room);}

    /**
     * Ship hop animation
     */
    private void hyperspeedHopAnimation() {
    }

    /**
     * Shield destroyed animation
     */
    private void shipDestroyedAnimation() {
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show the ship
     */
    public void showShipView() {
    }

    /**
     * Hide the ship
     */
    public void hideShipView() {
    }

}
