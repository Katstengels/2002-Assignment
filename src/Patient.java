package src;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@code Patient} class represents a patient in the system, inheriting basic user information
 * from the {@code User} class. This class stores and manages detailed information about patients,
 * including their personal information and contact details.
 * <p>
 * This class provides methods to retrieve and update patient information.
 * </p>
 */

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


	/**
	 * Constructs a {@code Patient} object with the specified details.
	 *
	 * @param userID      the unique identifier of the user
	 * @param password    the password for authentication
	 * @param role        the role of the user in the system
	 * @param patientID   the unique identifier of the patient
	 * @param name        the name of the patient
	 * @param dateOfBirth the date of birth of the patient
	 * @param gender      the gender of the patient
	 * @param email       the email address of the patient
	 * @param contactNum  the contact number of the patient
	 * @param bloodType   the blood type of the patient
	 */

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
		int choice = 0, d = 0, m = 0, y = 0;
		String newPart;

		System.out.println("Choose particular to change: ");
		System.out.println("1. Name");
		System.out.println("2. Date of Birth");
		System.out.println("3. Gender");
		System.out.println("4. Email");
		System.out.println("5. Contact number");
		System.out.println("6. Blood type");

		// Input validation for 'choice'
		do {
			System.out.println("Enter selection: ");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please enter an integer.");
				sc.next(); // Consume the invalid input
			}
			choice = sc.nextInt();

			if (choice > 6 || choice < 1) {
				System.out.println("Invalid selection! Please enter again.");
			}
		} while (choice > 6 || choice < 1);

		sc.nextLine(); // Consume the newline character left by nextInt()

		switch (choice) {
			case 1:
				System.out.println("Enter new name: ");
				newPart = sc.nextLine();
				name = newPart;
				break;

			case 2:
				// Input validation for 'd' (day)
				do {
					System.out.println("Enter day: ");
					while (!sc.hasNextInt()) {
						System.out.println("Invalid input! Please enter an integer.");
						sc.next(); // Consume the invalid input
					}
					d = sc.nextInt();

					if (d > 31 || d < 1) {
						System.out.println("Invalid date! Please enter again.");
					}
				} while (d > 31 || d < 1);

				// Input validation for 'm' (month)
				do {
					System.out.println("Enter month: ");
					while (!sc.hasNextInt()) {
						System.out.println("Invalid input! Please enter an integer.");
						sc.next(); // Consume the invalid input
					}
					m = sc.nextInt();

					if (m > 12 || m < 1) {
						System.out.println("Invalid month! Please enter again.");
					}
				} while (m > 12 || m < 1);

				// Input validation for 'y' (year)
				System.out.println("Enter year: ");
				while (!sc.hasNextInt()) {
					System.out.println("Invalid input! Please enter an integer.");
					sc.next(); // Consume the invalid input
				}
				y = sc.nextInt();

				calendar.set(y, m - 1, d); // Adjust month by subtracting 1 (Calendar month starts from 0)
				dateOfBirth = calendar.getTime();

				System.out.println("DOB edited ");
				break;

			case 3:
				System.out.println("Enter new gender: ");
				newPart = sc.nextLine();
				gender = newPart;
				System.out.println("Gender edited ");
				break;

			case 4:
				System.out.println("Enter new email: ");
				newPart = sc.nextLine();
				email = newPart;
				System.out.println("Email edited ");
				break;

			case 5:
				System.out.println("Enter new contact number: ");
				newPart = sc.nextLine();
				contactNum = newPart;
				System.out.println("Contact number edited ");
				break;

			case 6:
				System.out.println("Enter new blood type: ");
				newPart = sc.nextLine();
				bloodType = newPart;
				System.out.println("Blood type edited ");
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

}
