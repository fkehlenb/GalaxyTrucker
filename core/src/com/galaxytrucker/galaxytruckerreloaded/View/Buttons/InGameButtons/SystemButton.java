package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

public class SystemButton extends Button
{
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

//    public void render(SpriteBatch batch)
//    {
//        int mouseX = Gdx.input.getX();
//        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//        boolean oldHovering = hover;
//        hover = containsPoint(mouseX, mouseY);
//        if(hover == true && oldHovering == false)
//        {
//            Sounds.playSound("buttonHover");
//        }
//
//        if(hover)
//        {
//            if(system.getPower() > 0)
//                batch.setColor(120/256f, 1, 120/256f, 1);
//            batch.draw(glow, imageX + 8, imageY + 8);
//            batch.setColor(1, 1, 1, 1);
//
//            if(system.getPower() > 0)
//                batch.draw(image_hover, imageX, imageY);
//            else
//                batch.draw(image_hover_off, imageX, imageY);
//        }
//        else
//        {
//            if(system.getPower() > 0)
//                batch.draw(image_up, imageX, imageY);
//            else
//                batch.draw(image_off, imageX, imageY);
//        }
//    }

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
