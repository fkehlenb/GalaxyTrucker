package com.galaxytrucker.galaxytruckerreloaded.Communication;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.Getter;
import lombok.NonNull;

import java.io.*;
import java.net.Socket;

/** This class handles the client-side networking */
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
     * @return whether the client is allowed to login or not
     *
     * @throws IllegalArgumentException on error
     */
    public boolean login(String username) throws IllegalArgumentException {
        try {
            send.println("[LOGIN]:" + username);
            boolean successfulLogin = receive.readLine().equals("true");
            if (successfulLogin) {
                send.println("[GIVE-ME-SHIP]");
                myShip = (Ship) receiveObject.readObject();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

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
            throw new IllegalArgumentException("Couldn't initialize connection to server");
        }
    }
}
