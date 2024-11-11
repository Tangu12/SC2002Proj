package Entity.Repository;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class AppointmentsRepository implements IRepository <String,String,Appointment,Appointment> {
	
	private static final String FILE_NAME = "program_files/appointments.csv";
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
			else if (Objects.equals(row[7], "Others")) appPur = Purpose.Other;
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
			else if (Objects.equals(row[9], "PrescriptionPending")) appStatus = Status.PendingPrescription;

			Appointment apps = new Appointment(Boolean.valueOf(row[0]),row[1],row[2],row[3],row[4],row[5],row[6],appPur,appDept,appStatus, " ", row[11], row[12], row[13], row[14]);
			AppointmentList.getInstance().getAppointmentList().add(apps);
			i++;
		}
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
        updateFile(data);
    }
	
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

	public static void updateFile(List<String[]> data) {
		overwriteCSV(data);
	}

	@Override
	public void createRecord(Object... attributes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Appointment readRecord(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRecord(Appointment record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRecord(String record) {
		// TODO Auto-generated method stub
		
	}
 }





