import java.util.Calendar;

public class Doctor extends Staff{
    private Schedule[][] schedule = new Schedule[7][7];    
    
    public Doctor(String userID, String name, String role, String gender, int age, String password) {
        super(userID, name, role, gender, age, password);
        
        for (int i=0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				schedule[i][j] = new Schedule(i, j);
			}
		}
    }

    public void houseMD(){ //TEST
        System.out.println(" HOUSE HOUSE HOUSE");
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
				for(int j=0; j<7; j++) {
					System.out.format("%d. %s\n", j+1, schedule[dChoice-1][j].getTime());
					}
				System.out.println("8. Back");
				System.out.println("Choose time: ");
				tChoice = sc.nextInt();
					
				if (tChoice > 0 && tChoice < 8) {
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
				else if(tChoice > 8 || tChoice < 1){
					System.out.println("Invalid time! Please enter again.");
				}
					
			} while (tChoice != 8);
		}
				
		else if (dChoice > 8 || dChoice < 1){
				System.out.println("Date invalid! Please enter again.");
		}
	} while (dChoice != 8); 
    }
	
	
    public void getAllAvail(){
	int dChoice;
	Scanner sc = new Scanner(System.in);
	do {
		for (int i=0; i<7; i++) {
			System.out.format("%d. %s\n", i+1, schedule[i][1].getDate());
		}
		System.out.println("8. Back");
		System.out.println("Choose date: ");
		dChoice = sc.nextInt();
			
		if (dChoice < 8 && dChoice > 0) {
			System.out.format("%s\n", schedule[dChoice-1][1].getDate());
			for(int j=0; j<7; j++) {
				System.out.format("%d. %s : ", j+1, schedule[dChoice-1][j].getTime());
				if (schedule[dChoice-1][j].getAvail()) System.out.println("Available");
				else System.out.println("Unavailable");
			}					
		}
				
		else if (dChoice > 8|| dChoice < 1){
			System.out.println("Date invalid! Please enter again.");
		}
	} while (dChoice != 8 );
    }

    public getAvailability(Calendar time){
	    for (int i=0; i<8; i++){
		    for (int j=0; j<8; j++){
			    if (time == schedule[i][j].getDateTime()) return schedule[i][j].getAvail;
		    }
	    }
    }
}
