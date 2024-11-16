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
