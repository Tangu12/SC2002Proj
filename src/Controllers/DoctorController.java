package Controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Entity.Appointment;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;
import Entity.User.Doctor;
import Services.AppointmentService;

/**
 * {@code DoctorController} handles all the logic a Doctor's Functions
 */
public class DoctorController {
	
	private Doctor doctor;
	private AppointmentService appointmentService;

	/**
	 * Constructor of {@code DoctorController}
	 * @param doc
	 * @param appService
	 */
	public DoctorController(Doctor doc, AppointmentService appService) {
		this.doctor = doc;
		this.appointmentService = appService;
	}

	/**
	 * The getter method of the instance of a {@code Doctor}
	 * @return The instance of the {@code Doctor}
	 */
	public Doctor getDoctor() {
		return this.doctor;
	}

	/**
	 * Calls the createAppointmentSlot function of the {@code AppointmentService} class
	 * @param docID
	 * @param docName
	 * @param plusDays
	 * @param dep
	 * @param startTime
	 * @param endTime
	 */
	public void createAppointmentSlot(String docID, String docName, int plusDays, Department dep, int startTime, int endTime) {
		appointmentService.createAppointmentSlot(docID, docName, plusDays, dep, startTime, endTime);
	}

	/**
	 * Calls the acceptOrDeclinePendingApp function of the {@code AppointmentService} class
	 * @param index
	 * @param decision
	 */
	public void acceptOrDeclinePendingApp(int index, int decision) {
		appointmentService.acceptOrDeclinePendingApp(index, decision);
	}

	/**
	 * Calls the updateApptOutcomeRecords function of the {@code AppointmentService} class
	 * @param index
	 * @param status
	 * @param notes
	 */
	public void updateApptOutcomeRecords(int index, Status status, String notes) {
		appointmentService.updateApptOutcomeRecords(index, status, notes);
	}
}
