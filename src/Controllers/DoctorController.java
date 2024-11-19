package Controllers;

import Controllers.IController.IAppointment.IDoctorAppointment;
import Entity.Enums.Department;
import Entity.Enums.Status;
import Entity.User.Doctor;
import Services.AppointmentService;

/**
 * {@code DoctorController} handles all the logic a Doctor's Functions
 */
public class DoctorController implements IDoctorAppointment {

	private Doctor doctor;
	private AppointmentService appointmentService;

	/**
	 * Constructor of {@code DoctorController}
	 *
	 * @param doc
	 * @param appService
	 */
	public DoctorController(Doctor doc, AppointmentService appService) {
		this.doctor = doc;
		this.appointmentService = appService;
	}

	/**
	 * The getter method of the instance of a {@code Doctor}
	 *
	 * @return The instance of the {@code Doctor}
	 */
	public Doctor getDoctor() {
		return this.doctor;
	}

	/**
	 * Calls the createAppointmentSlot function of the {@code AppointmentService} class
	 *
	 * @param docID     the unique ID of the doctor creating the appointment slot
	 * @param docName   the name of the doctor creating the appointment slot
	 * @param plusDays  the number of days from today when the appointment slot should be created
	 * @param dep       the {@code Department} of the doctor for which the appointment slot is being created
	 * @param startTime the start time of the appointment slot (in 24-hour format, e.g., 9 for 9 AM)
	 * @param endTime   the end time of the appointment slot (in 24-hour format, e.g., 17 for 5 PM)
	 */
	public void createAppointmentSlot(String docID, String docName, int plusDays, Department dep, int startTime, int endTime) {
		appointmentService.createAppointmentSlot(docID, docName, plusDays, dep, startTime, endTime);
		appointmentService.updateAppointmentFile();
	}

	/**
	 * Calls the acceptOrDeclinePendingApp function of the {@code AppointmentService} class
	 *
	 * @param index    the index of the pending appointment in the list to accept or decline
	 * @param decision the decision for the pending appointment (e.g., 1 for accept, 0 for decline)
	 */
	public void acceptOrDeclinePendingApp(int index, int decision) {
		appointmentService.acceptOrDeclinePendingApp(index, decision);
		appointmentService.updateAppointmentFile();
	}

	/**
	 * Calls the updateApptOutcomeRecords function of the {@code AppointmentService} class
	 *
	 * @param index  the index of the appointment in the list for which the outcome is being updated
	 * @param status the {@code Status} of the appointment after the update (e.g., COMPLETED, CANCELLED)
	 * @param notes  additional notes or details regarding the appointment outcome
	 */
	public void updateApptOutcomeRecords(int index, Status status, String notes) {
		appointmentService.updateApptOutcomeRecords(index, status, notes);
		appointmentService.updateAppointmentFile();
	}
}
