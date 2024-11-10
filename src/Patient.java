import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Patient extends User {
	
	private static int columnWidth = 20;
	private static ArrayList<String[]> patientList = new ArrayList<>();
	private static String FILE_NAME = "program_files/patients.csv";
	
    public Patient(String name, String hospitalId, Domain domain, String gender, int age) {
        super(name, hospitalId, domain, gender, age);
    }

    public void homePage() {
        Schedule schedule = new Schedule();

        int choice;
        do {

// Patient Menu:
//● View Medical Record
//● Update Personal Information
//● View Available Appointment Slots
//● Schedule an Appointment
//● Reschedule an Appointment
//● Cancel an Appointment
//● View Scheduled Appointments
//● View Past Appointment Outcome Records
//● Logout


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
                choice = InputScanner.sc.nextInt();
                InputScanner.sc.nextLine(); // Buffer
                switch (choice) {
                    case 1:
                        MedicalRecord.viewMedicalRecord();
                        break;
                    case 2:
                        MedicalRecord.updatePersonalInformation();
                        break;
                    case 3:
                        // View available appointment slots
                        viewAvailableAppointmentSlots(Schedule.getAppointmentList());
                        break;
                    case 4:
                        scheduleAppointment();
                        break;
                    case 5:
                        System.out.println("Enter the Appointment that you want to change: ");
                        int orgAppIDIndex = getAppIdIndexFromTime(Schedule.getAppointmentList());
                        if (!schedule.checkAppIDExist(Schedule.getAppointmentList().get(orgAppIDIndex).getAppID())) { // Keeping this?
                            System.out.println("Only enter available Appointment IDs");
                            break;
                        }
                        cancelAppointment(orgAppIDIndex);
                        System.out.println("Your original appointment has been cancelled!!\n Select new Appointment.");
                        scheduleAppointment();
                        break;
                    case 6:
                        System.out.println("Enter the time slot that you want to cancel: ");
                        int oldAppIDIndex = getAppIdIndexFromTime(Schedule.getAppointmentList());
                        cancelAppointment(oldAppIDIndex);
                        break;
                    case 7:
                        viewPatientScheduledAppointments(Schedule.getAppointmentList());
                        break;
                    case 8:
                        // View Past Appointment Outcome Records
                        viewPatientPastAppointmentOutcomeRecord(Schedule.getAppointmentList());
                        break;
                    case 9:
                        System.out.println("Thank you for using our service!!");
                        break;
//                    case 8:
//                        viewPatientPendingAppointments(appointmentList);
//                        break;
//                    case 9:
//                        schedule.viewPatientAppointmentOutcomeRecords(this.getHospitalId());
//                        break;
//                    case 10:
//                        System.out.println("Thank you for using our service!!");
//                        // Update file
//                        updateAppointmentFile(appointmentList);
//                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 6.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                InputScanner.sc.nextLine(); //
                choice = -1;
            }
        } while (choice != 9);
    }


    // New functions for creating appointment objects
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
			if(appointments.getAvail()) {
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


    public void scheduleAppointment() {
        try {
            System.out.println("Choose number of the purpose of your Appointment:\n" // need exception handling?
                    + "(1) CheckUp\n"
                    + "(2) Surgery\n"
                    + "(3) Consultation\n"
                    + "(4) Other\n");
            int choicePur = InputScanner.sc.nextInt();  // Exception handling
            InputScanner.sc.nextLine(); // Buffer
            purpose pur;
            if (choicePur == 1) pur = purpose.CheckUp;
            else if (choicePur == 2) pur = purpose.Surgery;
            else if (choicePur == 3) pur = purpose.Consultation;
            else pur = purpose.Other; // No exception handling yet, plus abit suss
            // enter the department of your visit (check Appointments.java)
            System.out.println("Choose the department of your Appointment:\n"
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
            int choiceDep = InputScanner.sc.nextInt(); // Exception handling
            InputScanner.sc.nextLine(); // Buffer
            department dept;
            if (choiceDep == 1) dept = department.Cardiology;
            else if (choiceDep == 2) dept = department.Neurology;
            else if (choiceDep == 3) dept = department.Oncology;
            else if (choiceDep == 4) dept = department.Dermatology;
            else if (choiceDep == 5) dept = department.Endocrinology;
            else if (choiceDep == 6) dept = department.Gastroenterology;
            else if (choiceDep == 7) dept = department.Nephrology;
            else if (choiceDep == 8) dept = department.Pulmonology;
            else if (choiceDep == 9) dept = department.Rheumatology;
            else if (choiceDep == 10) dept = department.ObstetricsGynecology;
            else dept = department.Others;

            String prefDoctor = null;
            while (prefDoctor == null) {
                System.out.println("Enter your preferred Doctor: ");
                prefDoctor = selectionOfDoctor(pur, dept, Schedule.getAppointmentList());
            }
            int appIndex = selectionOfTimeSlot(prefDoctor, Schedule.getAppointmentList());
            Schedule.getAppointmentList().get(appIndex).setAvail(false);
            Schedule.getAppointmentList().get(appIndex).setPatID(this.getHospitalId());
            Schedule.getAppointmentList().get(appIndex).setPatName(this.getName());
            Schedule.getAppointmentList().get(appIndex).setPurposeOfApp(pur);
            Schedule.getAppointmentList().get(appIndex).setStatusOfApp(status.Pending);
            Schedule.getAppointmentList().get(appIndex).setPaymentStatus(paymentStatus.Pending);
        } catch (Exception e) { // add this to cancel
            System.out.println("Not Scheduled");
        }
    }


    public String selectionOfDoctor(purpose purposeOfVisit, department appDepartment, ArrayList<Appointment> appointmentList) throws Exception {
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
        try {
            selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return doctors.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
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


    public int selectionOfTimeSlot(String doctorName, ArrayList<Appointment> appointmentList) {
        // create a array and refer to elements by their index
        int selection = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        List<Integer> timeSlotsIndices = new ArrayList<>();
        int i = 1;
        int index = 0;
        System.out.println("Available Time Slots: ");
        for (Appointment appointments : appointmentList) {
            if (appointments.getAvail() && appointments.getDocName().equals(doctorName)) {
                String appTime = String.valueOf(appointments.getTimeOfApp().format(formatter));
                System.out.println(i + ". " + appTime);
                timeSlotsIndices.add(index);
                i++;
            }
            index++;
        }
        try {
            selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return timeSlotsIndices.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Back to Home Page.");
            // how to return it back to the home page?
        }
        return 0;
    }


    public int getAppIdIndexFromTime(ArrayList<Appointment> appointmentList) {
        int i = 1;
        int index = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        List<Integer> possibleAppsIndices = new ArrayList<>();
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), this.getHospitalId()) && (appointments.getStatusOfApp() == status.Confirmed || appointments.getStatusOfApp() == status.Pending)) {
                System.out.println(i + ". " + appointments.getTimeOfApp().format(formatter));
                i++;
                possibleAppsIndices.add(index);
            }
            index++;
        }
        try {
            int selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return possibleAppsIndices.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Please choose a valid doctor number.");
        }
        return 0;
    }


    public void cancelAppointment(int oldID) {
        Schedule.getAppointmentList().get(oldID).setStatusOfApp(status.Cancelled);
        Schedule.getAppointmentList().get(oldID).setAvail(true);
    }


    public void viewPatientScheduledAppointments(ArrayList<Appointment> appointmentList) {
    	System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+");

		System.out.println("|" + formatCell("No.", 5)
				+ "|" + formatCell("Purpose of App", columnWidth)
				+ "|" + formatCell("Time", columnWidth)
				+ "|" + formatCell("Note", 40) + "|");

		System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (appointments.getPatID().equals(this.getHospitalId()) && appointments.getStatusOfApp() == status.Confirmed) {
            	System.out.println("|" + formatCell(String.valueOf(i), 5)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell("With Dr. " + appointments.getDocName(), 40) + "|");
				System.out.println("+" + "-".repeat(5) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(40) + "+");
		        i++;
            }
            if (appointments.getPatID().equals(this.getHospitalId()) && appointments.getStatusOfApp() == status.Pending) {
            	System.out.println("|" + formatCell(String.valueOf(i), 5)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell("Waiting for Dr. " + appointments.getDocName() + "'s Approval", 40) + "|");
				System.out.println("+" + "-".repeat(5) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(40) + "+");
				i++;
            }
        }
    }


    public void viewPatientPastAppointmentOutcomeRecord(ArrayList<Appointment> appointmentList) {
    	System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+");

		System.out.println("|" + formatCell("No.", 5)
				+ "|" + formatCell("Purpose of App", columnWidth)
				+ "|" + formatCell("Time", columnWidth)
				+ "|" + formatCell("Doc Name", columnWidth)
				+ "|" + formatCell("Feedback", 40) + "|");

		System.out.println("+" + "-".repeat(5) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(40) + "+");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Completed) {
            	System.out.println("|" + formatCell(String.valueOf(i), 5)
						+ "|" + formatCell(appointments.getPurposeOfApp().toString(), columnWidth)
						+ "|" + formatCell(appointments.getTimeOfApp().format(formatter), columnWidth)
						+ "|" + formatCell("Dr. " + appointments.getDocName(), columnWidth) 
						+ "|" + formatCell(appointments.getAppointOutcomeRecord(), 40) + "|");
				System.out.println("+" + "-".repeat(5) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(40) + "+");
                i++;
            }
        }
    }

    public String findDoctorID(String doctorName) {
        FileIO file = new FileIO("program_files/doctors.csv");
        List<String[]> data = file.getAllRows();
        for (String[] row : data) {
            if (row[1].equalsIgnoreCase(doctorName)) {
                return row[0];
            }
        }
        return null;
    }
    
    private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}
    
    public static void loadPatientlist() {
		List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else {
				String[] addingPatient = new String[2];
				addingPatient[0] = row[0];
				addingPatient[1] = row[1];
				patientList.add(addingPatient);
			}
		}
    }
    
    public static ArrayList<String[]> getPatientList(){
    	return Patient.patientList;
    }
}

// I am very proud! :D
