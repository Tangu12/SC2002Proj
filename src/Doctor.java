import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doctor extends User {
    public Doctor(String name, String hospitalId, domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        Schedule schedule = new Schedule();

        ArrayList<Appointment> appointmentList = new ArrayList<>();

        loadAppointments(appointmentList);

        int choice;
        do {
            System.out.println("Choose the number of function:\n"
                    + "(1) Add your shift\n" // should change to set busy timings, function to choose which appt to accept and decline
                    // should change to set busy timings, function to choose which appt to accept and decline
                    + "(2) View Pending Appointments\n"
                    + "(3) View your scheduled Appointments\n"
                    + "(4) Set your Unavailable Appointment Slots\n"
                    + "(5) View Appointment Outcome Records\n"
                    + "(6) Change the Appointment Status\n"
                    + "(7) Exit");



            // add view patients medical record
            // add update patient medical record
            // view personal schedule VS view upcoming appointments
            // record appointment outcome --> Success, Failure? Prescription..?


            try {
                choice = InputScanner.sc.nextInt();
                InputScanner.sc.nextLine();

                switch (choice) {
                    case 1:
                        do {
                            System.out.println("Please enter your choice to update your shift for upcoming week: \n"
                                    + "(1) " + LocalDate.now() + "\n"
                                    + "(2) " + LocalDate.now().plusDays(1) + "\n"
                                    + "(3) " + LocalDate.now().plusDays(2) + "\n"
                                    + "(4) " + LocalDate.now().plusDays(3) + "\n"
                                    + "(5) " + LocalDate.now().plusDays(4) + "\n"
                                    + "(6) " + LocalDate.now().plusDays(5) + "\n"
                                    + "(7) " + LocalDate.now().plusDays(6) + "\n"
                                    + "(8) Exit");
                            choice = InputScanner.sc.nextInt();

                            switch(choice) {
                                case 1:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 0);
                                    break;
                                case 2:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 1);
                                    break;
                                case 3:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 2);
                                    break;
                                case 4:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 3);
                                    break;
                                case 5:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 4);
                                    break;
                                case 6:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 5);
                                    break;
                                case 7:
                                    schedule.createAppointmentSlot(super.getHospitalId(), super.getName(), 6);
                                    break;
                                case 8:
                                    schedule.sortFile();
                                    System.out.println("Quitting....");
                                    break;
                                default:
                                    System.out.println("Only enter available choices!!");
                                    break;
                            }
                        } while(choice != 8);
                        break;
                    case 2:
                        String accceptedApp = null;
                        while (accceptedApp == null) {
                            System.out.println("Enter your accepted Appointment: ");
                            accceptedApp = listOfPendingApp(appointmentList);
                        }
                        schedule.approveAppointment(accceptedApp); // Or use changeApptStatus function?
                        break;
                    case 3:
                        viewDoctorScheduledAppointments(appointmentList);
                        break;
                    case 4:
                        System.out.println("Enter unavailable timeslot: ");
                        String unavailableTimeslot = InputScanner.sc.nextLine();
                        String unavailID = getAppIdFromTime(unavailableTimeslot);
                        setUnavailableTimeslot(unavailID);
                        break;
                    case 5: // WORK ON THIS
                        schedule.viewDoctorAppointmentOutcomeRecords(this.getHospitalId());
                        break;
                    case 6: // WORK ON THIS
                        System.out.println("Enter the appointment ID: ");
                        String appID = InputScanner.sc.nextLine().trim();
                        if (!schedule.checkAppIDExist(appID)) {
                            System.out.println("Only enter available Appointment IDs");
                            break;
                        }
                        System.out.println("Choose number of the Appointment status to change\n"
                                + "(1) Confirmed\n"
                                + "(2) Cancelled\n"
                                + "(3) Completed\n"
                                + "(4) Pending");
                        int statusChoice = InputScanner.sc.nextInt();
                        status stat;
                        if (statusChoice == 1) stat = status.Confirmed;
                        else if (statusChoice == 2) stat = status.Cancelled;
                        else if (statusChoice == 3) stat = status.Completed;
                        else if (statusChoice == 4) stat = status.Pending;
                        else break;
                        schedule.changeAppointmentStatus(appID, stat);
                        break;
                    case 7:
                        System.out.println("Thank you for using our service!!");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                InputScanner.sc.nextLine(); //
                choice = -1;
            }
        } while (choice != 6);
    }


    public String listOfPendingApp(ArrayList<Appointment> appointmentList) {
        viewDoctorPendingAppointments(appointmentList);
        int selection = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine(); // Buffer
        return appointmentList.get(selection-1).getAppID();
//        Schedule schedule = new Schedule();
//        List<String[]> data = schedule.getAllRows();
//        int i = 1;
//        for (String[] row : data) {
//            if ((row[0].equals("FALSE") || row[0].equals("false")) && row[3].equals(this.getHospitalId()) && row[8].equals("Pending")) {
//                System.out.println(i + ". " + row[7] + " at time: " + row[2]);
//                i++;
//                apps.add(row[1]);
//            }
//        }
//        try {
//            int selection = InputScanner.sc.nextInt();
//            InputScanner.sc.nextLine();
//            return apps.get(selection - 1);
//        } catch (InputMismatchException e) {
//            System.out.println("Invalid input.");  // can just return a random string to exit
//            InputScanner.sc.nextLine();
//            // Clear invalid input
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("Selected number is out of range."); // can just return a random string to exit
//        }
//        return null;
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

    public void setUnavailableTimeslot(String unAvailableID) {
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        for(String[] row : data) {
            if(row[2].equals(unAvailableID) && (row[0].equals("TRUE") || row[0].equals("true"))) {
                row[0] = "false";
                break;
            }
        }
        schedule.changeAppointmentStatus(unAvailableID, status.Unavailable);
    }


    public void cancelAppointment(String oldID, ArrayList<Appointment> appointmentList) {
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getAppID(), oldID)) {
                appointments.setStatusOfApp(status.Cancelled);
            }
        }
    }


    public void viewDoctorScheduledAppointments(ArrayList<Appointment> appointmentList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        int i = 1;
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Confirmed) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Dr. " + appointments.getDocName());
                i++;
            }
        }
    }


    public void viewDoctorPendingAppointments(ArrayList<Appointment> appointmentList) {
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Appointment appointments : appointmentList) {
            if (Objects.equals(appointments.getDocID(), this.getHospitalId()) && appointments.getStatusOfApp() == status.Pending) {
                System.out.println(i + ". " + appointments.getPurposeOfApp() + " at " + appointments.getTimeOfApp().format(formatter) + " with Dr. " + appointments.getDocName());
                i++;
            }
        }
    }
}


