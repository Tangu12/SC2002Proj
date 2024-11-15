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

public class AppointmentService {
	private AppointmentsRepository appointmentsRepository;

	public AppointmentService(AppointmentsRepository appointmentsRepository) {
		this.appointmentsRepository = appointmentsRepository;
	}
	
	public void scheduleAppointment(Patient pat, int appIndex, Purpose pur) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setAvail(false);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatID(pat.getUserID());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatName(pat.getName());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPurposeOfApp(pur);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(Status.Pending);
    }
	
	public void cancelAppointment(int oldID) {
		AppointmentList.getInstance().getAppointmentList().get(oldID).setStatusOfApp(Status.Cancelled);
		AppointmentList.getInstance().getAppointmentList().get(oldID).setAvail(true);
    }

	// TODO Implement UpdateAppointmentStatus
	// Updates the Appointment in the Appointments file and in the AppointmentsList
	public void updateAppointmentStatus(Appointment appointment, int appIndex, Status status) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(status);
		appointmentsRepository.updateRecord(appointment);
	}
	
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
	
	public void acceptOrDeclinePendingApp(int index, int decision) {
		if(decision == 1) AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(Status.Confirmed);
		else {
			AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(Status.Cancelled);
			AppointmentList.getInstance().getAppointmentList().get(index).setAvail(true);
		}
	}
	
	public void updateApptOutcomeRecords(int index, Status status, String notes) {
		AppointmentList.getInstance().getAppointmentList().get(index).setStatusOfApp(status);
		AppointmentList.getInstance().getAppointmentList().get(index).setAppointOutcomeRecord(notes);
	}

}
