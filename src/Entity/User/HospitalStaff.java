package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

public abstract class HospitalStaff implements IUser {
    private String userID;
    private String name;
    private int age;
    private Gender gender;
    private Domain domain;

    public HospitalStaff (String userID, String name, int age, Gender gender, Domain domain) {
    	this.userID = userID;
    	this.name = name;
    	this.age = age;
    	this.gender = gender;
    	this.domain = domain;
    }

    public abstract void updateDepartment(Domain newRole, Department dept);

    // Getter and Setter for userID
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter for gender
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // Getter and Setter for domain
    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

}
