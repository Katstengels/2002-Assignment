package src

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Doctor extends Staff {
    private final int MAX_APT = 7; // Max number of appointments per day
    private static ArrayList<Doctor> doctors = new ArrayList<>(); // Static list of all doctors

    private String doctorID; // Unique ID for each doctor
    private Schedule[][] schedule = new Schedule[7][MAX_APT];    
    Scanner sc = new Scanner(System.in);

    // Constructor
    public Doctor(String userID, String name, String role, String gender, int age, String password) {
        super(userID, name, role, gender, age, password);
        this.doctorID = userID; // Assign doctorID
        doctors.add(this); // Add the new doctor to the static list

        // Initialize schedule
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < MAX_APT; j++) {
                schedule[i][j] = new Schedule(i, j);
            }
        }
    }

    // Static method to get a doctor by ID
    public static Doctor getDoctorByID(String doctorID) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorID().equals(doctorID)) {
                return doctor;
            }
        }
        return null; // Return null if not found
    }

    // Getter for doctorID
    public String getDoctorID() {
        return doctorID;
    }

    public void setAvailability() {
        int dChoice, tChoice, avail;
        do {
            // Display dates
            for (int i = 0; i < 7; i++) {
                System.out.format("%d. %s\n", i + 1, schedule[i][1].getDate());
            }
            System.out.println("8. Back");
            System.out.println("Choose date: ");
            dChoice = sc.nextInt();
            
            if (dChoice > 0 && dChoice < 8) {
                do {
                    // Display times
                    System.out.format("%s\n", schedule[dChoice - 1][1].getDate());
                    for (int j = 0; j < MAX_APT; j++) {
                        System.out.format("%d. %s\n", j + 1, schedule[dChoice - 1][j].getTime());
                    }
                    System.out.println((MAX_APT + 1) + ". Back");
                    System.out.println("Choose time: ");
                    tChoice = sc.nextInt();
                    
                    if (tChoice > 0 && tChoice < MAX_APT + 1) {
                        do {
                            System.out.println("Indicate availability:");
                            System.out.println("1. Available");
                            System.out.println("2. Not available");
                            System.out.println("3. Back");
                            avail = sc.nextInt();

                            switch (avail) {
                                case 1:
                                    schedule[dChoice - 1][tChoice - 1].changeAvail(true);
                                    System.out.format("Available on %s %s\n", schedule[dChoice - 1][tChoice - 1].getDate(), schedule[dChoice - 1][tChoice - 1].getTime());
                                    break;
                                case 2:
                                    schedule[dChoice - 1][tChoice - 1].changeAvail(false);
                                    System.out.format("Unavailable on %s %s\n", schedule[dChoice - 1][tChoice - 1].getDate(), schedule[dChoice - 1][tChoice - 1].getTime());
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Invalid choice! Please enter again.");
                                    break;
                            }
                        } while (avail > 3 || avail < 1);
                    } else if (tChoice > MAX_APT + 1 || tChoice < 1) {
                        System.out.println("Invalid time! Please enter again.");
                    }
                } while (tChoice != MAX_APT + 1);
            } else if (dChoice > 8 || dChoice < 1) {
                System.out.println("Date invalid! Please enter again.");
            }
        } while (dChoice != 8);
    }

    public void getAllAvailability() {
        int dChoice;
        do {
            for (int i = 0; i < 7; i++) {
                System.out.format("%d. %s\n", i + 1, schedule[i][1].getDate());
            }
            System.out.println("8. Back");
            System.out.println("Choose date: ");
            dChoice = sc.nextInt();
            
            if (dChoice < 8 && dChoice > 0) {
                System.out.format("%s\n", schedule[dChoice - 1][1].getDate());
                for (int j = 0; j < MAX_APT; j++) {
                    System.out.format("%d. %s : ", j + 1, schedule[dChoice - 1][j].getTime());
                    if (schedule[dChoice - 1][j].getAvail()) {
                        if (schedule[dChoice - 1][j].getAppointment() != null) {
                            System.out.println(schedule[dChoice - 1][j].getAppointment().getID() + " " + schedule[dChoice - 1][j].getAppointment().getPatient());
                        } else {
                            System.out.println("Available");
                        }
                    } else {
                        System.out.println("Unavailable");
                    }
                }
                System.out.println();
            } else if (dChoice > 8 || dChoice < 1) {
                System.out.println("Date invalid! Please enter again.");
            }
        } while (dChoice != 8);
    }

    public void getAvailSlots() {
        int num = 0;
        System.out.println("Available slots: ");
        for (int i = 0; i < 7; i++) {
            System.out.println(schedule[i][1].getDate() + ": ");
            for (int j = 0; j < MAX_APT; j++) {
                if (schedule[i][j].getAvail()) {
                    num++;
                    System.out.println(num + ". " + schedule[i][j].getTime());
                }
            }
        }
    }

    public boolean getAvailability(Date time) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < MAX_APT; j++) {
                if (time.equals(schedule[i][j].getDateTime())) {
                    return schedule[i][j].getAvail();
                }
            }
        }
        return false;
    }

    public void addAppointment(Appointment a) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < MAX_APT; j++) {
                if (a.getDateTime().equals(schedule[i][j].getDateTime()) && schedule[i][j].getAvail()) {
                    schedule[i][j].addAppointment(a);
                    schedule[i][j].changeAvail(false); // Mark as unavailable once scheduled
                }
            }
        }
    }
}
