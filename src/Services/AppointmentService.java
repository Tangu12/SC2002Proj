package Services;

import Entity.Appointment;
import Entity.AppointmentList;
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

}
