package src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Appointment {
    // Static list to hold all Appointment instances
    private static ArrayList<Appointment> appointments = new ArrayList<>();

    private String appointmentID;
    private String patientName;
    private String doctorName;
    private Patient patient;
    private Doctor doctor; // Store the Doctor object directly
    private Date date;
    private String status;
    private String outcome; 
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // Display time as 12:00
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM YYYY"); // Display date as "Friday, 01 Nov 2024"
    private ArrayList<PrescriptedMed> pMedList = new ArrayList<>();
    private boolean hasMedication = false;

    // Constructor
    public Appointment(Patient pat, Doctor doc, Date day) {
        this.appointmentID = generateID(); // Generate a unique appointment ID
        this.patientName = pat.getName();
        this.doctorName = doc.getName();
        this.date = day;
        this.status = "pending";
        pMedList.clear();
        appointments.add(this); // Add the new appointment to the static list
    }

     // Adjust constructor to accept and store Doctor object directly
    public Appointment(Patient pat, Doctor doc, Date day, String time) {
        this.appointmentID = generateID();
        this.patient = pat;
        this.doctor = doc; // Store the Doctor object
        this.date = day;
        this.status = "pending";
    }

    // Getter for Doctor
    public Doctor getDoctor() {
        return this.doctor;
    }

    // Generate a random appointment ID (can be customized)
    private String generateID() {
        return "Appt" + System.currentTimeMillis(); // Simple unique ID
    }
    
    // Static method to retrieve an appointment by its ID
    public static Appointment getAppointmentByID(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.appointmentID.equals(id)) {
                return appointment;
            }
        }
        return null; // Return null if no appointment is found with the given ID
    }

    // Getters
    public String getID() { return appointmentID; }
    public String getPatient() { return patientName; }
    public Patient getPatientObj() { return patient; }    
    public String getDoctorName() { return doctorName; }
    public String getDate() { return dateFormat.format(date); }
    public String getTime() { return timeFormat.format(date); }
    public Date getDateTime() { return date; }
    public String getStatus() { return status; }
    public String getOutcome() { return outcome; }
    public boolean isHasMedication(){ return hasMedication; }

    // Update methods
    protected void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void updateOutcome(String notes) {
        this.outcome = notes;
    }

    public void TrueHasMedication(){ hasMedication = true; }

    // Prescription methods
    public void addPrescript(String name, int amt){
        String med = name.substring(0, 1).toUpperCase() + name.substring(1);
        PrescriptedMed medicine = new PrescriptedMed(med, amt);
        pMedList.add(medicine);
    }

    public boolean removePrescript(String med) {
        int index = findPrescriptIndex(med);
        if (index != -1) {
            pMedList.remove(index);
            System.out.println("Prescription removed successfully.");
            return true;
        } else {
            System.out.println("Prescription " + med + " not found.");
            return false;
        }
    }

    private int findPrescriptIndex(String med) {
        for (int i = 0; i < pMedList.size(); i++) {
            if (pMedList.get(i).getMedName().equals(med)) {
                return i;
            }
        }
        return -1;
    }

    // Method to update appointment date and time
    public void updateDateTime(Date newDate) {
        this.date = newDate;
    }

    public void printPrescription(){
        addPrescript("Paracetamol",10); // TEMP
        addPrescript("Ibuprofen",20);   // TEMP

        for (PrescriptedMed n : pMedList) {
            String status = n.isMedIsFilled() ? "FULFILLED" : "NOT FULFILLED";
            System.out.printf("%-20s %-10s %-10s \n", n.getMedName(), n.getMedAmount(), status);
            System.out.println("--------------------------------------------------");
        }
    }
}
