package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/** Used for managing the ship's weapons */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class WeaponService {

    /** ShipDAO */
    @NonNull
    private ShipDAO shipDAO;

    /** WeaponDAO */
    @NonNull
    private WeaponDAO weaponDAO;

    /** Weapon System */
    @NonNull
    private System weaponSystem;

    /** Equip a weapon
     * @param s - the user's ship
     * @param w - the weapon to equip */
    public ResponseObject equipWeapon(Ship s,Weapon w){
        ResponseObject responseObject = new ResponseObject();
        try {
            if (s.getInventory().contains(w)){
                List<System> shipSystems = new ArrayList<>();
                for (Room r : s.getSystems()){
                    if (r.isSystem()){
                        shipSystems.add((System) r);
                    }
                }
                for (System sys : shipSystems){
                    if (sys.getSystemType().equals(SystemType.WEAPON_SYSTEM)){
                        if (sys.getShipWeapons().size()<4){
                            List<Weapon> weapons = sys.getShipWeapons();
                            weapons.add(w);
                            sys.setShipWeapons(weapons);
                            w.setWeaponSystem(sys);
                            List<Weapon> inventory = new ArrayList<>(s.getInventory());
                            inventory.remove(w);
                            s.setInventory(inventory);
                            weaponDAO.update(w);
                            shipDAO.update(s);
                            responseObject.setValidRequest(true);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            // TODO Rollback actions
        }
        return responseObject;
    }

    /** Uneqip a weapon
     * @param s - the ship
     * @param w - the weapon to unequip */
    public ResponseObject unequipWeapon(Ship s,Weapon w){
        ResponseObject responseObject = new ResponseObject();
        try {
            List<System> shipSystems = new ArrayList<>();
            for (Room r : s.getSystems()){
                if (r.isSystem()){
                    shipSystems.add((System) r);
                }
            }
            for (System sys : shipSystems){
                if (sys.getSystemType().equals(SystemType.WEAPON_SYSTEM)){
                    if (sys.getShipWeapons().contains(w)){
                        List<Weapon> weapons = sys.getShipWeapons();
                        weapons.remove(w);
                        sys.setShipWeapons(weapons);
                        w.setWeaponSystem(null);
                        List<Weapon> inventory = new ArrayList<>(s.getInventory());
                        inventory.add(w);
                        s.setInventory(inventory);
                        weaponDAO.update(w);
                        shipDAO.update(s);
                        responseObject.setValidRequest(true);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            // TODO Rollback actions
        }
        return responseObject;
    }

}
