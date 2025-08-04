package controller.auctionRelated;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import application.Main;
import model.Player;
import model.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AuctionHistoryController implements Initializable {

    @FXML
    private VBox auctionEntriesVBox;
    @FXML
    private AnchorPane auctionListAnchorPane;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAuctionHistory();
    }

    private void loadAuctionHistory() {
        AuctionHistoryManager historyManager = new AuctionHistoryManager();
        historyManager.loadFromFile();
        List<AuctionRecord> records = historyManager.getAllRecords();
        auctionEntriesVBox.getChildren().clear();

        for (AuctionRecord record : records) {
            HBox auctionRow = createAuctionRow(record);
            auctionEntriesVBox.getChildren().add(auctionRow);
        }
    }

    private HBox createAuctionRow(AuctionRecord record) {
        HBox row = new HBox(10);
        row.setStyle("-fx-background-color: #e0f7fa; -fx-background-radius: 8; -fx-padding: 8; -fx-border-color: #90caf9; -fx-border-width: 1;");

        Label dateLabel = new Label(record.getFormattedTimestamp());
        dateLabel.setPrefWidth(280);
        dateLabel.setFont(Font.font("Segoe UI", 15));
        dateLabel.setTextFill(Color.web("#2e4f4f"));

        Label soldLabel = new Label(String.valueOf(record.getTotalPlayersSold()));
        soldLabel.setPrefWidth(160);
        soldLabel.setFont(Font.font("Segoe UI", 15));
        soldLabel.setTextFill(Color.web("#2e4f4f"));

        Label durationLabel = new Label(String.valueOf(record.getTotalAuctionDurationSeconds()));
        durationLabel.setPrefWidth(160);
        durationLabel.setFont(Font.font("Segoe UI", 15));
        durationLabel.setTextFill(Color.web("#2e4f4f"));

        Label idLabel = new Label(record.getAuctionId().substring(0, 8) + "...");
        idLabel.setPrefWidth(230);
        idLabel.setFont(Font.font("Segoe UI", 15));
        idLabel.setTextFill(Color.web("#2e4f4f"));

        row.getChildren().addAll(dateLabel, soldLabel, durationLabel, idLabel);
        row.setOnMouseClicked((MouseEvent event) -> showAuctionDetails(record));
        return row;
    }

    private void showAuctionDetails(AuctionRecord record) {
        StringBuilder details = new StringBuilder();
        details.append("Auction Date: ").append(record.getFormattedTimestamp()).append("\n");
        details.append("Duration: ").append(record.getTotalAuctionDurationSeconds()).append(" seconds\n");
        details.append("Players Sold: ").append(record.getTotalPlayersSold()).append("\n\n");
        for (Player p : record.getPlayersSoldDetails()) {
            details.append("Name: ").append(p.getName())
                    .append("\nClub: ").append(p.getClub())
                    .append("\nFinal Bid: $").append(String.format("%,d", p.getFinalBidPrice()))
                    .append("\n----------------------\n");
        }

        showPopup(details.toString(), 500, 400);
    }

    private void showPopup(String message, int width, int height) {
        Stage popup = new Stage();
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setFont(Font.font("Segoe UI", 15));
        textArea.setStyle("-fx-control-inner-background: #f3e5f5; -fx-text-fill: #4a148c;");
    }

    private void handleBackToDashboard(){
        String userType = Session.getUserType();
        System.out.println("back tiptesi.");
        if (userType.equalsIgnoreCase("Admin")) {
            Main.setRoot("AdminDashboard.fxml");
        } else if (userType.equalsIgnoreCase("Viewer")) {
            Main.setRoot("ViewerDashboard.fxml");
        } else if (userType.equalsIgnoreCase("Club")) {
            Main.setRoot("ClubDashboard.fxml");
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event){
        handleBackToDashboard();
    }
}