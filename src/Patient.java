package src;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Patient extends User {
    private String name;
    private Date dateOfBirth;
    private String gender;
    private String contactNumber;
    private String emailAddress;
    private String bloodType;
    private ArrayList<String> pastDiagnoses;
    private ArrayList<String> treatments;
    private ArrayList<Appointment> appointments;

    Scanner sc = new Scanner(System.in);

    // Constructor
    public Patient(String userID, String password, String name, Date dob, String gender, String contact, String email, String bloodType) {
        super(userID, password, "Patient");
        this.name = name;
        this.dateOfBirth = dob;
        this.gender = gender;
        this.contactNumber = contact;
        this.emailAddress = email;
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public String getID() {
        return super.getUserID(); // Assuming userID is stored in the parent User class
    }

    public String getName() {
        return name;
    }

    public Date getDOB() {
        return dateOfBirth;
    }

    public String getEmail() {
        return emailAddress;
    }

    public String getContactNum() {
        return contactNumber;
    }

    public String getBloodType() {
        return bloodType;
    }


    // Access patient medical records
    public void viewMedicalRecord() {
        System.out.println("Patient ID: " + getUserID());
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Contact: " + contactNumber);
        System.out.println("Email: " + emailAddress);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Past Diagnoses: " + String.join(", ", pastDiagnoses));
        System.out.println("Treatments: " + String.join(", ", treatments));
    }

    // Update non-medical personal information
    public void updatePersonalInfo() {
        System.out.println("Update Personal Information:");
        System.out.println("1. Update Contact Number");
        System.out.println("2. Update Email Address");
        System.out.print("Choose an option: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                System.out.print("Enter new contact number: ");
                contactNumber = sc.nextLine();
                System.out.println("Contact number updated.");
                break;
            case 2:
                System.out.print("Enter new email address: ");
                emailAddress = sc.nextLine();
                System.out.println("Email address updated.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // View available appointment slots
    public void viewAvailableSlots(Doctor doctor) {
        doctor.getAvailSlots();
    }

    // Schedule an appointment
    public void scheduleAppointment(Doctor doctor, Date date) {
        if (doctor.getAvailability(date)) {
            Appointment newAppointment = new Appointment(this, doctor, date, "Scheduled");
            doctor.addAppointment(newAppointment);
            appointments.add(newAppointment);
            System.out.println("Appointment scheduled successfully.");
        } else {
            System.out.println("Selected time is unavailable.");
        }
    }

    // Reschedule an appointment
    public void rescheduleAppointment(String appointmentID, Doctor doctor, Date newDate) {
        for (Appointment appt : appointments) {
            if (appt.getID().equals(appointmentID)) {
                cancelAppointment(appt);
                scheduleAppointment(doctor, newDate);
                System.out.println("Appointment rescheduled successfully.");
                return;
            }
        }
        System.out.println("Appointment not found.");
    }

    // Cancel an appointment
    public void cancelAppointment(Appointment appointment) {
        if (appointments.contains(appointment)) {
            appointments.remove(appointment);
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // View scheduled appointments
    public void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments:");
        for (Appointment appt : appointments) {
            System.out.println("Appointment ID: " + appt.getID() + " with Dr. " + appt.getDoctor() + " on " + appt.getDate());
        }
    }

    // View past appointment outcomes
    public void viewPastAppointmentOutcomes() {
        System.out.println("Past Appointment Outcomes:");
        for (Appointment appt : appointments) {
            if (appt.getStatus().equals("completed")) {
                System.out.println("Appointment ID: " + appt.getID() + " - Outcome: " + appt.getOutcome());
            }
        }
    }
}
