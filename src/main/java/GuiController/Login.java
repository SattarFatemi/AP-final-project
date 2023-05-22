package GuiController;

import Logic.Controller;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private int recaptchaAnswer;
    private int numberOfRecaptcha = 0;

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    ImageView recaptchaImageView;
    @FXML
    Button reloadRecaptchaButton;
    @FXML
    TextField codeTextField;
    @FXML
    Button loginButton;
    @FXML
    Text resultText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reloadRecaptcha();

    }

    public void reloadRecaptcha() {

        numberOfRecaptcha++;
        if (numberOfRecaptcha == 6) numberOfRecaptcha = 1;
        setRecaptchaAnswer();
        setRecaptchaImage();

    }

    private void setRecaptchaAnswer() {

        switch (numberOfRecaptcha) {
            case 1:
                recaptchaAnswer = 6954;
                break;
            case 2:
                recaptchaAnswer = 8665;
                break;
            case 3:
                recaptchaAnswer = 5374;
                break;
            case 4:
                recaptchaAnswer = 5874;
                break;
            case 5:
                recaptchaAnswer = 4039;
                break;
        }

    }

    private void setRecaptchaImage() {

        Image image = new Image("Images\\Recaptcha\\" + numberOfRecaptcha + ".jpg");
        recaptchaImageView.setImage(image);

    }

    public void login(ActionEvent actionEvent) {

        if (usernameTextField.getText().length() == 0 || passwordField.getText().length() == 0) {
            Popup.showAlert("", "Lotfan ebteda field ha ro takmil konid!");
        }
        else if (!Integer.toString(recaptchaAnswer).equals(codeTextField.getText())) {
            Popup.showAlert("Recaptcha", "Code amniati nadorost ast!");
        }
        else {

            User user = University.findUserByUsername(usernameTextField.getText());

            if (user == null) {
                Popup.showAlert("User 404", "User peyda nashod!");
            }
            else {

                if (!Controller.checkCorrectPassword(user, passwordField.getText())) {
                    Popup.showAlert("Password", "Password eshtebahe! password ro faramoosh kardin? kari az dastam bar nemiad :)");
                }
                else {

                    // Successful Login
                    Controller.login(user);

                }
            }
        }
    }
}
