package Entity.User;

import Entity.Enums.BloodType;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

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
     * @param userID The unique identifier for the {@code Patient}. This could be an ID assigned to the patient in the hospital system (e.g., "P12345").
     * @param name The name of the {@code Patient}. This is a string representing the patient's full name (e.g., "John Doe").
     * @param age The age of the {@code Patient}. This is an integer representing the patient's age in years (e.g., 30).
     * @param gender The gender of the {@code Patient}. This is a value from the {@code Gender} enum, such as `MALE`, `FEMALE`, `OTHER`.
     * @param domain The domain or role of the {@code Patient}. This is a value from the {@code Domain} enum, which will always be `PATIENT` for this class.
     * @param contactInfo The contact information for the {@code Patient}. This is typically a phone number or email address (e.g., "123-456-7890").
     * @param dateOfBirth The date of birth of the {@code Patient}. This is a {@code LocalDate} representing the patient's birth date (e.g., `1994-08-15`).
     * @param bloodType The blood type of the {@code Patient}. This is a value from the {@code BloodType} enum, such as `A_POSITIVE`, `O_NEGATIVE`, etc.
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
     * @return The unique identifier of the {@code Patient}.
     */
    @Override
    public String getUserID() {
        return this.userID;
    }

    /**
     * The getter method of a {@code Patient}'s name
     * @return The name of the {@code Patient}.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * The getter method of a {@code Patient}'s age
     * @return The age of the {@code Patient}.
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * The getter method of a {@code Patient}'s gender
     * @return The gender of the {@code Patient}.
     */
    @Override
    public Gender getGender() {
        return this.gender;
    }

    /**
     * The getter method of a {@code Patient}'s domain
     * @return The domain/role of the {@code Patient}, which is always `PATIENT` for this class.
     */
    @Override
    public Domain getDomain() {
        return this.domain;
    }

    /**
     * The getter method of a {@code Patient}'s contact information
     * @return The contact information of the {@code Patient}, such as phone number or email.
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * The getter method of a {@code Patient}'s date of birth
     * @return The date of birth of the {@code Patient}.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * The getter method of a {@code Patient}'s blood type
     * @return The blood type of the {@code Patient}.
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * The setter method of a {@code Patient}'s {@code HospitalID}
     * @param userId The unique identifier to set for the {@code Patient}. This ID is used to track the patient in the hospital system.
     */
    @Override
    public void setUserID(String userId) {
        this.userID = userId;
    }

    /**
     * The setter method of a {@code Patient}'s name
     * @param name The name to set for the {@code Patient}.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The setter method of a {@code Patient}'s age
     * @param age The age to set for the {@code Patient}.
     */
    @Override
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * The setter method of a {@code Patient}'s gender
     * @param gender The gender to set for the {@code Patient}. This is a value from the {@code Gender} enum.
     */
    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * The setter method of a {@code Patient}'s domain
     * @param domain The domain to set for the {@code Patient}. This will always be `PATIENT` for this class.
     */
    @Override
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /**
     * The setter method of a {@code Patient}'s contact information
     * @param contactInfo The contact information to set for the {@code Patient}.
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
     * @param dateOfBirth The date of birth to set for the {@code Patient}.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * The setter method of a {@code Patient}'s blood type
     * @param bloodType The blood type to set for the {@code Patient}. This is a value from the {@code BloodType} enum.
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}
