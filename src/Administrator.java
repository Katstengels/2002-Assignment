import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Administrator extends User {
    private List<User> staffList;
    private List<Appointment> appointments;
    private List<Inventory> inventory;

    public Administrator(String userID, String name) {
        super(userID, "password", name, "Administrator");
        this.staffList = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    // Staff Management
    public void addStaff(User staff) {
        staffList.add(staff);
    }

    public void updateStaff(String userID, String newName, String newRole) {
        for (User user : staffList) {
            if (user.getUserID().equals(userID)) {
                user.setName(newName);
                user.setRole(newRole);
                break;
            }
        }
    }

    public void removeStaff(String userID) {
        staffList.removeIf(user -> user.getUserID().equals(userID));
    }

    // Filtering the staff list
    public List<User> filterStaff(String role, String gender, int minAge, int maxAge) {
        List<User> filteredList = new ArrayList<>();
        for (User user : staffList) {
            if ((role == null || user.getRole().equals(role))) {
                filteredList.add(user);
            }
        }
        return filteredList;
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

    // Inventory Management
    public void addInventory(String medName, int stock, int lowAlert) {
        inventory.add(new Inventory(medName, stock, lowAlert));
    }

    public void updateInventoryStock(String medName, int amount) {
        for (Inventory item : inventory) {
            if (item.medicineName.equals(medName)) {
                item.updateStock(medName, amount);
                System.out.println("Updated stock for " + medName + ": " + item.viewStockLevels());
                break;
            }
        }
    }

    public void approveReplenishmentRequest(String medName, int amount) {
        for (Inventory item : inventory) {
            if (item.medicineName.equals(medName) && item.stockLevel < item.lowStockAlert) {
                item.updateStock(medName, amount);
                System.out.println("Replenishment approved for " + medName);
                break;
            }
        }
    }
}
