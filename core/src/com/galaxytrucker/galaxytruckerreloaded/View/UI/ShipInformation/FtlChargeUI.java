package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class FtlChargeUI {


        /**
         * fuel UI background
         */
        private Texture ftlChargeBackground;

        /**
         * the amount of ftlCharge
         */
        private int amount = 0;

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
                ftlChargeBackground = new Texture("buttons/balken/0_balken.png");
            } else if(amount <20){
                ftlChargeBackground = new Texture("buttons/balken/10_balken.png");
            } else if(amount <30){
                ftlChargeBackground = new Texture("buttons/balken/20_balken.png");
            } else if(amount <40){
                ftlChargeBackground = new Texture("buttons/balken/30_balken.png");
            } else if(amount <50){
                ftlChargeBackground = new Texture("buttons/balken/40_balken.png");
            } else if(amount <60){
                ftlChargeBackground = new Texture("buttons/balken/50_balken.png");
            } else if(amount <70){
                ftlChargeBackground = new Texture("buttons/balken/60_balken.png");
            } else if(amount <80){
                ftlChargeBackground = new Texture("buttons/balken/70_balken.png");
            } else if(amount <90){
                ftlChargeBackground = new Texture("buttons/balken/80_balken.png");
            } else if(amount <100){
                ftlChargeBackground = new Texture("buttons/balken/90_balken.png");
            } else {
                ftlChargeBackground = new Texture("buttons/balken/100_balken.png");
            }
        }

        /**
         * render
         * no stage stuff
         */
        public void render() {
            main.batch.begin();
            main.batch.draw(ftlChargeBackground, Main.WIDTH/(2.259f), Main.HEIGHT - Main.HEIGHT/(10f), Main.WIDTH/(19f), Main.HEIGHT/(25.12f));
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
        public void showFtlChargeUI() {
        }

        /**
         * hide the ui
         */
        public void hideFtlChargeUI() {
        }

        /**
         * Dispose of ui
         */
        public void disposeFtlChargeUI() {
            ftlChargeBackground.dispose();
        }

        /**
         * the amount of ftlCharge is updated
         * @param amount by how much the amount is changed
         */
        public void changeAmount(int amount) {
            this.amount += amount;
            if(amount <= 10){
                ftlChargeBackground = new Texture("buttons/balken/0_balken.png");
            } else if(amount <20){
                ftlChargeBackground = new Texture("buttons/balken/10_balken.png");
            } else if(amount <30){
                ftlChargeBackground = new Texture("buttons/balken/20_balken.png");
            } else if(amount <40){
                ftlChargeBackground = new Texture("buttons/balken/30_balken.png");
            } else if(amount <50){
                ftlChargeBackground = new Texture("buttons/balken/40_balken.png");
            } else if(amount <60){
                ftlChargeBackground = new Texture("buttons/balken/50_balken.png");
            } else if(amount <70){
                ftlChargeBackground = new Texture("buttons/balken/60_balken.png");
            } else if(amount <80){
                ftlChargeBackground = new Texture("buttons/balken/70_balken.png");
            } else if(amount <90){
                ftlChargeBackground = new Texture("buttons/balken/80_balken.png");
            } else if(amount <100){
                ftlChargeBackground = new Texture("buttons/balken/90_balken.png");
            } else {
                ftlChargeBackground = new Texture("buttons/balken/100_balken.png");
            }
        }

        /**
         * the amount of ftlCharge is updated
         * @param amount the new total amount
         */
        public void changeOverallAmount(int amount) {
            this.amount = amount;
            if(amount <= 10){
                ftlChargeBackground = new Texture("buttons/balken/0_balken.png");
            } else if(amount <20){
                ftlChargeBackground = new Texture("buttons/balken/10_balken.png");
            } else if(amount <30){
                ftlChargeBackground = new Texture("buttons/balken/20_balken.png");
            } else if(amount <40){
                ftlChargeBackground = new Texture("buttons/balken/30_balken.png");
            } else if(amount <50){
                ftlChargeBackground = new Texture("buttons/balken/40_balken.png");
            } else if(amount <60){
                ftlChargeBackground = new Texture("buttons/balken/50_balken.png");
            } else if(amount <70){
                ftlChargeBackground = new Texture("buttons/balken/60_balken.png");
            } else if(amount <80){
                ftlChargeBackground = new Texture("buttons/balken/70_balken.png");
            } else if(amount <90){
                ftlChargeBackground = new Texture("buttons/balken/80_balken.png");
            } else if(amount <100){
                ftlChargeBackground = new Texture("buttons/balken/90_balken.png");
            } else {
                ftlChargeBackground = new Texture("buttons/balken/100_balken.png");
            }
        }

}