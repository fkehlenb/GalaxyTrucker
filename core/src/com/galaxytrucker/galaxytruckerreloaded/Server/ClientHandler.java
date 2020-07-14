package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Handle each client in a separate thread
 */
public class ClientHandler implements Runnable {

    /**
     * Client socket
     */
    private Socket clientSocket;

    /**
     * The server
     */
    public Server server;

    /**
     * ServerServiceCommunicator
     */
    private ServerServiceCommunicator serverServiceCommunicator;

    /**
     * Used to send data
     */
    private PrintWriter send;

    /**
     * Used to receive data
     */
    private BufferedReader receive;

    /**
     * ObjectOutputStream for sending objects
     */
    private ObjectOutputStream sendObject;

    /**
     * ObjectInputStream for receiving objects
     */
    private ObjectInputStream receiveObject;

    /**
     * Username
     */
    private String username;

    /**
     * Game running
     */
    @Getter
    @Setter
    private boolean gameActive = true;

    /**
     * Map seed
     */
    private int seed;

    /**
     * User
     */
    private User user;

    /**
     * Planet name array
     */
    private String[] names = {"MERCURY", "VENUS", "EARTH", "MARS", "JUPITER", "SATURN", "URANUS", "NEPTUN", "PLUTO", "AUGUSTUS",
            "SIRIUS", "HOMESTREAD", "ALPHA CENTAURI", "GLIESE", "404 NOT FOUND", "KEPLER", "TOI", "USCO1621b", "OGLE", "WASP", "WENDELSTEIN"
            , "EPIC", "ARION", "DIMIDIUM", "GALILEO", "DAGON", "SMETRIOS", "THESTIAS", "SAMH", "SAFFAR", "ARBER", "MADRIU", "AWASIS", "DITSO"};

    /**
     * Planet name list
     */
    private List<String> planetNames = new ArrayList<>();

    /**
     * Used planet names list
     */
    private List<String> usedPlanetNames = new ArrayList<>();

    /**
     * Constructor
     *
     * @param clientSocket - the client's socket
     * @param server       - the server
     */
    public ClientHandler(Socket clientSocket, Server server) throws IllegalArgumentException {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            sendObject = new ObjectOutputStream(clientSocket.getOutputStream());
            receiveObject = new ObjectInputStream(clientSocket.getInputStream());
            send = new PrintWriter(clientSocket.getOutputStream(), true);
            receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            serverServiceCommunicator = ServerServiceCommunicator.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * Run
     */
    @Override
    public void run() {
        planetNames.addAll(Arrays.asList(names));
        // ==================== LOGIN ====================
        try {
            this.username = receive.readLine().replace("[LOGIN]:", "");
            if (serverServiceCommunicator.isLoggedIn(username)) {
                send.println(false);
            } else {
                send.println(true);
                // ==================== NEW GAME ====================
                try {
                    this.user = serverServiceCommunicator.getUserService().getUser(username);
                    if (user.isFirstGame()) {
                        send.println("[NEW-GAME]");
                        // ==================== Overworld Creation ====================
                        this.seed = UUID.randomUUID().hashCode();
                        Overworld overworld = generateOverworld(this.seed, username);
                        user.setOverworld(overworld);
                        //====================== Ship Creation ==================
                        ShipType shipType = (ShipType) receiveObject.readObject();
                        user.setUserShip(generateShip(shipType, username, overworld));
                        //=======================================================
                        user.setFirstGame(false);
                    }
                    // ==================== UPDATE LOGIN ====================
                    user.setLoggedIn(true);
                    serverServiceCommunicator.getUserService().updateUser(user);
                    // ==================== FETCH SHIP ====================
                    try {
                        send.println("[FETCH-SHIP]");
                        sendObject.writeObject(this.serverServiceCommunicator.getClientShip(username));
                    } catch (Exception f) {
                        f.printStackTrace();
                        send.println("[EXCEPTION]:[FETCH-SHIP]:[USERNAME]:" + username);
                        throw new IllegalArgumentException(f.getMessage());
                    }
                    // ==================== FETCH MAP ====================
                    try {
                        send.println("[FETCH-MAP]");
                        sendObject.writeObject(this.serverServiceCommunicator.getClientMap(username));
                    } catch (Exception f) {
                        f.printStackTrace();
                        send.println("[EXCEPTION]:[FETCH-MAP]:[USERNAME]:" + username);
                        throw new IllegalArgumentException(f.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    send.println("[EXCEPTION]:[NEW-GAME]:[USERNAME]:" + username);
                }
                // ==================== RUNNING ====================
                while (gameActive) {
                    sendObject.writeObject(this.serverServiceCommunicator.getResponse((RequestObject) receiveObject.readObject()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Socket will be closed thanks to exception, therefor cannot send more data
            // Thread will terminate with socket exception
            try {
                serverServiceCommunicator.logoutAfterException(username);
                send.println("[EXCEPTION]:[LOGIN]:[USERNAME]:" + username);
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    /**
     * Get a new planet name
     *
     * @param names     - planet names
     * @param usedNames - the already used planet names
     * @param seed      - the world seed
     * @return an unused planet name
     */
    private String getPlanetName(List<String> names, List<String> usedNames, int seed) {
        Random random = new Random(seed);
        String newName = names.get(random.nextInt(names.size() - 1));
        if (usedNames.contains(newName)) {
            getPlanetName(names, usedNames, seed);
        }
        return newName;
    }

    /**
     * Generate a new overworld
     *
     * @param seed - the world seed
     * @return the generated overworld
     */
    private Overworld generateOverworld(int seed, String username) {
        Random random = new Random(seed);
        List<PlanetEvent> planetEvents = new ArrayList<PlanetEvent>();
        planetEvents.add(PlanetEvent.SHOP);
        planetEvents.add(PlanetEvent.VOID);
        planetEvents.add(PlanetEvent.COMBAT);
        planetEvents.add(PlanetEvent.METEORSHOWER);
        planetEvents.add(PlanetEvent.NEBULA);
        List<Planet> planets = new ArrayList<Planet>();
        // Create start planet
        //TODO CHANGE RANDOMIZER TO HAVE EVERYTHING IN EACH MAP
        //TODO ADD TRADER WITH ITEMS TO MAP
        planets.add(new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, seed),
                0, 0, PlanetEvent.VOID, new ArrayList<Ship>()));
        for (int i = 0; i < 5; i++) {
            for (int a = 0; a < 5; a++) {
                String nextPlanet = getPlanetName(planetNames, usedPlanetNames, seed);
                planets.add(new Planet(UUID.randomUUID().hashCode(), nextPlanet, i, a,
                        planetEvents.get(random.nextInt(planetEvents.size() - 1)), new ArrayList<Ship>()));
            }
        }
        // Boss planet
        planets.add(new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, seed),
                30, 30, PlanetEvent.BOSS, new ArrayList<Ship>()));
        Overworld overworld = new Overworld(UUID.randomUUID().hashCode(), seed, username);
        overworld.setStartPlanet(planets.get(0));
        overworld.setPlanetMap(planets);
        return overworld;
    }

    /**
     * Create a new ship
     *
     * @param shipType - the ship type
     * @return the created ship
     */
    @SuppressWarnings("Duplicates")
    private Ship generateShip(ShipType shipType, String username, Overworld overworld) {
        List<Weapon> inventory = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        List<Integer> crewStats = new ArrayList<>();
        switch (shipType) {
            // ========== KESTREL A ==========
            case DEFAULT:
                for (int f = 0; f < 5; f++) {
                    crewStats.add(2);
                }
                for (int i = 0; i < 17; i++) {
                    // ========== Tile generator ==========
                    // 2 Above each other
                    if (i == 0 || i == 16) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                    }
                    // 2 beside each other
                    else if (i == 1 || i == 3 || i == 4 || i == 6 || i == 7 || i == 10 || i == 13 || i == 14) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                    } else {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
                    }
                    // ========== Room Generator ==========
                    // O2
                    if (i == 1) {
                        Room o2 = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 5, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    // Engine
                    else if (i == 2) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 5, 0, SystemType.ENGINE, new ArrayList<Weapon>());
                        engine.setTiles(tiles);
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Isac", 8, 8, crewStats, 3 * crewStats.size() * 2, username);
                        crew.setTile(engine.getTiles().get(0));
                        engine.getTiles().get(0).setStandingOnMe(crew);
                        crew.setCurrentRoom(engine);
                        List<Crew> crewInRoom = engine.getCrew();
                        crewInRoom.add(crew);
                        engine.setCrew(crewInRoom);
                        rooms.add(engine);
                    }
                    // Weapons
                    else if (i == 5) {
                        System weapons = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 3, 5, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
                        weapons.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Newton", 8, 8, crewStats, 3 * crewStats.size() * 2, username);
                        crew.setTile(weapons.getTiles().get(0));
                        crew.setCurrentRoom(weapons);
                        List<Crew> crewInRoom = weapons.getCrew();
                        crewInRoom.add(crew);
                        weapons.getTiles().get(0).setStandingOnMe(crew);
                        weapons.setCrew(crewInRoom);
                        // Add Weapons
                        Weapon laser = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 2, 1, 1, 1, 0,
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser Gun", 30);
                        Weapon rocket = new Weapon(UUID.randomUUID().hashCode(), WeaponType.ROCKET,1, 2, 2, 1, 1, (float) 1.0,
                                (float) 0.25, 4, (float) 1.0, 2, 1, "Allahu Akbar", 30);
                        // TODO Add weapon price list based on weapon level
                        laser.setWeaponSystem(weapons);
                        rocket.setWeaponSystem(weapons);
                        List<Weapon> shipWeapons = new ArrayList<>();
                        shipWeapons.add(laser);
                        shipWeapons.add(rocket);
                        weapons.setShipWeapons(shipWeapons);
                        rooms.add(weapons);
                    }
                    // Medbay
                    else if (i == 11) {
                        Room medbay = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 5, 0, SystemType.MEDBAY, new ArrayList<Weapon>());
                        medbay.setTiles(tiles);
                        rooms.add(medbay);
                    }
                    // Shields
                    else if (i == 12) {
                        Room shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 5, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        rooms.add(shields);
                    }
                    // Cameras
                    else if (i == 14) {
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 5, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    // Cockpit
                    else if (i == 16) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 5, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Ahmad", 8, 8, crewStats, 3 * crewStats.size() * 2, username);
                        crew.setTile(cockpit.getTiles().get(0));
                        crew.setCurrentRoom(cockpit);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(crew);
                        cockpit.getTiles().get(0).setStandingOnMe(crew);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    } else {
                        Room room = new Room(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(), new ArrayList<Tile>());
                        room.setTiles(tiles);
                        rooms.add(room);
                    }
                    tiles.clear();
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 30, 60, 11, 7, 8,
                        0, 0, 0, overworld.getStartPlanet(), 0, 100, rooms, inventory, false);
            // ========== ROCK A ==========
            case TANK:
                crewStats.add(0);
                crewStats.add(3);
                crewStats.add(4);
                crewStats.add(3);
                crewStats.add(0);
                // ========== Tile Generator ==========
                for (int i=0;i<18;++i){
                    // Vertical 2x1
                    if (i<=4 || i==14 || i==16 || i==17) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),0,0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),0,1));
                    }
                    // Horizontal 1x2
                    else if (i == 6 || i== 7 || i==10 || i==11){
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),0,0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),1,0));
                    }
                    // Normal 4x4
                    else{
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),0,0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),1,0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),0,1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(),1,1));
                    }
                    // ========== Room Generator ==========
                    // O2
                    if (i==0){
                        rooms.add(new System(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),
                                tiles,1,5,0,SystemType.O2,new ArrayList<Weapon>()));

                    }
                    // Cameras
                    else if (i==1){
                        rooms.add(new System(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),
                                tiles,1,5,0,SystemType.CAMERAS,new ArrayList<Weapon>()));
                    }
                    // Shields
                    else if (i==5){
                        rooms.add(new System(UUID.randomUUID().hashCode(),0,100,i, new ArrayList<Crew>(),
                                tiles,2,5,0,SystemType.SHIELDS,new ArrayList<Weapon>()));
                    }
                    // Engine
                    else if (i==8){
                        Room engine = new System(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),
                                tiles,2,5, 0,SystemType.ENGINE,new ArrayList<Weapon>());
                        Crew c = new Crew(UUID.randomUUID().hashCode(),"Varnas",8, 8, crewStats, 3 * crewStats.size() * 2, username);
                        c.setTile(tiles.get(0));
                        tiles.get(0).setStandingOnMe(c);
                        engine.setTiles(tiles);
                        c.setCurrentRoom(engine);
                        List<Crew> crewInRoom = engine.getCrew();
                        crewInRoom.add(c);
                        engine.setCrew(crewInRoom);
                        rooms.add(engine);
                    }
                    // Weapons
                    else if (i==12){
                        System weapons = new System(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),
                                tiles,3,5,0,SystemType.WEAPON_SYSTEM,new ArrayList<Weapon>());
                        Weapon rocket2 = new Weapon(UUID.randomUUID().hashCode(),WeaponType.ROCKET,2,3,2,1,1,
                                (float) 0.3,(float)0.25,4,(float)1.0,2,1,"Rocket 2",25);
                        Weapon rocket1 = new Weapon(UUID.randomUUID().hashCode(),WeaponType.ROCKET,2,2,2,1,1,
                                (float) 0.3,(float)0.25,4,(float)1.0,2,1,"Rocket 1",25);
                        // TODO Add weapon price list based on weapon level
                        List<Weapon> currentWeapons = weapons.getShipWeapons();
                        rocket1.setWeaponSystem(weapons);
                        rocket2.setWeaponSystem(weapons);
                        currentWeapons.add(rocket1);
                        currentWeapons.add(rocket2);
                        weapons.setShipWeapons(currentWeapons);
                        rooms.add(weapons);
                    }
                    // Medbay
                    else if (i==15){
                        rooms.add(new System(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),
                                tiles,1,5,0,SystemType.MEDBAY,new ArrayList<Weapon>()));
                    }
                    // Cockpit
                    else if (i==16){
                        Room cockpit = new System(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),
                                tiles,1,5,0,SystemType.COCKPIT,new ArrayList<Weapon>());
                        Crew c = new Crew(UUID.randomUUID().hashCode(),"Bomfy",8,8,crewStats,3 * crewStats.size() * 2, username);
                        c.setTile(cockpit.getTiles().get(0));
                        c.setCurrentRoom(cockpit);
                        tiles.get(0).setStandingOnMe(c);
                        cockpit.setTiles(tiles);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(c);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    }
                    else {
                        rooms.add(new Room(UUID.randomUUID().hashCode(),0,100,i,new ArrayList<Crew>(),tiles));
                    }
                    tiles.clear();
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 50, 10, 30, 4, 9,
                        0, 0, 0, overworld.getStartPlanet(), 0, 100, rooms, inventory, false);
            case KILLER:

                // ========== Tile Generator ==========

                // ========== Room Generator ==========
            default:
                return null;
        }
    }
}
