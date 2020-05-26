package com.galaxytrucker.galaxytruckerreloaded.View.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Absftraqct Button-Class.
 * TODO: Momentan hat jeder Button seinen eigenen Constructor, in dem über die main ein SpriteBatch generiert wird. Möglicher Weise ist es eleganter das in dieser Klasse zu tun und in den jeweiligen Konstruktoren nur die entsprechende Funktion aufzurufen
 */
public abstract class Button {

    protected int imageX;
    protected int imageY;
    protected int screenX;
    protected int screenY;
    protected int width;
    protected int height;
    protected Texture image_up;
    protected Texture image_down;
    protected Texture image_hover;
    protected Texture image_disabled;

    protected boolean disabled;
    protected boolean hover;


    /**
     * Changes the usability of the Button
     * @param disabled
     */
    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }


    public void move(int x, int y)
    {
        imageX += x;
        screenX += x;
        imageY += y;
        screenY += y;
    }

    /**
     * Sets Position of the Button on the Screen
     * @param x horizontal Coordinate of the Button
     * @param y vertikal Coordinate of the Button
     */
    public void setPosition(int x, int y)
    {
        this.move(x - screenX, y - screenY);
    }

    /**
     * Sets Texture of the Button to the "hovered" Version of it
     * @param texture texture of the hovered Button
     */
    public void setHoverImage(Texture texture)
    {
        this.image_hover = texture;
    }

    /**
     * Sets Texture of the Button to the "disabled" Version of it
     * @param texture texture of the disabled Button
     */
    public void setDisabledImage(Texture texture)
    {
        this.image_disabled = texture;
    }

    /**
     * TODO not sure for what this one is.
     * @param texture
     */
    public void setDownImage(Texture texture)
    {
        this.image_down = texture;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public boolean containsPoint(int x, int y)
    {
        return x >= screenX && x < screenX+width && y >= screenY && y < screenY+height;
    }

    /**
     * Sets Hovering-Effect for an Button
     */
    public boolean isHovering(){
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return containsPoint(mouseX, mouseY);
    }


    /**
     * Left-Click action of the Button.
     */
    public abstract void leftClick();

}
