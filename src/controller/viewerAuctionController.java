package controller;

import Network.AuctionClient;
import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Club;
import model.Player;
import model.Session;

import java.io.IOException;

public class viewerAuctionController {

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

    @FXML private Label timerLabel;
    @FXML private Label currentBidLabel;
    @FXML private Label bidLeaderLabel;
    private AuctionClient client;

    public void initialize() {
        client = new AuctionClient(this);
    }

    public void updateUIWithAuctionData(AuctionData auctionData) {
        if (auctionData == null) {
            System.err.println("Received null auction data");
            return;
        }

        // UPDATE BY RAFI:
        if (auctionData.isCurrentPlayerSold()) {
            Player player = Main.playerDatabase.getPlayer(auctionData.getCurrentPlayer().getName());
            player.setClub(auctionData.getWinningClubName());
            player.setFinalBidPrice(auctionData.getFinalSalePrice());

            Main.playerDatabase.updatePlayerClub(player, auctionData.getWinningClubName());
            Main.clubDatabase.addPlayerToClub(player, auctionData.getWinningClubName());

            Club club = Main.clubDatabase.getClub(auctionData.getWinningClubName());
            club.setBudget(club.getBudget() - auctionData.getFinalSalePrice());

        }
        // *******

        updateBidUI(auctionData.getCurrentBid(), auctionData.getCurrentBidder());
        updateTimerUI(auctionData);
        showPlayerDetails(auctionData.getCurrentPlayer());
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

    public void handleLeaveAuction(ActionEvent event) {
        handleBack();
    }

    public void handleBack(ActionEvent actionEvent) {
        handleBack();
    }

    public void handleBack() {
        String userType = Session.getUserType();
        if (userType.equalsIgnoreCase("Admin")) {
            Main.setRoot("AdminDashboard.fxml");
        } else if (userType.equalsIgnoreCase("Viewer")) {
            Main.setRoot("ViewerDashboard.fxml");
        } else if (userType.equalsIgnoreCase("Club")) {
            Main.setRoot("ClubDashboard.fxml");
        }
    }
}
