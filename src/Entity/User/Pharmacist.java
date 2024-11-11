package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;

import java.util.ArrayList;

public class Pharmacist extends HospitalStaff{

    // Add more attributes as needed
    ArrayList<MedicalRecord> medicalHistory;

    public Pharmacist(String userID, String name, int age, Gender gender, Domain domain,ArrayList<MedicalRecord> medicalHistory) {
        super(userID, name, age, gender, Domain.PHARMACIST);
        this.medicalHistory = medicalHistory;
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
}
