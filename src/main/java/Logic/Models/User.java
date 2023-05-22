package Logic.Models;

import Logic.Controller;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;

public class User {

    private static Logger log = LogManager.getLogger(User.class);

    private String name;
    private String nationalCode = "4111111111";
    private String username;
    private String passwordHashed;
    private String imagePath = "images\\Users\\default.jpg";
    private String email = "unknown@gmail.com";
    private String lastVisit = "";
    private String nameOfDepartment;
    private String phoneNumber;

    public static String hashPassword(String s) {
        String t = s;

        // hash algorithm: swap every two chars.
        for (int i = 0; i+1 < t.length(); i += 2) {

            char c = t.charAt(i);
            t = t.substring(0, i) + t.charAt(i+1) + t.substring(i+1);
            t = t.substring(0, i+1) + c + t.substring(i+2);

        }

        return t;
    }

    public Department getDepartment() {
        for (Department department : University.getDepartments()) {
            if (department.getName().equals(getNameOfDepartment())) return department;
        }

        log.error("cant find department by name for student " + getUsername());
        return null;
    }

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getEmail() {
        return email;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public Date getLastVisitDate() {
        return Controller.stringToDate(getLastVisit());
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public void setPassword(String password) {
        this.passwordHashed = hashPassword(password);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
