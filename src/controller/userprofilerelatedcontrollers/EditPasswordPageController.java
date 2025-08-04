package controller.userprofilerelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import model.Session;
import model.UserDatabase;

import static model.AlertHelper.showAlert;
import static model.User.isValidPassword;

public class EditPasswordPageController {
    public PasswordField oldPasswordField;
    public PasswordField newPasswordField;


    public void goToBack(ActionEvent actionEvent) {
        Main.goBack();
    }


    public void onConfirmNewPassword(ActionEvent actionEvent) {
        String oldPassword = oldPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String username = Session.getUsername();
        String usertype = Session.getUserType();


        if (!UserDatabase.validateUser(usertype, username, oldPassword)) {
            showAlert("Error", "Old password does not match");
            return;

        }


        if (!isValidPassword(newPassword)) {
            showAlert("Error", "Password must be 6–32 characters with no spaces or commas.");
            return;
        }



        // update:
        if(UserDatabase.updatePassword(username, newPassword)) {
            showAlert("Success", "Password updated successfully.");
            Main.goBack();
        } else {
            showAlert("Error", "password update failed.");
            return;
        }


    }
}
