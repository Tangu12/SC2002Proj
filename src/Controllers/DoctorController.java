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

public class DoctorController {
	
	private Doctor doctor;
	private AppointmentService appointmentService;
	
	public DoctorController(Doctor doc, AppointmentService appService) {
		this.doctor = doc;
		this.appointmentService = appService;
	}
	
	public Doctor getDoctor() {
		return this.doctor;
	}
	
	public void createAppointmentSlot(String docID, String docName, int plusDays, Department dep, int startTime, int endTime) {
		appointmentService.createAppointmentSlot(docID, docName, plusDays, dep, startTime, endTime);
	}
	
	public void acceptOrDeclinePendingApp(int index, int decision) {
		appointmentService.acceptOrDeclinePendingApp(index, decision);
	}
	
	public void updateApptOutcomeRecords(int index, Status status, String notes) {
		appointmentService.updateApptOutcomeRecords(index, status, notes);
	}
}
