package controller;

import Network.AuctionClient;
import application.Main;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import controller.auctionRelated.*;
import javafx.util.Duration;
import model.Club;
import model.ClubDatabase;
import model.Player;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class clubAuctionController implements Initializable {

    @FXML private VBox root;
    @FXML private Label budgetLabel;
    @FXML private Label bidLeaderLabel;
    @FXML private Label currentBidLabel;
    @FXML private Label timerLabel;

    @FXML private AnchorPane playerCardAnchorPane;

    @FXML private TextField playerNameField;
    @FXML private TextField countryNameField;
    @FXML private TextField ageField;
    @FXML private TextField matchesPlayedField;
    @FXML private TextField ballsFacedField;
    @FXML private TextField fiftiesField;
    @FXML private TextField runsScoredField;
    @FXML private TextField runsConcededField;
    @FXML private TextField positionField;
    @FXML private TextField heightField;
    @FXML private TextField jerseyNumberField;
    @FXML private TextField wicketsField;
    @FXML private TextField centuriesField;
    @FXML private TextField overField;
    @FXML private TextField hattricksField;
    @FXML private TextField basePriceField;
    @FXML private TextField clubNameField;
    @FXML private TextField salaryField;

    private AuctionClient client;
    private AuctionData auctiondata;
    private Club currentClub;
    private static String currentClubName;
    private ClubDatabase ClubDB;

    private boolean isStarted = false;
    private int ourLastBid = 0;

    private static final int auctionTime = 12;
    private boolean playerSaleProcessed = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            client = new AuctionClient(this);
            initializeCurrentClub();
            initializeUI();
        } catch (Exception e) {
            System.err.println("Error initializing auction client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeCurrentClub() {
        try {
            String username = Session.getUsername();
            if (username == null || username.isEmpty()) {
                throw new RuntimeException("User type error...");
            }

            // UPDATE BY RAFI:
//            ClubDB = new ClubDatabase();
//            currentClub = ClubDB.getClubByUsername(username);
            currentClub = Main.clubDatabase.getClubByUsername(username);

            if (currentClub != null) {
                currentClubName = currentClub.getClubName();
            }
            if (currentClubName == null) {
                currentClubName = "Unknown Club";
                System.err.println("Warning: Club name is null, using default");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeUI() {
        Platform.runLater(() -> {
            if (bidLeaderLabel != null) bidLeaderLabel.setText("None");
            if (currentBidLabel != null) currentBidLabel.setText("$0");
            if (timerLabel != null) {
                timerLabel.setText("NO AUCTION");
                timerLabel.setStyle("-fx-font-size: 12px;");
                timerLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold;");
            }
            if (currentClub != null && budgetLabel != null) {
                budgetLabel.setText("$" + String.format("%,d", currentClub.getBudget()));
            }
        });
    }

    public void updateUIWithAuctionData(AuctionData auctionData) throws IOException {
        if(auctionData.isAuctionEnded()){
            showPopup("AUCTION ENDED!",300,150);
            if(client!=null) client.disconnect();
            Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2), e -> handleBack()));
            delay.play();
            return;
        }
        if (auctionData == null) {
            System.err.println("Received null auction data");
            return;
        }
        this.auctiondata = auctionData;

        updateBidUI(auctionData.getCurrentBid(), auctionData.getCurrentBidder());
        updateTimerUI(auctionData);
        showPlayerDetails(auctionData.getCurrentPlayer());

        if (auctionData.isCurrentPlayerSold()) {
            int price = auctionData.getFinalSalePrice();
            String clubName = auctionData.getWinningClubName();

            Player player = Main.playerDatabase.getPlayer(auctionData.getCurrentPlayer().getName());
            player.setFinalBidPrice(price);
            player.setSalary(price);
            Main.playerDatabase.updatePlayerClub(player, clubName);
            Main.clubDatabase.addPlayerToClub(player, clubName);

/// ////////////////
            Main.clubDatabase.uploadInfoToFile();
            Main.playerDatabase.uploadInfoToFile();
            Main.clubDatabase.reloadFromFile();
            Main.playerDatabase.reloadFromFile();

            if (!playerSaleProcessed) {
                if (currentClubName.equalsIgnoreCase(auctionData.getWinningClubName())) {

                    // UPDATE BY RAFI:
                    Player soldPlayer = auctionData.getCurrentPlayer();
                    int finalPrice = auctionData.getFinalSalePrice();

                    if (soldPlayer != null && finalPrice > 0) {
                        processPurchase(soldPlayer, finalPrice);
                        playerSaleProcessed = true;
                    } else {
                        System.err.println("Invalid sold player or price received from server for purchase processing.");
                    }
                }
                ourLastBid = 0;
            }
        } else {
            playerSaleProcessed = false;
        }
    }

    private void updateBidUI(int bid, String bidder) {
        String displayBid = bid > 0 ? "$" + String.format("%,d", bid) : "$0";
        if (currentBidLabel != null) currentBidLabel.setText(displayBid);
        if (bidLeaderLabel != null) {
            if (bidder == null || bidder.isEmpty() || bid <= 0) {
                bidLeaderLabel.setText("None");
            } else {
                bidLeaderLabel.setText(bidder);
            }
        }
    }

    private void updateTimerUI(AuctionData auctionData) {
        if (timerLabel == null) return;
        int timeLeft = auctionData.getTimeLeft();
        if(auctionData.isBiddingPaused()){
            timerLabel.setText(timeLeft + "s");
            timerLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        }
        if (timeLeft > 0) {
            timerLabel.setText(timeLeft + "s");
            timerLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        } else if (timeLeft == 0 && auctionData.isCurrentPlayerSold()) {
            timerLabel.setText("SOLD!");
            timerLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        }else if (auctionData.isBiddingPaused()) {
            timerLabel.setText("PAUSED");
            timerLabel.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
        }else {
            timerLabel.setText("Finished");
            timerLabel.setStyle("-fx-text-fill: #946d6d; -fx-font-weight: bold;");
        }
    }

    private void updateBudgetUI(int newBudget) {
        if (budgetLabel != null) {
            budgetLabel.setText("$" + String.format("%,d", newBudget));
            budgetLabel.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");
            Timeline resetColor = new Timeline(new KeyFrame(Duration.seconds(1.5), e ->
                    budgetLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;")));
            resetColor.play();
        }
    }

    private void showPlayerDetails(Player player) {
        if (player != null) {
            playerNameField.setText(player.getName());
            countryNameField.setText(player.getCountry());
            ageField.setText(String.valueOf(player.getAge()));
            heightField.setText(String.valueOf(player.getHeight()));
            clubNameField.setText(player.getClub() != null && !player.getClub().isEmpty() ? player.getClub() : "N/A");
            positionField.setText(player.getPosition());
            jerseyNumberField.setText(String.valueOf(player.getNumber()));
            salaryField.setText(String.format("%,d", player.getSalary()));
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

    public void handlePlaceBid(ActionEvent actionEvent) {
        if (auctiondata == null || !auctiondata.isAuctionStarted() || auctiondata.getTimeLeft() < 1) {
            showPopup("No auction in progress", 350, 150);
            return;
        }
        if(auctiondata.isBiddingPaused()){
            showPopup("Bidding is paused. Can't bid",370,150);
            return;
        }
        if(auctiondata.getCurrentBidder().equalsIgnoreCase(currentClubName)){
            showPopup("already placed bid!",350,150);
            return;
        }
        int incre = 5000000;
        int newBid = auctiondata.getCurrentBid() + incre;

        if (currentClub == null || newBid > currentClub.getBudget()) {
            showPopup("NOT ENOUGH MONEY IN BUDGET!", 600, 150);
            return;
        }
        ourLastBid = newBid;
        sendBidToServer(newBid);
    }

    private void sendBidToServer(int newBid) {
        try {
            if (client == null || !client.isConnected()) {
                Platform.runLater(() -> showPopup("Not connected to server!", 400, 150));
                return;
            }
            AuctionData updatedAuctionData = new AuctionData(
                    new ArrayList<>(auctiondata.getPlayers()),
                    auctiondata.getCurrentIndex(),
                    auctiondata.getTimeLeft(),
                    newBid,
                    currentClubName,
                    auctiondata.isBiddingPaused(),
                    auctiondata.isAuctionStarted(),
                    auctiondata.isAuctionEnded(),
                    auctiondata.getCurrentPlayer(),
                    false,
                    null,
                    0
            );
            client.sendAuctionData(updatedAuctionData);
            System.out.println("Sent the bid to the server: " + newBid);
        } catch (Exception e) {
            System.err.println("Error sending bid to server: " + e.getMessage());
            Platform.runLater(() -> showPopup("Failed to send bid to server!", 400, 150));
        }
    }




    // UPDATE BY RAFI:
    private void processPurchase(Player purchased, int price) {
        try {
            if (currentClub == null) {
                System.err.println("Cannot process purchase: currentClub is null");
                return;
            }

            purchased.setClub(currentClubName);
            purchased.setFinalBidPrice(price);
            purchased.setSalary(price);
//            currentClub.addNewPlayer(purchased);
            Main.playerDatabase.updatePlayerClub(purchased, currentClubName);
            Main.clubDatabase.addPlayerToClub(purchased, currentClubName);

            Club club = Main.clubDatabase.getClub(currentClubName);
            club.setBudget(club.getBudget() - price);

            int newBudget = currentClub.getBudget() - price;
//            currentClub.setBudget(newBudget);
            updateBudgetUI(newBudget);

            Main.clubDatabase.uploadInfoToFile();
            Main.playerDatabase.uploadInfoToFile();
            Main.clubDatabase.reloadFromFile();
            Main.playerDatabase.reloadFromFile();

            String str = "Player Purchased!  \n" +
                    purchased.getName()+" sold to "+ currentClubName+"!"+
                    "\nPrice : "+price;

            showPopup(str,450,200);

            System.out.println("Player " + purchased.getName() + " successfully purchased for " + price);
        } catch (Exception e) {
            System.err.println("Error processing purchase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleEndAuction(ActionEvent event) {
        isStarted = false;
        ourLastBid = 0;
        if (client != null) client.disconnect();
        showPopup("Auction ended. Returning to dashboard...", 400, 150);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2), e -> handleBack()));
        delay.play();
    }

    public void handleBack(ActionEvent actionEvent) {
        if (client != null) client.disconnect();
        isStarted = false;
        ourLastBid = 0;
        handleBack();
    }

    public void handleBack() {
        try {
            String userType = Session.getUserType();
            if ("Admin".equalsIgnoreCase(userType)) {
                Main.setRoot("AdminDashboard.fxml");
            } else if ("Viewer".equalsIgnoreCase(userType)) {
                Main.setRoot("ViewerDashboard.fxml");
            } else if ("Club".equalsIgnoreCase(userType)) {
                Main.setRoot("ClubDashboard.fxml");
            }
        } catch (Exception e) {
            e.printStackTrace();
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