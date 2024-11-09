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
    public void setCurrentStock(int newStock) {
        if (newStock >= 0) {
            this.currentStock = newStock;
        } else {
            System.out.println("Error, current stock cannot be negative!");
        }
    }

    public void setLowStockLevelAlert(int newAlertLevel) {
        if (newAlertLevel >= 0) {
            this.lowStockLevelAlert = newAlertLevel;
        } else {
            System.out.println("Error, low level stock alert cannot be negative!");
        }
    }

    public void setRequestAmount(int requestAmount) {
        if (requestAmount >= 0) {
            this.requestAmount = requestAmount;
        } else {
            System.out.println("Error, request amount cannot be negative!");
        }
    }

    // Methods
    public boolean decrementStock(int amount) {
        if (amount <= currentStock && amount > 0) {
            currentStock -= amount;
            return true;
        } else {
            System.out.println("Error, Insufficient stock available or invalid amount.");
            return false;
        }
    }

    public boolean needsReplenishment() {
        return currentStock <= lowStockLevelAlert;
    }
}
