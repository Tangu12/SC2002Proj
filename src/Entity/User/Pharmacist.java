package Entity.User;

import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;

import java.util.ArrayList;

public class Pharmacist extends HospitalStaff{

    // Add more attributes as needed
    ArrayList<MedicalRecord> medicalHistory;
    private static ArrayList<Pharmacist> pharmacistList = new ArrayList<>();

    public Pharmacist(String userID, String name, int age, Gender gender, Domain domain,ArrayList<MedicalRecord> medicalHistory) {
        super(userID, name, age, gender, Domain.PHARMACIST);
        this.medicalHistory = medicalHistory;
    }
    
    public Pharmacist(String userID, String name, int age, Gender gender, Domain domain) {
        super(userID, name, age, gender, Domain.PHARMACIST);
    }

    // Getters
    public ArrayList<MedicalRecord> getMedicalHistory() {
        return this.medicalHistory;
    }

    // Setters
    public void setMedicalHistory(ArrayList<MedicalRecord> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void addMedicalHistory(MedicalRecord medicalRecord) {
        this.medicalHistory.add(medicalRecord);
    }
    
    public static ArrayList<Pharmacist> getPharmacistList() {
    	return Pharmacist.pharmacistList;
    }
}
