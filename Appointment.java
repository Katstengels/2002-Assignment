import java.util.Date;

public class Appointment {
    private String appointmentID;
    private String patientName;
    private String doctorName;
    private Date date;
    private String timeSlot;
    private String status;
    private String outcome; 
    
    // Constructor
    public Appointment(Patient pat, Doctor doc, Date day, String time) {
        this.appointmentID = generateID(); // You can replace this with your own ID logic
        this.patientName = pat.getName();
        this.doctorName = doc.getName(); // Assuming Doctor has a getName() method
        this.date = day;
        this.timeSlot = time;
        this.status = "pending";
    }

    // Generate a random appointment ID (can be customized)
    private String generateID() {
        return "A" + System.currentTimeMillis(); // Simple unique ID
    }
    
    // Getters
    public String getID() { return appointmentID; }
    public String getPatient() { return patientName; }
    public String getDoctor() { return doctorName; }
    public Date getDate() { return date; }
    public String getTime() { return timeSlot; }
    public String getStatus() { return status; }
    public String getOutcome() { return outcome; }
    
    // Update Methods
    protected void updateStatus(String newstatus) {
        status = newstatus;
    }
    
    public void updateOutcome(String notes) {
        outcome = notes;
    }
}
