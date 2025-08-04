package controller;
// stop auction korleo timer off hocche na..

import Network.AuctionClient;
import application.Main;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Club;
import model.CreateFXML;
import controller.auctionRelated.*;
import model.Player;
import model.Session;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class startAuctionController {

    @FXML
    private AnchorPane playerCardAnchorPane;

    @FXML
    private TextField playerNameField;
    @FXML
    private TextField countryNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField matchesPlayedField;
    @FXML
    private TextField ballsFacedField;
    @FXML
    private TextField fiftiesField;
    @FXML
    private TextField runsScoredField;
    @FXML
    private TextField runsConcededField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField jerseyNumberField;
    @FXML
    private TextField wicketsField;
    @FXML
    private TextField centuriesField;
    @FXML
    private TextField overField;
    @FXML
    private TextField hattricksField;
    @FXML
    private TextField basePriceField;
    @FXML
    private TextField clubNameField;
    @FXML
    private TextField salaryField;
    @FXML
    private Button startBiddingButton;
    @FXML
    private Button stopBiddingButton;
    @FXML
    private Button stopAuctionButton;

    private List<Player> players = new ArrayList<>();
    private int playerCount = 0;
    private boolean isBiddingPaused = false;
    private boolean isStarted = false, isEnded = false;
    private int timeLeft, startTime = 12;
    private String highBidder = "";
    private int currentBid = 0;
    private int ekdomShuru = 0;

    private Timeline countDown;
    private static final int auctionTime = 12;
    private long auctionStartTimeMillis;

    @FXML
    private Label timerLabel, currentBidLabel, bidLeaderLabel;

    private AuctionClient client;

    @FXML
    public void initialize() {
        client = new AuctionClient(this);
        startBiddingButton.setDisable(true);
        stopBiddingButton.setDisable(false);
        stopAuctionButton.setDisable(true);
    }

    private void sendDataToClients(AuctionData auctionData) {
        if (client != null && client.isConnected()) {
            client.sendAuctionData(auctionData);
        } else {
            System.err.println("Client not connected ...");
        }
    }

    public void updateUIWithAuctionData(AuctionData auctionData) {
        if (auctionData.getCurrentBid() > currentBid) {
            currentBid = auctionData.getCurrentBid();
            highBidder = auctionData.getCurrentBidder();

            sendDataToClients(new AuctionData(
                    players,
                    playerCount,
                    timeLeft,
                    currentBid,
                    highBidder,
                    isBiddingPaused,
                    isStarted,
                    isEnded,
                    players.get(playerCount),
                    false,
                    null,
                    0
            ));
        }
        currentBidLabel.setText("$" + String.format("%,d", currentBid));
        bidLeaderLabel.setText(highBidder.isEmpty() ? "None" : highBidder);
    }

    @FXML
    public void startAuction() {
        ekdomShuru = 1;
        stopAuctionButton.setDisable(false);
        auctionStartTimeMillis = System.currentTimeMillis();
        if (isStarted) {
            showPopup("⚠ AUCTION ALREADY STARTED!", 400, 150);
            return;
        }

        if (players.isEmpty()) {
//            loadPlayersFromFile("players.txt");

            // UPDATE BY RAFI:
            players = Main.playerDatabase.getUnsoldPlayers();
        }

        isStarted = true;
        isEnded = false;
        playerCount = 0;

        if (!players.isEmpty()) {
            Player firstPlayer = players.get(0);
            timeLeft = auctionTime;
            currentBid = firstPlayer.getBasePrice();
            highBidder = "";

            showPlayerDetails(firstPlayer);
            startTimer();
            sendDataToClients(new AuctionData(players, playerCount, startTime, players.get(0).getBasePrice(), highBidder, isBiddingPaused, isStarted, !isStarted, players.get(0), false, null, 0));
        }
    }

    private void startTimer() {
        if (countDown != null) countDown.stop();

        timerLabel.setText("Timer: " + timeLeft);

        countDown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (isBiddingPaused) return;
            System.out.println("⏱ Time left: " + timeLeft);
            timeLeft--;
            if (timeLeft <= 0) {
                countDown.stop();
                Player player = players.get(playerCount);

                String soldtoClub = null;
                int finalSalePrice = player.getBasePrice();
                boolean isSold = false;

                // UPDATE BY RAFI:
                if (!highBidder.isEmpty() && currentBid >= player.getBasePrice()) {
                    soldtoClub = highBidder;
                    finalSalePrice = currentBid;
                    player.setClub(soldtoClub);
                    player.setFinalBidPrice(finalSalePrice);
                    player.setSalary(finalSalePrice);

                    Club club = Main.clubDatabase.getClub(soldtoClub);
                    club.setBudget(club.getBudget() - finalSalePrice);

                    Main.playerDatabase.updatePlayerClub(player, soldtoClub);
                    Main.clubDatabase.addPlayerToClub(player, soldtoClub);


                    // risky modification:
                    Main.playerDatabase.uploadInfoToFile();
                    Main.clubDatabase.uploadInfoToFile();

                    isSold = true;
                    System.out.println("SERVER: Player " + player.getName() + " sold to " + soldtoClub + " for $" + String.format("%,d", finalSalePrice));
                } else {
                    player.setClub("");
                    player.setFinalBidPrice(player.getBasePrice());
                    System.out.println("SERVER: Player " + player.getName() + " unsold (no valid bids or bid below base price).");
                }

                sendDataToClients(new AuctionData(
                        players,
                        playerCount,
                        0,
                        finalSalePrice,
                        player.getClub() != null ? player.getClub() : "",
                        isBiddingPaused,
                        isStarted,
                        isEnded,
                        player,
                        isSold,
                        soldtoClub,
                        finalSalePrice
                ));

                highBidder = "";
                currentBid = 0;

                new Timeline(new KeyFrame(Duration.seconds(2), event -> nextPlayer())).play();
            } else {
                timerLabel.setText("Timer: " + timeLeft);
                sendDataToClients(new AuctionData(
                        players,
                        playerCount,
                        timeLeft,
                        currentBid > 0 ? currentBid : players.get(playerCount).getBasePrice(),
                        highBidder,
                        isBiddingPaused,
                        isStarted,
                        isEnded,
                        players.get(playerCount),
                        false,
                        null,
                        0
                ));
            }
        }));
        countDown.setCycleCount(Timeline.INDEFINITE);
        countDown.play();
    }

    public void pauseTimerForSeconds(int seconds) {
        if (countDown != null) {
            countDown.pause();
            isBiddingPaused = true;
            Timeline resumeTimer = new Timeline(new KeyFrame(Duration.seconds(seconds), e -> {
                resumeTimer();
            }));
            resumeTimer.setCycleCount(1);
            resumeTimer.play();
        }
    }

    public void resumeTimer() {
        if (countDown != null) {
            countDown.play();
            isBiddingPaused = false;
            sendDataToClients(new AuctionData(
                    players,
                    playerCount,
                    timeLeft,
                    currentBid,
                    highBidder,
                    isBiddingPaused,
                    isStarted,
                    isEnded,
                    players.get(playerCount),
                    false, null, 0
            ));
        }
    }

    @FXML
    public void stopAuction() {
        if (!isStarted) {
            showPopup("Auction not started yet!", 300, 120);
            return;
        }
        if (countDown != null) countDown.stop();
        isStarted = false;
        isEnded = true;

        long auctionEndTimeMillis = System.currentTimeMillis();
        int auctionDuration = (int) ((auctionEndTimeMillis - auctionStartTimeMillis) / 1000);
        List<Player> soldPlayers = new ArrayList<>();
        for (Player p : players) {
            if (p.getClub() != null && !p.getClub().isEmpty()) {
                soldPlayers.add(p);
            }
        }

        AuctionRecord record = new AuctionRecord(soldPlayers.size(), auctionDuration, soldPlayers);
        AuctionHistoryManager history = new AuctionHistoryManager();
        history.addAuctionRecord(record);

        if (playerCount < players.size()) {
            sendDataToClients(new AuctionData(players, playerCount, 0, players.get(playerCount).getBasePrice(), highBidder, isBiddingPaused, isStarted, !isStarted, players.get(playerCount), false, null, 0));
        }

        showPopup("AUCTION ENDED!", 250, 100);
        new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            try {
                backToDash();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        })).play();
    }

    @FXML
    public void nextPlayer() {
        if (countDown != null) countDown.stop();

        playerCount++;
        currentBid = 0;
        highBidder = "";
        isBiddingPaused = false;

        if (playerCount >= players.size()) {
            stopAuction();
        } else {
            Player next = players.get(playerCount);
            timeLeft = auctionTime;
            currentBid = next.getBasePrice();

            showPlayerDetails(next);
            startTimer();

            sendDataToClients(new AuctionData(
                    players,
                    playerCount,
                    timeLeft,
                    next.getBasePrice(),
                    "",
                    isBiddingPaused,
                    isStarted,
                    !isStarted,
                    next, false, null, 0
            ));
        }
    }

    @FXML
    public void startBidding() {
        if (!isStarted) {
            showPopup("Auction not started yet!", 300, 120);
            return;
        }
        startBiddingButton.setDisable(true);
        stopBiddingButton.setDisable(false);
        isBiddingPaused = false;
        if (countDown != null) countDown.play();
        else if (!players.isEmpty()) {
            showPlayerDetails(players.get(playerCount));
            startTimer();
        }
        if (playerCount < players.size()) {
            sendDataToClients(new AuctionData(players, playerCount, timeLeft, players.get(playerCount).getBasePrice(), highBidder, isBiddingPaused, isStarted, !isStarted, players.get(playerCount), false, null, 0));
        }
    }

    @FXML
    public void stopBidding(ActionEvent event) {
        if (!isStarted) {
            showPopup("Auction not started yet!", 300, 120);
            return;
        }
        startBiddingButton.setDisable(false);
        stopBiddingButton.setDisable(true);
        isBiddingPaused = true;
        if (countDown != null) countDown.pause();
        if (playerCount < players.size()) {
            sendDataToClients(new AuctionData(players, playerCount, timeLeft, players.get(playerCount).getBasePrice(), highBidder, isBiddingPaused, isStarted, !isStarted, players.get(playerCount), false, null, 0));
        }
    }

    public void backToDash() throws IOException {
        String userType = Session.getUserType();
        System.out.println("back tiptesi.");
        if (userType.equalsIgnoreCase("Admin")) {
            Main.setRoot("AdminDashboard.fxml");
        } else if (userType.equalsIgnoreCase("Viewer")) {
            Main.setRoot("ViewerDashboard.fxml");
        } else if (userType.equalsIgnoreCase("Club")) {
            Main.setRoot("ClubDashboard.fxml");
        }
        if (client != null) client.disconnect();
    }

    @FXML
    public void backToDash(ActionEvent actionEvent) throws IOException {
        if (ekdomShuru == 0) {
            backToDash();
            return;
        }
        showConfirmationPopup();
    }

    private void showConfirmationPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Confirm Action");
        popupStage.setResizable(false);

        Label messageLabel = new Label("Are you sure you want to end the auction?");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Button yesButton = new Button("Yes, End Auction");
        yesButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
        yesButton.setOnAction(e -> {
            sendDataToClients(new AuctionData(
                    players,
                    playerCount,
                    timeLeft,
                    currentBid,
                    highBidder,
                    isBiddingPaused,
                    isStarted,
                    true,
                    players.get(playerCount),
                    false,
                    null,
                    0
            ));
            if (client != null) client.disconnect();
            stopAuction();
            try {
                backToDash();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            popupStage.close();
        });

        Button noButton = new Button("No, Continue");
        noButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
        noButton.setOnAction(e -> {
            popupStage.close();
        });

        HBox buttonLayout = new HBox(20);
        buttonLayout.getChildren().addAll(yesButton, noButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(messageLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 30; -fx-border-color: #66bb6a; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene scene = new Scene(layout, 450, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

//    private void loadPlayersFromFile(String fileName) {
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                Player p = parsePlayer(line);
//                if (p != null) players.add(p);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Player parsePlayer(String line) {
//        String[] parts = line.split(",");
//        if (parts.length != 19) return null;
//        try {
//            Player p = new Player();
//            p.setName(parts[0]);
//            p.setCountry(parts[1]);
//            p.setAge(Integer.parseInt(parts[2]));
//            p.setHeight(Double.parseDouble(parts[3]));
//            p.setClub(parts[4]);
//            p.setPosition(parts[5]);
//            p.setNumber(Integer.parseInt(parts[6]));
//            p.setSalary(Integer.parseInt(parts[7]));
//            p.setMatchesPlayed(Integer.parseInt(parts[8]));
//            p.setRunsScored(Integer.parseInt(parts[9]));
//            p.setBallsFaced(Integer.parseInt(parts[10]));
//            p.setFifties(Integer.parseInt(parts[11]));
//            p.setCenturies(Integer.parseInt(parts[12]));
//            p.setHattricks(Integer.parseInt(parts[13]));
//            p.setOversBowled(Double.parseDouble(parts[14]));
//            p.setRunsGiven(Integer.parseInt(parts[15]));
//            p.setWicketsTaken(Integer.parseInt(parts[16]));
//            p.setBasePrice(Integer.parseInt(parts[17]));
//            p.setFinalBidPrice(Integer.parseInt(parts[18]));
//            return p;
//        } catch (NumberFormatException e) {
//            System.err.println("Invalid player data: " + line);
//            return null;
//        }
//    }

    private void showPlayerDetails(Player player) {
        if (player != null) {
            playerNameField.setText(player.getName());
            countryNameField.setText(player.getCountry());
            ageField.setText(String.valueOf(player.getAge()));
            heightField.setText(String.valueOf(player.getHeight()));
            clubNameField.setText(player.getClub() != null && !player.getClub().isEmpty() ? player.getClub() : "N/A");
            positionField.setText(player.getPosition());
            jerseyNumberField.setText(String.valueOf(player.getNumber()));
            salaryField.setText(String.format("%,d", player.getSalary())); // Display Player's actual salary
            matchesPlayedField.setText(String.valueOf(player.getMatchesPlayed()));
            runsScoredField.setText(String.valueOf(player.getRunsScored()));
            ballsFacedField.setText(String.valueOf(player.getBallsFaced()));
            fiftiesField.setText(String.valueOf(player.getFifties()));
            centuriesField.setText(String.valueOf(player.getCenturies()));
            hattricksField.setText(String.valueOf(player.getHattricks()));
            overField.setText(String.valueOf(player.getOversBowled()));
            runsConcededField.setText(String.valueOf(player.getRunsGiven()));
            wicketsField.setText(String.valueOf(player.getWicketsTaken()));
            basePriceField.setText(String.format("%,d", player.getBasePrice()));
        } else {
            playerNameField.setText("");
            countryNameField.setText("");
            ageField.setText("");
            heightField.setText("");
            clubNameField.setText("");
            positionField.setText("");
            jerseyNumberField.setText("");
            salaryField.setText("");
            matchesPlayedField.setText("");
            runsScoredField.setText("");
            ballsFacedField.setText("");
            fiftiesField.setText("");
            centuriesField.setText("");
            hattricksField.setText("");
            overField.setText("");
            runsConcededField.setText("");
            wicketsField.setText("");
            basePriceField.setText("");
        }

    }

    private void showPopup(String message, int width, int height) {
        try {
            Stage popup = new Stage();

            // Main message
            Label msg = new Label(message);
            msg.setWrapText(true);
            msg.setAlignment(Pos.CENTER);
            msg.setStyle("-fx-text-fill: #333333;" +
                    "-fx-font-size: 16px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-family: 'Arial', sans-serif;");

            // Simple container
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(30));
            container.getChildren().add(msg);

            // Clean background
            container.setStyle("-fx-background-color: #f8f9fa;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #e9ecef;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 12;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 2);");

            Scene scene = new Scene(container, width, height);
            popup.setScene(scene);
            popup.setTitle("Notice");
            popup.setResizable(false);
            popup.show();

            // Auto-close timer
            Timeline delay = new Timeline(new KeyFrame(Duration.seconds(3), e -> popup.close()));
            delay.play();

        } catch (Exception e) {
            System.err.println("Error showing popup: " + e.getMessage());
        }
    }
}