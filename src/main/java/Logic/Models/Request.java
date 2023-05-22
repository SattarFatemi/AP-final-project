package Logic.Models;

import Logic.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;

public class Request {

    public enum RequestType {
        RECOMMENDATION,
        MINOR,
        WITHDRAWAL,
        CERTIFICATE_STUDENT,
        DORMITORY,
        DISSERTATION
    }

    public enum RequestStatus {
        WAITING,
        ACCEPTED,
        FAILED
    }

    private RequestType requestType;
    private RequestStatus requestStatus;

    private String applicantStudentNumber;
    private String receiverTeacherNumber;
    private String originDepartmentReceiverName;
    private String destinationDepartmentReceiverName;
    private String sendDateString;

    public Request(RequestType requestType, RequestStatus requestStatus, String applicantStudentNumber, String receiverTeacherNumber, String originDepartmentReceiverName, String destinationDepartmentReceiverName, String sendDateString) {
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.applicantStudentNumber = applicantStudentNumber;
        this.receiverTeacherNumber = receiverTeacherNumber;
        this.originDepartmentReceiverName = originDepartmentReceiverName;
        this.destinationDepartmentReceiverName = destinationDepartmentReceiverName;
        this.sendDateString = sendDateString;
    }

    public ArrayList<Lesson> getCommonLessons() {

        ArrayList<Lesson> commonLessons = new ArrayList<>();

        for (Lesson lesson : getReceiver().getLessons()) {
            if (lesson.findStudentAndScore(getApplicant()) != null) {
                commonLessons.add(lesson);
            }
        }

        return commonLessons;

    }

    public ArrayList<Double> getScoresOfCommonLesson() {

        ArrayList<Lesson> commonLessons = getCommonLessons();
        ArrayList<Double> scoresOfCommonLessons = new ArrayList<>();

        for (Lesson lesson : commonLessons) {
            scoresOfCommonLessons.add(lesson.findStudentAndScore(getApplicant()).getScore());
        }

        return scoresOfCommonLessons;

    }

    public ArrayList<String> getNamesOfCommonLessons() {

        ArrayList<Lesson> commonLessons = getCommonLessons();
        ArrayList<String> namesOfCommonLessons = new ArrayList<>();

        for (Lesson lesson : commonLessons) {
            namesOfCommonLessons.add(lesson.getNameOfLesson());
        }

        return namesOfCommonLessons;

    }

    public Student getApplicant() {
        return University.findStudentByStudentNumber(applicantStudentNumber);
    }

    public void setApplicant(Student applicant) {
        this.applicantStudentNumber = applicant.getStudentNumber();
    }

    public Teacher getReceiver() {
        Teacher teacher = University.findTeacherByNumber(receiverTeacherNumber);
        if (teacher == null) return getDestinationDepartmentReceiver().getEducationalAssistant();
        return teacher;
    }

    public void setReceiver(Teacher receiver) {
        this.receiverTeacherNumber = receiver.getNumberOfTeacher();
    }

    public Department getOriginDepartmentReceiver() {
        return University.findDepartmentByName(originDepartmentReceiverName);
    }

    public void setOriginDepartmentReceiver(Department originDepartmentReceiver) {
        this.originDepartmentReceiverName = originDepartmentReceiver.getName();
    }

    public Department getDestinationDepartmentReceiver() {
        return University.findDepartmentByName(destinationDepartmentReceiverName);
    }

    public void setDestinationDepartmentReceiver(Department destinationDepartmentReceiver) {
        this.destinationDepartmentReceiverName = destinationDepartmentReceiver.getName();
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getSendDate() {

        return Controller.stringToDate(sendDateString);

    }

    public void setSendDate(Date sendDate) {
        this.sendDateString = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").toString();
    }

}
