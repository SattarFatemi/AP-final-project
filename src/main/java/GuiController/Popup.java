package GuiController;

import javafx.scene.control.Alert;

public class Popup {

    public static void showAlert(String title, String text) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(text);
        alert.setTitle(title);
        alert.showAndWait();

    }

    public static void showError(String title, String text) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.setTitle(title);
        alert.showAndWait();

    }

    public static void showAccessError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Dastresi");
        alert.setContentText("Shoma be in mored dastresi nadarid!");
        alert.show();
    }

}
