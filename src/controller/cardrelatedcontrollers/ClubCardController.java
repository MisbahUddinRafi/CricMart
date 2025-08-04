package controller.cardrelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static model.AlertHelper.highlightError;
import static model.AlertHelper.showAlert;
import static model.Constants.*;
import static model.Constants.MAX_TROPHIES;
import static model.User.isValidPassword;
import static model.User.isValidUsername;

public class ClubCardController {


    public TextField clubNameField;
    public TextField managerNameField;
    public TextField budgetField;
    public TextField shortFormField;
    public TextField trophiesField;


    private String clubName;
    private String managerName;
    private String shortForm;
    private int budget;
    private int trophies;



    public void initialize() {
        clubName = clubNameField.getText();
        managerName = managerNameField.getText();
        shortForm = shortFormField.getText();
        budget = Integer.parseInt(budgetField.getText());
        trophies = Integer.parseInt(trophiesField.getText());

    }



    private void resetAllFields() {
        clubNameField.setText(clubName);
        shortFormField.setText(shortForm);
        managerNameField.setText(managerName);
        budgetField.setText(String.valueOf(budget));
        trophiesField.setText(String.valueOf(trophies));

    }


    @FXML
    public void goToAllPlayersOfClub(ActionEvent event) {
        Club club = Main.clubDatabase.getClub(clubName);
        if (club.getTotalPlayers() == 0) {
            AlertHelper.showAlert("Error", "No players in this club");
            return;
        }

        resetAllFields();           // reset before leaving
        CreateFXML.createClubPlayerListFXML(clubName);
        Main.setRoot("ClubPlayerList.fxml");
    }



    @FXML
    public void goToBack(ActionEvent event) {
//        String userType = Session.getUserType();

//        if (userType == null) {
//            AlertHelper.showAlert("Error", "Something went wrong.");
//        }

        resetAllFields();
        Main.goBack();

//        if (userType.equalsIgnoreCase("admin")) Main.setRoot("AdminDashboard.fxml");
//        else if (userType.equalsIgnoreCase("club")) Main.setRoot("ClubDashboard.fxml");
//        else if (userType.equalsIgnoreCase("player")) Main.setRoot("ViewerDashboard.fxml");
    }


    public void updateClubInfo(ActionEvent actionEvent) {
        Club club = Main.clubDatabase.getClub(clubName);

        String newClubName = clubNameField.getText();
        String newManagerName = managerNameField.getText();
        String newShortForm = shortFormField.getText();
        try {
            int newBudget = Integer.parseInt(budgetField.getText());
            int newTrophies = Integer.parseInt(trophiesField.getText());

            // check validity:
            boolean error = false;

            // validate the club name:
            if (!newClubName.equalsIgnoreCase(clubName) && newClubName.length() < CLUB_NAME_MIN_LENGTH || CLUB_NAME_MAX_LENGTH < newClubName.length() || newClubName.contains(",")
                    || !newClubName.matches("[a-zA-Z0-9 ]+")) {
                showAlert("Error", "Invalid Club Name! \nClub name must be between " + CLUB_NAME_MIN_LENGTH + " and " + CLUB_NAME_MAX_LENGTH + " characters! \nNo commas allowed");
                error = true;
            } else if (!newClubName.equalsIgnoreCase(clubName) && Main.clubDatabase.isPresentClub(newClubName)) {
                showAlert("Error", "Club Name Already Exists");
                error = true;

            }


            if (!newShortForm.equalsIgnoreCase(shortForm) && newShortForm.length() > SHORT_FORM_MAX_LENGTH || newShortForm.length() < SHORT_FORM_MIN_LENGTH
                    || newShortForm.contains(" ") || newShortForm.contains(",") || !newShortForm.matches("[a-zA-Z0-9]+")) {

                showAlert("Error", "Short form must be between 2 to 6 characters! \nOnly alphabets and digits are allowed");
                error = true;

            }


            if (!newManagerName.equalsIgnoreCase(managerName) && newManagerName.length() < MANAGER_NAME_MIN_LENGTH || MANAGER_NAME_MAX_LENGTH < newManagerName.length() || newManagerName.contains(",")
                    || !newManagerName.matches("[a-zA-Z0-9 ]+")) {

                showAlert("Error", "Invalid Manager Name! Club name must be between 3 and 32 characters! Only alphabets, digits and single space are allowed");
                error = true;

            }

            if (newBudget == budget && newBudget < MIN_BUDGET) {
                showAlert("Error", "Very low budget! \nMinimum budget should be at least 10000000");
                error = true;

            }


            if (newTrophies != trophies && newTrophies < trophies || MAX_TROPHIES < newTrophies) {
                showAlert("Error", "Enter valid trophy number");
                error = true;

            }


            // if error found, set the just reload the page:
            if (error) {
                resetAllFields();
                return;
            }


            // UPDATE THE CLUB INFO USING SETTERS (ALSO THE CLUB NAME OF ALL THE PLAYERS IN THIS CLUB IS UPDATED INSIDE setClubName()):
            boolean isUpdated = false;
            if (!newClubName.equalsIgnoreCase(clubName)) {
                club.setClubName(newClubName);
                isUpdated = true;
            }
            if (!newManagerName.equalsIgnoreCase(managerName)) {
                club.setManagerName(newManagerName);
                isUpdated = true;
            }
            if (!newShortForm.equalsIgnoreCase(shortForm)) {
                club.setShortForm(newShortForm);
                isUpdated = true;
            }
            if (newBudget != budget) {
                club.setBudget(newBudget);
                isUpdated = true;
            }
            if (newTrophies != trophies) {
                club.setTrophiesCount(newTrophies);
                isUpdated = true;
            }


            // load the club card again with the updated information:
            if (isUpdated) {
                AlertHelper.showAlert("Success", "Club Information Updated");
                CreateFXML.createClubFXML(club);

                Main.playerDatabase.uploadInfoToFile();
                Main.clubDatabase.uploadInfoToFile();
                Main.clubDatabase.reloadFromFile();
                Main.playerDatabase.reloadFromFile();

                CreateFXML.createClubProfileFXML(club.getUsername());
                Main.deletePreviousSceneFromStack();
                if (Session.getUserType().equalsIgnoreCase("admin")) {
                    Main.setRoot("AdminDashboard.fxml");
                } else if (Session.getUserType().equalsIgnoreCase("club")) {
                    Main.setRoot("ClubDashboard.fxml");
                }
            }
        } catch (NumberFormatException e) {
            AlertHelper.showAlert("Error", "Please ensure that all numeric fields are filled with integers");
            budgetField.setText(String.valueOf(budget));
            trophiesField.setText(String.valueOf(trophies));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    public void onDeleteClub(ActionEvent actionEvent) {
        boolean confirmed = AlertHelper.showConfirmationAlert(
                "Confirm Delete",
                "Do you really want to delete your club?\nAll of your players will be released",
                "This action cannot be undone."
        );

        if (confirmed) {

            // delete from user database:
            UserDatabase.deleteUser(Session.getUsername());

            // delete from club database and release all players (safely released all players internally)
            Main.clubDatabase.deleteClub(clubName);
            AlertHelper.showAlert("Account Deleted", "Your club account has been successfully deleted.");
            Main.setRoot("LoginPage.fxml");

        } else {
            Main.setRoot("ClubProfile.fxml");
        }
    }
}
