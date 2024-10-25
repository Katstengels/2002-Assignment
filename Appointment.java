package Hospital;
import java.util.Date;
import java.util.Calendar;

public class Appointment {
    
    private String appointmentID;
    private String patientName;
    private String doctorName;
    private Date date;
    private String timeSlot;
    private String status;
    private String outcome; 
    
    public String getID() {
    	return appointmentID;
    }
    
    public String getPatient() {
    	return patientName;
    }
    
    public String getDoctor() {
    	return doctorName;
    }
    
    public Date getDate() {
    	return date;
    }
    
    public String getTime() {
    	return timeSlot;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public String getOutcome() {
    	return outcome;
    }
    
    public Appointment(Patient pat, Date day, String time) {
        
        patient = pat;
        date = day;
        timeSlot = time;
        status = "pending";
        
    }
     
    protected void updateStatus (String newstatus) {
        status = newstatus;
    }
    
    public void updateOutcome(String notes) {
    	outcome = notes;
    }

}
