package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator extends Staff {

    private List<Appointment> appointments;
    private List<MedicineInv> inventory;
    private MedicineInv medicineInventory;

    public Administrator(String userID, String name, String role, String gender, int age, String password) {
        super(userID, name, role, gender, age, password);

        this.appointments = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.medicineInventory = MedicineInv.getInstance();
    }



    public MedicineInv getMedicineInventory() {
        return this.medicineInventory;
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

        // Create a new Medicine object
        Medicine newMedicine = new Medicine(medName, quantity, lowstockalertval);

        // Add the new medicine to the medicineInventory
        medicineInventory.addMedicine(newMedicine);

        System.out.println("[Medicine Name: " + medName + ", Quantity: " + quantity + ", Low Stock Level Alert: " + lowstockalertval + "] added successfully.");



    }

    public void removeMedicine() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the name of the medicine to remove
        System.out.println("Enter the name of the medicine to remove: ");
        String medName = scanner.nextLine();

        // Attempt to remove the medicine from the medicineInventory
        boolean success = medicineInventory.removeMedicine(medName);


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
