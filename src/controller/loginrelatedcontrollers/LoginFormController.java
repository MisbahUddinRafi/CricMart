package controller.loginrelatedcontrollers;

import application.Main;
import model.Logger;
import model.Session;
import model.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static model.AlertHelper.showAlert;

public class LoginFormController {
    @FXML private ChoiceBox<String> userTypeBox;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void initialize() {
        userTypeBox.getItems().addAll("viewer", "club", "admin");
        userTypeBox.setValue("viewer");
    }

    @FXML
    public void handleLogin() throws Exception {
        String type = userTypeBox.getValue();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (UserDatabase.validateUser(type, username, password)) {
            Session.set(username, type);
            Logger.log("Login success: " + username + " (" + type + ")");
            if (type.equals("admin")) {
                Main.setRoot("AdminDashboard.fxml");
                Main.setStageTitle("admin: " + username);
            } else if (type.equals("viewer")) {
                Main.setRoot("ViewerDashboard.fxml");
                Main.setStageTitle("viewer: " + username);


            } else if (type.equals("club")) {
                Main.setRoot("ClubDashboard.fxml");
                Main.setStageTitle("club: " + Main.clubDatabase.getClubByUsername(username).getClubName());
            } else {
                showAlert("Error", "Invalid user type");
            }
        } else {
            Logger.log("Login failed: " + username + " (" + type + ")");
            showAlert("Login Failed", "Incorrect credentials.");
        }
    }

    @FXML
    public void gotoLoginPage(ActionEvent actionEvent) {
        Main.setRoot("LoginPage.fxml");
    }
}
