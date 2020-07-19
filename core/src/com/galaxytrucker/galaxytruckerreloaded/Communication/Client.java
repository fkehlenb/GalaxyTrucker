package com.galaxytrucker.galaxytruckerreloaded.Communication;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.Getter;
import lombok.NonNull;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/** This class handles the client side networking */
public class Client {

    /**
     * Client socket for network communication
     */
    private Socket socket;

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
     * The client's ship
     */
    @Getter
    private Ship myShip;

    /** Client map */
    @Getter
    private Overworld overworld;

    /** Ship type */
    private Enum<ShipType> shipType;

    /**
     * Send a request to the server
     *
     * @param requestObject - the request object
     * @return the server's response
     *
     * @throws IllegalArgumentException on exception
     */
    public ResponseObject sendAndReceive(RequestObject requestObject) throws IllegalArgumentException {
        try {
            sendObject.writeObject(requestObject);
            return (ResponseObject) receiveObject.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Login
     *
     * @param username - the username of the user to login
     *
     * @param shipType
     * @return whether the client is allowed to login or not
     *
     * @throws IllegalArgumentException on error
     */
    public boolean login(String username, ShipType shipType, int difficulty) throws IllegalArgumentException {
        try {
            // ==================== LOG-IN ====================
            send.println("[LOGIN]:" + username);
            String received = receive.readLine();
            // ==================== EXCEPTION ====================
            if (received.contains("[EXCEPTION]:[LOGIN]")){
                System.out.println("<CLIENT>:[EXCEPTION DURING LOGIN! TERMINATING...]");
                throw new IllegalArgumentException();
            }
            // ==================== SUCCESSFUL LOGIN ====================
            else if (received.equals("true")){
                System.out.println("<CLIENT>:[LOGIN SUCCESSFUL]:[USERNAME]:" + username);
                received = receive.readLine();
                // ==================== NEW GAME ====================
                if (received.equals("[NEW-GAME]")){
                    System.out.println("<CLIENT>:[NEW-GAME]:[USERNAME]:"+username+":[SHIP-TYPE]:"+shipType+":[DIFFICULTY]:"+difficulty);
                    send.println(difficulty);
                    sendObject.writeObject(shipType);
                    received = receive.readLine();
                }
                // ==================== FETCH SHIP ====================
                if (received.equals("[FETCH-SHIP]")){
                    System.out.println("<CLIENT>:[FETCH-SHIP]:[USERNAME]:"+username);
                    try {
                        this.myShip = (Ship) receiveObject.readObject();
                        System.out.println("<CLIENT>:[RECEIVED-SHIP]:[USERNAME]:"+username+":[SHIP-ID]:"+myShip.getId());
                    }
                    catch (Exception f){
                        f.printStackTrace();
                        System.out.println("<CLIENT>:[EXCEPTION]:[FETCH-SHIP]:[USERNAME]:"+username);
                        throw new IllegalArgumentException();
                    }
                    received = receive.readLine();
                }
                // ==================== FETCH MAP ====================
                if (received.equals("[FETCH-MAP]")){
                    try {
                        this.overworld = (Overworld) receiveObject.readObject();
                        System.out.println("<CLIENT>:[RECEIVED-MAP]:[USERNAME]:"+username+":[MAP-ID]:"+overworld.getId());
                    }
                    catch (Exception f){
                        f.printStackTrace();
                        System.out.println("<CLIENT>:[EXCEPTION]:[FETCH-MAP]:[USERNAME]:"+username);
                        throw new IllegalArgumentException();
                    }
                }
                return true;
            }
            // ==================== FAILED LOGIN ====================
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    //TODO logout function

    /**
     * Constructor
     *
     * @param ipAddress - the ipAddress of the server
     * @param port      - the server port
     *
     * @throws IllegalArgumentException on error
     */
    public Client(@NonNull String ipAddress, @NonNull int port) throws IllegalArgumentException {
        try {
            socket = new Socket(ipAddress, port);
            send = new PrintWriter(socket.getOutputStream(), true);
            receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendObject = new ObjectOutputStream(socket.getOutputStream());
            receiveObject = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("<CLIENT>:[Couldn't initialize connection to server]");
        }
    }
}
