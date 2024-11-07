public class Medicine {
    private String name;
    private int currentStock;
    private int lowStockAlert;
    private int requestAmount;

    public Medicine(String name, int currentStock, int lowStockAlert) {
        this.name = name;
        this.currentStock = currentStock;
        this.lowStockAlert = lowStockAlert;
        this.requestAmount = 0; // Default to no replenishment requested
    }

    public String getName() {
        return name;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    public void setCurrentStock(int newStock) {
        this.currentStock = newStock;
    }
    public void setLowStockAlert(int newAlert) {
        this.lowStockAlert = newAlert;
    }
    public void setRequestAmount(int amount) {
        this.requestAmount = amount;
    }

    public void decrementStock(int amount) {
        if (amount <= currentStock) {
            currentStock -= amount;
        }
        else {
            System.out.println("Insufficient stock.");
        }
    }

    public boolean needsReplenishment() {
        return currentStock <= lowStockAlert;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Stock: " + currentStock +
                ", Low Stock Alert: " + lowStockAlert +
                ", Request Amount: " + requestAmount;
    }
}

