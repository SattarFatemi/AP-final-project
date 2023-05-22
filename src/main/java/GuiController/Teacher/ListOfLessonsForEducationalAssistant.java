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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import org.apache.logging.log4j.util.StackLocatorUtil;
import org.w3c.dom.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ListOfLessonsForEducationalAssistant implements Initializable {

    @FXML
    private TableColumn<Lesson, String> nameOfLessonColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfLessonColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfUnitsColumn;
    @FXML
    private TableColumn<Lesson, String> nameOfDepartmentOfLessonColumn;
    @FXML
    private TableColumn<Lesson, String> nameOfTeacherOfLessonColumn;
    @FXML
    private TableColumn<Lesson, String> explanationOfLessonColumn;
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
    private TableView<Lesson> table;
    @FXML
    private TextField nameOfLessonTextField;
    @FXML
    private TextField numberOfLessonTextField;
    @FXML
    private TextField numberOfUnitsTextField;
    @FXML
    private ComboBox<String> nameOfTeacherComboBox;
    @FXML
    private ComboBox<String> gradeOfLessonComboBox;
    @FXML
    private Button addLessonButton;
    @FXML
    private TextField numberOfGroupTextField;
    @FXML
    private TextField timeOfLessonTextField;
    @FXML
    private TextField timeOfExamTextField;
    @FXML
    private TableColumn<Lesson, String> deleteLessonColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initComboBoxes();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initTable() {

        nameOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        numberOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfUnits"));
        nameOfDepartmentOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfDepartment"));
        nameOfTeacherOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfTeacher"));
        explanationOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfGrade"));
        addDeleteLessonButton();

        table.getColumns().addAll(nameOfLessonColumn, numberOfLessonColumn, numberOfLessonColumn,
                nameOfDepartmentOfLessonColumn, nameOfTeacherOfLessonColumn, explanationOfLessonColumn);

        initEditableColumns();
        reloadTable();
    }

    private void initEditableColumns() {

        table.setEditable(true);

        nameOfLessonColumn.setEditable(true);
        nameOfLessonColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameOfLessonColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setNameOfLesson(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });

        numberOfUnitsColumn.setEditable(true);
        numberOfUnitsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberOfUnitsColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setNumberOfUnits(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });

        nameOfTeacherOfLessonColumn.setEditable(true);
        ObservableList<String> comboBoxOptionsForNameOfTeacher = FXCollections.observableArrayList();
        for (Teacher teacher : Controller.getCurrentUser().getDepartment().getTeachers()) {
            comboBoxOptionsForNameOfTeacher.add(teacher.getName());
        }
        nameOfTeacherOfLessonColumn.setCellFactory(ComboBoxTableCell.forTableColumn(comboBoxOptionsForNameOfTeacher));
        nameOfTeacherOfLessonColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setTeacherByName(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });


        explanationOfLessonColumn.setEditable(true);
        explanationOfLessonColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        explanationOfLessonColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setNameOfGrade(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });
    }

    private void addDeleteLessonButton() {

        Callback<TableColumn<Lesson, String>, TableCell<Lesson, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Lesson, String> call(TableColumn<Lesson, String> param) {
                final TableCell<Lesson, String> cell = new TableCell<>() {

                    private final Button button = new Button("Hazf");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Lesson lesson = getTableView().getItems().get(getIndex());

                            if (hasAccess(lesson)) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Hazfe Dars?");
                                alert.setContentText("dars hazf khahad shod...");
                                alert.showAndWait();
                                if (alert.getResult() == ButtonType.OK) {
                                    lesson.getDepartment().removeLesson(lesson);
                                }
                            }
                            else {
                                Popup.showAccessError();
                            }

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

        deleteLessonColumn.setCellFactory(cellFactory);

    }

    private <T> boolean hasAccess(TableColumn.CellEditEvent<Lesson, T> e) {
        return e.getTableView().getItems().get(e.getTablePosition().getRow()).getNameOfDepartment().equals(Controller.getCurrentUser().getNameOfDepartment());
    }

    private boolean hasAccess(Lesson lesson) {
        return lesson.getDepartment().getName().equals(Controller.getTargetUser().getDepartment().getName());
    }

    private void initComboBoxes() {

        ObservableList<String> teachersOfDepartment = FXCollections.observableArrayList();
        for (Teacher teacher : Controller.getCurrentUser().getDepartment().getTeachers()) {
            teachersOfDepartment.add(teacher.getName());
        }
        nameOfTeacherComboBox.getItems().addAll(teachersOfDepartment);

        ObservableList<String> gradeOptions = FXCollections.observableArrayList(
                "Karshenasi",
                "Karshenasi Arshad",
                "Doctora"
        );
        gradeOfLessonComboBox.getItems().addAll(gradeOptions);

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

    @FXML
    private void addLesson() {

        Lesson newLesson = new Lesson(nameOfLessonTextField.getText(),
                Controller.getCurrentUser().getNameOfDepartment(),
                University.findTeacherByName(nameOfTeacherComboBox.getValue()).getNumberOfTeacher(),
                numberOfUnitsTextField.getText(),
                gradeOfLessonComboBox.getValue(),
                numberOfLessonTextField.getText(),
                numberOfGroupTextField.getText(),
                timeOfLessonTextField.getText(),
                timeOfExamTextField.getText());

        newLesson.setStudentsAndScores(new ArrayList<>());
        for (Student student : Controller.getCurrentUser().getDepartment().getStudents()) {
            StudentAndScore studentAndScore = new StudentAndScore(student.getStudentNumber(), -1.0);
            newLesson.addStudentAndScore(studentAndScore);
        }
        Controller.getCurrentUser().getDepartment().addLesson(newLesson);


        reloadTable();
    }
}
