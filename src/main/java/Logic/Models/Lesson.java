package Logic.Models;

import Logic.Controller;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Lesson {

    private static Logger log = LogManager.getLogger(Lesson.class);

    public enum ScoresStatus {
        NOT_APPROVED,
        TEMPORARY_APPROVED,
        APPROVED
    }

    private String nameOfLesson;
    private String nameOfDepartment;
    private String numberOfTeacher;
    private String numberOfUnits;
    private String nameOfGrade;
    private String numberOfLesson;
    private String groupNumber;
    private String timeOfLesson;
    private String timeOfExamInString;
    private ScoresStatus status = ScoresStatus.NOT_APPROVED;
    private ArrayList<StudentAndScore> studentsAndScores = new ArrayList<>();

    public Lesson(String nameOfLesson, String nameOfDepartment, String numberOfTeacher, String numberOfUnits, String nameOfGrade, String numberOfLesson, String groupNumber, String timeOfLesson, String timeOfExamInString) {
        this.nameOfLesson = nameOfLesson;
        this.nameOfDepartment = nameOfDepartment;
        this.numberOfTeacher = numberOfTeacher;
        this.numberOfUnits = numberOfUnits;
        this.nameOfGrade = nameOfGrade;
        this.numberOfLesson = numberOfLesson;
        this.groupNumber = groupNumber;
        this.timeOfLesson = timeOfLesson;
        this.timeOfExamInString = timeOfExamInString;
        this.status = ScoresStatus.NOT_APPROVED;
    }

    public Integer getNumberOfPassed() {
        if (status == ScoresStatus.NOT_APPROVED) return -1;

        Integer ans = 0;
        for (StudentAndScore studentAndScore : getStudentsAndScores()) {
            if (studentAndScore.getScore() >= 10.0) ans++;
        }

        return ans;
    }

    public Integer getNumberOfStudents() {
        return getStudentsAndScores().size();
    }

    public Integer getNumberOfFailed() {
        if (status == ScoresStatus.NOT_APPROVED) return -1;
        return getNumberOfStudents() - getNumberOfPassed();
    }

    public Double getAverageScore() {
        if (status == ScoresStatus.NOT_APPROVED) return -1.0;
        Double sum = 0.0;
        for (StudentAndScore studentAndScore : getStudentsAndScores()) {
            sum += studentAndScore.getScore();
        }
        return Controller.roundNumberByQuarter(sum/getNumberOfStudents());
    }

    public Double getAveragePassedScore() {
        if (status == ScoresStatus.NOT_APPROVED) return -1.0;
        Double sum = 0.0;
        for (StudentAndScore studentAndScore : getStudentsAndScores()) {
            if (studentAndScore.getScore() >= 10.0) sum += studentAndScore.getScore();
        }
        return Controller.roundNumberByQuarter(sum/getNumberOfPassed());

    }

    public StudentAndScore findStudentAndScore(Student student) {

        for (StudentAndScore studentAndScore : studentsAndScores) {
            if (student.getStudentNumber().equals(studentAndScore.getStudent().getStudentNumber())) {
                return studentAndScore;
            }
        }

        log.warn("studentAndScore " + student.getUsername() + " dar " + getNameOfLesson() + " yaft nashod");
        return null;

    }

    public void addStudentAndScore(StudentAndScore studentAndScore) {
        studentsAndScores.add(studentAndScore);
    }

    public void changeScoreOfStudent(Student student, Double newScore) {
        for (StudentAndScore studentAndScore : studentsAndScores) {
            if (studentAndScore.getStudent().getStudentNumber().equals(student.getStudentNumber())) {
                studentAndScore.setScore(newScore);
            }
        }
    }

    public void removeStudentAndScore(StudentAndScore studentAndScore) {
        studentsAndScores.remove(studentAndScore);
    }

    public ArrayList<StudentAndScore> getStudentsAndScores() {
        return studentsAndScores;
    }

    public void setStudentsAndScores(ArrayList<StudentAndScore> studentsAndScores) {
        this.studentsAndScores = studentsAndScores;
    }

    public Department getDepartment() {
        for (Department department : University.getDepartments()) {
            if (department.getName().equals(getNameOfDepartment())) return department;
        }

        log.error("department baraye darse " + getNameOfLesson() + " yaft nashod");
        return null;
    }

    public String toString() {
        return getNumberOfLesson() + "," + getGroupNumber();
    }

    public String getNameOfLesson() {
        return nameOfLesson;
    }

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public String getNameOfTeacher() {
        for (Teacher teacher : University.getTeachers()) {
            if (teacher.getNumberOfTeacher().equals(numberOfTeacher)) return teacher.getName();
        }

        log.error("nameOfTeacher baraye " + getNameOfLesson() + " yaft nashod");
        return null;
    }

    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    public String getNameOfGrade() {
        return nameOfGrade;
    }

    public String getNumberOfLesson() {
        return numberOfLesson;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public String getTimeOfLesson() {
        return timeOfLesson;
    }

    public String getTimeOfExamInString() {
        return timeOfExamInString;
    }

    public Date getTimeOfExam() {

        try {
            Date timeOfExam = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(getTimeOfExamInString());
            return timeOfExam;

        } catch (ParseException e) {
            e.printStackTrace();
            log.error("getTimeOfExamInString() is not formatted. return null");
            return null;
        }

    }

    public String getNumberOfTeacher() {
        return numberOfTeacher;
    }

    public void setNumberOfTeacher(String numberOfTeacher) {
        this.numberOfTeacher = numberOfTeacher;
    }

    public void setNameOfLesson(String nameOfLesson) {
        this.nameOfLesson = nameOfLesson;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }

    public void setTeacher(Teacher teacher) {
        this.numberOfTeacher = teacher.getNumberOfTeacher();
    }

    public void setTeacher(String numberOfTeacher) {
        this.numberOfTeacher = numberOfTeacher;
    }

    public void setTeacherByName(String nameOfTeacher) { this.numberOfTeacher = University.findTeacherByName(nameOfTeacher).getNumberOfTeacher(); }

    public void setNumberOfUnits(String numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public void setNameOfGrade(String nameOfGrade) {
        this.nameOfGrade = nameOfGrade;
    }

    public void setNumberOfLesson(String numberOfLesson) {
        this.numberOfLesson = numberOfLesson;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setTimeOfLesson(String timeOfLesson) {
        this.timeOfLesson = timeOfLesson;
    }

    public void setTimeOfExamInString(String timeOfExamInString) {
        this.timeOfExamInString = timeOfExamInString;
    }

    public ScoresStatus getStatus() {
        return status;
    }

    public String getStatusString() {

        if (status == null) status = ScoresStatus.NOT_APPROVED;

        switch (status) {
            case NOT_APPROVED -> {
                return "Sabt Nashodeh";
            }
            case TEMPORARY_APPROVED -> {
                return "Movaghat";
            }
            case APPROVED -> {
                return "Nahayi";
            }
            default -> {
                return "NaMaloom";
            }
        }
    }

    public void setStatus(ScoresStatus status) {
        this.status = status;
    }
}
