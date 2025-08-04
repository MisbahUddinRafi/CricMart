package controller.userprofilerelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import model.*;


public class ClubProfileController {

    public TextField clubNameField;

    public void goToBack(ActionEvent actionEvent) {
        Main.deletePreviousSceneFromStack();
        Main.setRoot("ClubDashboard.fxml");
    }

    public void handleLogOut(ActionEvent actionEvent) {
        Main.setRoot("LoginPage.fxml");
    }

    public void goToEditUsername(ActionEvent actionEvent) {
        CreateFXML.createEditUsernamePageFXML(Session.getUsername());
        Main.setRoot("EditUsernamePage.fxml");
    }

    public void goToEditPassword(ActionEvent actionEvent) {
        CreateFXML.createViewerProfileFXML(Session.getUsername());
        Main.setRoot("EditPasswordPage.fxml");
    }


    public void onDeleteAccount(ActionEvent actionEvent) {

        boolean confirmed = AlertHelper.showConfirmationAlert(
                "Confirm Delete",
                "Do you really want to delete your club?\nAll of your players will be released",
                "This action cannot be undone."
        );

        if (confirmed) {

            // delete from user database:
            UserDatabase.deleteUser(Session.getUsername());

            // delete from club database and release all players (safely released all players internally)
            Main.clubDatabase.deleteClub(clubNameField.getText());
            AlertHelper.showAlert("Account Deleted", "Your club account has been successfully deleted.");
            Main.setRoot("LoginPage.fxml");

        } else {
            Main.setRoot("ClubProfile.fxml");
        }

    }

    public void goToEditClubInfo(ActionEvent actionEvent) {
        String clubName = clubNameField.getText();
        Club club = Main.clubDatabase.getClub(clubName);
        CreateFXML.createClubFXML(club);
        Main.setRoot("ClubCard.fxml");
    }


    public void goToReleasePlayer(ActionEvent actionEvent) {
        String clubName = clubNameField.getText();

        Club club = Main.clubDatabase.getClub(clubName);
        if (club.getTotalPlayers() == 0) {
            AlertHelper.showAlert("Error", "No players in this club");
            return;
        }
        CreateFXML.createClubPlayerListFXML(clubName);
        Main.setRoot("ClubPlayerList.fxml");
    }
}
