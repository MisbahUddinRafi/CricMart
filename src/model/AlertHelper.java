package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;


public class AlertHelper {
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Adds the error style and sets the prompt text.
    public static void highlightError(TextField field, String message) {
        if (!field.getStyleClass().contains("input-error")) {
            field.getStyleClass().add("input-error");
        }
        field.clear();
        field.setPromptText(message);
    }


    // Removes the error style from one or more fields.
    public static void clearErrorStyles(TextField... fields) {
        for (TextField field : fields) {
            field.getStyleClass().remove("input-error");
        }
    }

    // Overload: Clears error style from a list of fields.
    public static void clearErrorStyles(List<TextField> fields) {
        for (TextField field : fields) {
            field.getStyleClass().remove("input-error");
        }
    }



    public static boolean showConfirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }
}
