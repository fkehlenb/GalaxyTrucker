package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.*;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.NextRoundButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.GameOver;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.EnemyShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

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
     * Planet Textrue Filepath
     */
    private Texture planetTexture;

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
     * the stage for the room buttons
     */
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

    /**
     * the crew member that was chosen to move
     */
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
     * the button to move on to the next round in a combat
     */
    private NextRoundButton nextRoundButton;

    /**
     * a font used to draw text
     */
    private BitmapFont font15;

    private BitmapFont font25;

    /**
     * the battle controller
     */
    private BattleController battleController = BattleController.getInstance(null);


    /**
     * Constructor
     *
     * @param main - main class
     */
    public GamePlay(Main main) {
        this.main = main;
        //initialising the textures at the beginning
        background = new Texture("1080p.png");
        planetTexture = getPlanetTexture();
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

        params.size = 25;

        font25 = generator.generateFont(params);

        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(viewport, main.batch);
        pauseStage = new Stage(viewport, main.batch);
        tileStage = new Stage(viewport, main.batch);


        player = new ShipView(main, main.getClient().getMyShip(), stage, tileStage, main.getClient().getOverworld(), this, font15, font25);

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
        if (planetTexture != null){
            main.batch.draw(planetTexture,Main.WIDTH/2f,Main.HEIGHT/2f,planetTexture.getWidth(),planetTexture.getHeight());
        }



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
        planetTexture.dispose();
        player.disposeShipView();
        font15.dispose();
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
        //Gdx.input.setInputProcessor(stage);
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Pause-menu
            Gdx.input.setInputProcessor(pauseStage);
            createPauseMenu();
        }
        if(crewMoving && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || takingAim && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Gdx.input.setInputProcessor(stage);
            if(takingAim && chosenWeapon!= null) {
                player.weaponFired(chosenWeapon); //Not really, just to undo button change
            }
            crewMoving = false;
            takingAim = false;
        }
    }

    /**
     * new event ui
     */
    public void createEvent(PlanetEvent event) {
        eventGUI = new EventGUI(main, event, stage, this, font15);
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
        boolean success;
        if(ClientControllerCommunicator.getInstance(null).getClientShip().isInCombat()) {
            success = BattleController.getInstance(null).fleeFight(planet);
        }
        else {
            success = TravelController.getInstance(null).travel(planet); //Communicator can be null since already created, so never used
        }
            planet = PlanetEventController.getInstance(null).getClientShip().getPlanet();
        if(success) {
            createEvent(planet.getEvent());
            if(planet.getEvent() == PlanetEvent.SHOP) {
                background = new Texture("1080p.png");
                createShop(planet.getTrader());
            }
            else if(planet.getEvent().equals(PlanetEvent.COMBAT)) {
                if (planet.getShips().size() > 1){
                    background = new Texture("1080p.png");
                    battleController.setOpponent(planet.getShips().get(0));
                    createEnemy();
                    createRoundButton();
                }
            }
            else if(PlanetEventController.getInstance(null).getClientShip().getPlanet().getEvent() == PlanetEvent.NEBULA || PlanetEventController.getInstance(null).getClientShip().getPlanet().getEvent() == PlanetEvent.METEORSHOWER){
                background = new Texture(PlanetEventController.getInstance(null).getClientShip().getPlanet().getPlanetTexture());
                planetTexture = null;
            }
            else{
                background = new Texture("1080p.png");
                planetTexture = getPlanetTexture();
            }
        }
        return success;
    }

    /**
     * create the enemy ui, if there is an enemy
     */
    private void createEnemy(){
        if(enemy == null) {
            java.lang.System.out.println("--- Rendering ship ---");
            enemy = new EnemyShip(main, battleController.getOpponent(), stage, this, tileStage);
            player.update(ClientControllerCommunicator.getInstance(null).getClientShip());
        }
    }

    /**
     *  remove the enemey ui after fight
     */
    private void removeEnemy() {
        enemy.disposeShipView();
        enemy = null;
    }

    /**
     * create next round button
     */
    private void createRoundButton() {
        nextRoundButton = new NextRoundButton(Main.WIDTH/(2.5f), Main.HEIGHT - (Main.HEIGHT/(8f)), 248, 50, this);
        stage.addActor(nextRoundButton);
    }

    /**
     * remove the round button after fight
     */
    private void removeRoundButton() {
        nextRoundButton.remove();
        nextRoundButton = null;
    }

    /**
     * move on to the next fight round
     * called by next round button
     * calling the battle controller
     */
    public void nextFightRound() {
        boolean success = battleController.playMoves();
        if (success){
            boolean combatOver = battleController.fetchUpdatedData();
            player.update(ClientControllerCommunicator.getInstance(null).getClientShip());
            enemy.update(BattleController.getInstance(null).getOpponent());
            if (combatOver){
                if (battleController.combatOver()){
                    if (battleController.combatWon()){
                        // todo
                    }
                    else{
                        // todo
                    }
                    removeEnemy();
                    removeRoundButton();
                }
            }
        }
    }

    /**
     * gets the current position of the ship and returns the planet texture
     * @return the new planet texture
     */
    public Texture getPlanetTexture(){
        String planetTextureString = PlanetEventController.getInstance(null).getClientShip().getPlanet().getPlanetTexture();
        planetTexture = new Texture(planetTextureString);
        return planetTexture;
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
        Gdx.input.setInputProcessor(stage);
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
            crewMoving = false;
            chosenCrew = null;
        }
        else if(takingAim && chosenWeapon != null) {
            weaponShot(chosenWeapon, room);
            player.weaponFired(chosenWeapon);
            Gdx.input.setInputProcessor(stage);
            takingAim = false;
            chosenWeapon = null;
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
    private void weaponShot(Weapon weapon, Room room) {
        battleController.attack(weapon, room);
    } 

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
    public void roomSystemEnergyAdded(Room room, int amount) {
        boolean success = SystemController.getInstance(null).addEnergy((System) room, amount);
        if(success) {
            roomSystemEnergyUpdate(room, getNewSystemEnergyAmount(room));
        }
    }

    /**
     * get the new total system energy amount from the ship saved in the clientcontrollercommunicator
     * @param room the system whose energy status is queried
     * @return the total energy currently allocated to this system
     */
    private int getNewSystemEnergyAmount(Room room) {
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.getId() == room.getId()) {
                return ((System) r).getEnergy();
            }
        }
        return 0;
    }

    /**
     * the player has chosen a new amount of energy for a system
     * @param room the room
     * @param amount how much should be removed
     */
    public void roomSystemEnergyRemoved(Room room, int amount) {
        boolean success = SystemController.getInstance(null).removeEnergy((System) room, amount);
        if(success) {
            roomSystemEnergyUpdate(room, getNewSystemEnergyAmount(room));
        }
    }

    /**
     * the energy for a system is updated
     * @param amount the new total amount
     */
    private void roomSystemEnergyUpdate(Room room, int amount) {
        player.roomSystemEnergyUpdate(room, amount);
        energyStatusUpdate(ClientControllerCommunicator.getInstance(null).getClientShip().getEnergy());
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
        List<Room> rs = ClientControllerCommunicator.getInstance(null).getClientShip().getSystems();
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
        List<Weapon> weapons = new LinkedList<>(ClientControllerCommunicator.getInstance(null).getClientShip().getInventory());
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
        return ClientControllerCommunicator.getInstance(null).getClientShip().getMissiles();
    }

    /**
     * load the fuel of a ship
     * @return the amount of fuel
     */
    public int loadFuel() {
        return ClientControllerCommunicator.getInstance(null).getClientShip().getFuel();
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
