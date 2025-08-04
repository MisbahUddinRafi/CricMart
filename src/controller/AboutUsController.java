package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Session;

public class AboutUsController {

    public void onBackToMain(ActionEvent actionEvent) {
        Main.goBack();
//        String userTyoe = Session.getUserType();
//
//        if (userTyoe.equalsIgnoreCase("Admin")) {
//            Main.setRoot("AdminDashboard.fxml");
//        } else if (userTyoe.equalsIgnoreCase("Viewer")) {
//            Main.setRoot("ViewerDashboard.fxml");
//        } else if (userTyoe.equalsIgnoreCase("Club")) {
//            Main.setRoot("ClubDashboard.fxml");
//        }
    }



}
