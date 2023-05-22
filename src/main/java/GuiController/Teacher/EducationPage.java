package GuiController.Teacher;

import Logic.Controller;
import Logic.Models.Lesson;
import Logic.Models.Request;
import Logic.Models.Student;
import Logic.Models.Teacher;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class EducationPage implements Initializable {

    @FXML
    private TableColumn<Lesson, String> nameOfLessonForWeekPlanColumn;
    @FXML
    private TableColumn<Lesson, String> nameOfLessonForExamsColumn;
    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button educationButton;
    @FXML
    private TableView<Lesson> examsTable;
    @FXML
    private Button exitButton;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private Button mainPageButton;
    @FXML
    private TableColumn<Lesson, String> numberOfGroupOfLessonForExamsColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfGroupOfLessonForWeekPlanColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfLessonForExamsColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfLessonForWeekPlanColumn;
    @FXML
    private Button profileButton;
    @FXML
    private Button registryButton;
    @FXML
    private Button reportButton;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private TableColumn<Lesson, Date> timeOfExamForExamsColumn;
    @FXML
    private TableColumn<Lesson, String> timeOfLessonForWeekPlanColumn;
    @FXML
    private TableView<Lesson> weekPlanTable;
    @FXML
    private Button recommendationRequestsButton;
    @FXML
    private Button minorRequestsButton;
    @FXML
    private Button withdrawalRequestsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonsOfRequests();
        initTables();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initButtonsOfRequests() {

        Teacher teacher = (Teacher) Controller.getCurrentUser();

        disableAllButtonsOfRequests();

        if (teacher.getTeacherPosition() == Teacher.TeacherPosition.EDUCATIONAL_ASSISTANT) {
            enableButton(recommendationRequestsButton);
            enableButton(minorRequestsButton);
            enableButton(withdrawalRequestsButton);
        }
        else {
            enableButton(recommendationRequestsButton);
        }

    }

    private void disableAllButtonsOfRequests() {

        disableButton(recommendationRequestsButton);
        disableButton(minorRequestsButton);
        disableButton(withdrawalRequestsButton);

    }

    private void disableButton(Button button) {
        button.setVisible(false);
        button.setManaged(false);
    }

    private void enableButton(Button button) {
        button.setVisible(true);
        button.setManaged(true);
    }

    private void initTables() {

        nameOfLessonForWeekPlanColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonForWeekPlanColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        numberOfGroupOfLessonForWeekPlanColumn.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));
        timeOfLessonForWeekPlanColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfLesson"));

        weekPlanTable.getColumns().addAll(nameOfLessonForWeekPlanColumn, numberOfLessonForWeekPlanColumn,
                numberOfGroupOfLessonForWeekPlanColumn, timeOfLessonForWeekPlanColumn);

        nameOfLessonForExamsColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonForExamsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        numberOfGroupOfLessonForExamsColumn.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));
        timeOfExamForExamsColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfExam"));

        examsTable.getColumns().addAll(nameOfLessonForExamsColumn, numberOfLessonForExamsColumn,
                numberOfGroupOfLessonForExamsColumn, timeOfExamForExamsColumn);

        reloadTables();
    }

    private void reloadTables() {

        weekPlanTable.getItems().clear();
        for (Lesson lesson : ((Teacher) Controller.getCurrentUser()).getLessons()) {
            weekPlanTable.getItems().add(lesson);
        }

        examsTable.getItems().clear();
        for (Lesson lesson : ((Teacher) Controller.getCurrentUser()).getLessons()) {
            examsTable.getItems().add(lesson);
        }

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

    public void recommendationRequests(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.RECOMMENDATION);
        Controller.changeScene("Teacher/requestPage.fxml");
    }

    public void minorRequests(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.MINOR);
        Controller.changeScene("Teacher/requestPage.fxml");
    }

    public void withdrawalRequests(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.WITHDRAWAL);
        Controller.changeScene("Teacher/requestPage.fxml");
    }
}
