import java.util.Scanner;

public class MedicineApp {
    public static void main(String[] args) {
        // Create a MedicationInventory instance
        MedicationInventory inventory = new MedicationInventory();

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Main loop to interact with the inventory
        while (true) {
            System.out.println("\n--- Medication Inventory System ---");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Medicine");
            System.out.println("3. Update Medicine Stock");
            System.out.println("4. Check Inventory for Low Stock");
            System.out.println("5. Use Medicine (Update Inventory)");
            System.out.println("6. Submit Replenishment Request");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    inventory.viewInventory();
                    break;

                case 2:
                    addNewMedicine(inventory, scanner);
                    break;

                case 3:
                    System.out.print("Enter medicine name: ");
                    String medicineName = scanner.next();
                    System.out.print("Enter new stock amount: ");
                    int newStock = scanner.nextInt();
                    updateMedicineStock(inventory, medicineName, newStock);
                    break;

                case 4:
                    inventory.checkInventory();
                    break;

                case 5:
                    System.out.print("Enter medicine name: ");
                    String medName = scanner.next();
                    System.out.print("Enter amount used: ");
                    int amountUsed = scanner.nextInt();

                    if (!inventory.updateInventory(medName, amountUsed)) {
                        System.out.println("No such medicine found in the inventory.");
                    } else {
                        System.out.println(medName + " inventory updated successfully.");
                    }
                    break;

                case 6:
                    System.out.print("Enter medicine name: ");
                    String reqMedicine = scanner.next();
                    System.out.print("Enter request amount: ");
                    int reqAmount = scanner.nextInt();
                    inventory.submitReplenishmentRequest(reqMedicine, reqAmount);
                    break;

                case 7:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Helper function to add new medicine to the inventory
    private static void addNewMedicine(MedicationInventory inventory, Scanner scanner) {
        System.out.print("Enter medicine name: ");
        String name = scanner.next();
        System.out.print("Enter initial stock: ");
        int stock = scanner.nextInt();
        System.out.print("Enter low stock alert level: ");
        int alertLevel = scanner.nextInt();

        Medicine newMedicine = new Medicine(name, stock, alertLevel);
        inventory.addMedicine(newMedicine);
        System.out.println(name + " has been added to the inventory.");
    }

    // Helper function to update the stock of a specific medicine
    private static void updateMedicineStock(MedicationInventory inventory, String medicineName, int newStock) {
        Medicine medicine = inventory.getInventory()
                                     .stream()
                                     .filter(m -> m.getNameOfMedicine().equalsIgnoreCase(medicineName))
                                     .findFirst()
                                     .orElse(null);

        if (medicine != null) {
            medicine.setCurrentStock(newStock);
            System.out.println("Stock updated for " + medicineName + " to " + newStock);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }
}
