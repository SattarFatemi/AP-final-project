package GuiController.Teacher;

import GuiController.Popup;
import Logic.Controller;
import Logic.Models.*;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MainPage implements Initializable {

    private static Logger log = LogManager.getLogger(MainPage.class);

    @FXML
    private Button addStudentButton;
    @FXML
    private VBox addStudentVBox;
    @FXML
    private Button addTeacherButton;
    @FXML
    private VBox addTeacherVBox;
    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button educationButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private Button mainPageButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button registryButton;
    @FXML
    private Button reportButton;
    @FXML
    private TextField studentEmailTextField;
    @FXML
    private ComboBox<String> studentGradeComboBox;
    @FXML
    private TextField studentNameTextField;
    @FXML
    private TextField studentPasswordTextField;
    @FXML
    private TextField studentUsernameTextField;
    @FXML
    private TextField teacherEmailTextField;
    @FXML
    private ComboBox<String> teacherLevelComboBox;
    @FXML
    private TextField teacherNameTextField;
    @FXML
    private TextField teacherNumberTextField;
    @FXML
    private TextField teacherPasswordTextField;
    @FXML
    private TextField teacherPhoneNumberTextField;
    @FXML
    private TextField teacherRoomNumberTextField;
    @FXML
    private TextField teacherUsernameTextField;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField teacherNationalCodeTextField;
    @FXML
    private TextField studentNumberTextField;
    @FXML
    private TextField studentNationalCodeTextField;
    @FXML
    private TextField studentPhoneNumberTextField;
    @FXML
    private TextField studentEntryYearTextField;
    @FXML
    private ComboBox<String> studentSupervisorComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reload();

    }

    public void addTeacher(ActionEvent actionEvent) {

        Teacher newTeacher = new Teacher();
        newTeacher.setName(teacherNameTextField.getText());
        newTeacher.setUsername(teacherUsernameTextField.getText());
        newTeacher.setPassword(teacherPasswordTextField.getText());
        newTeacher.setEmail(teacherEmailTextField.getText());
        newTeacher.setNumberOfTeacher(teacherNumberTextField.getText());
        newTeacher.setRoomNumber(teacherRoomNumberTextField.getText());
        newTeacher.setPhoneNumber(teacherPhoneNumberTextField.getText());
        newTeacher.setNameOfDepartment(Controller.getCurrentUser().getNameOfDepartment());
        newTeacher.setTeacherPosition(Teacher.TeacherPosition.TEACHER);
        newTeacher.setNationalCode(teacherNationalCodeTextField.getText());
        newTeacher.setNumberOfLessons(new ArrayList<>());

        switch (teacherLevelComboBox.getValue()) {
            case "ASSISTANT_PROFESSOR":
                newTeacher.setTeacherLevel(Teacher.TeacherLevel.ASSISTANT_PROFESSOR);
                break;
            case "ASSOCIATE_PROFESSOR":
                newTeacher.setTeacherLevel(Teacher.TeacherLevel.ASSOCIATE_PROFESSOR);
                break;
            case "PROFESSOR":
                newTeacher.setTeacherLevel(Teacher.TeacherLevel.PROFESSOR);
                break;
            default:
                break;
        }

        if (University.findUserByUsername(newTeacher.getUsername()) != null) {
            Popup.showError("Username", "yek karbar ba in username vojood darad.");
        }
        else {
            Popup.showAlert("SUCCESS", "karbar ezafeh shod.");
            Controller.getCurrentUser().getDepartment().addTeacher(newTeacher);

            log.info(newTeacher.getUsername() + " be onvane Teacher, tavassote " + Controller.getCurrentUser().getUsername() + " ezafeh shod");
        }
    }

    public void addStudent(ActionEvent actionEvent) {

        Student newStudent = new Student();
        newStudent.setName(studentNameTextField.getText());
        newStudent.setUsername(studentUsernameTextField.getText());
        newStudent.setPassword(studentPasswordTextField.getText());
        newStudent.setEmail(studentEmailTextField.getText());
        newStudent.setNameOfDepartment(Controller.getCurrentUser().getNameOfDepartment());
        newStudent.setStudentNumber(studentNumberTextField.getText());
        newStudent.setNationalCode(studentNationalCodeTextField.getText());
        newStudent.setPhoneNumber(studentPhoneNumberTextField.getText());
        newStudent.setEntryYear(Integer.parseInt(studentEntryYearTextField.getText()));

        newStudent.setSupervisor(University.findTeacherByName(studentSupervisorComboBox.getValue()));

        switch (studentGradeComboBox.getValue()) {
            case "UNDERGRADUATE":
                newStudent.setStudentGrade(Student.StudentGrade.UNDERGRADUATE);
                break;
            case "MASTERS":
                newStudent.setStudentGrade(Student.StudentGrade.MASTERS);
                break;
            case "PHD":
                newStudent.setStudentGrade(Student.StudentGrade.PHD);
                break;
            default:
                break;
        }

        if (University.findUserByUsername(newStudent.getUsername()) != null) {
            Popup.showError("Username", "yek karbar ba in username vojood darad.");
        }
        else {
            Popup.showAlert("SUCCESS", "karbar ezafeh shod.");
            Controller.getCurrentUser().getDepartment().addStudent(newStudent);

            log.info(newStudent.getUsername() + " be onvane Student, tavassote " + Controller.getCurrentUser().getUsername() + " ezafeh shod");
        }

    }


    public static class RowOfTable {

        public String title;
        public String explanation;

        public RowOfTable(String title, String explanation) {
            this.title = title;
            this.explanation = explanation;
        }

        public String getTitle() {
            return title;
        }

        public String getExplanation() {
            return explanation;
        }

    }

    public void reload() {
        initUserinfo();
        setLastVisitLabel();
        setCurrentTimeLabel();
        initAddUserVBoxes();
    }


    private void initUserinfo() {

        Image image = new Image(Controller.getCurrentUser().getImagePath());
        userImageView.setImage(image);

        usernameLabel.setText(Controller.getCurrentUser().getUsername());

        userEmailLabel.setText(Controller.getCurrentUser().getEmail());

    }

    private void initAddUserVBoxes() {

        if (((Teacher) Controller.getCurrentUser()).getTeacherPosition() == Teacher.TeacherPosition.EDUCATIONAL_ASSISTANT) {
            addTeacherVBox.setVisible(true);
            addStudentVBox.setVisible(true);
        }
        else if (((Teacher) Controller.getCurrentUser()).getTeacherPosition() == Teacher.TeacherPosition.HEAD_OF_DEPARTMENT) {
            addTeacherVBox.setVisible(true);
            addStudentVBox.setVisible(false);
        }
        else {
            addTeacherVBox.setVisible(false);
            addStudentVBox.setVisible(false);
            return;
        }

        ObservableList<String> teacherLevelOptions =
                FXCollections.observableArrayList(
                        "ASSISTANT_PROFESSOR",
                        "ASSOCIATE_PROFESSOR",
                        "PROFESSOR"
                );
        teacherLevelComboBox.getItems().addAll(teacherLevelOptions);

        ObservableList<String> studentGradeOptions =
                FXCollections.observableArrayList(
                        "UNDERGRADUATE",
                        "MASTERS",
                        "PHD"
                );
        studentGradeComboBox.getItems().addAll(studentGradeOptions);

        ObservableList<String> studentSupervisorOptions = FXCollections.observableArrayList();
        for (Teacher teacher : Controller.getCurrentUser().getDepartment().getTeachers()) {
            studentSupervisorOptions.add(teacher.getName());
        }
        studentSupervisorComboBox.getItems().addAll(studentSupervisorOptions);

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
}
