package Tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Boundary.UserUI.PatientMainPage;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.Enums.Status;
import Entity.Repository.AppointmentsRepository;

public class TestPatientFunction {

	public static void main(String[] args) throws Exception {
//		AppointmentsRepository.loadAppointments();
//		PatientMainPage test = new PatientMainPage();
//		test.mainpage();
//        AppointmentsRepository.updateAppointmentFile(AppointmentList.getAppointmentList());
		AppointmentsRepository appRepo = new AppointmentsRepository();
		Boolean avail = true;
		String appID = "APP123";
		String timeOfAppString = "20/10/2024 14:30";  // Example date and time in dd/MM/yyyy HH:mm format
		String docID = "DOC456";
		String docName = "Dr. John Smi";
		String patID = "PAT789";
		String patName = "John Doe";
		Purpose purposeOfApp = Purpose.Consultation;  // Replace with an actual value from your Purpose enum
		Department department = Department.Cardiology;  // Replace with an actual value from your Department enum
		Status statusOfApp = Status.Confirmed;  // Replace with an actual value from your Status enum
		String appointOutcomeRecord = "Follow-up required";
		String medicine = "Aspirin";
		String medicineIssuedDateString = "22/10/2024";  // Example date in dd/MM/yyyy format
		String dosage = "100 mg";
		String instructions = "Take with food";

		// Parse the date strings into LocalDateTime and LocalDate objects
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
		LocalDateTime timeOfApp = LocalDateTime.parse(timeOfAppString, dateTimeFormatter);
		LocalDate medicineIssuedDate = LocalDate.parse(medicineIssuedDateString, dateFormatter);

		// Creating the Appointment object
		Appointment app = new Appointment(
		    true,
		    appID,
		    timeOfAppString,
		    docID,
		    docName,
		    patID,
		    patName,
		    purposeOfApp,
		    department,
		    statusOfApp,
		    appointOutcomeRecord,
		    medicine,
		    medicineIssuedDateString,
		    dosage,
		    instructions
		);
//		appRepo.createRecord(true,
//			    appID,
//			    timeOfAppString,
//			    docID,
//			    docName,
//			    patID,
//			    patName,
//			    purposeOfApp,
//			    department,
//			    statusOfApp,
//			    appointOutcomeRecord,
//			    medicine,
//			    medicineIssuedDateString,
//			    dosage,
//			    instructions);
		appRepo.deleteRecord(appID);
	}

}
