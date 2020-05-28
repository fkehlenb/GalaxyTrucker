package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.j256.ormlite.support.ConnectionSource;

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

    /** Start serverServiceCommunicator
     * @param source - the database connection source */
    private void startServerServiceCommunicator(ConnectionSource source){

    }

    /** Receive some data from the client and return a response
     * @param socket - the client socket */
    private void receiveAndSendData(Socket socket){
    }

    /** Initialize database
     * @return the database connection source */
    private ConnectionSource initializeDatabase(){
        return null;
    }
}
