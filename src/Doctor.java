import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doctor extends User {
	
	private department dep;
	
    public Doctor(String name, String hospitalId, Domain domain, String gender, int age) {
        super(name, hospitalId, domain, gender, age);
    }

    public void setDepartment(String dep) {
    	switch(dep) {
	        case "Cardiology":
	            this.dep = department.Cardiology;
	            break;
	        case "Neurology":
	            this.dep = department.Neurology;
	            break;
	        case "Oncology":
	            this.dep = department.Oncology;
	            break;
	        case "Dermatology":
	        	this.dep = department.Dermatology;
	            break;
	        case "Endocrinology":
	        	this.dep = department.Endocrinology;
	            break;
	        case "Gastroenterology":
	        	this.dep = department.Gastroenterology;
	            break;
	        case "Nephrology":
	        	this.dep = department.Nephrology;
	            break;
	        case "Pulmonology":
	        	this.dep = department.Pulmonology;
	            break;
	        case "Rheumatology":
	        	this.dep = department.Rheumatology;
	            break;
	        case "ObstetricsGynecology":
	        	this.dep = department.ObstetricsGynecology;
	            break;
	        case "Others":
	        	this.dep = department.Others;
	            break;
	        default:
	            System.out.println("Invalid department name.");
	            break;
    	}
    }
    
    public department getDepartment() {
    	return this.dep;
    }
    
    public void homePage() {
        Schedule schedule = new Schedule();

        int choice;
        do {
            System.out.println("Choose the number of function:\n"
                    + "(1) View Patient Medical Records\n"
                    + "(2) Update Patient Medical Records\n"
                    + "(3) View Personal Schedule\n"
                    + "(4) Set Availability for Appointments\n"
                    + "(5) Accept or Decline Appointment Requests\n"
                    + "(6) View Upcoming Appointments\n"
                    + "(7) Record Appointment Outcome\n"
                    + "(8) Exit\n");


// Doctor Menu:
//● View Patient Medical Records
//● Update Patient Medical Records
//● View Personal Schedule
//● Set Availability for Appointments
//● Accept or Decline Appointment Requests
//● View Upcoming Appointments
//● Record Appointment Outcome
//● Logout

            try {
                choice = InputScanner.sc.nextInt();
                InputScanner.sc.nextLine();

                switch (choice) {
                    case 1:
                        MedicalRecord.viewMedicalRecord();
                        break;
                    case 2:
                        // Update Medical Records
                        updateMedicalRecords();
                        break;
                    case 3:
                        // View Personal Schedule
                        viewPersonalSchedule(Schedule.getAppointmentList());
                        break;
                    case 4:
                        // Set availability for appointments
                        setAvailability();
                        break;
                    case 5:
                        // Accept or decline appointments
                        acceptOrDeclinePendingApp(Schedule.getAppointmentList());
                        break;
                    case 6:
                        // View upcoming appointments
                        viewDoctorScheduledAppointments(Schedule.getAppointmentList());
                        break;
                    case 7:
                        // Record Appt Outcome Records
                        updateApptOutcomeRecords(Schedule.getAppointmentList());
                        break;
                    case 8:
                        System.out.println("Thank you for using our service!!");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                InputScanner.sc.nextLine(); //
                choice = -1;
            }
        } while (choice != 8);
    }


    public void updateMedicalRecords() {
        System.out.println("Enter PatientID: ");
        String patientID = InputScanner.sc.nextLine().trim();
        System.out.println("Enter new Diagnosis: ");
        String newDiagnosis = InputScanner.sc.nextLine().trim();
        System.out.println("Enter new Prescription: ");
        String newPrescription = InputScanner.sc.nextLine().trim();
        MedicalRecord.updateDiagnosis(patientID, newDiagnosis);
        MedicalRecord.updatePrescriptions(patientID, newPrescription);
    }


    public void setAvailability() {
        Schedule schedule = new Schedule();
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
            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            switch(choice) {
                case 1:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 0, this.dep);
                    break;
                case 2:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 1, this.dep);
                    break;
                case 3:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 2, this.dep);
                    break;
                case 4:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 3, this.dep);
                    break;
                case 5:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 4, this.dep);
                    break;
                case 6:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 5, this.dep);
                    break;
                case 7:
                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 6, this.dep);
                    break;
                case 8:
                    schedule.sortFile();
                    System.out.println("Quitting....");
                    break;
                default:
                    System.out.println("Only enter available choices!!");
                    break;
            }
        } while(choice != 8);
    }


    public void viewPersonalSchedule(ArrayList<Appointment> appointmentList) {
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId())) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with status: " + appointments.getStatusOfApp());
                i++;
            }
        }
    }


    public void acceptOrDeclinePendingApp(ArrayList<Appointment> appointmentList) {
        viewDoctorPendingAppointments(appointmentList);
        int selection = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine(); // Buffer
        int decision = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        try {
            System.out.println("Accept or Decline appointment:\n"
                    + "1. Accept Appointment\n"
                    + "2. Decline Apppointment\n");
            if (decision == 1) {
                appointmentList.get(selection - 1).setStatusOfApp(status.Confirmed);
            } else if (decision == 2) {
                appointmentList.get(selection - 1).setStatusOfApp(status.Cancelled);
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
        } // Test this
    }


    public void updateApptOutcomeRecords(ArrayList<Appointment> appointmentList) {
        viewDoctorScheduledAppointments(appointmentList);
        int selection = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine(); // Buffer
        String doctorNotes = InputScanner.sc.nextLine().trim();
        appointmentList.get(selection-1).setAppointOutcomeRecord(doctorNotes);
    }


    public void viewDoctorScheduledAppointments(ArrayList<Appointment> appointmentList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Confirmed) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Mr/Mrs. " + appointments.getPatName());
                i++;
            }
        }
    }


    public void viewDoctorPendingAppointments(ArrayList<Appointment> appointmentList) {
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Pending) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Mr/Mrs. " + appointments.getPatName());
                i++;
            }
        }
    }



    public String getAppIdFromTime(String time) {
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        for (String[] row : data) {
            if (row[2].equals(time)) {
                return row[1]; //returns doctorName
            }
        }
        return null;
    }

    public void setUnavailableTimeslot(String unAvailableID) {
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        for(String[] row : data) {
            if(row[2].equals(unAvailableID) && (row[0].equals("TRUE") || row[0].equals("true"))) {
                row[0] = "false";
                break;
            }
        }
        schedule.changeAppointmentStatus(unAvailableID, status.Unavailable);
    }
    
    private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}


    public void cancelAppointment(String oldID, ArrayList<Appointment> appointmentList) {
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getAppID(), oldID)) {
                appointments.setStatusOfApp(status.Cancelled);
            }
        }
    }

}



//            System.out.println("Choose the number of function:\n"
//                    + "(1) Set Availability for Appointments\n" // case 4
//                    + "(2) View Pending Appointments\n" // Extra function
//                    + "(3) View your scheduled Appointments\n" // View upcoming appointments, case 6
//                    + "(4) Set your Unavailable Appointment Slots\n" // Extra function
//                    + "(5) View Appointment Outcome Records\n"
//                    + "(6) Change the Appointment Status\n"
//                    + "(7) Exit"); // case 8

//            try {
//                choice = InputScanner.sc.nextInt();
//                InputScanner.sc.nextLine();
//
//                switch (choice) {
//                    case 1:
//                        do {
//                            System.out.println("Please enter your choice to update your shift for upcoming week: \n"
//                                    + "(1) " + LocalDate.now() + "\n"
//                                    + "(2) " + LocalDate.now().plusDays(1) + "\n"
//                                    + "(3) " + LocalDate.now().plusDays(2) + "\n"
//                                    + "(4) " + LocalDate.now().plusDays(3) + "\n"
//                                    + "(5) " + LocalDate.now().plusDays(4) + "\n"
//                                    + "(6) " + LocalDate.now().plusDays(5) + "\n"
//                                    + "(7) " + LocalDate.now().plusDays(6) + "\n"
//                                    + "(8) Exit");
//                            choice = InputScanner.sc.nextInt();
//
//                            switch(choice) {
//                                case 1:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 0);
//                                    break;
//                                case 2:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 1);
//                                    break;
//                                case 3:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 2);
//                                    break;
//                                case 4:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 3);
//                                    break;
//                                case 5:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 4);
//                                    break;
//                                case 6:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 5);
//                                    break;
//                                case 7:
//                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 6);
//                                    break;
//                                case 8:
//                                    schedule.sortFile();
//                                    System.out.println("Quitting....");
//                                    break;
//                                default:
//                                    System.out.println("Only enter available choices!!");
//                                    break;
//                            }
//                        } while(choice != 8);
//                        break;
//                    case 2:
//                        String accceptedApp = null;
//                        while (accceptedApp == null) {
//                            System.out.println("Enter your accepted Appointment: ");
//                            accceptedApp = listOfPendingApp(appointmentList);
//                        }
//                        schedule.approveAppointment(accceptedApp); // Or use changeApptStatus function?
//                        break;
//                    case 3:
//                        viewDoctorScheduledAppointments(appointmentList);
//                        break;
//                    case 4:
//                        System.out.println("Enter unavailable timeslot: ");
//                        String unavailableTimeslot = InputScanner.sc.nextLine();
//                        String unavailID = getAppIdFromTime(unavailableTimeslot);
//                        setUnavailableTimeslot(unavailID);
//                        break;
//                    case 5: // WORK ON THIS
//                        schedule.viewDoctorAppointmentOutcomeRecords(this.getHospitalId());
//                        break;
//                    case 6: // WORK ON THIS
//                        System.out.println("Enter the appointment ID: ");
//                        String appID = InputScanner.sc.nextLine().trim();
//                        if (!schedule.checkAppIDExist(appID)) {
//                            System.out.println("Only enter available Appointment IDs");
//                            break;
//                        }
//                        System.out.println("Choose number of the Appointment status to change\n"
//                                + "(1) Confirmed\n"
//                                + "(2) Cancelled\n"
//                                + "(3) Completed\n"
//                                + "(4) Pending");
//                        int statusChoice = InputScanner.sc.nextInt();
//                        status stat;
//                        if (statusChoice == 1) stat = status.Confirmed;
//                        else if (statusChoice == 2) stat = status.Cancelled;
//                        else if (statusChoice == 3) stat = status.Completed;
//                        else if (statusChoice == 4) stat = status.Pending;
//                        else break;
//                        schedule.changeAppointmentStatus(appID, stat);
//                        break;
//                    case 7:
//                        System.out.println("Thank you for using our service!!");
//                        break;
//                    default:
//                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
//                        break;
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input. Please enter a number between 1 and 5.");
//                InputScanner.sc.nextLine(); //
//                choice = -1;
//            }
//        } while (choice != 6);
//      }
