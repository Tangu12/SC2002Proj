package Entity.User;

import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;
import java.util.ArrayList;

/*
 * This is the patient object, it has an array of his own medical records, which is his medical history
 * */

public class Patient implements IUser{
	
    String userID;
    String name;
    int age;
    Gender gender;
    Domain domain = Domain.PATIENT;
    private static ArrayList<String[]> patientList = new ArrayList<>();

    // Add more attributes as needed
    ArrayList<MedicalRecord> medicalHistory;

    public Patient(String userID, String name, int age, Gender gender, Domain domain, ArrayList<MedicalRecord> medicalHistory) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.domain = domain;
        this.medicalHistory = medicalHistory;

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

    public ArrayList<MedicalRecord> getMedicalHistory() {
        return this.medicalHistory;
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

    public void setMedicalHistory(ArrayList<MedicalRecord> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void addMedicalHistory(MedicalRecord medicalRecord){
        this.medicalHistory.add(medicalRecord);
    }

    public void updateMedicalHistory(MedicalRecord medicalRecord){
        // uses medical history repository
    }
    
    public static ArrayList<String[]> getPatientList(){
    	return Patient.patientList;
    }

}
