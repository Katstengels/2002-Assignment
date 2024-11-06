package src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient extends User {
    private String patientID;
    private String name;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String contactNum;
    private String bloodType;

    // Constructor
    public Patient(String userID, String password, String role, String patientID, String name, Date dateOfBirth,
                   String gender, String email, String contactNum, String bloodType) {
        super(userID, password, role);
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.contactNum = contactNum;
        this.bloodType = bloodType;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getID() {
        return patientID;   //UserID = PatientID
    }
    public Date getDOB() {
        return dateOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public String getBloodType() {
        return bloodType;
    }
    public String getEmail() {
        return email;
    }
    public String getContactNum() {
        return contactNum;
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");




    @Override
    public String toString() { //FOR TESTING ONLY
        return "Patient {" +
                "UserID='" + super.getUserID()+
                "', Password: " + super.getPassword()+ "\n"+
                "Role: " + super.getRole()+
                " , PatientID: " + this.patientID +  "\n"+
                " Name: " + this.name+
                " , DOB: " + formatter.format(dateOfBirth)+
                " , Email: " + this.email +
                " , Contact: " + this.contactNum +
                " , BloodType: " + this.bloodType + "}";
    }

    private boolean verifyPW(String pw) {
        if (pw == this.getPassword()) return true;
        else return false;
    }

}
