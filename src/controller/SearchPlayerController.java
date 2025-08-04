package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.*;
import application.Main;

import java.io.IOException;
import java.util.List;

public class SearchPlayerController {

    @FXML
    private TextField playerNameField;

    @FXML
    private TextField countryNameField;

    @FXML
    private TextField clubNameField;

    @FXML
    private TextField startingSalaryField;

    @FXML
    private TextField endingSalaryField;

    @FXML
    private ChoiceBox<String> positionChoiceBox;


    @FXML
    public void initialize() {
        // Fill the choice box with the valid player positions
        positionChoiceBox.getItems().addAll("Batsman", "Bowler", "Allrounder", "Wicketkeeper");
        positionChoiceBox.setValue("Batsman");
    }

    @FXML
    private void onSearchByName() throws IOException {
        String name = playerNameField.getText().trim();

        if (name.isEmpty()) {
            AlertHelper.showAlert("Error", "Enter a player name!");
            return;
        }


        Player player = Main.playerDatabase.getPlayer(name);
        if (player == null) {
            AlertHelper.showAlert("Error", "Player not found");
            return;
        }

        // show the player card:
        CreateFXML.createPlayerFXML(player);
        Main.setRoot("PlayerCard.fxml");

    }

    @FXML
    private void onSearchByCountry() {
        String country = countryNameField.getText().trim();
        if (country.isEmpty()) {
            AlertHelper.showAlert("Error", "Country name cannot be empty!");
            return;
        }

        List<Player> countryPlayers = Main.playerDatabase.getPlayersOfCountry(country);
        if (countryPlayers.isEmpty()) {
            AlertHelper.showAlert("Error", "No player found for country " + country);
            return;
        }


        // show the player card:
        CreateFXML.createCountryFXML(countryPlayers);
        Main.setRoot("CountryCard.fxml");
    }

    @FXML
    private void onSearchByClub() {
        String clubName = clubNameField.getText().trim();

        if (clubName.isEmpty()) {
            AlertHelper.showAlert("Error", "Club not found");
            return;
        }

        Club club = Main.clubDatabase.getClub(clubName);
        if (club == null) {
            AlertHelper.showAlert("Error", "club " + clubName + " not found");
            return;
        }

        // show club profile/ card:
        CreateFXML.createClubFXML(club);
        Main.setRoot("ClubCard.fxml");
    }



    @FXML
    private void onSearchByPosition() {
        String position = positionChoiceBox.getValue().trim();

        if (position.isEmpty()) {
            AlertHelper.showAlert("Error", "Position not found");
            return;
        }

        List<Player> positionPlayers = Main.playerDatabase.getPlayersOfPosition(position);
        if (positionPlayers.isEmpty()) {
            AlertHelper.showAlert("Error", "No player found for position " + position);
            return;
        }
        CreateFXML.createPositionPlayerListFXML(positionPlayers);
        Main.setRoot("PositionPlayerList.fxml");
    }



    @FXML
    private void onSearchBySalary() {
        try {
            String startingSalary = startingSalaryField.getText().trim();
            String endingSalary = endingSalaryField.getText().trim();
            if (startingSalary.isEmpty() || endingSalary.isEmpty()) {
                AlertHelper.showAlert("Error", "Enter valid salary");
                return;
            }

            int startingSalaryInt = Integer.parseInt(startingSalary);
            int endingSalaryInt = Integer.parseInt(endingSalary);

            if (startingSalaryInt > endingSalaryInt) {
                AlertHelper.showAlert("Error", "Starting salary cannot be greater than ending salary");
                return;
            }


            List<Player> salaryPlayers = Main.playerDatabase.getPlayersOfSalaryRange(startingSalaryInt, endingSalaryInt);
            if (salaryPlayers.isEmpty()) {
                AlertHelper.showAlert("Error", "No player found in salary range: " + startingSalaryInt + " - " + endingSalaryInt);
                return;
            }
            CreateFXML.createSalaryBasedPlayerListFXML(salaryPlayers, startingSalaryInt, endingSalaryInt);
            Main.setRoot("SalaryBasedPlayerList.fxml");
        } catch (NumberFormatException e) {
            AlertHelper.showAlert("Error", "Salary must be an integer");
            return;
        }

    }



    @FXML
    private void goToBack(ActionEvent event) {
        Main.goBack();

//        String userType = Session.getUserType();
//
//        if (userType.equalsIgnoreCase("Admin")) {
//            Main.setRoot("AdminDashboard.fxml");
//        } else if (userType.equalsIgnoreCase("Viewer")) {
//            Main.setRoot("ViewerDashboard.fxml");
//        } else if (userType.equalsIgnoreCase("Club")) {
//            Main.setRoot("ClubDashboard.fxml");
//        }
    }

}


