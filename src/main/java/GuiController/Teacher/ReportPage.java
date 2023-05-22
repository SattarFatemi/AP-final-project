package GuiController.Teacher;

import Logic.Controller;
import Logic.Models.Student;
import Logic.Models.Teacher;
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

public class ReportPage implements Initializable {

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
    private Button mainPageButton;
    @FXML
    private Button registryButton;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private Button goToTemporaryScoresPageButton;
    @FXML
    private Button goToMainTemporaryScoresPageForEducationalAssistantButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtons();
        setLastVisitLabel();
        setCurrentTimeLabel();
    }

    private void initButtons() {
        if (((Teacher) Controller.getCurrentUser()).getTeacherPosition() != Teacher.TeacherPosition.EDUCATIONAL_ASSISTANT) {
            goToMainTemporaryScoresPageForEducationalAssistantButton.setVisible(false);
            goToMainTemporaryScoresPageForEducationalAssistantButton.setManaged(false);
        }
    }

    public void goToTemporaryScoresPage(ActionEvent actionEvent) {
        Controller.setTargetUser(Controller.getCurrentUser());
        Controller.changeScene("Teacher/temporaryScoresPage.fxml");
    }

    public void goToStudentStatusPage(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/temporaryScoresPage.fxml");
    }

    public void goToMainTemporaryScoresPageForEducationalAssistant(ActionEvent actionEvent) {
        Controller.changeScene("Teacher/mainTemporaryScoresPageForEducationalAssistant.fxml");
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
