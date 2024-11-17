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

/**
 * {@code Patient} class which inherits from {@code HospitalStaff}, with the {@code Patient}'s medical history, an array of {@code MedicalRecord}s.
 */
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


    /**
     * Constructor for {@code Patient}
     * @param userID
     * @param name
     * @param age
     * @param gender
     * @param domain
     * @param contactInfo
     * @param dateOfBirth
     * @param bloodType
     */
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

    /**
     * The getter method of a {@code Patient}'s {@code HospitalID}
     * @return
     */
    @Override
    public String getUserID() {
        return this.userID;
    }

    /**
     * The getter method of a {@code Patient}'s name
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * The getter method of a {@code Patient}'s age
     * @return
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * The getter method of a {@code Patient}'s gender
     * @return
     */
    @Override
    public Gender getGender() {
        return this.gender;
    }

    /**
     * The getter method of a {@code Patient}'s domain
     * @return
     */
    @Override
    public Domain getDomain() {
        return this.domain;
    }

    /**
     * The getter method of a {@code Patient}'s contact information
     * @return
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * The getter method of a {@code Patient}'s date of birth
     * @return
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * The getter method of a {@code Patient}'s blood type
     * @return
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * The setter method of a {@code Patient}'s {@code HospitalID}
     * @param userId
     */
    @Override
    public void setUserID(String userId) {
        this.userID = userId;
    }

    /**
     * The setter method of a {@code Patient}'s name
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The setter method of a {@code Patient}'s age
     * @param age
     */
    @Override
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * The setter method of a {@code Patient}'s gender
     * @param gender
     */
    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * The setter method of a {@code Patient}'s domain
     * @param domain
     */
    @Override
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /**
     * Null function
     * @param medicalRecord
     */
    public void updateMedicalHistory(MedicalRecord medicalRecord){
        // uses medical history repository
    }

    /**
     * The setter method of a {@code Patient}'s contact information
     * @param contactInfo
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * The getter method for the {@code Patient} list of an {@code Patient}
     * @return The {@code Patient} list
     */
    public static ArrayList<Patient> getPatientList(){
    	return Patient.patientList;
    }

    /**
     * The setter method of a {@code Patient}'s date of birth
     * @param dateOfBirth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * The setter method of a {@code Patient}'s blood type
     * @param bloodType
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}
