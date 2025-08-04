package controller;

import application.Main;
import model.User;
import model.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static model.AlertHelper.showAlert;
import static model.User.isValidPassword;
import static model.User.isValidUsername;

public class AdminAddController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void addAdmin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!isValidUsername(username)) {
            showAlert("Error", "Username must be 4–32 characters long and contain only letters and digits. No spaces or commas allowed.");
            return;
        }

        if (!isValidPassword(password)) {
            showAlert("Error", "Password must be 6–32 characters with no spaces or commas.");
            return;
        }

        if (UserDatabase.addUser(new User("admin", username, password))) {
            showAlert("Success", "Admin added.");
            Main.setRoot("AdminDashboard.fxml");
        } else {
            showAlert("Error", "Username already exists.");
        }
    }

    @FXML
    public void goToAdminDashboard(ActionEvent event) {
        Main.goBack();
//        Main.setRoot("AdminDashboard.fxml");
    }
}
