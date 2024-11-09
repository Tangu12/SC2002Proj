package Entity.Repository;

import Entity.Appointment;

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


    public void createRecord(String availability, String appointmentID, String time, String doctorID, String doctorName, String patientID, String patientName, String purposeOfAppointment, String appointmentDepartment, String statusOfAppointment, String appointmentOutcomeRecord) {
         {
            String newAppointment = availability + "," + appointmentID + "," + time + "," + doctorID + "," + doctorName + "," + patientID + "," + patientName + "," + purposeOfAppointment + "," + appointmentDepartment + "," + statusOfAppointment + "," + appointmentOutcomeRecord;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.write(newAppointment);
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }


    public Appointment readRecord(String appoointmentID){
        List<String[]> data = getAllRows();
        for (String[] row : data) {

        }
    }



    public void updateRecord(String appointmentID, String header, String updatedData){
        List<String[]> data = getAllRows();
        int i = 0;
        for (String[] row : data) {
            while (!Objects.equals(row[i], header)) {
                i++;
            }
            if (Objects.equals(appointmentID, row[1])) {
                row[i] = updatedData;
                break;
            }
        }
        overwriteCSV(data);
    }


    public void deleteRecord(String id) {
        List<String[]> data = getAllRows();
        boolean found = false;

        // Remove the record with the specified ID

        data.removeIf(record -> {
            if (record[0].equals(id)) {
                found = true;
                return true;
            }
            return false;
        });

        if (found) {
            overwriteCSV(data); // Write the updated list back to the CSV
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record with ID " + id + " not found.");
        }
    }



    public void sortRecord() {
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


    public List<String[]> getAllRows() {
        List<String[]> data = new ArrayList<>();
        //Read the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for(String[] row : data) writer.write(String.join(",", row) + "\n");
            writer.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}




//List<String[]> data = new ArrayList<>();
//
//        //Read the CSV file
//        try (BufferedReader reader = new BufferedReader(new FileReader())) {
//            String row;
//            while ((row = reader.readLine()) != null) {
//                String[] values = row.split(",");
//                data.add(values);
//            }
//        } catch (IOException e) {
//            //e.printStackTrace();
//            System.out.println("File is not created yet!!");
//        }
//
//
//        private void overwriteCSV(List<String[]> data) {
//            try {
//                BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
//                for(String[] row : data) writer.write(String.join(",", row) + "\n");
//                writer.close();
//            } catch(IOException e) {
//                System.out.println("An error occurred.");
//                e.printStackTrace();
//            }
//        }


//(String docID, String docName, int plusDay, department docDep)
//        int startTime, endTime;
//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
//DateTimeFormatter formatterForID = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//DateTimeFormatter formatterForInput = DateTimeFormatter.ofPattern("d/M/yyyy");
//LocalDate updateDate = LocalDate.now().plusDays(plusDay);
//printAppointmentOfaDayForDoc(updateDate.format(formatterForInput), docID);
//        do {
//        System.out.println("Please enter when your shift starts: (0 -> 23) (-1 to return)");
//startTime = InputScanner.sc.nextInt();
//            if(startTime == -1) return;
//        System.out.println("Please enter when your shift ends: (0 -> 24) (-1 to return)");
//endTime = InputScanner.sc.nextInt();
//            if(endTime == -1) return;
//        if(startTime < 0 && endTime < 0 && startTime >= 24 && endTime> 24 && startTime >= endTime)
//        System.out.println("Please only enter available time and endTime must be greater than startTime");
//        } while(startTime < 0 && endTime < 0 && startTime >= 24 && endTime> 24 && startTime >= endTime);
//        InputScanner.sc.nextLine();
//
//        try {
//File appointmentFile = new File(FILE_NAME);
//            if (appointmentFile.createNewFile()) {
//FileWriter headerWriter = new FileWriter(appointmentFile);
//                headerWriter.write("Availability,AppointmentID,TimeOfAppointment,DoctorID,DoctorName,PatientID,PatientName,PurposeOfAppointment,Department,StatusOfAppointment,Cost,PaymentStatus,AppointmentOutcomeRecord\n");
//                headerWriter.close();
//            }
//BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
//            for(int i = startTime; i < endTime; i++) {
//LocalDateTime firstSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 0, 0));
//String newFirstSlot = String.valueOf(true) + "," + "A" + firstSlot.format(formatterForID) + docID + "," + firstSlot.format(formatter) + "," + docID + "," + docName + ", , , ," + docDep.toString() + ", , , , , , "; //add space to avoid null data when retrieve data
//                if(!checkAppIDExist("A" + firstSlot.format(formatterForID) + docID)) writer.write(String.join(",", newFirstSlot) + "\n");
//LocalDateTime secondSlot = LocalDateTime.of(updateDate, LocalTime.of(i, 30, 0));
//String newSecondSlot = String.valueOf(true) + "," + "A" + secondSlot.format(formatterForID) + docID + "," + secondSlot.format(formatter) + "," + docID + "," + docName + ", , , ," + docDep.toString() + ", , , , , , "; //add space to avoid null data when retrieve data
//                if(!checkAppIDExist("A" + secondSlot.format(formatterForID) + docID)) writer.write(String.join(",", newSecondSlot) + "\n");
//        }
//        writer.close();
//        } catch(IOException e) {
//        System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//sortFile();
