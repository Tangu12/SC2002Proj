import java.io.BufferedReader;
import java.io.BufferedWriter;
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

	public void createAppointmentSlot(String docID, String docName, int plusDay) {
		int startTime, endTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		DateTimeFormatter formatterForID = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		DateTimeFormatter formatterForInput = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
				headerWriter.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,StatusOfAppointment,Cost,PaymentStatus\n");
				headerWriter.close();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
			for(int i = startTime; i < endTime; i++) {
				LocalDateTime firstSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 0, 0));
				String newFirstSlot = String.valueOf(true) + "," + "A" + firstSlot.format(formatterForID) + docID + "," + firstSlot.format(formatter) + "," + docID + "," + docName + ", , , , , , , , "; //add space to avoid null data when retrieve data
				if(!checkAppIDExist("A" + firstSlot.format(formatterForID) + docID)) writer.write(String.join(",", newFirstSlot) + "\n");
				LocalDateTime secondSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 30, 0));
				String newSecondSlot = String.valueOf(true) + "," + "A" + secondSlot.format(formatterForID) + docID + "," + secondSlot.format(formatter) + "," + docID + "," + docName +", , , , , , , , "; //add space to avoid null data when retrieve data
				if(!checkAppIDExist("A" + secondSlot.format(formatterForID) + docID)) writer.write(String.join(",", newSecondSlot) + "\n");
			}
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		sortFile();
	}

	public void scheduleAppointment(String appTime, String doctorID, String doctorName, String patID, String patName, String purpose) {
		List<String[]> data = getAllRows();
		boolean foundData = false;

		for(String[] row : data) {
			if(row[2].equals(appTime)) {
				if(row[0].equals("FALSE") || row[0].equals("false")) {
					System.out.println("The slot is occupied!!!");
					return;
				}
				row[0] = String.valueOf(false);
				row[3] = doctorID;
				row[4] = doctorName;
				row[5] = patID;
				row[6] = patName;
				row[7] = purpose;
				row[8] = status.Pending.name();
				foundData = true;
				break;
			}
		}

		if(!foundData) {
			System.out.println("Appointment ID cannot be found! Please try again.");
			return;
		}

		overwriteCSV(data);

	}

	public void viewAvailAppointmentSlots() {
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

		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if(row[0].equals("TRUE") || row[0].equals("true")) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth) + "|");
				System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+");
			}
		}
	}

	public void rescheduleAppointment(String orgAppID, String newAppID, String patID) {
		List<String[]> data = getAllRows();
		String patName = "";
		String purpose = "";

		for(String[] row : data) {
			if(row[1].equals(orgAppID) && (row[0].equals("FALSE") || row[0].equals("false"))) {
				row[0] = String.valueOf(true);
				patName = row[6];
				purpose = row[7];
				break;
			}
		}
		for(String[] row : data) {
			if(row[1].equals(newAppID)) {
				row[0] = String.valueOf(false);
				row[5] = patID;
				row[6] = patName;
				row[7] = purpose;
				row[8] = "Pending";
				break;
			}
		}
		System.out.println("Your appointment has been successfully rescheduled! Thank you!");
//		if (!foundorgData && !foundnewData) {
//			System.out.println("The Appointment ID you want to change and new Appointment ID cannot be found! Please try again.");
//			return;
//		}
//		else if (!foundorgData) {
//			System.out.println("The Appointment ID you want to change cannot be found! Please try again.");
//			return;
//		}
//		else if (!foundnewData) {
//			System.out.println("The new Appointment ID cannot be found!\nThe Appointment ID you want to change has been cancelled. Please schedule again!");
//			return;
//		}
		overwriteCSV(data);
	}

	public void cancelAppointment(String appID, String patID) {
		List<String[]> data = getAllRows();
		boolean foundData = false;

		for(String[] row : data) {
			if(row[1].equals(appID) && (row[0].equals("FALSE") || row[0].equals("false"))) {
				if(!row[5].equals(patID)) {
					System.out.println("You can only cancel your own appointment!");
					return;
				}
				row[0] = String.valueOf(true);
				row[8] = status.Cancelled.name();
				foundData = true;
				break;
			}
		}

		if (!foundData) {
			System.out.println("The Appointment ID you want to cancel cannot be found! Please try again.");
			return;
		}
		overwriteCSV(data);
	}

	public void viewPatientScheduledAppointments(String hospitalID) {
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

		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(hospitalID) && row[8].equals("Confirmed")) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth)
						+ "|" + formatCell(row[5], columnWidth)
						+ "|" + formatCell(row[6], columnWidth)
						+ "|" + formatCell(row[7], columnWidth)
						+ "|" + formatCell(row[8], columnWidth) + "|");
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

	public void viewPatientPendingAppointments(String hospitalID) {
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

		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(hospitalID) && row[8].equals("Pending")) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth)
						+ "|" + formatCell(row[5], columnWidth)
						+ "|" + formatCell(row[6], columnWidth)
						+ "|" + formatCell(row[7], columnWidth)
						+ "|" + formatCell(row[8], columnWidth) + "|");
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


	public void viewDoctorScheduledAppointments(String hospitalID) {
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

		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if((row[0].equals("FALSE") || row[0].equals("false")) && row[3].equals(hospitalID) && row[8].equals("Confirmed")) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth)
						+ "|" + formatCell(row[5], columnWidth)
						+ "|" + formatCell(row[6], columnWidth)
						+ "|" + formatCell(row[7], columnWidth)
						+ "|" + formatCell(row[8], columnWidth) + "|");
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




	public void approveAppointment(String appointmentID) {
		// Switch the pending to Confirmed
		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if (Objects.equals(row[1], appointmentID)) {
				row[8] = "Confirmed";
			}
		}
		overwriteCSV(data);
	}



	public void viewPatientAppointmentOutcomeRecords(String hospitalID) {
		System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
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
				+ "|" + formatCell("Status Of App", columnWidth)
				+ "|" + formatCell("Cost", columnWidth)
				+ "|" + formatCell("Payment Status", columnWidth) + "|");

		System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");

		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(hospitalID) && row[8].equals("Completed")) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth)
						+ "|" + formatCell(row[5], columnWidth)
						+ "|" + formatCell(row[6], columnWidth)
						+ "|" + formatCell(row[7], columnWidth)
						+ "|" + formatCell(row[8], columnWidth)
						+ "|" + formatCell(row[9], columnWidth)
						+ "|" + formatCell(row[10], columnWidth) + "|");
				System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
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


	public void viewDoctorAppointmentOutcomeRecords(String hospitalID) {
		System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
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
				+ "|" + formatCell("Status Of App", columnWidth)
				+ "|" + formatCell("Cost", columnWidth)
				+ "|" + formatCell("Payment Status", columnWidth) + "|");

		System.out.println("+" + "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+"
				+ "-".repeat(columnWidth) + "+");

		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if((row[0].equals("FALSE") || row[0].equals("false")) && row[3].equals(hospitalID) && row[8].equals("Completed")) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth)
						+ "|" + formatCell(row[5], columnWidth)
						+ "|" + formatCell(row[6], columnWidth)
						+ "|" + formatCell(row[7], columnWidth)
						+ "|" + formatCell(row[8], columnWidth)
						+ "|" + formatCell(row[9], columnWidth)
						+ "|" + formatCell(row[10], columnWidth) + "|");
				System.out.println("+" + "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
						+ "-".repeat(columnWidth) + "+"
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


	public void changePaymentStatus(String appID, paymentStatus stat) {
		List<String[]> data = getAllRows();
		boolean foundData = false;

		for(String[] row : data) {
			if(row[1].equals(appID) && (row[0].equals("FALSE") || row[0].equals("false")) && !row[8].equals("Cancelled")) {
				row[10] = stat.name();
				foundData = true;
				break;
			}
		}

		if (!foundData) {
			System.out.println("The Appointment ID you want to change payment status cannot be found! Please try again.");
			return;
		}
		overwriteCSV(data);
	}

	public void addChangeCost(String appID, double cost) {
		List<String[]> data = getAllRows();
		boolean foundData = false;

		for(String[] row : data) {
			if(row[1].equals(appID) && (row[0].equals("FALSE") || row[0].equals("false")) && !row[8].equals("Cancelled")) {
				row[9] = String.valueOf(cost);
				foundData = true;
				break;
			}
		}

		if (!foundData) {
			System.out.println("The Appointment ID you want to add cost cannot be found! Please try again.");
			return;
		}
		overwriteCSV(data);
	}

	public boolean checkAppIDExist(String appID){
		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if(row[1].equals(appID)) return true;
		}
		return false;
	}

	public void printAppointmentOfaDayForDoc(String date, String docID) {

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
		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if(row[2].startsWith(date) && row[3].equals(docID)) {
				System.out.println("|" + formatCell(row[1], columnWidth)
						+ "|" + formatCell(row[2], columnWidth)
						+ "|" + formatCell(row[3], columnWidth)
						+ "|" + formatCell(row[4], columnWidth)
						+ "|" + formatCell(row[5], columnWidth)
						+ "|" + formatCell(row[6], columnWidth)
						+ "|" + formatCell(row[7], columnWidth)
						+ "|" + formatCell(row[8], columnWidth) + "|");
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

	public void displayAppointmentList(List<String[]> appointments) {
		if (appointments == null || appointments.isEmpty()) {
			System.out.println("No appointments found.");
			return;
		}

		int columnWidth = 20;

		// Print table header
		System.out.println("+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+" +
				"-".repeat(columnWidth) + "+");

		System.out.println("|" + formatCell("Appointment ID", columnWidth)
				+ "|" + formatCell("App Date and Time", columnWidth)
				+ "|" + formatCell("Doctor ID", columnWidth)
				+ "|" + formatCell("Doctor Name", columnWidth)
				+ "|" + formatCell("Patient ID", columnWidth)
				+ "|" + formatCell("Patient Name", columnWidth)
				+ "|" + formatCell("Purpose", columnWidth)
				+ "|" + formatCell("Status", columnWidth)
				+ "|" + formatCell("Cost", columnWidth)
				+ "|" + formatCell("Payment Status", columnWidth) + "|");

		for (String[] row : appointments) {
			// Print the row content for debugging
			System.out.println("Debug: " + String.join(",", row)); // Debug line

			if (row.length < 11) {
				System.out.println("Skipping row with insufficient data: " + String.join(",", row));
				continue;
			}

			System.out.println("|" + formatCell(row[1], columnWidth)
					+ "|" + formatCell(row[2], columnWidth)
					+ "|" + formatCell(row[3], columnWidth)
					+ "|" + formatCell(row[4], columnWidth)
					+ "|" + formatCell(row[5], columnWidth)
					+ "|" + formatCell(row[6], columnWidth)
					+ "|" + formatCell(row[7], columnWidth)
					+ "|" + formatCell(row[8], columnWidth)
					+ "|" + formatCell(row[9], columnWidth)
					+ "|" + formatCell(row[10], columnWidth) + "|");
		}
	}


	public List<String[]> getAppointmentsByDoctorID(String doctorID) {
		List<String[]> data = getAllRows();
		List<String[]> filteredAppointments = new ArrayList<>();

		for (String[] row : data) {
			if (row.length > 3 && row[3].equals(doctorID)) {
				filteredAppointments.add(row);
			}
		}

		return filteredAppointments;
	}

	// Retrieve appointments by Patient ID
	public List<String[]> getAppointmentsByPatientID(String patientID) {
		List<String[]> data = getAllRows();
		List<String[]> filteredAppointments = new ArrayList<>();

		for (String[] row : data) {
			if (row.length > 5 && row[5].equals(patientID)) {
				filteredAppointments.add(row);
			}
		}

		return filteredAppointments;
	}

	// Retrieve a single appointment by Appointment ID
	public String[] getAppointmentByID(String appID) {
		List<String[]> data = getAllRows();

		for (String[] row : data) {
			if (row.length > 1 && row[1].equals(appID)) {
				return row;
			}
		}
		return null;
	}

	// Retrieve appointments by Status
	public List<String[]> getAppointmentsByStatus(String status) {
		List<String[]> data = getAllRows();
		List<String[]> filteredAppointments = new ArrayList<>();

		for (String[] row : data) {
			if (row.length > 8 && row[8].equalsIgnoreCase(status)) {
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
				return row1[2].compareTo(row2[2]);
			}
		});
		overwriteCSV(data);
	}

	public List<String[]> getAllRows() {
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


	private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}

}