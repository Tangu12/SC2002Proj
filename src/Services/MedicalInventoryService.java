package Services;

import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Repository.MedicationInventoryRepository;

import java.util.ArrayList;

/**
 * {@code MedicalInventoryService} class which handles all the logic dealing with the {@code MedicalInventory} class
 */
public class MedicalInventoryService {
    private MedicationInventory medicationInventory;
    private MedicationInventoryRepository medicationInventoryRepository;

    /**
     * Constructor of {@code MedicalInventoryService} with Dependency Injection of {@code MedicationInventory} Singleton and {@code MedicationInventoryRepository}
     * @param medicationInventory
     * @param medicationInventoryRepository
     */
    // Dependency Injection of MedicationInventory Singleton and MedicationInventoryRepository
    public MedicalInventoryService(MedicationInventory medicationInventory,MedicationInventoryRepository medicationInventoryRepository) {
        this.medicationInventory = medicationInventory;
        this.medicationInventoryRepository = medicationInventoryRepository;
    }

    /**
     * Loads the {@code MedicationInventory} with the data stored in side the {@code MedicationInventory} file
     */
    public void loadInventoryFromFile(){
        medicationInventory.setInventory(medicationInventoryRepository.getAllMedicine());
    }


    /**
     * Saves the {@code MedicationInventory} inside the {@code MedicationInventory} file
     */
    public void saveInventoryToFile(){
        medicationInventoryRepository.saveInventoryToFile(medicationInventory.getInventory());
    }

    /**
     * Adds a new {@code Medicine} if it does not already exist, it gets added into the current list of {@code Medicine} and the {@code MedicationInventory} file
     * @param medicine
     */
    public void addMedicineToInventory(Medicine medicine){
        medicationInventory.addMedicine(medicine);
        medicationInventoryRepository.createRecord(medicine);
    }

    /**
     * Finds and returns a {@code Medicine} from the {@code MedicationInventory} based on its name
     * @param medicineName
     * @return The {@code Medicine} with the corresponding input name
     */
    public Medicine findMedicineFromInventory(String medicineName){
        return medicationInventory.findMedicine(medicineName);
    }

    /**
     * Remove a {@code Medicine} from the {@code Medicine} list and from the {@code MedicationInventory} file based on its name
     * @param medicineName
     */
    public void removeMedicineFromInventory(String medicineName){
        medicationInventory.deleteMedicine(medicineName);
        medicationInventoryRepository.deleteRecord(medicineName);
        
    }

    /**
     * Increment the stock of a {@code Medicine} based on its {@code Medicine} name and the input amount
     * @param medicineName
     * @param incrementAmount
     * @return
     */
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

    /**
     * Decrements the stock of a {@code Medicine} based on its {@code Medicine} name and the input amount
     * @param medicineName
     * @param decrementAmount
     * @return True if the stock of that {@code Medicine} has been decremented and False otherwise
     */
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

    /**
     * Checks and prints the {@code Medicine} in the {@code MedicationInventory} that has Low stock and needs replenishment
     */
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

    /**
     * Gets the list of all the{@code Medicine} in the {@code MedicationInventory}
     * @return List of all {@code Medicine} in the {@code MedicationInventory}
     */
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

    /**
     * Process the replenishment of a {@code Medicine} based on its name
     * @param medicineName
     * @param addAmount
     * @return True if the replenishment request has been satisfied and False otherwise
     */
    public boolean processReplenishmentRequest(String medicineName, int addAmount){
        boolean hasRequests = false;
        for (Medicine medicine : medicationInventory.getInventory()) {
            if (medicine.getRequestAmount() > 0) {
                hasRequests = true;
                int newStock = medicine.getCurrentStock() + addAmount;
                medicine.setCurrentStock(newStock);
                System.out.println("Processed replenishment for " + medicine.getNameOfMedicine() + ". New stock: " + newStock);
                medicine.setRequestAmount(0);
            }
        }
        if (!hasRequests) {
            System.out.println("No pending replenishment requests.");
            return false;
        }
        saveInventoryToFile(); // Save changes
        return true;
    }

    /**
     * Searches and updates the parameters of a {@code Medicine} inside {@code MedicationInventory} based on its name
     * @param med
     */
    public void updateMedicine(Medicine med) {
    	medicationInventory.updateMedicine(med.getNameOfMedicine(), med);
    }

    /**
     * Processes a replenishment request for a {@code Medicine} in the {@code MedicationInventory}, then updates its stock level.
     * If the replenishment exceeds the requested amount, the request amount is reset to zero; otherwise, it is reduced by the replenished amount
     * @param selection
     * @param amount
     */
    public void processReplenishmentRequests(int selection, int amount) {
    		int originalAmount = MedicationInventory.getInventory().get(selection).getCurrentStock();
    		int originalRequestedAmount =  MedicationInventory.getInventory().get(selection).getRequestAmount();
    		MedicationInventory.getInventory().get(selection).setCurrentStock(originalAmount + amount);
        System.out.println("Processed replenishment for " + MedicationInventory.getInventory().get(selection).getNameOfMedicine() + ". New stock: " + (originalAmount + amount));
       if(amount>originalRequestedAmount) MedicationInventory.getInventory().get(selection).setRequestAmount(0); // Reset request amount
       else MedicationInventory.getInventory().get(selection).setRequestAmount(originalRequestedAmount - amount);
    }
}
