package Entity.Repository;

import Entity.Appointment;
import Entity.Medicine;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
//import java.io.*;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.List;


public class AppointmentsRepository implements IRepository {

    public String path;

    public AppointmentsRepository(String path) {
        this.path = path;
    }

    // Creating a new appointment entry inside the appointment file
    public void createRecord(Appointment newAppointment) {
         {
             if(readRecord(newAppointment.getAppID()) != null) {
                 System.out.println("Error!! Appointment already exists !!");
                 return;
             }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.write(String.valueOf(newAppointment.getAvail())  + "," + newAppointment.getAppID() + "," + newAppointment.getTimeOfApp() + "," + newAppointment.getDocID() + "," + newAppointment.getDocName() + "," + newAppointment.getPatID() + "," + newAppointment.getPatName() + "," + newAppointment.getPurposeOfApp() + "," + newAppointment.getAppointmentDepartment() + "," + newAppointment.getStatusOfApp() + "," + newAppointment.getAppointOutcomeRecord());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }


    // Finding instance of a appointment in appointment file and returning the appointment object
    public Appointment readRecord(String inputID){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String appointmentID = data[1].trim();

                if (inputID.equals(appointmentID)) {

                    boolean availability = Boolean.parseBoolean(data[0].trim());
                    LocalDateTime appointmentTime = LocalDateTime.parse(data[2], formatter);
                    String doctorID = data[3];
                    String doctorName = data[4];
                    String patientID = data[5];
                    String patientName = data[6];
                    String purposeOfAppointment = data[7];
                    String appointmentDepartment = data[8];
                    String statusOfAppointment = data[9];
                    String appointmentOutcomeRecord = data[10];

                    return new Appointment(availability, appointmentID, appointmentTime, doctorID, doctorName, patientID, patientName, purposeOfAppointment.valueOf(purposeOfAppointment), appointmentDepartment, statusOfAppointment, appointmentOutcomeRecord);
                }  // how tf to fix the enum
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }
        return null;
    }


    // Updating the data of an appointment slot inside the appointment file
    public void updateRecord(Appointment appointment){
        ArrayList<Appointment> tempAppointmentList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        boolean isUpdated = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                boolean availability = Boolean.parseBoolean(data[0].trim());
                String appointmentID = data[1].trim();
                LocalDateTime appointmentTime = LocalDateTime.parse(data[2], formatter);
                String doctorID = data[3];
                String doctorName = data[4];
                String patientID = data[5];
                String patientName = data[6];
                String purposeOfAppointment = data[7];
                String appointmentDepartment = data[8];
                String statusOfAppointment = data[9];
                String appointmentOutcomeRecord = data[10];
                                            // how to make enums in the constructor?
                Appointment temp = new Appointment((availability, appointmentID, appointmentTime, doctorID, doctorName, patientID, patientName, purposeOfAppointment, appointmentDepartment, statusOfAppointment, appointmentOutcomeRecord);
                if (appointment.getAppID().equalsIgnoreCase(temp.getAppID())) {
                    tempAppointmentList.add(appointment);
                }
                else {
                    tempAppointmentList.add(temp);
                }
            }
        } catch (Exception e) {
                System.out.println("Error accessing Appointment file !!");
                e.printStackTrace();
            }

            // Once the whole file is read, we copy the file back

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,Department,StatusOfAppointment,AppointmentOutcomeRecord\n\n");
                writer.newLine();

                for (Appointment tempAppointment : tempAppointmentList) {
                    writer.write(tempAppointment.getAvail() + "," + tempAppointment.getAppID() + "," + tempAppointment.getTimeOfApp() + "," + tempAppointment.getDocID() + tempAppointment.getDocName() + "," + tempAppointment.getPatID() + "," + tempAppointment.getPatName() + "," + tempAppointment.getPurposeOfApp() + "," +  tempAppointment.getAppointmentDepartment() + "," + tempAppointment.getStatusOfApp() + tempAppointment.getAppointOutcomeRecord());
                    writer.newLine();
                }
                if (!isUpdated) {
                    System.out.println("Error, appointment not found! File is unchanged.");
                }
            } catch (IOException e) {
                System.out.println("Error writing to Appointment file!");
                e.printStackTrace();
            }
        }


    public void deleteRecord(Appointment appointment) {
        ArrayList<Appointment> tempAppointmentList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        boolean isDeleted = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                boolean availability = Boolean.parseBoolean(data[0].trim());
                String appointmentID = data[1].trim();
                LocalDateTime appointmentTime = LocalDateTime.parse(data[2], formatter);
                String doctorID = data[3];
                String doctorName = data[4];
                String patientID = data[5];
                String patientName = data[6];
                String purposeOfAppointment = data[7];
                String appointmentDepartment = data[8];
                String statusOfAppointment = data[9];
                String appointmentOutcomeRecord = data[10];

                Appointment temp = new Appointment((availability, appointmentID, appointmentTime, doctorID, doctorName, patientID, patientName, purposeOfAppointment, appointmentDepartment, statusOfAppointment, appointmentOutcomeRecord);
                if (appointment.getAppID().equalsIgnoreCase(temp.getAppID())) {
                    isDeleted = true;
                }
                else {
                    tempAppointmentList.add(temp);
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing Appointment file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,Department,StatusOfAppointment,AppointmentOutcomeRecord\n\n");
            writer.newLine();

            for (Appointment tempAppointment : tempAppointmentList) {
                writer.write(tempAppointment.getAvail() + "," + tempAppointment.getAppID() + "," + tempAppointment.getTimeOfApp() + "," + tempAppointment.getDocID() + tempAppointment.getDocName() + "," + tempAppointment.getPatID() + "," + tempAppointment.getPatName() + "," + tempAppointment.getPurposeOfApp() + "," +  tempAppointment.getAppointmentDepartment() + "," + tempAppointment.getStatusOfApp() + tempAppointment.getAppointOutcomeRecord());
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, appointment not found! File is unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to Appointment file!");
            e.printStackTrace();
        }
    }


    // sortRecord According to time function

}



//    public List<String[]> getAllRows() {
//        List<String[]> data = new ArrayList<>();
//        //Read the CSV file
//        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
//            String row;
//            while ((row = reader.readLine()) != null) {
//                String[] values = row.split(",");
//                data.add(values);
//            }
//        } catch (IOException e) {
//            //e.printStackTrace();
//            System.out.println("File is not created yet!!");
//        }
//        return data;
//    }
//
//
//    private void overwriteCSV(List<String[]> data) {
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
//            for(String[] row : data) writer.write(String.join(",", row) + "\n");
//            writer.close();
//        } catch(IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }






