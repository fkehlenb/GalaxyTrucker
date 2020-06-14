package com.galaxytrucker.galaxytruckerreloaded.Communication;

import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.NonNull;

import java.io.*;
import java.net.Socket;

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
     * Send a request to the server
     * @return the server's response
     */
    public ResponseObject sendAndReceive(RequestObject requestObject) throws IllegalArgumentException {
        try {
            sendObject.writeObject(requestObject);
            return (ResponseObject) receiveObject.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Login
     */
    public boolean login(String username) throws IllegalArgumentException {
        try {
            send.println("[LOGIN]:" + username);
            return receive.readLine().equals("[ACCEPTED]");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Constructor
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
