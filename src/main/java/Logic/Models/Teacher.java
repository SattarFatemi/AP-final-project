package Logic.Models;

import Logic.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Teacher extends User {

    public enum TeacherLevel {
        ASSISTANT_PROFESSOR,
        ASSOCIATE_PROFESSOR,
        PROFESSOR;
    }

    public enum TeacherPosition {
        TEACHER,
        EDUCATIONAL_ASSISTANT,
        HEAD_OF_DEPARTMENT
    }

    private String numberOfTeacher;
    private TeacherLevel teacherLevel = TeacherLevel.ASSISTANT_PROFESSOR;
    private TeacherPosition teacherPosition = TeacherPosition.TEACHER;
    private String roomNumber;
    private ArrayList<String> numberOfLessons = new ArrayList<>();

    public String getNumberOfTeacher() {
        return numberOfTeacher;
    }

    public TeacherLevel getTeacherLevel() {
        return teacherLevel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getTeacherLevelString() {
        return this.getTeacherLevel().toString();
    }

    public TeacherPosition getTeacherPosition() {
        return teacherPosition;
    }

    public void setNumberOfTeacher(String numberOfTeacher) {
        this.numberOfTeacher = numberOfTeacher;
    }

    public void setTeacherLevel(TeacherLevel teacherLevel) {
        this.teacherLevel = teacherLevel;
    }

    public void setTeacherPosition(TeacherPosition teacherPosition) {
        this.teacherPosition = teacherPosition;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean setTeacherAsEducationalAssistant(Teacher teacher) {

        if (teacher.getDepartment().getEducationalAssistant() == null) {
            teacher.setTeacherPosition(TeacherPosition.EDUCATIONAL_ASSISTANT);
            teacher.getDepartment().setEducationalAssistant(teacher);
            return true;
        }
        return false;

    }

    public ArrayList<String> getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(ArrayList<String> numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public ArrayList<Lesson> getLessons() {

        ArrayList<Lesson> lessons = new ArrayList<>();

        for (Lesson lesson : getDepartment().getLessons()) {
            if (lesson.getNumberOfTeacher().equals(this.getNumberOfTeacher())) {
                lessons.add(lesson);
            }
        }

        return lessons;
    }

    public void deposalEducationalAssistant(Teacher educationalAssistant) {

        educationalAssistant.setTeacherPosition(TeacherPosition.TEACHER);

    }

    public void removeTeacher(Teacher teacher) {

        teacher.getDepartment().removeTeacher(teacher);

    }

    public void addTeacher(Teacher teacher) {

        this.getDepartment().addTeacher(teacher);

    }

    public void addLesson(Lesson lesson) {

        this.getDepartment().addLesson(lesson);

    }

    public void removeLesson(Lesson lesson) {
        for (String numberOfLesson : getNumberOfLessons()) {
            if (numberOfLesson.equals(lesson.getNumberOfLesson())) {
                numberOfLessons.remove(lesson);
            }
        }
    }
}
