package controller;

import application.Main;
import model.AlertHelper;
import model.CreateFXML;
import model.Player;

import static model.Constants.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;


public class PlayerAddController {

    @FXML
    public Button backButton;
    @FXML
    private TextField playerNameField;
    @FXML
    private TextField countryNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField jerseyNumberField;
    @FXML
    private ChoiceBox<String> positionField;


    @FXML
    private TextField matchesPlayedField;
    @FXML
    private TextField runsScoredField;
    @FXML
    private TextField ballsFacedField;
    @FXML
    private TextField fiftiesField;
    @FXML
    private TextField centuriesField;


    @FXML
    private TextField wicketsField;
    @FXML
    private TextField runsConcededField;
    @FXML
    private TextField hattricksField;
    @FXML
    private TextField overField;
    @FXML
    private TextField basePriceField;


    @FXML
    public void initialize() {
        positionField.getItems().addAll("batsman", "bowler", "allrounder", "wicketkeeper");
        positionField.setValue("batsman");
    }


    @FXML
    public void goToAdminDashboard(ActionEvent event) {
        Main.goBack();
//        Main.setRoot("AdminDashboard.fxml");
    }


    @FXML
    public void addPlayer(ActionEvent event) {
        // clear the input error message:
        AlertHelper.clearErrorStyles(playerNameField, countryNameField, ageField, heightField, jerseyNumberField,
                matchesPlayedField, runsScoredField, ballsFacedField, fiftiesField, centuriesField,
                wicketsField, runsConcededField, hattricksField, overField, basePriceField);

        try {
            String playerName = this.playerNameField.getText().trim();
            String country = countryNameField.getText().trim();
            String position = this.positionField.getValue();
            int age = Integer.parseInt(this.ageField.getText().trim());
            double height = Double.parseDouble(this.heightField.getText().trim());
            int jerseyNumber = Integer.parseInt(this.jerseyNumberField.getText().trim());

            int matchesPlayed = Integer.parseInt(this.matchesPlayedField.getText().trim());
            int runsScored = Integer.parseInt(this.runsScoredField.getText().trim());
            int ballsFaced = Integer.parseInt(this.ballsFacedField.getText().trim());
            int fifties = Integer.parseInt(this.fiftiesField.getText().trim());
            int centuries = Integer.parseInt(this.centuriesField.getText().trim());

            int wickets = Integer.parseInt(this.wicketsField.getText().trim());
            int runsConceded = Integer.parseInt(this.runsConcededField.getText().trim());
            int hattricks = Integer.parseInt(this.hattricksField.getText().trim());
            double over = Double.parseDouble(overField.getText().trim());

            int basePrice = Integer.parseInt(basePriceField.getText().trim());

            boolean error = false;

            // ERROR CHECK:
            if (playerName.length() < 3 || playerName.length() > MAX_NAME_LENGTH
                    || playerName.contains(",") || playerName.contains("  ") || !playerName.matches("[\\p{L}0-9 .'-]+")
            ) {
                AlertHelper.highlightError(playerNameField, "Name contains invalid characters");
                AlertHelper.showAlert("Error", "Player name must be between 3 and 32 characters\nAnd can't contain commas");
                error = true;
            }

            if (country.length() < 3 || country.length() > MAX_COUNTRY_LENGTH
                    || !country.matches("[a-zA-Z0-9 ]+")) {
                AlertHelper.highlightError(countryNameField, "Country name contains invalid characters");
                AlertHelper.showAlert("Error", "Country name must be between 3 and 32 characters");
                error = true;
            }

            if (age < MIN_AGE || age > MAX_AGE) {
                AlertHelper.highlightError(ageField, "Age " + MIN_AGE + "-" + MAX_AGE);
                AlertHelper.showAlert("Error", "Age must be between " + MIN_AGE + " and " + MAX_AGE);
                error = true;
            }

            if (height < MIN_HEIGHT || height > MAX_HEIGHT) {
                AlertHelper.highlightError(heightField, "Height 1-3.0");
                AlertHelper.showAlert("Error", "Height must be between 1 and 3.0");
                error = true;
            }


            if (jerseyNumber < 0 || jerseyNumber > MAX_JERSEY_NUMBER) {
                AlertHelper.highlightError(jerseyNumberField, "Jersey Number 0-" + MAX_JERSEY_NUMBER);
                AlertHelper.showAlert("Error", "Jersey number must be between 0 and " + MAX_JERSEY_NUMBER);
                error = true;

            }


            if (matchesPlayed < 0 || matchesPlayed > MAX_MATCHES) {
                AlertHelper.highlightError(matchesPlayedField, "Matches Played 0-" + MAX_MATCHES);
                AlertHelper.showAlert("Error", "Unrealistic number of matches");
                error = true;

            }

            if (ballsFaced < 0 || ballsFaced > MAX_BALLS) {
                AlertHelper.highlightError(ballsFacedField, "Balls faced 0-" + MAX_BALLS);
                AlertHelper.showAlert("Error", "Unrealistic number of balls faced");
                error = true;

            }


            if (runsScored < 0 || runsScored > ballsFaced * 6) {
                AlertHelper.highlightError(runsScoredField, "Runs should be consistent with balls faced");
                AlertHelper.showAlert("Error", "Unrealistic number of runs ");
                error = true;

            }


            if (fifties < 0 || fifties > matchesPlayed || ballsFaced < 9 * fifties) {     // minimum 9 balls required to score a fifty
                AlertHelper.highlightError(fiftiesField, "Fifties between 0 and matches played");
                AlertHelper.showAlert("Error", "Unrealistic number of fifties");
                error = true;

            }

            if (centuries < 0 || centuries > matchesPlayed || ballsFaced < 17 * centuries) {      // minimum 17 balls required to score a century
                AlertHelper.highlightError(centuriesField, "Centuries between 0 and matches played");
                AlertHelper.showAlert("Error", "Unrealistic number of centuries");
                error = true;

            }

            if (runsScored < 50 * fifties + 100 * centuries) {
                AlertHelper.highlightError(runsScoredField, "More fifties and centuries than runs scored");
                AlertHelper.highlightError(fiftiesField, "More fifties and centuries than runs scored");
                AlertHelper.highlightError(centuriesField, "More fifties and centuries than runs scored");
                AlertHelper.showAlert("Error", "No valid relation between number of runs scored, fifties and centuries");
                error = true;

            }


            if (over < 0 || over > matchesPlayed * 4) {
                AlertHelper.highlightError(overField, "Overs should be consistent with matches played");
                AlertHelper.showAlert("Error", "Unrealistic number of overs");
                error = true;

            }

            if (wickets < 0 || wickets > matchesPlayed * 10 || wickets > over * 6) {
                AlertHelper.highlightError(wicketsField, "Wickets should be consistent with overs bowled");
                AlertHelper.showAlert("Error", "Unrealistic number of wickets");
                error = true;

            }

            if (hattricks < 0 || hattricks * 3 > wickets || hattricks > over * 2) {         // maximum 2 hattricks per over
                AlertHelper.highlightError(hattricksField, "Hattricks should be consistent with overs bowled");
                AlertHelper.showAlert("Error", "Unrealistic number of hattricks");
                error = true;

            }

            if (runsConceded < 0 || runsConceded > over * 6 * 6) {      // maximum 36 runs per over
                AlertHelper.highlightError(runsConcededField, "Runs conceded should be consistent with overs bowled");
                AlertHelper.showAlert("Error", "Unrealistic number of runs conceded");
                error = true;

            }


            if (basePrice < MIN_BASE_PRICE || MAX_BASE_PRICE < basePrice) {
                AlertHelper.highlightError(basePriceField, "Base price " + MIN_BASE_PRICE + "-" + MAX_BASE_PRICE);
                AlertHelper.showAlert("Error", "Base price must be between " + MIN_BASE_PRICE + " and " + MAX_BASE_PRICE);
                error = true;

            }

            if (error) {
                return;
            }

            Player newPlayer = new Player();
            newPlayer.setName(playerName);
            newPlayer.setCountry(country);
            newPlayer.setAge(age);
            newPlayer.setHeight(height);
            newPlayer.setClub("");
            newPlayer.setPosition(position);
            newPlayer.setNumber(jerseyNumber);
            newPlayer.setSalary(0);

            newPlayer.setMatchesPlayed(matchesPlayed);
            newPlayer.setRunsScored(runsScored);
            newPlayer.setBallsFaced(ballsFaced);
            newPlayer.setFifties(fifties);
            newPlayer.setCenturies(centuries);

            newPlayer.setHattricks(hattricks);
            newPlayer.setOversBowled(over);
            newPlayer.setRunsGiven(runsConceded);
            newPlayer.setWicketsTaken(wickets);
            newPlayer.setBasePrice(basePrice);
            newPlayer.setFinalBidPrice(0);

            if (Main.playerDatabase.addNewPlayer(newPlayer)) {
                AlertHelper.showAlert("Player added", "Player successfully added to the database");

                Main.playerDatabase.uploadInfoToFile();
                Main.clubDatabase.uploadInfoToFile();
                Main.clubDatabase.reloadFromFile();
                Main.playerDatabase.reloadFromFile();

                Main.setRoot("AdminDashboard.fxml");
            } else {
                AlertHelper.showAlert("Error", "Something went wrong while adding a new player");
            }


        } catch (RuntimeException e) {
            AlertHelper.showAlert("Error", "Please make sure all numeric field contains valid numbers");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}