package project2002;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

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
					System.out.println("===============================================");
					System.out.println("Hello " + doctor.getName() + ", welcome to the doctor menu");
					System.out.println("1. View Patient Medical Records");
					System.out.println("2. Update Patient Medical Records");
					System.out.println("3. View Personal Schedule");
					System.out.println("4. Set Availability for Appointments");
					System.out.println("5. Accept or Decline Appointment Requests");
					System.out.println("6. View Upcoming Appointments");
					System.out.println("7. Record Appointment Outcome");
					System.out.println("8. Logout");
					System.out.println("===============================================");

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
						String aptIDSelect = null;
						for (Appointment a : appointmentList) {
							if(a.getDoctor().equals(doctor.getName()) && a.getStatus().equals("Pending")) { // list all appointments under dr's name
								aptAmount++;
								System.out.println(aptAmount + ". " + a.getID() + ": " + "(" + a.getPatient() + ") " + a.getDate() + " " + a.getTime());
							}
						} 
						
						if (aptAmount != 0) {
							
							boolean exitAcceptApt = false;
							apt = null;
							
							do {					
								System.out.println("Enter full appointment ID to accept (Enter 'Back' to cancel): ");
								aptAmount = 0;
								aptIDSelect = null;
								sc.nextLine(); //clear scanner cos its acting up bruh
								
								aptIDSelect = sc.nextLine();
								if (aptIDSelect.equals("Back")) {
									exitAcceptApt = true;
									break;
								}
								for (Appointment a : appointmentList) {
									if (a.getID().equals(aptIDSelect)) {
										apt = a;
										aptAmount++;
									}
								}
								
								if (aptAmount == 0) System.out.println("Invalid appointment! Please enter again.");
							} while (aptAmount == 0);
							
							if (exitAcceptApt) break;
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
								apt.updateStatus("Cancelled");
								doctor.removeAppointment(apt);
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
							if (a.getDoctor().equals(doctor.getName()) && a.getStatus().equals("Confirmed")) { // list appointments that are in the future and under dr's name
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
						int medAmountCounter = 0;
						aptAmount = 0;
						aptIDSelect = null;
						for (Appointment a : appointmentList) {
							if(a.getDoctor().equals(doctor.getName()) && a.getStatus().equals("Confirmed")) { // list all appointments under dr's name
								aptAmount++;
								System.out.println(aptAmount + ". " + a.getID() + ": " + "(" + a.getPatient() + ") " + a.getDate() + " " + a.getTime());
							}
						} 
						
						if (aptAmount != 0) {
							
							
							apt = null;
							sc.nextLine();
							do {					
								System.out.println("Enter full appointment ID to update outcome: ");
								aptAmount = 0;
								
								aptIDSelect = sc.nextLine();
								for (Appointment a : appointmentList) {
									if (a.getID().equals(aptIDSelect)) {
										apt = a;
										aptAmount++;
									}
								}
								
								if (aptAmount == 0) System.out.println("Invalid appointment! Please enter again.");
							} while (aptAmount == 0);
							
							apt.updateStatus("Completed");
							System.out.println("Enter appointment outcome: ");
							apt.updateOutcome(sc.nextLine());
							System.out.println("Any medications prescribed?");
							System.out.println("1. Yes");
							System.out.println("2. No");
							
							do {
								System.out.println("Enter choice: ");
								medYN = sc.nextInt();
								
								if (medYN>2 || medYN<1) System.out.println("Invalid choice! Please enter again.");
							} while (medYN>2 || medYN<1);
							
							switch (medYN) {
							case 1:
								apt.TrueHasMedication();
								
								System.out.println("Medicine list: ");
								for (Medicine m : medicineList) {
									medAmountCounter++;
									System.out.println(medAmountCounter + ". " + m.getName());
								}
								medAmountCounter++;
								System.out.println(medAmountCounter + ". " + "(Exit Menu)");
								medAmountCounter--;
								
								
								do {
									do {
										System.out.println("Enter medicine: ");
										medSelect = sc.nextInt();
										
										if (medSelect>medAmountCounter+1) System.out.println("Invalid medicine! Please enter again.");
										
									} while (medSelect>medAmountCounter+1);
									
									if(medSelect!=medAmountCounter+1) {
										med = medicineList.get(medSelect-1);
										
										System.out.println("Enter medication amount: ");
										medAptAmt = sc.nextInt();
										System.out.println();
										apt.addPrescript(med.getName(), medAptAmt);
									}
								} while(medSelect!=medAmountCounter+1);
								break;
								
							case 2:
								break;
							}
							
							System.out.println("Appointment outcomes for " + apt.getID() +  " | (" + apt.getPatient() + ") "+ apt.getDate() + " "+ apt.getTime() + " successfully recorded.");
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
					System.out.println("===============================================");
					System.out.println("Hello " + pharma.getName() + ", welcome to the Pharmacy menu");
					System.out.println("1. Fulfill medication orders"); //TODO
					System.out.println("2. Display stock"); //TODO
					System.out.println("3. Request restock MENU");
					System.out.println("4. Request log"); //TODO
					System.out.println("5. Change Password");
					System.out.println("6. Log off");
					System.out.println("===============================================");



					do {
						System.out.println("Enter selection: ");
						choiceP = scPharma.nextInt();
						if (choiceP > 6 || choiceP < 1) System.out.println("Invalid choiceP! Please enter again.");
					} while (choiceP > 6 || choiceP < 1);


					switch (choiceP) {
						case 1:
							//1. Fulfill medication orders
							//Print all appointments with prescriptions
							Appointment aptToFulfil = null;
							String aptID = null;
							for (Appointment a : appointmentList) {
								if(a.isHasMedication())
								{
									aptID = a.getID();
									System.out.println("==================== Prescription ======================");
									System.out.printf("%-20s %-14s %-6s %-6s \n","Appointment ID","Medication","Amount","Status");
									System.out.println("========================================================");

									
									a.printPrescriptionFormatting();
								}
							}
							//Choose appointment with the index shown
							if(aptID != null)
							{
								scPharma.nextLine();
								System.out.println("Enter Appointment ID to fulfil medicine: ");
								aptID = scPharma.nextLine();
								while (true) {													//Reject non-int inputs
									for (Appointment a : appointmentList) {
										if (a.getID().equals(aptID) && a.isHasMedication()) {
											aptToFulfil = a;
										}
									}
									if (aptToFulfil != null) {
										
										break; // Exit loop if an integer was successfully read
									} else {
										System.out.println("Invalid input. Please enter again.");
										scPharma.next(); // Consume the invalid input to avoid infinite loop
									}
								}
								
								System.out.println("--------------------------------------------------");
								aptToFulfil.printPrescription();

								System.out.println("Enter prescription you would like to fulfill");
								//Choose which medication to fulfill, update "medIsFilled" per PrescriptedMed and update medicineList
								
								String prescription = scPharma.nextLine();
								
								aptToFulfil.fulfillPrescription(prescription);
								mList.minusStock(prescription,aptToFulfil.getPrescribedAmount(prescription));
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
					System.out.println("===============================================");
					System.out.println("1. View and Manage Hospital Staff");
					System.out.println("2. Manage Appointments");
					System.out.println("3. View and Manage Medication Inventory");
					System.out.println("4. Approve Replenishment Requests");
					System.out.println("5. Logout");
					System.out.println("===============================================");

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
		        	Staff tttStaff = null;
		        	Doctor tttDoc = null;
		        	Patient tttPat = null;
		        	boolean IDexists = false;
		        	String docID, patID = null;
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
		        	                        	IDexists = false;
		        	                            System.out.println("---------Filter by doctor----------");
		        	                            sc.nextLine();
		        	                            do {
			        	                            System.out.println("Enter doctor ID: ");
			        	                            docID = sc.nextLine();
			        	                            for (Staff s:staffList) {
			        	                            	if (s.getID().equals(docID)) {
			        	                            		tttStaff = s;
			        	                            		tttDoc = (Doctor) tttStaff;
			        	                            		IDexists = true;
			        	                            	}
			        	                            }
			        	                            if(!IDexists) {
			        	                            	System.out.println("ID does not exist!");
			        	                            }
		        	                            } while(!IDexists);
		        	                           
    	                                        System.out.println("Viewing appointments for " + docID + " " + tttDoc.getName());
    	                                        apptamt = 0;
    	                						
    	                						for (Appointment a : appointmentList) {
    	                							if (a.getDoctorID().equals(docID)) {
    	                								System.out.println();
    	                								System.out.println("Appointment ID	: " + a.getID());
    	                								System.out.println("Date			: " + a.getDate());
    	                								System.out.println("Time 			: " + a.getTime());
    	                								System.out.println("Patient			: " + a.getPatient());
    	                								System.out.println("Status			: " + a.getStatus());
    	                								if(a.getOutcome()==null) {
    	                									System.out.println("Outcome			: N/A, Appointment not completed");
    	                								}
    	                								else {
    	                								System.out.println("Outcome			: " + a.getOutcome());
    	                								}
    	                								apptamt++;
    	                							}
    	                						}
    	                						
    	                						if (apptamt == 0) System.out.println("No appointment found!");
    	                						System.out.println();
    	                                        break;
		        	                            
		        	                        case 2:
		        	                        	IDexists = false;
		        	                        	System.out.println("---------Filter by patient----------");
		        	                            sc.nextLine();
		        	                            do {
			        	                            System.out.println("Enter patient ID: ");
			        	                            patID = sc.nextLine();
			        	                            for (Patient p:patientList) {
			        	                            	if (p.getID().equals(patID)) {
			        	                            		tttPat = p;
			        	                            		IDexists = true;
			        	                            	}
			        	                            }
			        	                            if(!IDexists) {
			        	                            	System.out.println("ID does not exist!");
			        	                            }
		        	                            } while(!IDexists);
		        	                           
    	                                        System.out.println("Viewing appointments for " + patID + " " + tttPat.getName());
    	                                        apptamt=0;
    	                						for (Appointment a : appointmentList) {
    	                							if (a.getPatientID().equals(patID)) { // list appointments that are in the future and under dr's name
    	                								System.out.println();
    	                								System.out.println("Appointment ID	: " + a.getID());
    	                								System.out.println("Date			: " + a.getDate());
    	                								System.out.println("Time 			: " + a.getTime());
    	                								System.out.println("Patient			: " + a.getPatient());
    	                								System.out.println("Status			: " + a.getStatus());
    	                								if(a.getOutcome()==null) {
    	                									System.out.println("Outcome			: N/A, Appointment not completed");
    	                								}
    	                								else {
    	                								System.out.println("Outcome			: " + a.getOutcome());
    	                								}
    	                								apptamt++;
    	                							}
    	                						}
    	                						
    	                						if (apptamt == 0) System.out.println("No appointment found!");
    	                						System.out.println();
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
    	                								if(a.getOutcome()==null) {
    	                									System.out.println("Outcome			: N/A, Appointment not completed");
    	                								}
    	                								else {
    	                								System.out.println("Outcome			: " + a.getOutcome());
    	                								}
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
	///////////////////////////////////EDIT HERE!!!!!////
		        	                
	
   	                            	System.out.println("Enter Appointment ID: ");
   	                            	sc.nextLine();
   	                            	String apptID = sc.nextLine();
   	                            	apptamt = 0;
	           						for (Appointment a : appointmentList) {
	           							if (a.getID().equals(apptID)) { // print current details of appointment to be edited
	           								
	           								String tempPatientID = null;
	           								String tempDoctorID = null;
	           								
	           								for (Patient tempPat : patientList) {
	           									if (tempPat.getName().equals(a.getPatient())){
	           										tempPatientID = tempPat.getID();
	           									}
	           								}
	           								
	           								for (Staff tempDoc : staffList) {
	           									if (tempDoc.getName().equals(a.getDoctor())){
	           										tempDoctorID = tempDoc.getID();
	           									}
	           								}
	           								
	           								System.out.println();
	           								System.out.println("Current Appointment Details:");
	           								System.out.println("==================================");
	           								System.out.println("(1) Patient ID			: " + tempPatientID);
	           								System.out.println("(2) Doctor ID			: " + tempDoctorID);
	           								System.out.println("(3) Appointment Status 	: " + a.getStatus());
	           								System.out.println("(4) Date				: " + a.getDate());
	           								System.out.println("(5) Time				: " + a.getTime());
	           								if(a.getStatus().equals("Confirmed") || a.getStatus().equals("Pending") || a.getStatus().equals("Cancelled")) {
	           									System.out.println("(6) Outcome Record		: N/A");
	           								}
	           								else {
	           									System.out.println("(6) Outcome Record		: " + a.getOutcome());
	           								}
	           								System.out.println("(7) Back");
	           								System.out.println("==================================");
	           								apptamt++;
	           								
	           								break;
	           							}
	           						}
	           							
	           						if (apptamt == 0) {
	           							System.out.println("No appointment found!");
	           							System.out.println();
	           						}
	           						
	           						else {
	           							for (Appointment a : appointmentList) {
		           							if (a.getID().equals(apptID)) {
			           							

			           							int manageAptChoice = 0;
			           							Patient tempPat = null;
			           							Staff tempStaff = null;
			           							boolean patExists = false;
			           							boolean staffExists = false;
			           							do {
			           								System.out.println("Choose option from (1)-(7) to edit: ");
				           							manageAptChoice = sc.nextInt();
			           								switch(manageAptChoice) {
			           								case 1:
			           									System.out.println("Current Patient ID: " + a.getPatientID() + ", Patient Name: " + a.getPatient());
			           									System.out.println("Enter new Patient ID: ");
			           									sc.nextLine();
			           									String newPatID = sc.nextLine();
			           									
			           									
			           									for(Patient p : patientList) {
			           										if (p.getID().equals(newPatID)){
			           											tempPat = p;
			           											patExists = true;
			           											break;
			           										}
			           									}
			           									if (patExists) {
			           										a.updatePatientID(newPatID);
			           										a.updatePatientName(tempPat.getName());
			           										
			           										System.out.println("Patient ID updated to " + newPatID + ", Patient Name automatically updated to " + a.getPatient());
			           									}
			           									break;
			           								case 2:
			           									Doctor newdoctor = null;
			           									Doctor olddoctor = null;
			           									System.out.println("Current Doctor ID: " + a.getDoctorID() + ", Doctor Name: " + a.getDoctor());
			           									System.out.println("Enter new Doctor ID: ");
			           									sc.nextLine();
			           									String newDocID = sc.nextLine();
			           									for(Staff s : staffList) {
			           										if (s.getID().equals(newDocID)){
			           											tempStaff = s;
			           											newdoctor = (Doctor)s;
			           											staffExists = true;
			           											break;
			           										}
			           									}
			           									newdoctor = (Doctor) staffList.get(sList.findStaffIndexID(newDocID));
			           									olddoctor = (Doctor) staffList.get(sList.findStaffIndexID(a.getDoctorID()));
			           									
			           									if (staffExists) {
			           										a.updateDoctorID(newDocID);
			           										a.updateDoctorName(tempStaff.getName());
			           										olddoctor.removeAppointment(a);
			           										newdoctor.addAppointment(a);
			           										
			           										System.out.println("Doctor ID updated to " + newDocID + ", Doctor Name automatically updated to " + a.getDoctor());
			           									}
			           									break;

			           								case 3:
			           									Doctor tempDoc = null;
			           									Staff tStaff = null;
			           									for(Staff s:staffList) {
			           										if (s.getID().equals(a.getDoctorID())){
			           											tStaff = s;
			           										}
			           									}
			           									tempDoc = (Doctor) tStaff;
			           									boolean isDefault = true;
				           									if(a.getStatus().equals("Cancelled")) {
				           										System.out.println("Current status: Cancelled");
				           										System.out.println("Enter new status of Appointment (Pending/Confirmed: ");
					           									String newStatusChoice = null;
					           									sc.nextLine();		
						           								do{
						           									newStatusChoice = sc.nextLine();
					           										switch(newStatusChoice){
						           									case "Pending":
						           										a.updateStatus(newStatusChoice);
						           									case "Cancelled":
						           										System.out.println("No change in status.");	
						           									case "Confirmed":
						           										a.updateStatus(newStatusChoice);
						           										tempDoc.addAppointment(a);
						           									default: System.out.println("Invalid input.");
						           									}
						           								}while(!isDefault);
				           									}
				           									
				           									else if(a.getStatus().equals("Confirmed")) {
				           										System.out.println("Current status: Confirmed");
				           										System.out.println("Enter new status of Appointment (Pending/Cancelled: ");
					           									String newStatusChoice = null;
					           									sc.nextLine();		
						           								do{
						           									newStatusChoice = sc.nextLine();
					           										switch(newStatusChoice){
						           									case "Pending":

						           										tempDoc.removeAppointment(a);
						           										a.updateStatus(newStatusChoice);
						           									case "Cancelled":
						           										a.updateStatus(newStatusChoice);
						           										tempDoc.removeAppointment(a);
						           									case "Confirmed":
						           										System.out.println("No change in status.");	
						           										
						           									default: System.out.println("Invalid input.");
						           									}
						           								}while(!isDefault);
				           									}
				           									
				           									else if(a.getStatus().equals("Pending")) {
				           										System.out.println("Current status: Pending");
				           										System.out.println("Enter new status of Appointment (Confirmed/Cancelled: ");
					           									String newStatusChoice = null;
					           									sc.nextLine();		
						           								do{
						           									newStatusChoice = sc.nextLine();
						           									switch(newStatusChoice){
						           									case "Pending":
	
						           										System.out.println("No change in status.");	
						           									case "Cancelled":
						           										System.out.println("No change in status.");	
						           										a.updateStatus(newStatusChoice);
						           									case "Confirmed":
						           										a.updateStatus(newStatusChoice);
						           										tempDoc.addAppointment(a);
						           									default: System.out.println("Invalid input.");
						           									}
						           								}while(!isDefault);
				           									}
				           									
				           								
			           									break;
			           								case 4:
			           									int tChoice, dChoice = 0;
			           									
			           									doc = (Doctor) staffList.get(sList.findStaffIndexID(a.getDoctorID()));
			           									pat = (Patient) patientList.get(pList.findPatientIndexID(a.getPatientID()));
			           									
			           									do {
			           										for (int i=0; i<7; i++) {
			           											System.out.format("%d. %s\n", i+1, doc.getSchedule()[i][1].getDate());
			           										}
			           										System.out.println("8. Back");
			           										System.out.println("Choose new date: ");
			           										dChoice = sc.nextInt();
			           										
			           										if (dChoice > 0 && dChoice < 8) {
			           											do {
			           												System.out.format("%s\n", doc.getSchedule()[dChoice-1][1].getDate());
			           												for(int j=0; j<7; j++) {
			           													String slotAvailability;
			           													if (doc.getSchedule()[dChoice-1][j].getAvail()) {
			           														slotAvailability = "Available";
			           													}
			           													else {
			           														slotAvailability = "Unavailable";
			           													}
			           													System.out.format("%d. %s\n", j+1, doc.getSchedule()[dChoice-1][j].getTime() + " : " + slotAvailability);
			           													}
			           												System.out.println((7+1) + ". Back");
			           												System.out.println("Choose time: ");
			           												tChoice = sc.nextInt();
			           												
			           												if (tChoice > 0 && tChoice < 7+1) {
			           													if (doc.getSchedule()[dChoice-1][tChoice-1].getAvail()) {
			           														Appointment appoint = new Appointment(pat, doc, doc.getSchedule()[dChoice-1][tChoice-1].getDateTime(), doc.getSchedule()[dChoice-1][tChoice-1].getTime());
			           														System.out.println("Appointment successfully booked with " + doc.getName() + " at " + doc.getSchedule()[dChoice-1][tChoice-1].getDate() + ", " + doc.getSchedule()[dChoice-1][tChoice-1].getTime());
			           														appoint.updateStatus("Pending");
			           														appointmentList.add(appoint);
			           														doc.addAppointment(appoint);
			           														doc.removeAppointment(a);
			           														a.updateStatus("Cancelled");
			           														
			           													}
			           													else {
			           														System.out.println("Slot is unavailable!");
			           													}
			           												}
			           												else if(tChoice > 7+1 || tChoice < 1){
			           													System.out.println("Invalid time! Please enter again.");
			           												}
			           												
			           											} while (tChoice > 7+1 || tChoice < 1);
			           										}
			           											
			           										else if (dChoice > 8 || dChoice < 1){
			           												System.out.println("Date invalid! Please enter again.");
			           										}
			           									} while (dChoice > 8 || dChoice < 1); 
			           									
			           									
			           									System.out.println();
			           									break;
			           								case 5:
			           									
			           									break;
			           								case 6:
			           									           									
			           									if(a.getStatus().equals("Confirmed") || a.getStatus().equals("Pending") || a.getStatus().equals("Cancelled")) {
				           									System.out.println("Appointment not completed, no outcome to update.");
				           								}
			           									else {
			           										System.out.println("Current outcome: " + a.getOutcome());
			           										System.out.println("Enter new outcome: ");
			           										String newOutcome = null;
			           										newOutcome = sc.nextLine();
			           										a.updateOutcome(newOutcome);
			           									}
			           									break;
			           								case 7:
			           									System.out.println("Case 7 entered. Going back...");
			           									break;
			           								default:
			           									break;
			           								}
			           							} while (manageAptChoice < 1 || manageAptChoice > 7);
			           							break;
		           							}
	           							}
	           						}
	           						
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
				int counting = 0;
				int patientChoice = 0;
				String docID = "";
				System.out.println("Hello " + patient.getName() + ", welcome to the patient menu");
				do {
					System.out.println("1. View Medical Records");
					System.out.println("2. Update Personal Information");
					System.out.println("3. View Available Appointment Slots");
					System.out.println("4. Schedule Appointment");
					System.out.println("5. Reschedule Appointment");
					System.out.println("6. Cancel Appointment");
					System.out.println("7. View Scheduled Appointments");
					System.out.println("8. View Past Appointment Records");
					System.out.println("9. Logout");
					patientChoice = sc.nextInt();
					
					switch(patientChoice) {
					case 1: // view med record
						System.out.println("Name		: " + patient.getName());
						System.out.println("DOB		: " + dateFormat.format(patient.getDOB()));
						System.out.println("Email		: " + patient.getEmail());
						System.out.println("Contact no.	: " + patient.getContactNum());
						System.out.println("Blood type	: " + patient.getBloodType());
						System.out.println("Previous appointment outcomes:");
						for (Appointment a : appointmentList) {
							if (patient.getName().equals(a.getPatient())) {
								System.out.println(a.getDate() + ": " + a.getOutcome());
							}
						}
						System.out.println();
						break;
						
					case 2: // update particular
						patient.updatePart();
						System.out.println();
						break;
						
					case 3: // view avail apt slots
						docSelect = 0;
						docAmount = 0;
						
						System.out.println("Select Doctor:");
						for (Staff s : staffList) {
							if (s.getRole().equals("Doctor")) {
								docAmount++;
								System.out.println(docAmount + ". " + s.getName());
							}	
						}
						
						do {
							System.out.println("Select doctor: ");
							docSelect = sc.nextInt();
							
							if (docSelect>docAmount) System.out.println("Invalid doctor! Please enter again.");
						} while (docSelect>docAmount);
						
						for (Staff s : staffList) {
							if (s.getRole().equals("Doctor")) {
								counting++;
								if (counting == docSelect) {
									docID = s.getID();
									break;
								}
							}
						}
						
						doc = (Doctor) staffList.get(sList.findStaffIndexID(docID));
						
						doc.getAvailSlots();
						
						System.out.println("==================================");
						break;
						
					case 4: // make apt
						docSelect = 0;
						docAmount = 0;
						int dChoice, tChoice;
						
						for (Staff s : staffList) {
							if (s.getRole().equals("Doctor")) {
								docAmount++;
								System.out.println(docAmount + ". " + s.getName());
							}
							
							
						}
						
						do {
							System.out.println("Select doctor: ");
							docSelect = sc.nextInt();
							
							if (docSelect>docAmount) System.out.println("Invalid doctor! Please enter again.");
						} while (docSelect>docAmount);
						
						for (Staff s : staffList) {
							if (s.getRole().equals("Doctor")) {
								counting++;
								if (counting == docSelect) {
									docID = s.getID();
									break;
								}
							}
						}
						
						doc = (Doctor) staffList.get(sList.findStaffIndexID(docID));
						
						do {
							for (int i=0; i<7; i++) {
								System.out.format("%d. %s\n", i+1, doc.getSchedule()[i][1].getDate());
							}
							System.out.println("8. Back");
							System.out.println("Choose date: ");
							dChoice = sc.nextInt();
							
							if (dChoice > 0 && dChoice < 8) {
								do {
									System.out.format("%s\n", doc.getSchedule()[dChoice-1][1].getDate());
									for(int j=0; j<7; j++) {
										String slotAvailability;
										if (doc.getSchedule()[dChoice-1][j].getAvail()) {
											slotAvailability = "Available";
										}
										else {
											slotAvailability = "Unavailable";
										}
										System.out.format("%d. %s\n", j+1, doc.getSchedule()[dChoice-1][j].getTime() + " : " + slotAvailability);
										}
									System.out.println((7+1) + ". Back");
									System.out.println("Choose time: ");
									tChoice = sc.nextInt();
									
									if (tChoice > 0 && tChoice < 7+1) {
										if (doc.getSchedule()[dChoice-1][tChoice-1].getAvail()) {
											Appointment appoint = new Appointment(patient, doc, doc.getSchedule()[dChoice-1][tChoice-1].getDateTime(), doc.getSchedule()[dChoice-1][tChoice-1].getTime());
											System.out.println("Appointment successfully booked with " + doc.getName() + " at " + doc.getSchedule()[dChoice-1][tChoice-1].getDate() + ", " + doc.getSchedule()[dChoice-1][tChoice-1].getTime());
											appoint.updateStatus("Pending");
											appointmentList.add(appoint);
											doc.addAppointment(appoint);
											
										}
										else {
											System.out.println("Slot is unavailable!");
										}
									}
									else if(tChoice > 7+1 || tChoice < 1){
										System.out.println("Invalid time! Please enter again.");
									}
									
								} while (tChoice > 7+1 || tChoice < 1);
							}
								
							else if (dChoice > 8 || dChoice < 1){
									System.out.println("Date invalid! Please enter again.");
							}
						} while (dChoice > 8 || dChoice < 1); 
						
						
						System.out.println();
						break;
						
					case 5: // reschedule apt
						aptAmount = 0;
						String aptIDSelect = null;
						
						for (Appointment a : appointmentList) {
							if (patient.getName().equals(a.getPatient()) && (a.getStatus().equals("Confirmed") || a.getStatus().equals("Pending"))) { 
								aptAmount++;
								System.out.println(a.getID() + ": (Doctor: " + a.getDoctor() + " )" + a.getDate() +  " " + a.getTime() + "| Status: " + a.getStatus()); 
							}
						}
						if (aptAmount != 0) {
							medAmount = 0;
							medSelect = 0;
							Doctor cancelledDoc = null;
							apt = null;
								
							do {					
								System.out.println("Enter full appointment ID to reschedule: ");
								aptAmount = 0;
								sc.nextLine();
								aptIDSelect = sc.nextLine();
								for (Appointment a : appointmentList) {
									if (a.getID().equals(aptIDSelect)) {
										apt = a;
										aptAmount++;
									}
								}
								
								if (aptAmount == 0) System.out.println("Invalid appointment! Please enter again.");
							} while (aptAmount == 0);
								
							
							
							//give option to reschedule to new avail slot
							System.out.println("Rescheduling appointment...... ");
							apt.updateStatus("Cancelled");
							for (Staff s : staffList) {
								if (apt.getDoctor().equals(s.getName())) {
									cancelledDoc = (Doctor) s;
								}
							}
							System.out.println("Appointment with " + apt.getDoctor() + " at " + apt.getDate() + " " + apt.getTime() + " successfully cancelled.");
							System.out.println("============================================");
							cancelledDoc.removeAppointment(apt);
							
							docSelect = 0;
							docAmount = 0;
							dChoice = 0; 
							tChoice = 0;
							
							for (Staff s : staffList) {
								if (s.getRole().equals("Doctor")) {
									docAmount++;
									System.out.println(docAmount + ". " + s.getName());
								}
								
								
							}
							
							do {
								System.out.println("Select doctor to reschedule to: ");
								docSelect = sc.nextInt();
								
								if (docSelect>docAmount) System.out.println("Invalid doctor! Please enter again.");
							} while (docSelect>docAmount);
							
							for (Staff s : staffList) {
								if (s.getRole().equals("Doctor")) {
									counting++;
									if (counting == docSelect) {
										docID = s.getID();
										break;
									}
								}
							}
							
							doc = (Doctor) staffList.get(sList.findStaffIndexID(docID));
							
							do {
								for (int i=0; i<7; i++) {
									System.out.format("%d. %s\n", i+1, doc.getSchedule()[i][1].getDate());
								}
								System.out.println("8. Back");
								System.out.println("Choose date: ");
								dChoice = sc.nextInt();
								
								if (dChoice > 0 && dChoice < 8) {
									do {
										System.out.format("%s\n", doc.getSchedule()[dChoice-1][1].getDate());
										for(int j=0; j<7; j++) {
											String slotAvailability;
											if (doc.getSchedule()[dChoice-1][j].getAvail()) {
												slotAvailability = "Available";
											}
											else {
												slotAvailability = "Unavailable";
											}
											System.out.format("%d. %s\n", j+1, doc.getSchedule()[dChoice-1][j].getTime() + " : " + slotAvailability);
											}
										System.out.println((7+1) + ". Back");
										System.out.println("Choose time: ");
										tChoice = sc.nextInt();
										
										if (tChoice > 0 && tChoice < 7+1) {
											if (doc.getSchedule()[dChoice-1][tChoice-1].getAvail()) {
												Appointment appoint = new Appointment(patient, doc, doc.getSchedule()[dChoice-1][tChoice-1].getDateTime(), doc.getSchedule()[dChoice-1][tChoice-1].getTime());
												System.out.println("Appointment successfully booked with " + doc.getName() + " at " + doc.getSchedule()[dChoice-1][tChoice-1].getDate() + ", " + doc.getSchedule()[dChoice-1][tChoice-1].getTime());
												appointmentList.add(appoint);
												appoint.updateStatus("Pending");
												doc.addAppointment(appoint);
											}
										}
										else if(tChoice > 7+1 || tChoice < 1){
											System.out.println("Invalid time! Please enter again.");
										}
										
									} while (tChoice > 7+1 || tChoice < 1);
								}
									
								else if (dChoice > 8 || dChoice < 1){
										System.out.println("Date invalid! Please enter again.");
								}
							} while (dChoice > 8 || dChoice < 1); 
							
							
							System.out.println();
							break;
				
						}
						
						else System.out.println("No scheduled appointment.");
						System.out.println();
						break;
						
					case 6: // cancel apt
						aptAmount = 0;
						aptIDSelect = null;
						
						for (Appointment a : appointmentList) {
							if (patient.getName().equals(a.getPatient()) && (a.getStatus().equals("Confirmed") || a.getStatus().equals("Pending"))) { 
								aptAmount++;
								System.out.println(a.getID() + ": (Doctor: " + a.getDoctor() + " )" + a.getDate() +  " " + a.getTime() + "| Status: " + a.getStatus());
							}
						}
						if (aptAmount != 0) {
							medAmount = 0;
							medSelect = 0;
							Doctor cancelledDoc = null;
							apt = null;
								
							do {					
								System.out.println("Enter full appointment ID to cancel: ");
								aptAmount = 0;
								sc.nextLine();
								aptIDSelect = sc.nextLine();
								for (Appointment a : appointmentList) {
									if (a.getID().equals(aptIDSelect)) {
										apt = a;
										aptAmount++;
									}
								}
								
								if (aptAmount == 0) System.out.println("Invalid appointment! Please enter again.");
							} while (aptAmount == 0);
								
							
							
							//give option to reschedule to new avail slot
							System.out.println("Cancelling appointment...... ");
							apt.updateStatus("Cancelled");
							for (Staff s : staffList) {
								if (apt.getDoctor().equals(s.getName())) {
									cancelledDoc = (Doctor) s;
								}
							}
							System.out.println("Appointment with " + apt.getDoctor() + " at " + apt.getDate() + " " + apt.getTime() + " successfully cancelled.");
							System.out.println("============================================");
							cancelledDoc.removeAppointment(apt);
						}
						else {
							System.out.println("No upcoming appointment to cancel.");
						}
						break;
						
					case 7: // view apt
						aptAmount = 0;
						
						System.out.println("Showing upcoming appointment status:");
						System.out.println("=========================================");
						for (Appointment a : appointmentList) {
							if (patient.getName().equals(a.getPatient()) && (a.getStatus().equals("Confirmed") || a.getStatus().equals("Pending"))) { 
								aptAmount++;
								System.out.println(a.getID() + ": (Doctor: " + a.getDoctor() + " )" + a.getDate() +  " " + a.getTime() + "| Status: " + a.getStatus());
							}
						}
						if (aptAmount == 0) {
							System.out.println("No upcoming appointments scheduled.");
							System.out.println();
						}
						break;
						
					case 8: // view apt record
						aptAmount = 0;
						
						System.out.println("Past appointments and statuses:");
						System.out.println("===================================================");
						for (Appointment a : appointmentList) {
							if (patient.getName().equals(a.getPatient()) && a.getStatus().equals("Completed")) { 
								aptAmount++;
								System.out.println(a.getID() + ": (Doctor: " + a.getDoctor() + " )" + a.getDate() +  " " + a.getTime() + "| Status: " + a.getStatus()); 
							}
						}
						if (aptAmount != 0) {
							
							apt = null;
								
							do {					
								System.out.println("Enter full appointment ID to display past outcome record: ");
								aptAmount = 0;
								sc.nextLine();
								aptIDSelect = sc.nextLine();
								for (Appointment a : appointmentList) {
									if (a.getID().equals(aptIDSelect)) {
										apt = a;
										aptAmount++;
									}
								}
								
								if (aptAmount == 0) System.out.println("Invalid appointment! Please enter again.");
							} while (aptAmount == 0);
							
							System.out.println("Appointment records of " + apt.getID());
							System.out.println("===================================================");
							System.out.println("Appointment ID		: " + apt.getID());
							System.out.println("Patient Name		: " + apt.getPatient());
							System.out.println("Doctor Name		: " + apt.getDoctor());
							System.out.println("Date and Time		: " + apt.getDate() + " " + apt.getTime());
							System.out.println("Status			: " + apt.getStatus());
							System.out.println("Outcome			: " + apt.getOutcome());
							System.out.println();
							System.out.println("Prescribed Medicine");
							System.out.println("--------------------------------------------------");
							apt.printPrescription();
							System.out.println();
						}
						
						else {
							System.out.println("No past appointment record!");
							System.out.println();
						}
						break;
						
					case 9:
						System.out.println("Logging out...");
						sc.nextLine();
						loggedIn = false;
						System.out.println();
						break;
						
					default:
						System.out.println("Invalid choice! Please enter again.");
						break;
					}
				} while (patientChoice != 9);
				
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