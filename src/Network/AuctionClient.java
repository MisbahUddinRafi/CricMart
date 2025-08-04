package Network;

import controller.AuctionData;
import controller.clubAuctionController;
import controller.startAuctionController;
import controller.viewerAuctionController;
import javafx.application.Platform;

import java.io.*;
import java.net.*;

public class AuctionClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isConnected = false;

    private clubAuctionController clubController;
    private startAuctionController adminController;
    private viewerAuctionController viewerController;
    private Thread readerThread;

    // Constructor overloads for different controller types
    public AuctionClient(clubAuctionController controller) {
        this.clubController = controller;
        connect();
    }

    public AuctionClient(startAuctionController controller) {
        this.adminController = controller;
        connect();
    }

    public AuctionClient(viewerAuctionController controller) {
        this.viewerController = controller;
        connect();
    }

    // Connects to the auction server and initializes streams
    private void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            isConnected = true;

            System.out.println("Connected to auction server at " + SERVER_IP + ":" + SERVER_PORT);
            startReaderThread();

        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            isConnected = false;
        }
    }

    // Starts a background thread to read incoming server messages
    private void startReaderThread() {
        readerThread = new Thread(() -> {
            try {
                while (isConnected && !Thread.currentThread().isInterrupted()) {
                    Object message = in.readObject();
                    handleServerMessage(message);
                }
            } catch (EOFException e) {
                System.out.println("Server disconnected");
            } catch (IOException | ClassNotFoundException e) {
                if (isConnected) {
                    System.err.println("Error reading from server: " + e.getMessage());
                }
            } finally {
                isConnected = false;
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

    // Handles incoming AuctionData from server and updates UI
    private void handleServerMessage(Object message) {
        if (message instanceof AuctionData auctionData) {
            Platform.runLater(() -> {
                if (clubController != null) {
                    try {
                        clubController.updateUIWithAuctionData(auctionData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (adminController != null) {
                    adminController.updateUIWithAuctionData(auctionData);
                } else if (viewerController != null) {
                    viewerController.updateUIWithAuctionData(auctionData);
                }
            });
        }
    }

    // Sends auction data to server
    public void sendAuctionData(AuctionData auctionData) {
        if (!isConnected || out == null) {
            System.err.println("Not connected to server. Cannot send data.");
            return;
        }

        try {
            out.writeObject(auctionData);
            out.flush();
            System.out.println("Sent auction data to server - Bid: $" +
                    String.format("%,d", auctionData.getCurrentBid()) +
                    " by " + auctionData.getCurrentBidder());
        } catch (IOException e) {
            System.err.println("Error sending auction data: " + e.getMessage());
            isConnected = false;
        }
    }

    // Returns connection status
    public boolean isConnected() {
        return isConnected && socket != null && !socket.isClosed();
    }

    // Disconnects from server and closes resources
    public void disconnect() {
        isConnected = false;

        try {
            if (readerThread != null && readerThread.isAlive()) {
                readerThread.interrupt();
            }

            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();

            System.out.println("Disconnected from auction server");

        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
    }

    public String getServerHost() {
        return SERVER_IP;
    }

    public int getServerPort() {
        return SERVER_PORT;
    }
}
