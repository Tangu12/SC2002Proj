package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

/**
 * {@code HospitalStaff} class which inherits from {@code IUser} interface
 */
public abstract class HospitalStaff implements IUser {
    private String userID;
    private String name;
    private int age;
    private Gender gender;
    private Domain domain;

    /**
     * Constructor for {@code HospitalStaff}
     * @param userID The unique identifier for the {@code HospitalStaff}. This could be an employee ID, username, or another unique identifier used to track hospital staff.
     * @param name The name of the {@code HospitalStaff}. This is a string representing the full name of the staff member.
     * @param age The age of the {@code HospitalStaff}. This is an integer representing the staff member's age in years.
     * @param gender The gender of the {@code HospitalStaff}. This is an enum value from the {@code Gender} enum (e.g., MALE, FEMALE) representing the staff member's gender.
     * @param domain The domain or role of the {@code HospitalStaff}. This is an enum value from the {@code Domain} enum, such as DOCTOR, NURSE, ADMINISTRATOR, etc.
     */
    public HospitalStaff (String userID, String name, int age, Gender gender, Domain domain) {
    	this.userID = userID;
    	this.name = name;
    	this.age = age;
    	this.gender = gender;
    	this.domain = domain;
    }

    /**
     * Updates the department of a {@code HospitalStaff}
     * @param newRole The new role of the {@code HospitalStaff}. This is a {@code Domain} enum value that represents the new role (e.g., DOCTOR, NURSE, etc.).
     * @param dept The department to assign to the {@code HospitalStaff}. This is a {@code Department} enum value representing the department the staff member should be in (e.g., CARDIOLOGY, EMERGENCY, etc.).
     */
    public abstract void updateDepartment(Domain newRole, Department dept);

    /**
     * The getter method of a {@code HospitalStaff}'s {@code HospitalID}
     * @return The {@code HospitalID} of the User
     */
    public String getUserID() {
        return userID;
    }

    /**
     * The setter method of a {@code HospitalStaff}'s {@code HospitalID}
     * @param userID The unique identifier to set for the {@code HospitalStaff}.
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * The getter method of a {@code HospitalStaff}'s name
     * @return The {@code HospitalStaff}'s name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter method of a {@code HospitalStaff}'s name
     * @param name The name to set for the {@code HospitalStaff}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter method of a {@code HospitalStaff}'s age
     * @return The {@code HospitalStaff}'s age
     */
    public int getAge() {
        return age;
    }

    /**
     * The setter method of a {@code HospitalStaff}'s age
     * @param age The age to set for the {@code HospitalStaff}.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * The getter method of a {@code HospitalStaff}'s gender
     * @return The {@code HospitalStaff}'s gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * The setter method of a {@code HospitalStaff}'s gender
     * @param gender The gender to set for the {@code HospitalStaff}. This is an enum value from the {@code Gender} enum (e.g., MALE, FEMALE).
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * The getter method of a {@code HospitalStaff}'s domain
     * @return The {@code HospitalStaff}'s domain
     */
    public Domain getDomain() {
        return domain;
    }

    /**
     * The setter method of a {@code HospitalStaff}'s domain
     * @param domain The domain/role to set for the {@code HospitalStaff}. This is an enum value from the {@code Domain} enum (e.g., DOCTOR, NURSE, ADMINISTRATOR).
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

}
