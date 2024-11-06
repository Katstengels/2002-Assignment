package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MedicineInv {

    // Private constructor to prevent multiple instances
    private MedicineInv() {
        // Initialize the inventory
        this.medicineList = importCSV_M();
    }

    // Static instance of the class
    private static MedicineInv instance = null;


    // Medicine inventory list
    private ArrayList<Medicine> medicineList;


    //Singleton pattern to ensure only 1 instance of data exist within entire application
    public static MedicineInv getInstance() {
        if (instance == null) {
            instance = new MedicineInv();
        }
        return instance;
    }
    public ArrayList<Medicine> copyMedicineInventory() {
        return medicineList;
    }

    public ArrayList<Medicine> importCSV_M() {
        String filePath = "resources/Medicine_List.csv";

        ArrayList<Medicine> medicines = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(filePath));

            // Skip the header row
            if (sc.hasNextLine()) sc.nextLine();

            // Reading each line of the CSV
            while (sc.hasNextLine()) {
                String line = sc.nextLine();     // Read each line
                String[] values = line.split(","); // Split by comma

                //CSV structure is name,quantity,lowStockAlert
                String name = values[0].trim();
                int quantity = Integer.parseInt(values[1].trim());
                int lowStockAlert = Integer.parseInt(values[2].trim());

                // Create a new Medicine object and add it to the list
                Medicine medicine = new Medicine(name,quantity,lowStockAlert);
                medicines.add(medicine);
            }

            sc.close(); // Close scanner

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing quantity as integer.");
            e.printStackTrace();
        }

        return medicines;
    }


    public int findMedicineIndex(String name){
        int index=0;
        for (Medicine medicine : medicineList)
        {
            if(medicine.getName().equals(name)) 
            	{
            	return index;
            	}
            index++;
        }
        return -1;
    }

    public void printList() {
        for (Medicine medicine : medicineList) {
            System.out.println(medicine);
        }
        System.out.println();
    }
    
    public ArrayList<Medicine> copyMedicineList() {
        return medicineList;
    }

    public int getQuanity(String name) {
        return medicineList.get(findMedicineIndex(name)).getQuantity();
    }

    public int getLowStockAlertAmount(String name) {
        return medicineList.get(findMedicineIndex(name)).getLowStockAlert();
    }

    public void plusStock(String name, int amount){
        System.out.println(amount + " has been added to " + name);
         medicineList.get(findMedicineIndex(name)).plusStock(amount);
    }
    public void minusStock(String name, int amount) {
        if (this.getQuanity(name) == 0) {
            System.out.println(name + " HAS BEEN DEPLETED");
        } else if ((this.getQuanity(name) - amount) < 0) {
            System.out.println("Not enough stock of " + name + " Please choose a lower amount ");
            ;
        } else {
            System.out.println(amount + " has been removed from " + name);
            medicineList.get(findMedicineIndex(name)).minusStock(amount);
            this.stockWarning(name);
        }
    }

    public void stockWarning(String name) {
        if (this.getQuanity(name) == 0) {
            System.out.println(name + " HAS BEEN DEPLETED");
            return;
        }
        if (this.getQuanity(name) <= this.getLowStockAlertAmount(name)) {
            System.out.println("ALERT: " + name + " is in short supply");
        }
    }
    
    //Medicine methods
    public void addMedicine(Medicine medicine) {
        if (medicine != null) {
        	medicineList.add(medicine);
            System.out.println("Medicine added successfully.");
        } else {
            System.out.println("Invalid medicine.");
        }
    }
    
    
    public boolean removeMedicine(String medName) {
        int index = findMedicineIndex(medName);

        if (index != -1) {
            medicineList.remove(index);
            System.out.println("Medicine removed successfully.");
            return true;
        } else {
            System.out.println("Medicine " + medName + " not found.");
            return false;
        }
    }
}
