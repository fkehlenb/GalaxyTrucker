package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.AutofireButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MoveButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShipButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.*;

public class ShipView extends AbstractShip {

    /**
     * the uis of all the crew members
     */
    private List<CrewUI> crew;

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
     * the weapon autofire button
     */
    private AutofireButton weaponAutofire;

    /**
     * the background texture of the ship
     */
    private Texture shipBackground;


    /**
     * the general background for the weapon display in the bottom left corner next to the energy status display
     */
    private Texture weaponGeneralBackground;

    /**
     * for display in the upper left corner
     */
    private Texture statusTexture;

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

    /**
     * Dispose of ship
     */
    public void disposeShipView() {
    }

    /**
     * update of the hull status (hp)
     *
     * @param hpvalue new status
     */
    @Override
    public void hullStatusUpdate(int hpvalue) {

    }

    /**
     * shield status update
     *
     * @param shieldvalue new status
     */
    @Override
    public void shieldStatusUpdate(int shieldvalue) {

    }

    /**
     * open the inventory of this ship
     * called by ship button
     */
    public void openInventory() {

    }

    /**
     * open the map
     * called by move button
     */
    public void openMap() {

    }

    /**
     * autofire
     * called by autofire button
     */
    public void autofire() {

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
     * Constructor
     * TODO methods to access all shipinformation stuff
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main, Ship ship) {
        super(main, ship);
    }
}
