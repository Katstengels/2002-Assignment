package src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
  private ArrayList<PrescriptedMed> pMedList = new ArrayList<>();
  private boolean hasMedication = false;

    // Constructor
  public Appointment(Patient pat, Doctor doc, Date day, String time) {
    this.appointmentID = generateID(); // You can replace this with your own ID logic
    this.patientName = pat.getName();
    this.doctorName = doc.getName(); // Assuming Doctor has a getName() method
    this.date = day;
    this.status = "pending";
    pMedList.clear();
  }

    // Generate a random appointment ID (can be customized)
  private String generateID() {
    return "Appt" + System.currentTimeMillis(); // Simple unique ID
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
  public boolean isHasMedication(){ return hasMedication;}

  // Update Methods
  protected void updateStatus(String newstatus) {
    status = newstatus;
  }
    
  public void updateOutcome(String notes) {
    outcome = notes;
  }

  public void TrueHasMedication(){ hasMedication = true;};

    //Prescription Methods

  public void addPrescript(String name, int amt){
    String med = name.substring(0, 1).toUpperCase() + name.substring(1); //Sets to Prescript
    PrescriptedMed medicine = new PrescriptedMed(med,amt);
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

  private int findPrescriptIndex(String med){
    int index=0;
    for (PrescriptedMed medicine : pMedList)
    {
      if(medicine.getMedName().equals(med))
      {
        return index;
      }
      index++;
    }
    return -1;
  }

  public void printPrescription(){
    addPrescript("Paracetamol",10); //TEMP
    addPrescript("Ibuprofen",20);   //TEMP

    for(PrescriptedMed n : pMedList) {
      String status;
      if (n.isMedIsFilled()){
        status = "FULFILLED";
      }
        else status = "NOT FULFILLED";

      System.out.printf("%-20s %-10s %-10s \n",n.getMedName(),n.getMedAmount(),status);
      System.out.println("--------------------------------------------------");
    }

  }

}
