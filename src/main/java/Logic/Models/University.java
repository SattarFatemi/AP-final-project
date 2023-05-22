package Logic.Models;

import Logic.Controller;
import javafx.scene.control.TableColumn;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class University {


    private static Logger log = LogManager.getLogger(Controller.class);

    private static ArrayList<Department> departments = new ArrayList<>();

    private static ArrayList<Request> requests = new ArrayList<>();
    private static ArrayList<Protest> protests = new ArrayList<>();

    public static Department findDepartmentByName(String departmentName) {

        for (Department department : getDepartments()) {
            if (department.getName().equals(departmentName)) return department;
        }

        log.error("cant find department by name for " + departmentName);
        return null;

    }

    public static Student findStudentByStudentNumber(String studentNumber) {

        for (Student student : getStudents()) {
            if (student.getStudentNumber().equals(studentNumber)) return student;
        }

        log.error("cant find student by number for " + studentNumber);
        return null;

    }

    public static Lesson findLessonByLessonString(String lessonString) {

        for (Lesson lesson : getLessons()) {
            if (lesson.toString().equals(lessonString)) return lesson;
        }

        log.error("cant find lesson by lessonString for " + lessonString);
        return null;
    }

    public static Teacher findTeacherByNumber(String numberOfTeacher) {

        for (Teacher teacher : getTeachers()) {
            if (teacher.getNumberOfTeacher().equals(numberOfTeacher)) return teacher;
        }

        log.error("cant find teacher by number for " + numberOfTeacher);
        return null;

    }

    public static Teacher findTeacherByName(String nameOfTeacher) {

        for (Teacher teacher : getTeachers()) {
            if (teacher.getName().equals(nameOfTeacher)) return teacher;
        }

        log.error("cant find teacher by name for " + nameOfTeacher);
        return null;

    }

    public static void addLesson(Lesson lesson) {
        for (Department department : departments) {
            if (department.getName().equals(lesson.getNameOfDepartment())) department.addLesson(lesson);
        }
    }

    public static void addTeacher(Teacher teacher) {
        for (Department department : departments) {
            if (department.getName().equals(teacher.getNameOfDepartment())) department.addTeacher(teacher);
        }
    }

    public static void addStudent(Student student) {
        for (Department department : departments) {
            if (department.getName().equals(student.getNameOfDepartment())) department.addStudent(student);
        }
    }

    public static void addDepartment(Department department) {
        departments.add(department);
    }

    public static ArrayList<Department> getDepartments() {
        return departments;
    }

    public static ArrayList<User> getUsers() {

        ArrayList<User> users = new ArrayList<>();

        for (Department department : University.getDepartments()) {
            users.addAll(department.getStudents());
            users.addAll(department.getTeachers());
        }

        return users;
    }

    public static ArrayList<Lesson> getLessons() {

        ArrayList<Lesson> lessons = new ArrayList<>();

        for (Department department : University.getDepartments()) {
            lessons.addAll(department.getLessons());
        }

        return lessons;
    }

    public static ArrayList<Teacher> getTeachers() {

        ArrayList<Teacher> teachers = new ArrayList<>();

        for (Department department : University.getDepartments()) {
            teachers.addAll(department.getTeachers());
        }

        return teachers;
    }

    public static ArrayList<Student> getStudents() {

        ArrayList<Student> students = new ArrayList<>();

        for (Department department : University.getDepartments()) {
            for (Student student : department.getStudents()) {
                students.add(student);
            }
        }

        return students;
    }

    public static User findUserByUsername(String username) {

        for (int i = 0; i < getUsers().size(); i++) {
            if (getUsers().get(i).getUsername().equals(username)) {
                return getUsers().get(i);
            }
        }

        log.error("cant find user by userName for " + username);
        return null;

    }

    public static void addRequest(Request request) {
        requests.add(request);
    }

    public static void removeRequest(Request request) {
        requests.remove(request);
    }

    public static ArrayList<Request> getRequests() {
        return requests;
    }

    public static void setRequests(ArrayList<Request> requests) {
        University.requests = requests;
    }

    public static void addProtest(Protest protest) {
        protests.add(protest);
    }

    public static void removeProtest(Protest protest) {
        protests.remove(protest);
    }

    public static ArrayList<Protest> getProtests() {
        return protests;
    }

    public static void setProtests(ArrayList<Protest> protests) {
        University.protests = protests;
    }

    public static void removeStudent(Student student) {

        for (Lesson lesson : getLessons()) {
            for (StudentAndScore studentAndScore : lesson.getStudentsAndScores()) {
                if (studentAndScore.getStudent().getUsername().equals(student.getUsername())) {
                    lesson.removeStudentAndScore(studentAndScore);
                }
            }
        }

        for (Request request : getRequests()) {
            if (request.getApplicant().getUsername().equals(student.getUsername())) {
                removeRequest(request);
            }
        }

        for (Protest protest : getProtests()) {
            if (protest.getProtester().getUsername().equals(student.getUsername())) {
                removeProtest(protest);
            }
        }

        student.getDepartment().removeStudent(student);

    }
}
