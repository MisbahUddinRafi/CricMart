package controller.userprofilerelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import model.Club;
import model.CreateFXML;
import model.Session;
import model.UserDatabase;

import static model.AlertHelper.showAlert;
import static model.User.isValidUsername;

public class EditUsernamePageController {
    public TextField oldUsernameField;
    public TextField newUsernameField;


    public void goToBack(ActionEvent actionEvent) {
        Main.goBack();
    }

    public void onConfirmNewUsername(ActionEvent actionEvent) {
        String oldUsername = oldUsernameField.getText().trim();
        String newUsername = newUsernameField.getText().trim();

        if (!isValidUsername(newUsername)) {
            showAlert("Error", "Username must be 4–32 characters long and must contain only letters and digits. No spaces or commas allowed.");
            return;
        }

        if (UserDatabase.isPresentUsername(newUsername)) {
            showAlert("Error", "Username is already taken.");
            return;
        }

        // update:
        if(UserDatabase.updateUsername(oldUsername, newUsername)) {
            Session.currentUsername = newUsername;
            showAlert("Success", "Username has been updated.");

            String usertype = Session.getUserType();

            if (usertype.equalsIgnoreCase("Admin")) {
                Main.setStageTitle("admin: " + newUsername);
                CreateFXML.createAdminProfileFXML(newUsername);
                Main.deletePreviousSceneFromStack();
                Main.setRoot("AdminProfile.fxml");
            } else if (usertype.equalsIgnoreCase("viewer")) {
                Main.setStageTitle("viewer: " + newUsername);
                CreateFXML.createViewerProfileFXML(newUsername);
                Main.deletePreviousSceneFromStack();            // delete the old viewer profile
                Main.setRoot("ViewerProfile.fxml");
            } else if (usertype.equalsIgnoreCase("club")) {
                // update the information of the club:
                Club club = Main.clubDatabase.getClubByUsername(oldUsername);
                club.setUsername(newUsername);
                Main.setStageTitle("club: " + club.getClubName());

                CreateFXML.createClubProfileFXML(newUsername);
                Main.deletePreviousSceneFromStack();
                Main.setRoot("ClubProfile.fxml");
            }

        } else {
            showAlert("Error", "username update failed.");
            return;
        }


    }
}
