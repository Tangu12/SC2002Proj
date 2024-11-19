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
     * @param UserID The unique identifier for the {@code Administrator}. This is typically used to identify the user in the system.
     * @param name The name of the {@code Administrator}. This is a string that represents the full name of the administrator.
     * @param age The age of the {@code Administrator}. This integer represents the age in years.
     * @param gender The gender of the {@code Administrator}. This is an enum value from the {@code Gender} enum, which can be used to specify the gender (e.g., MALE, FEMALE).
     * @param medicationInventory The {@code MedicationInventory} instance associated with the {@code Administrator}. This is used to manage the medication inventory that the administrator has control over.
     */
    public Administrator(String UserID,String name,int age,Gender gender,MedicationInventory medicationInventory) {
        super(UserID,name,age,gender,Domain.ADMINISTRATOR);
        this.medicationInventory = medicationInventory;
    }

    /**
     * Constructor for a {@code Administrator}
     * @param UserID The unique identifier for the {@code Administrator}. This is typically used to identify the user in the system.
     * @param name The name of the {@code Administrator}. This is a string that represents the full name of the administrator.
     * @param age The age of the {@code Administrator}. This integer represents the age in years.
     * @param gender The gender of the {@code Administrator}. This is an enum value from the {@code Gender} enum, which can be used to specify the gender (e.g., MALE, FEMALE).
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
     * @param newRole The new role to be assigned to the {@code Administrator}. This is a {@code Domain} enum value that defines the role (e.g., ADMINISTRATOR, DOCTOR).
     * @param dept The department to be assigned. This is a {@code Department} enum value that specifies the department (e.g., PHARMACY, EMERGENCY).
     */
    @Override
    public void updateDepartment(Domain newRole, Department dept) {
        // null method
    }
}
