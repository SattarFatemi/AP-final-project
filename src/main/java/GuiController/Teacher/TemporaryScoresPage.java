package GuiController.Teacher;

import Logic.Controller;
import Logic.Models.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TemporaryScoresPage implements Initializable {

    private Lesson targetLesson;

    @FXML
    private TableColumn<Lesson, String> averagePassedScoreColumn;
    @FXML
    private TableColumn<Lesson, String> averageScoreColumn;
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
    private TableView<Lesson> lessonsTable;
    @FXML
    private Button mainPageButton;
    @FXML
    private TableColumn<Lesson, String> nameOfLessonsColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfFailedColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfLessonsColumn;
    @FXML
    private TableColumn<Lesson, String> numberOfPassedColumn;
    @FXML
    private Label pageTitleLabel;
    @FXML
    private Button profileButton;
    @FXML
    private TableColumn<Lesson, String> protestColumn;
    @FXML
    private VBox mainContainerVBox;
    @FXML
    private Button registryButton;
    @FXML
    private Button reportButton;
    @FXML
    private TableColumn<Lesson, String> setScoreColumn;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private TableView<RowAsStudent> scoresTableView;
    @FXML
    private TableColumn<RowAsStudent, String> nameOfStudentsColumn;
    @FXML
    private TableColumn<RowAsStudent, String> numberOfStudentsColumn;
    @FXML
    private TableColumn<RowAsStudent, String> scoreOfStudentsColumn;
    @FXML
    private Button saveTemporaryScoresButton;
    @FXML
    private Button saveFinalScoresButton;
    @FXML
    private HBox saveButtonsContainerHBox;
    @FXML
    private TableColumn<Lesson, String> lessonStatusColumn;

    public class RowAsStudent {

        Student student;
        Lesson lesson;
        Double score;

        public RowAsStudent(Student student, Lesson lesson) {
            this.student = student;
            this.lesson = lesson;
            this.score = lesson.findStudentAndScore(student).getScore();
        }

        public void saveTemporaryScore() {
            for (StudentAndScore studentAndScore : lesson.getStudentsAndScores()) {
                if (studentAndScore.getStudent().getStudentNumber().equals(getStudent().getStudentNumber())) {
                    studentAndScore.setScore(getScore());
                }
            }
        }

        public Double getScore() {
            return score;
        }

        public String getScoreString() {
            return getScore().toString();
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public String getNameOfStudent() {
            return getStudent().getName();
        }

        public String getNumberOfStudent() {
            return getStudent().getStudentNumber();
        }

        public void setScoreOfStudent(Double newScore) {
            for (StudentAndScore studentAndScore : lesson.getStudentsAndScores()) {
                if (studentAndScore.getStudent().getStudentNumber().equals(getStudent().getStudentNumber())) {
                    studentAndScore.setScore(newScore);
                }
            }
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public Lesson getLesson() {
            return lesson;
        }

        public void setLesson(Lesson lesson) {
            this.lesson = lesson;
        }

    }

    public enum TypeOfMainContainer {
        PROTEST_CONTAINER,
        SCORE_FIELDS_CONTAINER
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initScoresTable();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    public void initForEducationalAssistant() {
        if (Controller.getCurrentUser() != Controller.getTargetUser()) {
            saveButtonsContainerHBox.setVisible(false);
            scoreOfStudentsColumn.setEditable(false);
        } else {
            saveButtonsContainerHBox.setVisible(true);
            scoreOfStudentsColumn.setEditable(true);
        }
    }

    private void initScoresTable() {

        scoresTableView.setEditable(true);

        nameOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfStudent"));
        numberOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfStudent"));
        scoreOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("scoreString"));

        scoresTableView.getColumns().addAll(nameOfStudentsColumn, numberOfStudentsColumn,
                scoreOfStudentsColumn);

        initEditableColumnsOfScoresTable();
        hideScoresTable();
    }

    private void initEditableColumnsOfScoresTable() {

        scoreOfStudentsColumn.setEditable(true);
        scoreOfStudentsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        scoreOfStudentsColumn.setOnEditCommit(e -> {

            RowAsStudent row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            Double newScore = Double.parseDouble(e.getNewValue());

            if (newScore >= 0 && newScore <= 20) {
                row.setScore(Controller.roundNumberByQuarter(newScore));
            } else {
                row.setScoreOfStudent(Double.parseDouble(e.getOldValue()));

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(":/");
                alert.setHeaderText(":////");
                alert.setContentText("Lotfan yek adad az 0 ta 20 vared konid.");
                alert.show();
            }

        });
    }

    private void hideScoresTable() {
        scoresTableView.setVisible(false);
        scoresTableView.setManaged(false);
        saveButtonsContainerHBox.setVisible(false);
        saveButtonsContainerHBox.setManaged(false);
    }

    public void saveTemporaryScores(ActionEvent actionEvent) {

        boolean unsetScoreField = false;
        for (RowAsStudent row : scoresTableView.getItems()) {
            if (row.getScore() == -1.0) {
                unsetScoreField = true;
                break;
            }
        }

        if (unsetScoreField) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Kamel Nist!");
            alert.setContentText("Lotfan nomreh valid baraye hameh vared konid.");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Halleh!");
        alert.setContentText("Nomarat Sabte Movaghat shod.");
        alert.show();

        for (RowAsStudent row : scoresTableView.getItems()) row.saveTemporaryScore();

        getTargetLesson().setStatus(Lesson.ScoresStatus.TEMPORARY_APPROVED);

        reloadScoresTable();
    }

    public void saveFinalScores(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Nomarat sabte nahayi shod");
        alert.setHeaderText("sabt shod");
        alert.show();

        getTargetLesson().setStatus(Lesson.ScoresStatus.APPROVED);
        reloadScoresTable();

    }

    private void reloadScoresTable() {

        Lesson lesson = getTargetLesson();

        mainContainerVBox.getChildren().clear();

        scoresTableView.setVisible(true);
        scoresTableView.setManaged(true);
        saveButtonsContainerHBox.setVisible(true);
        saveButtonsContainerHBox.setManaged(true);

        mainContainerVBox.getChildren().add(scoresTableView);
        mainContainerVBox.getChildren().add(saveButtonsContainerHBox);

        scoresTableView.getItems().clear();

        for (StudentAndScore studentAndScore : lesson.getStudentsAndScores()) {

            RowAsStudent row = new RowAsStudent(studentAndScore.getStudent(), lesson);

            scoresTableView.getItems().add(row);
        }

        if (lesson.getStatus() == Lesson.ScoresStatus.TEMPORARY_APPROVED) {
            saveFinalScoresButton.setDisable(false);
        } else {
            saveFinalScoresButton.setDisable(true);
        }

        if (lesson.getStatus() == Lesson.ScoresStatus.APPROVED) {
            saveTemporaryScoresButton.setDisable(true);
        }

        initForEducationalAssistant();

    }

    private void reloadMainContainer(TypeOfMainContainer typeOfMainContainer) {

        mainContainerVBox.getChildren().clear();

        Lesson lesson = getTargetLesson();

        switch (typeOfMainContainer) {
            case PROTEST_CONTAINER -> {

                pageTitleLabel.setText("Baresie Eterazate Darse " + lesson.getNameOfLesson());

                for (int i = University.getProtests().size() - 1; i >= 0; i--) {
                    Protest protest = University.getProtests().get(i);
                    if (protest.getLesson() == lesson &&
                            protest.getReceiverTeacher().getUsername().equals(Controller.getTargetUser().getUsername())) {

                        ProtestHBox protestHBox = new ProtestHBox(protest);

                        addProtestHBox(protestHBox);

                    }
                }

            }
            case SCORE_FIELDS_CONTAINER -> {

                pageTitleLabel.setText("Nomrehdehie Darse " + lesson.getNameOfLesson());
                reloadScoresTable();

            }
        }

    }

    private void addProtestHBox(ProtestHBox protestHBox) {
        mainContainerVBox.getChildren().add(protestHBox);
    }

    private void initTable() {

        nameOfLessonsColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        numberOfPassedColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPassed"));
        numberOfFailedColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfFailed"));
        averageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        averagePassedScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averagePassedScore"));
        addProtestButtons();
        addSetScoreButtons();
        lessonStatusColumn.setCellValueFactory(new PropertyValueFactory<>("statusString"));

        lessonsTable.getColumns().addAll(nameOfLessonsColumn, numberOfLessonsColumn, numberOfPassedColumn,
                numberOfFailedColumn, averageScoreColumn, averagePassedScoreColumn, protestColumn,
                setScoreColumn);

        reloadTable();
    }

    private void reloadTable() {

        lessonsTable.getItems().clear();
        for (Lesson lesson : ((Teacher) Controller.getTargetUser()).getLessons()) {
            lessonsTable.getItems().add(lesson);
        }

    }

    private void addProtestButtons() {

        Callback<TableColumn<Lesson, String>, TableCell<Lesson, String>> cellFactory;
        cellFactory = new Callback<>() {
            @Override
            public TableCell<Lesson, String> call(TableColumn<Lesson, String> param) {
                final TableCell<Lesson, String> cell = new TableCell<>() {

                    private final Button button = new Button("Barresi");

                    {
                        button.setOnAction((ActionEvent event) -> {

                            Lesson lesson = getTableView().getItems().get(getIndex());
                            setTargetLesson(lesson);
                            reloadMainContainer(TypeOfMainContainer.PROTEST_CONTAINER);

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
                            setTargetLesson(lesson);
                            reloadMainContainer(TypeOfMainContainer.SCORE_FIELDS_CONTAINER);

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

    public Lesson getTargetLesson() {
        return targetLesson;
    }

    public void setTargetLesson(Lesson targetLesson) {
        this.targetLesson = targetLesson;
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
