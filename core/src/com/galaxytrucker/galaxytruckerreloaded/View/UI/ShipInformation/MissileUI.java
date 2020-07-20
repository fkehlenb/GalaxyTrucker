package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class MissileUI {


        /**
         * Missile UI background
         */
        private Texture missileBackground;

        /**
         * the amount of missiles
         */
        private int amount = 0;

        /**
         * the main class with the sprite batch
         */
        private Main main;

        private BitmapFont font;

        private GlyphLayout glyph = new GlyphLayout();

        /**
         * Constructor
         *
         * @param main - main class used for sprite batch and camera
         * @param missiles the amount of missiles
         */
        public MissileUI(Main main, int missiles) {
            this.main = main;
            this.amount = missiles;

            missileBackground = new Texture("gameuis/top_missile.png");

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
            params.size = 25;

            font = generator.generateFont(params);

            glyph.setText(font, Integer.toString(amount));
        }

        /**
         * render
         * no stage stuff
         */
        public void render() {
            main.batch.begin();
            main.batch.draw(missileBackground, 600, Main.HEIGHT - 138, 147, 60);
            font.draw(main.batch, glyph, 600 + (147f/2) - glyph.width/2, (Main.HEIGHT - 88) - glyph.height/2);
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
        public void showMissileUI() {
        }

        /**
         * hide the ui
         */
        public void hideMissileUI() {
        }

        /**
         * Dispose of ui
         */
        public void disposeMissileUI() {
            missileBackground.dispose();
            font.dispose();
        }

        /**
         * the amount of missiles is updated
         * @param amount by how much the amount is changed
         */
        public void changeAmount(int amount) {
            this.amount += amount;
            glyph.setText(font, Integer.toString(this.amount));
        }
}