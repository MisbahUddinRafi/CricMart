package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.AlertHelper;
import model.CreateFXML;
import model.Player;

public class ClubPlayerListController {


    public void goToBack(ActionEvent actionEvent) {

        Main.goBack();
//        String userType = Session.getUserType();
//
//        if (userType.equalsIgnoreCase("Admin")) {
//            Main.setRoot("AdminDashboard.fxml");
//        } else if (userType.equalsIgnoreCase("Viewer")) {
//            Main.setRoot("ViewerDashboard.fxml");
//        } else if (userType.equalsIgnoreCase("Club")) {
//            Main.setRoot("ClubDashboard.fxml");
//        }
    }





    public void showPlayerCard(ActionEvent actionEvent) {
        Button playerButton = (Button) actionEvent.getSource();
        String playerName = playerButton.getText().trim();
        Player player = Main.playerDatabase.getPlayer(playerName);
        if (player == null) {
            AlertHelper.showAlert("Error", "Player not found");
            return;
        }
        CreateFXML.createPlayerFXML(player);
        Main.setRoot("PlayerCard.fxml");
    }
}
