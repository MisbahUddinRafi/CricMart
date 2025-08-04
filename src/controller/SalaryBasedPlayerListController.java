package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.Club;
import model.CreateFXML;
import model.Player;
import model.Session;

public class SalaryBasedPlayerListController {

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
        CreateFXML.createPlayerFXML(player);
        Main.setRoot("PlayerCard.fxml");
    }

    public void showClubCard(ActionEvent actionEvent) {
        Button clubButton = (Button) actionEvent.getSource();
        String clubName = clubButton.getText().trim();
        Club club = Main.clubDatabase.getClub(clubName);
        CreateFXML.createClubFXML(club);
        Main.setRoot("ClubCard.fxml");
    }
}
