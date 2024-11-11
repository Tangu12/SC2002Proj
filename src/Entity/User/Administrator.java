package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicationInventory;

import java.util.ArrayList;

public class Administrator extends HospitalStaff{

    // Add more attributes as needed
    MedicationInventory medicationInventory;

    public Administrator(String UserID,String name,int age,Gender gender,Department department,MedicationInventory medicationInventory) {
        super(UserID,name,age,gender,Domain.ADMINISTRATOR);
        this.medicationInventory = medicationInventory;
    }

    // Getters
    public MedicationInventory getMedicationInventory() {
        return this.medicationInventory;
    }

    // Setters
    public void setMedicationInventory(MedicationInventory medicalInventory) {
        this.medicationInventory = medicationInventory;
    }

}
