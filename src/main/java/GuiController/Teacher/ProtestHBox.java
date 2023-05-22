package GuiController.Teacher;

import GuiController.Popup;
import Logic.Controller;
import Logic.Models.Lesson;
import Logic.Models.Protest;
import Logic.Models.University;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProtestHBox extends HBox {

    private Protest protest;

    VBox titleContainer = new VBox();
    Label protesterNameLabel = new Label();
    Label protestDate = new Label();
    Label protestLessonName = new Label();
    Label protestReceiverName = new Label();
    Label protesterScore = new Label();

    VBox contentContainer = new VBox();
    Label protestText = new Label();
    HBox answerAreaHBox = new HBox();
    TextArea textArea = new TextArea();
    VBox submitVBox = new VBox();
    TextField newScoreTextField = new TextField();
    Button submitButton = new Button();

    Label protestAnswer = new Label();


    public ProtestHBox(Protest protest) {
        this.protest = protest;
        init();
    }

    public Protest getProtest() {
        return protest;
    }

    public void setProtest(Protest protest) {
        this.protest = protest;
    }

    private void init() {

        setPrefHeight(207);
        setMinHeight(207);
        setPrefWidth(758.0);

        switch (protest.getProtestStatus()) {
            case WAITING -> setStyle("-fx-border-color: orange");
            case ANSWERED -> setStyle("-fx-border-color: black");
        }

        titleContainer.setPrefWidth(174.0);
        titleContainer.setPrefHeight(154.0);
        titleContainer.setPadding(new Insets(10, 10, 10, 10));
        titleContainer.setSpacing(10.0);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        protesterNameLabel.setText(University.findStudentByStudentNumber(protest.getProtesterStudentNumber()).getName());
        protesterNameLabel.setFont(Font.font("Montserrat Medium", 14));

        protestDate.setText(protest.getSendDateString());
        protestDate.setFont(Font.font("Montserrat Medium", 14));

        protestLessonName.setText(protest.getLesson().getNameOfLesson());
        protestLessonName.setFont(Font.font("Montserrat Medium", 14));

        protestReceiverName.setText(protest.getLesson().getNameOfTeacher());
        protestReceiverName.setFont(Font.font("Montserrat Medium", 14));

        protesterScore.setText(protest.getScore().toString());
        protesterScore.setFont(Font.font("Montserrat Medium", 14));

        titleContainer.getChildren().add(protesterNameLabel);
        titleContainer.getChildren().add(protestDate);
        titleContainer.getChildren().add(protestLessonName);
        titleContainer.getChildren().add(protestReceiverName);
        titleContainer.getChildren().add(protesterScore);

        //---------------------------------------------------------------------------

        contentContainer.setPrefWidth(594);
        contentContainer.setPrefHeight(206);
        contentContainer.setPadding(new Insets(0, 5, 0, 10));

        protestText.setText(protest.getProtestText());
        protestText.setWrapText(true);
        protestText.setFont(Font.font("Montserrat Light", 13));
        protestText.setPrefHeight(100);
        protestText.setPrefWidth(594);
        protestText.setPadding(new Insets(5));
        protestText.setAlignment(Pos.CENTER_LEFT);

        switch (protest.getProtestStatus()) {
            case WAITING -> {

                answerAreaHBox.setPrefWidth(594);
                answerAreaHBox.setPrefHeight(100);
                answerAreaHBox.setPadding(new Insets(0, 5, 0, 10));
                answerAreaHBox.setFillHeight(true);

                textArea.setPrefWidth(468);
                textArea.setPrefHeight(90);

                submitVBox.setPrefWidth(100);
                submitVBox.setPrefHeight(200);
                submitVBox.setFillWidth(true);
                submitVBox.setSpacing(10);
                submitVBox.setPadding(new Insets(5));

                newScoreTextField.setPrefWidth(100);
                newScoreTextField.setPrefHeight(32);
                newScoreTextField.setPromptText("Nomreh Jadid");
                newScoreTextField.setAlignment(Pos.CENTER);

                submitButton.setText("Ersal");
                submitButton.setPrefWidth(100);
                submitButton.setPrefHeight(46);

                submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> submit());

                submitVBox.getChildren().add(newScoreTextField);
                submitVBox.getChildren().add(submitButton);

                answerAreaHBox.getChildren().add(textArea);
                answerAreaHBox.getChildren().add(submitVBox);

                contentContainer.getChildren().add(protestText);
                contentContainer.getChildren().add(new Separator());
                contentContainer.getChildren().add(answerAreaHBox);

            }
            case ANSWERED -> {

                protestAnswer.setText(protest.getProtestAnswerText());
                protestAnswer.setWrapText(true);
                protestAnswer.setFont(Font.font("Montserrat Light", 13));
                protestAnswer.setPrefHeight(100);
                protestAnswer.setPrefWidth(594);
                protestAnswer.setPadding(new Insets(5));
                protestAnswer.setAlignment(Pos.CENTER_LEFT);

                contentContainer.getChildren().add(protestText);
                contentContainer.getChildren().add(new Separator());
                contentContainer.getChildren().add(protestAnswer);

            }
        }

        //---------------------------------------------------------------------------

        getChildren().add(titleContainer);
        getChildren().add(contentContainer);
    }

    private void reload() {

        switch (protest.getProtestStatus()) {
            case WAITING -> setStyle("-fx-border-color: orange");
            case ANSWERED -> setStyle("-fx-border-color: black");
        }

        contentContainer.getChildren().removeIf(node -> node instanceof HBox);

        protestAnswer.setText(protest.getProtestAnswerText());
        protestAnswer.setWrapText(true);
        protestAnswer.setFont(Font.font("Montserrat Light", 13));
        protestAnswer.setPrefHeight(100);
        protestAnswer.setPrefWidth(594);
        protestAnswer.setPadding(new Insets(5));
        protestAnswer.setAlignment(Pos.CENTER_LEFT);

        contentContainer.getChildren().add(protestAnswer);

    }

    private void submit() {

        if (Controller.getCurrentUser() != Controller.getTargetUser()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hey!");
            alert.setContentText("shoma be in mored dastresi nadarid. (Ba filtershekan ham nemiad)");
            alert.show();
            return;
        }

        if (protest.getLesson().getStatus() == Lesson.ScoresStatus.APPROVED) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dire");
            alert.setContentText("Nomarat ghablan nahayi shodand");
            alert.show();
            return;
        }

        double newScoreRounded = protest.getScore();

        if (newScoreTextField.getText().length() > 0) {
            newScoreRounded = Controller.roundNumberByQuarter(Double.parseDouble(newScoreTextField.getText()));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nomreh Tekrari");
            alert.setContentText("Nomreh ghabli sabt shod");
            alert.show();
        }

        if (newScoreRounded >= 0 && newScoreRounded <= 20) {

            protest.setProtestStatus(Protest.ProtestStatus.ANSWERED);
            protest.setProtestAnswerText(textArea.getText());
            protest.getLesson().findStudentAndScore(protest.getProtester()).setScore(newScoreRounded);

            textArea.clear();
            newScoreTextField.clear();

        }
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nomreh!");
            alert.setTitle("Nomreheye vared shode az 0 ta 20 nist!");

            return;
        }

        reload();

    }

}
