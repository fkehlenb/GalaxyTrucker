package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Audio;
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
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.AudioController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.BattleController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.CrewController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TravelController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.GameOver;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.EnemyShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

import java.util.LinkedList;
import java.util.List;

import static com.badlogic.gdx.Gdx.audio;

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

    private Stage tileStage;

    /**
     * the viewport
     */
    private Viewport viewport;

    /**
     * the pause menu ui, if existing
     */
    private PauseMenuUI pauseMenuUI;

    /**
     * whether or not a crew member was chosen to be moved
     */
    private boolean crewMoving = false;

    private Crew chosenCrew;

    /**
     * whether the player has chosen a weapon and is now in the process of choosing a room as a target
     */
    private boolean takingAim = false;

    /**
     * the weapon a player has chosen, if not null
     */
    private Weapon chosenWeapon;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public GamePlay(Main main) {
        this.main = main;
        background = new Texture("1080p.png");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport, main.batch);
        pauseStage = new Stage(viewport, main.batch);
        tileStage = new Stage(viewport, main.batch);


        player = new ShipView(main, main.getClient().getMyShip(), stage, tileStage, main.getClient().getOverworld(), this);

        createEnemy(); //TODO weg, is nur zum testen der enemy anzeige

        Gdx.input.setInputProcessor(stage);
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
        main.batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        main.batch.end();

        if(enemy == null) {
            player.render();
        }
        else {
            player.render1();
            enemy.render1();
            tileStage.draw();
            player.render2();
            enemy.render2();
        }

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
        if(enemy != null) { enemy.disposeShipView(); }
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
        if(crewMoving && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || takingAim && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Gdx.input.setInputProcessor(stage);
            crewMoving = false;
            takingAim = false;
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
        boolean success = TravelController.getInstance(null).travel(planet); //Communicator can be null since already created, so never used
        if(success) {
            createEvent(planet.getEvent());
            if(planet.getEvent() == PlanetEvent.SHOP) {
                createShop(planet.getTrader());
            }
            else if(planet.getEvent() == PlanetEvent.COMBAT) { //TODO wer erstellt wo den controller?
                createEnemy();
            }
        }
        return success;
    }

    /**
     * create the enemy ui, if there is an enemy
     */
    private void createEnemy() {
        if(enemy == null) {
            enemy = new EnemyShip(main, main.getClient().getMyShip(), stage, this, tileStage); //TODO hier das ship aus battleController nehmen
        }
    }

    /**
     * remove the enemy ui again
     */
    public void deleteEnemy() {
        enemy = null;
    }

    /**
     * shop ui pops up
     */
    private void createShop(Trader trader) {
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
        pauseStage = new Stage(viewport);
        Gdx.input.setInputProcessor(pauseStage);
        pauseMenuUI = new PauseMenuUI(main, pauseStage, this);
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
    private void crewMoved(Crew crew, Room room) {
        boolean success = CrewController.getInstance(null).moveCrewToRoom(crew, room);
        if(success) {
            player.crewMoved(crew, room);
        }
    }

    /**
     * a crew member was chosen to be moved.
     * @param crew the crew member
     */
    public void crewMoving(Crew crew) {
        crewMoving = !crewMoving;
        if(crewMoving) {
            Gdx.input.setInputProcessor(tileStage);
            chosenCrew = crew;
        }
        else {
            Gdx.input.setInputProcessor(stage);
            chosenCrew = null;
        }
    }

    /**
     * a room was chosen with the tile buttons
     * @param room the room that was chosen
     */
    public void roomChosen(Room room) {
        if(crewMoving && chosenCrew != null) {
            crewMoved(chosenCrew, room);
            Gdx.input.setInputProcessor(stage);
        }
    }

    /**
     * a room on the enemy ship was chosen as a target for a weapon
     * TODO: problem: man kann bisher nicht auf sein eigenes schiff feuern
     * @param room the room that was chosen
     */
    public void roomChosenAsTarget(Room room) {
        if(takingAim && chosenWeapon != null) {
            weaponShot(chosenWeapon, room);
            Gdx.input.setInputProcessor(stage);
        }
    }

    /**
     * a weapon from the bottom left corner is activated. next, a room needs to be chosen as a target
     * @param weapon the weapon that was chosen
     */
    public void weaponActivated(Weapon weapon) {
        takingAim = !takingAim;
        if(takingAim) {
            Gdx.input.setInputProcessor(tileStage);
            chosenWeapon = weapon;
        }
        else {
            Gdx.input.setInputProcessor(stage);
            chosenWeapon = null;
        }
    }

    /**
     * the player has chosen a weapon and a room
     * call to controller, add id of the enemyship
     *
     * @param weapon the weapon
     * @param room the room
     */
    private void weaponShot(Weapon weapon, Room room) {} //TODO call controller

    /**
     * update the health of a crew member
     * @param crew the crew member
     * @param health the new health
     */
    public void crewHealth(Crew crew, int health) { player.crewHealth(crew, health); }

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
    public void roomSystemEnergyChosen(Room room, int amount) {
        //TODO: call controller
    }

    /**
     * the energy for a system is updated
     * @param amount the new total amount
     */
    public void roomSystemEnergyUpdate(Room room, int amount) {
        player.roomSystemEnergyUpdate(room, amount);
    }

    /**
     * update the status of a system
     * @param room the system
     * @param amount the new status
     */
    public void roomSystemStatusUpdate(Room room, int amount) {
        player.roomSystemStatusUpdate(room, amount);
    }

    /**
     * change the amount of scrap
     * @param amount the new amount
     */
    public void changeAmountScrap(int amount) {
        player.changeAmountScrap(amount);
    }

    /**
     * load the crew of a ship
     * @return the crew members
     */
    public List<Crew> loadCrew() {
        List<Crew> crew = new LinkedList<>();
        List<Room> rs = main.getClient().getMyShip().getSystems();
        for(Room r : rs) {
            crew.addAll(r.getCrew());
        }
        return crew;
    }

    /**
     * load the weapons of a ship
     * @return the weapons of the ship in a list
     */
    public List<Weapon> loadWeapons() {
        List<Weapon> weapons = new LinkedList<>(main.getClient().getMyShip().getInventory());
        for(Room r : main.getClient().getMyShip().getSystems()) {
            if(r instanceof System) {
                if(((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                    weapons.addAll(((System) r).getShipWeapons());
                }
            }
        }
        //BIS HIER LÖSCHEN
        return weapons;
    }

    /**
     * load the missiles of a ship
     * @return the amount of missiles
     */
    public int loadMissiles() {
        return main.getClient().getMyShip().getMissiles();
    }

    /**
     * load the fuel of a ship
     * @return the amount of fuel
     */
    public int loadFuel() {
        return main.getClient().getMyShip().getFuel();
    }

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
