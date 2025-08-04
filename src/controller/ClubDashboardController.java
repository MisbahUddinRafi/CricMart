package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Club;
import model.CreateFXML;
import model.Session;

import java.io.IOException;

public class ClubDashboardController {
    @FXML private Label welcomeLabel;

    @FXML
    public void handleSearchPlayer(ActionEvent event) throws IOException {
        Main.playerDatabase.uploadInfoToFile();
        Main.clubDatabase.uploadInfoToFile();
        Main.clubDatabase.reloadFromFile();
        Main.playerDatabase.reloadFromFile();
        Main.setRoot("SearchPlayer.fxml");
    }

    @FXML
    public void handlePlayers(ActionEvent event) throws IOException {
        Main.playerDatabase.uploadInfoToFile();
        Main.clubDatabase.uploadInfoToFile();
        Main.clubDatabase.reloadFromFile();
        Main.playerDatabase.reloadFromFile();
        Main.setRoot("Players.fxml");
    }

    @FXML
    public void handleTeams(ActionEvent event) throws IOException {
        Main.playerDatabase.uploadInfoToFile();
        Main.clubDatabase.uploadInfoToFile();
        Main.clubDatabase.reloadFromFile();
        Main.playerDatabase.reloadFromFile();
        Main.setRoot("SearchClub.fxml");
    }

    @FXML
    public void handleLiveAuction(ActionEvent event) throws IOException {
        Main.setRoot("clubAuction.fxml");
    }

    @FXML
    public void handlePreviousAuctions(ActionEvent event) throws IOException {
        Main.setRoot("prevAuctions.fxml");
    }

    @FXML
    public void handleAboutUs(ActionEvent event) throws IOException {
        Main.setRoot("AboutUs.fxml");
    }

    @FXML
    public void handleLogOut(ActionEvent actionEvent) {
        Main.setRoot("LoginPage.fxml");
    }

    public void handleProfile(ActionEvent actionEvent) {
        CreateFXML.createClubProfileFXML(Session.getUsername());
        Main.setRoot("ClubProfile.fxml");
    }

    public void handleRemovePlayer(ActionEvent actionEvent) {

        // show the list of all players of that club:
        String username = Session.getUsername();
        Club club = Main.clubDatabase.getClubByUsername(username);
        CreateFXML.createClubPlayerListFXML(club.getClubName());
        Main.setRoot("ClubPlayerList.fxml");

    }

    public void handleUpdateBudget(ActionEvent actionEvent) {
        String username = Session.getUsername();
        Club club = Main.clubDatabase.getClubByUsername(username);
        CreateFXML.createClubFXML(club);
        Main.setRoot("ClubCard.fxml");
    }
}