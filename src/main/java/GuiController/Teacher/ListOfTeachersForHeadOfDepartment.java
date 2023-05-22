package GuiController.Teacher;

import GuiController.Popup;
import Logic.Controller;
import Logic.Models.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListOfTeachersForHeadOfDepartment implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private TableColumn<Teacher, String> departmentOfTeacherColumn;
    @FXML
    private Button educationButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private TableColumn<Teacher, String> levelOfTeacherColumn;
    @FXML
    private Button mainPageButton;
    @FXML
    private TableColumn<Teacher, String> nameOfTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> numberOfRoomColumn;
    @FXML
    private TableColumn<Teacher, String> numberOfTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> phoneNumberColumn;
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
    private TableView<Teacher> table;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private TableColumn<Teacher, String> deleteTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> selectAssistantColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initTable() {

        table.getItems().clear();

        nameOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTeacher"));
        levelOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherLevelString"));
        numberOfRoomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        departmentOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfDepartment"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addEducationalAssistantButton();
        addDeleteButton();

        table.getColumns().addAll(nameOfTeacherColumn, numberOfTeacherColumn, levelOfTeacherColumn,
                numberOfRoomColumn, departmentOfTeacherColumn, phoneNumberColumn,
                selectAssistantColumn, deleteTeacherColumn);

        initEditableColumns();
        reloadTable();
    }

    private void initEditableColumns() {

        table.setEditable(true);

        nameOfTeacherColumn.setEditable(true);
        nameOfTeacherColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameOfTeacherColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });

        levelOfTeacherColumn.setEditable(true);
        levelOfTeacherColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        levelOfTeacherColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                if (e.getNewValue().equals("ASSISTANT_PROFESSOR")) {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setTeacherLevel(Teacher.TeacherLevel.ASSISTANT_PROFESSOR);
                } else if (e.getNewValue().equals("ASSOCIATE_PROFESSOR")) {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setTeacherLevel(Teacher.TeacherLevel.ASSOCIATE_PROFESSOR);
                } else if (e.getNewValue().equals("PROFESSOR")) {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setTeacherLevel(Teacher.TeacherLevel.PROFESSOR);
                }
            }
            else {
                Popup.showAccessError();
            }
        });

        numberOfRoomColumn.setEditable(true);
        numberOfRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberOfRoomColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setRoomNumber(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });

        phoneNumberColumn.setEditable(true);
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(e -> {
            if (hasAccess(e)) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
            }
            else {
                Popup.showAccessError();
            }
        });

    }

    private void addDeleteButton() {

        Callback<TableColumn<Teacher, String>, TableCell<Teacher, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Teacher, String> call(TableColumn<Teacher, String> param) {
                final TableCell<Teacher, String> cell = new TableCell<>() {

                    private final Button button = new Button("Hazf");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Teacher teacher = getTableView().getItems().get(getIndex());

                            if (hasAccess(teacher)) {
                                teacher.getDepartment().removeTeacher(teacher);
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

        deleteTeacherColumn.setCellFactory(cellFactory);

    }

    private void addEducationalAssistantButton() {

        Callback<TableColumn<Teacher, String>, TableCell<Teacher, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Teacher, String> call(TableColumn<Teacher, String> param) {
                final TableCell<Teacher, String> cell = new TableCell<>() {

                    private final Button button = new Button("Entekhab");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Teacher teacher = getTableView().getItems().get(getIndex());

                            if (hasAccess(teacher)) {
                                Controller.getCurrentUser().getDepartment().setEducationalAssistant(teacher);
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

        selectAssistantColumn.setCellFactory(cellFactory);

    }

    private boolean hasAccess(TableColumn.CellEditEvent<Teacher, String> e) {
        return e.getTableView().getItems().get(e.getTablePosition().getRow()).getNameOfDepartment().equals(Controller.getCurrentUser().getNameOfDepartment());
    }

    private boolean hasAccess(Teacher teacher) {
        if (teacher.getUsername().equals(Controller.getCurrentUser().getUsername())) return false;
        return teacher.getNameOfDepartment().equals(Controller.getCurrentUser().getNameOfDepartment());
    }

    @FXML
    private void reloadTable() {

        searchByDepartmentTextField.clear();
        searchByTeacherLevelTextField.clear();
        searchByNameTextField.clear();

        table.getItems().clear();

        for (Teacher teacher : University.getTeachers()) {
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

        for (Teacher teacher : University.getTeachers()) {
            if (searchedName.equals(teacher.getName())) table.getItems().add(teacher);
        }

    }

    public void searchByTeacherLevel(ActionEvent actionEvent) {

        String searchedLevel = searchByTeacherLevelTextField.getText();

        table.getItems().clear();

        for (Teacher teacher : University.getTeachers()) {
            if (searchedLevel.equals(teacher.getTeacherLevelString())) table.getItems().add(teacher);
        }

    }

    public void searchByDepartment(ActionEvent actionEvent) {

        String searchedDepartment = searchByDepartmentTextField.getText();

        table.getItems().clear();

        for (Teacher teacher : University.getTeachers()) {
            if (searchedDepartment.equals(teacher.getDepartment().getName())) table.getItems().add(teacher);
        }

    }
}
