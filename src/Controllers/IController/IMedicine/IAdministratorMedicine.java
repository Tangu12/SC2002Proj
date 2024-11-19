package Controllers.IController.IMedicine;

import Entity.Medicine;

import java.util.ArrayList;

public interface IAdministratorMedicine {
    boolean isMedicineInInventory(String name);
    Medicine findMedicineByName(String name);
    void increaseCurrentStock(int stock, int selection);
    void addMedicine(String name, int stock, int alertLevel);
    void removeMedicine(int choice);
    void updateMedicine(Medicine med, int newStock, int newAlertLevel);
    void decrementStock(Medicine med, int usedAmount);
    void processReplenishmentRequests(int selection, int amount);
    ArrayList<Medicine> getMedicationInventory();
}
