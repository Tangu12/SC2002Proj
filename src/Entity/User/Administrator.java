package Entity.User;

import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalInventory;

import java.util.ArrayList;

public class Administrator implements IUser {
    String userID;
    String name;
    int age;
    Gender gender;
    Domain domain;

    // Add more attributes as needed
    ArrayList<MedicalInventory> medicalInventory;

}
