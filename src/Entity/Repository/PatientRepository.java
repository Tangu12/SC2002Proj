//package Entity.Repository;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import Entity.User.Patient;
//
//public class PatientRepository {
//private static String PATIENT_FILE_NAME = "program_files/patients.csv";
//	
//	public static void loadPatientlist() {
//		List<String[]> data = new ArrayList<>();
//
//		//Read the CSV file
//		try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_FILE_NAME))) {
//			String row;
//			while ((row = reader.readLine()) != null) {
//				String[] values = row.split(",");
//				data.add(values);
//			}
//		} catch (IOException e) {
//			//e.printStackTrace();
//			System.out.println("File is not created yet!!");
//		}
//		boolean headerRow = true;
//		for(String[] row : data) {
//			if(headerRow) headerRow = false;
//			else {
//				String[] addingPatient = new String[2];
//				addingPatient[0] = row[0];
//				addingPatient[1] = row[1];
//				Patient.getPatientList().add(addingPatient);
//			}
//		}
//    }
////	
//	public static void updatePatientlist() {
//		
//	}
//}
