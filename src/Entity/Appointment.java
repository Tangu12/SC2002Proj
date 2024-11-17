package Entity;

import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code Appointment} Class
 */
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
	private String medicine;
	private LocalDate medicineIssuedDate;
	private String dosage;
	private String instructions;

	/**
	 * Constructor for {@code Appointment}
	 * @param availability
	 * @param appointmentID
	 * @param time
	 * @param doctorID
	 * @param doctorName
	 * @param patientID
	 * @param patientName
	 * @param purposeOfAppointment
	 * @param appointmentDepartment
	 * @param statusOfAppointment
	 * @param appointmentOutcomeRecord
	 * @param medicine
	 * @param medicineIssuedDate
	 * @param dosage
	 * @param instructions
	 */
	public Appointment(Boolean availability, String appointmentID, String time, String doctorID, String doctorName, String patientID, String patientName, 
			Purpose purposeOfAppointment, Department appointmentDepartment, Status statusOfAppointment, String appointmentOutcomeRecord,
			String medicine, String medicineIssuedDate, String dosage, String instructions) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy"); 
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
		this.medicine = medicine;
		if(medicineIssuedDate.equals(" ")) this.medicineIssuedDate = null;
		else this.medicineIssuedDate = LocalDate.parse(medicineIssuedDate, formatterDate);
		this.dosage = dosage;
		this.instructions = instructions;
	}

	/**
	 * The getter method of a {@code Appointment}'s availability
	 * @return The availability of a {@code Appointment}
	 */
	public boolean getAvail() {
		return this.availability;
	}

	/**
	 * The getter method of a {@code Appointment}'s appointmentID
	 * @return The appointmentID of the {@code Appointment}
	 */
	public String getAppID() {
		return this.appointmentID;
	}

	/**
	 * The getter method of a {@code Appointment}'s time
	 * @return The time of the {@code Appointment}
	 */
	public LocalDateTime getTimeOfApp() {
		return this.time;
	}

	/**
	 * The getter method of the {@code Patient}'s {@code HospitalID} for a {@code Appointment}
	 * @return The {@code HospitalID} of the {@code Patient} for that {@code Appointment}
	 */
	public String getPatID() {
		return this.patientID;
	}

	/**
	 * The getter method of a {@code Appointment}'s {@code Patient} name
	 * @return The name of the {@code Patient} for that {@code Appointment}
	 */
	public String getPatName() {
		return this.patientName;
	}

	/**
	 * The getter method of the {@code Doctor}'s {@code HospitalID} for a {@code Appointment}
	 * @return The {@code HospitalID} of the {@code Doctor} for that {@code Appointment}
	 */
	public String getDocID() {
		return this.doctorID;
	}

	/**
	 * The getter method of a {@code Appointment}'s {@code Doctor} name
	 * @return The name of the {@code Doctor} for that {@code Appointment}
	 */
	public String getDocName() {
		return this.doctorName;
	}

	/**
	 * The getter method of a {@code Appointment}'s purpose
	 * @return The purpose of the {@code Appointment}
	 */
	public Purpose getPurposeOfApp() {
		return this.purposeOfAppointment;
	}

	/**
	 * The getter method of the department of a {@code Appointment}
	 * @return The department of a {@code Appointment}
	 */
	public Department getAppointmentDepartment() {
		return this.appointmentDepartment;
	}

	/**
	 * The getter method of a {@code Appointment}'s status
	 * @return The status of the {@code Appointment}
	 */
	public Status getStatusOfApp() {
		return this.statusOfAppointment;
	}

	/**
	 * The getter method of a {@code Appointment}'s outcome record
	 * @return The {@code Appointment}'s outcome record
	 */
	public String getAppointOutcomeRecord() {
		return this.appointOutcomeRecord;
	}

	/**
	 * The getter method of a {@code Appointment}'s {@code Medicine}
	 * @return The medicine prescribed in the {@code Appointment}
	 */
	public String getMedicine() {
		return this.medicine;
	}

	/**
	 * The getter method of a {@code Appointment}'s {@code Medicine} issue date
	 * @return The date at which the {@code Medicine} for that {@code Appointment} was issued
	 */
	public LocalDate getMedicineIssuedDate() {
		return this.medicineIssuedDate;
	}

	/**
	 * The getter method of a {@code Appointment}'s {@code Medicine} dosage
	 * @return The dosage of a {@code Appointment}'s {@code Medicine}
	 */
	public String getDosage() {
		return this.dosage;
	}

	/**
	 * The getter method of the instructions of taking the {@code Medicine} of a {@code Appointment}
	 * @return The instructions of taking the {@code Medicine} of a {@code Appointment}'
	 */
	public String getInstructions() {
		return this.instructions;
	}

	/**
	 * The setter method of a {@code Appointment}'s availability
	 * @param avail
	 */
	public void setAvail(boolean avail) {
		this.availability = avail;
	}

	/**
	 * The setter method of a {@code Appointment}'s appointmentID
	 * @param appID
	 */
	public void setAppID(String appID) {
		this.appointmentID = appID;
	}

	/**
	 * The setter method of a {@code Appointment}'s time
	 * @param time
	 */
	public void setTimeOfApp(LocalDateTime time) {
		this.time = time;
	}

	/**
	 * The setter method of the {@code HospitalID} of a {@code Patient} for a {@code Appointment}
	 * @param patID
	 */
	public void setPatID(String patID) {
		this.patientID = patID;
	}

	/**
	 * The setter method of the {@code Patient} name of a {@code Appointment}
	 * @param patName
	 */
	public void setPatName(String patName) {
		this.patientName = patName;
	}

	/**
	 * The setter method of the {@code HospitalID} of a {@code Doctor} for a {@code Appointment}
	 * @param docID
	 */
	public void setDocID(String docID) {
		this.doctorID = docID;
	}

	/**
	 * The setter method of the {@code Doctor} name of a {@code Appointment}
	 * @param docName
	 */
	public void setDocName(String docName) {
		this.doctorName = docName;
	}

	/**
	 * The setter method of a {@code Appointment}'s purpose
	 * @param purposeOfAppointment
	 */
	public void setPurposeOfApp(Purpose purposeOfAppointment) {
		this.purposeOfAppointment = purposeOfAppointment;
	}

	/**
	 * The setter method of the department of a {@code Appointment}
	 * @param appointmentDepartment
	 */
	public void setDepartmentOfAppointment(Department appointmentDepartment) {
		this.appointmentDepartment = appointmentDepartment;
	}

	/**
	 * The setter method of a {@code Appointment}'s status
	 * @param statusOfAppointment
	 */
	public void setStatusOfApp(Status statusOfAppointment) {
		this.statusOfAppointment = statusOfAppointment;
	}

	/**
	 * The setter method of a {@code Appointment}'s outcome record
	 * @param appointOutcomeRecord
	 */
	public void setAppointOutcomeRecord(String appointOutcomeRecord) {
		this.appointOutcomeRecord = appointOutcomeRecord;
	}

	/**
	 * The setter method of a {@code Appointment}'s {@code Medicine}
	 * @param med
	 */
	public void setMedicine(String med) {
		this.medicine = med;
	}

	/**
	 * The setter method of a {@code Appointment}'s {@code Medicine} issue date
	 * @param date
	 */
	public void setLocalDate(String date) {
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy"); 
		this.medicineIssuedDate = LocalDate.parse(date, formatterDate);
	}

	/**
	 * The setter method of a {@code Appointment}'s {@code Medicine} dosage
	 * @param dosage
	 */
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	/**
	 * The setter method of the instructions of taking the {@code Medicine} of a {@code Appointment}
	 * @param instructions
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
}
