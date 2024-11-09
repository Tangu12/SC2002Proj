package Entity;

import java.util.ArrayList;

public class MedicationInventory {
    private ArrayList<Medicine> inventory;
    private static MedicationInventory instance;

    private MedicationInventory() {
        this.inventory = new ArrayList<>();
    }

    /*
    Singleton, makes sure that there is only one instance in the whole program
    */
    public static MedicationInventory getInstance() {
        if (instance == null) {
            instance = new MedicationInventory();
        }
        return instance;
    }

    // Get list of all medicines in the inventory
    public ArrayList<Medicine> getInventory() {
        return inventory;
    }

    // Set Inventory
    public void setInventory(ArrayList<Medicine> inventory) {
        this.inventory = inventory;
    }

    /*
    Add Medicine to inventory
    */
    public void addMedicine(Medicine medicine){
        inventory.add(medicine);
        System.out.println("Medicine added successfully!");
    }

    /*
    Delete Medicine from list, returns True if Medicine is found, else return False
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

    /*
    Search for Medicine in list according to medicineName
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

    /*
    Increment currentStock
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

    // Decrement currentStock
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

    // set LowStockAlert
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

    /*
    Master Method to updateMedicine based on medicineName. Replaces the entry associated with the medicineName with the updatedMedicine Object.
    Returns true if successful, False otherwise
    */
    public boolean updateMedicine(String medicineName, Medicine updatedMedicine) {
        // Input Checking
        if(updatedMedicine.getCurrentStock()<0||updatedMedicine.getRequestAmount()<0||updatedMedicine.getLowStockLevelAlert()<0){
            System.out.println("Error, updated amount is not valid, please input a number >= 0!");
            return false;
        }

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getNameOfMedicine().equals(medicineName)) {
                inventory.set(i, updatedMedicine);
                System.out.println("Medicine updated successfully.");
                return true;
            }
        }
        System.out.println("Error, medicine not found in inventory!");
        return false;
    }

    /*
    Master Method to update the current stock based on medicineName. Returns True if successful, False otherwise
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