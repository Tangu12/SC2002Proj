package Entity.User;

import Entity.Enums.BloodType;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;

import java.time.LocalDate;
import java.util.ArrayList;

/*
 * This is the patient object, it has an array of his own medical records, which is his medical history
 * */

public class Patient implements IUser{
	
    private String userID;
    private String name;
    private int age;
    private Gender gender;
    private Domain domain = Domain.PATIENT;
    private String contactInfo;
    private LocalDate dateOfBirth;
    private BloodType bloodType;

    private static ArrayList<Patient> patientList = new ArrayList<>();


    public Patient(String userID, String name, int age, Gender gender, Domain domain, String contactInfo, LocalDate dateOfBirth, BloodType bloodType) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.domain = domain;
        this.contactInfo = contactInfo;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
    }

    // Getters
    @Override
    public String getUserID() {
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


    public String getContactInfo() {
        return contactInfo;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    // Setters
    @Override
    public void setUserID(String userId) {
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


    public void updateMedicalHistory(MedicalRecord medicalRecord){
        // uses medical history repository
    }


    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public static ArrayList<Patient> getPatientList(){
    	return Patient.patientList;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}
