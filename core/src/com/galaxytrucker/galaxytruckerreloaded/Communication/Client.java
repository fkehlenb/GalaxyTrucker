package com.galaxytrucker.galaxytruckerreloaded.Communication;

import lombok.NonNull;

import java.net.Socket;

public class Client {

    /** Client socket for network communication */
    private Socket socket;

    /** ClientControllerCommunicator for logic handling */
    private ClientControllerCommunicator clientControllerCommunicator;

    /**
     * - Request and Response from and to the server.
     * */
    public String sendAndReceive(){
        // View clicks something
        // Controller issues a request
        // Client ships request to server
        // receive response
        // give response to controller
        // update views
        // return server response as string
        return null;
    }

    /** Constructor
     *  Loop for receiving Data
     * */
    public Client(@NonNull String ipAddress, @NonNull int port) throws IllegalArgumentException{
        clientControllerCommunicator = new ClientControllerCommunicator();
        try {
            this.socket = new Socket(ipAddress,port);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Couldn't initialize connection to server");
        }
    }
}
