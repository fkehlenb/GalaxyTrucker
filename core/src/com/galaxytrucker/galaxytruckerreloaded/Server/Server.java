package com.galaxytrucker.galaxytruckerreloaded.Server;


import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class creates the game server and handles storing the data
 */
@SuppressWarnings("all") // TODO REMOVE THIS WHEN TEST PHASE IS DONE
public class Server implements Runnable{

    /** Server socket for network communication  */
    private ServerSocket serverSocket;

    /** Server thread */
    private Thread serverThread;

    /** ServerServiceCommunicator */
    private ServerServiceCommunicator serverServiceCommunicator;

    /** Server port */
    private int port;

    /** Is the server running? */
    private boolean running = true;

    /** Run the server (USE THIS) */
    public static void runServer(){
        Server server = new Server();
        server.setPort(5050);
        server.serverServiceCommunicator = new ServerServiceCommunicator();
        new Thread(server).start();
        try {
            Thread.sleep(1000);
        }
        catch (Exception f){
            f.printStackTrace();
        }
    }

    /** Main method (TESTING ONLY) */ // TODO REMOVE WHEN DONE
    public static void main(String[] args){
        Server server = new Server();
        server.setPort(5050);
        server.serverServiceCommunicator = new ServerServiceCommunicator();
        new Thread(server).start();
        try {
            Thread.sleep(1000);
        }
        catch (Exception f){
            f.printStackTrace();
        }
        Client client = new Client("localhost",5050);
        boolean a = client.login("ahmad", ShipType.DEFAULT);
    }

    /** Start server on specified port
     * @param port - the port to bind */
    private void bindPort(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Is the server running? */
    private synchronized boolean isRunning(){
        return this.running;
    }

    /** Kill server */
    public synchronized void killServer(){
        try {
            this.serverSocket.close();
            this.running = false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Get the serverServiceCommunicator */
    public synchronized ServerServiceCommunicator getServerServiceCommunicator(){
        return this.serverServiceCommunicator;
    }

    /** Run the server */
    public void run(){
        synchronized (this){
            this.serverThread = Thread.currentThread();
        }
        bindPort(this.port);
        System.out.println("Server initialized on " + serverSocket.getInetAddress().getHostAddress() + ":" + this.port + ", listening for connections...");
        while (isRunning()){
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted new connection from "+ clientSocket.getInetAddress().getHostAddress());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Server server = this;
            new Thread(
                  new ClientHandler(clientSocket,server)
            ).start();
        }
    }

    /** Set the server port
     * @param port - the server port */
    public void setPort(int port){
        this.port = port;
    }
}
