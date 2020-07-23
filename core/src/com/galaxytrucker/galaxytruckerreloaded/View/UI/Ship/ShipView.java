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
     * Ui displaying the amount of fuel player has.
     */
    private FuelUI fuel;

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

    private ShipType shipType;

    /**
     * the x and y position the ship starts at on the screen
     */
    private float baseX, baseY;

    /**
     * the x position of the weapon buttons and the weapon box
     */
    private float wx;

    /**
     * Constructor
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main, Ship ship, Stage stage, Stage tileStage, Overworld map, GamePlay game) {
        super(main, ship, stage, game, tileStage);

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

        width = shipBackground.getWidth()*1.5f;
        height = shipBackground.getHeight()*1.5f;
        roomWidth = shipRoomBackground.getWidth()*1.5f;
        roomHeight = shipRoomBackground.getHeight()*1.5f;

        baseX = (70 + width/2);
        baseY = main.HEIGHT/2;

        List<Crew> crews = new ArrayList<>();
        for(Room r : ship.getSystems()) {
            crews.addAll(r.getCrew());
        }
        crew = new HashMap<>();
        float cy = Main.HEIGHT - 150;
        for(Crew c : crews) {
            crew.put(c.getId(), new CrewUI(main, c, stage, this, 30, cy, font15, getRoomX(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseX), getRoomY(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseY), ship.getShipType()));
            cy -= 60;
        }

        this.map = map;

        //uis for all the systems/rooms
        rooms = new HashMap<>();
        List<Room> existingRooms = ship.getSystems();
        float sx = 60;
        //id and weaponui for the weaponui, saved temporarily bc it needs to be added to the list last
        int weaponroomid = 0;
        for(Room r : existingRooms) {
            if(r instanceof System) {
                if(((System) r).getSystemType() == SystemType.SHIELDS) {
                    rooms.put(r.getId(), new ShieldUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), baseX), getRoomY(ship.getShipType(), r.getInteriorID(), baseY), (System) r, sx, stage));
                }
                else if(((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                    weaponroomid = r.getId();
                    rooms.put(weaponroomid, new WeaponUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), baseX), getRoomY(ship.getShipType(), r.getInteriorID(), baseY), (System) r, sx, stage));
                }
                else {
                    rooms.put(r.getId(), new SubsystemUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), baseX), getRoomY(ship.getShipType(), r.getInteriorID(), baseY), (System) r, sx, stage));
                }
                sx += 55;
            }
            else {
                rooms.put(r.getId(), new RoomUI(main, r, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), baseX), getRoomY(ship.getShipType(), r.getInteriorID(), baseY)));
            }
        }
        ((WeaponUI) rooms.get(weaponroomid)).setBoxPosition(sx + 10, 80);

        moveButton = new MoveButton(Main.WIDTH/(2.259f), main.HEIGHT - Main.HEIGHT/(12), Main.WIDTH/(21.8f), Main.HEIGHT/(25.12f), this);
        inventory = new ShipButton(Main.WIDTH/(2.5f),main.HEIGHT - Main.HEIGHT/(12), Main.WIDTH/(21.8f), Main.HEIGHT/(25.12f), this);

        money = new ScrapUI(main, ship.getCoins());
        missiles = new MissileUI(main, ship.getMissiles());
        hull = new HullUI(main, ship.getHp());
        fuel = new FuelUI(main, ship.getFuel());

        //Um eine List aller Systems (existingSystems2) an EnergyUI zu übergeben.
        List<System> existingSystems2 = new ArrayList<>();
        for (Room r: existingRooms) {
            if(r instanceof System) {
                existingSystems2.add((System) r);
            }
        }
        energy = new EnergyUI(main, ship.getEnergy(), existingSystems2);


        stage.addActor(inventory);
        stage.addActor(moveButton);
    }

    /**
     * renders the ship
     * no stage stuff!! only textures
     */
    @Override
    public void render() {
        render1();
        tileStage.draw();
        render2();
    }

    /**
     * rendering the first part up to the tile stage
     * this seperation is needed because the enemy ship uses the same tile stage, and otherwise it does not work
     */
    public void render1() {
        main.batch.begin();
        main.batch.draw(shipBackground, 70, main.HEIGHT/2 - height/2, width, height);
        main.batch.draw(shipRoomBackground, (70 + width/2) - roomWidth/2, main.HEIGHT/2 - roomHeight/2, roomWidth, roomHeight);
        main.batch.end();

        money.render();
        missiles.render();
        fuel.render();
        hull.render();
        energy.render();
    }

    /**
     * rendering everything after the tile stage
     */
    public void render2() {
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
        money.disposeScrapUI();
        missiles.disposeMissileUI();
        hull.disposeHullUI();
        fuel.disposeFuelUI();
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
     */
    public void crewMoved(Crew crew, Room room) {
        this.crew.get(crew.getId()).crewMoved(getRoomX(shipType, room.getInteriorID(), baseX), getRoomY(shipType, room.getInteriorID(), baseY));
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
    @Override
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

    /**
     * changes amount of fuel displayed
     * @param amount by how much the amount is changed
     */
    public void changeAmountFuel(int amount) {fuel.changeAmount(amount);}

    /**
     * a weapon is chosen for an attack
     * called by weapon ui
     * @param weapon the weapon that was chosen
     */
    public void weaponChosen(Weapon weapon) {
        game.weaponActivated(weapon);
    }

    /**
     * a weapon was fired
     * called by gameplay screen after communication with controller
     * @param weapon the weapon that was fired
     */
    public void weaponFired(Weapon weapon) {

    }

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
