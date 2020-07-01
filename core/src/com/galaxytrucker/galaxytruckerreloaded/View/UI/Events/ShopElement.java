package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShopBuyButton;

public class ShopElement {

    private Main main;

    private Stage stage;

    private int id;

    private ShopBuyButton button;

    private Texture texture;

    private float x, y;

    /**
     * the shop ui this button is on
     */
    private ShopUI shop;

    /**
     * constructor
     * @param main the main game class
     * @param stage the stage for buttons
     * @param id the id of this element
     * @param texture the texture for this element
     */
    public ShopElement(Main main, Stage stage, int id, Texture texture, float x, float y, ShopUI shop) {
        this.main = main;
        this.stage = stage;
        this.id = id;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.shop = shop;

        button = new ShopBuyButton(0, 0, 10, 10, this); //TODO whxy
        stage.addActor(button);
    }

    /**
     * render no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(texture, x, y, 10, 10); //TODO whxy
        main.batch.end();
    }

    /**
     * dispose of this ui
     */
    public void disposeShopElement() {
        texture.dispose();
        button.remove();
    }

    /**
     * button was clicked, this item is to be bought
     */
    public void buy() {
        shop.buy(id);
    }
}
