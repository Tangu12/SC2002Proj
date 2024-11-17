package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;

import java.util.ArrayList;

/**
 * {@code Pharmacist} class which inherits from {@code HospitalStaff}, with the {@code Patient}'s medical History
 */
public class Pharmacist extends HospitalStaff{

    // Add more attributes as needed
    ArrayList<MedicalRecord> medicalHistory;
    private static ArrayList<Pharmacist> pharmacistList = new ArrayList<>();

    /**
     * Constructor for {@code Pharmacist}
     * @param userID
     * @param name
     * @param age
     * @param gender
     * @param domain
     * @param medicalHistory
     */
    public Pharmacist(String userID, String name, int age, Gender gender, Domain domain,ArrayList<MedicalRecord> medicalHistory) {
        super(userID, name, age, gender, Domain.PHARMACIST);
        this.medicalHistory = medicalHistory;
    }

    /**
     * Constructor for {@code Pharmacist}
     * @param userID
     * @param name
     * @param age
     * @param gender
     * @param domain
     */
    public Pharmacist(String userID, String name, int age, Gender gender, Domain domain) {
        super(userID, name, age, gender, Domain.PHARMACIST);
    }

    /**
     * Null function
     * @param newRole
     * @param dept
     */
    @Override
    public void updateDepartment(Domain newRole, Department dept) {
        // null method
    }

    /**
     * The getter method of a {@code Patient}'s medical history
     * @return
     */
    public ArrayList<MedicalRecord> getMedicalHistory() {
        return this.medicalHistory;
    }

    /**
     * The setter method of a {@code Patient}'s medical history
     * @param medicalHistory
     */
    public void setMedicalHistory(ArrayList<MedicalRecord> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * Adds a {@code MedicalRecord} to a {@code Patient}'d medical history
     * @param medicalRecord
     */
    public void addMedicalHistory(MedicalRecord medicalRecord) {
        this.medicalHistory.add(medicalRecord);
    }

    /**
     *
     * @return The {@code Pharmacist} list of a {@code Pharmacist}
     */
    public static ArrayList<Pharmacist> getPharmacistList() {
    	return Pharmacist.pharmacistList;
    }
}
