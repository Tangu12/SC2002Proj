package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

import java.util.ArrayList;

/**
 * {@code Doctor} class which inherits from {@code HospitalStaff}
 */
public class Doctor extends HospitalStaff{

    private static ArrayList<Doctor> doctorList = new ArrayList<>();
    private Department department;

    /**
     * Constructor for the {@code Doctor} class
     * @param userID The unique identifier for the {@code Doctor}. This is typically used to identify the doctor in the system.
     * @param name The name of the {@code Doctor}. This is a string representing the doctor's full name.
     * @param age The age of the {@code Doctor}. This is an integer representing the age of the doctor in years.
     * @param gender The gender of the {@code Doctor}. This is an enum value from the {@code Gender} enum, which can be used to specify the gender (e.g., MALE, FEMALE).
     * @param department The department to which the {@code Doctor} belongs. This is an enum value from the {@code Department} enum, such as CARDIOLOGY, NEUROLOGY, etc.
     */
    public Doctor(String userID, String name, int age, Gender gender,Department department) {
    	super(userID, name, age, gender, Domain.DOCTOR);
        this.department = department;
    }

    /**
     * Setter method for the {@code Doctor} which sets the {@code Doctor} list
     * @param docList The list of {@code Doctor} objects to set. This is an {@code ArrayList<Doctor>} that will replace the current list of doctors.
     */
    public static void setDoctorList(ArrayList<Doctor> docList) {
    	Doctor.doctorList = docList;
    }

    /**
     * The getter method for the {@code Doctor} list of an {@code Doctor}
     * @return The list of {@code Administrator}
     */
    public static ArrayList<Doctor> getDoctorList() {
    	return Doctor.doctorList;
    }

    /**
     * The setter method to set the department of the {@code Doctor}
     * @param dep
     */
    public void setDepartment(Department dep) {
    	this.department = dep;
    }

    /**
     * The getter method to get the department of the {@code Doctor}* @param dep The department to assign to the {@code Doctor}. This is an enum value from the {@code Department} enum (e.g., CARDIOLOGY, PEDIATRICS).
     *      */
    public Department getDepartment() {
    	return this.department;
    }

    /**
     * To update the {@code Department} of a {@code Doctor}
     * @param newRole The new role that is being assigned to the doctor. This is a {@code Domain} enum value, and should be set to {@code Domain.DOCTOR} for this method to apply.
     * @param dept The department to assign to the doctor. This is a {@code Department} enum value (e.g., CARDIOLOGY, SURGERY, etc.).
     */
    @Override
    public void updateDepartment(Domain newRole, Department dept) {
        if (newRole == Domain.DOCTOR) {
            this.setDepartment(dept);
        }
    }
}
