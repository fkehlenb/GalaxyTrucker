package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * ui for displaying the ftl charge needed for traveling
 */
public class FtlChargeUI {

    /**
     * fuel UI background
     */
    private Texture ftlChargeBackground;

    /**
     * the amount of ftlCharge
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
     * @param ftlCharge the amount of ftlCharge
     */
    public FtlChargeUI(Main main, int ftlCharge) {
        this.main = main;
        this.amount = ftlCharge;

        if(amount <= 10){
            ftlChargeBackground = new Texture("buttons/Balken/0_balken.png");
        } else if(amount <20){
            ftlChargeBackground = new Texture("buttons/Balken/10_balken.png");
        } else if(amount <30){
            ftlChargeBackground = new Texture("buttons/Balken/20_balken.png");
        } else if(amount <40){
            ftlChargeBackground = new Texture("buttons/Balken/30_balken.png");
        } else if(amount <50){
            ftlChargeBackground = new Texture("buttons/Balken/40_balken.png");
        } else if(amount <60){
            ftlChargeBackground = new Texture("buttons/Balken/50_balken.png");
        } else if(amount <70){
            ftlChargeBackground = new Texture("buttons/Balken/60_balken.png");
        } else if(amount <80){
            ftlChargeBackground = new Texture("buttons/Balken/70_balken.png");
        } else if(amount <90){
            ftlChargeBackground = new Texture("buttons/Balken/80_balken.png");
        } else if(amount <100){
            ftlChargeBackground = new Texture("buttons/Balken/90_balken.png");
        } else {
            ftlChargeBackground = new Texture("buttons/Balken/100_balken.png");
        }
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(ftlChargeBackground, Main.WIDTH/(2.6f), Main.HEIGHT - Main.HEIGHT/(8f)-20, Main.WIDTH/(19f), Main.HEIGHT/(25.12f));
        main.batch.end();
    }

    /**
     * Dispose of ui
     */
    public void disposeFtlChargeUI() {
        ftlChargeBackground.dispose();
    }

    /**
     * the amount of ftlCharge is updated
     * @param amount the new total amount
     */
    public void changeOverallAmount(int amount) {
        this.amount = amount;
        if(amount <= 10){
            ftlChargeBackground = new Texture("buttons/Balken/0_balken.png");
        } else if(amount <20){
            ftlChargeBackground = new Texture("buttons/Balken/10_balken.png");
        } else if(amount <30){
            ftlChargeBackground = new Texture("buttons/Balken/20_balken.png");
        } else if(amount <40){
            ftlChargeBackground = new Texture("buttons/Balken/30_balken.png");
        } else if(amount <50){
            ftlChargeBackground = new Texture("buttons/Balken/40_balken.png");
        } else if(amount <60){
            ftlChargeBackground = new Texture("buttons/Balken/50_balken.png");
        } else if(amount <70){
            ftlChargeBackground = new Texture("buttons/Balken/60_balken.png");
        } else if(amount <80){
            ftlChargeBackground = new Texture("buttons/Balken/70_balken.png");
        } else if(amount <90){
            ftlChargeBackground = new Texture("buttons/Balken/80_balken.png");
        } else if(amount <100){
            ftlChargeBackground = new Texture("buttons/Balken/90_balken.png");
        } else {
            ftlChargeBackground = new Texture("buttons/Balken/100_balken.png");
        }
    }

}
