package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import model.Club;
import model.CreateFXML;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchClubController {
    @FXML
    private AnchorPane clubListAnchorPane;
    @FXML
    private ChoiceBox<String> sortByChoiceField;
    @FXML
    private ChoiceBox<String> orderChoiceField;
    @FXML
    private List<Club> clubs;
    @FXML
    private Button clubNameButton;

    @FXML
    public void initialize() {
        System.out.println("initialize method called");
        // Load all clubs from the database
        clubs = Main.clubDatabase.getClubs();

        sortByChoiceField.getItems().addAll("Name", "Trophies", "PlayerCount", "Budget");
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

        List<Club> sortedClubs = new ArrayList<>(clubs);
        Comparator<Club> comparator;

        switch (sortBy) {
            case "Trophies":
                comparator = Comparator.comparingInt(Club::getTrophiesCount);
                break;
            case "PlayerCount":
                comparator = Comparator.comparingInt(club -> club.getPlayers().size());
                break;
            case "Budget":
                comparator = Comparator.comparingInt(Club::getBudget);
                break;
            case "Name":
            default:
                comparator = Comparator.comparing(Club::getClubName, String.CASE_INSENSITIVE_ORDER);
                break;
        }

        if ("Descending".equals(order)) {
            comparator = comparator.reversed();
        }

        // sort the clubs according to the given criteria:
        sortedClubs.sort(comparator);


        // clear the current anchor pane data: (current list is erased, so that we can show the sorted data)
        clubListAnchorPane.getChildren().clear();



        // create rows for all clubs in the anchor pane:
        double layoutY = 0.0; // Starting Y position

        for (Club club : sortedClubs) {
            AnchorPane row = new AnchorPane();
            row.setLayoutY(layoutY); // Set the Y position for the row itself

            Button nameBtn = new Button(club.getClubName());
            nameBtn.setLayoutX(28);
            nameBtn.setPrefWidth(250);
            nameBtn.setPrefHeight(40);
            nameBtn.getStyleClass().add("sidebar-button");
            nameBtn.setId("clubNameButton");
            nameBtn.setOnAction(e -> showClubCard(e)); // Just pass the ActionEvent

            Button trophiesBtn = new Button(String.valueOf(club.getTrophiesCount()));
            trophiesBtn.setLayoutX(298);
            trophiesBtn.setPrefWidth(163);
            trophiesBtn.setPrefHeight(40);
            trophiesBtn.setId("trophiesField");
            trophiesBtn.getStyleClass().add("sidebar-button");

            Button budgetBtn = new Button(String.valueOf(club.getBudget()));
            budgetBtn.setLayoutX(485);
            budgetBtn.setPrefWidth(163);
            budgetBtn.setPrefHeight(40);
            budgetBtn.setId("budgetField");
            budgetBtn.getStyleClass().add("sidebar-button");

            Button playerCountBtn = new Button(String.valueOf(club.getPlayers().size()));
            playerCountBtn.setLayoutX(675);
            playerCountBtn.setPrefWidth(163);
            playerCountBtn.setPrefHeight(40);
            playerCountBtn.setId("playerCountField");
            playerCountBtn.getStyleClass().add("sidebar-button");

            row.getChildren().addAll(nameBtn, trophiesBtn, budgetBtn, playerCountBtn);
            clubListAnchorPane.getChildren().add(row); // Add this row to the anchor pane

            System.out.println("Created row for " + club.getClubName());

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


}
