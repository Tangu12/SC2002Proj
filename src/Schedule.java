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
import java.util.List;

public class Schedule {
	private static final String FILE_NAME = "C:/Users/steve/Documents/SC2002(OOP)/codes/GroupProject/appointments.csv";
	private int columnWidth = 20; 
	
	public void createAppointmentSlot() {
		try {
			File appointmentFile = new File(FILE_NAME);
			if (appointmentFile.createNewFile()) {
				FileWriter headerWriter = new FileWriter(appointmentFile);
				headerWriter.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,StatusOfAppointment,Cost,PaymentStatus\n");
				headerWriter.close();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			DateTimeFormatter formatterForID = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			LocalDate now = LocalDateTime.now().toLocalDate();
			for(int i = 9; i <= 21; i++) {
				LocalDateTime firstSlot = LocalDateTime.of(now, LocalTime.of(i, 0, 0));
				String newFirstSlot = String.valueOf(true) + "," + "A" + firstSlot.format(formatterForID) + "," + firstSlot.format(formatter) + ", , , , , , , , "; //add space to avoid null data when retrieve data
				writer.write(String.join(",", newFirstSlot) + "\n");
				LocalDateTime secondSlot = LocalDateTime.of(now, LocalTime.of(i, 30, 0));
				String newSecondSlot = String.valueOf(true) + "," + "A" + secondSlot.format(formatterForID) + "," + secondSlot.format(formatter) + ", , , , , , , , "; //add space to avoid null data when retrieve data
				writer.write(String.join(",", newSecondSlot) + "\n");
			}
	        writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
	        e.printStackTrace();
		}
	}
	
	public void scheduleAppointment(String appID, String patID, String patName, String purpose) {
		List<String[]> data = getAllRows();
		boolean foundData = false;
		
		for(String[] row : data) {
			if(row[1].equals(appID)) {
				if(row[0].equals("FALSE") || row[0].equals("false")) {
					System.out.println("The slot is occupied!!!");
					return;
				}
				row[0] = String.valueOf(false);
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
		boolean foundorgData = false;
		boolean foundnewData = false;
		String patName = "";
		String purpose = "";
		
		for(String[] row : data) {
			if(row[1].equals(orgAppID) && (row[0].equals("FALSE") || row[0].equals("false"))) {
				if(!row[5].equals(patID)) {
					System.out.println("Please delete your own appointment only!");
					return;
				}
				row[0] = String.valueOf(true);
				patName = row[6];
				purpose = row[7];
				foundorgData = true;
				break;
			}
		}
		
		if(foundorgData) {
			for(String[] row : data) {
				if(row[1].equals(newAppID)) {
					if(row[0].equals("FALSE") || row[0].equals("false")) {
						System.out.println("The slot is occupied!!!");
						return;
					}
					row[0] = String.valueOf(false);
					row[5] = patID;
					row[6] = patName;
					row[7] = purpose;
					foundnewData = true;
					break;
				}
			}
		}
		
		if (!foundorgData && !foundnewData) {
			System.out.println("The Appointment ID you want to change and new Appointment ID cannot be found! Please try again.");
			return;
		}
		else if (!foundorgData) {
			System.out.println("The Appointment ID you want to change cannot be found! Please try again.");
			return;
		}
		else if (!foundnewData) {
			System.out.println("The new Appointment ID cannot be found!\nThe Appointment ID you want to change has been cancelled. Please schedule again!");
			return;
		}
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
	
	public void viewScheduledAppointments(String patID) {
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
        	if((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(patID) && (row[8].equals("Pending") || row[8].equals("Confirmed"))) {
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
	
	
	public void viewAppointmentOutcomeRecords(String patID) {
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
        	if((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(patID) && row[8].equals("Completed")) {
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
	
	private List<String[]> getAllRows() {
		List<String[]> data = new ArrayList<>();

        //Read the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] values = row.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
	
	private static String formatCell(String value, int width) {
	    if (value == " ") {
	        value = "";
	    }
	    return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}

}
