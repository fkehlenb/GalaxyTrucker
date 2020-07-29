package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MoveButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.PauseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShipButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Map.MapUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
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
     * the ftlChargeUI
     */
    private FtlChargeUI ftlChargeUI;

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
     * pausemenubutton to call pausemenu
     */
    private PauseButton pauseButton;

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

    @Getter
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
     * the y position for the crew stuff on the most left side (with name etc)
     */
    private float cy;

    /**
     * the list of y positions of removed crew member uis
     * used to position the crew uis on the left side without holes etc
     */
    private List<Float> freeYPositions = new ArrayList<>();

    /**
     * Constructor
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main, Ship ship, Stage stage, Stage tileStage, GamePlay game, BitmapFont font15, BitmapFont font25) {
        super(main, ship, stage, game, tileStage);
        this.font15 = font15;

        shipType = ship.getShipType();

        shipBackground = new Texture("ship/" + main.getClient().getMyShip().getShipType().toString().toLowerCase() + "base.png");
        shipRoomBackground = new Texture("ship/" + main.getClient().getMyShip().getShipType().toString().toLowerCase() + "floor.png");

        width = shipBackground.getWidth()*1.5f;
        height = shipBackground.getHeight()*1.5f;
        roomWidth = shipRoomBackground.getWidth()*1.5f;
        roomHeight = shipRoomBackground.getHeight()*1.5f;


        baseX = (70 + width/2);
        baseY = Main.HEIGHT/2f;

        List<Crew> crews = new ArrayList<>();
        for(Room r : ship.getSystems()) {
            crews.addAll(r.getCrew());
        }
        crew = new HashMap<>();
        cy = Main.HEIGHT - 150;
        for(Crew c : crews) {
            crew.put(c.getId(), new CrewUI(main, c, stage, this, 30, cy, font15, getRoomX(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseX), getRoomY(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseY), ship.getShipType()));
            cy -= 60;
        }

        //uis for all the systems/rooms
        rooms = new HashMap<>();
        List<Room> existingRooms = ship.getSystems();
        float sx = 60;
        //id and weaponui for the weaponui, saved temporarily bc it needs to be added to the list last
        int weaponroomid = 0;
        for(Room r : existingRooms) {
            if(r instanceof System) {
                if(((System) r).getSystemType() == SystemType.SHIELDS) {
                    rooms.put(r.getId(), new ShieldUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), baseX), getRoomY(ship.getShipType(), r.getInteriorID(), baseY), (System) r, sx, stage, 70, 235, Main.HEIGHT - 170, ship.getShields()));
                }
                else if(((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                    weaponroomid = r.getId();
                    rooms.put(weaponroomid, new WeaponUI(main, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), baseX), getRoomY(ship.getShipType(), r.getInteriorID(), baseY), (System) r, sx, stage, font15));
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

        moveButton = new MoveButton(Main.WIDTH/(2.259f), Main.HEIGHT - Main.HEIGHT/(12f), Main.WIDTH/(21.8f), Main.HEIGHT/(25.12f), this);
        inventory = new ShipButton(Main.WIDTH/(2.5f),Main.HEIGHT - Main.HEIGHT/(12f), Main.WIDTH/(21.8f), Main.HEIGHT/(25.12f), this);
        pauseButton = new PauseButton(95*Main.WIDTH/100f, 95*Main.HEIGHT/100f, Main.WIDTH/(21.8f),Main.HEIGHT/(25.12f),this);

        money = new ScrapUI(main, ship.getCoins(), font25);
        missiles = new MissileUI(main, ship.getMissiles(), font25);
        hull = new HullUI(main, ship.getHp());
        fuel = new FuelUI(main, ship.getFuel(), font25);
        ftlChargeUI = new FtlChargeUI(main, ship.getFTLCharge());

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
        stage.addActor(pauseButton);
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
        main.batch.draw(shipBackground, 70, Main.HEIGHT/2f - height/2, width, height);
        main.batch.draw(shipRoomBackground, (70 + width/2) - roomWidth/2, Main.HEIGHT/2f - roomHeight/2, roomWidth, roomHeight);
        main.batch.end();

        money.render();
        missiles.render();
        fuel.render();
        hull.render();
        energy.render();
        ftlChargeUI.render();
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
        pauseButton.remove();
        ftlChargeUI.disposeFtlChargeUI();
        for(RoomUI r : rooms.values()) {
            r.disposeRoomUI();
        }
        for(CrewUI c : crew.values()) {
            c.disposeEnemyCrewUI();
        }
    }

    /**
     * update everything that belongs to the ship
     * @param ship the ship with all the updated attributes
     */
    public void update(Ship ship) {
        //Hull
        hull.updateStatus(ship.getHp());
        //Rooms
        //Crew
        List<Weapon> equippedWeapons = new ArrayList<>();
        List<Integer> deadOnes = new ArrayList<>(crew.keySet());
        for(Room r : ship.getSystems()) {
            for(Crew c : r.getCrew()) {
                try {
                    crew.get(c.getId()).update(c, getRoomX(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseX), getRoomY(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseY));
                    deadOnes.remove(new Integer(c.getId())); //do not remove "new Integer(...)", otherwise it will use wrong remove method
                }
                catch (NullPointerException f){
                    crew.put(c.getId(), new CrewUI(main, c, stage, this, 30, cy, font15, getRoomX(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseX), getRoomY(ship.getShipType(), c.getCurrentRoom().getInteriorID(), baseY), shipType));
                    cy -= 60;
                }
            }
            if(r.isSystem() && ((System) r).getSystemType() != SystemType.SHIELDS) {
                rooms.get(r.getId()).update(r);
                if (((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                    equippedWeapons.addAll(((System) r).getShipWeapons());
                }
            }
            else if(r.isSystem()) { //shields has an extra update method
                ((ShieldUI) rooms.get(r.getId())).update(r, ship.getShields());
            }
        }
        //remove the crew uis of dead crew members
        for(Integer i : deadOnes) {
            crew.get(i).crewDied();
            freeYPositions.add(crew.get(i).getY());
            crew.remove(i);
        }
        //see if the crew uis that are left need to be repositioned
        if(freeYPositions.size() > 0) {
            Collections.sort(freeYPositions);
            for (CrewUI c : crew.values()) {
                if(freeYPositions.size() > 0) {
                    if (c.getY() < freeYPositions.get(freeYPositions.size()-1)) {
                        float newone = c.getY();
                        c.updateLeftPosition(30, freeYPositions.remove(freeYPositions.size()-1));
                        freeYPositions.add(newone);
                        Collections.sort(freeYPositions);
                    }
                }
                else {
                    break;
                }
            }
        }
        //Inventory, if existing
        if(inventoryUI != null) {
            inventoryUI.update(ship.getInventory(), equippedWeapons);
        }
        //Energy
        energy.energyUpdate(ship.getEnergy());
        //Money
        money.changeOverallAmount(ship.getCoins());
        //Missiles
        missiles.changeOverallAmount(ship.getMissiles());
        //Fuel
        fuel.changeOverallAmount(ship.getFuel());
        //FTLCharge
        ftlChargeUI.changeOverallAmount(ship.getFTLCharge());
    }

    /**
     * open the inventory of this ship
     * called by ship button
     */
    public void openInventory() {
        if(inventoryUI == null){
            inventoryUI = new InventoryUI(main, game.loadCrew(), game.loadWeapons(), game.loadEquippedWeapons(), game.loadFuel(), game.loadMissiles(), stage, this, font15, shipType);
        }
    }

    /**
     * open the map
     * called by move button
     */
    public void openMap() {
        if(mapUI == null){
            mapUI = new MapUI(main, stage, this);
        }
    }

    /**
     * remove the inventory ui
     */
    public void deleteInventory() {
        inventoryUI = null;
    }

    /**
     * remove the map ui
     */
    public void deleteMap() {
        mapUI = null;
    }

    /**
     * create a pause menu
     */
    public void createPauseMenu() {
        game.createPauseMenu();
    }

    /**
     * travel to a planet
     *
     * send to gameplay, through that to travelcontroller, which declares whether this jump is possible
     *
     * if possible, travel to new planet (with execution of event etc)
     *
     * @param planet the target planet
     * @return whether travel valid
     */
    public boolean travel(Planet planet) {
        return game.travel(planet);
    }

    /**
     * reverse the texture of the crew button
     * @param crew the crew member
     */
    public void undoCrewButton(Crew crew) {
        this.crew.get(crew.getId()).undoCrewButton();
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
     * the player has chosen a new amount of energy
     * @param amount how much should be added
     * @param room the room/system to which to add energy
     */
    public void roomSystemEnergyAdded(Room room, int amount) {
        game.roomSystemEnergyAdded(room, amount);
    }

    /**
     * the player has chosen a new amount of energy
     * @param room the room/system from which to subtract energy
     * @param amount the amount ot be removed
     */
    public void roomSystemEnergyRemoved(Room room, int amount) {
        game.roomSystemEnergyRemoved(room, amount);
    }

    /**
     * change the amount of scrap/money displayed
     * @param amount by how much the amount is changed
     */
    public void changeAmountScrap(int amount) { money.changeAmount(amount);}

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
     * equip a weapon
     * (move from ship inventory to weapon system inventory)
     * @param weapon the weapon
     */
    public void equipWeapon(Weapon weapon) {
        game.equipWeapon(weapon);
    }

    /**
     * unequip a weapon
     * (move from weapon system inventory to ship inventory)
     * @param weapon the weapon
     */
    public void unequipWeapon(Weapon weapon) {game.unequipWeapon(weapon);}

    /**
     * a weapon was fired
     * called by gameplay screen after communication with controller
     * @param weapon the weapon that was fired
     */
    public void weaponFired(Weapon weapon) {
        for(RoomUI r : rooms.values()) {
            if(r instanceof WeaponUI) {
                ((WeaponUI) r).weaponShot(weapon);
            }
        }
    }

}
