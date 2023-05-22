package GuiController.Student;

import Logic.Controller;
import Logic.Models.Protest;
import Logic.Models.University;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProtestHBox extends HBox {

    private Protest protest;

    public ProtestHBox(Protest protest) {
        this.protest = protest;
        reload();
    }

    public Protest getProtest() {
        return protest;
    }

    public void setProtest(Protest protest) {
        this.protest = protest;
    }

    private void reload() {

        setPrefHeight(207);
        setMinHeight(207);
        setPrefWidth(758.0);

        switch (protest.getProtestStatus()) {
            case WAITING -> setStyle("-fx-border-color: orange");
            case ANSWERED -> setStyle("-fx-border-color: black");
        }



        VBox titleContainer = new VBox();
        titleContainer.setPrefWidth(174.0);
        titleContainer.setPrefHeight(154.0);
        titleContainer.setPadding(new Insets(10, 10, 10, 10));
        titleContainer.setSpacing(10.0);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        Label protesterNameLabel = new Label(University.findStudentByStudentNumber(protest.getProtesterStudentNumber()).getName());
        protesterNameLabel.setFont(Font.font("Montserrat Medium", 14));

        Label protestDate = new Label(protest.getSendDateString());
        protestDate.setFont(Font.font("Montserrat Medium", 14));

        Label protestLessonName = new Label(protest.getLesson().getNameOfLesson());
        protestLessonName.setFont(Font.font("Montserrat Medium", 14));

        Label protestReceiverName = new Label(protest.getLesson().getNameOfTeacher());
        protestReceiverName.setFont(Font.font("Montserrat Medium", 14));

        Label protesterScore = new Label(protest.getScore().toString());
        protesterScore.setFont(Font.font("Montserrat Medium", 14));

        titleContainer.getChildren().add(protesterNameLabel);
        titleContainer.getChildren().add(protestDate);
        titleContainer.getChildren().add(protestLessonName);
        titleContainer.getChildren().add(protestReceiverName);
        titleContainer.getChildren().add(protesterScore);



        VBox contentContainer = new VBox();
        contentContainer.setPrefWidth(594);
        contentContainer.setPrefHeight(206);
        contentContainer.setPadding(new Insets(0, 5, 0, 10));

        Label protestText = new Label(protest.getProtestText());
        protestText.setWrapText(true);
        protestText.setFont(Font.font("Montserrat Light", 13));
        protestText.setPrefHeight(100);
        protestText.setPrefWidth(594);
        protestText.setPadding(new Insets(5));
        protestText.setAlignment(Pos.CENTER_LEFT);

        Label protestAnswer = new Label(protest.getProtestAnswerText());
        protestAnswer.setWrapText(true);
        protestAnswer.setFont(Font.font("Montserrat Light", 13));
        protestAnswer.setPrefHeight(100);
        protestAnswer.setPrefWidth(594);
        protestAnswer.setPadding(new Insets(5));
        protestAnswer.setAlignment(Pos.CENTER_LEFT);

        contentContainer.getChildren().add(protestText);
        contentContainer.getChildren().add(new Separator());
        contentContainer.getChildren().add(protestAnswer);


        getChildren().add(titleContainer);
        getChildren().add(contentContainer);
    }

}
