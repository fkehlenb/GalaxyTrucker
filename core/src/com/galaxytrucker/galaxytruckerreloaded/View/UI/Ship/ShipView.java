package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.AutofireButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MoveButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShipButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Map.MapUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.*;

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

    /**
     * Constructor
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main, Ship ship, Stage stage, Overworld map, GamePlay game) {
        super(main, ship, stage, game);
        //java.util.List crew = ship.get ??

        this.map = map;

        rooms = new HashMap<>();
        List<Room> existingRooms = ship.getSystems();
        for(Room r : existingRooms) {
            //TODO wie system das zu raum gehört? dann sys id als key, roomui als value
        }

        moveButton = new MoveButton(850, main.HEIGHT - 90, 150, 92, this);
        inventory = new ShipButton(750,main.HEIGHT - 80, 50, 92, this);

        money = new ScrapUI(main, ship.getCoins());
        hull = new HullUI(main, ship.getHp());
        energy = new EnergyUI(main, ship.getEnergy());

        if(main.getClient().getMyShip().getShipType() == ShipType.DEFAULT) {
            shipBackground = new Texture("ship/kestral/kestral_base.png"); //TODO scaling richtig
            shipRoomBackground = new Texture("ship/kestral/kestral_floor.png");
        }
        else if(main.getClient().getMyShip().getShipType() == ShipType.KILLER) {
            shipBackground = new Texture("ship/anaerobic/an2base.png");
            shipRoomBackground = new Texture("ship/anaerobic/an2floor.png");
        }
        else if(main.getClient().getMyShip().getShipType() == ShipType.BARRAGE) {
            shipBackground = new Texture("ship/fed/fed_cruiser_2_base.png");
            shipRoomBackground = new Texture("ship/fed/fed_cruiser_floor.png");
        }
        weaponGeneralBackground = new Texture("shipsys/weapon/generalbox.png");

        stage.addActor(inventory);
        stage.addActor(moveButton);
    }

    /**
     * renders the ship
     * no stage stuff!! only textures
     */
    @Override
    public void render() {

        main.batch.begin();
        main.batch.draw(shipBackground, main.WIDTH -1730, main.HEIGHT/2 - shipBackground.getHeight()/2 - 200, 1000, 1000);
        main.batch.draw(shipRoomBackground, main.WIDTH -1500, main.HEIGHT/2 - shipRoomBackground.getHeight()/2 - 100, 550, 550);
        main.batch.draw(weaponGeneralBackground, 700, 100, 328, 90);
        main.batch.end();

        money.render();
        hull.render();
        energy.render();

        if(inventoryUI != null) {
            inventoryUI.render();
        }
        else if(mapUI != null) {
            mapUI.render();
        }

        for(RoomUI r : rooms.values()) {
            r.render();
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
            inventoryUI = new InventoryUI(main, game.loadCrew(id), game.loadWeapons(id), game.loadFuel(id), game.loadMissiles(id), stage, this);
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
     * @param crewid the id of the crew member
     * @param room the new room
     */
    public void crewMoved(int crewid, Room room) {
        game.crewMoved(crewid, room);
    }

    /**
     * crew member health update. called by game
     * @param crewid the crew member
     * @param health the new health
     */
    public void crewHealth(int crewid, int health) {
        if(health == 0) {
            crew.get(crewid).crewDied();
        }
        else {
            crew.get(crewid).statusUpdate(health);
        }
    }

    public void energyStatusUpdate(int status) {
        energy.energyUpdate(status);
    }

    /**
     * update the energy of a system in a room
     * @param amount the new total amount
     */
    public void roomSystemEnergyUpdate(int sysid, int amount) {
        rooms.get(sysid).systemEnergyUpdate(amount);
    }

    /**
     * update the status of a system in a room
     * @param sysid the system id
     * @param amount the new status
     */
    public void roomSystemStatusUpdate(int sysid, int amount) {
        rooms.get(sysid).systemStatusUpdate(amount);
    }

    /**
     * the player has chosen a new amount of energy
     * @param amount how much should be subtracted/added
     */
    public void roomSystemEnergyChosen(int id, int amount) {
        game.roomSystemEnergyChosen(id, amount);
    }

    /**
     * change the amount of scrap/money displayed
     * @param amount by how much the amount is changed
     */
    public void changeAmountScrap(int amount) { money.changeAmount(amount);}

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
