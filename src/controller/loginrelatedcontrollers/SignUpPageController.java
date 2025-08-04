package controller.loginrelatedcontrollers;

import application.Main;
import model.User;
import model.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static model.AlertHelper.showAlert;
import static model.User.isValidPassword;
import static model.User.isValidUsername;

public class SignUpPageController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!isValidUsername(username)) {
            showAlert("Error", "Username must be 4–32 characters long and must contain only letters and digits. No spaces or commas allowed.");
            return;
        }

        if (!isValidPassword(password)) {
            showAlert("Error", "Password must be 6–32 characters with no spaces or commas.");
            return;
        }


        if (UserDatabase.addUser(new User("viewer", username, password))) {
            showAlert("Success", "Viewer account created.");
            showAlert("Next step", "go to the login page and login");
            Main.setRoot("LoginPage.fxml");
        } else {
            showAlert("Error", "Username already exists.");
        }
    }

    @FXML
    public void gotoLoginPage(ActionEvent actionEvent) {
        Main.setRoot("LoginPage.fxml");
    }
}
