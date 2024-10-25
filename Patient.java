import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient extends User {
    private String patientID;
    private String name;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String contactNum;
    private String bloodType;
    private List<Diagnosis> pastDiagnoses;
    private List<Treatment> treatments;
    private List<Appointment> scheduledAppointments;
    private List<Appointment> appointmentHistory;

    // Constructor
    public Patient(String userID, String password, String role, String patientID, String name, Date dateOfBirth, 
                   String gender, String email, String contactNum, String bloodType) {
        super(userID, password, role);
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.contactNum = contactNum;
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.scheduledAppointments = new ArrayList<>();
        this.appointmentHistory = new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public String getID() { return patientID; }
    public Date getDOB() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getBloodType() { return bloodType; }
    public String getEmail() { return email; }
    public String getContactNum() { return contactNum; }

    // Public Methods for Patient Operations
    public void updatePersonalInfo(String contactNum, String email) {
        this.contactNum = contactNum;
        this.email = email;
    }

    public void viewMedicalRecord() {
        // Display past diagnoses and treatments
        System.out.println("Medical Record:");
        System.out.println("Past Diagnoses: " + pastDiagnoses);
        System.out.println("Treatments: " + treatments);
    }

    protected void updateRecord(Diagnosis diagnosis, Treatment treatment) {
        // This method can be accessed by doctors
        pastDiagnoses.add(diagnosis);
        treatments.add(treatment);
    }

    public void updateContactInfo(String newContactInfo) {
        this.contactNum = newContactInfo;
    }

    public List<Appointment> viewAvailableSlots() {
        // Return available appointments (mock implementation)
        return new ArrayList<>(); // Replace with actual logic to fetch available slots
    }

    public boolean scheduleAppointment(Doctor doctor, Date date, String timeSlot) {
        // Schedule an appointment logic
        Appointment appointment = new Appointment(this, doctor, date, timeSlot);
        scheduledAppointments.add(appointment);
        return true; // Indicate successful scheduling
    }

    public boolean rescheduleAppointment(String appointmentID, Date newDate, String newTimeSlot) {
        // Logic to reschedule an appointment
        for (Appointment appointment : scheduledAppointments) {
            if (appointment.getID().equals(appointmentID)) {
                appointment.setDate(newDate);
                appointment.setTimeSlot(newTimeSlot);
                return true; // Indicate successful rescheduling
            }
        }
        return false; // Appointment not found
    }

    public boolean cancelAppointment(String appointmentID) {
        // Logic to cancel an appointment
        boolean removed = scheduledAppointments.removeIf(a -> a.getID().equals(appointmentID));
        if (removed) {
            // Optionally, move the appointment to appointmentHistory
            // Here you would add logic to retrieve the appointment from scheduledAppointments and add it to appointmentHistory
        }
        return removed; // Indicate if the appointment was successfully removed
    }

    public List<Appointment> viewScheduledAppointments() {
        return new ArrayList<>(scheduledAppointments); // Return a copy of the scheduled appointments
    }

    public List<Appointment> viewAppointmentHistory() {
        return new ArrayList<>(appointmentHistory); // Return a copy of the appointment history
    }
}
