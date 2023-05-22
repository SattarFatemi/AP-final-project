package GuiController.Student;

import Logic.Controller;
import Logic.Models.Student;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/*import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.chrono.ISOChronology;*/

public class MainPage implements Initializable {


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
    private TableView<Object> table;
    @FXML
    private TableColumn<Object, Object> titlesColumn;
    @FXML
    private TableColumn<Object, Object> explanationsColumn;
    @FXML
    private ToggleButton themeToggleButton;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
    }

    public void reload() {
        initTable();
        initUserinfo();
        setLastVisitLabel();
        setCurrentTimeLabel();
    }

    private void initTable() {

        table.getItems().removeAll();

        titlesColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        explanationsColumn.setCellValueFactory(new PropertyValueFactory<>("explanation"));

        table.getColumns().addAll(titlesColumn, explanationsColumn);

        table.getItems().add(new RowOfTable("Vaziate Tahsili", "Mojaz Beh Sabtenam"));
        table.getItems().add(new RowOfTable("Ostad Rahnama", ((Student) Controller.getTargetUser()).getSupervisor().getName()));
        table.getItems().add(new RowOfTable("Mojavveze Sabtenam", "Sader Shode"));
        table.getItems().add(new RowOfTable("Saate Sabtenam", "Taien Shode"));

    }

    private void initUserinfo() {

        Image image = new Image(Controller.getCurrentUser().getImagePath());
        userImageView.setImage(image);

        usernameLabel.setText(Controller.getCurrentUser().getUsername());

        userEmailLabel.setText(Controller.getCurrentUser().getEmail());

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
}
