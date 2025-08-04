package controller.userprofilerelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import model.AlertHelper;
import model.CreateFXML;
import model.Session;
import model.UserDatabase;


public class AdminProfileController {


    public void goToBack(ActionEvent actionEvent) {
        Main.deletePreviousSceneFromStack();
        Main.setRoot("AdminDashboard.fxml");
    }

    public void handleLogOut(ActionEvent actionEvent) {
        Main.setRoot("LoginPage.fxml");
    }

    public void goToEditUsername(ActionEvent actionEvent) {
        CreateFXML.createEditUsernamePageFXML(Session.getUsername());
        Main.setRoot("EditUsernamePage.fxml");
    }

    public void goToEditPassword(ActionEvent actionEvent) {
        CreateFXML.createEditPasswordPageFXML(Session.getUsername());
        Main.setRoot("EditPasswordPage.fxml");
    }


    public void onDeleteAccount(ActionEvent actionEvent) {

        boolean confirmed = AlertHelper.showConfirmationAlert(
                "Confirm Delete",
                "Do you really want to delete your account?",
                "This action cannot be undone."
        );

        if (confirmed) {

            // Show info
            UserDatabase.deleteUser(Session.getUsername());
            AlertHelper.showAlert("Account Deleted", "Your account has been successfully deleted.");
            Main.setRoot("LoginPage.fxml");

        } else {
            return;
        }
    }

    public void goToEditClubInfo(ActionEvent actionEvent) {
        Main.setRoot("SearchPlayer.fxml");
    }

    public void goToEditPlayerInfo(ActionEvent actionEvent) {
        Main.setRoot("SearchPlayer.fxml");
    }
}
