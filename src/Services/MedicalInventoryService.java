package Services;

import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Repository.MedicationInventoryRepository;

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
    public void removeMedicineFromInventory(Medicine medicine){
        medicationInventory.deleteMedicine(medicine.getNameOfMedicine());
        medicationInventoryRepository.deleteRecord(medicine);
    }

    // Update Medicine (LowLevel, Stock)
    // increment medicine (calls medication Inventory increment)
    // Increment Stock (Medicine)
    // Decrement Stock (Medicine)

    // Check Low stock in inventory
    public void checkInventory() {
        boolean lowStockFound = false;
        for (Medicine medicine : medicationInventory.getInventory()) {
            if (medicine.needsReplenishment()) {
                System.out.println("Low stock alert: " + medicine.getNameOfMedicine());
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }



    // Process Replenishment

    // submit replenishment request

}
