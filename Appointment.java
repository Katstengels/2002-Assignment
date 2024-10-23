import java.util.Date;
import java.util.Calendar;

public class Appointment {
    
    protected String appointmentID;
    protected Patient patient;
    protected Date date;
    protected String timeSlot;
    protected String status;
    protected String outcome; 
    
    public Appointment(Patient pat, Date day, String time) {
        
        patient = pat;
        Date = day;
        timeSlot = time;
        status = "pending";
        
    }
     
    protected void updateStatus (String newstatus) {
        status = newstatus;
    }

}
