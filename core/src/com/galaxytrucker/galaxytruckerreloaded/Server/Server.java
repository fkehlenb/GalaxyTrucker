package com.galaxytrucker.galaxytruckerreloaded.Server;


import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Server.Opponent.NormalAI;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.*;
import lombok.Setter;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class creates the game server and handles storing the data
 */
@SuppressWarnings("all") // TODO REMOVE THIS WHEN TEST PHASE IS DONE
public class Server implements Runnable{

    /** Server instance */
    private static Server instance = null;

    /** Get the instance */
    public static Server getInstance(){
        if (instance == null){
            Server.runServer();
        }
        return instance;
    }

    /** Server socket for network communication  */
    private ServerSocket serverSocket;

    /** Server thread */
    private Thread serverThread;

    /** ServerServiceCommunicator */
    @Setter
    private ServerServiceCommunicator serverServiceCommunicator;

    /** Server port */
    private int port;

    /** Is the server running? */
    private boolean running = true;

    /** Run the server (USE THIS) */
    public static void runServer(){
        instance = new Server();
        instance.setPort(5050);
        instance.serverServiceCommunicator = ServerServiceCommunicator.getInstance();
        new Thread(instance).start();
        try {
            Thread.sleep(1000);
            BattleServiceDAO.getInstance();
            CrewDAO.getInstance();
            OverworldDAO.getInstance();
            PlanetDAO.getInstance();
            RequestObjectDAO.getInstance();
            ResponseObjectDAO.getInstance();
            RoomDAO.getInstance();
            ShipDAO.getInstance();
            TileDAO.getInstance();
            TraderDAO.getInstance();
            UserDAO.getInstance();
            WeaponDAO.getInstance();
            CrewService.getInstance();
            PlanetRewardService.getInstance();
            PVPService.getInstance();
            SystemService.getInstance();
            TraderService.getInstance();
            TraderService.getInstance();
            UserService.getInstance();
            WeaponService.getInstance();
            NormalAI.getInstance();
        }
        catch (Exception f){
            f.printStackTrace();
        }
    }

    /** Start server on specified port
     * @param port - the port to bind */
    private void bindPort(int port) {
        try {
            this.serverSocket = new ServerSocket(port, 0, InetAddress.getLoopbackAddress());
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
                clientSocket.setSoTimeout(0);
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
