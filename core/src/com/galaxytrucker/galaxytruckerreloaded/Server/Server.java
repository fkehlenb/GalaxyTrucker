package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.CrewService;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class creates the game server and handles storing the data
 */
public class Server {

    /**
     * The database
     */
    private Database database;

    /**
     * Start the server
     */
    public static void main(String[] args) {
        Database database = new Database();
        database.setup();
    }

    /**
     * Client handler handles clients connected
     */
    public void client_handler(Socket socket) {
    }

    /**
     * Send some data
     *
     * @param stream - input stream of data to send
     */
    public void sendPackets(InputStream stream) {
    }

    /**
     * Receive some data
     *
     * @return outputstream of received data
     */
    public OutputStream receivePackets() {
        return null;
    }

    /**
     * Validate packets
     */
    public void validatePackets() {
    }

    /**
     * Initialize database
     */
    private void initializeDatabase() {

    }
}
