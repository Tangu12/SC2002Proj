import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Schedule {
	private static final String FILE_NAME = "program_files/appointments.csv";
	private int columnWidth = 20;
	private static ArrayList<Appointment> appointmentList = new ArrayList<>();

	public void createAppointmentSlot(String docID, String docName, int plusDay, department docDep) {
		int startTime, endTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter formatterForID = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		DateTimeFormatter formatterForInput = DateTimeFormatter.ofPattern("d/M/yyyy");
		LocalDate updateDate = LocalDate.now().plusDays(plusDay);
		printAppointmentOfaDayForDoc(updateDate.format(formatterForInput), docID);
		do {
			System.out.println("Please enter when your shift starts: (0 -> 23) (-1 to return)");
			startTime = InputScanner.sc.nextInt();
			if(startTime == -1) return;
			System.out.println("Please enter when your shift ends: (0 -> 24) (-1 to return)");
			endTime = InputScanner.sc.nextInt();
			if(endTime == -1) return;
			if(startTime < 0 && endTime < 0 && startTime >= 24 && endTime> 24 && startTime >= endTime)
				System.out.println("Please only enter available time and endTime must be greater than startTime");
		} while(startTime < 0 && endTime < 0 && startTime >= 24 && endTime> 24 && startTime >= endTime);
		InputScanner.sc.nextLine();

		try {
			File appointmentFile = new File(FILE_NAME);
			if (appointmentFile.createNewFile()) {
				FileWriter headerWriter = new FileWriter(appointmentFile);
				headerWriter.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,Department,StatusOfAppointment,AppointmentOutcomeRecord,Medicine,IssuedDate,Dosage,Instructions\n");
				headerWriter.close();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
			for(int i = startTime; i < endTime; i++) {
				LocalDateTime firstSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 0, 0));
				String newFirstSlot = String.valueOf(true) + "," + "A" + firstSlot.format(formatterForID) + docID + "," + firstSlot.format(formatter) + "," + docID + "," + docName + ", , , ," + docDep.toString() + ", , , , , , "; //add space to avoid null data when retrieve data
				if(!checkAppIDExist("A" + firstSlot.format(formatterForID) + docID)) writer.write(String.join(",", newFirstSlot) + "\n");
				LocalDateTime secondSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 30, 0));
				String newSecondSlot = String.valueOf(true) + "," + "A" + secondSlot.format(formatterForID) + docID + "," + secondSlot.format(formatter) + "," + docID + "," + docName + ", , , ," + docDep.toString() + ", , , , , , "; //add space to avoid null data when retrieve data
				if(!checkAppIDExist("A" + secondSlot.format(formatterForID) + docID)) writer.write(String.join(",", newSecondSlot) + "\n");
			}
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		sortFile();
		loadAppointments();
	}

	public void changeAppointmentStatus(String appID, status stat) {
		List<String[]> data = getAllRows();
		boolean foundData = false;

		for(String[] row : data) {
			if(row[1].equals(appID) && (row[0].equals("FALSE") || row[0].equals("false")) && (row[8].equals("Confirmed") || row[8].equals("Pending"))) {
				if(stat == status.Cancelled) row[0] = String.valueOf(true);
				row[8] = stat.name();
				foundData = true;
				break;
			}
		}

		if (!foundData) {
			System.out.println("The Appointment ID you want to change status cannot be found! or The Appointment has been cancelled or completed! Please try again.");
			return;
		}
		overwriteCSV(data);
	}

	public boolean checkAppIDExist(String appID){
		for(Appointment app : appointmentList) {
			if(app.getAppID().equals(appID)) return true;
		}
		return false;
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
		
		for(Appointment app : appointmentList) {
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

	public void displayAppointmentList(ArrayList<Appointment> appointmentList) {
		if (appointmentList == null || appointmentList.isEmpty()) {
			System.out.println("No appointments are found.");
			return;
		}

		// Print table header
		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(30) + "+");
		
		System.out.println("|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("App Date and Time", columnWidth)
				+ "|" + formatCell("Doctor ID", columnWidth)
				+ "|" + formatCell("Doctor Name", columnWidth)
				+ "|" + formatCell("Patient ID", columnWidth)
				+ "|" + formatCell("Patient Name", columnWidth)
				+ "|" + formatCell("Purpose", columnWidth)
				+ "|" + formatCell("Department", columnWidth)
				+ "|" + formatCell("Status", columnWidth)
				+ "|" + formatCell("Appointment Outcomes", 50) 
				+ "|" + formatCell("Medicine", columnWidth) 
				+ "|" + formatCell("Issued Date", columnWidth)
				+ "|" + formatCell("Dosage", columnWidth)
				+ "|" + formatCell("Instructions", 30) + "|");
		
		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" + "-".repeat(50) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(30) + "+");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");
		for (Appointment row : appointmentList) {
			System.out.println("|" + formatCell(row.getAppID(), columnWidth)
					+ "|" + formatCell(row.getTimeOfApp().format(formatter), columnWidth)
					+ "|" + formatCell(row.getDocID(), columnWidth)
					+ "|" + formatCell(row.getDocName(), columnWidth)
					+ "|" + formatCell(row.getPatID(), columnWidth)
					+ "|" + formatCell(row.getPatName(), columnWidth)
					+ "|" + formatCell((row.getPurposeOfApp() != null) ? row.getPurposeOfApp().toString() : " ", columnWidth)
					+ "|" + formatCell(row.getAppointmentDepartment().toString(), columnWidth)
					+ "|" + formatCell((row.getStatusOfApp() != null) ? row.getStatusOfApp().toString() : " ", columnWidth)
					+ "|" + formatCell(row.getAppointOutcomeRecord(), 50) 
					+ "|" + formatCell(row.getMedicine(), columnWidth)
					+ "|" + formatCell((row.getMedicineIssuedDate() != null) ? row.getMedicineIssuedDate().format(formatter1) : " ", columnWidth)
					+ "|" + formatCell(row.getDosage(), columnWidth)
					+ "|" + formatCell(row.getInstructions(), 30) + "|");
			
			System.out.println("+" + "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(50) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(columnWidth) + "+"
					+ "-".repeat(30) + "+");
		}
	}


	public ArrayList<Appointment> getAppointmentsByDoctorID(String doctorID) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();
		for(Appointment appointment : appointmentList) {
			if(appointment.getDocID().equals(doctorID)) filteredAppointments.add(appointment);
		}

		return filteredAppointments;
	}

	// Retrieve appointments by Patient ID
	public ArrayList<Appointment> getAppointmentsByPatientID(String patientID) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();
		for(Appointment appointment : appointmentList) {
			if (appointment.getPatID().equals(patientID))
				filteredAppointments.add(appointment);
		}

		return filteredAppointments;
	}

	// Retrieve a single appointment by Appointment ID
	public Appointment getAppointmentByID(String appID) {
		for(Appointment appointment : appointmentList) {
			if(appID.equals(appointment.getAppID())) return appointment;
		}
		return null;
	}

	// Retrieve appointments by Status
	public ArrayList<Appointment> getAppointmentsByStatus(String status) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();

		for (Appointment row : appointmentList) {
			if (row.getStatusOfApp() != null && row.getStatusOfApp().toString().equalsIgnoreCase(status)) {
				filteredAppointments.add(row);
			}
		}

		return filteredAppointments;
	}



	public void sortFile() {
		List<String[]> data = getAllRows();
		// Sort data based on the value of row[2]
		Collections.sort(data.subList(1, data.size()), new Comparator<String[]>() {
			@Override
			public int compare(String[] row1, String[] row2) {
				return row1[1].compareTo(row2[1]);
			}
		});
		overwriteCSV(data);
	}

	public static List<String[]> getAllRows() {
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
		return data;
	}
	
	public static void loadAppointments() {
		int i = 0;
		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if (i == 0) {
				i++;
				continue;
			}
			// if row is not empty,  should i put these inside the constructor of Appointments?
			purpose appPur;
			if (Objects.equals(row[7], "CheckUp")) appPur = purpose.CheckUp;
			else if (Objects.equals(row[7], "Surgery")) appPur = purpose.Surgery;
			else if (Objects.equals(row[7], "Consultation")) appPur = purpose.Consultation;
			else if (Objects.equals(row[7], "Others")) appPur = purpose.Other;
			else appPur = null;

			department appDept;
			if (Objects.equals(row[8], "Cardiology")) appDept = department.Cardiology;
			else if (Objects.equals(row[8], "Neurology")) appDept = department.Neurology;
			else if (Objects.equals(row[8], "Oncology")) appDept = department.Oncology;
			else if (Objects.equals(row[8], "Dermatology")) appDept = department.Dermatology;
			else if (Objects.equals(row[8], "Neurology")) appDept = department.Endocrinology;
			else if (Objects.equals(row[8], "Oncology")) appDept = department.Gastroenterology;
			else if (Objects.equals(row[8], "Dermatology")) appDept = department.Nephrology;
			else if (Objects.equals(row[8], "Neurology")) appDept = department.Pulmonology;
			else if (Objects.equals(row[8], "Oncology")) appDept = department.Rheumatology;
			else if (Objects.equals(row[8], "Dermatology")) appDept = department.ObstetricsGynecology;
			else appDept = department.Others;


			status appStatus = null;
			if (Objects.equals(row[9], "Confirmed")) appStatus = status.Confirmed;
			else if (Objects.equals(row[9], "Cancelled")) appStatus = status.Cancelled;
			else if (Objects.equals(row[9], "Completed")) appStatus = status.Completed;
			else if (Objects.equals(row[9], "Pending")) appStatus = status.Pending;
			else if (Objects.equals(row[9], "Unavailable")) appStatus = status.Unavailable;
			else if (Objects.equals(row[9], "PrescriptionPending")) appStatus = status.PrescriptionPending;

			Appointment apps = new Appointment(Boolean.valueOf(row[0]),row[1],row[2],row[3],row[4],row[5],row[6],appPur,appDept,appStatus, " ", row[11], row[12], row[13], row[14]);
			appointmentList.add(apps);
			i++;
		}
	}
	
	// Update file function
    public static void updateAppointmentFile(ArrayList<Appointment> appointmentList) {
        Schedule schedule = new Schedule();
        List<String[]> data = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");
        String[] values = new String[15];
        values[0] = "Availability";
        values[1] = "AppointmentID";
        values[2] = "TimeOfAppointment";
        values[3] = "DoctorID";
        values[4] = "DoctorName";
        values[5] = "PatientID";
        values[6] = "PatientName";
        values[7] = "PurposeOfAppointment";
        values[8] = "Department";
        values[9] = "StatusOfAppointment";
        values[10] = "Appointment Outcomes";
        values[11] = "Medicine";
        values[12] = "Date Issued";
        values[13] = "Dosage";
        values[14] = "Instructions";
        data.add(values);
        
        for(Appointment appointment : appointmentList) {
        	String[] row = new String[15];
        	row[0] = String.valueOf(appointment.getAvail());
            row[1] = appointment.getAppID();
            row[2] = appointment.getTimeOfApp().format(formatter);
            row[3] = appointment.getDocID();
            row[4] = appointment.getDocName();
            row[5] = appointment.getPatID();
            row[6] = appointment.getPatName();
            if(appointment.getPurposeOfApp() != null) row[7] = appointment.getPurposeOfApp().toString(); else row[7] = " ";
            row[8] = appointment.getAppointmentDepartment().toString();
            if(appointment.getStatusOfApp() != null) row[9] = appointment.getStatusOfApp().toString(); else row[9] = " ";
            row[10] = appointment.getAppointOutcomeRecord();
            row[11] = appointment.getMedicine();
            if(appointment.getMedicineIssuedDate() != null) row[12] = appointment.getMedicineIssuedDate().format(formatter1); else row[12] = " ";
            row[13] = appointment.getDosage();
            row[14] = appointment.getInstructions();
            
            data.add(row);
        }
        schedule.updateFile(data);
    }

	private void overwriteCSV(List<String[]> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
			for(String[] row : data) writer.write(String.join(",", row) + "\n");
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void updateFile(List<String[]> data) {
		overwriteCSV(data);
	}
	
	public static ArrayList<Appointment> getAppointmentList(){
		return appointmentList;
	}


	private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}

}