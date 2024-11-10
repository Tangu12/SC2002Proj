package Entity;

public class Medicine {
    private final String nameOfMedicine;
    private int currentStock;
    private int lowStockLevelAlert;
    private int requestAmount;

    // Constructor
    public Medicine(String nameOfMedicine, int currentStock, int lowStockLevelAlert) {
        this.nameOfMedicine = nameOfMedicine;
        this.currentStock = Math.max(currentStock, 0);
        this.lowStockLevelAlert = Math.max(lowStockLevelAlert, 0);
        this.requestAmount = 0;
    }

    public Medicine(String nameOfMedicine, int currentStock, int lowStockLevelAlert,int requestAmount) {
        this.nameOfMedicine = nameOfMedicine;
        this.currentStock = Math.max(currentStock, 0);
        this.lowStockLevelAlert = Math.max(lowStockLevelAlert, 0);
        this.requestAmount = Math.max(requestAmount, 0);
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

    // Setters with validation
    public boolean setCurrentStock(int newStock) {
        if (newStock >= 0) {
            this.currentStock = newStock;
            return true;
        } else {
            System.out.println("Error, current stock cannot be negative!");
            return false;
        }
    }

    public boolean setLowStockLevelAlert(int newAlertLevel) {
        if (newAlertLevel >= 0) {
            this.lowStockLevelAlert = newAlertLevel;
            return true;
        } else {
            System.out.println("Error, low level stock alert cannot be negative!");
            return false;
        }
    }

    public boolean setRequestAmount(int requestAmount) {
        if (requestAmount >= 0) {
            this.requestAmount = requestAmount;
            return true;
        } else {
            System.out.println("Error, request amount cannot be negative!");
            return false;
        }
    }

    // Methods
    public void incrementStock(int amount){
        currentStock += amount;
    }

    public boolean decrementStock(int amount) {
        if (amount <= currentStock && amount > 0) {
            currentStock -= amount;
            return true;
        } else {
            System.out.println("Error, insufficient stock available or invalid amount.");
            return false;
        }
    }

    public boolean needsReplenishment() {
        return currentStock <= lowStockLevelAlert;
    }
}
