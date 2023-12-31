package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;

/**
 * to represent a crew member in the inventory
 */
public class InventoryCrewSlotUI extends InventorySlotUI {

    /**
     * the texture
     */
    private Texture crewTexture;

    /**
     * the textures to display the health status
     */
    private Texture healthStatus;

    /**
     * the surrounding box for the health bar
     */
    private Texture healthBox;

    /**
     * how many of the health status bars need to be rendered
     */
    private int currTexture;

    /**
     * the glyph layout for the name of the crew member
     */
    private GlyphLayout glyphName = new GlyphLayout();

    /**
     * the glyph layout for the engine stat of the crew member
     */
    private GlyphLayout glyphEngine = new GlyphLayout();

    /**
     * the glyph layout for the weapon stat of the crew member
     */
    private GlyphLayout glyphWeapon = new GlyphLayout();

    /**
     * the glyph layout for the shield stat of the crew member
     */
    private GlyphLayout glyphShield = new GlyphLayout();

    /**
     * the glyph layout for the repair stat of the crew member
     */
    private GlyphLayout glyphRepair = new GlyphLayout();

    /**
     * the glyph layout for the combat stat of the crew member
     */
    private GlyphLayout glyphCombat = new GlyphLayout();

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventoryCrewSlotUI(Main main, Crew crew, float x, float y, BitmapFont font, ShipType type) {
        super(main, x, y, font);

        glyphName.setText(font, crew.getName());
        List<Integer> stats = crew.getStats();
        glyphEngine.setText(font, "Engine: "+stats.get(0));
        glyphWeapon.setText(font, "Weapon: "+stats.get(1));
        glyphShield.setText(font, "Shield: "+stats.get(2));
        glyphRepair.setText(font, "Repair: "+stats.get(3));
        glyphCombat.setText(font, "Combat: "+stats.get(4));

        int health = crew.getHealth();
        int maxhealth = crew.getMaxhealth();
        currTexture = (int) (((float) health/maxhealth) * 10);

        healthStatus = new Texture("gameuis/energybar.png");
        healthBox = new Texture("crew/health_box.png");

        crewTexture = new Texture("crew/"+type.toString().toLowerCase()+".png");
    }

    /**
     * render
     * without stage stuff
     */
    public void render() {
        main.batch.begin();
        font.draw(main.batch, glyphName, posX+Main.WIDTH/(1920/72), posY + Main.HEIGHT/(1080/50));
        font.draw(main.batch, glyphEngine, posX+Main.WIDTH/(1920/72), posY + Main.HEIGHT/(1080/30));
        font.draw(main.batch, glyphWeapon, posX+Main.WIDTH/(1920/72), posY + Main.HEIGHT/(1080/10));
        font.draw(main.batch, glyphShield, posX+Main.WIDTH/(1920/200), posY + Main.HEIGHT/(1080/50));
        font.draw(main.batch, glyphRepair, posX+Main.WIDTH/(1920/200), posY + Main.HEIGHT/(1080/30));
        font.draw(main.batch, glyphCombat, posX+Main.WIDTH/(1920/200), posY + Main.HEIGHT/(1080/10));
        main.batch.draw(crewTexture, posX, posY, Main.WIDTH/(1920/72), Main.HEIGHT/(1080/72)); //48
        main.batch.draw(healthBox, posX+Main.WIDTH/(1920/5), posY-Main.HEIGHT/(1080/15), 50, Main.HEIGHT/(1080/15));
        float x = posX+Main.WIDTH/(1920/11);
        for(int i=0;i<currTexture;i++) {
            main.batch.draw(healthStatus, x, posY-Main.HEIGHT/(1080/10), Main.WIDTH/(1920/4), Main.HEIGHT/(1080/5));
            x+=Main.WIDTH/(1920/4);
        }
        main.batch.end();
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        crewTexture.dispose();

        healthStatus.dispose();
        healthBox.dispose();
    }
}
