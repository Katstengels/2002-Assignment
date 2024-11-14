package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator extends User {
    private StaffInv staffInventory;
    private List<Appointment> appointments;
    private List<MedicineInv> inventory;
    private MedicineInv medicineInventory;

    public Administrator(String userID, String password, String role) {
        super(userID, password, role);
        this.staffInventory = StaffInv.getInstance();
        this.appointments = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.medicineInventory = MedicineInv.getInstance();
    }

    public MedicineInv getMedicineInventory() {
        return this.medicineInventory;
    }



    // Staff Management
    public void addStaff() {
        Scanner scanner = new Scanner(System.in);
        boolean duplicateindicator = false;
        String userID = "null";
        do {
            duplicateindicator = false;
            System.out.print("Enter User ID: ");
            userID = scanner.nextLine();
            for (Staff staff : staffInventory.copyStaffList()) {
                if (staff.getID().equalsIgnoreCase(userID)) {
                    System.out.println("User ID taken! Please select another.");
                    duplicateindicator = true;
                    break;
                }
            }
        } while (duplicateindicator);


        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Role: ");
        String role = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        // Clear the scanner buffer
        scanner.nextLine();

        Staff newStaff = null;
        //ADD SWITCH STATEMENT TO CREATE diff staff objects

        switch(role) {
            case "Doctor":
                newStaff = new Doctor(userID, name, role, gender, age, "password");
                break;
            case "Pharmacist":
                newStaff = new Pharmacist(userID, name, role, gender, age, "password");
                break;

        }



        // Add the new staff member to the staffInventory
        staffInventory.addStaff(newStaff);

        System.out.println("[" + userID + ", " + name + ", " + role + ", " + gender + ", " + age + " years old]" + " added successfully.");



    }

    public void updateStaffInv() {
        staffInventory.updateCSV();
    }

    public void removeStaff() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the User ID of the staff member to remove
        System.out.print("Enter the User ID of the staff member to remove: ");
        String userID = scanner.nextLine();

        // Attempt to remove the staff member from the staffInventory
        boolean success = staffInventory.removeStaff(userID);

        // Optionally, update the CSV file if the staff member was removed
        if (success) {
            System.out.print("User ID " + userID + " removed.");
        }

        else {
            System.out.print("User ID " + userID + " does not exist.");
            // Close the scanner
        }
    }

    // Filtering the staff list
    public void filterStaff(int choice) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to choose a filter criterion

        // Filter based on the chosen criterion
        ArrayList<Staff> filteredStaff = new ArrayList<>();
        do {
            switch (choice) {
                case 1:
                    System.out.print("Enter the role to filter by (e.g., Doctor, Nurse): ");
                    String role = scanner.nextLine();
                    for (Staff staff : staffInventory.copyStaffList()) {
                        if (staff.getRole().equalsIgnoreCase(role)) {
                            filteredStaff.add(staff);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter the gender to filter by (e.g., Male, Female): ");
                    String gender = scanner.nextLine();
                    for (Staff staff : staffInventory.copyStaffList()) {
                        if (staff.getGender().equalsIgnoreCase(gender)) {
                            filteredStaff.add(staff);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter the minimum age to filter by: ");
                    int minAge = scanner.nextInt();
                    System.out.print("Enter the maximum age to filter by: ");
                    int maxAge = scanner.nextInt();
                    for (Staff staff : staffInventory.copyStaffList()) {
                        if (staff.getAge() >= minAge && staff.getAge() <= maxAge) {
                            filteredStaff.add(staff);
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    choice = 4;
            }
        } while (choice == 4);

        if (filteredStaff.isEmpty()) {
            System.out.println("No staff members found matching the criteria.");
        } else {
            System.out.println("Filtered Staff List:");
            for (Staff staff : filteredStaff) {
                System.out.println(staff);
            }
        }
    }


    // Appointment Management
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void updateAppointmentStatus(String appointmentID, String newStatus) {
        for (Appointment appointment : appointments) {
            if (appointment.getID().equals(appointmentID)) {
                appointment.updateStatus(newStatus);
                break;
            }
        }
    }

    public void viewAppointmentDetails(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getID().equals(appointmentID)) {
                System.out.println("Appointment ID: " + appointment.getID());
                System.out.println("Patient: " + appointment.getPatient());
                System.out.println("Doctor: " + appointment.getDoctor());
                System.out.println("Date: " + appointment.getDate());
                System.out.println("Time Slot: " + appointment.getTime());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println("Outcome: " + appointment.getOutcome());
                break;
            }
        }
    }

    //Medicine Inventory Management
    public void addMedicine() {
        Scanner scanner = new Scanner(System.in);
        boolean duplicateindicator = false;
        String medName = "null";
        do {
            duplicateindicator = false;
            System.out.print("Enter Medicine Name: ");
            medName = scanner.nextLine();
            for (Medicine medicine : medicineInventory.copyMedicineList()) {
                if (medicine.getName().equalsIgnoreCase(medName)) {
                    System.out.println("Medicine exists! Re-enter medicine name.");
                    duplicateindicator = true;
                    break;
                }
            }
        } while (duplicateindicator);


        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter Low Stock Alert Value: ");
        int lowstockalertval = scanner.nextInt();


        // Clear the scanner buffer
        scanner.nextLine();

        // Create a new Staff object
        Medicine newMedicine = new Medicine(medName, quantity, lowstockalertval);

        // Add the new staff member to the staffInventory
        medicineInventory.addMedicine(newMedicine);

        System.out.println("[Medicine Name: " + medName + ", Quantity: " + quantity + ", Low Stock Level Alert: " + lowstockalertval + "] added successfully.");



    }

    public void removeMedicine() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the User ID of the staff member to remove
        System.out.println("Enter the name of the medicine to remove: ");
        String medName = scanner.nextLine();

        // Attempt to remove the staff member from the staffInventory
        boolean success = medicineInventory.removeMedicine(medName);

        // Optionally, update the CSV file if the staff member was removed
        if (success) {
            System.out.println("Medicine " + medName + " removed.");
        }

        else {
            System.out.println("Medicine " + medName + " does not exist.");
            // Close the scanner

        }


    }
    public void plusStock(String medName, int medAmount) {
        medicineInventory.plusStock(medName, medAmount);

    }
}
