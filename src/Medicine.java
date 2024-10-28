public class Medicine {
    private String nameOfMedicine;
    private int currentStock;
    private int lowStockLevelAlert;
    private int requestAmount;

    // Constructor
    public Medicine(String nameOfMedicine, int currentStock, int lowStockLevelAlert) {
        this.nameOfMedicine = nameOfMedicine;
        this.currentStock = currentStock;
        this.lowStockLevelAlert = lowStockLevelAlert;
        this.requestAmount = 0;
    }

    // Getters
    public String getNameOfMedicine() {
        return nameOfMedicine;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getLowStockLevelAlert() {
        return lowStockLevelAlert;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    // Setters
    public void setCurrentStock(int newStock) {
        this.currentStock = newStock;
    }

    public void setLowStockLevelAlert(int newAlertLevel) {
        this.lowStockLevelAlert = newAlertLevel;
    }

    public void setRequestAmount(int requestAmount) {
        this.requestAmount = requestAmount;
    }

    // Methods
    public void decrementStock(int amount) {
        if (amount <= currentStock) {
            currentStock -= amount;
        } else {
            System.out.println("Insufficient stock available.");
        }
    }

    public boolean needsReplenishment() {
        return currentStock <= lowStockLevelAlert;
    }
}
