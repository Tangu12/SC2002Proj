package Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;
import Entity.Repository.AppointmentsRepository;
import Entity.User.Patient;

/**
 * {@code AppointmentService} class which handles all the logic dealing with the {@code Appointment} class
 */
public class AppointmentService {
	private AppointmentsRepository appointmentsRepository;

	/**
	 * Constructor for {@code AppointmentService}
	 * @param appointmentsRepository appointmentsRepository
	 */
	public AppointmentService(AppointmentsRepository appointmentsRepository) {
		this.appointmentsRepository = appointmentsRepository;
	}

	/**
	 * Schedules a {@code Appointment} for a {@code Patient} by:
	 * <ul>
	 *     <li> Setting the availability of the {@code Appointment} to False</li>
	 *     <li> Attaching the {@code Patient}'s {@code HospitalID} and {@code Patient}'s name to the {@code Appointment} </li>
	 *     <li> Setting the purpose of the {@code Appointment} to whichever purpose chosen by the {@code Patient} </li>
	 *     <li> Setting the status of the {@code Appointment} to {@code Pending} </li>
	 * </ul>
	 *
	 * @param pat patient name
	 * @param appIndex appoointment index in array list
	 * @param pur purpose of appointment
	 */
	public void scheduleAppointment(Patient pat, int appIndex, Purpose pur) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setAvail(false);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatID(pat.getUserID());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatName(pat.getName());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPurposeOfApp(pur);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(Status.Pending);
		updateAppointmentFile();
    }

	/**
	 * Cancelling a {@code Appointment} by:
	 * <ul>
	 *     <li> Setting the availability of the {@code Appointment} to True </li>
	 *     <li> Setting the status of the {@code Appointment} to {@code Cancelled} </li>
	 * </ul>
	 * @param oldID appointmentID of the old appointment
	 */
	public void cancelAppointment(int oldID) {
		AppointmentList.getInstance().getAppointmentList().get(oldID).setStatusOfApp(Status.Cancelled);
		AppointmentList.getInstance().getAppointmentList().get(oldID).setAvail(true);
		updateAppointmentFile();
    }

	/**
	 * Updates the {@code Appointment} in the {@code Appointment} file by:
	 * <ul>
	 *      <li> Updating its status in the {@code Appointment} list </li>
	 *      <li> Updating it in the {@code Appointment} file </li>
	 * </ul>
	 * @param appointment appointment to be Updated
	 * @param appIndex index of the appointment in the appointment list
	 * @param status new status of the appointment
	 */
	public void updateAppointmentStatus(Appointment appointment, int appIndex, Status status) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(status);
		appointmentsRepository.updateRecord(appointment);
		updateAppointmentFile();
	}

	/**
	 * Creates {@code Appointment} slots in the appointment list from the {@code Appointment} file
	 * @param docID the doctor's ID
	 * @param docName the doctor's name
	 * @param plusDays the number of days ahead of the appointment
	 * @param dep the department of the doctor
	 * @param startTime the starting time of the appointment
	 * @param endTime the ending time of the appointment
	 */
	public void createAppointmentSlot(String docID, String docName, int plusDays, Department dep, int startTime, int endTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter formatterForID = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		LocalDate updateDate = LocalDate.now().plusDays(plusDays);
		for(int i = startTime; i < endTime; i++) {
			LocalDateTime firstSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 0, 0));
			String newFirstSlot = String.valueOf(true) + "," + "A" + firstSlot.format(formatterForID) + docID + "," + firstSlot.format(formatter) + "," + docID + "," + docName + ", , , ," + dep.toString() + ", , , , , , "; //add space to avoid null data when retrieve data
			if(!checkAppIDExist("A" + firstSlot.format(formatterForID) + docID)) appointmentsRepository.createAppointments(String.join(",", newFirstSlot) + "\n");
			LocalDateTime secondSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 30, 0));
			String newSecondSlot = String.valueOf(true) + "," + "A" + secondSlot.format(formatterForID) + docID + "," + secondSlot.format(formatter) + "," + docID + "," + docName + ", , , ," + dep.toString() + ", , , , , , "; //add space to avoid null data when retrieve data
			if(!checkAppIDExist("A" + secondSlot.format(formatterForID) + docID)) appointmentsRepository.createAppointments(String.join(",", newSecondSlot) + "\n");
		}
		appointmentsRepository.sortFile();
		AppointmentList.getInstance().getAppointmentList().clear();
		AppointmentsRepository.loadAppointments();
	}

	/**
	 * Checks if the entered appointmentID is a valid {@code HospitalID}
	 * @param appID
	 * @return True if the entered appointmentID is a valid {@code HospitalID}
	 */
	public boolean checkAppIDExist(String appID){
		for(Appointment app : AppointmentList.getInstance().getAppointmentList()) {
			if(app.getAppID().equals(appID)) return true;
		}
		return false;
	}

	/**
	 * Function to set the {@code Appointment}'s status to {@code Confirmed} or {@code Cancelled} based on the {@code Doctor}'s decision
	 * @param index
	 * @param decision
	 */
	public void acceptOrDeclinePendingApp(int index, int decision) {
		if(decision == 1) AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(Status.Confirmed);
		else {
			AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(Status.Cancelled);
			AppointmentList.getInstance().getAppointmentList().get(index).setAvail(true);
		}
		updateAppointmentFile();
	}

	/**
	 * Updates the Appointment Outcome Record of an {@code Appointment} by:
	 * <ul>
	 *     <li> Updating its status </li>
	 *     <li> Updating the Doctor's Notes of the {@code Appointment} </li>
	 * </ul>
	 * @param index
	 * @param status
	 * @param notes
	 */
	public void updateApptOutcomeRecords(int index, Status status, String notes) {
		AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(status);
		AppointmentList.getInstance().getAppointmentList().get(index).setAppointOutcomeRecord(notes);
		updateAppointmentFile();
	}

	/**
	 * Adds a new Prescription to a {@code Appointment} based on its AppointmentID
	 * @param appointment
	 * @param dateIssued
	 * @param medicine
	 * @param dosage
	 * @param instructions
	 */
	public void addNewPrescription(Appointment appointment, String dateIssued, String medicine, String dosage, String instructions) {
        appointment.setLocalDate(dateIssued);
        appointment.setMedicine(medicine);
        appointment.setDosage(dosage);
        appointment.setInstructions(instructions);
        System.out.println("New prescription added successfully.");
        updateAppointmentFile();
    }

	/**
	 * Cleans up {@code Appointments} that are unscheduled and have already passed
	 */
	public void cleanUpUnscheduledAppointments() {
		List<Appointment> appointmentList = AppointmentList.getInstance().getAppointmentList();
	    // Use an iterator to avoid ConcurrentModificationException
	    Iterator<Appointment> iterator = appointmentList.iterator();
	    while (iterator.hasNext()) {
	        Appointment appointment = iterator.next();
	        if (appointment.getAvail() && appointment.getTimeOfApp().isBefore(LocalDateTime.now())) {
	            iterator.remove(); // Safely removes the current element
	        }
	    }
	    updateAppointmentFile();
	}
	
	
	/**
	 * Calls updateAppointmentFile function from the {@code AppointmentsRepository} class 
	 */
	public void updateAppointmentFile() {
		AppointmentsRepository.updateAppointmentFile(AppointmentList.getInstance().getAppointmentList());
	}
}
