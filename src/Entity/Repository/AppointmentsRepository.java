package Entity.Repository;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The Repository that accesses and changes everything related to appointments into our Appointment database
 */

public class AppointmentsRepository implements IRepository <String,String,Appointment,Appointment> {
	
	public static String FILE_NAME;

	/**
	 * Constructor for the {@code AppointmentRepository} which initialises the path of which the {@code AppointmentRepository} reads from
	 * @param path
	 */
    public AppointmentsRepository(String path) {
    	AppointmentsRepository.FILE_NAME = path;
    }

	/**
	 * Loads all the appointments as Appointment objects and adds them into a Array list of Appointments
	 */
	public static void loadAppointments() {
		int i = 0;
		List<String[]> data = getAllRows();
		for(String[] row : data) {
			if (i == 0) {
				i++;
				continue;
			}
			// if row is not empty,  should i put these inside the constructor of Appointments?
			Purpose appPur;
			if (Objects.equals(row[7], "CheckUp")) appPur = Purpose.CheckUp;
			else if (Objects.equals(row[7], "Surgery")) appPur = Purpose.Surgery;
			else if (Objects.equals(row[7], "Consultation")) appPur = Purpose.Consultation;
			else if (Objects.equals(row[7], "Other")) appPur = Purpose.Other;
			else appPur = null;

			Department appDept;
			if (Objects.equals(row[8], "Cardiology")) appDept = Department.Cardiology;
			else if (Objects.equals(row[8], "Neurology")) appDept = Department.Neurology;
			else if (Objects.equals(row[8], "Oncology")) appDept = Department.Oncology;
			else if (Objects.equals(row[8], "Dermatology")) appDept = Department.Dermatology;
			else if (Objects.equals(row[8], "Neurology")) appDept = Department.Endocrinology;
			else if (Objects.equals(row[8], "Oncology")) appDept = Department.Gastroenterology;
			else if (Objects.equals(row[8], "Dermatology")) appDept = Department.Nephrology;
			else if (Objects.equals(row[8], "Neurology")) appDept = Department.Pulmonology;
			else if (Objects.equals(row[8], "Oncology")) appDept = Department.Rheumatology;
			else if (Objects.equals(row[8], "Dermatology")) appDept = Department.ObstetricsGynecology;
			else appDept = Department.Others;


			Status appStatus = null;
			if (Objects.equals(row[9], "Confirmed")) appStatus = Status.Confirmed;
			else if (Objects.equals(row[9], "Cancelled")) appStatus = Status.Cancelled;
			else if (Objects.equals(row[9], "Completed")) appStatus = Status.Completed;
			else if (Objects.equals(row[9], "Pending")) appStatus = Status.Pending;
			else if (Objects.equals(row[9], "Unavailable")) appStatus = Status.Unavailable;
			else if (Objects.equals(row[9], "PendingPrescription")) appStatus = Status.PendingPrescription;

			Appointment apps = new Appointment(Boolean.valueOf(row[0]),row[1],row[2],row[3],row[4],row[5],row[6],appPur,appDept,appStatus, row[10], row[11], row[12], row[13], row[14]);
			AppointmentList.getInstance().getAppointmentList().add(apps);
			i++;
		}
	}

	/**
	 * Gathers all the contents of the file
	 * @return List of Arrays of Strings split row by row, separated by a comma in each row
	 */
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

	/**
	 * Updates the appointment file based on the current status of the appointments in the list of Appointments
	 * @param appointmentList
	 */
	public static void updateAppointmentFile(ArrayList<Appointment> appointmentList) {
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
		overwriteCSV(data);;
    }

	/**
	 * Updates the CSV file
	 * @param data
	 */
	private static void overwriteCSV(List<String[]> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
			for(String[] row : data) writer.write(String.join(",", row) + "\n");
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new appointment inside the appointment file
	 * @param attributes
	 */
	@Override
	public void createRecord(Object... attributes) {
		// TODO Auto-generated method stub
		// Convert attributes into an Appointment object
	    Appointment newAppointment = new Appointment(
	            (Boolean) attributes[0],
	            (String) attributes[1],
	            (String) attributes[2],
	            (String) attributes[3],
	            (String) attributes[4],
	            (String) attributes[5],
	            (String) attributes[6],
	            (Purpose) attributes[7],
	            (Department) attributes[8],
	            (Status) attributes[9],
	            (String) attributes[10],
	            (String) attributes[11],
	            (String) attributes[12],
	            (String) attributes[13],
	            (String) attributes[14]
	    );

	    // Append the new appointment directly to the CSV file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
	        writer.write(String.join(",", convertAppointmentToArray(newAppointment)) + "\n");
	    } catch (IOException e) {
	        System.out.println("An error occurred while creating the record.");
	        e.printStackTrace();
	    }
	}

	/**
	 * Finds an appointment inside the appointment file
	 * @param identifier
	 * @return The Appointment with the same appointmentID as the identifier
	 */
	@Override
	public Appointment readRecord(String identifier) {
		// TODO Auto-generated method stub
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
	        String row;
	        while ((row = reader.readLine()) != null) {
	            String[] values = row.split(",");
	            if (values.length > 1 && values[1].equals(identifier)) { // Assuming the AppointmentID is at index 1
	                return convertArrayToAppointment(values);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("An error occurred while reading the record.");
	        e.printStackTrace();
	    }
	    System.out.println("Appointment not found for ID: " + identifier);
	    return null;
	}

	/**
	 * Updates a change made in an appointment in the appointment file
	 * @param record
	 */
	@Override
	public void updateRecord(Appointment record) {
		// TODO Auto-generated method stub
		List<String[]> data = new ArrayList<>();
	    boolean recordUpdated = false;

	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
	        String row;
	        while ((row = reader.readLine()) != null) {
	            String[] values = row.split(",");
	            if (values.length > 1 && values[1].equals(record.getAppID())) { // Update matching record
	                data.add(convertAppointmentToArray(record));
	                recordUpdated = true;
	            } else {
	                data.add(values);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("An error occurred while updating the record.");
	        e.printStackTrace();
	    }

	    if (recordUpdated) {
	        overwriteCSV(data);
	    } else {
	        System.out.println("No matching appointment found to update for ID: " + record.getAppID());
	    }
	}

	/**
	 * Deletes a appointment inside the appointment file
	 * @param record
	 */
	@Override
	public void deleteRecord(String record) {
		// TODO Auto-generated method stub
		List<String[]> data = new ArrayList<>();
	    boolean recordDeleted = false;

	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
	        String row;
	        while ((row = reader.readLine()) != null) {
	            String[] values = row.split(",");
	            if (values.length > 1 && values[1].equals(record)) { // Skip the matching record
	                recordDeleted = true;
	            } else {
	                data.add(values);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("An error occurred while deleting the record.");
	        e.printStackTrace();
	    }

	    if (recordDeleted) {
	        overwriteCSV(data);
	    } else {
	        System.out.println("No matching appointment found to delete for ID: " + record);
	    }
	}


	/**
	 * Finds the appointments of a Patient
	 * @param patientId
	 * @return Array list of Appointments based on the hospitalID of the patient
	 */
	public ArrayList<Appointment> findAppointmentsByPatientId(String patientId) {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				if (values.length > 1 && values[5].equals(patientId)) { // Assuming the patientID is at index 5
					appointments.add(convertArrayToAppointment(values));
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred while reading the record.");
			e.printStackTrace();
		}
		return appointments;
	}


	/**
	 * Finds a specific appointment
	 * @param appointmentID
	 * @return The Appointment with the same Appointment ID as appointmentID
	 */
	public Appointment findAppointmentsByAppointmentId(String appointmentID) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				if (values.length > 1 && values[1].equals(appointmentID)) { // Assuming the patientID is at index 5
					return convertArrayToAppointment(values);
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred while reading the record.");
			e.printStackTrace();
		}
		System.out.println("The appointment that you are looking for does not exist.");
		return null;
	}


	/**
	 * Converts an Appointment object to array of String storing its parameters
	 * @param appointment
	 * @return A array of String with its entries being the parameters of appointment
	 */
	private String[] convertAppointmentToArray(Appointment appointment) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
	    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");

	    String[] row = new String[15];
	    row[0] = String.valueOf(appointment.getAvail());
	    row[1] = appointment.getAppID();
	    row[2] = appointment.getTimeOfApp().format(formatter);
	    row[3] = appointment.getDocID();
	    row[4] = appointment.getDocName();
	    row[5] = appointment.getPatID();
	    row[6] = appointment.getPatName();
	    row[7] = appointment.getPurposeOfApp() != null ? appointment.getPurposeOfApp().toString() : " ";
	    row[8] = appointment.getAppointmentDepartment().toString();
	    row[9] = appointment.getStatusOfApp() != null ? appointment.getStatusOfApp().toString() : " ";
	    row[10] = appointment.getAppointOutcomeRecord();
	    row[11] = appointment.getMedicine();
	    row[12] = appointment.getMedicineIssuedDate() != null ? appointment.getMedicineIssuedDate().format(formatter1) : " ";
	    row[13] = appointment.getDosage();
	    row[14] = appointment.getInstructions();
	    return row;
	}

	/**
	 * Converts an array of Strings to an Appointment object with its entries being the parameters of the Appointment
	 * @param row
	 * @return Appointment object with its parameters being the entries of the array
	 */
	private Appointment convertArrayToAppointment(String[] row) {
	    // This method will parse the row array back into an Appointment object.
	    // Assuming valid parsing and conversions, the process would look similar to what you have in loadAppointments.
	    Purpose appPur = Purpose.valueOf(row[7]);
	    Department appDept = Department.valueOf(row[8]);
	    Status appStatus = Status.valueOf(row[9]);

	    return new Appointment(
	            Boolean.parseBoolean(row[0]),
	            row[1],
	            row[2],
	            row[3],
	            row[4],
	            row[5],
	            row[6],
	            appPur,
	            appDept,
	            appStatus,
	            row[10],
	            row[11],
	            row[12],
	            row[13],
	            row[14]
	    );
	}

	/**
	 * Creates and updates the Appointment file with a new Appointment object
	 * @param newSlot
	 */
	public void createAppointments(String newSlot) {
		try {
			File appointmentFile = new File(FILE_NAME);
			if (appointmentFile.createNewFile()) {
				FileWriter headerWriter = new FileWriter(appointmentFile);
				headerWriter.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,Department,StatusOfAppointment,AppointmentOutcomeRecord,Medicine,IssuedDate,Dosage,Instructions\n");
				headerWriter.close();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
			writer.write(newSlot);
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
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
 }





