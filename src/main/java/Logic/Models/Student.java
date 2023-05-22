package Logic.Models;

import Logic.Controller;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Student extends User {

    private static Logger log = LogManager.getLogger(Student.class);

    public enum StudentGrade {
        UNDERGRADUATE,
        MASTERS,
        PHD
    }

    public enum StudentStatus {
        STUDENT,
        GRADUATED
    }

    private StudentGrade studentGrade;
    private String studentNumber = "999";
    private String numberOfSupervisor = "123456789";
    private int entryYear = 1400;
    private StudentStatus studentStatus = StudentStatus.STUDENT;
    private ArrayList<String> lessonStrings = new ArrayList<>();

    public Double getAverageScore() {

        Double sum = 0.0;
        int sumOfNumbersOfUnits = 0;

        for (String lessonString : lessonStrings) {
            Lesson lesson = University.findLessonByLessonString(lessonString);
            Double score = lesson.findStudentAndScore(this).getScore();
            if (score >= 0) {
                int numberOfUnitsInt = Integer.parseInt(lesson.getNumberOfUnits());
                sum += score * numberOfUnitsInt;
                sumOfNumbersOfUnits += numberOfUnitsInt;
            }
        }

        return Controller.round(sum/sumOfNumbersOfUnits, 3);
    }

    public void addLesson(Lesson lesson) {
        lessonStrings.add(lesson.toString());
    }

    public void removeLesson(Lesson lesson) {
        lessonStrings.remove(lesson.toString());
    }

    public ArrayList<Lesson> getLessons() {
        ArrayList<Lesson> lessons = new ArrayList<>();

        for (String lessonString : lessonStrings) {
            lessons.add(University.findLessonByLessonString(lessonString));
        }

        return lessons;
    }

    public StudentGrade getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(StudentGrade studentGrade) {
        this.studentGrade = studentGrade;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Teacher getSupervisor() {

        for (Teacher teacher : University.getTeachers()) {
            if (teacher.getNumberOfTeacher().equals(numberOfSupervisor)) return teacher;
        }

        log.error("supervisor baraye " + getUsername() + " yaft nashod");
        return null;
    }

    public void setSupervisor(Teacher supervisor) {
        numberOfSupervisor = supervisor.getNumberOfTeacher();
    }

    public int getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

    public ArrayList<String> getLessonStrings() {
        return lessonStrings;
    }

    public void setLessonStrings(ArrayList<String> lessonStrings) {
        this.lessonStrings = lessonStrings;
    }
}
