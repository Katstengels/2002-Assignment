
public class Inventory {
    
    protected String medicineName;
    protected int stockLevel;
    protected int lowStockAlert;
    
    public Inventory(String medname, int amount, int alert) {
        medicineName = medname;
        stockLevel = amount;
        lowStockAlert = alert;
    }
     
     public int viewStockLevels(){
         return stockLevel;
     }
    
    public void updateStock(String medname, int amount) {
        if (medicineName == medname){
            stockLevel += amount;
        }
    } 

}
