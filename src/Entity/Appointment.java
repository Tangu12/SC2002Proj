package Entity;

import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private Boolean availability;
    private String appointmentID;
    private LocalDateTime time;
    private String doctorID;
    private String doctorName;
    private String patientID;
    private String patientName;
    private Purpose purposeOfAppointment;
    private Department appointmentDepartment;
    private Status statusOfAppointment;
    private String appointOutcomeRecord;




    public Appointment(Boolean availability, String appointmentID, String time, String doctorID, String doctorName, String patientID, String patientName, Purpose purposeOfAppointment, Department appointmentDepartment, Status statusOfAppointment, String appointmentOutcomeRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.availability = availability;
        this.appointmentID = appointmentID;
        this.time = LocalDateTime.parse(time, formatter);
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.patientID = patientID;
        this.patientName = patientName;
        this.purposeOfAppointment = purposeOfAppointment;
        this.appointmentDepartment = appointmentDepartment;
        this.statusOfAppointment = statusOfAppointment;
        this.appointOutcomeRecord = appointmentOutcomeRecord;
    }

    public boolean getAvail() {
        return this.availability;
    }

    public String getAppID() {
        return this.appointmentID;
    }

    public LocalDateTime getTimeOfApp() {
        return this.time;
    }

    public String getPatID() {
        return this.patientID;
    }

    public String getPatName() {
        return this.patientName;
    }

    public String getDocID() {
        return this.doctorID;
    }

    public String getDocName() {
        return this.doctorName;
    }

    public Purpose getPurposeOfApp() {
        return this.purposeOfAppointment;
    }

    public Department getAppointmentDepartment() {
        return this.appointmentDepartment;
    }

    public Status getStatusOfApp() {
        return this.statusOfAppointment;
    }

    public String getAppointOutcomeRecord() {
        return this.appointOutcomeRecord;
    }

    public void setAppID(String appID) {
        this.appointmentID = appID;
    }

    public void setTimeOfApp(LocalDateTime time) {
        this.time = time;
    }

    public void setPatID(String patID) {
        this.patientID = patID;
    }

    public void setPatName(String patName) {
        this.patientName = patName;
    }

    public void setDocID(String docID) {
        this.doctorID = docID;
    }

    public void setDocName(String docName) {
        this.doctorName = docName;
    }

    public void setPurposeOfApp(Purpose purposeOfAppointment) {
        this.purposeOfAppointment = purposeOfAppointment;
    }

    public void setDepartmentOfAppointment(Department appointmentDepartment) {
        this.appointmentDepartment = appointmentDepartment;
    }

    public void setStatusOfApp(Status statusOfAppointment) {
        this.statusOfAppointment = statusOfAppointment;
    }

    public void setAppointOutcomeRecord(String appointOutcomeRecord) {
        this.appointOutcomeRecord = appointOutcomeRecord;
    }

}
