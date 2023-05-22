package Logic.Models;

import Logic.Controller;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Department {

    private static Logger log = LogManager.getLogger(Department.class);

    private String name;
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Teacher getEducationalAssistant() {
        for (Teacher teacher : teachers) {
            if (teacher.getTeacherPosition() == Teacher.TeacherPosition.EDUCATIONAL_ASSISTANT) return teacher;
        }

        log.warn("educationalAssistant dar " + getName() + " yaft nashod");
        return null;
    }

    public Teacher getHeadOfDepartment() {
        for (Teacher teacher : teachers) {
            if (teacher.getTeacherPosition() == Teacher.TeacherPosition.HEAD_OF_DEPARTMENT) return teacher;
        }

        log.error("headOfDepartment dar " + getName() + " yaft nashod");
        return null;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEducationalAssistant(Teacher educationalAssistant) {
        if (getEducationalAssistant() != null) getEducationalAssistant().setTeacherPosition(Teacher.TeacherPosition.TEACHER);
        for (Teacher teacher : teachers) {
            if (teacher.getUsername().equals(educationalAssistant.getUsername())) {
                teacher.setTeacherPosition(Teacher.TeacherPosition.EDUCATIONAL_ASSISTANT);
                log.info(teacher.getUsername() + " tavasote " + Controller.getCurrentUser().getUsername() + " moaven amoozeshi shode");
            }
        }
    }

    public void addTeacher(Teacher teacher) {

        log.info(teacher.getUsername() + " tavasote " + Controller.getCurrentUser().getUsername() + " ezafeh shod");

        teachers.add(teacher);
        teacher.setNameOfDepartment(this.getName());

    }

    public void removeTeacher(Teacher teacher) {

        if (getEducationalAssistant() != null && teacher.getUsername().equals(getEducationalAssistant().getUsername())){
            getHeadOfDepartment().deposalEducationalAssistant(getEducationalAssistant());
        }
        teacher.setNameOfDepartment("");
        teachers.remove(teacher);

    }

    public void addLesson(Lesson lesson) {
        log.info(lesson.getNameOfLesson() + " tavasote " + Controller.getCurrentUser().getUsername() + " ezafeh shod");
        lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {

        for (Protest protest : University.getProtests()) {
            if (protest.getLesson() == lesson) University.removeProtest(protest);
        }

        for (Teacher teacher : University.getTeachers()) {
            teacher.removeLesson(lesson);
        }

        for (StudentAndScore studentAndScore : lesson.getStudentsAndScores()) {
            Student student = studentAndScore.getStudent();

            student.removeLesson(lesson);
        }

        lessons.remove(lesson);
    }

    public void addStudent(Student student) {
        log.info(student.getUsername() + " tavasote " + Controller.getCurrentUser().getUsername() + " ezafeh shod");
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
}
