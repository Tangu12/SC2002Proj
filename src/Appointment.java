

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum purpose {
	CheckUp,
	Surgery,
	Consultation,
	Other
	}

enum status {
	Confirmed,
	Cancelled,
	Completed
}

enum paymentStatus{
	Prepaid,
	Pending,
	Completed,
	Overdued,
	Cancelled
}

public class Appointment {
	private String appointmentID;
	private LocalDateTime time;
	private String patientID;
	private String patientName;
	private String doctorID;
	private String doctorName;
	private purpose purposeOfAppointment;
	private status statusOfAppointment;
	private double costOfAppointment;
	private paymentStatus paymentStatus;
	private boolean availability;
	
	public Appointment(String appointmentID, String time, String doctorID, String doctorName) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		this.appointmentID = appointmentID;
		this.time = LocalDateTime.parse(time, formatter);
		this.doctorID = doctorID;
		this.doctorName = doctorName;
		this.availability = true;
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
	
	public status getStatusOfApp() {
		return this.statusOfAppointment;
	}
	
	public double getCostOfApp() {
		return this.costOfAppointment;
	}
	
	public paymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}
	
	public boolean getAvail() {
		return this.availability;
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
	
	public void setStatusOfApp(status statusOfAppointment) {
		this.statusOfAppointment = statusOfAppointment;
	}
	
	public void setCostOfApp(double costOfAppointment) {
		this.costOfAppointment = costOfAppointment;
	}
	
	public void setPaymentStatus(paymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
