package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    public void onSearchPlayer(ActionEvent event) {
        Main.setRoot("SearchPlayer.fxml");
    }

    @FXML
    public void onSearchClub(ActionEvent event) {
        Main.setRoot("SearchClub.fxml");
    }

    @FXML
    public void onAddPlayer(ActionEvent event) {
        Main.setRoot("UnderConstruction.fxml");
    }

    @FXML
    public void onExit(ActionEvent event) throws Exception {
        Main.updateDatabase();
        System.exit(0);

    }

    @FXML
    public void handleLogOut(ActionEvent event) {
        Main.setRoot("LoginPage.fxml");
    }
}
