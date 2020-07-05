package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.BlankRoom;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.LaserBlaster;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.GameOver;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.EnemyShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Main game screen
 */
public class GamePlay implements Screen {

    /**
     * Background texture
     */
    private Texture background;

    /**
     * Looping music
     */
    private Music music;

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * ship of the player
     */
    private ShipView player;

    /**
     * the enemy, if existing
     */
    private EnemyShip enemy;

    /**
     * the shop, if existing
     */
    private ShopUI shopUI;

    /**
     * the event ui if existing
     */
    private EventGUI eventGUI;

    /***
     * the game over ui, if existing
     */
    private GameOver gameOverUI;

    /**
     * ingame general settings
     */
    private GeneralUI generalUI;

    /**
     * ingame Video settings
     */
    private VideoUI videoUI;

    /**
     * audio settings ui
     */
    private AudioUI audioUI;

    /**
     * credits ui
     */
    private CreditsUI creditsUI;

    /**
     * control settings ui
     */
    private ControlUI controlUI;

    /**
     * the ingame options ui, if existing
     */
    private OptionUI optionUI;

    /**
     * the main game class
     */
    private Main main;

    /**
     * the stage for the buttons
     */
    public Stage stage;

    /**
     * the stage for the buttons of the pause menu
     */
    private Stage pauseStage;

    /**
     * the viewport
     */
    private Viewport viewport;

    /**
     * the pause menu ui, if existing
     */
    private PauseMenuUI pauseMenuUI;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public GamePlay(Main main) {
        this.main = main;
        background = new Texture("1080p.png");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);
        pauseStage = new Stage(viewport);

        player = new ShipView(main, fakeShip(), stage, fakeMap(), this); //TODO wie schiff aus controller?

        Gdx.input.setInputProcessor(stage);
    }

    //TODO only for testing
    public Ship fakeShip(){
        Trader trader = new Trader();
        //Planet planet = new Planet("planet", 125f, 125f, PlanetEvent.SHOP, false, new LinkedList<>(), trader);
        Planet planet = new Planet();
        List<Room> rooms = new LinkedList<>();
        rooms.add(new BlankRoom());
        rooms.add(new BlankRoom());
        rooms.add(new BlankRoom());
        List<Weapon> weapons = new LinkedList<>();
        weapons.add(new LaserBlaster("karl"));
        weapons.add(new LaserBlaster("test"));
        return new Ship(1, "aaron", ShipType.DEFAULT, 100, 49, 5, 5, 7, 9, 23, 6f, planet, 6, 6, rooms, weapons, false);
    }

    /**
     * später durch laden aus controller ersetzen
     */
    public Overworld fakeMap() {
        Overworld res = new Overworld(2, 1, "aaron");
        HashMap<String, Planet> hmap = new HashMap<>();
        Planet sp = new Planet(0, "planet1", (float) 78, (float) 199, PlanetEvent.SHOP, new LinkedList<Ship>());
        String f = "78, 199";
        hmap.put(f, sp);
        Planet sp1 = new Planet(1, "planet2", (float) 200, (float) 154, PlanetEvent.COMBAT, new LinkedList<Ship>());
        String f1 = "200, 154";
        hmap.put(f1, sp1);
        //TODO
        //res.setPlanetMap(hmap);
        res.setStartPlanet(sp);

        return res;
    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        updateInput();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        main.batch.end();

        player.render();

        if(eventGUI != null) { eventGUI.render(); }
        else if(shopUI != null) { shopUI.render(); }
        else if(gameOverUI != null) { gameOverUI.render(); }
        else if(videoUI != null) { videoUI.render(); }
        else if(generalUI != null) { generalUI.render(); }
        else if(optionUI != null) { optionUI.render(); }
        else if(pauseMenuUI != null) { pauseMenuUI.render(); }

        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        player.disposeShipView();
        if(shopUI != null) { shopUI.disposeShopUI(); }
        if(eventGUI != null) { eventGUI.disposeEventGUI(); }
        if(gameOverUI != null) { gameOverUI.disposeGameoverUI(); }
        if(videoUI != null) { videoUI.disposeVideoUI(); }
        if(generalUI != null) { generalUI.disposeGeneralUI(); }
        if(optionUI != null) { optionUI.disposeOptionsUI(); }
        if(pauseMenuUI != null) { pauseMenuUI.disposePauseMenuUI(); }
        stage.dispose();
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Pause-menu
            Gdx.input.setInputProcessor(pauseStage);
            createPauseMenu();
        }
    }

    /**
     * new event ui
     */
    public void createEvent(PlanetEvent event) {
        eventGUI = new EventGUI(main, event, stage, this);
    }

    /**
     * remove the event ui from the screen
     */
    public void deleteEvent() {
        eventGUI = null;
    }

    /**
     * travel to a planet
     * call to travelController
     *
     * if valid request, travel to new planet with execution of event etc
     * if not, error message?
     *
     * @param planet the target planet
     * @return whether or not it is a valid request
     */
    public boolean travel(Planet planet) {
        boolean success = true; //TODO call to controller
        if(success) {
            createEvent(planet.getEvent());
            if(planet.getEvent() == PlanetEvent.SHOP) {
                createShop(planet.getTrader());
            }
        }
        return success;
    }

    /**
     * shop ui pops up
     */
    public void createShop(Trader trader) {
        if(shopUI == null) {
            shopUI = new ShopUI(main, stage, this, trader, null, 0);
        }
    } //TODO

    /**
     * remove the shop
     */
    public void deleteShop() {
        shopUI = null;
    }

    /**
     * buy a weapon from the trader
     * call to controller
     * @param weapon the weapon
     */
    public boolean buyWeapon(Weapon weapon) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(-(weapon.getPrice().get(weapon.getWeaponLevel())));
        }
        return success;
    }

    /**
     * buy a crew member from the trader
     * call to controller
     * @param crew the crew member
     */
    public boolean buyCrew(Crew crew) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(-(crew.getPrice()));
        }
        return success;
    }

    /**
     * buy fuel from the trader
     * call to controller
     * @param amount the amount of fuel
     */
    public boolean buyFuel(int amount) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(-(5*amount)); //TODO festpreis
        }
        return success;
    }

    /**
     * buy missiles from the trader
     * call to controller
     * @param amount the amount of missiles
     */
    public boolean buyMissiles(int amount) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(-(5*amount)); //TODO festpreis
        }
        return success;
    }

    /**
     * buy hp from the trader
     * call to controller
     * @param amount the amount of hp
     */
    public boolean buyHp(int amount) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(-(5*amount)); //TODO festpreis
        }
        return success;
    }

    /**
     * sell missiles to the trader
     * @param amount the amount of missiles
     * @return successfull? call to controller
     */
    public boolean sellMissiles(int amount) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(5*amount); //TODO festpreis
        }
        return success;
    }

    /**
     * sell weapon to trader
     * @param weapon the weapon
     * @return successfull? call to controller
     */
    public boolean sellWeapon(Weapon weapon) {
        boolean success = false; //TODO controller
        if(success) {
            player.changeAmountScrap(weapon.getPrice().get(weapon.getWeaponLevel()));
        }
        return success;
    }

    /**
     * create a game over ui if it does not yet exist
     * @param won whether the game was won
     */
    public void createGameOver(boolean won) {
        if(gameOverUI == null) {
            gameOverUI = new GameOver(main, stage, won, this);
        }
    }

    /**
     * delete the game over ui
     */
    public void deleteGameOver() {
        gameOverUI = null;
    }

    /**
     * opens in game pause menu
     * called by controller
     */
    public void createPauseMenu() {
        if(pauseMenuUI == null) {
            pauseStage = new Stage(viewport);
            Gdx.input.setInputProcessor(pauseStage);
            pauseMenuUI = new PauseMenuUI(main, pauseStage, this);
        }
        //TODO controller sagen dass spiel "pausiert"?
    }

    /**
     * closes in game pause menu
     */
    public void deletePauseMenu() {
        pauseMenuUI = null;
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * opens in game options
     */
    public void createOptions() {
        if(optionUI == null) {
            optionUI = new OptionUI(main, pauseStage, this, null);
        }
    }

    /**
     * closes in game options
     */
    public void deleteOptions() {
        optionUI = null;
    }

    /**
     * opens in game general options
     */
    public void createGeneralUI() {
        if(generalUI == null) {
            generalUI = new GeneralUI(main, pauseStage, this, null);
        }
    }

    /**
     * closes in game general options
     */
    public void deleteGeneralUI() {
        generalUI = null;
    }

    /**
     * opens in game video options
     */
    public void createVideoUI() {
        if(videoUI == null) {
            videoUI = new VideoUI(main, pauseStage, this, null);
        }
    }

    /**
     * closes ingame video settings
     */
    public void deleteVideoUI() {
        videoUI = null;
    }

    /**
     * create audio option ui, if it does not yet exist
     */
    public void createAudioUI(){
        if(audioUI == null) {
            audioUI = new AudioUI(main, pauseStage, this, null);
        }
    }

    /**
     * delete the audio option ui
     */
    public void deleteAudioUI(){
        audioUI = null;
    }

    /**
     * create the credit ui, if it does not yet exist
     */
    public void createCreditUI(){
        if(creditsUI == null) {
            creditsUI = new CreditsUI(main, pauseStage, this, null);
        }
    }

    /**
     * delete the credit ui
     */
    public void deleteCreditUI(){
        creditsUI = null;
    }

    /**
     * create the control option ui, if it does not yet exist
     */
    public void createControlUI(){
        if(controlUI == null) {
            controlUI = new ControlUI(main, pauseStage, this, null);
        }
    }

    /**
     * delete the control ui
     */
    public void deleteControlUI(){
        controlUI = null;
    }

    /**
     * delete the ship
     */
    public void deleteShip() {
        player = null;
    }

    /**
     * the crew moved from one room to the other
     * @param crew the id of the crew member
     * @param room the new room
     */
    public void crewMoved(int crew, Room room) {} //call controller, ask for permission

    /**
     * update the health of a crew member
     * @param crew the crew member
     * @param health the new health
     */
    public void crewHealth(int crew, int health) { player.crewHealth(crew, health); }

    /**
     * update the energy status of the overall energy not yet assigned to a system
     * @param energy the new energy
     */
    public void energyStatusUpdate(int energy) { player.energyStatusUpdate(energy); }

    /**
     * update the status of the hull
     * @param status the new status
     */
    public void hullStatusUpdate(int status) { player.hullStatusUpdate(status); }

    /**
     * the player has chosen a new amount of energy for a system
     * @param amount how much should be subtracted/added
     */
    public void roomSystemEnergyChosen(int id, int amount) {
        //call controller
    }

    /**
     * the energy for a system is updated
     * @param amount the new total amount
     */
    public void roomSystemEnergyUpdate(int id, int amount) {
        player.roomSystemEnergyUpdate(id, amount);
    }

    /**
     * update the status of a system
     * @param id the id of the system
     * @param amount the new status
     */
    public void roomSystemStatusUpdate(int id, int amount) {
        player.roomSystemStatusUpdate(id, amount);
    }

    /**
     * change the amount of scrap
     * @param amount the new amount
     */
    public void changeAmountScrap(int amount) {
        player.changeAmountScrap(amount);
    }

    /**
     * the player has chosen a weapon and a room
     * call to controller, add id of the enemyship
     *
     * @param id the weapon id
     * @param room the room
     */
    public void weaponShot(int id, Room room) {} //call controller

    /**
     * load the crew of a ship
     * @param shipId the ship id
     * @return the crew members
     */
    public List<Crew> loadCrew(int shipId) {  //TODO call controller
        List<Crew> result = new LinkedList<>();
        Crew c1 = new Crew(1, "ana", 7, 10);
        result.add(c1);
        Crew c2 = new Crew(2, "battle", 8, 10);
        result.add(c2);
        return result;
    }

    /**
     * load the weapons of a ship
     * @param shipId the ship id
     * @return the weapons of the ship in a list
     */
    public List<Weapon> loadWeapons(int shipId) {
        List<Weapon> weapons = new LinkedList<>();
        weapons.add(new LaserBlaster("karl"));
        weapons.add(new LaserBlaster("test"));
        return weapons;
    }

    /**
     * load the missiles of a ship
     * @param shipId the id of the ship
     * @return the amount of missiles
     */
    public int loadMissiles(int shipId) { return 0; }

    /**
     * load the fuel of a ship
     * @param shipId the id of the ship
     * @return the amount of fuel
     */
    public int loadFuel(int shipId) { return 0; }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Change the background
     *
     * @param background - the new background
     */
    public void setBackground(Texture background) {
        this.background = background;
    }

    /**
     * Gets the current open OptionUI
     * @return current OptionUI
     */
    public OptionUI getOptionUI() {
        return optionUI;
    }

    /**
     * Gets the current PauseMenu.
     * @return current PauseMenuUI
     */
    public PauseMenuUI getPauseMenuUI() {
        return pauseMenuUI;
    }
}
