package Boundary.UserUI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import Controllers.DoctorController;
import Controllers.PatientController;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;
import Entity.User.Patient;
import Services.InputService;

public class PatientMainPage extends UserMainPage{
	private PatientController patientController;
	
	public PatientMainPage(PatientController patCon) {
		this.patientController = patCon;
	}
	
	private static int columnWidth = 20;
    
    public void homePage() {
    	int choice;
        do {
			System.out.println("|  _ \\ __ _| |_(_) ___ _ __ | |_ \n" +
					"| |_) / _` | __| |/ _ \\ '_ \\| __|\n" +
					"|  __/ (_| | |_| |  __/ | | | |_ \n" +
					"|_|   \\__,_|\\__|_|\\___|_| |_|\\__|");


            System.out.println("Choose the number of function:\n"
                    + "(1) View Medical Record\n"
                    + "(2) Update Personal Information\n"
                    + "(3) View Available Appointment Slots\n"
                    + "(4) Schedule an Appointment\n" // case 1
                    + "(5) Reschedule an Appointment\n" // case 2
                    + "(6) Cancel an Appointment\n" // case 3
                    + "(7) View Scheduled Appointments\n" // case 4
                    + "(8) View Past Appointment Outcome Records\n"
                    + "(9) Exit\n");


            try {
                choice = InputService.inputInteger();
                switch (choice) {
                    case 1:
                        viewMedicalRecord(this.patientController.getPatient());
                        break;
                    case 2:
                        updatePersonalInformation(this.patientController.getPatient());
                        break;
                    case 3:
                        // View available appointment slots
                    	viewAvailableAppointmentSlots(AppointmentList.getInstance().getAppointmentList());
                        break;
                    case 4:
                    	if(scheduleAppointment(this.patientController.getPatient())) System.out.println("Your appointment is successfully scheduled");
                    	else System.out.println("Appointment is NOT scheduled!");
                        break;
                    case 5:
                        System.out.println("Enter the Appointment that you want to change: ");
                        int orgAppIDIndex = getAppIdIndexFromTime(AppointmentList.getInstance().getAppointmentList());
                        if(orgAppIDIndex == 0) break;
                        if (!patientController.checkAppIDExist(AppointmentList.getInstance().getAppointmentList().get(orgAppIDIndex).getAppID())) { // Keeping this?
                            System.out.println("Only enter available Appointment IDs");
                            break;
                        }
                        System.out.println("Select new Appointment:");
                        if(scheduleAppointment(this.patientController.getPatient())) {
                        		patientController.cancelAppointment(orgAppIDIndex);
                        		System.out.println("Your original appointment has been cancelled!!");
                        }
                        else System.out.println("Unable to schedule new appointment. No appointment is changed\n");
                        break;
                    case 6:
                        System.out.println("Enter the time slot that you want to cancel: ");
                        int oldAppIDIndex = getAppIdIndexFromTime(AppointmentList.getInstance().getAppointmentList());
                        patientController.cancelAppointment(oldAppIDIndex);
                        System.out.println("Your appointment is cancelled successfully");
                        break;
                    case 7:
                    	viewPatientScheduledAppointments(AppointmentList.getInstance().getAppointmentList(), patientController.getPatient());
                        break;
                    case 8:
                        // View Past Appointment Outcome Records
                    	viewPatientPastAppointmentOutcomeRecord(AppointmentList.getInstance().getAppointmentList(), patientController.getPatient());
                        break;
                    case 9:
                        System.out.println("Thank you for using our service!!");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 6.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                choice = -1;
            } catch (Exception e) {
				System.out.println("Error when scheduling appointment!");
				choice = -1;
			}
        } while (choice != 9);
    }
    
	public void viewAvailableAppointmentSlots(ArrayList<Appointment> appointmentList) {
			
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
	        
	        System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
	
			System.out.println("|" + formatCell("Appointment ID", columnWidth)
					+ "|" + formatCell("App Date and Time", columnWidth)
					+ "|" + formatCell("Doctor ID", columnWidth)
					+ "|" + formatCell("Doctor Name", columnWidth) + "|");
	
			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
	
			for(Appointment appointments : appointmentList) {
				if(appointments.getAvail() && !appointments.getTimeOfApp().isBefore(LocalDateTime.now())) {
					System.out.println("|" + formatCell(appointments.getAppID(), columnWidth)
							+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
							+ "|" + formatCell(appointments.getDocID(), columnWidth)
							+ "|" + formatCell(appointments.getDocName(), columnWidth) + "|");
					System.out.println("+" + "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+"
							+ "-".repeat(columnWidth) + "+");
				}
			}
	   }
	
	public void viewPatientScheduledAppointments(ArrayList<Appointment> appointmentList, Patient pat) {
    	System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+");

		System.out.println("|" + formatCell("No.", 5)
				+ "|" + formatCell("Status of App", columnWidth)
				+ "|" + formatCell("Purpose of App", columnWidth)
				+ "|" + formatCell("Time", columnWidth)
				+ "|" + formatCell("Note", 40) + "|");

		System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (appointments.getPatID().equals(pat.getUserID()) && appointments.getStatusOfApp() == Status.Confirmed) {
            	System.out.println("|" + formatCell(String.valueOf(i), 5)
            				+ "|" + formatCell(appointments.getStatusOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell("With Dr. " + appointments.getDocName(), 40) + "|");
				System.out.println("+" + "-".repeat(5) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(40) + "+");
		        i++;
            }
            if (appointments.getPatID().equals(pat.getUserID()) && appointments.getStatusOfApp() == Status.Pending) {
            	System.out.println("|" + formatCell(String.valueOf(i), 5)
            				+ "|" + formatCell(appointments.getStatusOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell("Waiting for Dr. " + appointments.getDocName() + "'s Approval", 40) + "|");
				System.out.println("+" + "-".repeat(5) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(40) + "+");
				i++;
            }
        }
        if(i==1) System.out.println("There is no scheduled appointment.");
        System.out.println("If the appointment is cancelled, you will not be able to see it anymore.");
    }
	
	public void viewPatientPastAppointmentOutcomeRecord(ArrayList<Appointment> appointmentList, Patient pat) {
    	System.out.println("+" + "-".repeat(5) + "+"
    				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+"
				+ "-".repeat(50) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");

		System.out.println("|" + formatCell("No.", 5)
				+ "|" + formatCell("Status of App", columnWidth)
				+ "|" + formatCell("Purpose of App", columnWidth)
				+ "|" + formatCell("Time", columnWidth)
				+ "|" + formatCell("Doc Name", columnWidth)
				+ "|" + formatCell("Feedback", 40) 
				+ "|" + formatCell("Medicine", 50) 
				+ "|" + formatCell("Date Issued", columnWidth) 
				+ "|" + formatCell("Dosage", columnWidth) 
				+ "|" + formatCell("Instructions", columnWidth)+ "|");

		System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+"
				+ "-".repeat(50) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d-M-yyyy");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), pat.getUserID()) && (appointments.getStatusOfApp() == Status.Completed || appointments.getStatusOfApp() == Status.PendingPrescription)) {
            	System.out.println("|" + formatCell(String.valueOf(i), 5)
            				+ "|" + formatCell(appointments.getStatusOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell("Dr. " + appointments.getDocName(), columnWidth) 
						+ "|" + formatCell(appointments.getAppointOutcomeRecord(), 40) 
						+ "|" + formatCell(appointments.getMedicine(), 50) 
						+ "|" + formatCell((appointments.getMedicineIssuedDate() != null) ? appointments.getMedicineIssuedDate().format(formatter1): "N/A", columnWidth) 
						+ "|" + formatCell(appointments.getDosage(), columnWidth) 
						+ "|" + formatCell(appointments.getInstructions(), columnWidth)+ "|");
				System.out.println("+" + "-".repeat(5) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(40) + "+"
						+ "-".repeat(50) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+");
                i++;
            }
        }
    }
	
	public boolean scheduleAppointment(Patient pat) throws Exception {
        System.out.println("Choose number of the purpose of your Appointment: (0 to exit)\n" // need exception handling?
                + "(1) CheckUp\n"
                + "(2) Surgery\n"
                + "(3) Consultation\n"
                + "(4) Other\n");
        int choicePur = InputService.inputInteger();
        if(choicePur == 0) return false;
        if(choicePur<0 || choicePur>4) {
        		System.out.println("Please only enter the available options!!");
        		return false;
        }
        Purpose pur;
        if (choicePur == 1) pur = Purpose.CheckUp;
        else if (choicePur == 2) pur = Purpose.Surgery;
        else if (choicePur == 3) pur = Purpose.Consultation;
        else pur = Purpose.Other;
        // enter the department of your visit (check Appointments.java)
        System.out.println("Choose the department of your Appointment: (0 to exit)\n"
                + "(1) Cardiology\n"
                + "(2) Neurology\n"
                + "(3) Oncology\n"
                + "(4) Dermatology\n"
                + "(5) Endocrinology\n"
                + "(6) Gastroenterology\n"
                + "(7) Nephrology\n"
                + "(8) Pulmonology\n"
                + "(9) Rheumatology\n"
                + "(10) ObstetricsGynecology\n"
                + "(11) Others\n");
        int choiceDep = InputService.inputInteger();
        if(choiceDep == 0) return false;
        if(choiceDep<0 || choiceDep>11) {
	    		System.out.println("Please only enter the available options!!");
	    		return false;
	    }
        Department dept;
        if (choiceDep == 1) dept = Department.Cardiology;
        else if (choiceDep == 2) dept = Department.Neurology;
        else if (choiceDep == 3) dept = Department.Oncology;
        else if (choiceDep == 4) dept = Department.Dermatology;
        else if (choiceDep == 5) dept = Department.Endocrinology;
        else if (choiceDep == 6) dept = Department.Gastroenterology;
        else if (choiceDep == 7) dept = Department.Nephrology;
        else if (choiceDep == 8) dept = Department.Pulmonology;
        else if (choiceDep == 9) dept = Department.Rheumatology;
        else if (choiceDep == 10) dept = Department.ObstetricsGynecology;
        else dept = Department.Others;

        String prefDoctor = null;
        while (prefDoctor == null) {
            System.out.println("Enter your preferred Doctor (-1 to quit): ");
            prefDoctor = patientController.selectionOfDoctor(pur, dept, AppointmentList.getInstance().getAppointmentList());
			if(prefDoctor!=null && prefDoctor.equals("NOAVAILABLEDOCTORS") || prefDoctor.equals("INDEXOUTOFBOUNDS") || prefDoctor.equals("-1")) return false;
		}
        int appIndex = selectionOfTimeSlot(prefDoctor, AppointmentList.getInstance().getAppointmentList());
        patientController.scheduleAppointment(pat, appIndex, pur);
        return true;
	}
	
	public int getAppIdIndexFromTime(ArrayList<Appointment> appointmentList) {
		int i = 1;
        int index = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        List<Integer> possibleAppsIndices = new ArrayList<>();
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), patientController.getPatient().getUserID()) && (appointments.getStatusOfApp() == Status.Confirmed || appointments.getStatusOfApp() == Status.Pending)) {
                System.out.println(i + ". " + appointments.getTimeOfApp().format(formatter));
                i++;
                possibleAppsIndices.add(index);
            }
            index++;
        }
        int selection = InputService.inputInteger();
        if(selection<=0 || selection>possibleAppsIndices.size()) {
        		System.out.println("Selection is out of range and unavailable choice!!");
        		return 0;
        }
        return patientController.getAppIdIndexFromTime(appointmentList, possibleAppsIndices, selection);
	}
	
	public int selectionOfTimeSlot(String doctorName, ArrayList<Appointment> appointmentList) {
        int selection = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        List<Integer> timeSlotsIndices = new ArrayList<>();
        int i = 1;
        int index = 0;
        System.out.println("Available Time Slots: ");
        for (Appointment appointments : appointmentList) {
            if (appointments.getAvail() && appointments.getDocName().equals(doctorName) && !appointments.getTimeOfApp().isBefore(LocalDateTime.now())) {
                String appTime = String.valueOf(appointments.getTimeOfApp().format(formatter));
                System.out.println(i + ". " + appTime);
                timeSlotsIndices.add(index);
                i++;
            }
            index++;
        }
        selection = InputService.inputInteger();
        return patientController.selectionOfTimeSlot(appointmentList, timeSlotsIndices, selection);
	}
	
	public void viewMedicalRecord(Patient pat) {
		boolean haveRecord = false;
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
				haveRecord = true;
			}
		}
		
		if(!haveRecord) {
			System.out.println("There is no medical record for you!\n");
			 System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+");

				System.out.println("|" + formatCell("Patient Name", columnWidth)
						+ "|" + formatCell("Date of Birth", columnWidth)
						+ "|" + formatCell("Gender", columnWidth)
						+ "|" + formatCell("Blood Type", columnWidth)
						+ "|" + formatCell("Email", columnWidth) + "|");

				System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+");
			
			System.out.println("|" + formatCell(pat.getName(), columnWidth)
			+ "|" + formatCell(pat.getDateOfBirth().format(formatter), columnWidth)
			+ "|" + formatCell(pat.getGender().toString(), columnWidth)
			+ "|" + formatCell(pat.getBloodType().toString(), columnWidth)
			+ "|" + formatCell(pat.getContactInfo(), columnWidth) + "|");
			
			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+");
		}
	}
	
	public void updatePersonalInformation(Patient pat) {
		System.out.println("Updating email address and contact: ");
        //System.out.println("1. Phone Number");
        //System.out.println("2. Email Address");
        String email = InputService.inputEmail();
        this.patientController.updatePersonalInformation(pat.getUserID(), email);
        this.patientController.getPatient().setContactInfo(email);
        System.out.println("Email and contact are updated successfully!");
	}
	
	private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}
}
