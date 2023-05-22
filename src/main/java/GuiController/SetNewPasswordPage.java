package GuiController;

import Logic.Controller;
import Logic.Models.Teacher;
import Logic.Models.University;
import Logic.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class SetNewPasswordPage {

    private static Logger log = LogManager.getLogger(SetNewPasswordPage.class);

    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private Text resultText;
    @FXML
    private Button savePasswordButton;

    public void savePassword(ActionEvent actionEvent) {

        if (newPasswordField.getText().length() == 0 || oldPasswordField.getText().length() == 0) {

            Popup.showAlert("", "Lotfan ebteda field ha ro takmil konid!");

        }
        else {

            User user = Controller.getCurrentUser();

            if (!Controller.checkCorrectPassword(user, oldPasswordField.getText())) {

                Popup.showAlert("Password", "Password eshtebahe! password ro faramoosh kardin? kari az dastam bar nemiad :)");

            }
            else {

                user.setPassword(newPasswordField.getText());

                log.info(user.getUsername() + " Login");

                if (user instanceof Teacher) Controller.changeScene("Teacher/mainPage.fxml");
                else Controller.changeScene("Student/mainPage.fxml");

            }
        }
    }
}
