import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    public Doctor(String name, String hospitalId, domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        Schedule schedule = new Schedule();
        int choice;
        do {
            System.out.println("Choose the number of function:\n"
                    //+ "(1) Create Appointment Slots\n" // should change to set busy timings, function to choose which appt to accept and decline
                    // should change to set busy timings, function to choose which appt to accept and decline
                    + "(1) View Pending Appointments\n"
                    + "(2) View your scheduled Appointments\n"
                    + "(3) Set your Unavailable Appointment Slots\n"
                    + "(4) View Appointment Outcome Records\n"
                    + "(5) Change the Appointment Status\n"
                    + "(6) Exit");

            try {
                choice = InputScanner.sc.nextInt();
                InputScanner.sc.nextLine();

                switch (choice) {
                    case 1:
                        String accceptedApp = null;
                        while (accceptedApp == null) {
                            System.out.println("Enter your accepted Appointment: ");
                            accceptedApp = listOfPendingApp(this.hospitalId);
                        }
                        schedule.approveAppointment(accceptedApp); // Or use changeApptStatus function?
                        break;
                    case 2:
                        schedule.viewDoctorScheduledAppointments(this.getHospitalId());
                        break;
                    case 3:
                        System.out.println("Enter unavailable timeslot: ");
                        String unavailableTimeslot = InputScanner.sc.nextLine();
                        String unavailID = getAppIdFromTime(unavailableTimeslot);
                        setUnavailableTimeslot(unavailID);
                        break;
                    case 4:
                        schedule.viewDoctorAppointmentOutcomeRecords(this.getHospitalId());
                        break;
                    case 5:
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
                    case 6:
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


    public String listOfPendingApp(String docID) {
        List<String> apps = new ArrayList<>();
        Schedule schedule = new Schedule();
        List<String[]> data = schedule.getAllRows();
        int i = 1;
        for (String[] row : data) {
            if ((row[0].equals("FALSE") || row[0].equals("false")) && row[3].equals(this.getHospitalId()) && row[8].equals("Pending")) {
                System.out.println(i + ". " + row[7] + " at time: " + row[2]);
                i++;
                apps.add(row[1]);
            }
        }
        try {
            int selection = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();
            return apps.get(selection - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");  // can just return a random string to exit
            InputScanner.sc.nextLine();
            // Clear invalid input
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Selected number is out of range."); // can just return a random string to exit
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


}


// Method to find current schedule of doctor