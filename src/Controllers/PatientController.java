package Controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Purpose;
import Entity.User.Patient;
import Services.AppointmentService;

public class PatientController {
	private Patient patient;
	private AppointmentService appointmentService;
	
	public PatientController(Patient pat, AppointmentService appService) {
		this.patient = pat;
		this.appointmentService = appService;
	}
	
	public int selectionOfTimeSlot(ArrayList<Appointment> appointmentList, List<Integer> timeSlotsIndices, int selection) {
        try {
            return timeSlotsIndices.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Back to Home Page.");
            // how to return it back to the home page?
        }
        return 0;
    }
	
	public int getAppIdIndexFromTime(ArrayList<Appointment> appointmentList, List<Integer> possibleAppsIndices, int selection) {
        try {
            return possibleAppsIndices.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Please choose a valid doctor number.");
        }
        return 0;
    }
	
	
	public void scheduleAppointment(Patient pat, int appIndex, Purpose pur) {
		appointmentService.scheduleAppointment(pat, appIndex, pur);
	}
	
	public void cancelAppointment(int oldIndex) {
		appointmentService.cancelAppointment(oldIndex);
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public boolean checkAppIDExist(String appID){
		for(Appointment app : AppointmentList.getInstance().getAppointmentList()) {
			if(app.getAppID().equals(appID)) return true;
		}
		return false;
	}
}
