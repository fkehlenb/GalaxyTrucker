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
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.LaserBlaster;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.GameOver;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;
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
     * single Player active
     */
    private boolean singlePlayer;

    /**
     * the map for this game
     */
    private Overworld map;

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

    private Viewport viewport;

    /**
     * Pausemenu Object.
     */
    private PauseMenu pauseMenu;

    private PauseMenuUI pauseMenuUI;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public GamePlay(Main main) {
        this.main = main;
        background = new Texture("1080p.png");

        pauseMenu = new PauseMenu(main);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        player = new ShipView(main, fakeShip(), stage, fakeMap(), this); //TODO wie schiff aus controller?

        Gdx.input.setInputProcessor(stage);
    }

    //TODO only for testing
    public Ship fakeShip(){
        Trader trader = new Trader();
        //Planet planet = new Planet("planet", 125f, 125f, PlanetEvent.SHOP, false, new LinkedList<>(), trader);
        Planet planet = new Planet();
        List<Room> rooms = new LinkedList<>();
        rooms.add(new BlankRoom(12));
        rooms.add(new BlankRoom(13));
        rooms.add(new BlankRoom(14));
        List<Weapon> weapons = new LinkedList<>();
        weapons.add(new LaserBlaster("karl"));
        weapons.add(new LaserBlaster("test"));
        return new Ship(1, "aaron", 100, 49, 5, 5, 7, 9, 23, 6f, planet, 6, 6, rooms, weapons, false);
    }

    /**
     * später durch laden aus controller ersetzen
     */
    public Overworld fakeMap() {
        HashMap<float[], Planet> hmap = new HashMap<>();
        Planet sp = new Planet("planet1", (float) 78, (float) 199, PlanetEvent.SHOP, new LinkedList<Ship>());
        float[] f = new float[2];
        f[0] = 78;
        f[1] = 199;
        hmap.put(f, sp);
        Planet sp1 = new Planet("planet2", (float) 200, (float) 154, PlanetEvent.COMBAT, new LinkedList<Ship>());
        float[] f1 = new float[2];
        f1[0] = 200;
        f1[1] = 154;
        hmap.put(f1, sp1);
        Overworld res = new Overworld(2);
        res.setPlanetMap(hmap);
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

        if(shopUI != null) { shopUI.render(); }
        else if(eventGUI != null) { eventGUI.render(); }
        else if(gameOverUI != null) { gameOverUI.render(); }
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
        if(optionUI != null) { optionUI.disposeOptionsUI(); }
        if(pauseMenuUI != null) { pauseMenuUI.disposePauseMenuUI(); }
        stage.dispose();
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        System.out.println("touched");
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("Where");
            createPauseMenu();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.P)) {
            //paused
        }
    }

    /**
     * new event ui
     */
    public void createEvent(PlanetEvent event) {
        eventGUI = new EventGUI(main, event, stage, this);
    }

    public void deleteEvent() {
        eventGUI = null;
    }

    /**
     * shop ui pops up
     */
    public void createShop() {
        shopUI = new ShopUI(main, stage, this);
    }

    public void deleteShop() {
        shopUI = null;
    }

    public void buy(int item) { //TODO still not sure how that is gonna work
        //call controller
    }

    public void createGameOver(boolean won) {
        gameOverUI = new GameOver(main, stage, won, this);
    }

    public void deleteGameOver() {
        gameOverUI = null;
    }

    /**
     * opens in game options
     * called by controller
     */
    public void createPauseMenu() {
        if(pauseMenuUI == null) {
            pauseMenuUI = new PauseMenuUI(main, stage, this);
        }
        //TODO controller sagen dass spiel "pausiert"?
    }

    public void deletePauseMenu() {
        pauseMenuUI = null;
    }

    public void createOptions() {
        optionUI = new OptionUI(main, stage, this);
    }

    public void deleteOptions() {
        optionUI = null;
    }

    public void createShip() {

    }

    public void deleteShip() {
        player = null;
    }

    /**
     * the crew moved from one room to the other
     * @param crew the id of the crew member
     * @param room the new room
     */
    public void crewMoved(int crew, Room room) {} //call controller, ask for permission
    public void crewHealth(int crew, int health) { player.crewHealth(crew, health); }

    public void energyStatusUpdate(int energy) { player.energyStatusUpdate(energy); }

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

    public void roomSystemStatusUpdate(int id, int amount) {
        player.roomSystemStatusUpdate(id, amount);
    }

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

    public List<Crew> loadCrew(int shipId) {  //TODO call controller
        List<Crew> result = new LinkedList<>();
        int[] arr = new int[4];
        arr[0] = 3;
        arr[1] = 7;
        arr[2] = 8;
        arr[3] = 6;
        Crew c1 = new Crew(1, "ana", 7, 10, arr);
        result.add(c1);
        Crew c2 = new Crew(2, "battle", 8, 10, arr);
        result.add(c2);
        return result;
    }
    public List<Weapon> loadWeapons(int shipId) {
        List<Weapon> weapons = new LinkedList<>();
        weapons.add(new LaserBlaster("karl"));
        weapons.add(new LaserBlaster("test"));
        return weapons;
    }
    public int loadMissiles(int shipId) { return 0; }
    public int loadFuel(int shipId) { return 0; }

    @Override
    public void pause() {
        pauseMenu.render(1);
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

}
