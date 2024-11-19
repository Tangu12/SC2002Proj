package Controllers.IController.IMedicine;

import Entity.Medicine;

import java.util.ArrayList;

public interface IPharmacistMedicine {
    ArrayList<Medicine> getMedicationInventory();
    void submitReplenishmentRequest(String medicineName,int requestAmount);
    void decrementStock(Medicine med, int usedAmount);
    Medicine findMedicineByName(String name);
}
