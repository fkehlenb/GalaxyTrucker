package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;

import java.util.HashMap;

public class RewardsPage {

    private Texture background;

    private BitmapFont font;

    private Main main;

    private EventPage page;

    public RewardsPage(Main main, BitmapFont font) {
        this.main = main;
        this.font = font;

        background = new Texture("event/eventbackground.png");

        createPage();
    }

    private void createPage() {
        HashMap<Texture, GlyphLayout> hash = new HashMap<>();
        GlyphLayout g = new GlyphLayout();
        g.setText(font, Integer.toString(2));

        hash.put(new Texture("gameuis/top_missile.png"), g);
        hash.put(new Texture("gameuis/top_fuel.png"), g);
        hash.put(new Texture("gameuis/top_missile.png"), g);

        GlyphLayout f = new GlyphLayout();
        f.setText(font, "hallo");

        hash.put(new Texture("crew/barrage.png"), f);
        hash.put(new Texture("crew/default.png"), f);
        hash.put(new Texture("crew/killer.png"), f);

        hash.put(new Texture("shipsys/weapon_system/bomb.png"), f);
        hash.put(new Texture("shipsys/weapon_system/rocket.png"), f);
        hash.put(new Texture("shipsys/weapon_system/radio.png"), f);

        float px = Main.WIDTH/2f - background.getWidth()/2f;
        float py = Main.HEIGHT/2f - background.getHeight()/2f;
        float width = background.getWidth();
        float height = background.getHeight();

        page = new EventPage(main, hash, "Rewards", px, py, font, width, height);
    }

    public void render() {
        main.batch.begin();
        main.batch.draw(background, Main.WIDTH/2f - background.getWidth()/2f, Main.HEIGHT/2f - background.getHeight()/2f, background.getWidth(), background.getHeight());
        main.batch.end();
        page.render();
    }
}
