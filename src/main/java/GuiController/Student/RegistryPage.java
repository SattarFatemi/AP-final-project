package GuiController.Student;

import Logic.Controller;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegistryPage implements Initializable {

    @FXML
    private Button educationButton;
    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button reportButton;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private Button listOfTeachersButton;
    @FXML
    private Button listOfLessonsButton;
    @FXML
    private Button mainPageButton;
    @FXML
    private Button registryButton;
    @FXML
    private ToggleButton themeToggleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLastVisitLabel();
        setCurrentTimeLabel();
    }

    public void goToListOfLessonsPage(ActionEvent actionEvent) {
        Controller.changeScene("Student/listOfLessons.fxml");
    }

    public void goToListOfTeachersPage(ActionEvent actionEvent) {
        Controller.changeScene("Student/listOfTeachers.fxml");
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
        Controller.changeScene("Student/registry.fxml");
    }

    public void goToEducationPage(ActionEvent actionEvent) {
        Controller.changeScene("Student/education.fxml");
    }

    public void goToReportPage(ActionEvent actionEvent) {
        Controller.changeScene("Student/report.fxml");
    }

    public void goToProfilePage(ActionEvent actionEvent) {
        Controller.changeScene("Student/profile.fxml");
    }

    public void goToMainPage(ActionEvent actionEvent) {
        Controller.changeScene("Student/mainPage.fxml");
    }
}
