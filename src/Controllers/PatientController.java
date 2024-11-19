package Controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import Controllers.IController.IAppointment.IPatientAppointment;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.User.Patient;
import Services.AppointmentService;
import Services.InputService;
import Services.UserAccount.PatientAccountService;

/**
 * {@code PatientController} handles all the logic a Patient's Functions
 */
public class PatientController implements IPatientAppointment {
	private Patient patient;
	private AppointmentService appointmentService;
	private PatientAccountService patientAccountService;

    /**
     * Constructor for {@code PatientController}
     *
     * @param pat the {@code Patient} instance representing the logged-in patient
     * @param appService the {@code AppointmentService} instance used for handling appointment-related operations
     * @param patientAccountService the {@code PatientAccountService} instance used for managing patient accounts
     */
    public PatientController(Patient pat, AppointmentService appService, PatientAccountService patientAccountService) {
		this.patient = pat;
		this.appointmentService = appService;
		this.patientAccountService = patientAccountService;
	}

    /**
     * Displays the {@code Doctor}s that are in the same Department as the requested {@code Appointment} by the {@code Patient}
     *
     * @param purposeOfVisit    the purpose of the appointment, provided by the patient
     * @param appDepartment     the {@code Department} in which the appointment is being scheduled
     * @param appointmentList   a list of {@code Appointment}s available for selection
     * @return The selected doctor's name as a {@code String}, or specific return values like "NOAVAILABLEDOCTORS" or "INDEXOUTOFBOUNDS" for certain outcomes
     * @throws Exception if an invalid selection leads to cancellation
     */
	public String selectionOfDoctor(Purpose purposeOfVisit, Department appDepartment, ArrayList<Appointment> appointmentList) throws Exception {
        // create a array and refer to elements by their index
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
            selection = InputService.inputInteger();
            if(selection == -1) return null;
            else if(selection>doctors.size() || selection <= 0) {
            		System.out.println("Please only select the available doctor!!");
            		return null;
            }
            return doctors.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Back to Home Page");
            return "INDEXOUTOFBOUNDS";
        }
        if (selection == -1) { // add this to cancel
            throw new Exception();
        }
        return null;
    }

    /**
     * Handles the selection of a time slot from a list of available time slots
     * @param appointmentList   a list of {@code Appointment}s from which time slots are being chosen
     * @param timeSlotsIndices  a list of indices representing available time slots in the {@code appointmentList}
     * @param selection         the index of the time slot selected by the patient (1-based index)
     * @return The index of the selected time slot from the {@code timeSlotsIndices} list, or 0 if the selection is invalid
     */
	public int selectionOfTimeSlot(ArrayList<Appointment> appointmentList, List<Integer> timeSlotsIndices, int selection) {
        try {
            return timeSlotsIndices.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Back to Home Page.");
        }
        return 0;
    }

    /**
     * Retrieves the appointment ID index corresponding to the user's time slot selection
     * @param appointmentList a list of {@code Appointment}s available for scheduling
     * @param possibleAppsIndices a list of indices representing appointments available for the selected time slot
     * @param selection the index of the appointment selected by the patient (1-based index)
     * @return The index of the selected appointment from the {@code possibleAppsIndices} list, or 0 if the selection is invalid
     */
	public int getAppIdIndexFromTime(ArrayList<Appointment> appointmentList, List<Integer> possibleAppsIndices, int selection) {
        try {
            return possibleAppsIndices.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range.");
        }
        return 0;
    }

    /**
     * Calls scheduleAppointment function from the {@code AppointmentService} class
     * @param pat instance of Patient
     * @param appIndex index of appointment in the appointment list
     * @param pur purpose of the appointment
     */
	public void scheduleAppointment(Patient pat, int appIndex, Purpose pur) {
		appointmentService.scheduleAppointment(pat, appIndex, pur);
	}

    /**
     * Calls cancelAppointment function from the {@code AppointmentService} class
     * @param oldIndex index of appointment to be cancelled in the appointment list
     */
	public void cancelAppointment(int oldIndex) {
		appointmentService.cancelAppointment(oldIndex);
	}

    /**
     * The getter method of the instance of a {@code Patient}
     * @return The instance of the {@code Patient}
     */
	public Patient getPatient() {
		return this.patient;
	}

    /**
     * Checks if there is a {@code Appointment} with a matching AppointmentID as the entered AppointmentID
     * @param appID the appointmentID
     * @return True if there exists an appointment with the corresponding appointmentID, False otherwise
     */
	public boolean checkAppIDExist(String appID){
		for(Appointment app : AppointmentList.getInstance().getAppointmentList()) {
			if(app.getAppID().equals(appID)) return true;
		}
		return false;
	}

    /**
     * Updates the personal information of a {@code Patient}
     * @param patID Patient's HospitalID
     * @param email new Patient's email
     */
	public void updatePersonalInformation(String patID, String email) {
		for(Patient patient : Patient.getPatientList()) {
			if(patient.getUserID().equals(patID)) {
				patient.setContactInfo(email);
				patientAccountService.updateUserData(patient);
				return;
			}
		}
		
	}
}
