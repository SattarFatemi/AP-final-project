package GuiController.Teacher;

import Logic.Controller;
import Logic.Models.Request;
import Logic.Models.University;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

public class RequestHBox extends HBox {

    private Request request;

    private VBox titleContainer = new VBox();
    private VBox textContainer = new VBox();
    private VBox buttonContainer = new VBox();

    private Label requestTitle = new Label();
    private Label requestText = new Label();
    private Label timeLabel = new Label();

    private Button acceptButton = new Button();
    private Button declineButton = new Button();

    public RequestHBox(Request request) {
        this.request = request;
        init();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void init() {

        setPrefHeight(82.0);
        setMinHeight(82.0);
        setPrefWidth(912.0);
        setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

        requestTitle.setText(request.getApplicant().getName());
        timeLabel.setText(Controller.dateToString(request.getSendDate()));

        switch (request.getRequestType()) {
            case RECOMMENDATION -> {

                String text = "Matn be in soorat khahad bood: 'Injaneb "
                        + request.getReceiver().getName()
                        + " govahi midaham ke "
                        + request.getApplicant().getName()
                        + " be shomareh daneshjooee "
                        + request.getApplicant().getStudentNumber()
                        + ", darshaye "
                        + request.getNamesOfCommonLessons()
                        + " ba nomarate "
                        + request.getScoresOfCommonLesson()
                        + " be payan resandeh ast.' Aya tayeed mikonid?";

                requestText.setText(text);

            }
            case CERTIFICATE_STUDENT -> {

                LocalDateTime currentDateTime = Controller.stringToLocalDateTime(Controller.dateToString(request.getSendDate())).plusDays(10);
                String formattedDateTime = Controller.localDateTimeToString(currentDateTime);

                String text = "Matn be in soorat khahad bood: 'Govahi mishavad ke "
                        + request.getApplicant().getName()
                        + " be shomareh daneshjooee "
                        + request.getApplicant().getStudentNumber()
                        + ", mashghool be tahsil dar daneshkadeh "
                        + request.getApplicant().getDepartment().getName()
                        + " dar daneshgahe Sanati Sharif ast. Tarikh etebare govahi ta "
                        + formattedDateTime
                        + " ast.' Aya tayeed mikonid?";

                requestText.setText(text);
            }
            case MINOR, WITHDRAWAL, DORMITORY -> requestText.setText("Darkhast baraye " + request.getRequestType().toString().toLowerCase(Locale.ROOT) + ".");
            case DISSERTATION -> {

                String text = "Matn be in soorat khahad bood: 'Dar Tarikheh 1401/10/"
                        + (new Random().nextInt(30) + 1)
                        + " mitavanid az payannameheye khod defa konid.' Aya tayeed mikonid?";

                requestText.setText(text);

            }
        }

        requestTitle.setFont(Font.font("Montserrat Medium", 14.0));
        requestText.setFont(Font.font("Montserrat Light", 14.0));

        requestText.setWrapText(true);

        titleContainer.setAlignment(Pos.CENTER_LEFT);
        titleContainer.setSpacing(10.0);
        titleContainer.setPrefWidth(155.0);
        titleContainer.setPrefHeight(60.0);

        textContainer.setAlignment(Pos.CENTER_LEFT);
        textContainer.setPrefWidth(610.0);
        textContainer.setPrefHeight(60);

        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setPrefWidth(103.0);
        buttonContainer.setPrefHeight(60.0);
        buttonContainer.setSpacing(5);


        acceptButton.setText("Paziroftan");
        acceptButton.setFont(Font.font("Montserrat Bold", 13.0));
        acceptButton.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white");
        acceptButton.setPrefWidth(103.0);
        acceptButton.setPrefHeight(25.0);
        acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> accept());

        declineButton.setText("Rad Kardan");
        declineButton.setFont(Font.font("Montserrat Bold", 13.0));
        declineButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white");
        declineButton.setPrefWidth(103.0);
        declineButton.setPrefHeight(25.0);
        declineButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> decline());

        titleContainer.getChildren().add(requestTitle);
        titleContainer.getChildren().add(timeLabel);

        textContainer.getChildren().add(requestText);

        buttonContainer.getChildren().add(acceptButton);
        buttonContainer.getChildren().add(declineButton);

        getChildren().add(titleContainer);
        getChildren().add(textContainer);
        getChildren().add(buttonContainer);

        switch (request.getRequestStatus()) {
            case WAITING -> setStyle("-fx-border-color: orange");
            case FAILED, ACCEPTED -> reload();
        }
    }

    private void reload() {

        switch (request.getRequestStatus()) {
            case WAITING -> setStyle("-fx-border-color: orange");
            case FAILED -> {
                setStyle("-fx-border-color: red");
                requestText.setText(requestText.getText() + " --- Rad shod ---");
            }
            case ACCEPTED -> {
                setStyle("-fx-border-color: green");
                requestText.setText(requestText.getText() + " --- Pazirofteh shod ---");
            }
        }

        acceptButton.setDisable(true);
        declineButton.setDisable(true);
    }

    public void accept() {
        request.setRequestStatus(Request.RequestStatus.ACCEPTED);
        if (request.getRequestType() == Request.RequestType.WITHDRAWAL) University.removeStudent(request.getApplicant());
        reload();
    }

    public void decline() {
        request.setRequestStatus(Request.RequestStatus.FAILED);
        reload();
    }
}
