package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.*;

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
            "SIRIUS", "HOMESTREAD", "ALPHA CENTAURI", "GLIESE", "404 NOT FOUND", "KEPLER", "TOI", "USCO1621b","OGLE","WASP","WENDELSTEIN"
            ,"EPIC","ARION","DIMIDIUM","GALILEO","DAGON","SMETRIOS","THESTIAS","SAMH","SAFFAR","ARBER","MADRIU","AWASIS","DITSO"};

    /**
     * Planet name list
     */
    private List<String> planetNames;

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
        System.out.println("\n========== HANDLER RUNNING ==========\n");
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
                        Overworld overworld = new Overworld(UUID.randomUUID().hashCode(), UUID.randomUUID().hashCode(), username);
                        this.seed = overworld.getSeed();
                        //TODO PLANET CREATION, ADD TO OVERWORLD

                        //========================================
                        ShipType shipType = (ShipType) receiveObject.readObject();
                        //TODO SHIP STATS
                        switch (shipType) {
                            case DEFAULT:
                                break;
                        }
                        //=========================
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
            send.println("[EXCEPTION]:[LOGIN]:[USERNAME]:" + username);
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
        String newName = names.get(random.nextInt(names.size()));
        if (usedNames.contains(newName)) {
            getPlanetName(names, usedNames, seed);
        }
        return newName;
    }
}
