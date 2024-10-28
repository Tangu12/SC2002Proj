import java.util.ArrayList;

public class MedicationInventory {
    private ArrayList<Medicine> inventory;

    // Constructor
    public MedicationInventory() {
        this.inventory = new ArrayList<>();
    }

    // Add a new medicine to the inventory
    public void addMedicine(Medicine medicine) {
        inventory.add(medicine);
        System.out.println("Medicine added: " + medicine.getNameOfMedicine());
    }

    // Get the entire inventory
    public ArrayList<Medicine> getInventory() {
        return inventory;
    }

    // Check inventory for low stock levels
    public void checkInventory() {
        boolean lowStockFound = false;
        for (Medicine medicine : inventory) {
            if (medicine.needsReplenishment()) {
                System.out.println("Low stock alert for: " + medicine.getNameOfMedicine());
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }

    // Submit a replenishment request for a specific medicine
    public void submitReplenishmentRequest(String medicineName, int requestAmount) {
        Medicine medicine = findMedicineByName(medicineName);
        if (medicine != null) {
            medicine.setRequestAmount(requestAmount);
            System.out.println("Replenishment request submitted for " + medicineName + ": " + requestAmount + " units.");
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    // Update inventory after using medicine
    public boolean updateInventory(String medicineName, int amountUsed) {
        Medicine medicine = findMedicineByName(medicineName);
        if (medicine != null) {
            medicine.decrementStock(amountUsed);
            checkInventory();
            return true;
        }
        return false;
    }

    // View inventory details
    public void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is currently empty.");
        } else {
            System.out.println("Current Medicine Inventory:");
            for (Medicine medicine : inventory) {
                System.out.println("Name: " + medicine.getNameOfMedicine() +
                        ", Stock: " + medicine.getCurrentStock() +
                        ", Low Stock Alert: " + medicine.getLowStockLevelAlert() +
                        ", Request Amount: " + medicine.getRequestAmount());
            }
        }
    }

    // Helper method to find a medicine by name
    private Medicine findMedicineByName(String medicineName) {
        for (Medicine medicine : inventory) {
            if (medicine.getNameOfMedicine().equalsIgnoreCase(medicineName)) {
                return medicine;
            }
        }
        return null;
    }
}
