import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationInventory {
    private List<Medicine> inventory;
    private static MedicationInventory instance;
    private static final String FILE_PATH = "program_files/MedicationInventory.csv";

    private MedicationInventory() {
        this.inventory = new ArrayList<>();
        loadInventoryFromFile();
    }

    public static MedicationInventory getInstance() {
        if (instance == null) {
            instance = new MedicationInventory();
        }
        return instance;
    }

    // Load inventory from CSV file
    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                int currentStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());
                inventory.add(new Medicine(name, currentStock, lowStockAlert));
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Save inventory to CSV file
    private void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("Medicine Name,Current Stock,Low Stock Alert,Request Amount");
            writer.newLine();

            for (Medicine medicine : inventory) {
                writer.write(medicine.getName() + "," +
                        medicine.getCurrentStock() + "," +
                        medicine.getLowStockAlert() + "," +
                        medicine.getRequestAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // View all medicines in the inventory
    public void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            for (Medicine medicine : inventory) {
                System.out.println(medicine);
            }
        }
    }

    // Check if a medicine exists in the inventory
    public boolean isMedicineInInventory(String name) {
        return findMedicineByName(name) != null;
    }

    // Helper method to find a medicine by its name
    private Medicine findMedicineByName(String name) {
        for (Medicine medicine : inventory) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }

    // Add or increment stock of a medicine
    public void addOrIncrementMedicine() {
        System.out.print("Enter medicine name: ");
        String name = InputScanner.sc.nextLine();

        if (isMedicineInInventory(name)) {
            System.out.print("Enter additional stock: ");
            int additionalStock = InputScanner.sc.nextInt();
            incrementStock(name, additionalStock);
        } else {
            System.out.print("Enter initial stock: ");
            int stock = InputScanner.sc.nextInt();
            System.out.print("Enter low stock alert level: ");
            int alertLevel = InputScanner.sc.nextInt();
            addNewMedicine(name, stock, alertLevel);
        }
        saveInventoryToFile(); // Save changes
    }

    // Add new medicine to the inventory
    public void addNewMedicine(String name, int stock, int alertLevel) {
        inventory.add(new Medicine(name, stock, alertLevel));
        System.out.println("Medicine added: " + name);
        saveInventoryToFile(); // Save changes
    }

    // Increment stock of an existing medicine
    public void incrementStock(String name, int additionalStock) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            int newStock = medicine.getCurrentStock() + additionalStock;
            medicine.setCurrentStock(newStock);
            System.out.println("Added " + additionalStock + " units to " + name + ". New stock: " + newStock);
            saveInventoryToFile(); // Save changes
        }
    }

    // Remove Medicine from inventory
    public void removeMedicine() {
        System.out.print("Enter the name of the medicine to remove: ");
        String name = InputScanner.sc.nextLine();
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            inventory.remove(medicine);
            System.out.println("Medicine removed: " + name);
            saveInventoryToFile(); // Save changes
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Update medicine stock and alert level
    public void updateMedicine() {
        System.out.print("Enter existing medicine name: ");
        String name = InputScanner.sc.nextLine();
        Medicine medicine = findMedicineByName(name);
        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("Enter new stock amount: ");
        int newStock = InputScanner.sc.nextInt();
        System.out.print("Enter new alert level: ");
        int newAlertLevel = InputScanner.sc.nextInt();
        medicine.setCurrentStock(newStock);
        medicine.setLowStockAlert(newAlertLevel);
        System.out.println("Updated " + name + ": New stock = " + newStock + ", New alert level = " + newAlertLevel);
        saveInventoryToFile(); // Save changes
    }

    // Use a specific amount of medicine
    public void useMedicine() {
        System.out.print("Enter medicine name: ");
        String name = InputScanner.sc.nextLine();
        Medicine medicine = findMedicineByName(name);
        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("Enter amount used: ");
        int amount = InputScanner.sc.nextInt();
        if (amount <= medicine.getCurrentStock()) {
            medicine.decrementStock(amount);
            System.out.println("Used " + amount + " units of " + name + ".");
            saveInventoryToFile(); // Save changes
        } else {
            System.out.println("Insufficient stock.");
        }
    }

    // Check for low stock in the inventory
    public void checkInventory() {
        boolean lowStockFound = false;
        for (Medicine medicine : inventory) {
            if (medicine.needsReplenishment()) {
                System.out.println("Low stock alert: " + medicine.getName());
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }

    // Process all pending replenishment requests
    public void processReplenishmentRequests() {
        boolean hasRequests = false;
        for (Medicine medicine : inventory) {
            if (medicine.getRequestAmount() > 0) {
                hasRequests = true;
                int newStock = medicine.getCurrentStock() + medicine.getRequestAmount();
                medicine.setCurrentStock(newStock);
                System.out.println("Processed replenishment for " + medicine.getName() + ". New stock: " + newStock);
                medicine.setRequestAmount(0); // Reset request amount
            }
        }
        if (!hasRequests) {
            System.out.println("No pending replenishment requests.");
        }
        saveInventoryToFile(); // Save changes
    }

    // Submit a replenishment request for a specific medicine
    public void submitReplenishmentRequest(String medicineName, int requestAmount) {
        Medicine medicine = findMedicineByName(medicineName);
        if (medicine != null) {
            medicine.setRequestAmount(requestAmount);
            System.out.println("Replenishment request of " + requestAmount + " units submitted for " + medicineName + ".");
            saveInventoryToFile(); // Save changes
        } else {
            System.out.println("Medicine not found in the inventory.");
        }
    }
}
