package controller;

import application.Main;
import model.*;

import static model.Constants.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import static model.AlertHelper.showAlert;
import static model.AlertHelper.highlightError;
import static model.User.isValidPassword;
import static model.User.isValidUsername;

public class ClubAddController {


    @FXML
    private TextField clubNameField;
    @FXML
    private TextField shortFormField;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField managerNameField;
    @FXML
    private TextField budgetField;
    @FXML
    private TextField trophiesField;


    @FXML
    public void addClub(ActionEvent event) {
        AlertHelper.clearErrorStyles(clubNameField, shortFormField, userNameField,
                passwordField, managerNameField, budgetField, trophiesField);
        try {
            String clubName = clubNameField.getText().trim();
            String shortForm = shortFormField.getText().trim();
            String username = userNameField.getText().trim();
            String password = passwordField.getText().trim();
            String managerName = managerNameField.getText().trim();
            int budget = Integer.parseInt(budgetField.getText().trim());
            int trophies = Integer.parseInt(trophiesField.getText().trim());


            boolean error = false;

            // validate the club name:
            if (clubName.length() < CLUB_NAME_MIN_LENGTH || CLUB_NAME_MAX_LENGTH < clubName.length() || clubName.contains(",")
                    || !clubName.matches("[a-zA-Z0-9 ]+")) {
                highlightError(clubNameField, "Club name " + CLUB_NAME_MIN_LENGTH + "-" + CLUB_NAME_MAX_LENGTH);
                showAlert("Error", "Invalid Club Name! \nClub name must be between " + CLUB_NAME_MIN_LENGTH + " and " + CLUB_NAME_MAX_LENGTH + " characters! \nNo commas allowed");
                error = true;
            } else if (Main.clubDatabase.isPresentClub(clubName)) {
                highlightError(clubNameField, "Enter new club name");
                showAlert("Error", "Club Name Already Exists");
                error = true;

            }

            if (!isValidUsername(username)) {
                highlightError(userNameField, "enter a new username");
                showAlert("Error", "Username must be " + MIN_USERNAME_LENGTH + "-" + MAX_USERNAME_LENGTH + " characters long and contain only letters and digits.\n No spaces or commas allowed.");
                error = true;

            }

            if (!isValidPassword(password)) {
                highlightError(passwordField, "enter a valid password");
                showAlert("Error", "Incorrect Password Format! \nPassword must be between 6 and 32 characters! \nNo spaces and commas are allowed");
                error = true;

            }

            if (shortForm.length() > SHORT_FORM_MAX_LENGTH || shortForm.length() < SHORT_FORM_MIN_LENGTH
                    || shortForm.contains(" ") || shortForm.contains(",") || !shortForm.matches("[a-zA-Z0-9]+")) {
                highlightError(shortFormField, "short form " + SHORT_FORM_MIN_LENGTH + "-" + SHORT_FORM_MAX_LENGTH);
                showAlert("Error", "Short form must be between 2 to 6 characters! \nOnly alphabets and digits are allowed");
                error = true;

            }


            if (managerName.length() < MANAGER_NAME_MIN_LENGTH || MANAGER_NAME_MAX_LENGTH < managerName.length() || managerName.contains(",")
                    || !managerName.matches("[a-zA-Z0-9 ]+")) {
                highlightError(managerNameField, "manager name " + MANAGER_NAME_MIN_LENGTH + "-" + MANAGER_NAME_MAX_LENGTH);
                showAlert("Error", "Invalid Manager Name! Club name must be between 3 and 32 characters! Only alphabets, digits and single space are allowed");
                error = true;

            }

            if (budget < MIN_BUDGET) {
                highlightError(budgetField, "minimum budget " + MIN_BUDGET);
                showAlert("Error", "Very low budget! \nMinimum budget should be at least 10000000");
                error = true;

            }


            if (trophies < 0 || MAX_TROPHIES < trophies) {
                highlightError(trophiesField, "maximum trophies " + MAX_TROPHIES);
                showAlert("Error", "Enter valid trophy number");
                error = true;

            }


            if (UserDatabase.isPresentUsername(username)) {
                showAlert("Error", "Username already exists");
                highlightError(userNameField, "Username already exists");
                error = true;
            }

            if (error) {
                return;
            }



            if (UserDatabase.addUser(new User("club", username, password))) {
                Club newClub = new Club();
                newClub.setClubName(clubName);
                newClub.setShortForm(shortForm);
                newClub.setUsername(username);
                newClub.setPassword(password);
                newClub.setManagerName(managerName);
                newClub.setBudget(budget);
                newClub.setTrophiesCount(trophies);

                if (Main.clubDatabase.addNewClub(newClub)) {
                    showAlert("Success", "Club added.");

                    Main.playerDatabase.uploadInfoToFile();
                    Main.clubDatabase.uploadInfoToFile();
                    Main.clubDatabase.reloadFromFile();
                    Main.playerDatabase.reloadFromFile();

                    Main.setRoot("AdminDashboard.fxml");
                } else {
                    showAlert("Error", "Club Already Exists");
                }
            } else {
                showAlert("Error", "Username already exists.");
            }
        } catch (RuntimeException e) {
            showAlert("Error", "Please ensure that all numeric fields are valid numbers");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToAdminDashboard(ActionEvent event) {
        Main.goBack();
//        Main.setRoot("AdminDashboard.fxml");
    }




}
