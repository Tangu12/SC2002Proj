package Entity.User;

import Entity.Enums.Domain;
import Entity.Enums.Gender;

/*
* This is an interface for creating our Users, such as Patient, Doctor , Administrator and Pharmacist
* */

public interface IUser {
    // Getters
    String getUserID();
    String getName();
    int getAge();
    Gender getGender();
    Domain getDomain();

    // Setters
    void setUserID(String userID);
    void setName(String name);
    void setAge(int age);
    void setGender(Gender gender);
    void setDomain(Domain domain);
}
