package GuiController.Student;

import Logic.Controller;
import Logic.Models.*;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class RequestPage implements Initializable {

    private Request.RequestType requestPageType;

    @FXML
    private VBox listOfRequestsVBox;
    @FXML
    private Button backButton;
    @FXML
    private HBox certificateStudentHBox;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private ComboBox<String> destinationDepartmentComboBox;
    @FXML
    private Button educationButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private Button mainPageButton;
    @FXML
    private HBox minorHBox;
    @FXML
    private ComboBox<String> nameOfLessonComboBox;
    @FXML
    private ComboBox<String> nameOfTeacherComboBox;
    @FXML
    private Button profileButton;
    @FXML
    private HBox recommendationHBox;
    @FXML
    private Button registryButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button sendCertificateStudentRequestButton;
    @FXML
    private Button sendMinorRequestButton;
    @FXML
    private Button sendRecommendationRequestButton;
    @FXML
    private Button sendWithdrawalRequestButton;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private HBox withdrawalHBox;
    @FXML
    private HBox dormitoryHBox;
    @FXML
    private HBox dissertationHBox;
    @FXML
    private Button sendDormitoryRequestButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRequestArea();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void initRequestArea() {

        requestPageType = Controller.getLastRequestType();

        recommendationHBox.setVisible(false);
        recommendationHBox.setManaged(false);
        certificateStudentHBox.setVisible(false);
        certificateStudentHBox.setManaged(false);
        minorHBox.setVisible(false);
        minorHBox.setManaged(false);
        withdrawalHBox.setVisible(false);
        withdrawalHBox.setManaged(false);
        dormitoryHBox.setVisible(false);
        dormitoryHBox.setManaged(false);
        dissertationHBox.setVisible(false);
        dissertationHBox.setManaged(false);


        switch (requestPageType) {
            case RECOMMENDATION -> {
                recommendationHBox.setVisible(true);
                recommendationHBox.setManaged(true);
                initRecommendationRequestHBox();
            }
            case CERTIFICATE_STUDENT -> {
                certificateStudentHBox.setVisible(true);
                certificateStudentHBox.setManaged(true);
                initCertificateStudentHBox();
            }
            case MINOR -> {
                minorHBox.setVisible(true);
                minorHBox.setManaged(true);
                initMinorHBox();
            }
            case WITHDRAWAL -> {
                withdrawalHBox.setVisible(true);
                withdrawalHBox.setManaged(true);
                initWithdrawalHBox();
            }
            case DORMITORY -> {
                dormitoryHBox.setVisible(true);
                dormitoryHBox.setManaged(true);
                initDormitoryHBox();
            }
            case DISSERTATION -> {
                dissertationHBox.setVisible(true);
                dissertationHBox.setManaged(true);
                initDissertationHBox();
            }
        }
    }

    private void initRecommendationRequestHBox() {

        ObservableList<String> teachersNames = FXCollections.observableArrayList();
        for (Teacher teacher : University.getTeachers()) {
            teachersNames.add(teacher.getName());
        }
        nameOfTeacherComboBox.getItems().addAll(teachersNames);

        reloadRequests();
    }

    private void initCertificateStudentHBox() {
        reloadRequests();
    }

    private void initMinorHBox() {

        ObservableList<String> departmentsNames = FXCollections.observableArrayList();
        for (Department department : University.getDepartments()) {
            departmentsNames.add(department.getName());
        }
        destinationDepartmentComboBox.getItems().addAll(departmentsNames);

        reloadRequests();
    }

    private void initWithdrawalHBox() {
        reloadRequests();
    }

    private void initDormitoryHBox() {
        reloadRequests();
    }

    private void initDissertationHBox() {
        reloadRequests();
    }

    private void reloadRequests() {

        listOfRequestsVBox.getChildren().removeIf(node -> node instanceof RequestHBox);

        for (int i = University.getRequests().size() - 1; i >= 0; i--) {
            Request request = University.getRequests().get(i);
            if (request.getRequestType() == requestPageType &&
                    request.getApplicant().getUsername().equals(Controller.getCurrentUser().getUsername())) {

                RequestHBox requestHBox = new RequestHBox(request);
                requestHBox.setRequest(request);

                addRequestHBox(requestHBox);

            }
        }

    }

    private void addRequestHBox(RequestHBox requestHBox) {
        listOfRequestsVBox.getChildren().add(requestHBox);
    }

    public void sendRecommendationRequest(ActionEvent actionEvent) {

        University.addRequest(new Request(Request.RequestType.RECOMMENDATION,
                Request.RequestStatus.WAITING,
                ((Student) Controller.getCurrentUser()).getStudentNumber(),
                University.findTeacherByName(nameOfTeacherComboBox.getValue()).getNumberOfTeacher(),
                null, null,
                Controller.dateToString(new Date())));

        reloadRequests();

    }

    public void sendCertificateStudentRequest(ActionEvent actionEvent) {

        University.addRequest(new Request(Request.RequestType.CERTIFICATE_STUDENT,
                Request.RequestStatus.ACCEPTED,
                ((Student) Controller.getCurrentUser()).getStudentNumber(),
                Controller.getCurrentUser().getDepartment().getEducationalAssistant().getNumberOfTeacher(),
                null, null,
                Controller.dateToString(new Date())));

        reloadRequests();
    }

    public void sendMinorRequest(ActionEvent actionEvent) {

        University.addRequest(new Request(Request.RequestType.MINOR,
                Request.RequestStatus.WAITING,
                ((Student) Controller.getCurrentUser()).getStudentNumber(),
                null,
                Controller.getCurrentUser().getNameOfDepartment(),
                destinationDepartmentComboBox.getValue(),
                Controller.dateToString(new Date())));

        reloadRequests();

    }

    public void sendWithdrawalRequest(ActionEvent actionEvent) {

        University.addRequest(new Request(Request.RequestType.WITHDRAWAL,
                Request.RequestStatus.WAITING,
                ((Student) Controller.getCurrentUser()).getStudentNumber(),
                Controller.getCurrentUser().getDepartment().getEducationalAssistant().getNumberOfTeacher(),
                null, null,
                Controller.dateToString(new Date())));

        reloadRequests();

    }

    public void sendDormitoryRequest(ActionEvent actionEvent) {

        Request.RequestStatus status = Request.RequestStatus.FAILED;

        Random rand = new Random();
        boolean accepted = rand.nextBoolean();

        if (accepted) status = Request.RequestStatus.ACCEPTED;

        University.addRequest(new Request(Request.RequestType.DORMITORY,
                status,
                ((Student) Controller.getCurrentUser()).getStudentNumber(),
                Controller.getCurrentUser().getDepartment().getEducationalAssistant().getNumberOfTeacher(),
                null, null,
                Controller.dateToString(new Date())));

        reloadRequests();

    }

    public void sendDissertationRequest(ActionEvent actionEvent) {

        University.addRequest(new Request(Request.RequestType.DISSERTATION,
                Request.RequestStatus.ACCEPTED,
                ((Student) Controller.getCurrentUser()).getStudentNumber(),
                Controller.getCurrentUser().getDepartment().getEducationalAssistant().getNumberOfTeacher(),
                null, null,
                Controller.dateToString(new Date())));

        reloadRequests();

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
