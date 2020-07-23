package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;


import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

public abstract class AbstractShip {

    /**
     * HP
     */
    protected int hp;

    /**
     * Shields
     */
    protected int shields;

    /**
     * the id of the ship
     */
    protected int id;

    /**
     * the main class extending game
     */
    protected Main main;

    /**
     * the stage for buttons
     */
    protected Stage stage;

    /**
     * the gameplay screen
     */
    protected GamePlay game;

    /**
     * the stage for the tiles of the rooms
     */
    protected Stage tileStage;

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     */
    public AbstractShip(Main main, Ship ship, Stage stage, GamePlay game, Stage tileStage) {
        this.main = main;
        this.stage = stage;
        this.game = game;
        this.tileStage = tileStage;
        hp = ship.getHp();
        shields = ship.getShields();
        id = ship.getId();
    }

    /**
     * get the x position of a room depending on the interior id and the ship type
     *
     * one tile = 48*48
     *
     * @param id the interior id of the room (from left to right, up to down)
     * @param bx the x position of the start (the middle of the ship)
     * @return the total x position (lower right corner of the room)
     */
    protected float getRoomX(ShipType type, int id, float bx) {
        switch(type) {
            case DEFAULT:
                switch(id) {
                    case 0: return bx - 360;
                    case 1:
                    case 2:
                    case 3:
                        return bx-312;
                    case 4:
                    case 6:
                        return bx-216;
                    case 5:
                        return bx-168;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        return bx-72;
                    case 11:
                    case 12:
                        return bx+24;
                    case 13:
                    case 14:
                        return bx+120;
                    case 15:
                        return bx+216;
                    case 16: return bx+312;
                }
            case BARRAGE:
                switch(id) {
                    case 0:
                    case 1:
                        return bx - 360;
                    case 2:
                    case 3:
                    case 4:
                        return bx - 312;
                    case 5:
                    case 6:
                        return bx - 264;
                    case 7: return bx - 216;
                    case 8:
                    case 9:
                        return bx - 168;
                    case 10:
                        return bx - 120;
                    case 11: return bx - 24;
                    case 12: return bx + 72;
                    case 13:
                    case 14:
                        return bx + 120;
                    case 15:
                    case 16:
                        return bx + 216;
                    case 17:
                        return bx + 312;

                }
            case BOARDER:
                switch(id) {
                    case 0:
                    case 1:
                        return bx - 216;
                    case 2: return bx- 168;
                    case 3:
                    case 4:
                        return bx-120;
                    case 5:
                    case 6:
                        return bx - 72;
                    case 7:
                    case 8:
                        return bx - 24;
                    case 9:
                    case 10:
                        return bx + 24;
                    case 11:
                    case 12:
                    case 13:
                        return bx + 72;
                    case 14: return bx + 120;
                    case 15: return bx + 168;

                };
            case TANK: switch(id) {
                case 0:
                case 1:
                    return bx - 216;
                case 2:
                case 3:
                case 4:
                    return bx - 168;
                case 5:
                case 6:
                case 7:
                case 8:
                    return bx - 120;
                case 9:
                case 10:
                case 11:
                case 12:
                    return bx - 24;
                case 13:
                case 14:
                case 15:
                    return bx + 72;
                case 16:
                case 17:
                    return bx + 168;
            }
            case KILLER:
                switch(id) {
                    case 0:
                    case 1:
                        return bx - 192;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        return bx - 96;
                    case 6: return bx - 48;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        return bx;
                    case 11:
                    case 12:
                        return bx + 96;
                    case 13:
                    case 14:
                    case 15:
                        return bx + 144;
                }
            case STEALTH:
                switch(id) {
                    case 0: return bx - 312;
                    case 1:
                    case 2:
                    case 3:
                        return bx - 216;
                    case 4: return bx - 168;
                    case 5:
                    case 6:
                        return bx - 120;
                    case 7: return bx - 72;
                    case 8:
                    case 9:
                        return bx - 24;
                    case 10: return bx + 24;
                    case 11: return bx + 72;
                    case 12:
                    case 13:
                        return bx + 168;
                    case 14: return bx + 264;
                }
            default: return 0;
        }
    }

    /**
     * return the y position of a room, depending on the ship type
     * @param type the ship type
     * @param id the interior id
     * @param by the base y position (in the middle of the ship)
     * @return total y position (lower right corner of room)
     */
    protected float getRoomY(ShipType type, int id, float by) {
        switch(type) {
            case DEFAULT:
                switch(id) {
                    case 0:
                    case 2:
                    case 5:
                    case 14:
                    case 15:
                    case 16:
                        return by - 48;
                    case 1:
                    case 4:
                        return by +48;
                    case 3:
                    case 6:
                    case 9:
                    case 12:
                        return by -96;
                    case 7: return by +96;
                    case 8:
                    case 11:
                    case 13:
                        return by;
                    case 10 : return by -144;
                }
            case BARRAGE:
                switch (id) {
                    case 8:
                    case 13:
                    case 15:
                        return by;
                    case 5: return by + 48;
                    case 2:
                    case 0:
                        return by + 96;
                    case 3:
                    case 7:
                    case 10:
                    case 11:
                    case 12:
                    case 14:
                    case 17:
                        return by-48;
                    case 6:
                    case 9:
                    case 16:
                        return by - 96;
                    case 4: return by - 144;
                    case 1: return by - 192;
                }
            case BOARDER:
                switch(id) {
                    case 5: return by + 120;
                    case 0:
                    case 6:
                    case 9:
                        return by + 72;
                    case 3:
                    case 11:
                        return by + 24;
                    case 2:
                        return by - 24;
                    case 7:
                    case 12:
                    case 14:
                        return by - 72;
                    case 1:
                    case 4:
                    case 10:
                        return by - 120;
                    case 8:
                    case 13:
                    case 15:
                        return by - 168;
                }
            case TANK:
                switch(id) {
                    case 0:
                    case 2:
                    case 5:
                    case 9:
                    case 13:
                    case 16:
                        return by + 48;
                    case 6:
                    case 10:
                        return by;
                    case 3:
                    case 7:
                    case 11:
                    case 14:
                        return by - 48;
                    case 1:
                    case 4:
                    case 8:
                    case 12:
                    case 15:
                    case 17:
                        return by - 144;
                }
            case KILLER:
                switch(id) {
                    case 2:
                    case 7:
                        return by + 144;
                    case 0:
                    case 3:
                    case 11:
                        return by + 96;
                    case 8:
                    case 13:
                        return by + 48;
                    case 6:
                    case 14:
                        return by - 48;
                    case 4:
                    case 9:
                    case 15:
                        return by - 144;
                    case 1:
                    case 5:
                    case 10:
                    case 12:
                        return by - 196;

                }
            case STEALTH:
                switch (id) {
                    case 1: return by + 96;
                    case 5:
                    case 8:
                        return by + 48;
                    case 12: return by;
                    case 0:
                    case 2:
                    case 4:
                    case 7:
                    case 10:
                    case 11:
                    case 13:
                    case 14:
                        return by - 48;
                    case 9: return by - 96;
                    case 3:
                    case 6:
                        return by - 144;
                }
            default: return 0;
        }
    }

    /**
     * the room was chosen by the player using a tile button
     * @param room the room that was chosen
     */
    public abstract void roomChosen(Room room);

    /**
     * to render the ui
     */
    public abstract void render();

    /**
     * show the ship
     */
    public abstract void showShipView();

    /**
     * hide the ship
     */
    public abstract void hideShipView();

    /**
     * dispose of ship
     */
    public abstract void disposeShipView();

    /**
     * update of the hull status (hp)
     * @param hpvalue new status
     */
    public abstract void hullStatusUpdate(int hpvalue);

    /**
     * shield status update
     * @param shieldvalue new status
     */
    public abstract void shieldStatusUpdate(int shieldvalue);

}
