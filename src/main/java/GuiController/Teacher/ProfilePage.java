package GuiController.Teacher;

import Logic.Controller;
import Logic.Models.Student;
import Logic.Models.Teacher;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProfilePage implements Initializable {

    private static Logger log = LogManager.getLogger(ProfilePage.class);

    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button educationButton;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button exitButton;
    @FXML
    private Label imagePathLabel;
    @FXML
    private TextField imagePathTextField;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private Button mainPageButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label nameOfDepartmentLabel;
    @FXML
    private Label nationalCodeLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button profileButton;
    @FXML
    private Button registryButton;
    @FXML
    private Button reportButton;
    @FXML
    private Label roomNumberLabel;
    @FXML
    private Button setEmailButton;
    @FXML
    private Button setImagePathButton;
    @FXML
    private Button setPhoneNumberButton;
    @FXML
    private Label teacherLevelLabel;
    @FXML
    private Label teacherNumberLabel;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reload();

    }

    private void reload() {
        reloadUserInfo();
    }

    private void reloadUserInfo() {

        Teacher teacher = (Teacher) Controller.getCurrentUser();

        // reload list
        nameLabel.setText(teacher.getName());
        nationalCodeLabel.setText(teacher.getNationalCode());
        teacherNumberLabel.setText(teacher.getNumberOfTeacher());
        phoneNumberLabel.setText(teacher.getPhoneNumber());
        emailLabel.setText(teacher.getEmail());
        nameOfDepartmentLabel.setText(teacher.getNameOfDepartment());
        roomNumberLabel.setText(teacher.getRoomNumber());
        imagePathLabel.setText(teacher.getImagePath());
        teacherLevelLabel.setText(teacher.getTeacherLevelString());

        // image, name and email
        Image image;
        try {
            image = new Image(teacher.getImagePath());
        }
        catch (IllegalArgumentException e) {
            teacher.setImagePath("images\\Users\\default.jpg");
            image = new Image(teacher.getImagePath());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Aks");
            alert.setContentText("addrese aks dorost nist!");
            alert.show();
        }
        userImageView.setImage(image);

        usernameLabel.setText(teacher.getUsername());
        userEmailLabel.setText(teacher.getEmail());
    }

    public void setPhoneNumber(ActionEvent actionEvent) {
        Controller.getCurrentUser().setPhoneNumber(phoneNumberTextField.getText());

        log.info(Controller.getCurrentUser().getUsername() + " phone number ro be " + Controller.getCurrentUser().getPhoneNumber() + " taghir dad");
        reload();
    }

    public void setEmail(ActionEvent actionEvent) {
        Controller.getCurrentUser().setEmail(emailTextField.getText());

        log.info(Controller.getCurrentUser().getUsername() + " email ro be " + Controller.getCurrentUser().getEmail() + " taghir dad");
        reload();
    }

    public void setImagePath(ActionEvent actionEvent) {
        Controller.getCurrentUser().setImagePath(imagePathTextField.getText());

        log.info(Controller.getCurrentUser().getUsername() + " image path ro be " + Controller.getCurrentUser().getImagePath() + " taghir dad");
        reload();
    }

    public void logout(ActionEvent actionEvent) {
        Controller.logout();
    }

    public void backToPreviousScene(ActionEvent actionEvent) {
        Controller.goToPreviousScene();
    }

    public void setLastVisitLabel() {
        lastVisitLabel.setText(Controller.getCurrentUser().getLastVisit());
    }

    public void setCurrentTimeLabel() {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                currentTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    public void goToRegistryPage(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/registry.fxml");
    }

    public void goToEducationPage(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/education.fxml");
    }

    public void goToReportPage(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/report.fxml");
    }

    public void goToProfilePage(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/profile.fxml");
    }

    public void goToMainPage(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/mainPage.fxml");
    }
}
