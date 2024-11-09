package Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum purpose {
    CheckUp,
    Surgery,
    Consultation,
    Other
}

enum department {
    Cardiology,
    Neurology,
    Oncology,
    Dermatology,
    Endocrinology,
    Gastroenterology,
    Nephrology,
    Pulmonology,
    Rheumatology,
    ObstetricsGynecology,
    Others
}

enum status {
    Confirmed,
    Cancelled,
    Completed,
    Pending,
    Unavailable
}



public class Appointment {

    private Boolean availability;
    private String appointmentID;
    private LocalDateTime time;
    private String doctorID;
    private String doctorName;
    private String patientID;
    private String patientName;
    private purpose purposeOfAppointment;
    private department appointmentDepartment;
    private status statusOfAppointment;
    private String appointOutcomeRecord;




    public Appointment(Boolean availability, String appointmentID, String time, String doctorID, String doctorName, String patientID, String patientName, purpose purposeOfAppointment, department appointmentDepartment, status statusOfAppointment, String appointmentOutcomeRecord) {
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

    public purpose getPurposeOfApp() {
        return this.purposeOfAppointment;
    }

    public department getAppointmentDepartment() {
        return this.appointmentDepartment;
    }

    public status getStatusOfApp() {
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

    public void setPurposeOfApp(purpose purposeOfAppointment) {
        this.purposeOfAppointment = purposeOfAppointment;
    }

    public void setDepartmentOfAppointment(department appointmentDepartment) {
        this.appointmentDepartment = appointmentDepartment;
    }

    public void setStatusOfApp(status statusOfAppointment) {
        this.statusOfAppointment = statusOfAppointment;
    }

    public void setAppointOutcomeRecord(String appointOutcomeRecord) {
        this.appointOutcomeRecord = appointOutcomeRecord;
    }

}
