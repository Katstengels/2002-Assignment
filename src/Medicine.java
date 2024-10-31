class Medicine {
    private String name;
    private int quantity;
    private int lowStockAlert;

    // Constructor
    public Medicine(String name, int quantity, int alert) {
        this.name = name;
        this.quantity = quantity;
        this.lowStockAlert = alert;
    }

    // Getters and Setters (optional, for accessing fields later)
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    @Override
    public String toString() {
        return "Medicine{name='" + name + "', quantity= " + quantity + " , stock alert: " + lowStockAlert + "}";
    }



    public void plusStock(int amount) {
        quantity += amount;
    }

    public void minusStock(int amount) {
        quantity -= amount;
    }
}