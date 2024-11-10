import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doctor extends User {
	
	private department dep;
	private static final int columnWidth = 20;
	
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
                        viewPersonalSchedule();
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


    public void viewPersonalSchedule() {
        int i = 1;
        System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");

		System.out.println("|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("App Date and Time", columnWidth)
				+ "|" + formatCell("Purpose", columnWidth)
				+ "|" + formatCell("App Status", columnWidth) + "|");

		System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        for (Appointment appointments : Schedule.getAppointmentList()) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && !appointments.getAvail()) {
                System.out.println("|" + formatCell(appointments.getAppID(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getStatusOfApp().toString(), columnWidth) + "|");
				System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+");
				i++;
            }
        }
    }


    public void acceptOrDeclinePendingApp(ArrayList<Appointment> appointmentList) {
        List<Integer> pendingIndices = viewDoctorPendingAppointments(appointmentList);
        System.out.println("Please select your choice to accept or decline.");
        int selection = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine(); // Buffer
        try {
            System.out.println("Accept or Decline appointment:\n"
                    + "1. Accept Appointment\n"
                    + "2. Decline Apppointment\n");
            int decision = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            
            if (decision == 1) {
                appointmentList.get(pendingIndices.get(selection-1)).setStatusOfApp(status.Confirmed);
            } else if (decision == 2) {
                appointmentList.get(pendingIndices.get(selection-1)).setStatusOfApp(status.Cancelled);
                appointmentList.get(pendingIndices.get(selection-1)).setAvail(true);
            } else {
            	System.out.println("Invalid Input");
            }
        } catch (Exception e) {
            System.out.println("Invalid Input! Please choose only available options shown.");
        } // Test this
    }


    public void updateApptOutcomeRecords(ArrayList<Appointment> appointmentList) {
    	List<Integer> pendingIndices = viewDoctorScheduledAppointments(appointmentList);
        System.out.println("Select one of the record you would like to update: ");
        int selection = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine(); // Buffer
        System.out.println("Please enter your notes (Doctor's notes):");
        String doctorNotes = InputScanner.sc.nextLine().trim();
        if(selection-1 >= pendingIndices.size()) System.out.println("Please only enter the available records:");
        else appointmentList.get(pendingIndices.get(selection-1)).setAppointOutcomeRecord(doctorNotes);
    }


    public List<Integer> viewDoctorScheduledAppointments(ArrayList<Appointment> appointmentList) {
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
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Confirmed) {
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


    public List<Integer> viewDoctorPendingAppointments(ArrayList<Appointment> appointmentList) {
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
		
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Pending) {
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



    public String getAppIdFromTime(String time) {
        List<String[]> data = Schedule.getAllRows();
        for (String[] row : data) {
            if (row[2].equals(time)) {
                return row[1]; //returns doctorName
            }
        }
        return null;
    }

    public void setUnavailableTimeslot(String unAvailableID) {
        Schedule schedule = new Schedule();
        List<String[]> data = Schedule.getAllRows();
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
