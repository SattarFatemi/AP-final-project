import GuiController.Login;
import Logic.Controller;
import Logic.Load.LoadDatabase;
import Logic.Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.sound.midi.ControllerEventListener;
import java.awt.*;

public class Main extends Application {

    private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Controller.activeStage = primaryStage;
        primaryStage.setResizable(false);

        LoadDatabase.loadDatabase();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        log.info("App is started");
    }

    @Override
    public void stop() throws Exception {

        if (Controller.getCurrentUser() != null) Controller.logout();

        super.stop();
    }
}
