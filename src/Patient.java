import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    public Patient(String name, String hospitalId,Domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        Schedule schedule = new Schedule();
//        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
//
//        List<String[]> data = schedule.getAllRows();
//        for(String[] row : data) {
//            if ((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(this.getHospitalId()) && (row[8].equals("Pending") || row[8].equals("Confirmed"))) {
//                Appointment apps = new Appointment(row[1], row[2], row[3], row[4]);
//                appointmentList.add(apps);
//            }
//        }

        int choice;
        do {
            System.out.println( "Choose the number of function:\n"
                    + "(1) Schedule Appointment\n"
                    + "(2) Reschedule Appointment\n"
                    + "(3) Cancel Appointment\n"
                    + "(4) View your scheduled Appointments\n"
                    + "(5) View your pending Appointments\n"
                    + "(6) View Appointment Outcome Records\n"
                    + "(7) Exit\n");
            try {
                choice = InputScanner.sc.nextInt();
                InputScanner.sc.nextLine(); // Buffer
                switch (choice) {
                    case 1:
                        System.out.println("Choose number of the purpose of your Appointment:\n" // need exception handling?
                                + "(1) CheckUp\n"
                                + "(2) Surgery\n"
                                + "(3) Consultation\n"
                                + "(4) Other\n");

                        int choicePur = InputScanner.sc.nextInt();
                        InputScanner.sc.nextLine(); // Buffer
                        purpose pur;
                        if (choicePur == 1) pur = purpose.CheckUp;
                        else if (choicePur == 2) pur = purpose.Surgery;
                        else if (choicePur == 3) pur = purpose.Consultation;
                        else pur = purpose.Other; // No exception handling yet, plus abit suss
                        // enter the department of your visit (check Appointments.java)
                        String prefDoctor = null;
                        while (prefDoctor == null) {
                            System.out.println("Enter your preferred Doctor: ");
                            prefDoctor = selectionOfDoctor(pur);
                        }
                        //System.out.println("Enter your preferred time of appointment: "); // No exception handling
                        String doctorID = findDoctorID(prefDoctor);
                        String appTime = null;
                        while (appTime == null) appTime = selectTimeSlot(prefDoctor);
                        schedule.scheduleAppointment(appTime,doctorID,prefDoctor,this.getHospitalId(),this.getName(),pur.name());
                        break;
                    case 2:
                        System.out.println("Enter the Appointment that you want to change: ");
                        String orgAppID = getAppIdFromTime(this.currentApps());
                        if (!schedule.checkAppIDExist(orgAppID)) {
                            System.out.println("Only enter available Appointment IDs");
                            break;
                        }
                        // Find name of doctor/doctorID and then print out all of its available timeslots
                        String orgDoctor = findDoctorFromApptID(orgAppID);
                        //String orgDoctorID = findDoctorID(orgDoctor);
                        String newAppTime = null;
                        while (newAppTime == null) newAppTime = selectTimeSlot(orgDoctor);
                        String newAppId = getAppIdFromTime(newAppTime);
                        schedule.rescheduleAppointment(orgAppID, newAppId, this.getHospitalId());
                        break;
                    case 3:
                        System.out.println("Enter the Appointment ID that you want to cancel: ");
                        String appID2 = InputScanner.sc.nextLine().trim();
                        if (!schedule.checkAppIDExist(appID2)) {
                            System.out.println("Only enter available Appointment IDs");
                            break;
                        }
                        schedule.cancelAppointment(appID2, this.getHospitalId());
                        break;
                    case 4:
                        schedule.viewPatientScheduledAppointments(this.getHospitalId());
                        break;
                    case 5:
                        schedule.viewPatientPendingAppointments(this.getHospitalId());
                        break;
                    case 6:
                        schedule.viewPatientAppointmentOutcomeRecords(this.getHospitalId());
                        break;
                    case 7:
                        System.out.println("Thank you for using our service!!");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 6.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                InputScanner.sc.nextLine(); //
                choice = -1;
                }
        } while(choice!=7);
    }

    public String selectionOfDoctor (purpose purposeOfVisit) {
        // create a array and refer to elements by their index
        int selection = 0;
        List<String> doctors = new ArrayList<>();
        FileIO file = new FileIO("program_files/doctors.csv");
        List<String[]> data = file.getAllRows();
        int i = 1;
        for (String[] row : data) {
            if (row[2].equalsIgnoreCase(purposeOfVisit.name())) {
                System.out.println(i + ". Dr. " + row[1]);
                i++;
                doctors.add(row[1]);
            }
        }
        try {
            selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return doctors.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Please choose a valid doctor number.");
        }
        return null;
    }

    public String selectTimeSlot(String doctorName) {
        // create a array and refer to elements by their index
        int selection = 0;
        List<String> timeSlots = new ArrayList<>();
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        int i = 1;
        System.out.println("Available Time Slots: ");
        for(String[] row : data) {
            if(row[4].equals(doctorName) && (row[0].equals("TRUE") || row[0].equals("true"))) {
                System.out.println(i + ". " + row[2]);
                i++;
                timeSlots.add(row[2]);
            }
        }
        try {
            selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return timeSlots.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Please choose a valid doctor number.");
        }
        return null;
    }

    public String findDoctorID(String doctorName) {
        FileIO file = new FileIO("program_files/doctors.csv");
        List<String[]> data = file.getAllRows();
        for (String[] row : data) {
            if (row[1].equalsIgnoreCase(doctorName)) {
                return row[0];
            }
        }
        return null;
    }

    public String findDoctorFromApptID(String apptID) {
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        for (String[] row : data) {
            if (row[1].equals(apptID)) {
                return row[4]; //returns doctorName
            }
        }
        return null;
    }

    public String getAppIdFromTime(String time) {
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        for (String[] row : data) {
            if (row[2].equals(time)) {
                return row[1]; //returns doctorName
            }
        }
        return null;
    }

    public String currentApps() {
        Schedule schedule = new Schedule();
        List<String> apps = new ArrayList<>();
        List<String[]> data = schedule.getAllRows();
        int i = 1;
        for (String[] row : data) {
            if((row[0].equals("FALSE") || row[0].equals("false")) && row[5].equals(this.getHospitalId()) && (row[8].equals("Pending") || row[8].equals("Confirmed"))) {
                System.out.println(i + ". " + row[2]);
                i++;
                apps.add(row[2]);
            }
        }
        try {
            int selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return apps.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Please choose a valid doctor number.");
        }
        return null;
    }

}


