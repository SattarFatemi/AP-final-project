package GuiController.Student;

import Logic.Controller;
import Logic.Models.Lesson;
import Logic.Models.Teacher;
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

public class ListOfTeachers implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private TableColumn<Object, Object> departmentOfTeacherColumn;
    @FXML
    private Button educationButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private TableColumn<Object, Object> levelOfTeacherColumn;
    @FXML
    private Button mainPageButton;
    @FXML
    private TableColumn<Object, Object> nameOfTeacherColumn;
    @FXML
    private TableColumn<Object, Object> numberOfRoomColumn;
    @FXML
    private TableColumn<Object, Object> numberOfTeacherColumn;
    @FXML
    private TableColumn<Object, Object> phoneNumberColumn;
    @FXML
    private Button profileButton;
    @FXML
    private Button registryButton;
    @FXML
    private Button reloadButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button searchByDepartmentButton;
    @FXML
    private TextField searchByDepartmentTextField;
    @FXML
    private Button searchByNameButton;
    @FXML
    private TextField searchByNameTextField;
    @FXML
    private Button searchByTeacherLevelButton;
    @FXML
    private TextField searchByTeacherLevelTextField;
    @FXML
    private TableView<Object> table;
    @FXML
    private ToggleButton themeToggleButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initTable() {

        table.getItems().clear();

        nameOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        levelOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherLevel"));
        numberOfRoomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        departmentOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfDepartment"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        numberOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTeacher"));

        table.getColumns().addAll(nameOfTeacherColumn, levelOfTeacherColumn, numberOfRoomColumn,
                departmentOfTeacherColumn, phoneNumberColumn, numberOfTeacherColumn);

        reloadTable();
    }

    @FXML
    private void reloadTable() {

        searchByDepartmentTextField.clear();
        searchByTeacherLevelTextField.clear();
        searchByNameTextField.clear();

        table.getItems().clear();

        for (Teacher teacher: University.getTeachers()) {
            table.getItems().add(teacher);
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

        for (Teacher teacher: University.getTeachers()) {
            if (searchedName.equals(teacher.getName())) table.getItems().add(teacher);
        }

    }

    public void searchByTeacherLevel(ActionEvent actionEvent) {

        String searchedLevel = searchByTeacherLevelTextField.getText();

        table.getItems().clear();

        for (Teacher teacher: University.getTeachers()) {
            if (searchedLevel.equals(teacher.getTeacherLevelString())) table.getItems().add(teacher);
        }

    }

    public void searchByDepartment(ActionEvent actionEvent) {

        String searchedDepartment = searchByDepartmentTextField.getText();

        table.getItems().clear();

        for (Teacher teacher: University.getTeachers()) {
            if (searchedDepartment.equals(teacher.getDepartment().getName())) table.getItems().add(teacher);
        }

    }
}
