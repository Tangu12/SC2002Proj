import java.time.format.DateTimeFormatter;
import java.util.*;


public class Patient extends User {
    public Patient(String name, String hospitalId, Domain domain, String gender, int age) {
        super(name, hospitalId, domain, gender, age);
    }

    public void homePage() {
        Schedule schedule = new Schedule();
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        loadAppointments(appointmentList);

        int choice;
        do {

// Patient Menu:
//● View Medical Record
//● Update Personal Information
//● View Available Appointment Slots
//● Schedule an Appointment
//● Reschedule an Appointment
//● Cancel an Appointment
//● View Scheduled Appointments
//● View Past Appointment Outcome Records
//● Logout


            System.out.println("Choose the number of function:\n"
                    + "(1) View Medical Record\n"
                    + "(2) Update Personal Information\n"
                    + "(3) View Available Appointment Slots\n"
                    + "(4) Schedule an Appointment\n" // case 1
                    + "(5) Reschedule an Appointment\n" // case 2
                    + "(6) Cancel an Appointment\n" // case 3
                    + "(7) View Scheduled Appointments\n" // case 4
                    + "(8) View Past Appointment Outcome Records\n"
                    + "(9) Exit\n");


            try {
                choice = InputScanner.sc.nextInt();
                InputScanner.sc.nextLine(); // Buffer
                switch (choice) {
                    case 1:
                        MedicalRecord.viewMedicalRecord();
                        break;
                    case 2:
                        MedicalRecord.updatePersonalInformation();
                        break;
                    case 3:
                        // View available appointment slots
                        viewAvailableAppointmentSlots(appointmentList);
                        break;
                    case 4:
                        scheduleAppointment(appointmentList);
                        break;
                    case 5:
                        System.out.println("Enter the Appointment that you want to change: ");
                        String orgAppID = getAppIdFromTime(appointmentList);
                        if (!schedule.checkAppIDExist(orgAppID)) { // Keeping this?
                            System.out.println("Only enter available Appointment IDs");
                            break;
                        }
                        scheduleAppointment(appointmentList);
                        break;
                    case 6:
                        System.out.println("Enter the time slot that you want to cancel: ");
                        String oldAppID = getAppIdFromTime(appointmentList);
                        cancelAppointment(oldAppID, appointmentList);
                        break;
                    case 7:
                        viewPatientScheduledAppointments(appointmentList);
                        break;
                    case 8:
                        // View Past Appointment Outcome Records
                        viewPatientPastAppointmentOutcomeRecord(appointmentList);
                        break;
                    case 9:
                        System.out.println("Thank you for using our service!!");
                        // Update file
                        updateAppointmentFile(appointmentList);
                        break;
//                    case 8:
//                        viewPatientPendingAppointments(appointmentList);
//                        break;
//                    case 9:
//                        schedule.viewPatientAppointmentOutcomeRecords(this.getHospitalId());
//                        break;
//                    case 10:
//                        System.out.println("Thank you for using our service!!");
//                        // Update file
//                        updateAppointmentFile(appointmentList);
//                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 6.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                InputScanner.sc.nextLine(); //
                choice = -1;
            }
        } while (choice != 7);
    }


    // New functions for creating appointment objects
    public void viewAvailableAppointmentSlots(ArrayList<Appointment> appointmentList) {
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (appointments.getAvail()) {
                System.out.println(i + ". Dr. " + appointments.getDocName() + " available for " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp());
            }
        }
    }


    public void scheduleAppointment(ArrayList<Appointment> appointmentList) {
        try {
            DateTimeFormatter formatterForID = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            System.out.println("Choose number of the purpose of your Appointment:\n" // need exception handling?
                    + "(1) CheckUp\n"
                    + "(2) Surgery\n"
                    + "(3) Consultation\n"
                    + "(4) Other\n");
            int choicePur = InputScanner.sc.nextInt();  // Exception handling
            InputScanner.sc.nextLine(); // Buffer
            purpose pur;
            if (choicePur == 1) pur = purpose.CheckUp;
            else if (choicePur == 2) pur = purpose.Surgery;
            else if (choicePur == 3) pur = purpose.Consultation;
            else pur = purpose.Other; // No exception handling yet, plus abit suss
            // enter the department of your visit (check Appointments.java)
            System.out.println("Choose the department of your Appointment:\n"
                    + "(1) Cardiology\n"
                    + "(2) Neurology\n"
                    + "(3) Oncology\n"
                    + "(4) Dermatology\n"
                    + "(5) Endocrinology\n"
                    + "(6) Gastroenterology\n"
                    + "(7) Nephrology\n"
                    + "(8) Pulmonology\n"
                    + "(9) Rheumatology\n"
                    + "(10) ObstetricsGynecology\n"
                    + "(11) Others\n");
            int choiceDep = InputScanner.sc.nextInt(); // Exception handling
            InputScanner.sc.nextLine(); // Buffer
            department dept;
            if (choiceDep == 1) dept = department.Cardiology;
            else if (choiceDep == 2) dept = department.Neurology;
            else if (choiceDep == 3) dept = department.Oncology;
            else if (choiceDep == 4) dept = department.Dermatology;
            else if (choiceDep == 5) dept = department.Endocrinology;
            else if (choiceDep == 6) dept = department.Gastroenterology;
            else if (choiceDep == 7) dept = department.Nephrology;
            else if (choiceDep == 8) dept = department.Pulmonology;
            else if (choiceDep == 9) dept = department.Rheumatology;
            else if (choiceDep == 10) dept = department.ObstetricsGynecology;
            else dept = department.Others;

            String prefDoctor = null;
            while (prefDoctor == null) {
                System.out.println("Enter your preferred Doctor: ");
                prefDoctor = selectionOfDoctor(pur, dept, appointmentList);
            }
            String doctorID = findDoctorID(prefDoctor);
            String appTime = null;
            while (appTime == null) appTime = selectionOfTimeSlot(prefDoctor, appointmentList);
            Appointment newApp = new Appointment(false, "A" + appTime.format(String.valueOf(formatterForID)), appTime, doctorID, prefDoctor, this.getHospitalId(), this.getName(), pur, dept, status.Pending, 0, paymentStatus.Pending, " ");
            appointmentList.add(newApp);
        } catch (Exception e) { // add this to cancel
            System.out.println("Not Scheduled");
        }
    }


    public String selectionOfDoctor(purpose purposeOfVisit, department appDepartment, ArrayList<Appointment> appointmentList) throws Exception {
        // create a array and refer to elements by their index
        int selection = 0;
        int i = 1;
        List<String> doctors = new ArrayList<>();
        for (Appointment appointments : appointmentList) {
            if (appointments.getAvail() && appointments.getAppointmentDepartment() == appDepartment) {
                String possibleDoctor = appointments.getDocName();
                if (doctors.contains(possibleDoctor)) continue;
                System.out.println(i + ". Dr. " + possibleDoctor);
                i++;
                doctors.add(possibleDoctor);
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
            System.out.println("Selected number is out of range. Back to Home Page");
            // how to return it back to home page?
        }
        if (selection == -1) { // add this to cancel
            throw new Exception();
        }
        return null;
    }


    public String selectionOfTimeSlot(String doctorName, ArrayList<Appointment> appointmentList) {
        // create a array and refer to elements by their index
        int selection = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<String> timeSlots = new ArrayList<>();
        int i = 1;
        System.out.println("Available Time Slots: ");
        for (Appointment appointments : appointmentList) {
            if (appointments.getAvail() && appointments.getDocName() == doctorName) {
                String appTime = String.valueOf(appointments.getTimeOfApp().format(formatter));
                System.out.println(i + ". " + appTime);
                i++;
                timeSlots.add(appTime);
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
            System.out.println("Selected number is out of range. Back to Home Page.");
            // how to return it back to the home page?
        }
        return null;
    }


    public String getAppIdFromTime(ArrayList<Appointment> appointmentList) {
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<Appointment> possibleApps = new ArrayList<>();
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), this.getHospitalId()) && (appointments.getStatusOfApp() == status.Confirmed || appointments.getStatusOfApp() == status.Pending)) {
                System.out.println(i + ". " + appointments.getTimeOfApp().format(formatter));
                i++;
                possibleApps.add(appointments);
            }
        }
        try {
            int selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return possibleApps.get(selection - 1).getAppID();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range. Please choose a valid doctor number.");
        }
        return null;
    }


    public void cancelAppointment(String oldID, ArrayList<Appointment> appointmentList) {
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getAppID(), oldID)) {
                appointments.setStatusOfApp(status.Cancelled);
            }
        }
    }


    public void viewPatientScheduledAppointments(ArrayList<Appointment> appointmentList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Confirmed) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Dr. " + appointments.getDocName());
                i++;
            }
        }
    }


    public void viewPatientPastAppointmentOutcomeRecord(ArrayList<Appointment> appointmentList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Completed) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Dr. " + appointments.getDocName() + " had this feedback: " + appointments.getAppointOutcomeRecord());
                i++;
            }
        }
    }


    public void viewPatientPendingAppointments(ArrayList<Appointment> appointmentList) {
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getPatID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Pending) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Dr. " + appointments.getDocName());
                i++;
            }
        }
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


    // Update file function?
    public void updateAppointmentFile(ArrayList<Appointment> appointmentList) {
        int i = -1;
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (String[] row : data) {
            if (i == -1) {
                row[0] = "Availability";
                row[1] = "AppointmentID";
                row[2] = "TimeOfAppointment";
                row[3] = "DoctorID";
                row[4] = "DoctorName";
                row[5] = "PatientID";
                row[6] = "PatientName";
                row[7] = "PurposeOfAppointment";
                row[8] = "Department";
                row[9] = "StatusOfAppointment";
                row[10] = "Cost";
                row[11] = "PaymentStatus";
                i++;
                continue;
            }
            if (i - 1 < appointmentList.size()) {
                row[0] = String.valueOf(appointmentList.get(i).getAvail());
                row[1] = appointmentList.get(i).getAppID();
                row[2] = appointmentList.get(i).getTimeOfApp().format(formatter);
                row[3] = appointmentList.get(i).getDocID();
                row[4] = appointmentList.get(i).getDocName();
                row[5] = appointmentList.get(i).getPatID();
                row[6] = appointmentList.get(i).getPatName();
                row[7] = appointmentList.get(i).getPurposeOfApp().toString();
                row[8] = appointmentList.get(i).getAppointmentDepartment().toString();
                row[9] = appointmentList.get(i).getStatusOfApp().toString();
                row[10] = String.valueOf(appointmentList.get(i).getCostOfApp());
                row[11] = appointmentList.get(i).getPaymentStatus().toString();
                i++;
            }
        } schedule.updateFile(data);
    }
}

// I am very proud! :D
