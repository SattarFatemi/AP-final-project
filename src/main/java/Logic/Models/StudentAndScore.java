package Logic.Models;

import Logic.Controller;

public class StudentAndScore {

    private String studentNumber;
    private Double score = -1.0;

    public StudentAndScore(String studentNumber, Double score) {
        this.studentNumber = studentNumber;
        this.score = score;
    }

    public Student getStudent() {
        return University.findStudentByStudentNumber(studentNumber);
    }

    public void setStudent(Student student) {
        this.studentNumber = student.getStudentNumber();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = Controller.roundNumberByQuarter(score);
    }
}
