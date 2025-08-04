package controller.loginrelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class LoginPageController {
    @FXML
    public void goToLogin() throws Exception {
        Main.setRoot("LoginForm.fxml");
    }

    @FXML
    public void goToSignUp() throws Exception {
        Main.setRoot("SignUpPage.fxml");
    }

    @FXML
    public void goToExit(ActionEvent event) throws Exception {
        Main.updateDatabase();
        System.exit(0);
    }
}
