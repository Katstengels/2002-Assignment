
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class HospitalApp {

	public static void main(String[] args) {

        PatientInv pList = PatientInv.getInstance();
		StaffInv sList = StaffInv.getInstance();
		MedicineInv mList = MedicineInv.getInstance();
		
		ArrayList<Staff> staffList = sList.copyStaffList(); //Use members in staff list instance from StaffInv
		ArrayList<Patient> patientList = pList.copyPatientList();
		ArrayList<Medicine> medicineList = mList.copyMedicineList();
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		ArrayList<RestockForm> restockList = new ArrayList<>();

		
		Scanner sc = new Scanner(System.in);
		String username, password;
		User user = null;
		boolean loggedIn = false;
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
		int patSelect, aptSelect, medSelect, docSelect, patAmount, aptAmount, medAmount, docAmount;
		Patient pat;
		Doctor doc;
		Appointment apt;
		Medicine med;
		int choice;
		
		do {
			do {
				logInScreen();
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
				Doctor doctor = (Doctor) user;
				
				do {
					System.out.println("Hello " + doctor.getName() + ", welcome to the doctor menu");
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
						
						
						
						System.out.println();
						break;
						
					case 3: // personal schedule
						doctor.getAllAvailability(true);
						System.out.println();
						break;
					
					case 4: // set availability
						doctor.setAvailability();
						System.out.println();
						break;
						
					case 5: // accept or reject appointments
						aptAmount = 0;
						for (Appointment a : appointmentList) {
							if(a.getDoctor().equals(doctor.getName()) && a.getStatus().equals("pending")) { // list all appointments under dr's name
								aptAmount++;
								System.out.println(aptAmount + ". " + a.getID() + ": " + a.getDate() + " " + a.getTime());
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
								doctor.addAppointment(apt);
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
							if (a.getDoctor().equals(doctor.getName()) && a.getDateTime().after(now)) { // list appointments that are in the future and under dr's name
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
						int medYN, medAptAmt;
						patAmount = 0;
						aptAmount = 0;
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
							if (pat.getName().equals(a.getPatient()) && doctor.getName().equals(a.getDoctor())) { // appointments with both pt's and dr's names
								aptAmount++;
								System.out.println((aptAmount) + ". " + a.getDate());
							}
						}
						if (aptAmount != 0) {
							medAmount = 0;
							medSelect = 0;
							
							do {					
								System.out.println("Select appointment:");
								aptSelect = sc.nextInt();
								if (aptSelect > aptAmount) System.out.println("Invalid appointment! Please enter again.");
							} while (aptSelect > aptAmount);
							
							apt = appointmentList.get(aptSelect-1);
							
							System.out.println("Enter appointment outcome: ");
							apt.updateOutcome(sc.nextLine());
							System.out.println("Any medications prescribed?");
							System.out.println("1. Yes");
							System.out.println("2. No");
							
							do {
								System.out.println("Enter choice: ");
								medYN = sc.nextInt();
								
								if (medYN<2) System.out.println("Invalid choice! Please enter again.");
							} while (medYN<2);
							
							switch (medYN) {
							case 1:
								apt.TrueHasMedication();
								
								System.out.println("Medicine list: ");
								for (Medicine m : medicineList) {
									medAmount++;
									System.out.println(medAmount + ". " + m.getName());
								}
								
								do {
									System.out.println("Enter medicine: ");
									medSelect = sc.nextInt();
									
									if (medSelect>medAmount) System.out.println("Invalid medicine! Please enter again.");
									
								} while (medSelect>medAmount);
								
								med = medicineList.get(medSelect-1);
								
								System.out.println("Enter medication amount: ");
								medAptAmt = sc.nextInt();
								
								apt.addPrescript(med.getName(), medAptAmt);
								
								break;
								
							case 2:
								break;
							}
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

				int choiceP = 0;

				do {
					System.out.println();
					System.out.println("Hello " + pharma.getName() + ", welcome to the Pharmacy menu");
					System.out.println("1. Fulfill medication orders"); //TODO
					System.out.println("2. Display stock"); //TODO
					System.out.println("3. Request restock MENU");
					System.out.println("4. Request log"); //TODO
					System.out.println("5. Change Password");
					System.out.println("6. Log off");


					do {
						System.out.println("Enter selection: ");
						choiceP = scPharma.nextInt();
						if (choiceP > 6 || choiceP < 1) System.out.println("Invalid choiceP! Please enter again.");
					} while (choiceP > 6 || choiceP < 1);


					switch (choiceP) {
						case 1:
							//1. Fulfill medication orders
							//Print all appointments with prescriptions

							int index = -1;
							for (Appointment a : appointmentList) {
								if(a.isHasMedication())
								{
									index = appointmentList.indexOf(a);
									System.out.println("================= Prescription ===================");
									System.out.printf("%-10s %-10s %-10s %-10s \n","IndexNo","Medication","Amount","Status");
									System.out.println("==================================================");

									System.out.printf("%-10d",index);
									a.printPrescription();
								}
							}
							//Choose appointment with the index shown
							if(index!=-1)
							{
								System.out.println("Choose appointment with the index above");
								int apptIndex = -1;
								while (true) {													//Reject non-int inputs
									if (scPharma.hasNextInt()) {
										apptIndex = scPharma.nextInt(); // Read the integer input
										break; // Exit loop if an integer was successfully read
									} else {
										System.out.println("Invalid input. Please enter an integer.");
										scPharma.next(); // Consume the invalid input to avoid infinite loop
									}
								}
								Appointment a = appointmentList.get(apptIndex);
								a.printPrescription();

								System.out.println("Enter prescription you would like to fulfill");
								//Choose which medication to fulfill, update "medIsFilled" per PrescriptedMed and update medicineList
								String prescription = scPharma.nextLine();

								a.fulfillPrescription(prescription);
								mList.minusStock(prescription,a.getPrescribedAmount(prescription));
								System.out.println("Prescription fulfilled!");

							}else System.out.println("No appointment found!");


							break;
						case 2:
							//2. Display stock
							mList.printList();
							break;
						case 3:
							//3. Request restock
							int m;
							do {
								mList.printList();
								//RestockForm rForm = new RestockForm(null,-1,null);
								System.out.println("1. Request restock - Specific medication");
								System.out.println("2. Request restock - All of low");
								System.out.println("3. Cancel restock request");
								System.out.println("4. Back");
								do {
									System.out.println("Enter selection: ");
									m = scPharma.nextInt();
									if (m > 4 || m < 1) System.out.println("Invalid choice! Please enter again.");
								} while (m > 4 || m < 1);
								switch (m) {
									case 1:
										//1. Request restock - Specific medication
										mList.printList();
										String restockMed = null;
										int restockAmt = 0;

										System.out.println("Which medication would you like to restock?");
										scPharma.nextLine();
										restockMed = scPharma.nextLine();
										System.out.println("How much would you like to restock?");
										restockAmt = scPharma.nextInt();

										RestockForm rForm = new RestockForm(restockMed,restockAmt);
										restockList.add(rForm);

										System.out.println("Restock request has been made successfully.");
										break;
									case 2:
										//2. Request restock - All low
										mList.printLowStockMedicine();
										//
										break;
									case 3:
										//3. Cancel restock request
										//Lists all requests
										boolean triggg = false;
										System.out.printf("%-10s %-20s %-10s %-20s\n","restockID","Medicine","Amount","Status");
										for (RestockForm rF : restockList) {
											if(!rF.isFulfilled()){
												rF.printFormDetails();
												triggg = true;
											};
										}
										if (!triggg) {System.out.println("==========  There are no past requests  =========="); break;}

										//RestockForm restockList = new RestockForm()


										//restockList.remove(restockList);
										break;
									case 4:
										//4. Back
										break;
								}
							} while (m != 4);
							break;
						case 4:
							//4. Request log
							boolean trigg = false;
							System.out.printf("%-10s %-20s %-10s %-20s\n","restockID","Medicine","Amount","Status");
							for (RestockForm rF : restockList) {
								rF.printFormDetails();
								trigg = true;
							}
							if (!trigg) {System.out.println("==========  There are no past requests  ==========");}

							break;
						case 5:
							//5. Change Password
							scPharma.nextLine();
							String oldP, newP;
							System.out.println("Please enter your current password: ");
							oldP = scPharma.nextLine();
							System.out.println("Please enter your new password");
							newP = scPharma.nextLine();

							if(!pharma.changePassword(oldP,newP))
							{System.out.println("Password is incorrect");}
							break;
						case 6:
							//6. Log off
							loggedIn = false;
							break;
					}

				} while (choiceP != 6);



				break;
				
			case "Administrator":
				user = new Administrator(username, password, username);	
				int admchoice, staffSelect=0, staffAmount=0;
				
				//enter your code here
				do {
					System.out.println("1. View and Manage Hospital Staff");
					System.out.println("2. Manage Appointments");
					System.out.println("3. View and Manage Medication Inventory");
					System.out.println("4. Approve Replenishment Requests");
					System.out.println("5. Logout");
					admchoice = sc.nextInt();
					sc.nextLine();
					switch(admchoice) {
		        case 1:
		            int choice2;
		            do {
		            System.out.println("---- View and Manage Hospital Staff ----");
		            System.out.println("1. View Hospital Staff");
		            System.out.println("2. Manage Hospital Staff");
		            System.out.println("3. Back");
		            choice2 = sc.nextInt();
		            sc.nextLine();
		            switch(choice2){
		                case 1:
		                	staffAmount=0;
		                	int filterc = 0;
		                    System.out.println("Viewing hospital staff...");
		                   
		                    do {
		                    System.out.println("1. Filter by Staff Role");
		                    System.out.println("2. Filter by Staff Gender");
		                    System.out.println("3. Filter by Staff Age");
		                    System.out.println("4. View all Staff");
		                    System.out.println("5. Back");
		                    filterc = sc.nextInt();
		                    sc.nextLine();
		                    if (filterc>5 || filterc<1) System.out.println("Invalid choice! Please choose again.");
		                    
		                    
		                    else if (filterc == 4) {
		                    	System.out.println("Staff list: ");
		                    	sList.printList();
		                    	break;
		                    }
		                    
		                    else {
		                    	((Administrator) user).filterStaff(filterc);  
		                    	break;
		                    }
		                    
		                    } while (filterc!=5);
		                    // view view view
		                    break;										
		
		                case 2:
		                    int choice3;
		                    System.out.println("-----------Manage hospital staff------------");
		                    //show hospital staff
		                    do {
		                        System.out.println("Select action: ");
		                        System.out.println("1. Add Staff");
		                        System.out.println("2. Update Staff Details");
		                        System.out.println("3. Remove Staff");
		                        System.out.println("4. Back");
		                        choice3 = sc.nextInt();
		                        sc.nextLine();
		                        switch(choice3){
		                            case 1:
		                                System.out.println("---- Add hospital staff ----");
		                                ((Administrator) user).addStaff();
		                                break;
		                            case 2:
		                                int choice4, staffCount=0;
		                                boolean found = false;
		                                String desiredID;
		                                System.out.println("---- Update hospital staff ----");
		                                
		                                do{
		                                System.out.println("Enter User ID of staff to update:");
		                                desiredID = sc.nextLine();
		                                for (Staff staff : staffList) {
		            	                    if (staff.getID().equalsIgnoreCase(desiredID)) {
		            	                        staffSelect = staffAmount;
		            	                        found=true;
		            	                        break;
		            	                    }
		            	                    staffAmount++;
		            	                }
		                                if (!found) System.out.println("User ID not found! Please try again.");
		                                } while (!found);
		                                
		                                Staff staffy = staffList.get(staffSelect);
		                                System.out.println("Staff ID	: " + staffy.getID());
										System.out.println("Name		: " + staffy.getName());
										System.out.println("Gender		: " + staffy.getGender());
										System.out.println("Age		: " + staffy.getAge());
										System.out.println("Role		: " + staffy.getRole());
								
		                                
		                                do {
		                                System.out.println("Select item to edit: ");
		    							System.out.println("1. Name");
		    							System.out.println("2. Gender");
		    							System.out.println("3. Age");
		    							System.out.println("4. Role");
		    							System.out.println("5. Back");
		    							choice4 = sc.nextInt();
		    							sc.nextLine();
		    							switch(choice4){
		    							    case 1:
		    							        System.out.println("Enter new staff name:");
		    							        String newfield = sc.nextLine();
		    							        staffy.changeName(newfield);
		    							        break;
		    							    case 2:
		    							        System.out.println("Enter new staff gender:");
		    							        newfield = sc.nextLine();
		    							        staffy.changeGender(newfield);
		    							        break;
		    							    case 3:
		    							        System.out.println("Enter new staff age:");
		    							        int newage = sc.nextInt();
		    							        staffy.changeAge(newage);
		    							        sc.nextLine();
		    							        break;
		    							    case 4:
		    							        System.out.println("Enter new staff role:");
		    							        newfield = sc.nextLine();
		    							        staffy.changeRole(newfield);
		    							        break;
		    							    case 5: 
		    							        System.out.println("Case 5 entered. Going back...");
		    							        break;
		    							}
		                                } while (choice4!=5);
		                                break;
		                                
		    							
		        
		                            case 3:
		                                System.out.println("---- Remove hospital staff ----");
		                                ((Administrator) user).removeStaff();
		                                break;
		                            case 4:
		                                System.out.println("Case 4 entered. Going back..."); 
		                                break;
		                        }
		                        //((Administrator) user).updateStaffInv();
		                        
		                    } while (choice3!=4);
		                    break;
		                case 3:
		                    System.out.println("Case 3 entered. Going back..."); 
		                    break;
		            }
		            }  while (choice2!=3);
		            break;
		        case 2:
		        	int choicey, choicez, apptamt, choicef = 0;
		        	 do  {
		        	       System.out.println("1. View Appointment Details");
		        	       System.out.println("2. Manage Appointment Details");
		        	       System.out.println("3. Back");
		        	       System.out.println("Select option: ");
		        	       choicey = sc.nextInt();
		        	       switch (choicey) {
		        	           case 1:
		        	                System.out.println("---------View Appointment Details----------");
		        	                do {
		        	                    System.out.println("1. Filter by doctor");
		        	                    System.out.println("2. Filter by patient");
		        	                    System.out.println("3. Filter by Appointment ID");
		        	                    System.out.println("4. Back");
		        	                    System.out.println("Select option: ");
		        	                    choicez = sc.nextInt();
		        	                    switch (choicez) {
		        	                        case 1:
		        	                            System.out.println("---------Filter by doctor----------");
		        	                            sc.nextLine();
		        	                            System.out.println("Enter doctor name: ");
		        	                            String docName = sc.nextLine();
		        	                            do {
		        	                                System.out.println("1. View past appointments");
		        	                                System.out.println("2. View upcoming appointments");
		        	                                System.out.println("3. View all appointments");
		        	                                System.out.println("4. Back");
		        	                                choicef = sc.nextInt();
		        	                                switch(choicef){
		        	                                    case 1:
		        	                                        System.out.println("Viewing past appointments...");
		        	                                        apptamt = 0;
		        	                						
		        	                						for (Appointment a : appointmentList) {
		        	                							if (a.getDoctor().equals(docName) && a.getDateTime().before(now)) { // list appointments that are in the future and under dr's name
		        	                								System.out.println();
		        	                								System.out.println("Appointment ID	: " + a.getID());
		        	                								System.out.println("Date			: " + a.getDate());
		        	                								System.out.println("Time 			: " + a.getTime());
		        	                								System.out.println("Patient			: " + a.getPatient());
		        	                								System.out.println("Status			: " + a.getStatus());
		        	                								System.out.println("Outcome			: " + a.getOutcome());
		        	                								
		        	                								apptamt++;
		        	                							}
		        	                						}
		        	                						
		        	                						if (apptamt == 0) System.out.println("No appointment found!");
		        	                						System.out.println();
		        	                                        break;
		        	                                    case 2:
		        	                                        System.out.println("Viewing upcoming appointments...");
apptamt = 0;
		        	                						
		        	                						for (Appointment a : appointmentList) {
		        	                							if (a.getDoctor().equals(docName) && a.getDateTime().after(now)) { // list appointments that are in the future and under dr's name
		        	                								System.out.println();
		        	                								System.out.println("Appointment ID	: " + a.getID());
		        	                								System.out.println("Date			: " + a.getDate());
		        	                								System.out.println("Time 			: " + a.getTime());
		        	                								System.out.println("Patient			: " + a.getPatient());
		        	                								System.out.println("Status			: " + a.getStatus());
		        	                								System.out.println("Outcome			: " + a.getOutcome());
		        	                								
		        	                								apptamt++;
		        	                							}
		        	                						}
		        	                						
		        	                						if (apptamt == 0) System.out.println("No appointment found!");
		        	                						System.out.println();
		        	                                       
		        	                                        break;
		        	                                    case 3:
		        	                                        System.out.println("Viewing all appointments...");
apptamt = 0;
		        	                						
		        	                						for (Appointment a : appointmentList) {
		        	                							if (a.getDoctor().equals(docName)) { // list appointments that are in the future and under dr's name
		        	                								System.out.println();
		        	                								System.out.println("Appointment ID	: " + a.getID());
		        	                								System.out.println("Date			: " + a.getDate());
		        	                								System.out.println("Time 			: " + a.getTime());
		        	                								System.out.println("Patient			: " + a.getPatient());
		        	                								System.out.println("Status			: " + a.getStatus());
		        	                								System.out.println("Outcome			: " + a.getOutcome());
		        	                								
		        	                								apptamt++;
		        	                							}
		        	                						}
		        	                						
		        	                						if (apptamt == 0) System.out.println("No appointment found!");
		        	                						System.out.println();
		        	                                        break;
		        	                                       
		        	                                    case 4:
		        	                                        break;
		        	                            
		        	                                }
		        	                            } while (choicef!=4);
		        	                            break;
		        	                        case 2:
		        	                            System.out.println("---------Filter by patient----------");
		        	                            sc.nextLine();
		        	                            System.out.println("Enter patient name: ");
		        	                            String patName = "null"; 
		        	                            patName = sc.nextLine();
		        	                             do {
		        	                                System.out.println("1. View past appointments");
		        	                                System.out.println("2. View upcoming appointments");
		        	                                System.out.println("3. View all appointments");
		        	                                System.out.println("4. Back");
		        	                                choicef = sc.nextInt();
		        	                                switch(choicef){
		        	                                    case 1:
		        	                                        System.out.println("Viewing past appointments...");
		        	                                        apptamt=0;
		        	                						for (Appointment a : appointmentList) {
		        	                							if (a.getPatient().equals(patName) && a.getDateTime().before(now)) { // list appointments that are in the future and under dr's name
		        	                								System.out.println();
		        	                								System.out.println("Appointment ID	: " + a.getID());
		        	                								System.out.println("Date			: " + a.getDate());
		        	                								System.out.println("Time 			: " + a.getTime());
		        	                								System.out.println("Patient			: " + a.getPatient());
		        	                								System.out.println("Status			: " + a.getStatus());
		        	                								System.out.println("Outcome			: " + a.getOutcome());
		        	                								
		        	                								apptamt++;
		        	                							}
		        	                						}
		        	                						
		        	                						if (apptamt == 0) System.out.println("No appointment found!");
		        	                						System.out.println();
		        	                                        break;
		        	                                    case 2:
		        	                                        System.out.println("Viewing upcoming appointments");
		        	                                        apptamt=0;
		        	                						for (Appointment a : appointmentList) {
		        	                							if (a.getPatient().equals(patName) && a.getDateTime().after(now)) { // list appointments that are in the future and under dr's name
		        	                								System.out.println();
		        	                								System.out.println("Appointment ID	: " + a.getID());
		        	                								System.out.println("Date			: " + a.getDate());
		        	                								System.out.println("Time 			: " + a.getTime());
		        	                								System.out.println("Patient			: " + a.getPatient());
		        	                								System.out.println("Status			: " + a.getStatus());
		        	                								System.out.println("Outcome			: " + a.getOutcome());
		        	                								
		        	                								apptamt++;
		        	                							}
		        	                						}
		        	                						
		        	                						if (apptamt == 0) System.out.println("No appointment found!");
		        	                						System.out.println();
		        	                                        break;
		        	                                    case 3:
		        	                                        System.out.println("Viewing all appointments");
		        	                                        apptamt=0;
		        	                						for (Appointment a : appointmentList) {
		        	                							if (a.getPatient().equals(patName)) { // list appointments that are in the future and under dr's name
		        	                								System.out.println();
		        	                								System.out.println("Appointment ID	: " + a.getID());
		        	                								System.out.println("Date			: " + a.getDate());
		        	                								System.out.println("Time 			: " + a.getTime());
		        	                								System.out.println("Patient			: " + a.getPatient());
		        	                								System.out.println("Status			: " + a.getStatus());
		        	                								System.out.println("Outcome			: " + a.getOutcome());
		        	                								
		        	                								apptamt++;
		        	                							}
		        	                						}
		        	                						
		        	                						if (apptamt == 0) System.out.println("No appointment found!");
		        	                						System.out.println();
		        	                                        break;
		        	                                    case 4:
		        	                                        break;
		        	                            
		        	                                }
		        	                            } while (choicef!=4);
		        	                            
		        	                            break;
		        	                        case 3:
		        	                            System.out.println("---------Filter by Appointment ID----------");
		        	                            sc.nextLine();
		        	                            System.out.println("Enter Appointment ID: ");
		        	                            String apptID = sc.nextLine();
		        	                            
    	                                        apptamt=0;
    	                						for (Appointment a : appointmentList) {
    	                							if (a.getID().equals(apptID)) { // list appointments that are in the future and under dr's name
    	                								System.out.println();
    	                								System.out.println("Appointment ID	: " + a.getID());
    	                								System.out.println("Date			: " + a.getDate());
    	                								System.out.println("Time 			: " + a.getTime());
    	                								System.out.println("Patient			: " + a.getPatient());
    	                								System.out.println("Status			: " + a.getStatus());
    	                								System.out.println("Outcome			: " + a.getOutcome());
    	                								
    	                								apptamt++;
    	                							}
    	                						}
    	                						
    	                						if (apptamt == 0) System.out.println("No appointment found!");
    	                						System.out.println();
    	                                        
		        	                          
		        	                            break;
		        	                        case 4:
		        	                            System.out.println("Case 4 entered. Going back...");
		        	                            break;
		        	                    }
		        	                } while (choicez!=4);
		        	                
		        	                break;
		        	           case 2:
		        	               System.out.println("---------Manage Appointment Details----------");
		        	                break;
		        	           case 3:
		        	               System.out.println("Case 3 entered. Going back...");
		        	                break;
		        	          
		        	            
		        	       }
		        	       
		        	   } while (choicey != 3);
		        	   
		            break;
		        case 3:
		        	System.out.println("----------View and Manage Medication Inventory-----------");
		            int choicew, choicev;
		            do {
		            System.out.println("Select action: ");
		            System.out.println("1. View Medicine Inventory");
		            System.out.println("2. Manage Medicine Inventory");
		            System.out.println("3. Back");
		            choicew = sc.nextInt();
		            switch(choicew) {
		                case 1:
		                    System.out.println("Viewing medication...");
		                    ((Administrator) user).getMedicineInventory().printList();
		                    break;
		                case 2:
		                     do {
		                        System.out.println("Select action: ");
		                        System.out.println("1. Add Medication");
		                        System.out.println("2. Remove Medication");
		                        System.out.println("3. Update Medication Fields");
		                        System.out.println("4. Back");
		                        choicev = sc.nextInt();
		                        switch(choicev) {
		                            case 1:
		                                System.out.println("Adding medication...");
		                                ((Administrator) user).addMedicine();
		                                break;
		                            case 2:
		                                System.out.println("Removing medication...");
		                                ((Administrator) user).removeMedicine();
		                                break;
		                            case 3:
		                            	sc.nextLine();
		                                System.out.println("Updating medication...");
		                                int choice4, medCount=0; 
						medSelect=0; 
						medAmount=0;
		                                boolean foundm = false;
		                                String desiredMedName;
		                                System.out.println("---- Update Medication ----");
		                                
		                                do{
		                                medAmount = 0;
		                                System.out.println("Enter name of medication to update:");
		                                desiredMedName = sc.nextLine();
		                                for (Medicine medicine : medicineList) {
		            	                    if (medicine.getName().equalsIgnoreCase(desiredMedName)) {
		            	                        medSelect = medAmount;
		            	                        foundm=true;
		            	                        break;
		            	                    }
		            	                    medAmount++;
		            	                }
		                                if (!foundm) System.out.println("Medicine not found! Please try again.");
		                                } while (!foundm);
		                                
		                                Medicine meddo = medicineList.get(medSelect);
		                                
										System.out.println("Name		: " + meddo.getName());
										System.out.println("Quantity		: " + meddo.getQuantity());
										System.out.println("Low Stock Alert		: " + meddo.getLowStockAlertAmt());
	
								
		                                
		                                do {
		                                System.out.println("Select item to edit: ");
		    							System.out.println("1. Name");
		    							System.out.println("2. Quantity");
		    							System.out.println("3. Low Stock Alert");
		    							
		    							System.out.println("4. Back");
		    							choice4 = sc.nextInt();
		    							sc.nextLine();
		    							switch(choice4){
		    							    case 1:
		    							        System.out.println("Enter new medicine name:");
		    							        String newfield = sc.nextLine();
		    							        meddo.changeName(newfield);
		    							        
		    							        break;
		    							    case 2:
		    							        System.out.println("Enter new medicine quantity:");
		    							        int newAmount = sc.nextInt();
		    							        meddo.changeQuantity(newAmount);
		    							        sc.nextLine();
		    							        break;
		    							    case 3:
		    							        System.out.println("Enter new low stock alert threshold:");
		    							        newAmount = sc.nextInt();
		    							        meddo.changeLowStockAlert(newAmount);
		    							        sc.nextLine();
		    							        break;
		    							    
		    							    case 4: 
		    							        System.out.println("Case 4 entered. Going back...");
		    							        break;
		    							}
		                                } while (choice4!=4);
		                                break;
		                               
		                            case 4:
		                                System.out.println("Case 4 entered. Going back...");
		                                break;
		                        }
		                
		            } while (choicev!=4);
		            
		                    break;
		                case 3:
		                    System.out.println("Case 3 entered. Going back...");
		                    break;
		                    
		            }

		            }while (choicew!=3);
		            break;
		        case 4: 
		        	int choicex;
		        	do {
		        		
			        	System.out.println("---- Approve replenishment requests ----");
			        	System.out.println("1. View approved replenishment requests");
			        	System.out.println("2. View pending replenishment requests");
			        	System.out.println("3. Approve pending replenishment requests");
			        	System.out.println("4. Back");
			        	System.out.println("Enter selection:");
			        	choicex = sc.nextInt();
		        		switch(choicex) {
		        		case 1:
		        			boolean anyrequests = false;
		        			System.out.println("Approved replenishment requests: ");
		        			System.out.printf("%-10s %-20s %-10s %-20s\n","restockID","Medicine","Amount","Status");
							for (RestockForm rF : restockList) {
								if (rF.isFulfilled() == true) {
								rF.printFormDetails();
								anyrequests = true;
								}
							}
							if (anyrequests == false) {
								System.out.println("There are no approved replenishment requests!");
							}
		        			break;
		        		case 2:
		        			System.out.println("Pending replenishment requests: ");
		        			boolean anyrequest = false;
		        
		        			System.out.printf("%-10s %-20s %-10s %-20s\n","restockID","Medicine","Amount","Status");
							for (RestockForm rF : restockList) {
								if (rF.isFulfilled() == false) {
								rF.printFormDetails();
								anyrequest = true;
								}
							}
							if (anyrequest == false) {
								System.out.println("There are no pending replenishment requests!");
							}
		        			break;
		        		case 3:
		        			System.out.println("Pending replenishment requests: ");
		        	
		        			boolean anyreques = false, anyrestockID = false;
		        			int approveID = -1;
		        			
		        			
		        			System.out.printf("%-10s %-20s %-10s %-20s\n","restockID","Medicine","Amount","Status");
							for (RestockForm rF : restockList) {
								if (rF.isFulfilled() == false) {
								rF.printFormDetails();
								anyreques = true;
								}
							}
							if (anyreques == false) {
								System.out.println("There are no pending replenishment requests!");
								break;
							}
							
							do {
		        			System.out.println("Enter Restock ID of request to approve: ");
		        			approveID = sc.nextInt();
		        			anyrestockID = false;
		        			for (RestockForm rF : restockList) {
								if (rF.getRestockID() == approveID && rF.getFulfilled() == false) {
				
									((Administrator) user).plusStock(rF.getMedicationName(), rF.getRestockAmount());
									rF.setFulfilled(true);
									mList.printList();
									anyrestockID = true;
									break;
				
								}
								
							}
		        			
		        			if (anyrestockID == false) {
		        				System.out.println("This Restock ID does not exist!");
		        			}
							} while (anyrestockID == false);
		        			
		        			break;
		        		case 4:
		        			System.out.println("Case 4 entered. Going back...");
		        			break;
		        		}
		        	} while (choicex!=4);

		            break;
		        case 5:
		            System.out.println("Case 5 entered. Logging out...");
		            loggedIn = false;
		            break;
		        default:
		            System.out.println("Invalid option entered. Please try again. ");
		            break;
		    }
		} while (admchoice!=5);
				
				break;
				
			case "Patient":
				Patient patient = (Patient) user;
				  int choicePatient = 0;
				    do {
				        System.out.println();
				        System.out.println("Hello " + patient.getName() + ", welcome to the Patient menu");
				        System.out.println("1. View Medical Record");
				        System.out.println("2. Update Personal Information");
				        System.out.println("3. View Available Appointment Slots");
				        System.out.println("4. Schedule an Appointment");
				        System.out.println("5. Reschedule an Appointment");
				        System.out.println("6. Cancel an Appointment");
				        System.out.println("7. View Scheduled Appointments");
				        System.out.println("8. View Past Appointment Outcome Records");
				        System.out.println("9. Logout");

				        // Ensure valid input for choicePatient
				        do {
				            System.out.print("Enter selection: ");
				            choicePatient = sc.nextInt();
				            sc.nextLine(); // Clear the newline
				            if (choicePatient < 1 || choicePatient > 9) {
				                System.out.println("Invalid choice! Please enter a number between 1 and 9.");
				            }
				        } while (choicePatient < 1 || choicePatient > 9);

				        switch (choicePatient) {
				            case 1:
				                // View Medical Record
				                
				            	patient.viewMedicalRecord();
				            	System.out.println("----------------------------------");
				            	for (Appointment appt : appointmentList) {
				            		if(patient.getName().equals(appt.getPatient())) {
				            			System.out.println("Appt ID: " + appt.getID() +"\n" +
				            					"Doctor: " + appt.getDoctor()+ "\n"+
				            					"Status: " + appt.getStatus()+ "\n"+
				            					"Outcome: " + appt.getOutcome() + "\n");
				            			System.out.println("----------------------------------");
				            		}}
				            
				                break;

				            case 2:
				                // Update Personal Information
								patient.updatePersonalInfo();
				                
				                break;

				            case 3:
				                // View Available Appointment Slots
								System.out.println("Enter the doctor's ID to view available slots: ");
                				String doctorID = sc.nextLine();
								Doctor doctor1 = null;
								for (Staff s : staffList) {
									if (s.getID().equals(doctorID)) {
										if(s.getRole().equals("Doctor")){
											doctor1 = (Doctor) s;
											break;}
									}
								}
								if(doctor1 == null) {
									System.out.println("Doctor not found!");
									break;
								}
								doctor1.getAvailSlots();
	
				               
				                break;

				            case 4:
				                // Schedule an Appointment
								System.out.println("Enter the doctor's ID to schedule an appointment: ");
								doctorID = sc.nextLine();
								doctor1 = null;
								for (Staff s : staffList) {
									if (s.getID().equals(doctorID)) {
										if(s.getRole().equals("Doctor")){
											doctor1 = (Doctor) s;
											break;}
									}
								}
								if(doctor1 == null) {
									System.out.println("Doctor not found!");
									break;
								}
								doctor1.getAvailSlots();
								System.out.println("Enter the date of the appointment (yyyy-MM-dd HH:mm):  ");
								Date date = null;
								try {
									date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sc.nextLine());
								} catch (ParseException e) {
										System.out.println("Invalid date format. Please enter in 'yyyy-MM-dd HH:mm' format.");
								}
								System.out.println("Enter the time of the appointment (HH:mm): ");
								String time = sc.nextLine();
								Appointment appointment = new Appointment(patient, doctor1, date, time);
								System.out.println("Appointment scheduled successfully!");
	
				               
				                break;

				            case 5:
				               	// Reschedule an Appointment
								System.out.println("Enter the doctor's ID to reschedule the appointment: ");
								doctorID = sc.nextLine();
								doctor1 = null;
								for (Staff s : staffList) {
									if (s.getID().equals(doctorID)) {
										if(s.getRole().equals("Doctor")){
											doctor1 = (Doctor) s;
											break;}
									}
								}
								if(doctor1 == null) {
									System.out.println("Doctor not found!");
									break;
								}
								doctor1.promptAndRescheduleForPatient(patient);
	
				                break;

				            case 6:
				                // Cancel an Appointment
				               	System.out.println("Enter the doctor's ID to cancel the appointment: ");
								doctorID = sc.nextLine();
								doctor1 = null;
								for (Staff s : staffList) {
									if (s.getID().equals(doctorID)) {
										if(s.getRole().equals("Doctor")){
											doctor1 = (Doctor) s;
											break;}
									}
								}
								if(doctor1 == null) {
									System.out.println("Doctor not found!");
									break;
								}
								doctor1.promptAndCancelAppointmentForPatient(patient);

				                break;

				            case 7:
				                // View Scheduled Appointments
			
								System.out.println("Enter the doctor's ID to view scheduled appointments: ");
								doctorID = sc.nextLine();
								doctor1 = null;
								for (Staff s : staffList) {
									if (s.getID().equals(doctorID)) {
										if(s.getRole().equals("Doctor")){
											doctor1 = (Doctor) s;
											break;}
									}
								}
								if(doctor1 == null) {
									System.out.println("Doctor not found!");
									break;
								}
				                doctor1.listAppointmentsForPatient(patient);

				               
				                break;

				            case 8:
				                // View Past Appointment Outcome Records
								boolean found = false;
        
        						for (Appointment appointment1 : appointmentList) {
            						// Check if appointment belongs to the patient and is in the past with an outcome
            						if (appointment1.getPatient().equals(patient.getName()) && 
                						appointment1.getDateTime().before(new Date()) && 
                						appointment1.getOutcome() != null) {
                
                							found = true;
                							System.out.printf("Appointment ID: %s\n", appointment1.getID());
                                            System.out.printf("Doctor: %s\n", appointment1.getDoctor());
                                            System.out.printf("Date: %s\n", appointment1.getDate());
                                            System.out.printf("Time: %s\n", appointment1.getTime());
                                            System.out.printf("Outcome: %s\n", appointment1.getOutcome());
                                        	System.out.println("--------------------------------------------------");
            						}
        						}

        						if (!found) {
            						System.out.println("No past appointments with recorded outcomes found for this patient.");
        						}

				              
				                break;

				            case 9:
				                // Logout
				                System.out.println("Logging out...");
				                loggedIn = false;
				                break;
				        }

				    } while (choicePatient != 9);
				    break;


				
				
			}
		} while (!loggedIn); //go back to login page
	}


	private static void logInScreen(){
		System.out.println(
				"         _   _                 _ _        _ \n" +
				"   _    | | | | ___  ___ _ __ (_) |_ __ _| |\n" +
				" _| |_  | |_| |/ _ \\/ __| '_ \\| | __/ _` | |\n" +
				"|_   _| |  _  | (_) \\__ \\ |_) | | || (_| | |\n" +
				" _|_|__ |_| |_|\\___/|___/ .__/|_|\\__\\__,_|_|\n" +
				"|  \\/  | __ _ _ __   __ |_|__ _  ___ _ __   \n" +
				"| |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|  \n" +
				"| |  | | (_| | | | | (_| | (_| |  __/ |     \n" +
				"|_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|     \n" +
				"                          |___/             ");

	}
}
