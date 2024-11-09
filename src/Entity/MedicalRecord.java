package Entity;

public class MedicalRecord {
    private String patientId;
    public String name;
    public String dateOfBirth;
    public String gender;
    public String phoneNumber;
    public String emailAddress;
    public String pastDiagnosis;
    public String pastPrescriptions;
    public String bloodType;


    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPastDiagnosis(String pastDiagnosis) {
        this.pastDiagnosis = pastDiagnosis;
    }

    public void setPastPrescriptions(String pastPrescriptions) {
        this.pastPrescriptions = pastPrescriptions;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPastDiagnosis() {
        return pastDiagnosis;
    }

    public String getPastPrescriptions() {
        return pastPrescriptions;
    }

    public String getBloodType() {
        return bloodType;
    }
}
