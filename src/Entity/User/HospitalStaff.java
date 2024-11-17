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
     * @param userID
     * @param name
     * @param age
     * @param gender
     * @param domain
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
     * @param newRole
     * @param dept
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
     * @param userID
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
     * @param name
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
     * @param age
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
     * @param gender
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
     * @param domain
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

}
