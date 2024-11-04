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
        int index;
        for(index = 0; index <60; index++)
        {
            if(medicineList.get(index).getName().equals(name)) break;
        }
        return (index>50 ? -1:index);
    }

    public void printList() {
        System.out.println("==================================================");
        System.out.printf("%-20s %-10s %-10s \n","Medication","Amount","Stock lvl");
        System.out.println("==================================================");
        String health = new String();
        for (Medicine medicine : medicineList) {
            if(medicine.getQuantity()<=medicine.getLowStockAlertAmt()) health = "LOW";
                else health = "OK";

            System.out.printf("%-20s %-10s %-10s \n",medicine.getName(),Integer.toString(medicine.getQuantity()), health);
            System.out.println("--------------------------------------------------");
        }
        System.out.println();
    }

    public int getQuanity(String name) {
        return medicineList.get(findMedicineIndex(name)).getQuantity();
    }

    public int getLowStockAlertAmount(String name) {
        return medicineList.get(findMedicineIndex(name)).getLowStockAlertAmt();
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

    public void autoRestock(){
        boolean trig = false;
        for (Medicine medicine : medicineList) {
            boolean restock ;
            if(medicine.getQuantity()<=medicine.getLowStockAlertAmt()) restock = true;
            else restock = false;

            if(restock) {
                medicine.plusStock(100);
                trig = true;
            }

        }
        if (trig) System.out.println("----------- All low stocks replenished ! ---------");
        else System.out.println("----------- !!! All stocks healthy !!! -----------");
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

    public void printLowStock() {

        for (Medicine medicine : medicineList) {
            stockWarning(medicine.getName());
        }

    }
}
