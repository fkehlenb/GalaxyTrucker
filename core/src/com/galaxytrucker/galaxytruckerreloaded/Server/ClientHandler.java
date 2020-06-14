package com.galaxytrucker.galaxytruckerreloaded.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    /** Client socket */
    private Socket clientSocket;

    /** The server */
    public Server server;

    /** ServerServiceCommunicator */
    private ServerServiceCommunicator serverServiceCommunicator;

    /** Used to send data */
    private PrintWriter send;

    /** Used to receive data */
    private BufferedReader receive;

    /** ObjectOutputStream for sending objects */
    private ObjectOutputStream sendObject;

    /** ObjectInputStream for receiving objects */
    private ObjectInputStream receiveObject;

    /** Constructor
     * @param clientSocket - the client's socket
     * @param server - the server */
    public ClientHandler(Socket clientSocket,Server server) throws IllegalArgumentException {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            sendObject = new ObjectOutputStream(clientSocket.getOutputStream());
            receiveObject = new ObjectInputStream(clientSocket.getInputStream());
            send = new PrintWriter(clientSocket.getOutputStream(),true);
            receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /** Run */
    public void run(){

    }
}
