package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicationInventory;

import java.util.ArrayList;

/**
 * {@code Administrator} class which inherits from {@code HospitalStaff}, with a {@code MedicationInventory}
 */
public class Administrator extends HospitalStaff{

    // Add more attributes as needed
    MedicationInventory medicationInventory;
    private static ArrayList<Administrator> administratorList = new ArrayList<>();

    /**
     * Constructor for a {@code Administrator}
     * @param UserID
     * @param name
     * @param age
     * @param gender
     * @param medicationInventory
     */
    public Administrator(String UserID,String name,int age,Gender gender,MedicationInventory medicationInventory) {
        super(UserID,name,age,gender,Domain.ADMINISTRATOR);
        this.medicationInventory = medicationInventory;
    }

    /**
     * Constructor for a {@code Administrator}
     * @param UserID
     * @param name
     * @param age
     * @param gender
     */
    public Administrator(String UserID,String name,int age,Gender gender) {
        super(UserID,name,age,gender,Domain.ADMINISTRATOR);
        this.medicationInventory = MedicationInventory.getInstance();
    }


    /**
     * The getter method for the {@code MedicationInventory} of an {@code Administrator}
     * @return The {@code MedicationInventory} of an Administrator
     */
    public MedicationInventory getMedicationInventory() {
        return this.medicationInventory;
    }

    /**
     * The getter method for the {@code Administrator} list of an {@code Administrator}
     * @return The list of {@code Administrator}
     */
    public static ArrayList<Administrator> getAdministratorList(){
    	return Administrator.administratorList;
    }

    /**
     * The setter method for the {@code MedicationInventory} of an {@code Administrator}
     * @param medicalInventory
     */
    public void setMedicationInventory(MedicationInventory medicalInventory) {
        this.medicationInventory = medicalInventory;
    }

    /**
     * Null function
     * @param newRole
     * @param dept
     */
    @Override
    public void updateDepartment(Domain newRole, Department dept) {
        // null method
    }
}
