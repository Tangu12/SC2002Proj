package Services;

import Entity.AppointmentList;
import Entity.Enums.Purpose;
import Entity.Enums.Status;
import Entity.User.Patient;

public class AppointmentService {
	
	public void scheduleAppointment(Patient pat, int appIndex, Purpose pur) {
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setAvail(false);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatID(pat.getUserId());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPatName(pat.getName());
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setPurposeOfApp(pur);
		AppointmentList.getInstance().getAppointmentList().get(appIndex).setStatusOfApp(Status.Pending);
    }
	
	public void cancelAppointment(int oldID) {
		AppointmentList.getInstance().getAppointmentList().get(oldID).setStatusOfApp(Status.Cancelled);
		AppointmentList.getInstance().getAppointmentList().get(oldID).setAvail(true);
    }
}
