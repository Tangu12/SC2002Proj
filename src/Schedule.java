import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.xssf.usermodel.XSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class Schedule {
	private static final String FILE_NAME = "/Users/justintangu/Desktop/Java Programming/SC2002Proj/program_files/appointments.xlsx";
	private int columnWidth = 20; 
	
	public void createAppointmentSlot(Appointment app) { //for staff to add slots
			File file = new File(FILE_NAME);
	        if (!file.exists()) {
				try (XSSFWorkbook workbook = new XSSFWorkbook()) {
		            XSSFSheet sheet = workbook.createSheet("Appointments");  // Create a new sheet
	
		            // Add headers
		            String[] headers = {"Availability", "AppointmentID", "TimeOfAppointment", "DoctorID", "DoctorName", "PatientID", "PatientName", "PurposeOfAppointment", "StatusOfAppointment", "Cost", "PaymentStatus"};
		            Row headerRow = sheet.createRow(0);  // First row for headers
		            for (int i = 0; i < headers.length; i++) {
		                Cell cell = headerRow.createCell(i);
		                cell.setCellValue(headers[i]);
		            	}
		            
		            // Write the workbook to a file
		            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
		                workbook.write(fos);  // Write the newly created workbook to file
		                System.out.println("New file created with headers.");
		            	}
		        
		            } catch (IOException e) {
			            System.out.println("Error while creating a new file.");
			            e.printStackTrace();
			        }
	        }
		    
			if(checkAppointment(app.getAppID())) System.out.println("The slot is already added!!!");
			else {
				try (FileInputStream fis = new FileInputStream(FILE_NAME);
			            XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
	
			            XSSFSheet sheet = workbook.getSheetAt(0);  // Get the first sheet
			            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
			            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
			            Row newRow = sheet.createRow(lastRowNum + 1);  // Create a new row
			            Cell cell0 = newRow.createCell(0);
		                cell0.setCellValue(app.getAvail());  // Set cell value with Availibility
			            Cell cell1 = newRow.createCell(1);
		                cell1.setCellValue(app.getAppID());  // Set cell value with AppointmentID
		                Cell cell2 = newRow.createCell(2);
		                cell2.setCellValue(app.getTimeOfApp().format(formatter));  // Set cell value with TimeOfAppointment
		                Cell cell3 = newRow.createCell(3);
		                cell3.setCellValue(app.getDocID());  // Set cell value with DoctorID
		                Cell cell4 = newRow.createCell(4);
		                cell4.setCellValue(app.getDocName());  // Set cell value with DoctorName                
	
			            // Write the updated data to the file
			            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
			                workbook.write(fos);
			                System.out.println("Data successfully added to the existing file.");
			            }
	
			        } catch (IOException e) {
			            System.out.println("Error while writing to the existing file.");
			            e.printStackTrace();
			        }
			}
	}
	
	public void scheduleAppointment(String appID, String patID, String patName, String purpose) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            rowIterator.next(); // skip header row
            int index = 1;
            // Till there is an element condition holds true 
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
            	Cell cellAvail = row.getCell(0);
            	Cell cellAppID = row.getCell(1);
            	if(cellAvail.getBooleanCellValue() && cellAppID.getStringCellValue().equals(appID)) break;
            	index++;
            } 
            
            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
            if(index > lastRowNum) {
            	System.out.println("Please only enter the available Appointment ID");
            	return;
            }
            Row row = sheet.getRow(index);
            Cell cell = row.getCell(0);
            if (cell == null) cell = row.createCell(0);
            cell.setCellValue(false); // Set new value
            
            cell = row.getCell(5); // cell under PatientID
            if (cell == null) cell = row.createCell(5);
            cell.setCellValue(patID); // Set new value
            
            cell = row.getCell(6); // cell under PatientName
            if (cell == null) cell = row.createCell(6);
            cell.setCellValue(patName); // Set new value
            
            cell = row.getCell(7); // cell under Purpose
            if (cell == null) cell = row.createCell(7);
            cell.setCellValue(purpose); // Set new value
            
            cell = row.getCell(8); // cell under Appointment Status
            if (cell == null) cell = row.createCell(8);
            cell.setCellValue(status.Confirmed.name()); // Set new value
            
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
                System.out.println("Cell updated successfully.");
            }
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
	}
	
	public void rescheduleAppointment(String orgAppID, String newAppID, String patID) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            rowIterator.next(); // skip header row
            int index = 1;
            // Till there is an element condition holds true 
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
  
            	Cell cellAvail = row.getCell(0); // cell from Availability Column
            	Cell cellAppID = row.getCell(1); // cell from Appointment ID
            	Cell cellPatID = row.getCell(5); // cell from Patient ID
            	if(cellAvail != null && !cellAvail.getBooleanCellValue() && cellAppID != null && cellAppID.getStringCellValue().equals(orgAppID)
            			&& cellPatID != null && cellPatID.getStringCellValue().equals(patID)) break;
            	index++;
            } 
            
            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
            if(index > lastRowNum) {
            	System.out.println("Please only enter YOUR SCHEDULED Appointment ID!!!");
            	return;
            }
            Row row = sheet.getRow(index);
            
            Cell cell = row.getCell(6); // cell under PatientName
            String patientName = cell.getStringCellValue();
            
            cell = row.getCell(7); // cell under Purpose
            String purpose = cell.getStringCellValue();

            cancelAppointment(orgAppID, patID);
            scheduleAppointment(newAppID, patID, patientName, purpose);
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
	}
	
	public void cancelAppointment(String appID, String patID) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            rowIterator.next(); // skip header row
            int index = 1;
            // Till there is an element condition holds true 
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
  
            	Cell cellAvail = row.getCell(0); // cell from Availability Column
            	Cell cellAppID = row.getCell(1); // cell from Appointment ID
            	Cell cellPatID = row.getCell(5); // cell from Patient ID
            	if(!cellAvail.getBooleanCellValue() && cellAppID.getStringCellValue().equals(appID) && 
            			cellPatID.getStringCellValue().equals(patID)) break;
            	index++;
            } 
            
            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
            if(index > lastRowNum) {
            	System.out.println("Please only enter YOUR SCHEDULED Appointment ID!!!");
            	return;
            }
            Row row = sheet.getRow(index);
            Cell cell = row.getCell(0);
            if (cell == null) cell = row.createCell(0);
            cell.setCellValue(true); // Set new value
            cell = row.getCell(8);
            if (cell == null) cell = row.createCell(8);
            cell.setCellValue(status.Cancelled.name()); // Set new value
            
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
                System.out.println("Cell updated successfully.");
            }
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
	}
	
	public void viewAvailAppointmentSlots() {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
            
            rowIterator.next(); //skip header row
            // Till there is an element condition holds true 
            System.out.println(formatCell("Appointment ID", columnWidth)
            		+ formatCell("App Date and Time", columnWidth)
            		+ formatCell("Doctor ID", columnWidth)
            		+ formatCell("Doctor Name", columnWidth));
            System.out.println("-----------------------------------------------------------------------");
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
  
                // For each row, iterate through all the columns 
                Iterator<Cell> cellIterator = row.cellIterator(); 
  
            	Cell cell = cellIterator.next(); 
            	if(cell.getBooleanCellValue()) {
            		for(int i = 0; i<4; i++) {
            			if(cellIterator.hasNext()) {
            				cell = cellIterator.next(); 
                			System.out.print(formatCell(cell.getStringCellValue(), columnWidth));
            			}
            		}
            		System.out.println("");
            	}
            } 
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
        System.out.println("");
	}
	
	public void viewScheduledAppointments(String patID) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
            
            rowIterator.next(); //skip header row
            // Till there is an element condition holds true 
            System.out.println(formatCell("Appointment ID", columnWidth)
            		+ formatCell("App Date & Time", columnWidth)
            		+ formatCell("Doctor ID", columnWidth)
            		+ formatCell("Doctor Name", columnWidth)
            		+ formatCell("Patient ID", columnWidth)
            		+ formatCell("Patient Name", columnWidth)
            		+ formatCell("Purpose Of App", columnWidth)
            		+ formatCell("Status Of App", columnWidth));
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
  
                // For each row, iterate through all the columns 
                Iterator<Cell> cellIterator = row.cellIterator(); 
  
            	Cell cell = cellIterator.next(); 
            	Cell cellAppStatus = row.getCell(8);
            	Cell cellPatID = row.getCell(5);
            	if(!cell.getBooleanCellValue() && cellAppStatus != null && cellAppStatus.getStringCellValue().equals("Confirmed")
            			&& cellPatID != null && cellPatID.getStringCellValue().equals(patID)) {
            		for(int i = 0; i<8; i++) {
            			if(cellIterator.hasNext()) {
            				cell = cellIterator.next(); 
            				if(cell!=null) System.out.print(formatCell(cell.getStringCellValue(), columnWidth));
            				else System.out.print(formatCell(null, columnWidth));
            			}
            		}
            		System.out.println("");
            	}
            } 
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
        System.out.println("");
	}
	
	public void viewAppointmentOutcomeRecords(String patID) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
            
            rowIterator.next(); //skip header row
            // Till there is an element condition holds true 
            System.out.println(formatCell("Appointment ID", columnWidth)
            		+ formatCell("App Date & Time", columnWidth)
            		+ formatCell("Doctor ID", columnWidth)
            		+ formatCell("Doctor Name", columnWidth)
            		+ formatCell("Patient ID", columnWidth)
            		+ formatCell("Patient Name", columnWidth)
            		+ formatCell("Purpose Of App", columnWidth)
            		+ formatCell("Status Of App", columnWidth)
            		+ formatCell("Cost", columnWidth)
            		+ formatCell("Payment Status", columnWidth));
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
  
                // For each row, iterate through all the columns 
                Iterator<Cell> cellIterator = row.cellIterator(); 
  
            	Cell cell = cellIterator.next(); 
            	Cell cellAppStatus = row.getCell(8);
            	Cell cellPatID = row.getCell(5);
            	if(!cell.getBooleanCellValue() && cellAppStatus != null && !cellAppStatus.getStringCellValue().equals("Cancelled")
            			&& cellPatID != null && cellPatID.getStringCellValue().equals(patID)) {
            		for(int i = 0; i<10; i++) {
            			if(cellIterator.hasNext()) {
            				cell = cellIterator.next(); 
            				if(cell!=null) System.out.print(formatCell(cell.getStringCellValue(), columnWidth));
            				else System.out.print(formatCell(null, columnWidth));
            			}
            		}
            		System.out.println("");
            	}
            } 
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
        System.out.println("");
	}
	
	public void changePaymentStatus(String appID, paymentStatus stat) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            rowIterator.next(); // skip header row
            int index = 1;
            // Till there is an element condition holds true 
            while (rowIterator.hasNext()) { 
                Row row = rowIterator.next(); 
            	Cell cellAvail = row.getCell(0); // cell from Availability Column
            	Cell cellAppID = row.getCell(1); // cell from Appointment ID
            	if(cellAvail != null && !cellAvail.getBooleanCellValue() && cellAppID != null && cellAppID.getStringCellValue().equals(appID)) break;
            	index++;
            } 
            
            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
            if(index > lastRowNum) {
            	System.out.println("Please only enter YOUR SCHEDULED Appointment ID!!!");
            	return;
            }
            Row row = sheet.getRow(index);
            Cell cell = row.getCell(10);
            if (cell == null) cell = row.createCell(10);
            cell.setCellValue(stat.name()); // Set new value
            
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
                System.out.println("Cell updated successfully.");
            }
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
	}
	
	public void addChangeCost(String appID, double cost) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            rowIterator.next(); // skip header row
            int index = 1;
            // Till there is an element condition holds true 
            while (rowIterator.hasNext()) { 
                Row row = rowIterator.next(); 
            	Cell cellAvail = row.getCell(0); // cell from Availability Column
            	Cell cellAppID = row.getCell(1); // cell from Appointment ID
            	if(cellAvail != null && !cellAvail.getBooleanCellValue() && cellAppID != null && cellAppID.getStringCellValue().equals(appID)) break;
            	index++;
            } 
            
            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
            if(index > lastRowNum) {
            	System.out.println("Please only enter YOUR SCHEDULED Appointment ID!!!");
            	return;
            }
            Row row = sheet.getRow(index);
            Cell cell = row.getCell(9);
            if (cell == null) cell = row.createCell(9);
            cell.setCellValue(cost); // Set new value
            
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
                System.out.println("Cell updated successfully.");
            }
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
	}
	
	public void changeAppointmentStatus(String appID, status stat) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            rowIterator.next(); // skip header row
            int index = 1;
            // Till there is an element condition holds true 
            while (rowIterator.hasNext()) { 
                Row row = rowIterator.next(); 
            	Cell cellAvail = row.getCell(0); // cell from Availability Column
            	Cell cellAppID = row.getCell(1); // cell from Appointment ID
            	if(cellAvail != null && !cellAvail.getBooleanCellValue() && cellAppID != null && cellAppID.getStringCellValue().equals(appID)) break;
            	index++;
            } 
            
            int lastRowNum = sheet.getLastRowNum();  // Get the last row number
            if(index > lastRowNum) {
            	System.out.println("Please only enter YOUR SCHEDULED Appointment ID!!!");
            	return;
            }
            Row row = sheet.getRow(index);
            Cell cell = row.getCell(8);
            if (cell == null) cell = row.createCell(8);
            cell.setCellValue(stat.name()); // Set new value
			if (stat == status.Cancelled) {
				cell = row.getCell(0);
				if (cell == null) cell = row.createCell(0);
		        cell.setCellValue(true); // Set new value
			}
            
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
                System.out.println("Cell updated successfully.");
            }
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
	}
	
	private boolean checkAppointment(String appID) {
		// Try block to check for exceptions 
        try(// Reading file from local directory 
            FileInputStream file = new FileInputStream(new File(FILE_NAME)); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file);) { 
        	
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
  
            // Iterate through each rows one by one 
            Iterator<Row> rowIterator = sheet.iterator(); 
  
            // Till there is an element condition holds true 
            rowIterator.hasNext(); //skip header row
            while (rowIterator.hasNext()) { 
  
                Row row = rowIterator.next(); 
  
                // For each row, iterate through all the columns 
                Iterator<Cell> cellIterator = row.cellIterator(); 
  
                cellIterator.next(); // skip first cell
            	Cell cell = cellIterator.next(); //second cell
            	if(cell.getStringCellValue().equals(appID)) return true;
            } 
  
            // Closing file output streams 
            file.close();  
        } 
  
        // Catch block to handle exceptions 
        catch (Exception e) { 
  
            // Display the exception along with line number using printStackTrace() method 
            e.printStackTrace(); 
        } 
        
        return false;
	}
	
	
	private static String formatCell(String value, int width) {
	    if (value == null) {
	        value = "";  // Handle null values
	    }
	    return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
	}
		
}
