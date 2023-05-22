package Logic;

import Logic.Models.*;
import Logic.Save.Save;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Stack;

public class Controller {

    private static Logger log = LogManager.getLogger(Controller.class);

    private static User currentUser;
    private static User targetUser;

    private static Stack<Scene> stackOfScenes = new Stack<>();
    public static Stage activeStage;

    private static Request.RequestType lastRequestType;

    public static Request.RequestType getLastRequestType() {
        return lastRequestType;
    }

    public static void setLastRequestType(Request.RequestType lastRequestType) {
        Controller.lastRequestType = lastRequestType;
    }

    public static User getTargetUser() {
        if (targetUser == null) return getCurrentUser();
        return targetUser;
    }

    public static void setTargetUser(User targetUser) {
        Controller.targetUser = targetUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Controller.currentUser = currentUser;
    }

    public static boolean checkCorrectPassword(User user, String password) {
        return user.getPasswordHashed().equals(User.hashPassword(password));
    }

    public static void login(User user) {

        setCurrentUser(user);
        setTargetUser(user);

        if (tooLongOffline(user, 3)) {
            changeScene("setNewPasswordPage.fxml");
            return;
        }

        log.info(user.getUsername() + " Login");

        if (user instanceof Teacher) changeScene("Teacher/mainPage.fxml");
        else changeScene("Student/mainPage.fxml");

    }

    public static void logout() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        currentUser.setLastVisit(sdf.format(new Date()));

        log.info(getCurrentUser().getUsername() + " Logout");

        setCurrentUser(null);

        Save.saveChanges();

        changeScene("login.fxml");

        stackOfScenes.clear();

    }

    private static boolean tooLongOffline(User user, long hours) {
        long enoughSeconds = hours * 3600;
        return (new Date().getTime()/1000) - (user.getLastVisitDate().getTime()/1000) > enoughSeconds;
    }

    public static void goToPreviousScene() {
        activeStage.setScene(stackOfScenes.pop());
    }

    public static void changeScene(String fileName) {

        stackOfScenes.push(activeStage.getScene());

        FXMLLoader loader = new FXMLLoader(Controller.class.getClassLoader().getResource(fileName));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(fileName + " be onvane Scene yaft nashod");
        }
        Scene scene = new Scene(root);
        activeStage.setScene(scene);

    }

    public static void changeScene(Scene scene) {

        stackOfScenes.push(activeStage.getScene());

        activeStage.setScene(scene);

    }

    public static Date stringToDate(String dateString) {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("dateString is not formatted");
        }
        return date;

    }

    public static String dateToString(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);

    }

    public static LocalDateTime stringToLocalDateTime(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime;

    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);

    }

    public static Double roundNumberByQuarter(Double number) {
        int n = 0;
        while (number >= 0.25) {
            number -= 0.25;
            n++;
        }
        return n*(0.25);
    }

    public static Double round(Double value, Integer places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
