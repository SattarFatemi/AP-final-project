package GuiController.Student;

import Logic.Controller;
import Logic.Models.Lesson;
import Logic.Models.University;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListOfLessons implements Initializable {

    @FXML
    private TableColumn<Object, Object> nameOfLessonColumn;
    @FXML
    private TableColumn<Object, Object> numberOfLessonColumn;
    @FXML
    private TableColumn<Object, Object> numberOfUnitsColumn;
    @FXML
    private TableColumn<Object, Object> nameOfDepartmentOfLessonColumn;
    @FXML
    private TableColumn<Object, Object> nameOfTeacherOfLessonColumn;
    @FXML
    private TableColumn<Object, Object> explanationOfLessonColumn;
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
    private Button searchByNameButton;
    @FXML
    private TextField searchByNameTextField;
    @FXML
    private Button searchByNumberOfLessonButton;
    @FXML
    private TextField searchByNumberOfLessonTextField;
    @FXML
    private Button searchByUnitsButton;
    @FXML
    private TextField searchByUnitsTextField;
    @FXML
    private TableView<Object> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initTable() {

        table.getItems().clear();

        nameOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        numberOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfUnits"));
        nameOfDepartmentOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfDepartment"));
        nameOfTeacherOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfTeacher"));
        explanationOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfGrade"));

        table.getColumns().addAll(nameOfLessonColumn, numberOfLessonColumn, numberOfLessonColumn,
                nameOfDepartmentOfLessonColumn, nameOfTeacherOfLessonColumn, explanationOfLessonColumn);

        reloadTable();
    }

    @FXML
    private void reloadTable() {

        searchByUnitsTextField.clear();
        searchByNumberOfLessonTextField.clear();
        searchByNameTextField.clear();

        table.getItems().clear();

        for (Lesson lesson: University.getLessons()) {
            table.getItems().add(lesson);
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

    public void searchByName(ActionEvent actionEvent) {

        String searchedName = searchByNameTextField.getText();

        table.getItems().clear();

        for (Lesson lesson: University.getLessons()) {
            if (searchedName.equals(lesson.getNameOfLesson())) table.getItems().add(lesson);
        }

    }

    public void searchByNumberOfLesson(ActionEvent actionEvent) {

        String searchedNumber = searchByNumberOfLessonTextField.getText();

        table.getItems().clear();

        for (Lesson lesson: University.getLessons()) {
            if (searchedNumber.equals(lesson.getNumberOfLesson())) table.getItems().add(lesson);
        }

    }

    public void searchByUnits(ActionEvent actionEvent) {

        String searchedUnit = searchByUnitsTextField.getText();

        table.getItems().clear();

        for (Lesson lesson: University.getLessons()) {
            if (searchedUnit.equals(lesson.getNumberOfUnits())) table.getItems().add(lesson);
        }

    }
}
