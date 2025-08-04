package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import model.Club;
import model.CreateFXML;
import model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PlayersController {
    @FXML
    private AnchorPane playerListAnchorPane;
    @FXML
    private ChoiceBox<String> sortByChoiceField;
    @FXML
    private ChoiceBox<String> orderChoiceField;

    @FXML
    private Button playerNameHeaderButton;
    @FXML
    private Button clubNameHeaderButton;
    @FXML
    private Button sortByHeaderButton;

    List<Player> players;

    @FXML
    public void initialize() {
        System.out.println("initialize method called");
        // Load all clubs from the database
        players = Main.playerDatabase.getPlayers();

        sortByChoiceField.getItems().addAll("Name", "Age", "Salary", "Matches", "Runs", "Wickets", "Height", "Country", "Position", "Base Price");
        sortByChoiceField.setValue("Name");

        orderChoiceField.getItems().addAll("Ascending", "Descending");
        orderChoiceField.setValue("Ascending");

        // Initial population of list
        updateList();
    }


    public void updateList() {
        System.out.println("updateList called");
        String sortBy = sortByChoiceField.getValue();
        String order = orderChoiceField.getValue();

        if (sortBy == null) sortBy = "Name";
        if (order == null) order = "Ascending";

        // set the sorting criteria:
        setSortByHeaderButton(sortBy);

        List<Player> sortedPlayers = Main.playerDatabase.getPlayers();
        Comparator<Player> comparator;

        switch (sortBy) {
            case "Age":
                comparator = Comparator.comparingInt(Player::getAge);
                break;
            case "Salary":
                comparator = Comparator.comparingInt(Player::getSalary);
                break;
            case "Matches":
                comparator = Comparator.comparingInt(Player::getMatchesPlayed);
                break;
            case "Runs":
                comparator = Comparator.comparingInt(Player::getRunsScored);
                break;
            case "Wickets":
                comparator = Comparator.comparingInt(Player::getWicketsTaken);
                break;
            case "Height":
                comparator = Comparator.comparingDouble(Player::getHeight);
                break;
            case "Country":
                comparator = Comparator.comparing(Player::getCountry, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Position":
                comparator = Comparator.comparing(Player::getPosition, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Base Price":
                comparator = Comparator.comparingInt(Player::getBasePrice);
                break;
            case "Name":
            default:
                comparator = Comparator.comparing(Player::getName, String.CASE_INSENSITIVE_ORDER);
                break;
        }

        if ("Descending".equals(order)) {
            comparator = comparator.reversed();
        }

        // sort the clubs according to the given criteria:
        sortedPlayers.sort(comparator);


        // clear the current anchor pane data: (current list is erased, so that we can show the sorted data)
        playerListAnchorPane.getChildren().clear();


        // create rows for all clubs in the anchor pane:
        double layoutY = 0.0; // Starting Y position

        for (Player player : sortedPlayers) {
            AnchorPane row = new AnchorPane();
            row.setLayoutY(layoutY); // Set the Y position for the row itself

            Button playerNameBtn = new Button(player.getName());
            playerNameBtn.setLayoutX(28);
            playerNameBtn.setPrefWidth(250);
            playerNameBtn.setPrefHeight(40);
            playerNameBtn.getStyleClass().add("sidebar-button");
            playerNameBtn.setId("playerNameButton");
            playerNameBtn.setOnAction(e -> showPlayerCard(e)); // Just pass the ActionEvent

            Button clubNameBtn = new Button(player.getClub());
            clubNameBtn.setLayoutX(348);
            clubNameBtn.setPrefWidth(250);
            clubNameBtn.setPrefHeight(40);
            clubNameBtn.setId("clubNameButton");
            clubNameBtn.getStyleClass().add("sidebar-button");
            clubNameBtn.setOnAction(e -> showClubCard(e)); // Just pass the ActionEvent


            Button sortByBtn = new Button();
            switch (sortBy) {
                case "Name" -> sortByBtn.setText(player.getCountry());
                case "Age" -> sortByBtn.setText(String.valueOf(player.getAge()));
                case "Salary" -> sortByBtn.setText(String.valueOf(player.getSalary()));
                case "Matches" -> sortByBtn.setText(String.valueOf(player.getMatchesPlayed()));
                case "Runs" -> sortByBtn.setText(String.valueOf(player.getRunsScored()));
                case "Wickets" -> sortByBtn.setText(String.valueOf(player.getWicketsTaken()));
                case "Height" -> sortByBtn.setText(String.valueOf(player.getHeight()));
                case "Country" -> sortByBtn.setText(player.getCountry());
                case "Position" -> sortByBtn.setText(player.getPosition());
                case "Base Price" -> sortByBtn.setText(String.valueOf(player.getBasePrice()));
            }
            sortByBtn.setLayoutX(675.0);
            sortByBtn.setPrefWidth(163);
            sortByBtn.setPrefHeight(40);
            sortByBtn.setId("sortByButton");
            sortByBtn.getStyleClass().add("sidebar-button");


            row.getChildren().addAll(playerNameBtn, clubNameBtn, sortByBtn);
            playerListAnchorPane.getChildren().add(row); // Add this row to the anchor pane

            System.out.println("Created row for " + player.getName());

            layoutY += 50; // Next row will be 50px below
        }

    }

    @FXML
    public void goToBack(ActionEvent event) {
        Main.goBack();
    }


    public void showClubCard(ActionEvent actionEvent) {
        System.out.println("Show club card called");
        Button clubNameButton = (Button) actionEvent.getSource();
        String clubName = clubNameButton.getText();

        Club club = Main.clubDatabase.getClub(clubName);
        CreateFXML.createClubFXML(club);
        Main.setRoot("ClubCard.fxml");
    }


    public void showPlayerCard(ActionEvent actionEvent) {
        System.out.println("Show player card called");
        Button playerNameButton = (Button) actionEvent.getSource();
        String playerName = playerNameButton.getText();

        Player player = Main.playerDatabase.getPlayer(playerName);
        CreateFXML.createPlayerFXML(player);
        Main.setRoot("PlayerCard.fxml");
    }


    /*
    *  <Button fx:id="sortByHeaderButton" layoutX="675.0" layoutY="10.0" prefHeight="40.0"
                            prefWidth="163.0" style="-fx-font-size: 15px; -fx-font-weight: bold;"
                            styleClass="title-button" text="Sort By"/>
    * */
    private void setSortByHeaderButton(String sortBy) {
        sortByHeaderButton.setText(sortBy);
        sortByHeaderButton.setId("sortByHeaderButton");
        sortByHeaderButton.setLayoutX(675.0);
        sortByHeaderButton.setLayoutY(10.0);
        sortByHeaderButton.setPrefWidth(163.0);
        sortByHeaderButton.setPrefHeight(40.0);
        sortByHeaderButton.getStyleClass().add("title-button");


    }
}
