package Logic.Models;

import Logic.Controller;

import java.util.Date;

public class Protest {

    public enum ProtestStatus {
        WAITING,
        ANSWERED
    }

    private ProtestStatus protestStatus;
    private String protesterStudentNumber;
    private String lessonString;
    private String sendDateString;
    private String protestText;
    private String protestAnswerText;
    private Double score;

    public Protest(ProtestStatus protestStatus, String protesterStudentNumber, String lessonString) {
        this.protestStatus = protestStatus;
        this.protesterStudentNumber = protesterStudentNumber;
        this.lessonString = lessonString;
        this.sendDateString = Controller.dateToString(new Date());
        this.score = 0.0;
    }

    public ProtestStatus getProtestStatus() {
        return protestStatus;
    }

    public void setProtestStatus(ProtestStatus protestStatus) {
        this.protestStatus = protestStatus;
    }

    public String getProtesterStudentNumber() {
        return protesterStudentNumber;
    }

    public Student getProtester() {
        return University.findStudentByStudentNumber(getProtesterStudentNumber());
    }

    public void setProtesterStudentNumber(String protesterStudentNumber) {
        this.protesterStudentNumber = protesterStudentNumber;
    }

    public String getLessonString() {
        return lessonString;
    }

    public void setLessonString(String lessonString) {
        this.lessonString = lessonString;
    }

    public Lesson getLesson() {
        return University.findLessonByLessonString(lessonString);
    }

    public Teacher getReceiverTeacher() {
        return University.findTeacherByNumber(getLesson().getNumberOfTeacher());
    }

    public String getSendDateString() {
        return sendDateString;
    }

    public void setSendDateString(String sendDateString) {
        this.sendDateString = sendDateString;
    }

    public String getProtestText() {
        return protestText;
    }

    public void setProtestText(String protestText) {
        this.protestText = protestText;
    }

    public String getProtestAnswerText() {
        return protestAnswerText;
    }

    public void setProtestAnswerText(String protestAnswerText) {
        this.protestAnswerText = protestAnswerText;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
