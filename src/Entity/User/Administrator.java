package Entity.User;

import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicationInventory;

import java.util.ArrayList;

public class Administrator implements IUser {
    String userID;
    String name;
    int age;
    Gender gender;
    Domain domain;

    // Add more attributes as needed
    MedicationInventory medicationInventory;

    // Getters
    @Override
    public String getUserId() {
        return this.userID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public Gender getGender() {
        return this.gender;
    }

    @Override
    public Domain getDomain() {
        return this.domain;
    }

    public MedicationInventory getMedicationInventory() {
        return this.medicationInventory;
    }

    // Setters

    @Override
    public void setUserId(String userId) {
        this.userID = userId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public void setMedicationInventory(MedicationInventory medicalInventory) {
        this.medicationInventory = medicationInventory;
    }

}
