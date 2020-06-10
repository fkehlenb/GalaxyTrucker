package com.galaxytrucker.galaxytruckerreloaded.Server;


import java.net.Socket;

/**
 * This class creates the game server and handles storing the data
 */
public class Server {

    /** Server service communicator */
    private ServerServiceCommunicator serverServiceCommunicator;

    /** Main method */
    public static void main(String[] args){}

    /** Client handler
     * @param socket - the client's socket */
    private void clientHandler(Socket socket){

    }

    /** Receive some data from the client and return a response
     * @param socket - the client socket */
    private void receiveAndSendData(Socket socket){
    }

}
