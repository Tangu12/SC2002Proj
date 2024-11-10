package Entity;

public class MedicalRecord {
    private String appointmentID;
    private String patientId;
    public String name;
    public String dateOfBirth;
    public String gender;
    public String bloodType;
    public String phoneNumber;
    public String emailAddress;
    public String diagnosis;
    public String prescriptions;


    public MedicalRecord(String appointmentID,String patientId, String name, String dateOfBirth, String gender, String bloodType, String phoneNumber, String emailAddress, String diagnosis, String prescriptions) {
        this.appointmentID = appointmentID;
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

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

    public void setPastDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescriptions(String prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAppointmentID() {
        return appointmentID;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescriptions() {
        return prescriptions;
    }

    public String getBloodType() {
        return bloodType;
    }
}
