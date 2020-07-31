package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
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
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * Planet DAO
     */
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    /**
     * Overworld DAO
     */
    private OverworldDAO overworldDAO = OverworldDAO.getInstance();

    /** Weapon DAO */
    private WeaponDAO weaponDAO = WeaponDAO.getInstance();

    /** Crew DAO */
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /** Trader DAO */
    private TraderDAO traderDAO = TraderDAO.getInstance();

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

    /** Response object dao */
    private ResponseObjectDAO responseObjectDAO = ResponseObjectDAO.getInstance();

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

    private boolean clientRunning = true;

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
    private String[] names = {"MERCURY", "VENUS", "EARTH", "MARS", "JUPITER", "SATURN", "URANUS", "NEPTUN", "PLUTO", "AUGUSTUS", "NABOO",
            "SIRIUS", "HOMESTEAD", "ALPHA CENTAURI", "GLIESE", "404 NOT FOUND", "KEPLER", "TOI", "USCO1621b", "OGLE", "WASP", "WENDELSTEIN"
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
            send = new PrintWriter(clientSocket.getOutputStream(), true);
            receiveObject = new ObjectInputStream(clientSocket.getInputStream());
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
        while (clientRunning) {
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
                            send.flush();
                            // ==================== Overworld Creation ====================
                            int difficulty = Integer.parseInt(receive.readLine());
                            send.println("[RECEIVED-DIFFICULTY]");
                            send.flush();
                            this.seed = UUID.randomUUID().hashCode();
                            Overworld overworld = generateOverworld(this.seed, username, difficulty);
                            overworldDAO.persist(overworld);
                            user.setOverworld(overworld);
                            //====================== Ship Creation ==================
                            ShipType shipType = (ShipType) receiveObject.readObject();
                            Ship ship = generateShip(shipType, username, overworld.getStartPlanet());
                            for (Room r : ship.getSystems()) {
                                if (r.isSystem() && ((System) r).getEnergy() == 0) {
                                    ((System) r).setDisabled(true);
                                }
                            }
                            shipDAO.persist(ship);
                            user.setUserShip(ship);
                            Ship userShip = user.getUserShip();
                            Planet startPlanet = overworld.getStartPlanet();
                            List<Ship> startPlanetShips = startPlanet.getShips();
                            startPlanetShips.add(userShip);
                            startPlanet.setShips(startPlanetShips);
                            userShip.setPlanet(startPlanet);
                            //=======================================================
                            user.setFirstGame(false);
                        }
                        // ==================== UPDATE LOGIN ====================
                        user.setLoggedIn(true);
                        serverServiceCommunicator.getUserService().updateUser(user);
                        // ==================== FETCH SHIP ====================
                        try {
                            send.println("[FETCH-SHIP]");
                            send.flush();
                            receive.readLine();
                            Ship s = this.serverServiceCommunicator.getClientShip(username);
                            sendObject.writeObject(s);
                            sendObject.flush();
                        } catch (Exception f) {
                            f.printStackTrace();
                            send.println("[EXCEPTION]:[FETCH-SHIP]:[USERNAME]:" + username);
                            send.flush();
                            throw new IllegalArgumentException(f.getMessage());
                        }
                        // ==================== FETCH MAP ====================
                        try {
//                            Thread.sleep(4000);
                            send.println("[FETCH-MAP]");
                            send.flush();
                            receive.readLine();
                            Overworld o = this.serverServiceCommunicator.getClientMap(username);
                            sendObject.writeObject(o);
                            sendObject.flush();
                        } catch (Exception f) {
                            f.printStackTrace();
                            send.println("[EXCEPTION]:[FETCH-MAP]:[USERNAME]:" + username);
                            send.flush();
                            throw new IllegalArgumentException(f.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        send.println("[EXCEPTION]:[NEW-GAME]:[USERNAME]:" + username);
                        send.flush();
                    }
                    gameActive = true;
                    // ===== Add to connected clients =====
                    this.serverServiceCommunicator.getPvpClients().add(username);
                    // ==================== RUNNING ====================
                    while (gameActive) {
                        if (!clientSocket.getInetAddress().isReachable(10000)) {
                            RequestObject requestObject = new RequestObject();
                            requestObject.setRequestType(RequestType.LOGOUT);
                            requestObject.setUsername(username);
                            this.serverServiceCommunicator.getResponse(requestObject);
                            java.lang.System.out.println("[Client-Disconnected]:[Auto-Logout]");
                        } else {
                            RequestObject request = (RequestObject) receiveObject.readObject();
                            sendObject.flush();
                            ResponseObject responseObject = this.serverServiceCommunicator.getResponse(request);
                            sendObject.writeObject(responseObject);
                            sendObject.flush();
                            if (request.getRequestType() == RequestType.LOGOUT) {
                                gameActive = false;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Socket will be closed thanks to exception, therefor cannot send more data
                // Thread will terminate with socket exception
                try {
                    serverServiceCommunicator.logoutAfterException(username);
//                    clientSocket.close();
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }
    }

    /**
     * Get a new planet name
     *
     * @param names     - planet names
     * @param usedNames - the already used planet names
     * @param random    - the randomizer
     * @return an unused planet name
     */
    private String getPlanetName(List<String> names, List<String> usedNames, Random random) {
        String newName = names.get(random.nextInt(names.size() - 1));
        if (usedNames.contains(newName)) {
            getPlanetName(names, usedNames, random);
        }
        return newName;
    }

    /** Crew name generator
     * @return a random name */
    private String crewNameGenerator(){
        String[] names = {"Trumpinator","Arnold Schwarzenegger", "Katie","Peter","Brian","Anakin","General Grievous",
                "Bob","Noob","Steve"};
        Random random = new Random();
        return names[random.nextInt(names.length-1)];
    }

    /** Name generator
     * @return a random name */
    private String weaponNameGenerator(){
        String[] names = {"BLASTER 9000","DESTRUCTION","Bombs Away","YO MOMMA", "U are DEAD", "GAME OVER", "Silly Billy"};
        Random random = new Random();
        return names[random.nextInt(names.length-1)];
    }

    /**
     * Generate a new overworld
     *
     * @param seed - the world seed
     * @return the generated overworld
     */
    @SuppressWarnings("Duplicates")
    private Overworld generateOverworld(int seed, String username, int difficulty) {
        Random random = new Random(seed);
        List<Planet> planetMap = new ArrayList<>();
        List<Planet> finalMap = new ArrayList<>();
        // ======================= Add traders to map =======================
        int traders = random.nextInt(4) + 1;
        List<WeaponType> weaponTypes = new ArrayList<>();
        weaponTypes.add(WeaponType.LASER);
        weaponTypes.add(WeaponType.ROCKET);
        weaponTypes.add(WeaponType.BOMB);
        weaponTypes.add(WeaponType.RADIO);
        weaponTypes.add(WeaponType.RADIO_BOMB);
        List<ShipType> shipTypes = new ArrayList<>();
        shipTypes.add(ShipType.DEFAULT);
        shipTypes.add(ShipType.BARRAGE);
        shipTypes.add(ShipType.KILLER);
        shipTypes.add(ShipType.BOARDER);
        shipTypes.add(ShipType.STEALTH);
        shipTypes.add(ShipType.TANK);
        List<Integer> weaponPrices = new ArrayList<>();
        weaponPrices.add(15);
        weaponPrices.add(25);
        weaponPrices.add(40);
        weaponPrices.add(60);
        weaponPrices.add(80);
        for (int i = 0; i < traders; i++) {
            int randomPlanetTextureInt = random.nextInt(7) + 1;
            String randomPlanetTexture = "map/planets/" + Integer.toString(randomPlanetTextureInt) + ".png";
            Planet planet = new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                    0, 0, PlanetEvent.SHOP, new ArrayList<Ship>(), randomPlanetTexture);
            List<Weapon> weaponStock = new ArrayList<>();
            List<Crew> crewStock = new ArrayList<>();
            for (int g=0;g<4;g++){
                Weapon w = new Weapon(UUID.randomUUID().hashCode(),weaponTypes.get(random.nextInt(weaponTypes.size()-1)),
                        random.nextInt(5)+1,random.nextInt(4)+1,random.nextInt(5),random.nextInt(4),random.nextInt(1),
                        random.nextFloat(),random.nextFloat(),random.nextInt(5),random.nextFloat(),random.nextInt(5),random.nextInt(3)+1,
                        weaponNameGenerator(),random.nextInt(100)+20);
                w.setPrice(weaponPrices);
                weaponStock.add(w);
                List<Integer> crewStats = new ArrayList<>();
                for (int a=0;a<5;a++){
                    crewStats.add(random.nextInt(5)+1);
                }
                crewStock.add(new Crew(UUID.randomUUID().hashCode(),crewNameGenerator(),8,8,crewStats,random.nextInt(200)+30,"None"));
            }
            Trader t = new Trader(UUID.randomUUID().hashCode(),planet,weaponStock,random.nextInt(10)+10,
                    random.nextInt(20)+10,random.nextInt(10)+10,crewStock);
            try {
                planetDAO.persist(planet);
                for (Weapon w : weaponStock){
                    weaponDAO.persist(w);
                }
                for (Crew c : crewStock){
                    crewDAO.persist(c);
                }
                traderDAO.persist(t);
                planet = planetDAO.getById(planet.getId());
                planetDAO.update(planet);
            }
            catch (Exception f){
                f.printStackTrace();
            }
            planet.setTrader(t);
            planetMap.add(planet);
        }/*
        // ======================= Add combat =======================
        int battles = random.nextInt(4) + 5;
        for (int i = 0; i < battles; i++) {
            //Random planet texture
            int randomPlanetTextureInt = random.nextInt(7) + 1;
            String randomPlanetTexture = "map/planets/" + Integer.toString(randomPlanetTextureInt) + ".png";

            Planet planet = new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                    0, 0, PlanetEvent.COMBAT, new ArrayList<Ship>(), randomPlanetTexture);
            List<Ship> ships = planet.getShips();
            Ship opponent = generateShip(shipTypes.get(random.nextInt(shipTypes.size() - 1)), "[ENEMY]", planet);
            try {
                planetDAO.persist(planet);
                shipDAO.persist(opponent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ships.add(opponent);
            planet.setShips(ships);
            planetMap.add(planet);
        }
        // ======================= Add minibosses =======================
        int minibosses = random.nextInt(2) + 1;
        for (int i = 0; i < minibosses; i++) {
            //Random planet texture
            int randomPlanetTextureInt = random.nextInt(7) + 1;
            String randomPlanetTexture = "map/planets/" + Integer.toString(randomPlanetTextureInt) + ".png";

            Planet planet = new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                    0, 0, PlanetEvent.MINIBOSS, new ArrayList<Ship>(), randomPlanetTexture);
            List<Ship> ships = planet.getShips();
            List<ShipType> minibossTypes = new ArrayList<>();
            minibossTypes.add(ShipType.BOARDER);
            minibossTypes.add(ShipType.BARRAGE);
            Ship opponent = generateShip(minibossTypes.get(random.nextInt(minibossTypes.size() - 1)), "[ENEMY]", planet);
            try {
                opponent.setHp(20 + random.nextInt(20));
                planetDAO.persist(planet);
                shipDAO.persist(opponent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ships.add(opponent);
            planet.setShips(ships);
            planetMap.add(planet);
        }
        // ======================= Add void =======================
        int voids = random.nextInt(2);
        for (int i = 0; i < voids; i++) {
            //Random planet texture
            int randomPlanetTextureInt = random.nextInt(7) + 1;
            String randomPlanetTexture = "map/planets/" + Integer.toString(randomPlanetTextureInt) + ".png";

            planetMap.add(new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                    0, 0, PlanetEvent.VOID, new ArrayList<Ship>(), randomPlanetTexture));
        }
        // ======================= Add Nebulae =======================
        int nebulae = random.nextInt(4) + 1;
        for (int i = 0; i < nebulae; i++) {
            planetMap.add(new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                    0, 0, PlanetEvent.NEBULA, new ArrayList<Ship>(), "map/nebula.png"));
        }
        // ======================= Add meteorshower =======================
        int meteors = random.nextInt(3) + 1;
        for (int i = 0; i < meteors; i++) {
            planetMap.add(new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                    0, 0, PlanetEvent.METEORSHOWER, new ArrayList<Ship>(), "map/asteroid.png"));
        }*/
        // ======================= Add start planet =======================
        //Random planet texture
        int randomPlanetTextureInt = random.nextInt(7) + 1;
        String randomPlanetTexture = "map/planets/" + Integer.toString(randomPlanetTextureInt) + ".png";

        Planet startPlanet = new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                -1, -1, PlanetEvent.VOID, new ArrayList<Ship>(), randomPlanetTexture);
        startPlanet.setDiscovered(true);
        finalMap.add(startPlanet);

        // ======================= Generate grid map =======================
        int grid = planetMap.size() / 2 + 1;
        for (int i = 0; i < grid / 2 + 1; i++) {
            for (int a = 0; a < grid / 2 + 1; a++) {
                if (planetMap.size() >= 1) {
                    Planet addToMap;
                    if (planetMap.size() > 1) {
                        addToMap = planetMap.get(random.nextInt((planetMap.size() - 1)));
                    } else {
                        addToMap = planetMap.get(0);
                    }
                    addToMap.setPosX(i);
                    addToMap.setPosY(a);
                    planetMap.remove(addToMap);
                    finalMap.add(addToMap);
                }
            }
        }
        // Add boss planet
        Planet boss = new Planet(UUID.randomUUID().hashCode(), getPlanetName(planetNames, usedPlanetNames, random),
                30, 30, PlanetEvent.BOSS, new ArrayList<Ship>(), "map/planet_sun1.png");
        List<Ship> ships = boss.getShips();
        Ship opponent = generateShip(ShipType.DEATH_STAR, "[ENEMY]", boss);
        try {
            planetDAO.persist(boss);
            shipDAO.persist(opponent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ships.add(opponent);
        boss.setShips(ships);
        finalMap.add(boss);
        for (Planet p : finalMap) {
            try {
                planetDAO.update(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Overworld(UUID.randomUUID().hashCode(), seed, difficulty, username, finalMap, startPlanet, boss);
    }

    /**
     * Create a new ship
     *
     * @param shipType - the ship type
     * @return the created ship
     */
    @SuppressWarnings("Duplicates")
    private Ship generateShip(ShipType shipType, String username, Planet planet) {
        List<Weapon> inventory = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Integer> crewStats = new ArrayList<>();
        List<Integer> weaponPrices = new ArrayList<>();
        weaponPrices.add(15);
        weaponPrices.add(25);
        weaponPrices.add(40);
        weaponPrices.add(60);
        weaponPrices.add(80);
        switch (shipType) {
            // ========== KESTREL A ==========
            case DEFAULT: //------------------done
                for (int f = 0; f < 5; f++) {
                    crewStats.add(2);
                }
                for (int i = 0; i < 17; i++) {
                    List<Tile> tiles = new ArrayList<>();
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
                                new ArrayList<Tile>(), 1, 2, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    // Engine
                    else if (i == 2) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.ENGINE, new ArrayList<Weapon>());
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
                                new ArrayList<Tile>(), 2, 3, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
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
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30);
                        Weapon rocket = new Weapon(UUID.randomUUID().hashCode(), WeaponType.ROCKET, 1, 2, 2, 1, 1, (float) 1.0,
                                (float) 0.25, 4, (float) 1.0, 2, 1, "Rocket", 30);
                        laser.setPrice(weaponPrices);
                        rocket.setPrice(weaponPrices);
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
                                new ArrayList<Tile>(), 0, 2, 0, SystemType.MEDBAY, new ArrayList<Weapon>());
                        medbay.setTiles(tiles);
                        rooms.add(medbay);
                    }
                    // Shields
                    else if (i == 12) {
                        Room shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 4, 4, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        rooms.add(shields);
                    }
                    // Cameras
                    else if (i == 14) {
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    // Cockpit
                    else if (i == 16) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Larry", 8, 8, crewStats, 3 * crewStats.size() * 2, username);
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
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 30, 60, 11, 7, 2,
                        0, 0, 0, planet, 0, 100, rooms, inventory, false, false, false, false);
            // ========== ROCK A ==========
            case TANK: //-------done
                crewStats = new ArrayList<>();
                crewStats.add(0);
                crewStats.add(3);
                crewStats.add(4);
                crewStats.add(3);
                crewStats.add(0);
                for (int i = 0; i < 18; i++) {

                    List<Tile> tiles = new ArrayList<>();
                    // ========== Tile Generator ==========
                    // 2 Above each other
                    if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 14 || i == 16 || i == 17) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                    }
                    // 2 beside each other
                    else if (i == 6 || i == 7 || i == 10 || i == 11) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                    } else {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
                    }
                    // ========== Room Generator ==========
                    //O2
                    if (i == 0) {
                        Room o2 = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    //Engine
                    else if (i == 8) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.ENGINE, new ArrayList<Weapon>());
                        engine.setTiles(tiles);
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Isac", 8, 8, crewStats, 3 * 3 + 3 * 4 + 3 * 3, username);
                        crew.setTile(engine.getTiles().get(0));
                        engine.getTiles().get(0).setStandingOnMe(crew);
                        crew.setCurrentRoom(engine);
                        List<Crew> crewInRoom = engine.getCrew();
                        crewInRoom.add(crew);
                        engine.setCrew(crewInRoom);
                        rooms.add(engine);
                    }
                    //Weapons
                    else if (i == 12) {
                        System weapons = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 4, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
                        weapons.setTiles(tiles);
                        // Add Weapons
                        Weapon rocket2 = new Weapon(UUID.randomUUID().hashCode(), WeaponType.ROCKET, 2, 3, 2, 1, 1, (float) 1.0,
                                (float) 0.25, 4, (float) 1.0, 2, 1, "Rocket", 30);
                        Weapon rocket1 = new Weapon(UUID.randomUUID().hashCode(), WeaponType.ROCKET, 1, 2, 2, 1, 1, (float) 1.0,
                                (float) 0.25, 4, (float) 1.0, 2, 1, "Rocket", 30);
                        rocket2.setPrice(weaponPrices);
                        rocket1.setPrice(weaponPrices);
                        rocket1.setWeaponSystem(weapons);
                        rocket2.setWeaponSystem(weapons);
                        List<Weapon> shipWeapons = new ArrayList<>();
                        shipWeapons.add(rocket1);
                        shipWeapons.add(rocket2);
                        weapons.setShipWeapons(shipWeapons);
                        rooms.add(weapons);
                    }
                    //Medbay
                    else if (i == 15) {
                        Room medbay = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 0, 2, 0, SystemType.MEDBAY, new ArrayList<Weapon>());
                        medbay.setTiles(tiles);
                        rooms.add(medbay);
                    }
                    //Shields
                    else if (i == 5) {
                        Room shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 6, 6, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        rooms.add(shields);
                    }
                    //Cameras
                    else if (i == 1) { //TODO irgendeine logik hinter energy/maxenergy?
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    //Cockpit
                    else if (i == 16) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 2, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Larry", 8, 8, crewStats, 3 * 3 + 3 * 4 + 3 * 3, username);
                        crew.setTile(cockpit.getTiles().get(0));
                        crew.setCurrentRoom(cockpit);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(crew);
                        cockpit.getTiles().get(0).setStandingOnMe(crew);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    }
                    //otherwise
                    else {
                        Room room = new Room(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(), new ArrayList<Tile>());
                        room.setTiles(tiles);
                        rooms.add(room);
                    }
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 50, 10, 30, 4, 1,
                        0, 0, 0, planet, 0, 100, rooms, inventory, false, false, false, false);
            case KILLER: //-------done
                crewStats = new ArrayList<>();
                crewStats.add(3);
                crewStats.add(4);
                crewStats.add(0);
                crewStats.add(1);
                crewStats.add(2);
                for (int i = 0; i < 16; i++) {
                    List<Tile> tiles = new ArrayList<>();
                    // ========== Tile Generator ==========
                    // 2 Above each other
                    if (i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                    }
                    // 2 beside each other
                    else if (i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 10) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                    } else {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
                    }
                    // ========== Room Generator ==========
                    //O2
                    if (i == 7) {
                        Room o2 = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    //Medbay TODO??
                    //Engine
                    else if (i == 6) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 4, 0, SystemType.ENGINE, new ArrayList<Weapon>());
                        engine.setTiles(tiles);
                        rooms.add(engine);
                    }
                    //Weapon
                    else if (i == 9) {
                        System weapons = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 4, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
                        weapons.setTiles(tiles);
                        // Add Weapons
                        Weapon laser = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 2, 1, 1, 1, 0,
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30);
                        Weapon radio = new Weapon(UUID.randomUUID().hashCode(), WeaponType.RADIO, 4, 0, 3, 1, 0, (float) 1.0,
                                (float) 0.12, 0, (float) 0.0, 2, 3, "Radio", 45);
                        radio.setPrice(weaponPrices);
                        laser.setPrice(weaponPrices);
                        radio.setWeaponSystem(weapons);
                        laser.setWeaponSystem(weapons);
                        List<Weapon> shipWeapons = new ArrayList<>();
                        shipWeapons.add(radio);
                        shipWeapons.add(laser);
                        weapons.setShipWeapons(shipWeapons);
                        rooms.add(weapons);
                    }
                    //Cockpit
                    else if (i == 14) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 2, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Larry", 8, 8, crewStats, 30, username);
                        crew.setTile(cockpit.getTiles().get(0));
                        crew.setCurrentRoom(cockpit);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(crew);
                        cockpit.getTiles().get(0).setStandingOnMe(crew);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    }
                    //Cameras
                    else if (i == 5) {
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 5, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    //Shields
                    else if (i == 8) {
                        Room shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 2, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        rooms.add(shields);
                    }
                    //otherwise
                    else {
                        Room room = new Room(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(), new ArrayList<Tile>());
                        room.setTiles(tiles);
                        rooms.add(room);
                    }
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 25, 40, 0, 10, 3,
                        0, 0, 0, planet, 0, 100, rooms, inventory, false, false, false, false);
            case BARRAGE:
                crewStats = new ArrayList<>();
                crewStats.add(2);
                crewStats.add(4);
                crewStats.add(2);
                crewStats.add(1);
                crewStats.add(1);
                for (int i = 0; i < 18; i++) {
                    List<Tile> tiles = new ArrayList<>();
                    // ========== Tile Generator ==========
                    // 2 Above each other
                    if (i == 0 || i == 1 || i == 7 || i == 8 || i == 9 || i == 12 || i == 17) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                    }
                    // 2 beside each other
                    else if (i == 2 || i == 4 || i == 5 || i == 6 || i == 13 || i == 14) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                    } else {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
                    }
                    // ========== Room Generator ==========
                    //O2
                    if (i == 9) {
                        Room o2 = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    //Engine
                    else if (i == 3) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 2, 0, SystemType.ENGINE, new ArrayList<Weapon>());
                        engine.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Isac", 8, 8, crewStats, 30, username);
                        crew.setTile(engine.getTiles().get(0));
                        crew.setCurrentRoom(engine);
                        List<Crew> crewInRoom = engine.getCrew();
                        crewInRoom.add(crew);
                        engine.getTiles().get(0).setStandingOnMe(crew);
                        engine.setCrew(crewInRoom);
                        rooms.add(engine);
                    }
                    //Medbay
                    else if (i == 10) {
                        Room medbay = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 0, 1, 0, SystemType.MEDBAY, new ArrayList<Weapon>());
                        medbay.setTiles(tiles);
                        rooms.add(medbay);
                    }
                    //Shields
                    else if (i == 11) {
                        Room shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 4, 4, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        rooms.add(shields);
                    }
                    //Cameras
                    else if (i == 13) {
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    //Cockpit
                    else if (i == 7) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 1, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Larry", 8, 8, crewStats, 30, username);
                        crew.setTile(cockpit.getTiles().get(0));
                        crew.setCurrentRoom(cockpit);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(crew);
                        cockpit.getTiles().get(0).setStandingOnMe(crew);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    }
                    //Weapons
                    else if (i == 15) {
                        System weapons = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 6, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
                        weapons.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Newton", 8, 8, crewStats, 30, username);
                        crew.setTile(weapons.getTiles().get(0));
                        crew.setCurrentRoom(weapons);
                        List<Crew> crewInRoom = weapons.getCrew();
                        crewInRoom.add(crew);
                        weapons.getTiles().get(0).setStandingOnMe(crew);
                        weapons.setCrew(crewInRoom);
                        // Add Weapons
                        Weapon laser = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 2, 1, 1, 1, 0,
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30);
                        Weapon laser2 = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 2, 1, 1, 1, 0,
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30);
                        Weapon laser3 = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 2, 1, 1, 1, 0,
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30);
                        laser2.setPrice(weaponPrices);
                        laser.setPrice(weaponPrices);
                        laser3.setPrice(weaponPrices);
                        laser.setWeaponSystem(weapons);
                        laser2.setWeaponSystem(weapons);
                        laser3.setWeaponSystem(weapons);
                        List<Weapon> shipWeapons = new ArrayList<>();
                        shipWeapons.add(laser);
                        shipWeapons.add(laser2);
                        shipWeapons.add(laser3);
                        weapons.setShipWeapons(shipWeapons);
                        rooms.add(weapons);
                    }
                    //otherwise
                    else {
                        Room room = new Room(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(), new ArrayList<Tile>());
                        room.setTiles(tiles);
                        rooms.add(room);
                    }
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 20, 10, 0, 3, 2,
                        0, 0, 0, planet, 0, 100, rooms, inventory, false, false, false, false);
            case STEALTH: //------------ done
                crewStats = new ArrayList<>();
                crewStats.add(4);
                crewStats.add(1);
                crewStats.add(0);
                crewStats.add(3);
                crewStats.add(2);
                for (int i = 0; i < 15; i++) {
                    List<Tile> tiles = new ArrayList<>();
                    // ========== Tile Generator ==========
                    // 2 Above each other
                    if (i == 2 || i == 7 || i == 10 || i == 14) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                    }
                    // 2 beside each other
                    else if (i == 1 || i == 3 || i == 8 || i == 9 || i == 12 || i == 13) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                    } else {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
                    }
                    // ========== Room Generator ==========
                    //O2
                    if (i == 12) {
                        Room o2 = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 2, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    //Engines
                    else if (i == 0) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 8, 0, SystemType.ENGINE, new ArrayList<Weapon>());
                        engine.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Isac", 8, 8, crewStats, 30, username);
                        crew.setTile(engine.getTiles().get(0));
                        crew.setCurrentRoom(engine);
                        List<Crew> crewInRoom = engine.getCrew();
                        crewInRoom.add(crew);
                        engine.getTiles().get(0).setStandingOnMe(crew);
                        engine.setCrew(crewInRoom);
                        rooms.add(engine);
                    }
                    //Medbay
                    else if (i == 5) {
                        Room medbay = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 0, 2, 0, SystemType.MEDBAY, new ArrayList<Weapon>());
                        medbay.setTiles(tiles);
                        rooms.add(medbay);
                    }
                    //Weapons
                    else if (i == 11) {
                        System weapons = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 2, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
                        weapons.setTiles(tiles);
                        // Add Weapons
                        Weapon laser = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 1, 1, 1, 1, 0,
                                (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30);
                        Weapon bomb = new Weapon(UUID.randomUUID().hashCode(), WeaponType.BOMB, 2, 6, 5, 1, 1,
                                (float) 1.5, (float) 0.18, 4, (float) 1.5, 3, 1, "Bomb", 35);
                        bomb.setPrice(weaponPrices);
                        laser.setPrice(weaponPrices);
                        laser.setWeaponSystem(weapons);
                        bomb.setWeaponSystem(weapons);
                        List<Weapon> shipWeapons = new ArrayList<>();
                        shipWeapons.add(laser);
                        shipWeapons.add(bomb);
                        weapons.setShipWeapons(shipWeapons);
                        rooms.add(weapons);
                    }
                    //Shields
                    else if (i == 3) {
                        System shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 0, 4, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        shields.setDisabled(true);
                        shields.setUnlocked(false);
                        rooms.add(shields);
                    }
                    //Cameras
                    else if (i == 1) {
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    //Cockpit
                    else if (i == 14) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Larry", 8, 8, crewStats, 30, username);
                        crew.setTile(cockpit.getTiles().get(0));
                        crew.setCurrentRoom(cockpit);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(crew);
                        cockpit.getTiles().get(0).setStandingOnMe(crew);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    }
                    //otherwise
                    else {
                        Room room = new Room(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(), new ArrayList<Tile>());
                        room.setTiles(tiles);
                        rooms.add(room);
                    }
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 30, 100, 25, 25, 7,
                        0, 0, 0, planet, 0, 100, rooms, inventory, false, false, false, false);
            case BOARDER:
                crewStats = new ArrayList<>();
                crewStats.add(2);
                crewStats.add(2);
                crewStats.add(2);
                crewStats.add(0);
                crewStats.add(4);
                for (int i = 0; i < 16; i++) {
                    List<Tile> tiles = new ArrayList<>();
                    // ========== Tile Generator ==========
                    // 2 Above each other
                    if (i == 2 || i == 3 || i == 7 || i == 8 || i == 9 || i == 10 || i == 12 || i == 14 || i == 15) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                    }
                    // 2 beside each other
                    else if (i == 5 || i == 6) {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                    } else {
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
                        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
                    }
                    // ========== Room Generator ==========
                    //O2
                    if (i == 3) {
                        Room o2 = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 2, 0, SystemType.O2, new ArrayList<Weapon>());
                        o2.setTiles(tiles);
                        rooms.add(o2);
                    }
                    //Engines
                    else if (i == 1) {
                        Room engine = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.ENGINE, new ArrayList<Weapon>());
                        engine.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Isac", 8, 8, crewStats, 30, username);
                        crew.setTile(engine.getTiles().get(0));
                        crew.setCurrentRoom(engine);
                        List<Crew> crewInRoom = engine.getCrew();
                        crewInRoom.add(crew);
                        engine.getTiles().get(0).setStandingOnMe(crew);
                        engine.setCrew(crewInRoom);
                        rooms.add(engine);
                    }
                    //Medbay
                    else if (i == 13) {
                        Room medbay = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 0, 3, 0, SystemType.MEDBAY, new ArrayList<Weapon>());
                        medbay.setTiles(tiles);
                        rooms.add(medbay);
                    }
                    //Weapons
                    else if (i == 11) {
                        System weapons = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 2, 3, 0, SystemType.WEAPON_SYSTEM, new ArrayList<Weapon>());
                        weapons.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "crew1", 8, 8, crewStats, 30, username);
                        crew.setTile(weapons.getTiles().get(0));
                        crew.setCurrentRoom(weapons);
                        List<Crew> crewInRoom = weapons.getCrew();
                        crewInRoom.add(crew);
                        weapons.getTiles().get(0).setStandingOnMe(crew);
                        weapons.setCrew(crewInRoom);
                        // Add Weapons
                        Weapon radioBomb = new Weapon(UUID.randomUUID().hashCode(), WeaponType.RADIO_BOMB, 2, 2, 5, 1, 0,
                                (float) 1.5, (float) 0.05, 4, (float) 1.5, 5, 1, "RadioBomb", 75);
                        Weapon healBomb = new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 2, 0, 6, 1, 1,
                                (float) 1.5, (float) 0.1, 4, (float) 0, 4, 1, "HealBomb", 60);
                        radioBomb.setPrice(weaponPrices);
                        healBomb.setPrice(weaponPrices);
                        radioBomb.setWeaponSystem(weapons);
                        healBomb.setWeaponSystem(weapons);
                        List<Weapon> shipWeapons = new ArrayList<>();
                        shipWeapons.add(radioBomb);
                        shipWeapons.add(healBomb);
                        weapons.setShipWeapons(shipWeapons);
                        rooms.add(weapons);
                    }
                    //Shields
                    else if (i == 4) {
                        Room shields = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 4, 4, 0, SystemType.SHIELDS, new ArrayList<Weapon>());
                        shields.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "crew2", 8, 8, crewStats, 30, username);
                        crew.setTile(shields.getTiles().get(0));
                        crew.setCurrentRoom(shields);
                        List<Crew> crewInRoom = shields.getCrew();
                        crewInRoom.add(crew);
                        shields.getTiles().get(0).setStandingOnMe(crew);
                        shields.setCrew(crewInRoom);
                        rooms.add(shields);
                    }
                    //Cameras
                    else if (i == 7) {
                        Room cameras = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.CAMERAS, new ArrayList<Weapon>());
                        cameras.setTiles(tiles);
                        rooms.add(cameras);
                    }
                    //Cockpit
                    else if (i == 15) {
                        Room cockpit = new System(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(),
                                new ArrayList<Tile>(), 1, 3, 0, SystemType.COCKPIT, new ArrayList<Weapon>());
                        cockpit.setTiles(tiles);
                        // Add crew
                        Crew crew = new Crew(UUID.randomUUID().hashCode(), "Larry", 8, 8, crewStats, 30, username);
                        crew.setTile(cockpit.getTiles().get(0));
                        crew.setCurrentRoom(cockpit);
                        List<Crew> crewInRoom = cockpit.getCrew();
                        crewInRoom.add(crew);
                        cockpit.getTiles().get(0).setStandingOnMe(crew);
                        cockpit.setCrew(crewInRoom);
                        rooms.add(cockpit);
                    }
                    //otherwise
                    else {
                        Room room = new Room(UUID.randomUUID().hashCode(), 0, 100, i, new ArrayList<Crew>(), new ArrayList<Tile>());
                        room.setTiles(tiles);
                        rooms.add(room);
                    }
                }
                return new Ship(UUID.randomUUID().hashCode(), username, shipType, 40, 10, 20, 7, 0,
                        0, 0, 0, planet, 0, 100, rooms, inventory, false, false, false, false);
            case DEATH_STAR:
                Tile t = new Tile(UUID.randomUUID().hashCode(),0,0);
                List<Tile> tiles = new ArrayList<>();
                tiles.add(t);
                System r = new System(UUID.randomUUID().hashCode(),0,0,0,new ArrayList<Crew>(),tiles,10,10,0,SystemType.WEAPON_SYSTEM,new ArrayList<Weapon>());
                rooms = new ArrayList<>();
                //crew member
                Crew crew = new Crew(UUID.randomUUID().hashCode(), "crew2", 1000, 1000, crewStats, 30, "[ENEMY]");
                crew.setTile(t);
                crew.setCurrentRoom(r);
                List<Crew> crewList = new ArrayList<>();
                crewList.add(crew);
                r.setCrew(crewList);
                //weapons
                List<Weapon> weapons = new ArrayList<>();
                weapons.add(new Weapon(UUID.randomUUID().hashCode(), WeaponType.LASER, 1, 1, 0, 1, 0,
                        (float) 1.0, (float) 0.3, 0, (float) 0.3, 1, 1, "Laser", 30));
                weapons.add(new Weapon(UUID.randomUUID().hashCode(), WeaponType.BOMB, 2, 6, 0, 1, 1,
                        (float) 1.5, (float) 0.18, 4, (float) 1.5, 3, 1, "Bomb", 35));
                System s = new System(UUID.randomUUID().hashCode(),0,0,0,crewList,tiles,10,10000,0,SystemType.WEAPON_SYSTEM,weapons);
                s.setEnergy(10000);
                rooms.add(s);
                crew.setCurrentRoom(s);
                crew.setTile(t);
                return new Ship(UUID.randomUUID().hashCode(),"[ENEMY]",ShipType.DEATH_STAR,100,1000,1000,1000,4,4,10,
                        0,planet,10,100,rooms,new ArrayList<Weapon>(),false,false,false,false);
            default:
                return null;
        }
    }
}
