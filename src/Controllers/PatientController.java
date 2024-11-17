package Controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
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
	
	public String selectionOfDoctor(Purpose purposeOfVisit, Department appDepartment, ArrayList<Appointment> appointmentList) throws Exception {
        // create a array and refer to elements by their index
		Scanner sc = new Scanner(System.in);
        int selection = 0;
        int i = 1;
        List<String> doctors = new ArrayList<>();
        for (Appointment appointments : appointmentList) {
            if (appointments.getAvail() && appointments.getAppointmentDepartment() == appDepartment) {
                String possibleDoctor = appointments.getDocName();
                if (doctors.contains(possibleDoctor)) continue;
                System.out.println(i + ". Dr. " + possibleDoctor);
                i++;
                doctors.add(possibleDoctor);
            }
        }

        if(doctors.isEmpty()){
            System.out.println("There are no available doctors at this moment.");
            return "NOAVAILABLEDOCTORS";
        }

        try {
            selection = sc.nextInt();
            sc.nextLine();
            return doctors.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Back to Home Page");
            // how to return it back to home page?
        }
        if (selection == -1) { // add this to cancel
            throw new Exception();
        }
        return null;
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
            System.out.println("Selected number is out of range.");
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
	
	public void updatePersonalInformation(String patID, String email) {
		for(Patient patient : Patient.getPatientList()) {
			if(patient.getUserID().equals(patID)) {
				patient.setContactInfo(email);
				return;
			}
		}
	}
}
