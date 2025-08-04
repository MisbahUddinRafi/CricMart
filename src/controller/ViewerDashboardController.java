package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.CreateFXML;
import model.Session;

import java.io.IOException;

public class ViewerDashboardController {

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
        Main.setRoot("viewerAuction.fxml");
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
    public void handleProfile(ActionEvent actionEvent) {
        CreateFXML.createViewerProfileFXML(Session.getUsername());
        Main.setRoot("ViewerProfile.fxml");
    }


}