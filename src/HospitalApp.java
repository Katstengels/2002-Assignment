package newAssignment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Scanner;

public class HospitalApp {

	public static void main(String[] args) {
		
        	PatientInv pList = PatientInv.getInstance();
		StaffInv sList = StaffInv.getInstance();
		MedicineInv mList = MedicineInv.getInstance();
		
		ArrayList<Staff> staffList = sList.copyStaffList(); //Use members in staff list instance from StaffInv
		ArrayList<Patient> patientList = pList.copyPatientList();
		ArrayList<Medicine> medicineList = mList.copyMedicineInventory(); 
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		
		Scanner sc = new Scanner(System.in);
		String username, password;
		User user = null;
		boolean loggedIn = false;
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
		
		do {
			do {
				System.out.println("Enter Username: ");
				username = sc.nextLine().toUpperCase();
				System.out.println("Enter Password: ");
				password = sc.nextLine();
				
				for (Staff s : staffList) {
					loggedIn = s.login(username, password);
					if (loggedIn) {
						user = s;
						break;
					}
				}
				
				if (!loggedIn) {
				 	for (Patient p : patientList) {
						loggedIn = p.login(username, password);
						if (loggedIn) {
							user = p;
							break;
						}
				 	}
				}   
				
				if (!loggedIn) {
					System.out.println("Username or password incorrect! Please try again.");
				}
				
			} while (!loggedIn);
			
			System.out.println("Welcome!");
			
			switch (user.getRole()) {
			case "Doctor":
				Doctor d = (Doctor) user;
				int patSelect, aptSelect, patAmount, aptAmount;
				Patient pat;
				Appointment apt;
				int choice;
				
				do {
					System.out.println("1. View Patient Medical Records");
					System.out.println("2. Update Patient Medical Records");
					System.out.println("3. View Personal Schedule");
					System.out.println("4. Set Availability for Appointments");
					System.out.println("5. Accept or Decline Appointment Requests");
					System.out.println("6. View Upcoming Appointments");
					System.out.println("7. Record Appointment Outcome");
					System.out.println("8. Logout");
					choice = sc.nextInt();
					
					switch(choice) {
					case 1: // view patient medical record
						patAmount = 0;
						System.out.println("Patient list: ");
						for (Patient p : patientList) {
							patAmount++;
							System.out.println((patAmount) + ". " + p.getID() + " " + p.getName());
						} // print patient list
						
						do {					
							System.out.println("Select patient:");
							patSelect = sc.nextInt();
							if (patSelect > patAmount) System.out.println("Invalid patient! Please enter again.");
						} while (patSelect > patAmount); // patient selection
						
						pat = patientList.get(patSelect-1);
						System.out.println("Name		: " + pat.getName());
						System.out.println("DOB		: " + dateFormat.format(pat.getDOB()));
						System.out.println("Email		: " + pat.getEmail());
						System.out.println("Contact no.	: " + pat.getContactNum());
						System.out.println("Blood type	: " + pat.getBloodType());
						System.out.println("Previous appointment outcomes:");
						for (Appointment a : appointmentList) {
							if (pat.getName().equals(a.getPatient())) {
								System.out.println(a.getDate() + ": " + a.getOutcome());
							}
						}
						System.out.println();
						break;
						
					case 2: // update patient record
						patAmount = 0;
						aptAmount = 0;
						int particular;
						
						System.out.println("Patient list: ");
						for (Patient p : patientList) {
							patAmount++;
							System.out.println((patAmount) + ". " + p.getID() + " " + p.getName());
						} // print patient list
						
						do {					
							System.out.println("Select patient:");
							patSelect = sc.nextInt();
							if (patSelect > patAmount) System.out.println("Invalid patient! Please enter again.");
						} while (patSelect > patAmount); // patient selection
						
						pat = patientList.get(patSelect-1);

						do {
							System.out.println("Select item to edit: ");
							System.out.println("1. Name");
							System.out.println("2. DOB");
							System.out.println("3. Email");
							System.out.println("4. Contact no");
							System.out.println("5. Blood type");
							System.out.println("6. Exit");

							do {
								System.out.println("Enter selection: ");
								particular = sc.nextInt();
								if (particular>6) System.out.println("Invalid choice! Please enter again.");
							} while (particular>6);

							switch (particular){
							case 1:
								System.out.println("Enter new name: ");
								// need patient update method
								break;
									
							case 2:
								System.out.println("Enter new DOB: ");
								// need patient update method
								break;
									
							case 3:
								System.out.println("Enter new email: ");
								// need patient update method
								break;
									
							case 4:
								System.out.println("Enter new contact no: ");
								// need patient update method
								break;
									
							case 5:
								System.out.println("Enter new blood type: ");
								// need patient update method
								break;
									
							case 6:
								break;
							}
						
						} while (particular != 6);
						
						System.out.println();
						break;
						
					case 3: // personal schedule
						d.getAllAvailability();
						System.out.println();
						break;
					
					case 4: // set availability
						d.setAvailability();
						System.out.println();
						break;
						
					case 5: // accept or reject appointments
						aptAmount = 0;
						System.out.println("Appointment list: ");
						for (Appointment a : appointmentList) {
							if(a.getDoctor().equals(d.getName())) { // list all appointments under dr's name
								aptAmount++;
								System.out.println(aptAmount + ". " + a.getID() + ": " + a.getDate() + " " + a.getTime() + " " + a.getPatient());
							}
						} 
						
						if (aptAmount != 0) {
							do {					
								System.out.println("Select appointment:");
								aptSelect = sc.nextInt();
								if (aptSelect > aptAmount) System.out.println("Invalid appointment! Please enter again.");
							} while (aptSelect > aptAmount); // appointment selection
							
							apt = appointmentList.get(aptSelect-1);
							
							int acc;
							
							System.out.println("1. Accept appointment");
							System.out.println("2. Reject appointment");
							do {
								System.out.println("Enter selection: ");
								acc = sc.nextInt();
								if (acc > 2) System.out.println("Invalid selection! Please enter again.");
							} while (acc>2);
							
							switch (acc) {
							case 1:
								apt.updateStatus("Confirmed");
								d.addAppointment(apt);
								System.out.println("Appointment accepted.");
								break;
							case 2:
								apt.updateStatus("Rejected");
								System.out.println("Appointment rejected.");
								break;
							}
						}
						
						else System.out.println("No appointment found!");
						System.out.println();
						break;
						
					case 6: // view upcoming appointments
						System.out.println("Upcoming appointments: ");
						
						aptAmount = 0;
						
						for (Appointment a : appointmentList) {
							if (a.getDoctor().equals(d.getName()) && a.getDateTime().after(now)) { // list appointments that are in the future and under dr's name
								System.out.println();
								System.out.println("Appointment ID	: " + a.getID());
								System.out.println("Date			: " + a.getDate());
								System.out.println("Time 			: " + a.getTime());
								System.out.println("Patient			: " + a.getPatient());
								aptAmount++;
							}
						}
						
						if (aptAmount == 0) System.out.println("No appointment found!");
						System.out.println();
						break;
					
					case 7: // appointment outcome
						patAmount = 0;
						aptAmount = 0;
						System.out.println("Patient list: ");
						for (Patient p : patientList) {
							patAmount++;
							System.out.println((patAmount) + ". " + p.getID() + " " + p.getName());
						} // list patients
						
						do {					
							System.out.println("Select patient:");
							patSelect = sc.nextInt();
							if (patSelect > patAmount) System.out.println("Invalid patient! Please enter again.");
						} while (patSelect > patAmount);
						
						pat = patientList.get(patAmount-1);
						for (Appointment a : appointmentList) {
							if (pat.getName().equals(a.getPatient()) && d.getName().equals(a.getDoctor())) { // appointments with both pt's and dr's names
								aptAmount++;
								System.out.println((aptAmount) + ". " + a.getID() + " " + a.getDate());
							}
						}
						if (aptAmount != 0) {
							do {					
								System.out.println("Select appointment:");
								aptSelect = sc.nextInt();
								if (aptSelect > aptAmount) System.out.println("Invalid appointment! Please enter again.");
							} while (aptSelect > aptAmount);
							
							apt = appointmentList.get(aptSelect-1);
							
							apt.updateOutcome(sc.nextLine());
						}
						
						else System.out.println("No appointment found!");
						System.out.println();
						break;
						
					case 8: // logout
						System.out.println("Logging out...");
						sc.nextLine();
						loggedIn = false;
						System.out.println();
						break;
						
					default:
						System.out.println("Invalid choice! Please enter again.");
						break;
					}
				} while (choice != 8);
				
				
				break;
				
			case "Pharmacist":
			Pharmacist pharma = (Pharmacist) user;

                    	Scanner scPharma = new Scanner(System.in); //Used new scanner to prevent Username from being entered on exit.

				    int choice = 0;
		
				    do{
					System.out.println();
					System.out.println("Hello " + pharma.getName() + ", welcome to the Pharmacy menu");
					System.out.println("1. Change Password");
					System.out.println("2. PatientCare");
					System.out.println("3. Medicine Management");
					System.out.println("4. Quit");
		
					do {
					    System.out.println("Enter selection: ");
					    choice = scPharma.nextInt();
					    if (choice>4 || choice < 1) System.out.println("Invalid choice! Please enter again.");
					} while (choice>4 || choice < 1);
		
		
					switch(choice){
					    case 1:
		
						break;
					    case 2:
						System.out.println("PatientCare temp");
						break;
					    case 3:
						mList.printList();
						int m;
						do {
						    System.out.println("1. Add stock");
						    System.out.println("2. Replenish low stocks");
						    System.out.println("3. Previous Menu");
						    do {
							System.out.println("Enter selection: ");
							m = scPharma.nextInt();
							if (m>3 || m < 1) System.out.println("Invalid choice! Please enter again.");
						    } while (m>3 || m < 1);
						    switch(m){
							case 1:
							case 2:
							    mList.autoRestock();
							    break;
							case 3:
							    break;
						    }
						}while(m !=3);
		
		
		                                break;
		                            case 4:
		                                loggedIn = false;
		                                break;
		                        }
		
		
		                    }while(choice !=4);
		

				break;
				
			case "Administrator":
				System.out.println("Admin"); // just to test can delete later

				//enter your code here
				
				break;
				
			case "Patient":
				System.out.println("Patient"); // just to test can delete later

				//enter your code here
				
				break;
			}
			
		} while (!loggedIn); //go back to login page
	}

}
