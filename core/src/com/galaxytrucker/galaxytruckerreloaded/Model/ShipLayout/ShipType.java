package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;


public enum ShipType {
    DEFAULT,TANK,KILLER,BARRAGE,STEALTH,BOARDER;

    public ShipType next() {
        switch(this) {
            case DEFAULT: return TANK;
            case TANK: return KILLER;
            case KILLER: return BARRAGE;
            case BARRAGE: return STEALTH;
            case STEALTH: return BOARDER;
            case BOARDER: return DEFAULT;
            default: return null;
        }
    }

    public ShipType previous() {
        switch(this) {
            case DEFAULT: return BOARDER;
            case TANK: return DEFAULT;
            case BARRAGE: return KILLER;
            case BOARDER: return STEALTH;
            case STEALTH: return BARRAGE;
            case KILLER: return TANK;
            default: return null;
        }
    }
}
