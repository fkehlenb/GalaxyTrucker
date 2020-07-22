package com.galaxytrucker.galaxytruckerreloaded.Server;

/** Type of request sent to the server */
public enum RequestType {
    LOGOUT, HYPERJUMP, TRADERBUYWEAPON, TRADERBUYFUEL, TRADERBUYROCKETS, TRADERBUYCREW, TRADERBUYHP, TRADERSELLROCKETS,
    TRADERSELLWEAPON, MoveCrew, HealCrew, HealCrewInRoom, DamageCrew, FixSystems, RepairBreach, EQUIP_WEAPON,UNEQIP_WEAPON,
    ROUND_UPDATE_DATA,ATTACK_SHIP, UPGRADE_SYSTEM, ADD_ENERGY_SYSTEM,REMOVE_ENERGY_SYSTEM
}
