package controller;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.CreateFXML;
import model.Session;

import java.io.IOException;


public class AdminDashboardController {

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
    public void handleStartAuction(ActionEvent event) throws IOException {
        Main.setRoot("startAuction.fxml");
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
    public void handleLogOut(ActionEvent event) throws IOException {
        Main.setRoot("LoginPage.fxml");
    }


    @FXML
    public void handleProfile(ActionEvent event) throws IOException {
        CreateFXML.createAdminProfileFXML(Session.getUsername());
        Main.setRoot("AdminProfile.fxml");
    }


    // special features of the admin dashboard:
    @FXML
    public void handleRemove(ActionEvent event) throws IOException {
        Main.setRoot("SearchPlayer.fxml");
    }

    @FXML
    public void handleAddPlayer(ActionEvent event) throws IOException {
        Main.setRoot("PlayerAdd.fxml");
    }

    @FXML
    public void handleAddClub(ActionEvent event) throws IOException {
        Main.setRoot("ClubAdd.fxml");
    }

    @FXML
    public void handleAddAdmin(ActionEvent event) throws IOException {
        Main.setRoot("AdminAdd.fxml");
    }


}
