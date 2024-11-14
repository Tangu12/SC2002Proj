package Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MedicalRecord {
    private String appointmentID;
    private String patientId;
    public String appointmentOutcomeRecord;
    public String medicine;
    public LocalDate medicineIssueDate;
    public String dosage;
    public String instructions;


    public MedicalRecord(String appointmentID, String patientId, String appointmentOutcomeRecord, String medicine, String medicineIssueDate, String dosage, String instructions) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy");
        this.appointmentID = appointmentID;
        this.patientId = patientId;
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
        this.medicine = medicine;
        if(medicineIssueDate.equals(" ")) this.medicineIssueDate = null;
        else this.medicineIssueDate = LocalDate.parse(medicineIssueDate, formatterDate);
        this.dosage = dosage;
        this.instructions = instructions;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setAppointmentOutcomeRecord(String appointmentOutcomeRecord) {
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setMedicineIssueDate(String medicineIssueDate) {
        this.medicineIssueDate = medicineIssueDate;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getAppointmentOutcomeRecord() {
        return appointmentOutcomeRecord;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getMedicineIssueDate() {
        return medicineIssueDate;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }


}




