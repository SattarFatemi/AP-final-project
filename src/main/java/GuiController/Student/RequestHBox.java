package GuiController.Student;

import Logic.Controller;
import Logic.Models.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class RequestHBox extends HBox {

    private Request request;

    public RequestHBox(Request request) {
        this.request = request;
        reload();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void reload() {

        setPrefHeight(82.0);
        setMinHeight(82.0);
        setPrefWidth(912.0);
        setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

        switch (request.getRequestStatus()) {
            case WAITING -> setStyle("-fx-border-color: orange");
            case FAILED -> setStyle("-fx-border-color: red");
            case ACCEPTED -> setStyle("-fx-border-color: green");
        }

        VBox titleContainer = new VBox(), textContainer = new VBox();

        Label requestTitle = new Label();
        requestTitle.setText(request.getReceiver().getName());

        Label timeLabel = new Label();
        timeLabel.setText(Controller.dateToString(request.getSendDate()));

        Label requestText = new Label();
        if (request.getRequestStatus() == Request.RequestStatus.WAITING) {
            requestText.setText("Dar entezare pasokh...");
        }
        else if (request.getRequestStatus() == Request.RequestStatus.FAILED) {
            requestText.setText("Rad shodeh.");
        }
        else if (request.getRequestStatus() == Request.RequestStatus.ACCEPTED) {
            switch (request.getRequestType()) {
                case RECOMMENDATION -> {

                    String text = "Injaneb "
                            + request.getReceiver().getName()
                            + " govahi midaham ke "
                            + request.getApplicant().getName()
                            + " be shomareh daneshjooee "
                            + request.getApplicant().getStudentNumber()
                            + ", darshaye "
                            + request.getNamesOfCommonLessons()
                            + " ba nomarate "
                            + request.getScoresOfCommonLesson()
                            + " be payan resandeh ast.";

                    requestText.setText(text);

                }
                case CERTIFICATE_STUDENT -> {

                    LocalDateTime currentDateTime = Controller.stringToLocalDateTime(Controller.dateToString(request.getSendDate())).plusDays(10);
                    String formattedDateTime = Controller.localDateTimeToString(currentDateTime);

                    String text = "Govahi mishavad ke "
                            + request.getApplicant().getName()
                            + " be shomareh daneshjooee "
                            + request.getApplicant().getStudentNumber()
                            + ", mashghool be tahsil dar daneshkadeh "
                            + request.getApplicant().getDepartment().getName()
                            + " dar daneshgahe Sanati Sharif ast. Tarikh etebare govahi ta "
                            + formattedDateTime;

                    requestText.setText(text);
                }
                case MINOR, WITHDRAWAL, DORMITORY -> requestText.setText("Pazirofteh shodeh.");
                case DISSERTATION -> {

                    String text = "Dar Tarikheh 1401/10/"
                            + (new Random().nextInt(30)+1)
                            + " mitavanid az payannameheye khod defa konid.";

                    requestText.setText(text);

                }
            }
        }

        requestTitle.setFont(Font.font("Montserrat Medium", 14.0));
        requestText.setFont(Font.font("Montserrat Light", 14.0));

        requestText.setWrapText(true);

        titleContainer.setAlignment(Pos.CENTER_LEFT);
        titleContainer.setSpacing(10.0);
        titleContainer.setPrefWidth(167.0);
        titleContainer.setPrefHeight(60.0);

        textContainer.setAlignment(Pos.CENTER_LEFT);
        textContainer.setPrefWidth(714.0);
        textContainer.setPrefHeight(60);

        titleContainer.getChildren().add(requestTitle);
        titleContainer.getChildren().add(timeLabel);

        textContainer.getChildren().add(requestText);

        getChildren().add(titleContainer);
        getChildren().add(textContainer);
    }
}
