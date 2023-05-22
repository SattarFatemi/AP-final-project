package GuiController.Teacher;

import Logic.Controller;
import Logic.Models.Lesson;
import Logic.Models.Student;
import Logic.Models.StudentAndScore;
import Logic.Models.University;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainTemporaryScoresPageForEducationalAssistant implements Initializable {

    @FXML
    private TableColumn<Lesson, String> averagePassedScoreColumn;
    @FXML
    private TableColumn<Lesson, String> averageScoreColumn;
    @FXML
    private TableColumn<Student, String> averageScoreOfStudentColumn;
    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button educationButton;
    @FXML
    private Button exitButton;
    @FXML
    private TableColumn<Student, String> goToTemporaryScoresPageColumn;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private TableView<Lesson> lessonsTable;
    @FXML
    private Button mainPageButton;
    @FXML
    private TableColumn<Lesson, String> nameOfLessonsColumn;
    @FXML
    private TableColumn<Student, String> nameOfStudentsColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfFailedColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfLessonsColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfPassedColumn;
    @FXML
    private TableColumn<Student, String> numberOfStudentsColumn;
    @FXML
    private Button profileButton;
    @FXML
    private TableColumn<Lesson, String> protestColumn;
    @FXML
    private Button registryButton;
    @FXML
    private Button reloadStudentsTableButton;
    @FXML
    private Button reloadLessonsTableButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button searchByNameOfStudentButton;
    @FXML
    private TextField searchByNameOfStudentTextField;
    @FXML
    private Button searchByNameOfTeacherButton;
    @FXML
    private TextField searchByNameOfTeacherTextField;
    @FXML
    private Button searchByNumberOfStudentButton;
    @FXML
    private TextField searchByNumberOfStudentTextField;
    @FXML
    private Button searchByNumberOfTeacherButton;
    @FXML
    private TextField searchByNumberOfTeacherTextField;
    @FXML
    private TableColumn<Lesson, String> setScoreColumn;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private TableView<Student> studentsTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLessonsTable();
        initStudentsTable();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    public void initLessonsTable() {

        nameOfLessonsColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        numberOfPassedColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPassed"));
        numberOfFailedColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfFailed"));
        averageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        averagePassedScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averagePassedScore"));
        addProtestButtons();
        addSetScoreButtons();

        lessonsTable.getColumns().addAll(nameOfLessonsColumn, numberOfLessonsColumn, numberOfPassedColumn,
                numberOfFailedColumn, averageScoreColumn, averagePassedScoreColumn);

        reloadLessonsTable(new ActionEvent());
    }

    public void reloadLessonsTable(ActionEvent actionEvent) {

        searchByNameOfTeacherTextField.clear();
        searchByNumberOfTeacherTextField.clear();

        lessonsTable.getItems().clear();

        for (Lesson lesson : Controller.getCurrentUser().getDepartment().getLessons()) {
            lessonsTable.getItems().add(lesson);
        }

    }

    public void searchByNumberOfTeacher(ActionEvent actionEvent) {

        lessonsTable.getItems().clear();

        for (Lesson lesson : Controller.getCurrentUser().getDepartment().getLessons()) {
            if (lesson.getNumberOfTeacher().equals(searchByNumberOfTeacherTextField.getText())) {
                lessonsTable.getItems().add(lesson);
            }
        }

        searchByNameOfTeacherTextField.clear();
        searchByNumberOfTeacherTextField.clear();
    }

    public void searchByNameOfTeacher(ActionEvent actionEvent) {

        lessonsTable.getItems().clear();

        for (Lesson lesson : Controller.getCurrentUser().getDepartment().getLessons()) {
            if (University.findTeacherByNumber(lesson.getNumberOfTeacher()).getName().equals(searchByNameOfTeacherTextField.getText())) {
                lessonsTable.getItems().add(lesson);
            }
        }

        searchByNameOfTeacherTextField.clear();
        searchByNumberOfTeacherTextField.clear();
    }

    public void initStudentsTable() {

        nameOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        averageScoreOfStudentColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        addGoToTemporaryScorePageButton();

        studentsTable.getColumns().addAll(nameOfStudentsColumn, numberOfStudentsColumn,
                averageScoreOfStudentColumn, goToTemporaryScoresPageColumn);

        reloadStudentsTable(new ActionEvent());
    }

    public void reloadStudentsTable(ActionEvent actionEvent) {

        searchByNameOfStudentTextField.clear();
        searchByNumberOfStudentTextField.clear();

        studentsTable.getItems().clear();

        for (Student student : Controller.getCurrentUser().getDepartment().getStudents()) {
            studentsTable.getItems().add(student);
        }

    }

    public void searchByNumberOfStudent(ActionEvent actionEvent) {

        studentsTable.getItems().clear();

        for (Student student : Controller.getCurrentUser().getDepartment().getStudents()) {
            if (student.getStudentNumber().equals(searchByNumberOfStudentTextField.getText())) {
                studentsTable.getItems().add(student);
            }
        }

        searchByNumberOfStudentTextField.clear();
        searchByNameOfStudentTextField.clear();
    }

    public void searchByNameOfStudent(ActionEvent actionEvent) {

        studentsTable.getItems().clear();

        for (Student student : Controller.getCurrentUser().getDepartment().getStudents()) {
            if (student.getName().equals(searchByNameOfStudentTextField.getText())) {
                studentsTable.getItems().add(student);
            }
        }

        searchByNumberOfStudentTextField.clear();
        searchByNameOfStudentTextField.clear();
    }

    private void addProtestButtons() {

        Callback<TableColumn<Lesson, String>, TableCell<Lesson, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Lesson, String> call(TableColumn<Lesson, String> param) {
                final TableCell<Lesson, String> cell = new TableCell<>() {

                    private final Button button = new Button("Baresi");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Lesson lesson = getTableView().getItems().get(getIndex());

                            Controller.setTargetUser(University.findTeacherByNumber(lesson.getNumberOfTeacher()));
                            Controller.changeScene("Teacher/temporaryScoresPage.fxml");

                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        protestColumn.setCellFactory(cellFactory);
    }

    private void addSetScoreButtons() {

        Callback<TableColumn<Lesson, String>, TableCell<Lesson, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Lesson, String> call(TableColumn<Lesson, String> param) {
                final TableCell<Lesson, String> cell = new TableCell<>() {

                    private final Button button = new Button("Entekhab");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Lesson lesson = getTableView().getItems().get(getIndex());

                            Controller.setTargetUser(University.findTeacherByNumber(lesson.getNumberOfTeacher()));
                            Controller.changeScene("Teacher/temporaryScoresPage.fxml");

                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        setScoreColumn.setCellFactory(cellFactory);
    }

    private void addGoToTemporaryScorePageButton() {

        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Student, String> call(TableColumn<Student, String> param) {
                final TableCell<Student, String> cell = new TableCell<>() {

                    private final Button button = new Button("Baresi");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Student student = getTableView().getItems().get(getIndex());

                            Controller.setTargetUser(student);
                            Controller.changeScene("Student/temporaryScoresPage.fxml");

                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        goToTemporaryScoresPageColumn.setCellFactory(cellFactory);
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
