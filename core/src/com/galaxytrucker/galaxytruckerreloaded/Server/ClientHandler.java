package com.galaxytrucker.galaxytruckerreloaded.Server;

import java.net.Socket;

public class ClientHandler implements Runnable {

    /** Client socket */
    private Socket clientSocket;

    /** The server */
    public Server server;

    /** ServerServiceCommunicator */
    private ServerServiceCommunicator serverServiceCommunicator;

    /** Constructor
     * @param clientSocket - the client's socket
     * @param server - the server */
    public ClientHandler(Socket clientSocket,Server server){
        this.clientSocket = clientSocket;
        this.server = server;
    }

    /** Run */
    public void run(){

    }
}
