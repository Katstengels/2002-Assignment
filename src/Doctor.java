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
				System.out.format("%d. %s", i+1, schedule[i][1].getDate());
			}
			System.out.println("Choose date: ");
			dChoice = sc.nextInt();
			
			if (dChoice < 8) {
				do {
					for(int j=0; j<7; j++) {
						System.out.format("%d. %s", j+1, schedule[dChoice-1][j].getTime());
						}
					System.out.println("Choose time: ");
					tChoice = sc.nextInt();
					
					if (tChoice < 8) {
						do {
							System.out.println("Indicate availability:");
							System.out.println("1. Available");
							System.out.println("2. Not available");
							avail = sc.nextInt();
						
							switch(avail) {
							case 1:
								schedule[dChoice-1][tChoice-1].changeAvail(true);
								break;
							case 2:
								schedule[dChoice-1][tChoice-1].changeAvail(false);
								break;
							default:
								System.out.println("Invalid choice! Please enter again.");
								break;
							}
						} while (avail>2);
					}
					else {
						System.out.println("Invalid time! Please enter again.");
					}
					
				} while (tChoice > 7);
			}
				
			else {
					System.out.println("Date invalid! Please enter again.");
			}
		} while (dChoice > 7); 
	}
    //Feel free to add all the appointments and other stuff here


}

