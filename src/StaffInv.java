import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;

public class StaffInv {

    // Private constructor to prevent multiple instances
    public StaffInv(){
        this.staffList = importCSV_S();
    }

    //Static instance of the class
    private static StaffInv instance = null ;

    //Patient List
    private ArrayList<Staff> staffList;

    //Singleton pattern to ensure only 1 instance of data exist within entire app
    public static StaffInv getInstance() {
        if (instance == null) {
            instance = new StaffInv();
        }
        return instance;
    }

    public ArrayList<Staff> copyStaffList() {
        return staffList;
    }

    public ArrayList<Staff> importCSV_S() {
        String filePath = "resources/Staff_List.csv";

        ArrayList<Staff> allStaff = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(filePath));

            // Skip the header row
            if (sc.hasNextLine()) sc.nextLine();

            // Reading each line of the CSV
            while (sc.hasNextLine()) {
                String line = sc.nextLine();     // Read each line
                String[] values = line.split(","); // Split by comma

                //CSV structure
                String userID = values[0].trim(); //patientID = userID
                String name = values[1].trim();
                String role = values[2].trim();
                String gender = values[3].trim();
                int age = Integer.valueOf(values[4].trim());

                String password = "password";

                Staff staff = null;
                if (role.equals("Doctor")) {
                    System.out.println(" Doc here");
                    staff = new Doctor(userID, name, role, gender, age, password);
                } else if (role.equals("Pharmacist")) {
                    System.out.println(" Big Pharma");
                    staff = new Pharmacist(userID, name, role, gender, age, password);
                } else if (role.equals("Administrator")) {
                    System.out.println(" Waste of $");
                    staff = new Administrator(userID, name, role, gender, age, password);
                } else {
                    System.out.println(" PLEB here");
                    staff = new Staff(userID, name, role, gender, age, password);
                }
                allStaff.add(staff);
            }

            sc.close(); // Close scanner

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing quantity as integer.");
            e.printStackTrace();
        }
        return allStaff;
    }

    public void printList(){
        for (Staff s : staffList){
            System.out.println(s);
        }
        System.out.println();
    }

    //Find Patient Index within List, with UserID
    public int findStaffIndexID(String userID){
        int index;
        for(index = 0; index <60; index++)
        {
            if(staffList.get(index).getUserID().equals(userID)) break;
        }
        return (index>50 ? -1:index);
    }

}
