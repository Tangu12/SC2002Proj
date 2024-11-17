package Boundary.UserUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

import Controllers.DoctorController;
import Controllers.PatientController;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Status;
import Entity.User.Doctor;
import Entity.User.Patient;
import Services.InputService;

public class DoctorMainPage extends UserMainPage {

	private static int columnWidth = 20;
	private DoctorController doctorController;
	//private PatientController patientController;
	
	public DoctorMainPage(DoctorController docCon) {
		this.doctorController = docCon;
		//this.patientController = patCon;
	}
	
	 public void homePage() {
        int choice;
        do {
            System.out.println("Choose the number of function:\n"
                    + "(1) View Patient Medical Records\n"
                    // + "(2) Update Patient Medical Records\n" already updating in below choices
                    + "(2) View Personal Schedule\n"
                    + "(3) Set Availability for Appointments\n"
                    + "(4) Accept or Decline Appointment Requests\n"
                    + "(5) View Upcoming Appointments\n"
                    + "(6) Record Appointment Outcome\n"
                    + "(7) Exit\n");

            try {
                choice = InputService.inputInteger();

                switch (choice) {
                    case 1:
                        viewMedicalRecord();
                        break;
                    case 2:
                        // View Personal Schedule
                        viewPersonalSchedule(doctorController.getDoctor());
                        break;
                    case 3:
                        // Set availability for appointments
                        setAvailability();
                        break;
                    case 4:
                        // Accept or decline appointments
                        acceptOrDeclinePendingApp();
                        break;
                    case 5:
                        // View upcoming appointments
                        viewDoctorScheduledAppointments();
                        break;
                    case 6:
                        // Record Appt Outcome Records
                        updateApptOutcomeRecords();
                        break;
                    case 7:
                        System.out.println("Thank you for using our service!!");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                choice = -1;
            }
        } while (choice != 7);
    }
	 
	 public void viewPersonalSchedule(Doctor doc) {
        System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");

		System.out.println("|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("App Date and Time", columnWidth)
				+ "|" + formatCell("Patient ID", columnWidth)
				+ "|" + formatCell("Patient Name", columnWidth)
				+ "|" + formatCell("Purpose", columnWidth)
				+ "|" + formatCell("App Status", columnWidth) + "|");

		System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        for (Appointment appointments : AppointmentList.getInstance().getAppointmentList()) {
            if (appointments.getDocID().equals(doc.getUserID())) {
                System.out.println("|" + formatCell(appointments.getAppID(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell(appointments.getPatID(), columnWidth)
						+ "|" + formatCell(appointments.getPatName(), columnWidth)
						+ "|" + formatCell((appointments.getPurposeOfApp() == null) ? " " : appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell((appointments.getStatusOfApp() == null) ? " " : appointments.getStatusOfApp().toString(), columnWidth) + "|");
				System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+");
            }
        }
	 }
	 
	 public void setAvailability() {
	        int choice;
	        do {
	            System.out.println("Please enter your choice to update your shift for upcoming week: \n"
	                    + "(1) " + LocalDate.now() + "\n"
	                    + "(2) " + LocalDate.now().plusDays(1) + "\n"
	                    + "(3) " + LocalDate.now().plusDays(2) + "\n"
	                    + "(4) " + LocalDate.now().plusDays(3) + "\n"
	                    + "(5) " + LocalDate.now().plusDays(4) + "\n"
	                    + "(6) " + LocalDate.now().plusDays(5) + "\n"
	                    + "(7) " + LocalDate.now().plusDays(6) + "\n"
	                    + "(8) Exit");
	            choice = InputService.inputInteger();
	            switch(choice) {
	                case 1:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 0, doctorController.getDoctor().getDepartment());
	                    break;
	                case 2:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 1, doctorController.getDoctor().getDepartment());
	                    break;
	                case 3:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 2, doctorController.getDoctor().getDepartment());
	                    break;
	                case 4:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 3, doctorController.getDoctor().getDepartment());
	                    break;
	                case 5:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 4, doctorController.getDoctor().getDepartment());
	                    break;
	                case 6:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 5, doctorController.getDoctor().getDepartment());
	                    break;
	                case 7:
	                	createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), 6, doctorController.getDoctor().getDepartment());
	                    break;
	                case 8:
	                	//doctorController.sortFile();
	                    System.out.println("Quitting....");
	                    break;
	                default:
	                    System.out.println("Only enter available choices!!");
	                    break;
	            }
	        } while(choice != 8);
	    }
	 
	 private static String formatCell(String value, int width) {
			if (value == " ") {
				value = "";
			}
			return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
		}
	 
	 public void createAppointmentSlot(String docID, String docName, int plusDays, Department dep) {
		 int startTime, endTime;
		 DateTimeFormatter formatterForInput = DateTimeFormatter.ofPattern("d/M/yyyy");
		 LocalDate updateDate = LocalDate.now().plusDays(plusDays);
		 printAppointmentOfaDayForDoc(updateDate.format(formatterForInput), docID);
		do {
			System.out.println("Please enter when your shift starts: (0 -> 23) (-1 to return) (eg. 0 means 12am, 23 means 11pm)");
			startTime = InputService.inputInteger();
			if(startTime == -1) return;
			System.out.println("Please enter when your shift ends: (0 -> 24) (-1 to return) (eg. 0 means 12am, 23 means 11pm)");
			endTime = InputService.inputInteger();
			if(endTime == -1) return;
			if(startTime < 0 || endTime < 0 || startTime >= 24 || endTime> 24 || startTime >= endTime)
				System.out.println("Please only enter available time and endTime must be greater than startTime");
		} while(startTime < 0 || endTime < 0 || startTime >= 24 || endTime> 24 || startTime >= endTime);
		doctorController.createAppointmentSlot(doctorController.getDoctor().getUserID(), doctorController.getDoctor().getName(), plusDays, doctorController.getDoctor().getDepartment(), startTime, endTime);
		System.out.println("Appointments are created succeesully!");
	 }
	 
	 public void printAppointmentOfaDayForDoc(String date, String docID) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
			System.out.println("Below is the schedule for the day");
			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");

			System.out.println("|" + formatCell("Appointment ID", columnWidth)
					+ "|" + formatCell("App Date and Time", columnWidth)
					+ "|" + formatCell("Doctor ID", columnWidth)
					+ "|" + formatCell("Doctor Name", columnWidth)
					+ "|" + formatCell("Patient ID", columnWidth)
					+ "|" + formatCell("Patient Name", columnWidth)
					+ "|" + formatCell("Purpose Of App", columnWidth)
					+ "|" + formatCell("Status Of App", columnWidth) + "|");

			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
			
			for(Appointment app : AppointmentList.getInstance().getAppointmentList()) {
				if(app.getTimeOfApp().format(formatter).startsWith(date) && app.getDocID().equals(docID)) {
					System.out.println("|" + formatCell(app.getAppID(), columnWidth)
							+ "|" + formatCell(app.getTimeOfApp().format(formatter), columnWidth)
							+ "|" + formatCell(app.getDocID(), columnWidth)
							+ "|" + formatCell(app.getDocName(), columnWidth)
							+ "|" + formatCell(app.getPatID(), columnWidth)
							+ "|" + formatCell(app.getPatName(), columnWidth)
							+ "|" + formatCell((app.getPurposeOfApp() != null) ? app.getPurposeOfApp().toString() : " ", columnWidth)
							+ "|" + formatCell((app.getStatusOfApp() != null) ? app.getStatusOfApp().toString() : " ", columnWidth) + "|");
					System.out.println("+" + "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+");
				}
			}
		}
	 
	 public void acceptOrDeclinePendingApp() {
	        List<Integer> pendingIndices = viewDoctorPendingAppointments();
	        System.out.println("Please enter the index of the appointment that you want to accept or decline.");
	        int selection = InputService.inputInteger();
	        try {
	            System.out.println("Accept or Decline appointment: (Enter 1 or 2)\n"
	                    + "1. Accept Appointment\n"
	                    + "2. Decline Apppointment\n");
	            int decision = InputService.inputInteger();

	            if (decision == 1 || decision == 2) {
	                doctorController.acceptOrDeclinePendingApp(pendingIndices.get(selection-1), decision);
	            } else {
	            	System.out.println("Invalid Input");
	            }
	        } catch (Exception e) {
	            System.out.println("Invalid Input! Please choose only available options shown.");
	        } // Test this
	    }
	 
	 public List<Integer> viewDoctorPendingAppointments() {
	        int i = 1;
	        int index = 0;
	        List<Integer> pendingIndices = new ArrayList<>();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
	        
	        System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");

			System.out.println("|" + formatCell("No.", 3)
					+ "|" + formatCell("App Date and Time", columnWidth)
					+ "|" + formatCell("Purpose", columnWidth)
					+ "|" + formatCell("Pat Name", columnWidth) + "|");

			System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
			
	        for (Appointment appointments : AppointmentList.getInstance().getAppointmentList()) {
	            if (Objects.equals(appointments.getDocID(), doctorController.getDoctor().getUserID()) && appointments.getStatusOfApp() == Status.Pending) {
	                System.out.println("|" + formatCell(String.valueOf(i), 3)
							+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
							+ "|" + formatCell((appointments.getPurposeOfApp() == null) ? " " : appointments.getPurposeOfApp().toString(), columnWidth)
							+ "|" + formatCell(appointments.getPatName(), columnWidth) + "|");
					System.out.println("+" + "-".repeat(3) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+");
	                i++;
	                pendingIndices.add(index);
	            }
	            index++;
	        }
	        return pendingIndices;
	    }
	 
	 public List<Integer> viewDoctorScheduledAppointments() {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
	        System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");

			System.out.println("|" + formatCell("No.", 3)
					+ "|" + formatCell("App Date and Time", columnWidth)
					+ "|" + formatCell("Purpose", columnWidth)
					+ "|" + formatCell("Pat Name", columnWidth) + "|");

			System.out.println("+" + "-".repeat(3) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
	        int i = 1;
	        int index = 0;
	        List<Integer> pendingIndices = new ArrayList<>();
	        for (Appointment appointments : AppointmentList.getInstance().getAppointmentList()) {
	            if (Objects.equals(appointments.getDocID(), doctorController.getDoctor().getUserID()) && (appointments.getStatusOfApp() == Status.Confirmed || appointments.getStatusOfApp() == Status.PendingPrescription)) {
	                System.out.println("|" + formatCell(String.valueOf(i), 3)
							+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
							+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
							+ "|" + formatCell(appointments.getPatName(), columnWidth) + "|");
					System.out.println("+" + "-".repeat(3) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+");
					i++;
					pendingIndices.add(index);
	            }
	            index++;
	        }
	        return pendingIndices;
	    }
	 
	 public void updateApptOutcomeRecords() {
 		 List<Integer> pendingIndices = viewDoctorScheduledAppointments();
 		 if (pendingIndices.isEmpty()) {
 			 System.out.println("There is no scheduled appointment for you.");
 			 return;
 		 }
	     System.out.println("Select one of the record you would like to update: (enter the index eg. 1)");
	     int selection = InputService.inputInteger();
	     if(selection-1 >= pendingIndices.size()) {System.out.println("Please only enter the available records:"); return;}
	     System.out.println("Please enter your notes (Doctor's notes):");
	     String doctorNotes = InputService.inputString().trim();
	     System.out.println("Please select the status of the appointment:\n"
	     		+ "(1) Confirmed\n"
	     		+ "(2) Cancelled\n"
	     		+ "(3) Completed\n"
	     		+ "(4) Pending\n"
	     		+ "(5) Unavailable\n"
	     		+ "(6) PrescriptionPending");
	     int choice = InputService.inputInteger();
	     switch(choice) {
		        case 1:
		        		doctorController.updateApptOutcomeRecords(pendingIndices.get(selection-1), Status.Confirmed, doctorNotes);
		        		break;
		        case 2:
		        		doctorController.updateApptOutcomeRecords(pendingIndices.get(selection-1), Status.Cancelled, doctorNotes);
		        		break;
		        case 3:
		        		doctorController.updateApptOutcomeRecords(pendingIndices.get(selection-1), Status.Completed, doctorNotes);
		        		break;
		        case 4:
		        		doctorController.updateApptOutcomeRecords(pendingIndices.get(selection-1), Status.Pending, doctorNotes);
		        		break;
		        case 5:
		        		doctorController.updateApptOutcomeRecords(pendingIndices.get(selection-1), Status.Unavailable, doctorNotes);
		        		break;
		        case 6:
		        		doctorController.updateApptOutcomeRecords(pendingIndices.get(selection-1), Status.PendingPrescription, doctorNotes);
		        		break;
	        	default:
		        		System.out.println("Please only select the available status.");
		        		return;
	     }
	 }
	 
	 public void viewMedicalRecord() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
	        System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(50) + "+"
					+ "-".repeat(50) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");

			System.out.println("|" + formatCell("Appointment ID", columnWidth)
					+ "|" + formatCell("Patient Name", columnWidth)
					+ "|" + formatCell("Date of Birth", columnWidth)
					+ "|" + formatCell("Gender", columnWidth)
					+ "|" + formatCell("Blood Type", columnWidth)
					+ "|" + formatCell("Email", columnWidth)
					+ "|" + formatCell("Doctor Name", columnWidth) 
					+ "|" + formatCell("Appointment Outcomes", 50) 
					+ "|" + formatCell("Medicine", 50) 
					+ "|" + formatCell("Date Issued", columnWidth) 
					+ "|" + formatCell("Dosage", columnWidth) 
					+ "|" + formatCell("Instructions", columnWidth)+ "|");

			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(50) + "+"
					+ "-".repeat(50) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
			
			for(Patient pat : Patient.getPatientList()) {
				for(Appointment appointments : AppointmentList.getInstance().getAppointmentList()) {
					if(appointments.getPatID().equals(pat.getUserID()) && (appointments.getStatusOfApp() == Status.Completed || appointments.getStatusOfApp() == Status.PendingPrescription)) {
						System.out.println("|" + formatCell(appointments.getAppID(), columnWidth)
						+ "|" + formatCell(pat.getName(), columnWidth)
						+ "|" + formatCell(pat.getDateOfBirth().format(formatter), columnWidth)
						+ "|" + formatCell(pat.getGender().toString(), columnWidth)
						+ "|" + formatCell(pat.getBloodType().toString(), columnWidth)
						+ "|" + formatCell(pat.getContactInfo(), columnWidth)
						+ "|" + formatCell(appointments.getDocName(), columnWidth) 
						+ "|" + formatCell(appointments.getAppointOutcomeRecord(), 50) 
						+ "|" + formatCell(appointments.getMedicine(), 50) 
						+ "|" + formatCell((appointments.getMedicineIssuedDate() != null) ? appointments.getMedicineIssuedDate().format(formatter): "N/A", columnWidth) 
						+ "|" + formatCell(appointments.getDosage(), columnWidth) 
						+ "|" + formatCell(appointments.getInstructions(), columnWidth)+ "|");
						
						System.out.println("+" + "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(50) + "+"
								+ "-".repeat(50) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+"
								+ "-".repeat(columnWidth) + "+");
					}
				}
			}
		}
}
