package com.galaxytrucker.galaxytruckerreloaded.Communication;

import java.net.Socket;

public class Client {

    private Socket socket;

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
    public Client(){
        clientControllerCommunicator = new ClientControllerCommunicator();
        // Connect to server
        try {
            // Send login request
            // receive true/false
            // send ship request
            //clientControllerCommunicator.setClientShip();
        }
        catch (Exception e){

        }
        while (true){
            sendAndReceive();
        }
    }
}