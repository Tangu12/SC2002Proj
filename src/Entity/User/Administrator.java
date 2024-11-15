package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicationInventory;

import java.util.ArrayList;

public class Administrator extends HospitalStaff{

    // Add more attributes as needed
    MedicationInventory medicationInventory;
    private static ArrayList<Administrator> administratorList = new ArrayList<>();

    public Administrator(String UserID,String name,int age,Gender gender,MedicationInventory medicationInventory) {
        super(UserID,name,age,gender,Domain.ADMINISTRATOR);
        this.medicationInventory = medicationInventory;
    }
    
    public Administrator(String UserID,String name,int age,Gender gender) {
        super(UserID,name,age,gender,Domain.ADMINISTRATOR);
        this.medicationInventory = MedicationInventory.getInstance();
    }

    // Getters
    public MedicationInventory getMedicationInventory() {
        return this.medicationInventory;
    }
    
    public static ArrayList<Administrator> getAdministratorList(){
    	return Administrator.administratorList;
    }

    // Setters
    public void setMedicationInventory(MedicationInventory medicalInventory) {
        this.medicationInventory = medicalInventory;
    }

    @Override
    public void updateDepartment(Domain newRole, Department dept) {
        // null method
    }
}
