package src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public void updatePart() {
    	int choice, d, m, y;
    	String newPart;
    	
    	System.out.println("Choose particular to change: ");
    	System.out.println("1. Name");
    	System.out.println("2. Date of Birth");
    	System.out.println("3. Gender");
    	System.out.println("4. Email");
    	System.out.println("5. Contact number");
    	System.out.println("6. Blood type");
    	
    	do {
    		System.out.println("Enter selection: ");
    		choice = sc.nextInt();
    		
    		if (choice>6) System.out.println("Invalid selection! Please enter again.");
    	} while (choice>6);
    	
    	switch (choice){
    	case 1:
    		newPart = sc.nextLine();
    		name = newPart;
    		break;
    		
    	case 2:
    		do {
        		System.out.println("Enter date: ");
        	    d = sc.nextInt();
        	    
        	    if (d>31) System.out.println("Invalid date! Please enter again");
        	} while (d>31);
        	
        	do {
        		System.out.println("Enter month: ");
        	    m = sc.nextInt();
        	    
        	    if (m>12) System.out.println("Invalid month! Please enter again");
        	} while (m>12);
        	
        	System.out.println("Enter year: ");
    	    y = sc.nextInt();
        	
        	calendar.set(y, m, d);
        	dateOfBirth = calendar.getTime();
    		break;
    		
    	case 3:
    		newPart = sc.nextLine();
    		gender = newPart;
    		break;
    		
    	case 4:
    		newPart = sc.nextLine();
    		email = newPart;
    		break;
    		
    	case 5:
    		newPart = sc.nextLine();
    		contactNum = newPart;
    		break;
    		
    	case 6:
    		newPart = sc.nextLine();
    		bloodType = newPart;
    		break;
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
