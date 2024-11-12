package Services;

import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Repository.MedicationInventoryRepository;

import java.util.ArrayList;

public class MedicalInventoryService {
    private MedicationInventory medicationInventory;
    private MedicationInventoryRepository medicationInventoryRepository;

    // Dependency Injection of MedicationInventory Singleton and MedicationInventoryRepository
    public MedicalInventoryService(MedicationInventory medicationInventory,MedicationInventoryRepository medicationInventoryRepository) {
        this.medicationInventory = medicationInventory;
        this.medicationInventoryRepository = medicationInventoryRepository;
    }

    // load Inventory from file (update the local list from the file)
    public void loadInventoryFromFile(){
        medicationInventory.setInventory(medicationInventoryRepository.getAllMedicine());
    }

    // Save inventory to file
    public void saveInventoryToFile(){
        medicationInventoryRepository.saveInventoryToFile(medicationInventory.getInventory());
    }

    // add new medicine that is not in the list (adds to current list and file)
    public void addMedicineToInventory(Medicine medicine){
        medicationInventory.addMedicine(medicine);
        medicationInventoryRepository.createRecord(medicine);
    }

    // readInventory (find medicine in inventory)
    // checkifInInventory
    // find medicine by name
    public Medicine findMedicineFromInventory(String medicineName){
        return medicationInventory.findMedicine(medicineName);
    }

    // Remove Medicine from list and from file
    public void removeMedicineFromInventory(String medicineName){
        medicationInventory.deleteMedicine(medicineName);
        medicationInventoryRepository.deleteRecord(medicineName);
    }

    // Increment Stock of Medicine
    public boolean incrementStock(String medicineName, int incrementAmount) {
        //We can reference medicine directly because arrayLists return references and not copies

        Medicine medicine = medicationInventory.findMedicine(medicineName);
        if (medicine == null) {
            System.out.println("Medicine not found in inventory.");
            return false;
        }
        int newStock = medicine.getCurrentStock() + incrementAmount;
        medicine.setCurrentStock(newStock);
        System.out.println("Stock for " + medicineName + " incremented by " + incrementAmount + ". New stock: " + newStock);

        saveInventoryToFile(); // Save the updated inventory to the file
        return true;
    }

    // Decrement Stock of Medicine
    public boolean decrementStock(String medicineName, int decrementAmount) {
        Medicine medicine = medicationInventory.findMedicine(medicineName);
        //We can reference medicine directly because arrayLists return references and not copies

        if (medicine == null) {
            System.out.println("Medicine not found in inventory.");
            return false;
        }
        int newStock = medicine.getCurrentStock() - decrementAmount;

        if (newStock < 0) {
            System.out.println("Error! Stock cannot be negative.");
            return false;
        }

        medicine.setCurrentStock(newStock);
        System.out.println("Stock for " + medicineName + " decremented by " + decrementAmount + ". New stock: " + newStock);

        saveInventoryToFile();
        return true;
    }

    // Check Low stock in inventory
    public void checkLowStockInInventory() {
        boolean lowStockFound = false;
        for (Medicine medicine : medicationInventory.getInventory()) {
            if (medicine.needsReplenishment()) {
                System.out.println("Low stock alert: " + medicine.getNameOfMedicine());
                System.out.println("Current Stock: " + medicine.getCurrentStock() + "Low Level Alert: "+medicine.getLowStockLevelAlert());
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }

    // View Inventory, returns a list of all medicine in inventory
    public ArrayList<Medicine> viewInventory(){
        return medicationInventory.getInventory();
    }

    // submit replenishment request, updates request amount in inventory and in file
    public boolean submitReplenishmentRequest(String medicineName, int requestAmount){
        // Uses setRequestAmount in medicationInventory to set the request amount, which returns true or false if successful or not
        if(!medicationInventory.setRequestAmount(medicineName, requestAmount)){
            return false;
        }
        // if it returns true, we save the inventory to file
        else {
            medicationInventoryRepository.saveInventoryToFile(medicationInventory.getInventory());
            return true;
        }
    }

    // Process Replenishment
    public boolean processReplenishmentRequest(String medicineName, int addAmount){
        boolean hasRequests = false;
        for (Medicine medicine : medicationInventory.getInventory()) {
            if (medicine.getRequestAmount() > 0) {
                hasRequests = true;
                int newStock = medicine.getCurrentStock() + medicine.getRequestAmount();
                medicine.setCurrentStock(newStock);
                System.out.println("Processed replenishment for " + medicine.getNameOfMedicine() + ". New stock: " + newStock);
                medicine.setRequestAmount(0); // Reset request amount
            }
        }
        if (!hasRequests) {
            System.out.println("No pending replenishment requests.");
            return false;
        }
        saveInventoryToFile(); // Save changes
        return true;
    }
    
    public void updateMedicine(Medicine med) {
    	medicationInventory.updateMedicine(med.getNameOfMedicine(), med);
    }
}
