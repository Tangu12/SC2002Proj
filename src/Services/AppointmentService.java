package Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
	 * @param appointmentsRepository
	 */
	public AppointmentService(AppointmentsRepository appointmentsRepository) {
		this.appointmentsRepository = appointmentsRepository;
	}

	/**
	 * Schedules a {@code Appointment} for a {@code Patient} by:
	 *<li> Setting the availability of the {@code Appointment} to False</li>
	 *<li> Attaching the {@code Patient}'s {@code HospitalID} and {@code Patient}'s name to the {@code Appointment} </li>
	 *<li> Setting the purpose of the {@code Appointment} to whichever purpose chosen by the {@code Patient} </li>
	 *<li> Setting the status of the {@code Appointment} to {@code Pending} </li>
	 *
	 * @param pat
	 * @param appIndex
	 * @param pur
	 */
	public void scheduleAppointment(Patient pat, int appIndex, Purpose pur) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setAvail(false);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatID(pat.getUserID());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatName(pat.getName());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPurposeOfApp(pur);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(Status.Pending);
    }

	/**
	 * Cancelling a {@code Appointment} by:
	 * <li> Setting the availability of the {@code Appointment} to True </li>
	 * <li> Setting the status of the {@code Appointment} to {@code Cancelled} </li>
	 * @param oldID
	 */
	public void cancelAppointment(int oldID) {
		AppointmentList.getInstance().getAppointmentList().get(oldID).setStatusOfApp(Status.Cancelled);
		AppointmentList.getInstance().getAppointmentList().get(oldID).setAvail(true);
    }

	/**
	 * Updates the {@code Appointment} in the {@code Appointment} file by:
	 * <li> Updating its status in the {@code Appointment} list </li>
	 * <li> Updating it in the {@code Appointment} file </li>
	 * @param appointment
	 * @param appIndex
	 * @param status
	 */
	public void updateAppointmentStatus(Appointment appointment, int appIndex, Status status) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(status);
		appointmentsRepository.updateRecord(appointment);
	}

	/**
	 * Creates {@code Appointment} slots in the
	 * @param docID
	 * @param docName
	 * @param plusDays
	 * @param dep
	 * @param startTime
	 * @param endTime
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
	}

	/**
	 * Updates the Appointment Outcome Record of a {@code Appointment} by:
	 * <li> Updating its status </li>
	 * <li> Updating the Doctor's Notes of the {@code Appointment} </li>
	 * @param index
	 * @param status
	 * @param notes
	 */
	public void updateApptOutcomeRecords(int index, Status status, String notes) {
		AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(status);
		AppointmentList.getInstance().getAppointmentList().get(index).setAppointOutcomeRecord(notes);
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
    }
}
