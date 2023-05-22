package GuiController.Student;

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
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TemporaryScoresPage implements Initializable {

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
    private TableColumn<Row, String> nameOfLessonColumn;
    @FXML
    private ComboBox<String> nameOfLessonComboBox;
    @FXML
    private TableColumn<Row, String> nameOfTeacherColumn;
    @FXML
    private TableColumn<Row, String> numberOfLessonColumn;
    @FXML
    private Button profileButton;
    @FXML
    private VBox protestBox;
    @FXML
    private TextArea protestTextArea;
    @FXML
    private Button registryButton;
    @FXML
    private Button reportButton;
    @FXML
    private TableColumn<Row, Double> scoreColumn;
    @FXML
    private Button submitProtestButton;
    @FXML
    private TableView<Row> table;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private VBox mainContainerVBox;
    @FXML
    private Label averageScoreLabel;
    @FXML
    private Label numberOfPassedUnitsLabel;
    @FXML
    private TableColumn<Row, String> lessonStatusColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCurrentTimeLabel();
        setLastVisitLabel();
        initTable();
        initProtestBox();
        initForEducationalAssistant();
        reloadProtests();
    }

    private void initForEducationalAssistant() {
        if (Controller.getTargetUser() != Controller.getCurrentUser()) {
            protestBox.setDisable(true);
        }
        else {
            protestBox.setDisable(false);
        }
    }

    private void initTable() {

        nameOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfLesson"));
        numberOfLessonColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfLesson"));
        nameOfTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfTeacherOfLesson"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        lessonStatusColumn.setCellValueFactory(new PropertyValueFactory<>("statusString"));

        table.getColumns().addAll(nameOfLessonColumn, numberOfLessonColumn, nameOfTeacherColumn,
                scoreColumn, lessonStatusColumn);

        reloadTable();
    }

    private void reloadTable() {

        Double sumOfScores = 0.0;
        int numberOfPassedUnits = 0, n = 0;

        table.getItems().clear();
        for (Lesson lesson : ((Student) Controller.getTargetUser()).getLessons()) {
            table.getItems().add(new Row(lesson, ((Student) Controller.getTargetUser())));

            if (lesson.getStatus() != Lesson.ScoresStatus.NOT_APPROVED) {
                Double studentScoreInThisLesson = lesson.findStudentAndScore((Student) Controller.getTargetUser()).getScore();
                n++;
                sumOfScores += studentScoreInThisLesson;
                if (studentScoreInThisLesson >= 10) numberOfPassedUnits += Integer.parseInt(lesson.getNumberOfUnits());
            }
        }

        averageScoreLabel.setText(((Student) Controller.getTargetUser()).getAverageScore().toString());
        numberOfPassedUnitsLabel.setText(Integer.toString(numberOfPassedUnits));

    }

    private void reloadProtests() {

        mainContainerVBox.getChildren().removeIf(node -> node instanceof ProtestHBox);

        for (int i = University.getProtests().size() - 1; i >= 0; i--) {
            Protest protest = University.getProtests().get(i);
            if (protest.getProtester().getUsername().equals(Controller.getTargetUser().getUsername())) {

                ProtestHBox protestHBox = new ProtestHBox(protest);

                addProtestHBox(protestHBox);

            }
        }

    }

    private void addProtestHBox(ProtestHBox protestHBox) {
        mainContainerVBox.getChildren().add(protestHBox);
    }

    private void initProtestBox() {

        ObservableList<String> lessonsOptions = FXCollections.observableArrayList();
        for (Lesson lesson : ((Student) Controller.getTargetUser()).getLessons()) {
            if (lesson.getStatus() == Lesson.ScoresStatus.TEMPORARY_APPROVED) {
                lessonsOptions.add(lesson.toString());
            }
        }
        nameOfLessonComboBox.getItems().addAll(lessonsOptions);

    }

    public void submitProtest(ActionEvent actionEvent) {

        Protest protest = new Protest(Protest.ProtestStatus.WAITING,
                ((Student) Controller.getTargetUser()).getStudentNumber(),
                nameOfLessonComboBox.getValue());

        protest.setProtestText(protestTextArea.getText());
        protest.setProtestAnswerText("Dar Entezare Pasokh...");
        protest.setScore(protest.getLesson().findStudentAndScore((Student) Controller.getTargetUser()).getScore());

        ProtestHBox protestHBox = new ProtestHBox(protest);

        mainContainerVBox.getChildren().add(protestHBox);

        University.addProtest(protest);

        reloadProtests();

    }

    public class Row {

        private Lesson lesson;
        private Student student;

        public Row(Lesson lesson, Student student) {
            this.lesson = lesson;
            this.student = student;
        }

        public Double getScore() {
            for (StudentAndScore studentAndScore : lesson.getStudentsAndScores()) {
                if (studentAndScore.getStudent().getUsername().equals(student.getUsername())) {
                    return studentAndScore.getScore();
                }
            }
            return -1.0;
        }

        public String getNameOfLesson() {
            return lesson.getNameOfLesson();
        }

        public String getNumberOfLesson() {
            return lesson.getNumberOfLesson();
        }

        public String getNameOfTeacherOfLesson() {
            return lesson.getNameOfTeacher();
        }

        public Lesson.ScoresStatus getStatus() {
            return lesson.getStatus();
        }

        public String getStatusString() {
            Lesson.ScoresStatus status = getStatus();

            switch (status) {
                case NOT_APPROVED -> {
                    return "Sabt Nashodeh";
                }
                case TEMPORARY_APPROVED -> {
                    return "Movaghat";
                }
                case APPROVED -> {
                    return "Nahayi";
                }
                default -> {
                    return "NaMaloom";
                }
            }
        }

        public Lesson getLesson() {
            return lesson;
        }

        public void setLesson(Lesson lesson) {
            this.lesson = lesson;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
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


}
