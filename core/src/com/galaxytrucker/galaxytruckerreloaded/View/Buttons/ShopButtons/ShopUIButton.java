package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.CurrentShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUI;

public class ShopUIButton extends ImButton {

        private CurrentShopUI nextUI;

        private ShopUI shop;
        private ShopButtonType type;

        public ShopUIButton(Texture tex, float x, float y, float width, float height, ShopUI shopUI, ShopButtonType type) {
            super(tex, x, y, width, height);
            this.shop=shopUI;
            this.type=type;
            this.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    leftClick();
                }
            });

        }

        @Override
        public void leftClick()
        {
            if (shop.getCurrent()!=null){
            shop.getCurrent().dispose();
        }
            switch (type){
                case UPGRADES:
                    shop.openShopUpgradeUI();
                    break;
                case CREW:
                    shop.openShopCrewUI();
                    break;
                case SELL:
                    shop.openShopSellUI();
                    break;
                case SYSTEM:
                    shop.openShopSystemUI();
                    break;
                case WEAPON:
                    shop.openShopWeaponUI();
                    break;
                case RESOURCE:
                    shop.openShopResourceUI();
                    break;
            }
        }

}
