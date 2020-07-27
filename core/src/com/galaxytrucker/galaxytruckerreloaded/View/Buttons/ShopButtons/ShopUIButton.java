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
            switch (type){
                case UPGRADES:
                    shop.openShopUpgradeUI();

                case CREW:
                    shop.openShopCrewUI();

                case SELL:
                    shop.openShopSellUI();

                case SYSTEM:
                    shop.openShopSystemUI();

                case WEAPON:
                    shop.openShopWeaponUI();

                case RESOURCE:
                    shop.openShopResourceUI();
            }
        }

}
