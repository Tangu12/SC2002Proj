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
     * @param userID
     * @param name
     * @param age
     * @param gender
     * @param department
     */
    public Doctor(String userID, String name, int age, Gender gender,Department department) {
    	super(userID, name, age, gender, Domain.DOCTOR);
        this.department = department;
    }

    /**
     * Setter method for the {@code Doctor} which sets the {@code Doctor} list
     * @param docList
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
     * The getter method to get the department of the {@code Doctor}
     * @return
     */
    public Department getDepartment() {
    	return this.department;
    }

    /**
     * To update the {@code Department} of a {@code Doctor}
     * @param newRole
     * @param dept
     */
    @Override
    public void updateDepartment(Domain newRole, Department dept) {
        if (newRole == Domain.DOCTOR) {
            this.setDepartment(dept);
        }
    }
}
