import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MedicationInventory {
    private static List<Medicine> inventory = new ArrayList<>();
    private static final String FILE_PATH = "program_files/MedicationInventory.csv";

    // Load inventory from CSV file
    public static void loadInventoryFromFile() {
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
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // View all medicines in the inventory
    public void viewInventory() {
    	int i = 1;
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            System.out.println("+" + "-".repeat(3) + "+"
    				+ "-".repeat(30) + "+"
    				+ "-".repeat(15));

    		System.out.println("|" + formatCell("No.", 3)
    				+ "|" + formatCell("Medicine Name", 30)
    				+ "|" + formatCell("Current Stock", 15) + "|");

    		System.out.println("+" + "-".repeat(3) + "+"
    				+ "-".repeat(30) + "+"
    				+ "-".repeat(15) + "+");
            for (Medicine medicine : inventory) {
                //System.out.println(i + ". " + medicine);
                System.out.println("|" + formatCell(String.valueOf(i), 3)
						+ "|" + formatCell(medicine.getName(), 30)
						+ "|" + formatCell(String.valueOf(medicine.getCurrentStock()), 15) + "|");
				System.out.println("+" + "-".repeat(3) + "+"
						+ "-".repeat(30) + "+"
						+ "-".repeat(15) + "+");
				i++;
            }
        }
    }

    // Check if a medicine exists in the inventory
    public boolean isMedicineInInventory(String name) {
        return findMedicineByName(name) != null;
    }

    // Helper method to find a medicine by its name
    public Medicine findMedicineByName(String name) {
        for (Medicine medicine : inventory) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }

    // Add or increment stock of a medicine
    public void addOrIncrementMedicine() {
    	viewInventory();
        System.out.print("Please select the medicine to add stock (0 to add new medicine): ");
        int selection = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        if(selection < 0 || selection > inventory.size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices.");
        	return;
        }
        	
        if (selection != 0 && isMedicineInInventory(inventory.get(selection-1).getName())) {
            System.out.print("Enter additional stock: ");
            int additionalStock = InputScanner.sc.nextInt();
            incrementStock(inventory.get(selection-1).getName(), additionalStock);
        } else {
        	System.out.print("Enter new medicine name: ");
        	String name = InputScanner.sc.nextLine();
        	if(isMedicineInInventory(name)) {
        		System.out.println("Medicine is inside the inventory! Please check again.");
        		return;
        	}
            System.out.print("Enter initial stock: ");
            int stock = InputScanner.sc.nextInt();
            System.out.print("Enter low stock alert level: ");
            int alertLevel = InputScanner.sc.nextInt();
            addNewMedicine(name, stock, alertLevel);
        }
    }

    // Add new medicine to the inventory
    public void addNewMedicine(String name, int stock, int alertLevel) {
        inventory.add(new Medicine(name, stock, alertLevel));
        System.out.println("Medicine added: " + name);
    }

    // Increment stock of an existing medicine
    public void incrementStock(String name, int additionalStock) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            int newStock = medicine.getCurrentStock() + additionalStock;
            medicine.setCurrentStock(newStock);
            System.out.println("Added " + additionalStock + " units to " + name + ". New stock: " + newStock);
        }
    }

    // Remove Medicine from inventory
    public void removeMedicine() {
    	viewInventory();
        System.out.print("Select the medicine to remove: ");
        int choice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        Medicine medicine = findMedicineByName(inventory.get(choice-1).getName());
        if(choice <= 0 || choice > inventory.size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices: ");
        	return;
        }
        if (medicine != null) {
            inventory.remove(medicine);
            System.out.println("Medicine removed: " + inventory.get(choice-1).getName());
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Update medicine stock and alert level
    public void updateMedicine() {
    	viewInventory();
    	System.out.print("Select the medicine to update: ");
        int choice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        if(choice <= 0 || choice > inventory.size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices: ");
        	return;
        }
        Medicine medicine = findMedicineByName(inventory.get(choice-1).getName());
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
        System.out.println("Updated " + inventory.get(choice-1).getName() + ": New stock = " + newStock + ", New alert level = " + newAlertLevel);
    }

    // Use a specific amount of medicine
    public void useMedicine() {
    	viewInventory();
        System.out.print("Enter medicine name to use: ");
        int choice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        Medicine medicine = findMedicineByName(inventory.get(choice-1).getName());
        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("Enter amount used: ");
        int amount = InputScanner.sc.nextInt();
        if (amount <= medicine.getCurrentStock()) {
            medicine.decrementStock(amount);
            System.out.println("Used " + amount + " units of " + inventory.get(choice-1).getName() + ".");
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
    }

    // Submit a replenishment request for a specific medicine
    public void submitReplenishmentRequest(Medicine medicine, int requestAmount) {
        if (medicine != null) {
            medicine.setRequestAmount(requestAmount);
            System.out.println("Replenishment request of " + requestAmount + " units submitted for " + medicine.getName() + ".");
        } else {
            System.out.println("Medicine not found in the inventory.");
        }
    }
    
    public static void updateInventoryFile(List<Medicine> inventoryList) {
        List<String[]> data = new ArrayList<>();
        
        String[] values = new String[4];
        values[0] = "Medicine Name";
        values[1] = "Current Stock";
        values[2] = "Low Stock";
        values[3] = "Request Amount";
        data.add(values);
        
        for(Medicine med : inventoryList) {
        	String[] row = new String[4];
        	row[0] = med.getName();
            row[1] = String.valueOf(med.getCurrentStock());
            row[2] = String.valueOf(med.getLowStockAlert());
            row[3] = String.valueOf(med.getRequestAmount());
            data.add(row);
        }
        updateFile(data);
    }
    
    private static void updateFile(List<String[]> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
			for(String[] row : data) writer.write(String.join(",", row) + "\n");
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
    
    public static List<Medicine> getInventory(){
    	return MedicationInventory.inventory;
    }
    
    private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}
}
