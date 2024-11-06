package src;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
  private String appointmentID;
  private String patientName;
  private String doctorName;
  private Date date;
  private String status;
  private String outcome; 
  private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); //diaplay time from Date data in the form 12:00
	private SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM YYYY"); // display date from Date data in the form "Friday, 01 Nov 2024"
    
    // Constructor
  public Appointment(Patient pat, Doctor doc, Date day, String time) {
    this.appointmentID = generateID(); // You can replace this with your own ID logic
    this.patientName = pat.getName();
    this.doctorName = doc.getName(); // Assuming Doctor has a getName() method
    this.date = day;
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
  public String getDate() { return dateFormat.format(date.getTime());	}
	public String getTime() { return timeFormat.format(date.getTime());	}
	public Date getDateTime() {	return date; }
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
