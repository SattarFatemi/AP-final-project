package GuiController.Student;

import Logic.Controller;
import Logic.Models.Lesson;
import Logic.Models.Request;
import Logic.Models.Student;
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
    private Button requestForCertificateStudentButton;
    @FXML
    private Button requestForMinorButton;
    @FXML
    private Button requestForRecommendationButton;
    @FXML
    private Button requestForWithdrawalButton;
    @FXML
    private Button requestForDormitoryButton;
    @FXML
    private Button requestForDissertationButton;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private TableColumn<Lesson, Date> timeOfExamForExamsColumn;
    @FXML
    private TableColumn<Lesson, String> timeOfLessonForWeekPlanColumn;
    @FXML
    private TableView<Lesson> weekPlanTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonsOfRequests();
        initTables();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initButtonsOfRequests() {

        Student student = (Student) Controller.getCurrentUser();

        disableAllButtonsOfRequests();

        if (student.getStudentGrade() == Student.StudentGrade.UNDERGRADUATE) {

            enableButton(requestForRecommendationButton);
            enableButton(requestForCertificateStudentButton);
            enableButton(requestForMinorButton);
            enableButton(requestForWithdrawalButton);

        }
        else if (student.getStudentGrade() == Student.StudentGrade.MASTERS) {

            enableButton(requestForRecommendationButton);
            enableButton(requestForCertificateStudentButton);
            enableButton(requestForDormitoryButton);
            enableButton(requestForWithdrawalButton);

        }
        else if (student.getStudentGrade() == Student.StudentGrade.PHD) {

            enableButton(requestForCertificateStudentButton);
            enableButton(requestForWithdrawalButton);
            enableButton(requestForDissertationButton);

        }

    }

    private void disableAllButtonsOfRequests() {

        disableButton(requestForRecommendationButton);
        disableButton(requestForCertificateStudentButton);
        disableButton(requestForMinorButton);
        disableButton(requestForWithdrawalButton);
        disableButton(requestForDormitoryButton);
        disableButton(requestForDissertationButton);

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
        for (Lesson lesson : ((Student) Controller.getCurrentUser()).getLessons()) {
            weekPlanTable.getItems().add(lesson);
        }

        examsTable.getItems().clear();
        for (Lesson lesson : ((Student) Controller.getCurrentUser()).getLessons()) {
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

    public void requestForRecommendation(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.RECOMMENDATION);
        Controller.changeScene("Student/requestPage.fxml");
    }

    public void requestForCertificateStudent(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.CERTIFICATE_STUDENT);
        Controller.changeScene("Student/requestPage.fxml");
    }

    public void requestForMinor(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.MINOR);
        Controller.changeScene("Student/requestPage.fxml");
    }

    public void requestForWithdrawal(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.WITHDRAWAL);
        Controller.changeScene("Student/requestPage.fxml");
    }

    public void requestForDormitory(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.DORMITORY);
        Controller.changeScene("Student/requestPage.fxml");
    }

    public void requestForDissertation(ActionEvent actionEvent) {
        Controller.setLastRequestType(Request.RequestType.DISSERTATION);
        Controller.changeScene("Student/requestPage.fxml");
    }
}
