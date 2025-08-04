package controller.cardrelatedcontrollers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.AlertHelper;
import model.CreateFXML;
import model.Player;
import model.Session;

import java.io.FileNotFoundException;
import java.io.IOException;

import static model.Constants.*;


public class PlayerCardController {

    @FXML
    public Button backButton;
    @FXML
    private TextField playerNameField;
    @FXML
    private TextField clubNameField;
    @FXML
    private TextField countryNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField jerseyNumberField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField salaryField;


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



    private String playerName;
    private String clubName;
    private String countryName;
    private int age;
    private double height;
    private int jerseyNumber;
    private String position;
    private int matchesPlayed;
    private int ballsFaced;
    private int runsScored;
    private int fifties;
    private int centuries;
    private int wickets;
    private int runsConceded;
    private int hattricks;
    private double over;
    private int basePrice;
    private int salary;


    public void initialize() {
        playerName = playerNameField.getText();
        clubName = clubNameField.getText();
        countryName = countryNameField.getText();
        age = Integer.parseInt(ageField.getText());
        height = Double.parseDouble(heightField.getText());
        jerseyNumber = Integer.parseInt(jerseyNumberField.getText());
        position = positionField.getText();
        salary = Integer.parseInt(salaryField.getText());

        matchesPlayed = Integer.parseInt(matchesPlayedField.getText());
        ballsFaced = Integer.parseInt(ballsFacedField.getText());
        runsScored = Integer.parseInt(runsScoredField.getText());
        fifties = Integer.parseInt(fiftiesField.getText());
        centuries = Integer.parseInt(centuriesField.getText());
        wickets = Integer.parseInt(wicketsField.getText());
        runsConceded = Integer.parseInt(runsConcededField.getText());
        hattricks = Integer.parseInt(hattricksField.getText());
        over = Double.parseDouble(overField.getText());
        basePrice = Integer.parseInt(basePriceField.getText());

    }


    @FXML
    public void goToBack(ActionEvent event) {
        // go to the previous page:
        resetAllFields();
        Main.goBack();
    }





    public void updatePlayerInfoByAdmin(ActionEvent actionEvent) {
        Player player = Main.playerDatabase.getPlayer(playerName);

        try {
            String newCountryName = countryNameField.getText().trim();
            int newSalary = Integer.parseInt(salaryField.getText().trim());
            int newAge = Integer.parseInt(ageField.getText().trim());
            double newHeight = Double.parseDouble(heightField.getText().trim());
            int newJerseyNumber = Integer.parseInt(jerseyNumberField.getText().trim());

            int newMatchesPlayed = Integer.parseInt(matchesPlayedField.getText().trim());
            int newRunsScored = Integer.parseInt(runsScoredField.getText().trim());
            int newBallsFaced = Integer.parseInt(ballsFacedField.getText().trim());
            int newFifties = Integer.parseInt(fiftiesField.getText().trim());
            int newCenturies = Integer.parseInt(centuriesField.getText().trim());

            int newWickets = Integer.parseInt(wicketsField.getText().trim());
            int newRunsConceded = Integer.parseInt(runsConcededField.getText().trim());
            int newHattricks = Integer.parseInt(hattricksField.getText().trim());
            double newOver = Double.parseDouble(overField.getText().trim());
            int newBasePrice = Integer.parseInt(basePriceField.getText().trim());

            boolean error = false;

            // VALIDATION (same as your original logic above)
            if (!newCountryName.equalsIgnoreCase(countryName) && (newCountryName.length() < 3 || newCountryName.length() > MAX_COUNTRY_LENGTH
                    || !newCountryName.matches("[a-zA-Z0-9 ]+"))) {
                AlertHelper.showAlert("Error", "Country name must be between 3 and 32 characters");
                error = true;
            }

            if (newAge != age && (newAge < MIN_AGE || newAge > MAX_AGE)) {
                AlertHelper.showAlert("Error", "Age must be between " + MIN_AGE + " and " + MAX_AGE);
                error = true;
            }

            if (newJerseyNumber != jerseyNumber && (newJerseyNumber < 0 || newJerseyNumber > MAX_JERSEY_NUMBER)) {
                AlertHelper.showAlert("Error", "Jersey number must be between 0 and " + MAX_JERSEY_NUMBER);
                error = true;
            }

            if (newMatchesPlayed != matchesPlayed && (newMatchesPlayed < 0 || newMatchesPlayed > MAX_MATCHES)) {
                AlertHelper.showAlert("Error", "Unrealistic number of matches");
                error = true;
            }

            if (newBallsFaced != ballsFaced && (newBallsFaced < 0 || newBallsFaced > MAX_BALLS)) {
                AlertHelper.showAlert("Error", "Unrealistic number of balls faced");
                error = true;
            }

            if (newRunsScored != runsScored && (newRunsScored < 0 || newRunsScored > ballsFaced * 6)) {
                AlertHelper.showAlert("Error", "Unrealistic number of runs ");
                error = true;
            }

            if (newFifties!=fifties && newFifties < 0 || newFifties > newMatchesPlayed || newBallsFaced < 9 * newFifties) {
                AlertHelper.showAlert("Error", "Unrealistic number of fifties");
                error = true;
            }

            if (newCenturies != centuries && newCenturies != centuries && (newCenturies < 0 || newCenturies > newMatchesPlayed || newBallsFaced < 17 * newCenturies)) {
                AlertHelper.showAlert("Error", "Unrealistic number of centuries");
                error = true;
            }

            if (newRunsScored < 50 * newFifties + 100 * newCenturies) {
                AlertHelper.showAlert("Error", "No valid relation between number of runs scored, fifties and centuries");
                error = true;
            }

            if (newOver != over && (newOver < 0 || newOver > newMatchesPlayed * 4)) {
                AlertHelper.showAlert("Error", "Unrealistic number of overs");
                error = true;
            }

            if (newWickets < 0 || newWickets > newMatchesPlayed * 10 || newWickets > newOver * 6) {
                AlertHelper.showAlert("Error", "Unrealistic number of wickets");
                error = true;
            }

            if (newHattricks < 0 || newHattricks * 3 > newWickets || newHattricks > newOver * 2) {
                AlertHelper.showAlert("Error", "Unrealistic number of hattricks");
                error = true;
            }

            if (newRunsConceded < 0 || newRunsConceded > newOver * 6 * 6) {
                AlertHelper.showAlert("Error", "Unrealistic number of runs conceded");
                error = true;
            }

            if (newBasePrice < MIN_BASE_PRICE || newBasePrice > MAX_BASE_PRICE) {
                AlertHelper.showAlert("Error", "Base price must be between " + MIN_BASE_PRICE + " and " + MAX_BASE_PRICE);
                error = true;
            }

            if (error) {
                resetAllFields();
                return;
            }

            // APPLY CHANGES IF ANY FIELD CHANGED
            boolean isUpdated = false;

            if (!newCountryName.equalsIgnoreCase(player.getCountry())) {
                player.setCountry(newCountryName);
                Main.playerDatabase.addNewCountry(player.getCountry());
                isUpdated = true;
            }
            if (newSalary != player.getSalary()) {
                player.setSalary(newSalary);
                isUpdated = true;
            }
            if (newAge != player.getAge()) {
                player.setAge(newAge);
                isUpdated = true;
            }
            if (newHeight != player.getHeight()) {
                player.setHeight(newHeight);
                isUpdated = true;
            }
            if (newJerseyNumber != player.getNumber()) {
                player.setNumber(newJerseyNumber);
                isUpdated = true;
            }


            if (newMatchesPlayed != player.getMatchesPlayed()) {
                player.setMatchesPlayed(newMatchesPlayed);
                isUpdated = true;
            }
            if (newRunsScored != player.getRunsScored()) {
                player.setRunsScored(newRunsScored);
                isUpdated = true;
            }
            if (newBallsFaced != player.getBallsFaced()) {
                player.setBallsFaced(newBallsFaced);
                isUpdated = true;
            }
            if (newFifties != player.getFifties()) {
                player.setFifties(newFifties);
                isUpdated = true;
            }
            if (newCenturies != player.getCenturies()) {
                player.setCenturies(newCenturies);
                isUpdated = true;
            }


            if (newWickets != player.getWicketsTaken()) {
                player.setWicketsTaken(newWickets);
                isUpdated = true;
            }
            if (newRunsConceded != player.getRunsGiven()) {
                player.setRunsGiven(newRunsConceded);
                isUpdated = true;
            }
            if (newHattricks != player.getHattricks()) {
                player.setHattricks(newHattricks);
                isUpdated = true;
            }
            if (newOver != player.getOversBowled()) {
                player.setOversBowled(newOver);
                isUpdated = true;
            }
            if (newBasePrice != player.getBasePrice()) {
                player.setBasePrice(newBasePrice);
                isUpdated = true;
            }



            if (isUpdated) {
                AlertHelper.showAlert("Success", "Player information updated successfully");
                CreateFXML.createPlayerFXML(player);

                Main.playerDatabase.uploadInfoToFile();
                Main.clubDatabase.uploadInfoToFile();
                Main.clubDatabase.reloadFromFile();
                Main.playerDatabase.reloadFromFile();

                Main.deletePreviousSceneFromStack();
                Main.setRoot("AdminDashboard.fxml");
            }
        } catch (NumberFormatException e) {
            AlertHelper.showAlert("Error", "Please ensure that all numeric fields are valid integers");
            resetAllFields();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public void onDeletePlayerByAdmin(ActionEvent actionEvent) throws IOException {
        if(Session.getUserType().equalsIgnoreCase("admin")) {
            boolean confirmed = AlertHelper.showConfirmationAlert("Confirm Delete", "Are you sure you want to delete this player?", "This can't be undone");

            if (confirmed) {
                Player player = Main.playerDatabase.getPlayer(playerName);

                // player will be removed from both player database and club:
                Main.playerDatabase.removePlayer(player);
                AlertHelper.showAlert("Success", "Player " + playerNameField.getText() + " has been deleted");

                Main.playerDatabase.uploadInfoToFile();
                Main.clubDatabase.uploadInfoToFile();
                Main.clubDatabase.reloadFromFile();
                Main.playerDatabase.reloadFromFile();

                Main.deletePreviousSceneFromStack();
                Main.setRoot("AdminDashboard.fxml");

            }
        }
    }



    public void onReleasePlayerByClub(ActionEvent actionEvent) throws IOException {
        if(Session.getUserType().equalsIgnoreCase("club")) {
            boolean confirmed = AlertHelper.showConfirmationAlert("Confirm Release",
                    "Are you sure you want to release this player?", "This can't be undone");

            if (confirmed) {

                // player's club is updated in both clubdatabase and playerdatabase:
                Player player = Main.playerDatabase.getPlayer(playerName);
                Main.clubDatabase.removePlayerFromClub(player);

                Main.playerDatabase.uploadInfoToFile();
                Main.clubDatabase.uploadInfoToFile();
                Main.clubDatabase.reloadFromFile();
                Main.playerDatabase.reloadFromFile();

                AlertHelper.showAlert("Success", "Player " + playerNameField.getText() + " has been released from " + clubNameField.getText());
                Main.deletePreviousSceneFromStack();
                Main.goBack();
            }
        }
    }



    private void resetAllFields() {
        playerNameField.setText(playerName);
        clubNameField.setText(clubName);
        countryNameField.setText(countryName);
        ageField.setText(Integer.toString(age));
        heightField.setText(Double.toString(height));
        jerseyNumberField.setText(Integer.toString(jerseyNumber));
        positionField.setText(position);
        salaryField.setText(Integer.toString(salary));

        matchesPlayedField.setText(Integer.toString(matchesPlayed));
        ballsFacedField.setText(Integer.toString(ballsFaced));
        runsScoredField.setText(Integer.toString(runsScored));
        fiftiesField.setText(Integer.toString(fifties));
        centuriesField.setText(Integer.toString(centuries));

        wicketsField.setText(Integer.toString(wickets));
        runsConcededField.setText(Integer.toString(runsConceded));
        hattricksField.setText(Integer.toString(hattricks));
        overField.setText(Double.toString(over));
        basePriceField.setText(Integer.toString(basePrice));

    }
}
