package Entity;

/**
 * {@code Medicine} Class which stores information about each {@code Medicine}
 */
public class Medicine {
    private final String nameOfMedicine;
    private int currentStock;
    private int lowStockLevelAlert;
    private int requestAmount;

    /**
     * Constructor for a {@code Medicine}
     * @param nameOfMedicine
     * @param currentStock
     * @param lowStockLevelAlert
     */
    public Medicine(String nameOfMedicine, int currentStock, int lowStockLevelAlert) {
        this.nameOfMedicine = nameOfMedicine;
        this.currentStock = Math.max(currentStock, 0);
        this.lowStockLevelAlert = Math.max(lowStockLevelAlert, 0);
        this.requestAmount = 0;
    }

    /**
     * Constructor for a {@code Medicine}
     * @param nameOfMedicine
     * @param currentStock
     * @param lowStockLevelAlert
     * @param requestAmount
     */
    public Medicine(String nameOfMedicine, int currentStock, int lowStockLevelAlert,int requestAmount) {
        this.nameOfMedicine = nameOfMedicine;
        this.currentStock = Math.max(currentStock, 0);
        this.lowStockLevelAlert = Math.max(lowStockLevelAlert, 0);
        this.requestAmount = Math.max(requestAmount, 0);
    }

    /**
     * The getter method for the name of a {@code Medicine}
     * @return The name of a {@code Medicine}
     */
    public String getNameOfMedicine() {
        return nameOfMedicine;
    }

    /**
     * The getter method for the current stock of a {@code Medicine}
     * @return
     */
    public int getCurrentStock() {
        return currentStock;
    }

    /**
     * The getter method for the low stock level alert of a {@code Medicine}
     * @return
     */
    public int getLowStockLevelAlert() {
        return lowStockLevelAlert;
    }

    /**
     * The getter method for request of a {@code Medicine}
     * @return
     */
    public int getRequestAmount() {
        return requestAmount;
    }

    /**
     * The setter method for the current stock of a {@code Medicine}
     * @param newStock
     * @return
     */
    public boolean setCurrentStock(int newStock) {
        if (newStock >= 0) {
            this.currentStock = newStock;
            return true;
        } else {
            System.out.println("Error, current stock cannot be negative!");
            return false;
        }
    }

    /**
     * The setter method for the low stock level alert of a {@code Medicine}
     * @param newAlertLevel
     * @return
     */
    public boolean setLowStockLevelAlert(int newAlertLevel) {
        if (newAlertLevel >= 0) {
            this.lowStockLevelAlert = newAlertLevel;
            return true;
        } else {
            System.out.println("Error, low level stock alert cannot be negative!");
            return false;
        }
    }

    /**
     * The setter method for the request amount of a {@code Medicine}
     * @param requestAmount
     * @return
     */
    public boolean setRequestAmount(int requestAmount) {
        if (requestAmount >= 0) {
            this.requestAmount = requestAmount;
            return true;
        } else {
            System.out.println("Error, request amount cannot be negative!");
            return false;
        }
    }

    /**
     * Increments the stock of a {@code Medicine} by the input amount
     * @param amount
     */
    public void incrementStock(int amount){
        currentStock += amount;
    }

    /**
     * Decrements the stock of a {@code Medicine} by the input amount
     * @param amount
     * @return
     */
    public boolean decrementStock(int amount) {
        if (amount <= currentStock && amount > 0) {
            currentStock -= amount;
            return true;
        } else {
            System.out.println("Error, insufficient stock available or invalid amount.");
            return false;
        }
    }

    /**
     * Checks if a {@code Medicine} needs replenishment
     * @return True if the current stock of a {@code Medicine} is lower than its low stock level alert and False otherwise
     */
    public boolean needsReplenishment() {
        return currentStock <= lowStockLevelAlert;
    }
}
