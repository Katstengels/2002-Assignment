package src;

import java.util.Scanner;
import java.util.Date;

class Doctor extends Staff{
	private final int MAX_APT = 7;		//Max no of appointment per day
	Scanner sc = new Scanner(System.in);
	private Schedule[][] schedule = new Schedule[7][MAX_APT];    
	public Doctor(String userID, String name, String role, String gender, int age, String password) {
		super(userID, name, role, gender, age, password);
	        
	    for (int i=0; i<7; i++) {
	    	for (int j = 0; j<7; j++) {
				schedule[i][j] = new Schedule(i, j);
			}
	    }
	}
	
	public Schedule[][] getSchedule(){
		return schedule;
	}
	
	public void setAvailability() {
		int dChoice, tChoice, avail;
		Scanner sc = new Scanner(System.in);
		do {
			for (int i=0; i<7; i++) {
				System.out.format("%d. %s\n", i+1, schedule[i][1].getDate());
			}
			System.out.println("8. Back");
			System.out.println("Choose date: ");
			dChoice = sc.nextInt();
			
			if (dChoice > 0 && dChoice < 8) {
				do {
					System.out.format("%s\n", schedule[dChoice-1][1].getDate());
					for(int j=0; j<MAX_APT; j++) {
						System.out.format("%d. %s\n", j+1, schedule[dChoice-1][j].getTime());
						}
					System.out.println((MAX_APT+1) + ". Back");
					System.out.println("Choose time: ");
					tChoice = sc.nextInt();
					
					if (tChoice > 0 && tChoice < MAX_APT+1) {
						do {
							System.out.println("Indicate availability:");
							System.out.println("1. Available");
							System.out.println("2. Not available");
							System.out.println("3. Back");
							avail = sc.nextInt();
						
							switch(avail) {
							case 1:
								schedule[dChoice-1][tChoice-1].changeAvail(true);
								System.out.format("Available on %s %s\n", schedule[dChoice-1][tChoice-1].getDate(), schedule[dChoice-1][tChoice-1].getTime());
								break;
							case 2:
								schedule[dChoice-1][tChoice-1].changeAvail(false);
								System.out.format("Unvailable on %s %s\n", schedule[dChoice-1][tChoice-1].getDate(), schedule[dChoice-1][tChoice-1].getTime());
								break;
							case 3:
								break;
							default:
								System.out.println("Invalid choice! Please enter again.");
								break;
							}
						} while (avail > 3 || avail < 1);
					}
					else if(tChoice > MAX_APT+1 || tChoice < 1){
						System.out.println("Invalid time! Please enter again.");
					}
					
				} while (tChoice != MAX_APT+1);
			}
				
			else if (dChoice > 8 || dChoice < 1){
					System.out.println("Date invalid! Please enter again.");
			}
		} while (dChoice != 8); 
	}
	
	
	public void getAllAvailability(boolean docAccess){
		int dChoice;
		do {
			for (int i=0; i<7; i++) {
				System.out.format("%d. %s\n", i+1, schedule[i][1].getDate());
			}
			System.out.println("8. Back");
			System.out.println("Choose date: ");
			dChoice = sc.nextInt();
			
			if (dChoice < 8 && dChoice > 0) {
				if (docAccess) {
					System.out.format("%s\n", schedule[dChoice-1][1].getDate());
					for(int j=0; j<MAX_APT; j++) {
						System.out.format("%d. %s : ", j+1, schedule[dChoice-1][j].getTime());
						if (schedule[dChoice-1][j].getAvail()) {
							if (schedule[dChoice-1][j].getAppointment() != null) {
								System.out.println(schedule[dChoice-1][j].getAppointment().getID() + " " + schedule[dChoice-1][j].getAppointment().getPatient());
							}
							else System.out.println("Available");
						}
						else System.out.println("Unavailable");
					}	
				}
				else {
					System.out.format("%s\n", schedule[dChoice-1][1].getDate());
					for(int j=0; j<MAX_APT; j++) {
						System.out.format("%d. %s : ", j+1, schedule[dChoice-1][j].getTime());
						if (schedule[dChoice-1][j].getAvail()) {
							System.out.println("Available");
						}
						else System.out.println("Unavailable");
					}
				}
				System.out.println();
			}
				
			else if (dChoice > 8|| dChoice < 1){
				System.out.println("Date invalid! Please enter again.");
			}
		} while (dChoice != 8 );
			
	}
	
	public void getAvailSlots() {
		int num = 0;
		System.out.println("Available slots: ");
		for (int i = 0; i<7; i++) {
			System.out.println(schedule[i][1].getDate() + ": ");
			for (int j = 0; j<MAX_APT; j++) {
				if (schedule[i][j].getAvail()) {
					num++;
					System.out.println(num + ". " + schedule[i][j].getTime());
				}
			}
		}
	}

				
	public boolean getAvailability(Date time){
	    for (int i=0; i<7; i++){
		    for (int j=0; j<MAX_APT; j++){
			    if (time == schedule[i][j].getDateTime()) {
			    	return schedule[i][j].getAvail();
			    }
		    }
	    }
	    return false;
  }
	
	public void addAppointment(Appointment a) {
		for (int i=0; i<7; i++){
		  for (int j=0; j<MAX_APT; j++){
			  if (a.getDateTime() == schedule[i][j].getDateTime()) {
			    schedule[i][j].addAppointment(a);
			  }
			    
		  }
		}
	}
}
	
