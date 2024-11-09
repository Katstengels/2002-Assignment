package src;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Patient extends User {
	private Calendar calendar = Calendar.getInstance();
    private String patientID;
    private String name;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String contactNum;
    private String bloodType;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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

    public void viewMedicalRecord() {
        System.out.println("Patient ID: " + getUserID());
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + formatter.format(dateOfBirth));
        System.out.println("Gender: " + gender);
        System.out.println("Contact: " + contactNum);
        System.out.println("Email: " + email);
        System.out.println("Blood Type: " + bloodType);
    }
    public void updatePersonalInfo() {
        System.out.println("Update Personal Information:");
        System.out.println("1. Update Contact Number");
        System.out.println("2. Update Email Address");
        System.out.print("Choose an option: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                System.out.print("Enter new contact number: ");
                contactNum = sc.nextLine();
                System.out.println("Contact number updated.");
                break;
            case 2:
                System.out.print("Enter new email address: ");
                email = sc.nextLine();
                System.out.println("Email address updated.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }




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
