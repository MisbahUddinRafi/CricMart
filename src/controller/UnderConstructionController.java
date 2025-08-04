package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UnderConstructionController {

    @FXML
    public void onBackToMain(ActionEvent event) {
        Main.setRoot("MainMenu.fxml");
    }
}
