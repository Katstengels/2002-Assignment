import java.util.Date;
import java.util.Scanner;

public class Doctor extends User{
	private String doctorID;
	private String name;
	private Appointment appointment[];
	private Patient patient[];
	private String specialisation;
	private String schedule[];
	private int age;
	private static int patNo = 0;
	
	public Doctor(String ID, String inName, String spec, int inAge) {
		doctorID = ID;
		name = inName;
		specialisation = spec;
		age = inAge;
		
		int i;
		for (i=0; i<7; i++) {
			schedule[i] = "Available";
		}
	}
	
	public String getID() {
		return doctorID;
	}
	
	public String getName() {
		return name;
	}

	public String getSpecialisation() {
		return specialisation;
	}
	
	public int getAge() {
		return age;
	}
	public void addPatient(String name, Date DOB, String gender, String phoneNo, String email, String bloodtype) {
		patient[patNo] = new Patient (name, DOB, gender, phoneNo, email, bloodType);
		patNo++;
	}
	
	public void viewPatientRecord(String patientID) {
		int i;
		for (i = 0; i<patient.length; i++) {
			if (patient[i].getID == patientID) break;
		}
		patient[i].viewMedicalRecord;
	}
	
	public void updatePatientRecord(String patientID, String diagnosis, String treatment) {
		int i;
		for (i=0; i<patient.length;i++) {
			if (patient[i].getID == patientID) break;
		}
		patient[i].updateRecord(diagnosis, treatment);
	}
	
	public void setAvailability() {
		int choice, avail;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Choose day:");
			System.out.println("1. Monday");
			System.out.println("2. Tuesday");
			System.out.println("3. Wednesday");
			System.out.println("4. Thursday");
			System.out.println("5. Friday");
			System.out.println("6. Saturday");
			System.out.println("7. Sunday");
			System.out.println("8. Exit");
			choice = sc.nextInt();
			
			if (choice != 8) {
				do {
					System.out.println("Indicate availability:");
					System.out.println("1. Available");
					System.out.println("2. Not available");
					avail = sc.nextInt();
				
					switch(avail) {
					case 1:
						schedule[choice-1] = "Available";
						break;
					case 2:
						schedule[choice-1] = "Unavailable";
						break;
					default:
						System.out.println("Invalid choice! Please enter again.");
						break;
					}
				} while (avail>2);
			}
		} while (choice<8); 
	}
	
	public void acceptAppointment(String appointmentID) {
		int i;
		for(i=0; i<appointment.length; i++) {
			if (appointment[i].getID() == appointmentID) appointment[i].updateStatus("Confirmed");
			else System.out.println("Appointment not found!");
		}
	}
	
	public void rejectAppointment(String appointmentID) {
		int i;
		for(i=0; i<appointment.length; i++) {
			if (appointment[i].getID() == appointmentID) appointment[i].updateStatus("Cancelled");
			else System.out.println("Appointment not found!");
		}
	}
	
	public void recordAppointmentOutcome(String appointmentID, String notes) {
		int i;
		for(i=0; i<appointment.length; i++) {
			if (appointment[i].getID() == appointmentID) appointment[i].updateOutcome(notes);
			else System.out.println("Appointment not found!");
	}
}
