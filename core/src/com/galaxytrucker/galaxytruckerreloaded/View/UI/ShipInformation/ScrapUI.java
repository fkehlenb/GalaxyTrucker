package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class ScrapUI {

    /**
     * Scrap UI background
     */
    private Texture scrapBackground;

    /**
     * the amount of money
     */
    private int amount;

    /**
     * the main class with the sprite batch
     */
    private Main main;

    /**
     * Constructor
     *
     * @param main - main class used for sprite batch and camera
     * @param money the amount of money
     */
    public ScrapUI(Main main, int money) {
        this.main = main;
        amount = money;

        scrapBackground = new Texture("gameuis/top_scrap.png");
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(scrapBackground, 0, 0, 0, 0); //TODO xy, w, h
        main.batch.end();
    }

    /**
     * setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the ui
     */
    public void showScrapUI() {
    }

    /**
     * hide the ui
     */
    public void hideScrapUI() {
    }

    /**
     * Dispose of ui
     */
    public void disposeScrapUI() {
        scrapBackground.dispose();
    }

    /**
     * the amount of money is updated
     * @param amount the new amount
     */
    public void changeAmount(int amount) {
        this.amount = amount;
    }
}
