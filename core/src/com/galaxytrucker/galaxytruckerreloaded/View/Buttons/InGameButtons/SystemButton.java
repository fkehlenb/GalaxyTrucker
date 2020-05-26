package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Button for regulation of (Sub)-System energie supplyment
 */
public class SystemButton extends Button
{
    /**
     * Sprite batch
     */
    private SpriteBatch batch;
    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;


    private System system;

    private Texture image_off;
    private Texture image_hover_off;

    Texture glow;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public SystemButton(Main main) {
    }

    public void setGlowTexture(Texture glow)
    {
        this.glow = glow;
    }

    public void setOffTextures(Texture image_off, Texture image_hover_off)
    {
        this.image_off = image_off;
        this.image_hover_off = image_hover_off;
    }


    /**
     * decrease the Energie provided for a System
     */
    public void rightClick()
    {
//        int power = -1;
//        if(system instanceof ShieldSystem)
//            power = -2;
//
//        if(system instanceof WeaponSystem)
//        {
//            WeaponSystem weaponSystem = (WeaponSystem)system;
//            for(int i = 3; i >= 0; i--)
//            {
//                if(weaponSystem.getWeapon(i) != null && weaponSystem.getWeapon(i).isPowered())
//                {
//                    int prevPower = system.getPower();
//                    weaponSystem.powerDownWeapon(i);
//                    if(prevPower != system.getPower())
//                    {
//                        Sounds.playSound("buttonOff");
//                    }
//                    break;
//                }
//            }
//        }
//        else
//        {
//            int prevPower = system.getPower();
//            system.addPower(power);
//            if(prevPower != system.getPower())
//            {
//                Sounds.playSound("buttonOff");
//            }
//        }
    }

    /**
     * increases the Energie provided for a System
     */
    @Override
    public void leftClick(){
//    {
//        int power = 1;
//        if(system instanceof ShieldSystem)
//            power = 2;
//
//        if(system instanceof WeaponSystem)
//        {
//            WeaponSystem weaponSystem = (WeaponSystem)system;
//            for(int i = 0; i < 4; i++)
//            {
//                if(!weaponSystem.getWeapon(i).isPowered())
//                {
//                    int prevPower = system.getPower();
//                    weaponSystem.powerOnWeapon(i);
//                    if(prevPower != system.getPower())
//                    {
//                        Sounds.playSound("buttonOn");
//                    }
//                    else
//                    {
//                        Sounds.playSound("buttonFail");
//                    }
//                    break;
//                }
//            }
//        }
//        else
//        {
//            int prevPower = system.getPower();
//            system.addPower(power);
//            if(prevPower != system.getPower())
//            {
//                Sounds.playSound("buttonOn");
//            }
//            else
//            {
//                Sounds.playSound("buttonFail");
//            }
//        }
//    }
}
}
