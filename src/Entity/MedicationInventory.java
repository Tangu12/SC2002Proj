package Entity;

import java.util.ArrayList;

/**
 * Medication Inventory Class which stores all the available {@code Medicine} and their information
 */
public class MedicationInventory {
    private static ArrayList<Medicine> inventory;
    private static MedicationInventory instance;

    /**
     * Constructor for {@code MedicationInventory}
     */
    private MedicationInventory() {
        MedicationInventory.inventory = new ArrayList<>();
    }

    /**
     * Singleton, makes sure that there is only one instance in the whole program
     * @return A single instance of {@code MedicationInventory}
     */
    public static MedicationInventory getInstance() {
        if (instance == null) {
            instance = new MedicationInventory();
        }
        return instance;
    }

    /**
     * The getter method of all the {@code Medicine} in the {@code MedicationInventory}
     * @return The list of all {@code Medicine} objects in the inventory
     */

    public static ArrayList<Medicine> getInventory() {
        return inventory;
    }

    /**
     * The setter method of all the {@code Medicine} in the {@code MedicationInventory}
     * @param inventory The new list of {@code Medicine} objects to replace the existing inventory
     */
    public void setInventory(ArrayList<Medicine> inventory) {
    	MedicationInventory.inventory = inventory;
    }

    /**
     * Adds a {@code Medicine} to the {@code MedicationInventory}
     */
    public void addMedicine(Medicine medicine){
        inventory.add(medicine);
        System.out.println("Medicine added successfully!");
    }

    /**
     *  Delete a {@code Medicine} from the {@code MedicationInventory} if it exists inside the {@code MedicationInventory}
     * @param medicineName The name of the {@code Medicine} to be deleted
     * @return True if the medicine was deleted successfully, false if it was not found
     */
    public boolean deleteMedicine(String medicineName) {
        for (Medicine medicine : inventory) {
            if (medicine.getNameOfMedicine().equals(medicineName)) {
                inventory.remove(medicine);
                return true;
            }
        }
        System.out.println("Error, Medicine is not found!");
        return false;
    }

    /**
     * Searches for a {@code Medicine} inside {@code MedicationInventory} using the name of the {@code Medicine}
     * @param medicineName The name of the {@code Medicine} to search for
     * @return The {@code Medicine} object if found, null if it does not exist in the inventory
     */
    public Medicine findMedicine(String medicineName) {
        for (Medicine medicine : inventory) {
            if (medicine.getNameOfMedicine().equals(medicineName)) {
                return medicine;
            }
        }
        System.out.println("Error, Medicine is not found!");
        return null;
    }

    /**
     * Increments the stock a {@code Medicine} inside the {@code MedicationInventory} if its exists
     * @param medicineName The name of the {@code Medicine} whose stock is to be incremented
     * @param increment The amount to increment the stock by
     * @return True if the stock was incremented successfully, false otherwise
     */
    public boolean incrementCurrentStock(String medicineName, int increment) {
        for(Medicine medicine : inventory){
            if(medicine.getNameOfMedicine().equals(medicineName)){
                medicine.incrementStock(increment);
                System.out.println("Medicine stock successfully incremented.");
                return true;
            }
        }
        System.out.println("Error, Medicine is not found!");
        return false;
    }


    /**
     * Decrements the stock a {@code Medicine} inside the {@code MedicationInventory} if its exists
     * @param medicineName The name of the {@code Medicine} whose stock is to be decremented
     * @param decrement The amount to decrement the stock by
     * @return True if the stock was decremented successfully, false if there was an error (e.g., insufficient stock)
     */
    public boolean decrementCurrentStock(String medicineName, int decrement) {
        if (decrement<0) {
            System.out.println("Error, decrement value cannot be negative!");
            return false;
        }

        boolean decremented = false;

        for(Medicine medicine : inventory){
            if(medicine.getNameOfMedicine().equals(medicineName)){
                decremented = medicine.decrementStock(decrement);
            }
        }

        if(!decremented){
            System.out.println("Error, medicine not found in inventory.");
        }else {
            System.out.println("Stock decremented successfully!");
        }

        return decremented;
    }

    /**
     * The setter method of the low level stock alert of a {@code Medicine} in the {@code MedicationInventory} if it exists
     * @param medicineName The name of the {@code Medicine} to update
     * @param lowStock The new low stock alert threshold to set for the {@code Medicine}
     * @return True if the low stock alert level was set successfully, false if the medicine was not found
     */
    public boolean setLowStockAlert(String medicineName, int lowStock) {
        if (lowStock<0) {
            System.out.println("Error, request value cannot be negative!");
            return false;
        }

        boolean updated = false;

        for(Medicine medicine : inventory){
            if(medicine.getNameOfMedicine().equals(medicineName)){
                updated = medicine.setLowStockLevelAlert(lowStock);
            }
        }

        if(!updated){
            System.out.println("Error, medicine not found in inventory.");
        }else {
            System.out.println("Low Stock Alert set successfully!");
        }

        return updated;

    }

    /**
     * The setter method of a current stock of a {@code Medicine} in the {@code MedicationInventory} if it exists
     * @param medicineName The name of the {@code Medicine} whose stock is to be updated
     * @param currentStock The new value for the current stock of the {@code Medicine}
     * @return True if the current stock was set successfully, false if the medicine was not found
     */
    // Set currentStock
    public boolean setCurrentStock(String medicineName, int currentStock) {
        if (currentStock<0) {
            System.out.println("Error, value cannot be negative!");
            return false;
        }
        boolean updated = false;

        for(Medicine medicine : inventory){
            if(medicine.getNameOfMedicine().equals(medicineName)){
                updated = medicine.setCurrentStock(currentStock);
            }
        }

        if(!updated){
            System.out.println("Error, medicine not found in inventory.");
        }else {
            System.out.println("Current Stock set successfully!");
        }

        return updated;
    }

    /**
     * The setter method for the request amount of a certain {@code Medicine} in the {@code MedicationInventory} if it exists* @param medicineName The name of the {@code Medicine} whose request amount is to be updated
     *      * @param requestAmount The new request amount to set for the {@code Medicine}
     *      * @return True if the request amount was set successfully, false if the medicine was not found
     *      */
    // Set requestAmount
    public boolean setRequestAmount(String medicineName, int requestAmount) {
        if (requestAmount<0) {
            System.out.println("Error, request value cannot be negative!");
            return false;
        }

        boolean updated = false;

        for(Medicine medicine : inventory){
            if(medicine.getNameOfMedicine().equals(medicineName)){
                updated = medicine.setRequestAmount(requestAmount);
            }
        }

        if(!updated){
            System.out.println("Error, medicine not found in inventory.");
        }else {
            System.out.println("Request Amount set successfully!");
        }

        return updated;
    }

    // Check Low Stock
    public ArrayList<Medicine> checkLowStock() {
        ArrayList<Medicine> lowStockMedicines = new ArrayList<>();
        for (Medicine medicine : inventory) {
            if (medicine.needsReplenishment()) {
                lowStockMedicines.add(medicine);
            }
        }
        return lowStockMedicines;
    }

    /**
     * Master Method to updateMedicine based on medicineName. Replaces the entry associated with the medicineName with the updatedMedicine Object
     * @param medicineName The name of the {@code Medicine} to update
     * @param updatedMedicine The new {@code Medicine} object to replace the existing one
     * @return True if the update was successful, false if the medicine was not found
     */
    public boolean updateMedicine(String medicineName, Medicine updatedMedicine) {
        // Input Checking
        if(updatedMedicine.getCurrentStock() < 0 || updatedMedicine.getRequestAmount() < 0 || updatedMedicine.getLowStockLevelAlert()<0){
            System.out.println("Error, updated amount is not valid, please input a number >= 0!");
            return false;
        }

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getNameOfMedicine().equals(medicineName)) {
                //inventory.set(i, updatedMedicine);
                inventory.remove(i);
                inventory.add(updatedMedicine);
                System.out.println("Medicine updated successfully.");
                return true;
            }
        }
        System.out.println("Error, medicine not found in inventory!");
        return false;
    }

    /**
     *  Master Method to update the current stock based on medicineName
     * @param medicineName name of Medicine that's current stock is going to be updated
     * @param updatedStock updated stock of the Medicine
     * @return True if successful, False otherwise
     */
    public boolean updateCurrentStock(String medicineName, int updatedStock) {
        if(updatedStock<0){
            System.out.println("Error, updated stock is not valid, please input a number >= 0!");
            return false;
        }

        for(Medicine medicine : inventory){
            if(medicine.getNameOfMedicine().equals(medicineName)){
                medicine.setCurrentStock(updatedStock);
                System.out.println("Medicine stock updated successfully.");
                return true;
            }
        }
        System.out.println("Error, medicine not found in inventory!");
        return false;
    }


}