package Network;

import application.Main;
import controller.AuctionData;
import model.Player;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuctionServer {
    private static final int PORT = 12345;

    private ServerSocket serverSocket;
    private List<ClientConnection> clients;
    private AuctionData currentAuctionData;
    private boolean isRunning;

    public AuctionServer() {
        clients = new CopyOnWriteArrayList<>();
        isRunning = false;
    }

    // Starts the auction server and listens for client connections
    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;

            System.out.println("Auction Server started on port " + PORT);
            System.out.println("Waiting for clients to connect...");

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    // Create a new thread to handle the client
                    ClientConnection clientConn = new ClientConnection(clientSocket, this);
                    clients.add(clientConn);
                    new Thread(clientConn).start();

                    System.out.println("New client connected from: " +
                            clientSocket.getInetAddress().getHostAddress() +
                            " | Total clients: " + clients.size());

                } catch (IOException e) {
                    if (isRunning) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server failed to start: " + e.getMessage());
        }
    }

    // Broadcasts the latest auction data to all connected clients
    public synchronized void broadcastAuctionData(AuctionData auctionData) {
        currentAuctionData = auctionData;

        System.out.println("Broadcasting to " + clients.size() + " clients: " +
                "Bid=$" + String.format("%,d", auctionData.getCurrentBid()) +
                " by " + auctionData.getCurrentBidder() +
                " | Timer: " + auctionData.getTimeLeft() + "s");

        for (ClientConnection client : clients) {
            if (!client.sendAuctionData(auctionData)) {
                clients.remove(client);
                System.out.println("Removed disconnected client. Active clients: " + clients.size());
            }
        }
    }

    // Returns the current auction data
    public synchronized AuctionData getCurrentAuctionData() {
        return currentAuctionData;
    }

    // Removes a client from the active client list
    public synchronized void removeClient(ClientConnection client) {
        clients.remove(client);
        System.out.println("Client disconnected. Active clients: " + clients.size());
    }

    // Gracefully shuts down the server
    public void stop() {
        isRunning = false;

        for (ClientConnection client : clients) {
            client.disconnect();
        }
        clients.clear();

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }

        System.out.println("Server stopped");
    }

    public static void main(String[] args) {
        AuctionServer server = new AuctionServer();

        // Add shutdown hook to clean up when program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down server gracefully...");
            server.stop();
        }));

        server.start();
    }

    // Inner class to handle each connected client
    private static class ClientConnection implements Runnable {
        private final Socket socket;
        private final AuctionServer server;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private final String clientId;
        private boolean isConnected;

        public ClientConnection(Socket socket, AuctionServer server) {
            this.socket = socket;
            this.server = server;
            this.clientId = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            this.isConnected = true;
        }

        @Override
        public void run() {
            try {
                // Setup streams
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                System.out.println("Client streams initialized: " + clientId);

                // Send current auction data to the client if exists
                AuctionData currentData = server.getCurrentAuctionData();
                if (currentData != null) {
                    sendAuctionData(currentData);
                }

                // Continuously listen for client messages
                while (isConnected) {
                    try {
                        Object message = in.readObject();
                        handleClientMessage(message);
                    } catch (EOFException e) {
                        break;
                    } catch (ClassNotFoundException | IOException e) {
                        if (isConnected) {
                            System.err.println("Error reading from client " + clientId + ": " + e.getMessage());
                        }
                        break;
                    }
                }

            } catch (IOException e) {
                System.err.println("Error handling client " + clientId + ": " + e.getMessage());
            } finally {
                cleanup();
            }
        }

        // Handles incoming auction data from client
        private void handleClientMessage(Object message) {
            if (message instanceof AuctionData auctionData) {
                System.out.println("Received auction data from " + clientId + ": Bid=$" +
                        String.format("%,d", auctionData.getCurrentBid()) +
                        " by " + auctionData.getCurrentBidder());

                server.broadcastAuctionData(auctionData);

            }
        }

        // Sends auction data to the client
        public boolean sendAuctionData(AuctionData data) {
            try {
                if (out != null && isConnected) {
                    out.writeObject(data);
                    out.flush();
                    return true;
                }
            } catch (IOException e) {
                System.err.println("Error sending data to client " + clientId + ": " + e.getMessage());
                isConnected = false;
            }
            return false;
        }

        // Disconnects the client
        public void disconnect() {
            isConnected = false;
        }

        // Cleans up resources after disconnect
        private void cleanup() {
            isConnected = false;
            server.removeClient(this);

            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error cleaning up client " + clientId + ": " + e.getMessage());
            }

            System.out.println("Client " + clientId + " cleaned up");
        }
    }
}
