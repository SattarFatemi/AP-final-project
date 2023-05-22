package GuiController.Teacher;

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
    private ToggleButton themeToggleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        requestPageType = Controller.getLastRequestType();

        reloadRequests();
        setCurrentTimeLabel();
        setLastVisitLabel();
    }

    private void reloadRequests() {

        listOfRequestsVBox.getChildren().removeIf(node -> node instanceof GuiController.Teacher.RequestHBox);

        for (int i = University.getRequests().size() - 1; i >= 0; i--) {
            Request request = University.getRequests().get(i);
            if (request.getRequestType() == requestPageType &&
                    request.getReceiver().getUsername().equals(Controller.getCurrentUser().getUsername())) {

                GuiController.Teacher.RequestHBox requestHBox = new GuiController.Teacher.RequestHBox(request);
                requestHBox.setRequest(request);

                addRequestHBox(requestHBox);

            }
        }

    }

    private void addRequestHBox(RequestHBox requestHBox) {
        listOfRequestsVBox.getChildren().add(requestHBox);
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
